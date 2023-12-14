window.onload=function (){
    attachReferenceOnMenuButton();
    let menu_buttons = document.getElementsByClassName("menu_button");
    for (let button_index in menu_buttons){
        if (menu_buttons[button_index].addEventListener==null) continue;
        menu_buttons[button_index].addEventListener("mouseover",overMenuButton);
        menu_buttons[button_index].addEventListener("mouseout",outMenuButton);
    }
    document.getElementsByClassName("searching_field")[0].addEventListener("keyup",showSortedFriendsList);
    let userId = getUserIdFromURL()
    let request = new XMLHttpRequest();
    request.onreadystatechange = function (){
        if (request.readyState==4) {
            let user = JSON.parse(request.responseText);
            showUser(user);
            showFriends(user["id"]);
        }
    }
    request.open("GET",'/getUser/'.concat(userId));
    request.send();
}


function showUser(user){
    let content = document.getElementsByClassName("main_content")[0];
    let user_container = document.getElementById("main_user_container");
    let user_avatar = document.createElement("img");
    user_avatar.setAttribute("class","avatars");
    user_avatar.setAttribute("src",user["avatar"]["path"]);
    let user_name = document.createElement("p");
    user_name.setAttribute("class","name");
    user_name.textContent = user["name"].concat(" ",user["lastname"]);
    user_container.appendChild(user_avatar);
    user_container.appendChild(user_name);

    let friendsContainer = document.getElementsByClassName("friends_container")[0];
    content.insertBefore(user_container,friendsContainer);
}

function showFriends(user_id){
    let request = new XMLHttpRequest();
    request.onreadystatechange=function () {
        if (request.readyState===4) {
            let friends = JSON.parse(request.responseText);
            let friends_container = document.getElementsByClassName("friends_container")[0];
            for (let i = 0; i < friends.length; i++) {
                let friend = friends[i];
                let user_container = document.createElement("div");
                user_container.setAttribute("class", "user_container");
                user_container.addEventListener("click",function (){window.location = "/profile/"+friend["id"];})
                let avatar = document.createElement("img");
                avatar.setAttribute("class", "avatars");
                if (friend["avatar"]) avatar.src = friend["avatar"]["path"];
                else avatar.src = "/pictures/user_pictures/0.jpg";

                let name = document.createElement("p");
                name.setAttribute("class","name");
                name.textContent = friend["name"] + " " + friend["lastname"];
                user_container.appendChild(avatar);
                user_container.appendChild(name);
                friends_container.appendChild(user_container);
            }
        }
    }
    request.open("GET","/getFriends/".concat(user_id))
    request.send()

}

function getUserIdFromURL(){
    let url = new URL(window.location);
    let pathname = url.pathname.split("/");
    return pathname[pathname.length-1];
}

function showSortedFriendsList(event){
    let request = new XMLHttpRequest();

    console.log(event.keyCode);
    console.log(event.keyCode);
    console.log(event.keyCode);
    console.log(event.keyCode);
    console.log(event.keyCode);
    console.log(event.keyCode);
    if (!(event.keyCode>=48 && event.keyCode<=57
        || event.keyCode>=65 && event.keyCode<=90
        || event.keyCode>=97 && event.keyCode<=122
        || event.keyCode==8 && document.getElementsByClassName("searching_field")[0]["value"]!=""))
        return;

    request.onreadystatechange = function (){

        if (request.readyState===4) {
            let main_container = document.getElementsByClassName("main_content")[0];
            let user_searching_window = document.getElementsByClassName("user_searching_window")[0]
            let result_searching_window = document.getElementsByClassName("result_searching_window")[0];
            let main_user_container = document.getElementById("main_user_container");
            if (result_searching_window!=null) main_container.removeChild(result_searching_window);
            if (request.responseText==="") return;
            let sorted_friends = JSON.parse(request.responseText);
            result_searching_window = document.createElement("div");
            result_searching_window.setAttribute("class","result_searching_window");

            for (let i in sorted_friends){
                let friend = sorted_friends[i];

                let avatar = document.createElement("img");
                avatar.className = "searching_result_avatar";

                if (friend["avatar"]) avatar.src = friend["avatar"]["path"];
                else avatar.src = "/pictures/user_pictures/0.jpg";

                let name = document.createElement("p");
                name.className = "searching_result_name";
                name.textContent = friend["name"]+" "+friend["lastname"];

                let user_result_container = document.createElement("div");
                user_result_container.className = "user_result_container";
                user_result_container.addEventListener("click",function (){window.location = "/profile/"+friend["id"];})
                user_result_container.appendChild(avatar);
                user_result_container.appendChild(name);
                result_searching_window.appendChild(user_result_container);
            }

            main_container.insertBefore(result_searching_window,main_user_container);
        }
    }
    let name = document.getElementsByClassName("searching_field")[0]["value"];
    request.open("GET","/getSortedFriendsList?id="+getUserIdFromURL()+"&name="+name);
    request.send();
}

function onFocus(event){
    this.style["backgroundColor"] = "#D9D4D4";
}

