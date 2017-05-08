/**
 * Created by Yochay on 05-Apr-17.
 */
var attempt = 3; // Variable to count number of attempts.
// Below function Executes on click of login button.
function validate(){
    var username = document.getElementById("username").value;
    var password = document.getElementById("password").value;
    var httpreq = new XMLHttpRequest();
    var response;
    var json_response;
    var bool;
    httpreq.open('POST','52.59.229.170/login/login.php', true);
    httpreq.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    var sentData = "username=" +username + "&password=" + password;
    httpreq.onload = function () {
        response = httpreq.responseText;
        json_response = JSON.parse(response);
        bool = json_response['signedin'];
        if(bool == 'true'){
            alert("signed in successfully");
        }
        else{
            attempt --;// Decrementing by one.
            alert("You have "+attempt+" attempts left;");
// Disabling fields after 3 attempts.
            if( attempt == 0){
                document.getElementById("username").disabled = true;
                document.getElementById("password").disabled = true;
                document.getElementById("submit").disabled = true;
                return false;
            }
        }
    };
    httpreq.send(sentData);
}
function signup(){

}
