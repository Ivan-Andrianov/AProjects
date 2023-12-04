window.addEventListener('load',ready)

function ready(){
    let fieldElements = document.getElementsByClassName("input");
    for (let i in fieldElements){
        let element = fieldElements[i];
        if (element instanceof HTMLElement) {
            element.addEventListener('focus', onFocus);
            element.addEventListener('blur', onBlur);
        }
    }

    let submitButton = document.getElementById("submitButton");
    submitButton.addEventListener('mouseover',overSubmitButton);
    submitButton.addEventListener('mouseout',outSubmitButton);

    try{
        let form = document.getElementById("loginForm");
        form.addEventListener('submit',checkLoginForm);
        form.addEventListener('submit',sendAuthenticationRequest);

    }catch (error){
        document.getElementById("registrationForm")["onsubmit"] = checkRegistrationForm;
    }



}

function isEmpty(element){
    return element["value"].trim()===""?true:false;
}

function passwordsIsNotEqual(){
    if (document.getElementById("passwordField")["value"].trim()!=document.getElementById("repeatPasswordField")["value"].trim()){
        let password = document.getElementById("passwordField");
        let repeatPassword = document.getElementById("repeatPasswordField");
        document.forms[0].getElementsByClassName("hint")[3]["title"]= "Пароли не совпадают";
        document.forms[0].getElementsByClassName("hint")[4]["title"] = "Пароли не совпадают";
        errorOccurrence(password);
        errorOccurrence(repeatPassword);
        return false;
    }

    return true;
}

function passwordIsCorrect(){
    let password = document.getElementById("passwordField");
    if (/.*[A-Za-z].*/.test(password["value"]) && /.*[0-9].*/.test(password["value"]) && password["value"].indexOf("_")>=0 && password["value"].length>=8) return true;
    else {
        document.forms[0].getElementsByClassName("hint")[3]["title"] = "Пароль должен содержать латинские символы, цифры и знак подчеркивания.";
        errorOccurrence(password);
        return false;
    }
}

function loginIsCorrect(){
    let login = document.getElementById("loginField");
    if ((/.+@.*mail\..+/).test(login["value"])) return true;
    else {
        document.forms[0].getElementsByClassName("hint")[2]["title"]="Некорректная почта."
        errorOccurrence(login);
        return false;
    }

}

