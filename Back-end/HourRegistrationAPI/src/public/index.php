<?php

use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Message\ServerRequestInterface as Request;
use Slim\Factory\AppFactory;

require  '../vendor/autoload.php';

require '../includes/DbConnect.php';
require '../includes/DbProjectOperations.php';
require '../includes/DbCompanyOperations.php';
require '../includes/DbUserOperations.php';
require '../includes/DbCalendarOperations.php';

$app = AppFactory::create();
$app->addBodyParsingMiddleware(); // <<<--- add this middleware!
$app->addRoutingMiddleware();
$app->addErrorMiddleware(true, true, true);

/* test request */
$app->get('/hello/{name}', function (Request $request, Response $response, array $args) {
    $name = $args['name'];
    $response->getBody()->write("Hello, $name");
    return $response;
});

/*
    endpoint: createproject
    parameters: name, hours, tag
    method: POST
*/
$app->post('/createproject', function (Request $request, Response $response) {

    //Check for empty parameters
    if (!haveEmptyParameters(array('name', 'hours', 'tag'), $request, $response)) {
        
        // Put the requested data(parameters) in a variable(array) and put them in seperate variables
        $request_data = $request->getParsedBody();
        $name = $request_data['name'];
        $hours = $request_data['hours'];
        $tag = $request_data['tag'];
        //new Object of DbProjectOperations class
        $db = new DbProjectOperations;
        //Put the return value of the createUser operation in a variable
        $result = $db->createProject($name, $hours, $tag);
        /*
            If the result of the createUser operation is "USER_CREATED", "USER_FAILURE" or "USER_EXISTS"
            an array is created called "message" containing error (true or false)
            and a message. The content of "message" is added to a response variable
            and returned. 
        */
        if ($result == CREATED) {
            $message = array();
            $message['error'] = false;
            $message['message'] = 'Project created successfully';
            $response->getBody()->write(json_encode($message));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(201);
        } else if ($result == USER_FAILURE) {
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Some error occurred';
            $response->getBody()->write(json_encode($message));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(422);
        } else if ($result == EXISTS) {
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Project Already Exists';
            $response->getBody()->write(json_encode($message));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(422);
        }
    }
    //If this code is reached the response comes from haveEmptyParameters function
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);
});

/*
   Read Operation
   Function is returning all the projects from database
*/
$app->get('/getallprojects', function (Request $request, Response $response) {
    $db = new DbProjectOperations();

    $projects = $db->getAllProjects();

    $response_data =array();
    $response_data['error'] = false;
    $response_data['projects'] = $projects;
    $response->getBody()->write(json_encode($response_data));

    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);
});

/*
 Read Operation
   Function is returning all the projects from database based on a ID
*/
$app->get('/getprojectbyid/{id}', function (Request $request, Response $response, array $args) {
    $db = new DbProjectOperations();
    $id = $args['id'];
    $project = $db->getProjectById($id);

    if($project == NULL_RETURNED){
        $response_data = array();
        $response_data['error'] = true;
        $response_data['message'] = 'No Data Returned';
        $response->getBody()->write(json_encode($response_data));

        return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);
    }else {
        $response_data = array();
        $response_data['error'] = false;
        $response_data['project'] = $project;
        $response->getBody()->write(json_encode($response_data));

        return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);
    }
});

/*
 Update Operation
  Function is returning all the projects from database based on a ID
    endpoint: update project
    parameters: name, hours, tag
    method: POST
*/
$app->put('/updateproject/{id}', function(Request $request, Response $response, array $args){
    $id = $args['id'];

    if(!haveEmptyParameters(array('name','hours','tag'), $request, $response)){

        $request_data = $request->getParsedBody();
        $name = $request_data['name'];
        $hours = $request_data['hours'];
        $tag = $request_data['tag'];

        $db = new DbProjectOperations();
        if($db->updateProject($name, $hours, $tag, $id)){
            $response_data = array();
            $response_data['error'] = false;
            $response_data['message'] = 'Project Updated Successfully';
            $project = $db->getProjectById($id);
            $response_data['project'] = $project;
            $response->getBody()->write(json_encode($response_data));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);

        }else{
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'Please try again later';
            $project = $db->getProjectById($id);
            $response_data['project'] = $project;
            $response->getBody()->write(json_encode($response_data));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);

        }
    }

    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);
});

$app->delete('/deleteproject/{id}', function(Request $request, Response $response, array $args) {
    $id = $args['id'];
    $db = new DbProjectOperations();
    $response_data = array();
    if ($db->deleteProject($id)) {
        $response_data['error'] = false;
        $response_data['message'] = 'Project has been deleted';
    } else {
        $response_data['error'] = true;
        $response_data['message'] = 'Please try again later';
    }
    $response->getBody()->write(json_encode($response_data));
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);

});

$app->post('/createcompany', function(Request $request, Response $response, array $args){
    //Check for empty parameters
    if (!haveEmptyParameters(array('name', 'password'), $request, $response)) {
        //Put the requested data(parameters) in a variable(array) and put them in seperate variables
        $request_data = $request->getParsedBody();
        $name = $request_data['name'];
        $password = $request_data['password'];
        $hash_password = password_hash($password, PASSWORD_DEFAULT);
        //new Object of DbProjectOperations class
        $db = new DbCompanyOperations();
        //Put the return value of the createUser operation in a variable
        $result = $db->createCompany($name, $hash_password);
        /*
            If the result of the createUser operation is "USER_CREATED", "USER_FAILURE" or "USER_EXISTS"
            an array is created called "message" containing error (true or false)
            and a message. The content of "message" is added to a response variable
            and returned.
        */
        if ($result == CREATED) {
            $message = array();
            $message['error'] = false;
            $message['message'] = 'Company created successfully';
            $response->getBody()->write(json_encode($message));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(201);
        } else if ($result == FAILURE) {
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Some error occurred';
            $response->getBody()->write(json_encode($message));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(422);
        } else if ($result == EXISTS) {
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Company Already Exists';
            $response->getBody()->write(json_encode($message));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(422);
        }
    }
    //If this code is reached the response comes from haveEmptyParameters function
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);
});
/*
   Read Operation
   Function is returning all the companies from database
*/
$app->get('/getallcompanies', function (Request $request, Response $response) {
    $db = new DbCompanyOperations();
    $companies = $db->getAllCompanies();

    $response_data =array();
    $response_data['error'] = false;
    $response_data['companies'] = $companies;
    $response->getBody()->write(json_encode($response_data));

    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);
});

$app->post('/companylogin', function(Request $request, Response $response){

});

$app->get('/getcompanybyid/{id}', function(Request $request, Response $response, array $args){
    $db = new DbCompanyOperations();
    $id = $args['id'];
    $company = $db->getCompanyById($id);

    if($company == NULL_RETURNED){
        $response_data = array();
        $response_data['error'] = true;
        $response_data['message'] = 'No Data Returned';
        $response->getBody()->write(json_encode($response_data));

        return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);
    }else {
        $response_data = array();
        $response_data['error'] = false;
        $response_data['company'] = $company;
        $response->getBody()->write(json_encode($response_data));

        return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);
    }
});

$app->delete('/deletecompany/{id}', function (Request $request, Response $response, array $args){
    $id = $args['id'];
    $db = new DbCompanyOperations();
    $response_data = array();
    if ($db->deleteCompany($id)) {
        $response_data['error'] = false;
        $response_data['message'] = 'Company has been deleted';
    } else {
        $response_data['error'] = true;
        $response_data['message'] = 'Please try again later';
    }
    $response->getBody()->write(json_encode($response_data));
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);
});

$app->put('/updatecompany/{id}', function(Request $request, Response $response, array $args){
    $id = $args['id'];

    if(!haveEmptyParameters(array('name', 'password'), $request, $response)){
        $request_data = $request->getParsedBody();
        $name = $request_data['name'];
        $password = $request_data['password'];
        $hash_password = password_hash($password, PASSWORD_DEFAULT);
        $db = new DbCompanyOperations();
        if($db->updateCompany($name, $hash_password, $id)){
            $response_data = array();
            $response_data['error'] = false;
            $response_data['message'] = 'Company Updated Successfully';
            $company = $db->getCompanyById($id);
            $response_data['company'] = $company;
            $response->getBody()->write(json_encode($response_data));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);

        }else{
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'Please try again later';
            $company = $db->getProjectById($id);
            $response_data['company'] = $company;
            $response->getBody()->write(json_encode($response_data));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);

        }
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);
});

$app->get('/getcompanypassbyid/{id}', function(Request $request, Response $response, array $args){
    $db = new DbCompanyOperations();
    $id = $args['id'];
    $password = $db->getCompanyPasswordById($id);

    if($password == NULL_RETURNED){
        $response_data = array();
        $response_data['error'] = true;
        $response_data['message'] = 'No Data Returned';
        $response->getBody()->write(json_encode($response_data));

        return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);
    }else {
        $response_data = array();
        $response_data['error'] = false;
        $response_data['companypassword'] = $password;
        $response->getBody()->write(json_encode($response_data));

        return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);
    }
});

$app->post('/createuser', function(Request $request, Response $response){
    //Check for empty parameters
    if (!haveEmptyParameters(array('admin', 'firstname', 'lastname', 'calendar_id', 'Company_id'), $request, $response)) {
        //Put the requested data(parameters) in a variable(array) and put them in seperate variables
        $request_data = $request->getParsedBody();
        $admin = $request_data['admin'];
        $firstname = $request_data['firstname'];
        $lastname = $request_data['lastname'];
        $calendar_id = $request_data['calendar_id'];
        $Company_id = $request_data['Company_id'];
        //new Object of DbProjectOperations class
        $db = new DbUserOperations();
        //Put the return value of the createUser operation in a variable
        $result = $db->createUser($admin, $firstname, $lastname, $calendar_id, $Company_id);

        /*
            If the result of the createUser operation is "USER_CREATED", "USER_FAILURE" or "USER_EXISTS"
            an array is created called "message" containing error (true or false)
            and a message. The content of "message" is added to a response variable
            and returned.
        */
        if ($result == CREATED) {
            $message = array();
            $message['error'] = false;
            $message['message'] = 'User created successfully';
            $response->getBody()->write(json_encode($message));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(201);
        } else if ($result == FAILURE) {
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Some error occurred';
            $response->getBody()->write(json_encode($message));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(422);
        }
    }
    //If this code is reached the response comes from haveEmptyParameters function
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);
});
//getallusers
/*
   Read Operation
   Function is returning all the companies from database
*/
$app->get('/getallusers', function (Request $request, Response $response) {
    $db = new DbUserOperations();
    $users = $db->getAllUsers();

    $response_data =array();
    $response_data['error'] = false;
    $response_data['users'] = $users;
    $response->getBody()->write(json_encode($response_data));

    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);
});

//deleteuser
$app->delete('/deleteuser/{id}', function (Request $request, Response $response, array $args){
    $id = $args['id'];
    $db = new DbUserOperations();
    $response_data = array();
    if($db->deleteUser($id)){
        $response_data['error'] = false;
        $response_data['message'] = 'User has been deleted';
    }
    else{
        $response_data['error'] = true;
        $response_data['message'] = 'Please try again later';
    }
    $response->getBody()->write(json_encode($response_data));
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);

});

//updateuser
$app->put('/updateuser/{id}', function (Request $request, Response $response, array $args){
    $id = $args['id'];

    if(!haveEmptyParameters(array('admin', 'firstname', 'lastname', 'calendar_id', 'Company_id'), $request, $response)){
        $request_data = $request->getParsedBody();
        $admin = $request_data['admin'];
        $firstname = $request_data['firstname'];
        $lastname = $request_data['lastname'];
        $calendar_id = $request_data['calendar_id'];
        $Company_id = $request_data['Company_id'];

        $db = new DbUserOperations();
        if($db->updateUser($admin, $firstname, $lastname, $calendar_id, $Company_id, $id)){
            $response_data = array();
            $response_data['error'] = false;
            $response_data['message'] = 'User Updated Successfully';
            $calendar = $db->getUserById($id);
            $response_data['calendar'] = $calendar;
            $response->getBody()->write(json_encode($response_data));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);

        }else{
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'Please try again later';
            $calendar = $db->getUserById($id);
            $response_data['calendar'] = $calendar;
            $response->getBody()->write(json_encode($response_data));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);
        }
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);
});

//getuserbyid
$app->get('/getuserbyid/{id}', function(Request $request, Response $response, array $args){
    $db = new DbUserOperations();
    $id = $args['id'];
    $user = $db->getUserById($id);

    if($user == NULL_RETURNED){
        $response_data = array();
        $response_data['error'] = true;
        $response_data['message'] = 'No Data Returned';
        $response->getBody()->write(json_encode($response_data));

        return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);
    }
    $response_data = array();
    $response_data['error'] = false;
    $response_data['calendar'] = $user;
    $response->getBody()->write(json_encode($response_data));

    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);
});

//creatcalendar
$app->post('/createcalendar', function(Request $request, Response $response){
    if (!haveEmptyParameters(array('name', 'appointment'), $request, $response)) {
        //Put the requested data(parameters) in a variable(array) and put them in seperate variables
        $request_data = $request->getParsedBody();
        $name = $request_data['name'];
        $appointment = $request_data['appointment'];
        //new Object of DbProjectOperations class
        $db = new DbCalendarOperations();
        //Put the return value of the createUser operation in a variable
        $result = $db->createCalendar($name, $appointment);
        /*
            If the result of the createUser operation is "USER_CREATED", "USER_FAILURE" or "USER_EXISTS"
            an array is created called "message" containing error (true or false)
            and a message. The content of "message" is added to a response variable
            and returned.
        */
        if ($result == CREATED) {
            $message = array();
            $message['error'] = false;
            $message['message'] = 'Calendar created successfully';
            $response->getBody()->write(json_encode($message));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(201);
        } else if ($result == FAILURE) {
            $message = array();
            $message['error'] = true;
            $message['message'] = 'Some error occurred';
            $response->getBody()->write(json_encode($message));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(422);
        }
    }
    //If this code is reached the response comes from haveEmptyParameters function
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(422);
});

//getallcalendars
$app->get('/getallcalendars', function(Request $request, Response $response){
   $db = new DbCalendarOperations();
   $calendars = $db->getAllCalendars();
   $response_data = array();
   $response_data['error'] = "false";
   $response_data['calendars'] = $calendars;

   $response->getBody()->write(json_encode($response_data));

    return $response
        ->withHeader('Content-Type', 'application/json')
        ->withStatus(200);
});

//deletecalendar
$app->delete('/deletecalendar/{id}', function (Request $request, Response $response, array $args){
    $id = $args['id'];
    $db = new DbCalendarOperations();
    $response_data = array();
    if($db->deleteCalendar($id)){
        $response_data['error'] = false;
        $response_data['message'] = 'Company has been deleted';
    }
    else{
        $response_data['error'] = true;
        $response_data['message'] = 'Please try again later';
    }
    $response->getBody()->write(json_encode($response_data));
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);

});

//updatecalendar
$app->put('/updatecalendar/{id}', function (Request $request, Response $response, array $args){
    $id = $args['id'];

    if(!haveEmptyParameters(array('name', 'appointment'), $request, $response)){
        $request_data = $request->getParsedBody();
        $name = $request_data['name'];
        $appointment = $request_data['appointment'];

        $db = new DbCalendarOperations();
        if($db->updateCalendar($name, $appointment, $id)){
            $response_data = array();
            $response_data['error'] = false;
            $response_data['message'] = 'Calendar Updated Successfully';
            $calendar = $db->getCalendarById($id);
            $response_data['calendar'] = $calendar;
            $response->getBody()->write(json_encode($response_data));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);

        }else{
            $response_data = array();
            $response_data['error'] = true;
            $response_data['message'] = 'Please try again later';
            $calendar = $db->getProjectById($id);
            $response_data['calendar'] = $calendar;
            $response->getBody()->write(json_encode($response_data));
            return $response
                ->withHeader('Content-type', 'application/json')
                ->withStatus(200);

        }
    }
    return $response
        ->withHeader('Content-type', 'application/json')
        ->withStatus(200);
});

//getcalendarbyid
$app->get('/getcalendarbyid/{id}', function(Request $request, Response $response, array $args){
    $db = new DbCalendarOperations();
    $id = $args['id'];
    $calendar = $db->getCalendarById($id);

    if($calendar == NULL_RETURNED){
        $response_data = array();
        $response_data['error'] = true;
        $response_data['message'] = 'No Data Returned';
        $response->getBody()->write(json_encode($response_data));

        return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);
    }
        $response_data = array();
        $response_data['error'] = false;
        $response_data['calendar'] = $calendar;
        $response->getBody()->write(json_encode($response_data));

        return $response
            ->withHeader('Content-type', 'application/json')
            ->withStatus(200);

});

$app->post('/tokensignin', function (Request $request, Response $response){
    include_once dirname(__FILE__)  . '/Constants.php';
   // require_once 'google-api-php-client/vendor/autoload.php';
    //$client = new Google_Client(['client_id' => $CLIENT_ID]);
    $verify = new Google_AccessToken_Verify();
    $request_data = $request->getParsedBody();
    $id_token = $request_data['id_token'];
    // $client->setScopes('email');  
    // $client->setRedirectUri('/tokensignin');
    // $client->setAccessType('offline');
    // $client->setApprovalPrompt('force');
    $payload = $verify->verifyIdToken($id_token);
    if ($payload) {
        $userid = $payload['sub'];
        $username = $payload['name'];
        print($username);
    } else {
        // Invalidsss ID token
    }
});



//Checks if the parameters are empty or not and returns json object with the missing parameters
function haveEmptyParameters($required_params, $request, $response)
{
    $error = false;
    $error_params = '';
    //Puts every filled in parameter of the request into the request_params array
    $request_params = $request->getParsedBody();
    /*
        Loops through the required_params and checks if every single required parameter is set in the request_params also makes sure
        that the length is more than zero characters. If thats not the case the error is set to true and the missing param is added to 
        error_params.
    */
    foreach ($required_params as $param) {
        if (!isset($request_params[$param]) || strlen($request_params[$param]) <= 0) {
            $error = true;
            $error_params .= $param . ', ';
        }
    }
    /*
        If the error variable is set to true the error_detail array is created and a message is added with all the missing parameters (deleted te last comma).
        The response is finally the json encoded version of the error_detail error. and this repsonse will be returned if there are missing parameters. 
    */
    if ($error) {
        $error_detail = array();
        $error_detail['error'] = true;
        $error_detail['message'] = 'Required parameter(s) ' . substr($error_params, 0, -2) . ' are missing or empty';
        $response->getBody()->write(json_encode($error_detail));
    }
    //Can be true or false.
    return $error;
}
$app->run();
