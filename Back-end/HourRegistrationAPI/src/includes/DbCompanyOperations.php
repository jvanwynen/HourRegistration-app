<?php
class DbCompanyOperations
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

    private function isCompanyExists($name)
    {
        $stmt = $this->con->prepare("SELECT id FROM company WHERE name = ?");
        $stmt->bind_param("s", $name);
        $stmt->execute();
        $stmt->store_result();
        return $stmt->num_rows > 0;
    }

    public function createCompany($name, $password)
    {
        if (!$this->isCompanyExists($name)) {
            $statement = $this->con->prepare("INSERT INTO company (name, password) VALUES (?, ?)");
            $statement->bind_param("ss", $name, $password);
            if ($statement->execute()) {
                return CREATED;
            }
        } else if ($this->isCompanyExists($name)) {
            return EXISTS;
        }
        return FAILURE;
    }

    public function getAllCompanies(){
        $stmt = $this->con->prepare("SELECT id, name FROM company;");
        $stmt->execute();
        $stmt->bind_result($id, $name);
        $companies = array();
        while($stmt->fetch()){
            $company = array();
            $company['id'] = $id;
            $company['name']=$name;
            array_push($companies, $company);
        }
        return $companies;
    }

    /*
    The method is returning the password of a given user
    to verify the given password is correct or not
*/
    public function getCompanyPasswordById($id){
        $stmt = $this->con->prepare("SELECT password FROM company WHERE id = ?");
        $stmt->bind_param("s", $id);
        $stmt->execute();
        $stmt->bind_result($password);
        $stmt->fetch();
        if($password == null){
            return NULL_RETURNED;
        }
        return $password;
    }

    public function getCompanyById($id){
        $statement = $this->con->prepare("SELECT id, name FROM company WHERE id = ?;");
        $statement->bind_param("s", $id);
        $statement->execute();
        $statement->bind_result($id, $name);
        $statement->fetch();
        $company = array();
        $company['id'] = $id;
        $company['name'] = $name;
        if(!array_filter($company)){
            return NULL_RETURNED;
        }
        return $company;
    }

//deletecompany
    public function deleteCompany($id){
            $statement = $this->con->prepare("DELETE FROM company WHERE id = ?");
            $statement->bind_param("i", $id);
            if($statement->execute()){
                return true;
            }
            return false;
    }
//updatecompany
    public function updateCompany($name, $password, $id){
        $statement = $this->con->prepare("UPDATE company SET name = ?, password = ? WHERE  id = ?;");
        $statement->bind_param("ssi", $name, $password, $id);
        if($statement->execute()){
            return true;
        }
        return false;
    }
}