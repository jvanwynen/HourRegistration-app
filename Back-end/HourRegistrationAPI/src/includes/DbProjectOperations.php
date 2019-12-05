<?php
class DbProjectOperations
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

    /*  Create Operation
        The function will insert a new project in our database
    */
    public function createProject($name, $hours, $tag)
    {
        if (!$this->isProjectExist($name)) {
            $statement = $this->con->prepare("INSERT INTO project (name, hours, tag) VALUES (?, ?, ?)");
            $statement->bind_param("sss", $name, $hours, $tag);
            if ($statement->execute()) {
                return CREATED;
            }
        } else if ($this->isProjectExist($name)) {
            return EXISTS;
        }
        return FAILURE;
    }

    /*
        Read Operation
        The function is checking if the project exist in the database or not
    */
    private function isProjectExist($name)
    {
        $stmt = $this->con->prepare("SELECT id FROM project WHERE name = ?");
        $stmt->bind_param("s", $name);
        $stmt->execute();
        $stmt->store_result();
        return $stmt->num_rows > 0;
    }

    /*
       Read Operation
       Function is returning all the projects from database
    */
    public function getAllProjects(){
        $stmt = $this->con->prepare("SELECT id, name, hours, tag FROM project;");
        $stmt->execute();
        $stmt->bind_result($id, $name, $hours, $tag);
        $projects = array();
        while($stmt->fetch()){
            $project = array();
            $project['id'] = $id;
            $project['name']=$name;
            $project['hours'] = $hours;
            $project['tag'] = $tag;
            array_push($projects, $project);
        }
        return $projects;
    }

    /*
     Read Operation
       Function is returning all the projects from database based on a ID
    */
    public function getProjectById($id){
        $statement = $this->con->prepare("SELECT id, name, hours, tag FROM project WHERE id = ?;");
        $statement->bind_param("s", $id);
        $statement->execute();
        $statement->bind_result($id, $name, $hours, $tag);
        $statement->fetch();
        $project = array();
        $project['id'] = $id;
        $project['name'] = $name;
        $project['hours'] = $hours;
        $project['tag'] = $tag;
        if(!array_filter($project)){
            return NULL_RETURNED;
        }
        return $project;
    }

    public function updateProject($name, $hours, $tag, $id){
        $statement = $this->con->prepare("UPDATE project SET name = ?, hours = ?, tag = ? WHERE  id = ?;");
        $statement->bind_param("sssi", $name, $hours, $tag, $id);
        if($statement->execute()){
            return true;
        }
        return false;
    }

    public function deleteProject($id){
        $statement = $this->con->prepare("DELETE FROM project WHERE id = ?");
        $statement->bind_param("i", $id);
        if($statement->execute()){
            return true;
        }
        return false;
    }

    /*
     * TO BE DONE
     * COMPANY TABLE: CRUD
     * USER TABLE: CRUD
     */
}
