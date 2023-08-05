window.onload=function (){
    let userId = getUserIdFromURL()
    let request = new XMLHttpRequest();
    request.onreadystatechange = function (){
        if (request.readyState===3) {
            let user = JSON.parse(request.responseText);
            showUser(user)
            showFriends(user["friends"])
        }
    }
    request.open("GET",'/getUser/'+userId);
    request.send();
}


function showUser(user){
    let content = document.getElementById("content");
    let userContainer = document.createElement("a");
    userContainer.id = "userContainer";
    let avatar = document.createElement("img");
    avatar.setAttribute("class","avatar");
    avatar.src = user["avatar"];
    let name = document.createElement("p");
    name.textContent = user["name"]+" "+user["lastname"]+" (friends "+user["friends"].length+")";
    userContainer.appendChild(avatar);
    userContainer.appendChild(name);
    content.appendChild(userContainer);
}

function showFriends(friends){

    for (let i = 0;i<friends.length;i++){
        let friend = friends[i];
        let content = document.getElementById("content");
        let friendContainer = document.createElement("a");
        friendContainer.setAttribute("class","friendContainer");
        let avatar = document.createElement("img");
        avatar.setAttribute("class","avatar");
        avatar.src = friend["avatar"];
        let name = document.createElement("p");
        name.textContent = friend["name"]+" "+friend["lastname"];
        let messageImg = document.createElement("img");
        messageImg.setAttribute("class","messageImg");
        messageImg.src = "/pictures/message.png";
        friendContainer.appendChild(avatar);
        friendContainer.appendChild(name);
        friendContainer.appendChild(messageImg);
        friendContainer.href="/profile/"+friend["id"];
        content.appendChild(friendContainer);
    }

}

function getUserIdFromURL(){
    let url = new URL(window.location);
    let pathname = url.pathname.split("/");
    return pathname[pathname.length-1];
}

