
let chat;
let user;

window.onload = function (){
    let request = new XMLHttpRequest();
    request.onreadystatechange = function (){
        if (request.readyState === 3){
            chat = JSON.parse(request.responseText);
            user = (getUserIdFromURL()==chat["user1"]["id"])?chat["user2"]:chat["user1"];
            if (chat["user1"]["id"]==getUserIdFromURL()) showUser(chat["user1"],"",document.getElementById("messageContainer"));
            else showUser(chat["user1"], "",document.getElementById("messageContainer"));
            for (let message in chat["listOfMessages"]){
                let messageBlock = document.createElement("div");
                messageBlock.className="message";
                let avatar = document.createElement("img");
                avatar.src = "/pictures/avatars/avatar".concat(chat["listOfMessages"][message]["sender"]["id"],".jpg");
                let text = document.createElement("div");
                if (chat["listOfMessages"][message]["sender"]["id"]==getUserIdFromURL()) {
                    avatar.className = "avatarOfMessageFriend";
                    text.className = "messageFriend";
                }
                else {
                    avatar.className = "avatarOfMessageUser";
                    text.className = "messageUser";
                }
                text.textContent = chat["listOfMessages"][message]["text"];
                messageBlock.appendChild(avatar);
                messageBlock.appendChild(text);
                let messageContainer = document.getElementById("messageContainer");
                messageContainer.appendChild(messageBlock);
                messageContainer.scrollTop = Math.ceil(messageContainer.scrollHeight - messageContainer.clientHeight);
            }


        }
    }
    request.open("GET","/getChatInformation/"+getUserIdFromURL());
    request.send()

    let writeForm = document.getElementById("messageWritingPanel");
    writeForm.onsubmit = sendMessage;

}


function getUserById(id){
    let request = new XMLHttpRequest();
    request.onreadystatechange = function (){
        if (request.readyState === 3){
            console.log(request.responseText)
            return JSON.parse(request.responseText);
        }
    }
    request.open("GET","/getUser/"+getUserIdFromURL());
    request.send()
}

function sendMessage(){
    let messageInput = document.getElementById("messageInput")
    let text = messageInput.value;

    console.log(text)
    if (text){
        let request = new XMLHttpRequest();
        request.onreadystatechange = function (){
            console.log(request.readyState)
            if (request.readyState === 4){
                writeMessage(text)
            }
        }
        request.open("POST","/sendMessage?chat_id="+chat["id"]+"&text="+text+
        "&sender_id="+user["id"]);
        request.send();
    }

    messageInput.value = "";

    return false;
}


function writeMessage(message){
    let messageBlock = document.createElement("div");
    console.log("1")
    messageBlock.className = "message";
    let avatar = document.createElement("img");
    avatar.src = "/pictures/avatars/avatar".concat(user["id"],".jpg");
    console.log(avatar.src)
    let text = document.createElement("div");
    avatar.className = "avatarOfMessageUser";
    text.className = "messageUser";
    text.textContent = message;
    messageBlock.appendChild(avatar);
    messageBlock.appendChild(text);
    let messageContainer = document.getElementById("messageContainer");
    messageContainer.appendChild(messageBlock);
    messageContainer.scrollTop = Math.ceil(messageContainer.scrollHeight - messageContainer.clientHeight);
}
