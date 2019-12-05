<?php


class DbUserOperations
{
    //the database connection variable
    private $con;

    //inside constructor
    //we are getting the connection link
    function __construct()
    {
        require_once dirname(__FILE__) . '/DbConnect.php';
        $db = new DbConnect;
        return $this->con = $db->connect();
    }

    /*
    Read Operation
    The function is checking if the project exist in the database or not
    */


    //createuser
    /*  Create Operation
    The function will insert a new project in our database
*/
    public function createUser($admin, $firstname, $lastname, $calendar_id, $Company_id)
    {
            $statement = $this->con->prepare("INSERT INTO user (admin, firstname, lastname, calendar_id, Company_id) VALUES (?, ?, ?, ?, ?);");
            $statement->bind_param("issii", $admin, $firstname, $lastname, $calendar_id, $Company_id);

            if ($statement->execute()) {
                echo "test";
                return CREATED;
            }else{
                return FAILURE;
            }

    }
    //getallusers
    //deleteuser
    //updateuser
    //getuserbyid

}