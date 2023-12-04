function checkLoginForm(event){
    let fields = document.getElementsByClassName("input");
    let hints = document.getElementsByClassName("hint");
    let result = true;
    for (let i=0;i<fields.length;i++){
        if (isEmpty(fields[i])){
            result = false;
            errorOccurrence(fields[i]);
            hints[i]["title"] = "Заполните поле";
        }
    }

    if (!result) event.preventDefault();

    return result;
}

function sendAuthenticationRequest(event){
    let request = new XMLHttpRequest();
    let username = document.getElementById("loginField")["value"];
    let password = document.getElementById("passwordField")["value"];

    request.onreadystatechange = function (){
        if (request.readyState===3 && request.getResponseHeader("error").includes("true")){
            let fields = document.getElementsByClassName("input");
            for (let i=0;i<fields.length;i++){
                errorOccurrence(fields[i]);
                let hints = document.getElementsByClassName("hint");
                hints[i]["title"] = "Некорректное имя пользователя или пароль.";
            }
        }
    }

    request.setRequestHeader("username",username);
    request.setRequestHeader("password",password);
    request.open("POST","/login");
    request.send();
    event.preventDefault();
}