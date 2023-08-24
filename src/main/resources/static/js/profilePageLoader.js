
function attachAvatar(path){
    document.getElementById("avatar").src=path;
}

function attachFriends(id){
    let request = new XMLHttpRequest()
    request.onreadystatechange = function (){


        if(request.readyState===3){
            console.log(request.responseText)
            let friends = JSON.parse(request.responseText);
            for (let i=0;i<friends.length;i++){
                let friend = document.createElement("a");
                friend.setAttribute("href","/profile/".concat(friends[i]["id"]));
                friend.setAttribute("class","friend");
                let friendsName = document.createElement("p");
                friendsName.textContent =  friends[i]["name"].concat(" ").concat(friends[i]["lastname"]);
                let friendsAvatar = document.createElement("img");
                friendsAvatar.src = friends[i]["avatar"];
                friendsAvatar.setAttribute("class","friendsAvatar")
                friend.appendChild(friendsAvatar);
                friend.appendChild(friendsName);
                document.getElementById("friendsContainer").appendChild(friend);
            }
        }
    }
    request.open("GET","/getFriends/".concat(id))
    request.send()


}

function attachUserInformation(name,lastname,country,status){
    document.getElementById("username").textContent=name.concat(" ",lastname);
    document.getElementById("country").textContent+=" "+country;
    document.getElementById("status").textContent+=" "+status;
}

function attachNavigationPanelButton(request){
    if (request.getResponseHeader("owner")==="true"){
        document.getElementById("addFriendButton").style.display="none"
        document.getElementById("writeMessageButton").style.display="none";
        document.getElementById("editButton").style.display="inline-block";
    }else{
        document.getElementById("addFriendButton").style.display="inline-block"
        document.getElementById("writeMessageButton").style.display="inline-block";
        document.getElementById("editButton").style.display="none";
    }
}

function attachReferences(user_id){
    let references = document.getElementsByClassName("title");
    references[0].setAttribute("href","/friends/".concat(user_id));
}

function requestForUserData(){
    let request = new XMLHttpRequest()
    let url = new URL(document.URL);
    request.onreadystatechange = function (){
        if(request.readyState===3){
            console.log(request.textContent)
            let response = JSON.parse(request.responseText)
            attachAvatar(response["avatar"])
            attachUserInformation(response["name"],response["lastname"],response["country"],response["status"])
            attachFriends(response["id"])
            attachNavigationPanelButton(request)
            attachReferences(response["id"])
        }
    }
    request.open("GET","/getUser/"+parseInt(url.pathname.split("/").pop()));
    request.send()
}




