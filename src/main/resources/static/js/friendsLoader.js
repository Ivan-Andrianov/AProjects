window.onload=function (){
    let userId = getUserIdFromURL()
    let request = new XMLHttpRequest();
    request.onreadystatechange = function (){
        if (request.readyState===3) {
            console.log(request.responseText)
            let user = JSON.parse(request.responseText);
            showUser(user," (friends)")
            showFriends(user["id"])
        }
    }
    request.open("GET",'/getUser/'+userId);
    request.send();
}


function showUser(user,addition,before){
    let content = document.getElementById("content");
    let userContainer = document.createElement("a");
    userContainer.id = "userContainer";
    let avatar = document.createElement("img");
    avatar.setAttribute("class","avatar");
    avatar.src = user["avatar"];
    let name = document.createElement("p");
    name.textContent = user["name"]+" "+user["lastname"]+addition;
    userContainer.appendChild(avatar);
    userContainer.appendChild(name);
    if (before) content.insertBefore(userContainer,before);
    else content.appendChild(userContainer);
}

function showFriends(user_id){
    let request = new XMLHttpRequest();
    request.onreadystatechange=function () {
        if (request.readyState===3) {
            let friends = JSON.parse(request.responseText)
            console.log(friends)
            for (let i = 0; i < friends.length; i++) {
                let friend = friends[i];
                let content = document.getElementById("content");
                let friendContainer = document.createElement("a");
                friendContainer.setAttribute("class", "friendContainer");
                let avatar = document.createElement("img");
                avatar.setAttribute("class", "avatar");
                avatar.src = friend["avatar"];
                let name = document.createElement("p");
                name.textContent = friend["name"] + " " + friend["lastname"];
                let messageImg = document.createElement("img");
                messageImg.setAttribute("class", "messageImg");
                messageImg.src = "/pictures/message.png";
                friendContainer.appendChild(avatar);
                friendContainer.appendChild(name);
                friendContainer.appendChild(messageImg);
                friendContainer.href = "/profile/" + friend["id"];
                content.appendChild(friendContainer);
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

