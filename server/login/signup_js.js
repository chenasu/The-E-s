/**
 * Created by Yochay on 06-Apr-17.
 */

//signup function html calls to it and checks if responded or not
function signup(){
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var email = document.getElementById("email").value;
    var httpreq = new XMLHttpRequest();
    var response;
    var json_response;
    var bool;
    alert("omri Agever");

    httpreq.open('POST','52.59.229.170/login/signup.php', true);
    httpreq.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    var sentData = "username=" +username + "&password=" + password + "&email=" +email;
    httpreq.onload = function () {
        response = httpreq.responseText;
        json_response = JSON.parse(response);
        alert(JSON.stringify(json_response));
        bool = json_response['signedup'];
        if(bool == 'true'){
            alert("signed up successfully");
        }
        else{
            alert("error");
        }
    };
    httpreq.send(sentData);
}