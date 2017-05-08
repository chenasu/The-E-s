<?php

class weather
{
    private $apikey;
    private $city;
    private $region;

    /**
     * WidgetFactory constructor.
     * @param string $string
     */
    public function __construct()
    {
        $this->apikey = "435ff3124fef6641c2eca016ff52fbc5";
        $this->city = $_GET['city'];
        $this->region = $_GET['region'];
    }
    public function get()
    {

        $jsonData = file_get_contents("http://api.openweathermap.org/data/2.5/weather?q=".$this->city.",".$this->region."&appid=".$this->apikey."&units=metric");

        $json = json_decode($jsonData, true);

        $output = [
            "Sky State" => $json['weather']['0']['main'],
            "Temp" => $json['main']['temp'],
            "Humidity" => $json['main']['humidity'],
            "City" => $json['name']
        ];
        echo json_encode($output);
    }
}

?>