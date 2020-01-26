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

    public function createUser($admin, $firstname, $lastname, $calendar_id, $Company_id)
    {
        
            $statement = $this->con->prepare("INSERT INTO user (admin, firstname, lastname, calendar_id, Company_id) VALUES (?, ?, ?, ?, ?);");
            $statement->bind_param("issii", $admin, $firstname, $lastname, $calendar_id, $Company_id);

            if ($statement->execute()) {
                return CREATED;
            }else{
                return FAILURE;
            }

    }

    public function getAllUsers(){
        $statement = $this->con->prepare("SELECT id, admin, firstname, lastname, calendar_id, Company_id FROM user;");
        $statement->execute();
        $statement->bind_result($id, $admin, $firstname, $lastname, $calendar_id, $Company_id);
        $users = array();
        while($statement->fetch()){
            $user = array();
            $user['id'] = $id;
            $user['admin'] = $admin;
            $user['firstname'] = $firstname;
            $user['lastname'] = $lastname;
            $user['calendar_id'] = $calendar_id;
            $user['Company_id'] = $Company_id;
            array_push($users, $user);
        }
        return $users;
    }

    public function deleteUser($id){
        $statement = $this->con->prepare("DELETE FROM user WHERE id = ?;");
        $statement->bind_param('i', $id);
        if($statement->execute()){
            return true;
        }
        return false;
    }

    public function updateUser($admin, $firstname, $lastname, $calendar_id, $Company_id, $id){
        $statement = $this->con->prepare("UPDATE user SET admin = ?, firstname = ?, lastname = ?, calendar_id = ?, Company_id = ? WHERE  id = ?;");
        $statement->bind_param("issiii", $admin, $firstname, $lastname, $calendar_id, $Company_id, $id);
        if($statement->execute()){
            return true;
        }
        return false;
    }

    public function getUserById($id){
        $statement = $this->con->prepare("SELECT id, admin, firstname, lastname, calendar_id, Company_id FROM user WHERE id = ?;");
        $statement->bind_param("s", $id);
        $statement->execute();
        $statement->bind_result($id, $admin, $firstname, $lastname, $calendar_id, $Company_id);
        $statement->fetch();
        $user = array();
        $user['id'] = $id;
        $user['admin'] = $admin;
        $user['firstname'] = $firstname;
        $user['lastname'] = $lastname;
        $user['calendar_id'] = $calendar_id;
        $user['Company_id'] = $Company_id;
        if(!array_filter($user)){
            return NULL_RETURNED;
        }
        return $user;
    }
}