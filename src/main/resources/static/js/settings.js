window.onload = function (){
    attachReferenceOnMenuButton();
    let menu_buttons = document.getElementsByClassName("menu_button");
    for (let button_index in menu_buttons){
        if (menu_buttons[button_index].addEventListener==null) continue;
        menu_buttons[button_index].addEventListener("mouseover",overMenuButton);
        menu_buttons[button_index].addEventListener("mouseout",outMenuButton);
    }

    let request_for_user_settings = new XMLHttpRequest();
    request_for_user_settings.open("GET","/getUserSettings");
    request_for_user_settings.onreadystatechange = function (){
        if (request_for_user_settings.readyState==4) {
            let user = JSON.parse(request_for_user_settings.responseText);
            document.getElementById("name_field").setAttribute("placeholder",user["name"]);
            document.getElementById("lastname_field").setAttribute("placeholder",user["lastname"]);
            document.getElementById("age_field").setAttribute("placeholder",user["age"]?user["age"]:"Не указано");
            document.getElementById("country_field").setAttribute("placeholder",user["country"]?user["country"]:"Не указано");
            document.forms[0].onsubmit = save_settings;
        }
    }
    request_for_user_settings.send();
}

function save_settings(e){
    let request_for_changing_settings = new XMLHttpRequest();
    let name = document.getElementById("name_field");
    let lastname = document.getElementById("lastname_field");
    let age = document.getElementById("age_field");
    let country = document.getElementById("country_field");

    request_for_changing_settings.open("POST","/saveSettings"
        +"/"+(name.value?name.value:name.getAttribute("placeholder"))
        +"/"+(lastname.value?lastname.value:lastname.getAttribute("placeholder"))
        +"/"+(age.value?age.value:age.getAttribute("placeholder"))
        +"/"+(country.value?country.value:country.getAttribute("placeholder")));

    if (request_for_changing_settings.readyState==4){
        let user = JSON.parse(request_for_changing_settings.responseText);
        name.value = "";
        name.placeholder = user["name"]
        lastname.value = "";
        lastname.placeholder = user["lastname"];
        age.value = "";
        age.placeholder = user["age"];
        country.value = "";
        country.placeholder = user["country"];
    }
    request_for_changing_settings.send();
    e.preventDefault();
}