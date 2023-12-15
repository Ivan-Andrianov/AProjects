let user_id;
let chat_window;
let stompClient;
let chat;


window.onload = function () {
    attachReferenceOnMenuButton();
    document.getElementsByClassName("message_input")[0].addEventListener("keyup",send_message);

    let menu_buttons = document.getElementsByClassName("menu_button");
    for (let button_index in menu_buttons){
        if (menu_buttons[button_index].addEventListener==null) continue;
        menu_buttons[button_index].addEventListener("mouseover",overMenuButton);
        menu_buttons[button_index].addEventListener("mouseout",outMenuButton);
    }

    let request_for_chat_information = new XMLHttpRequest();
    request_for_chat_information.open("GET", "/getChat/" + getChatIdFromURL());
    request_for_chat_information.onreadystatechange = async function () {
        if (request_for_chat_information.readyState === 4) {
            console.log(request_for_chat_information.responseText)
            chat = JSON.parse(request_for_chat_information.responseText);
            chat_window = document.getElementsByClassName("chat_window")[0];
            user_id = parseInt(request_for_chat_information.getResponseHeader("user_id"));

            for (let message in chat["listOfMessages"]) {
                let avatar = document.createElement("img");
                avatar.className = "sender_avatar";
                if (chat["listOfMessages"][message]["sender"]["avatar"]) {
                    avatar.src = chat["listOfMessages"][message]["sender"]["avatar"]["path"];
                }else{
                    avatar.src = "/pictures/0.jpg";
                }
                let text = document.createElement("div");
                text.textContent = chat["listOfMessages"][message]["text"];
                text.className = "message_text";
                let message_container = document.createElement("div");
                if (chat["listOfMessages"][message]["sender"]["id"] == user_id) {
                    message_container.className = "left_message";
                } else message_container.className = "right_message";

                message_container.appendChild(avatar);
                message_container.appendChild(text);
                chat_window.appendChild(message_container);
            }

            let socket = new SockJS("/ws");
            stompClient = Stomp.over(socket);
            stompClient.connect({},onConnected);

            let send_button = document.getElementsByClassName("send_button")[0];
            send_button.addEventListener("click", send_message);

            chat_window.scrollTop = Math.ceil(chat_window.scrollHeight - chat_window.clientHeight);
        }
    }

    request_for_chat_information.send();


}

function send_message(event){
    if (event.keyCode!=13 && event.keyCode!=null) return;
    let message_text = document.getElementsByClassName("message_input")[0]["value"];
    if (message_text.trim()=="") return;

    let message = {
        "chat_id":chat["id"],
        "text":message_text,
        "sender_id":user_id
    };

    stompClient.send("/app/messages/"+chat["id"],{"Content-Type":"text/JSON","user_id":user_id},JSON.stringify(message));
    document.getElementsByClassName("message_input")[0]["value"] = "";
}

const onConnected = ()=>{
    console.log("connected");
    stompClient.subscribe("/topic/messages/"+chat["id"],receivingMessage);
}

function receivingMessage(message){
    console.log("Сообщение получено")
    console.log(message);
    showMessage(message);
    chat_window.scrollTop = Math.ceil(chat_window.scrollHeight - chat_window.clientHeight);
}

function showMessage(message){
    let message_body = JSON.parse(message["body"]);
    let message_container = document.createElement("div");
    message_body["sender"]["id"]==user_id?message_container.className="left_message":message_container.className="right_message";
    let avatar = document.createElement("img");
    avatar.className = "sender_avatar";
    if (message_body["sender"]["avatar"]) avatar.src = message_body["sender"]["avatar"]["path"];
    else avatar.src = "/pictures/user_pictures/0.jpg";
    let message_text_container = document.createElement("div");
    message_text_container.className = "message_text";
    message_text_container.textContent = message_body["text"];
    message_container.appendChild(avatar);
    message_container.appendChild(message_text_container);
    chat_window.appendChild(message_container);
}
function getChatIdFromURL(){
    let url = window.location.pathname.split("/");
    return url[url.length-1];
}







