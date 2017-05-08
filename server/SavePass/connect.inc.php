<?php
$servername = "localhost";
$username = "SavePass";
$password = "Nisan3817";
$dbname = "SavePass";

// Create connection
$conn = new mysqli($servername,$username,$password,$dbname);
// Check connection
if ($conn->connect_error) {
    die("connection failed: " . $conn->connect_error);
}

?>
