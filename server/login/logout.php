<?php
require '../connect.inc.php';

$username = 'nitzan';
$password = 'Test';
if ($username == '' || $password == '')
{
    $msg = "You must enter all fields";
}
else {
    $sql = "SELECT password ,id FROM users WHERE username = '$username' AND password = BINARY '$password'";
    $query = $conn->query($sql);
    $row = $query->fetch_assoc();
    if ($query === false) {
        $msg = "Could not successfully run query ($sql) from DB.";
        exit;
    }
    if (mysqli_num_rows($query) > 0) {
        $jsonAns = ['signedout' => 'true'];
        $id = $row['id'];
        $query1 = "UPDATE users SET LOGGEDIN = '0' WHERE users . id = $id";
        $query = $conn->query($query1);
        header('content-type: application/json');
        echo json_encode($jsonAns);
    }
    else
    {
        $jsonAns = ['signedout'=> 'false'];
        header('content-type: application/json');
        echo json_encode($jsonAns);
    }
}
$conn->close();
?>