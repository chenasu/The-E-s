<?php

class translate
{
    private $apikey;
    private $targetLang;
    private $text;


    public function __construct()
    {
        $this->apikey = "AIzaSyDkafT37zmeAXpXGdqtMUPPASzcGyj58_8";
        $this->targetLang = $_GET['targetLang'];
        $this->text = $_GET['text'];
        $this->text = preg_replace('/\s+/', '+', $this->text);
    }

    public function get()
    {
        $jsonData = file_get_contents("https://www.googleapis.com/language/translate/v2?q=".$this->text."&target=".$this->targetLang."&key=".$this->apikey);

        $json = json_decode($jsonData,true);

        $output = [
            "Translated" => $json['data']['translations'][0]['translatedText']
        ];

        echo json_encode($output);
    }
}


?>
