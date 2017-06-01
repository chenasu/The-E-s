<?php

include 'weather.php';
include 'translate.php';
include 'SavePass/savepass.php';

if (isset($_GET['weather']))
{
    $var = (new weather()) -> get();
}

else if (isset($_GET['translate']))
{
    $var = (new translate()) -> get();
}

else if (isset($_POST['SavePass']))
{
    $var = (new savepass()) -> init();
}

else if (isset($_GET['check']))
{
    $var = (new savepass());
    $var ->createConnection();
    $var ->fetchInfo();
}

else
{
    echo "for weather send a GET request as following:";
    echo nl2br("/index.php?weather&city=Ashqelon&region=IL\n");
    echo nl2br("city = the city you want.\n");
    echo nl2br("region = country.\n\n\n");
    
    
    echo nl2br("for translate send a GET request as following\n");
    echo nl2br("server/index.php?translate&targetLang=en&text=what+is\n");
    echo nl2br("targetLang = language you want, en = english, iw = hebrew\n");
    echo nl2br("text = your text, it has to have + instead of space, for example, 'my name is' should be send like this - 'my+name+is'\n");
	echo nl2br("\n\n\n");
	echo nl2br("Weather JSON example:\n");
    $example = [
	"Sky State" => "string",
	"Temp" => "int",
	"Humidity" => "int",
	"City" => "string"
];
echo(json_encode($example));
}

//foreach ($set as $key => $value) {
//
//}


?>
