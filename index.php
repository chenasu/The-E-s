<?php
if (isset($_GET['weather'])) {
    $apikey = "435ff3124fef6641c2eca016ff52fbc5";
    $city = $_GET['city'];
    $region = $_GET['region'];
    //$url = 'http://api.openweathermap.org/data/2.5/weather?q=Ashqelon,IL&appid=435ff3124fef6641c2eca016ff52fbc5';
    $jsonData = file_get_contents("http://api.openweathermap.org/data/2.5/weather?q=".$city.",".$region."&appid=".$apikey);

    $json = json_decode($jsonData, true);

//var_dump($json['weather'][0]['id'])
//    $output = [
//    "Sky State" => $json['weather']['0']['main'],
//        "Temp" => $json['main']['temp'],
//        "Humidity" => $json['main']['humidity'],
//        "City" => $json['name']
//       ];
//
//    echo json_encode($output);
    $output = [
        $json['weather']['0']['main'],
        $json['main']['temp'],
        $json['main']['humidity'],
        $json['name']
    ];

    echo json_encode($output);

}


//http://api.openweathermap.org/data/2.5/weather?q=Ashqelon,IL&appid=435ff3124fef6641c2eca016ff52fbc5
?>



