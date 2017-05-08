<?php
$servername = "localhost";
$username = "SavePass";
$password = "Nisan3817";
$dbname = "SavePass";
$email = $_POST['email'];
$clientUN = $_POST['username'];
$clientPW = $_POST['password'];

// Create connection
$conn = new mysqli($servername,$username,$password,$dbname);
// Check connection
if ($conn->connect_error) {
    die("connection failed: " . $conn->connect_error);
}

$sql = "INSERT INTO `$email` (`username`, `password`) VALUES ('$clientUN', '$clientPW');";

$query = $conn->query($sql);
if ($query) {
    $jsonAns = ['addedToDB' => 'true'];
    header('content-type: application/json');
    echo json_encode($jsonAns);
}
