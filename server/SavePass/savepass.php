<?php

class SavePass
{

    private $servername;
    private $username;
    private $password;
    private $dbname;
    private $conn;

    public function __construct()
    {
        $this->servername = 'localhost';
        $this->username = "SavePass";
        $this->password = "Nisan3817";
        $this->dbname = "SavePass";
    }

    public function createConnection()
    {
        $this->conn = new mysqli($this->servername,$this->username,$this->password,$this->dbname);
        // Check connection
        if ($this->conn->connect_error) {
            die("connection failed: " . $this->conn->connect_error);
        }
    }

    public function userAddToTable()
    {
        $email = $_POST['email'];
        $clientUN = $_POST['username'];
        $clientPW = $_POST['password'];
        $this->createConnection();
        $sql = "INSERT INTO `$email` (`username`, `password`) VALUES ('$clientUN', '$clientPW');";

        $query = $this->conn->query($sql);
        if ($query) {
            $jsonAns = ['addedToDB' => 'true'];
            header('content-type: application/json');
            echo json_encode($jsonAns);
        }
        $this->conn->close();
    }

    public function createUser()
    {
        $username = $_POST['username'];
        $password = $_POST['password'];
        $email = $_POST['email'];
        $this->createConnection();
        $sql = "INSERT INTO `users` (`username`, `password`, `email`) VALUES ('$username', '$password', '$email');";

        $query = $this->conn->query($sql);
        if ($query) {
            $jsonAns = ['signedup' => 'true'];
            header('content-type: application/json');
            echo json_encode($jsonAns);
            $sql = "CREATE TABLE `$email` (
                username varchar(30),
                password varchar(30)
);";
            $query = $this->conn->query($sql);
        }
        else{
            $msg = "Could not successfully run query.";
            echo $msg;
            $jsonAns = ['signedup'=> 'false'];
            header('content-type: application/json');
            echo json_encode($jsonAns);
            exit;
        }
        $this->conn->close();
    }

    public function init()
    {
        if (isset($_POST['login']))
        {
            $this->login();
        }
        if (isset($_POST['userAddToTable']))
        {
            $this->userAddToTable();
        }
        if (isset($_POST['register']))
        {
            $this->createUser();
        }
        if (isset($_POST['fetchInfo']))
        {
            $this->fetchInfo();
        }
    }

    public function login()
    {
        $email = $_POST['email'];
        $password = $_POST{'password'};
        $this->createConnection();
        $sql = "SELECT `password` ,`email` FROM `users` WHERE `email` = '$email' AND `password` = '$password'";
        $query = $this->conn->query($sql);
        if ($query == false) {
            $msg = "Could not successfully run query ($sql) from DB.";
            exit;
        }
        if (mysqli_num_rows($query) > 0) {
            $jsonAns = ['msg' => 'Logged in successfully'];
            echo (json_encode($jsonAns));
        }
        else
        {
            $jsonAns = ['msg' => 'Wrong username or password'];
            echo (json_encode($jsonAns));
        }
        $this->conn->close();
    }

    public function fetchInfo()
    {
        $email = $_POST['email'];

        $this->createConnection();

        $sql = "SELECT `password` ,`username` from `$email`";

        $result = mysqli_query($this->conn, $sql);
        $data = "";
        if (mysqli_num_rows($result) > 0) {
            // output data of each row
            while($row = mysqli_fetch_assoc($result)) {
                $data = $data . "Username: " . $row["username"]. " \r\n" . "Password: " . $row["password"] . " \r\n\r\n";
            }
            $output = ['Data' => "$data"];
            echo(json_encode($output));
        } else {
            $output = ['Data' => 'No saved information'];
            echo(json_encode($output));
        }

        $this->conn->close();
    }
}

?>