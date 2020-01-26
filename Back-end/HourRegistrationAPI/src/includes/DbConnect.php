<?php

class DbConnect
{

    //    $host = "localhost";
    //    $port = 3306;
    //    $socket = "";
    //    $user = "root";
    //    $password = "root";
    //    $dbname = "mydb";

        private $con;

        function connect(){
            include_once dirname(__FILE__)  . '/Constants.php';

            $this->con = new mysqli(DB_HOST, DB_USER, DB_PASSWORD, DB_NAME);
            if(mysqli_connect_errno()){
                echo "Failed  to connect " . mysqli_connect_error();
                return null;
            }
            return $this->con;
        }
}
