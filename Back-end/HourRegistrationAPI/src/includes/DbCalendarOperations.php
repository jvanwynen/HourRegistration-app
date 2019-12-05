<?php


class DbCalendarOperations
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
    private function isCalendarExists($name){
        $stmt = $this->con->prepare("SELECT id FROM calendar WHERE name = ?");
        $stmt->bind_param("s", $name);
        $stmt->execute();
        $stmt->store_result();
        return $stmt->num_rows > 0;
    }

    /*  Create Operation
    The function will insert a new project in our database
    */
    public function createCalendar($name, $appointment)
    {
        if (!$this->isCalendarExists($name)) {
            $statement = $this->con->prepare("INSERT INTO calendar (name, appointment) VALUES (?, ?);");
            $statement->bind_param("ss", $name, $appointment);
            if ($statement->execute()) {
                return CREATED;
            }
        } else if ($this->isCalendarExists($name)) {
            return EXISTS;
        }
        return FAILURE;
    }

    public function getAllCalendars(){
        $statement = $this->con->prepare("SELECT name, appointment FROM calendar;");
        $statement->execute();
        $statement->bind_result($name, $appointment);
        $calendars = array();
        while($statement->fetch()){
            $calendar = array();
            $calendars['name'] = $name;
            $calendars['appointment'] = $appointment;
            array_push($calendars, $calendar);
        }
        return $calendars;
    }

    public function deleteCalendar($id){
        $statement = $this->con->prepare("DELETE * FROM calendar WHERE ");
    }
}