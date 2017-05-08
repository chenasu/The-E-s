<?php

$access_token = 'EAAaDPkWqihIBAIvy01ZAZCcwr8uRY1hQeOHOcFsU7LABZBZCocdppFe1M8YjZCI0547Rzq56EbiTKaVG3an573nHZASb6zvT0GHT42qaSgPZAOvCDdbvMN8bMGccgCDsrMZCNNaSVwervazdEfHR8hNZByR5h6sZBKyD4pI4lH3ouFeYsUZBBRCmj9uwe25Ti4WoBiTVQviugPNUgZDZD';
$url = 'https://graph.facebook.com/v2.9/me/feed';

//$message = 'this+is+testing+whatup';

$postdata = http_build_query(
    array(
        'message' => "nizan king again"
    )
);

$opts = array('http' =>
    array(
        'method'  => 'POST',
        'header'  => 'Content-type: application/x-www-form-urlencoded',
        'content' => $postdata
    )
);

$context  = stream_context_create($opts);

$result = file_get_contents('https://graph.facebook.com/v2.9/me/feed?access_token='.$access_token, false, $context);

if($result === FALSE){
    die('Error');
}

$responseData = json_decode($result,true);

echo "Published";

?>