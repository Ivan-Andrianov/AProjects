window.onload = function (){
    let menu_buttons = document.getElementsByClassName("menu_button");
    for (let button_index in menu_buttons){
        if (menu_buttons[button_index].addEventListener==null) continue;
        menu_buttons[button_index].addEventListener("mouseover",overMenuButton);
        menu_buttons[button_index].addEventListener("mouseout",outMenuButton);
    }
    attachReferenceOnMenuButton();
    let request_for_user_information = new XMLHttpRequest();

    request_for_user_information.onreadystatechange = async function(){
        if (request_for_user_information.readyState == 4){
            let user = JSON.parse(request_for_user_information.responseText);
            let user_container = document.getElementsByClassName("user_container")[0];
            let user_avatar = document.createElement("img");
            user_avatar.className = "avatars";
            if (user["avatar"]) user_avatar.src = user["avatar"]["path"];
            else user_avatar.src = "/pictures/0.jpg";
            let title = document.createElement("p");
            title.className = "title";
            title.textContent = user["name"]+" "+user["lastname"];


            user_container.appendChild(user_avatar);
            user_container.appendChild(title);
        }
    }

    request_for_user_information.open("GET","/getUser/"+getUserIdFromURL());
    request_for_user_information.send();



    let request_for_chats = new XMLHttpRequest();
    request_for_chats.onreadystatechange = async function (){

        if (request_for_chats.readyState == 4 && request_for_chats.status==200){
            let chats = JSON.parse(request_for_chats.responseText);

            for (let chat_index in chats){
                let chat = chats[chat_index];
                let list_of_messages = chat["listOfMessages"];
                let last_message = list_of_messages[list_of_messages.length-1];
                let receiver = chat["members"][0]["id"]==getUserIdFromURL()?chat["members"][1]:chat["members"][0];
                let message_container = document.createElement("div");
                message_container.className = "message";
                let avatar = document.createElement("img");
                avatar.className = "avatars";

                if (receiver["avatar"]) avatar.src = receiver["avatar"]["path"];
                else avatar.src = "/pictures/0.jpg";
                let message_content = document.createElement("div");
                message_content.className = "message_content";
                let sender_name = document.createElement("p");
                sender_name.className = "sender_name";
                sender_name.textContent = receiver["name"]+" "+receiver["lastname"];
                let message_text = document.createElement("div");
                message_text.className = "message_text";
                let sender_avatar = document.createElement("img");
                sender_avatar.className = "sender_avatar";
                if (last_message["sender"]["avatar"]) sender_avatar.src = last_message["sender"]["avatar"]["path"];
                else sender_avatar.src = "/pictures/0.jpg";
                let text = document.createElement("p");
                text.className = "text";
                text.textContent = last_message["text"].substr(0,20);

                message_text.appendChild(sender_avatar);
                message_text.appendChild(text);
                message_content.appendChild(sender_name);
                message_content.appendChild(message_text);
                message_container.appendChild(avatar);
                message_container.appendChild(message_content);
                message_container.onclick = function (){window.location = "/chat/"+chat["id"];}

                let messages_container = document.getElementsByClassName("messages_container")[0];
                messages_container.appendChild(message_container);
            }
        }
    }

    request_for_chats.open("GET","/getUserChats/"+getUserIdFromURL());
    request_for_chats.send();
}