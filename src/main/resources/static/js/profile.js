window.onload = function (){
    attachReferenceOnMenuButton();
    let menu_buttons = document.getElementsByClassName("menu_button");
    for (let button_index in menu_buttons){
        if (menu_buttons[button_index].addEventListener==null) continue;
        menu_buttons[button_index].addEventListener("mouseover",overMenuButton);
        menu_buttons[button_index].addEventListener("mouseout",outMenuButton);
    }
    document.getElementById("user_information_container_button_1").addEventListener("mouseover",mouseIsOverButton);
    document.getElementById("user_information_container_button_1").addEventListener("mouseout",mouseIsOutButton);
    document.getElementById("user_information_container_button_1").addEventListener("mouseout",mouseIsOutButton);
    document.getElementById("show_all_images_button").addEventListener("click",openModuleWindow);
    document.getElementById("show_all_images_button").addEventListener("click",openGalleryModelApplication);

    let images = document.getElementsByTagName("img");
    for (let image_index in images){
        if (images[image_index].addEventListener==null || (images[image_index].getAttribute("id")!=="avatar" && images[image_index].className!=="user_image" && images[image_index].className!="gallery_image")) continue;
        images[image_index].addEventListener("click",openModuleWindow);
        images[image_index].addEventListener("click",openUserPictureModuleApplication);
    }

    let close_buttons = document.getElementsByClassName("close");
    for (let button_index in close_buttons){
        if (close_buttons[button_index].addEventListener!=null) close_buttons[button_index].addEventListener("click",closeModuleWindow);
    }

    let request = new XMLHttpRequest();
    request.open("GET","/getUser/"+getUserIdFromURL());

    request.onreadystatechange = async function (){

        if (request.readyState==4){
            let user = JSON.parse(request.responseText);
            let avatar = document.getElementById("avatar");
            if (user["avatar"]) avatar.src = user["avatar"]["path"];
            else avatar.src = "/pictures/user_pictures/0.jpg";
            let name = document.getElementsByClassName("name")[0];
            name.textContent = user["name"]+" "+user["lastname"];
            let age = document.getElementById("age");
            age.textContent = "Возраст: "+(user["age"]?user["age"]:"Не указано");
            let country = document.getElementById("country");
            country.textContent = "Страна: "+(user["country"]?user["country"]:"Не указано");
            let status = document.getElementById("status");
            if (user["status"]) status.textContent = "Статус: "+user["status"];
            let images = user["images"];

            if (user["images"]!=undefined) {
                for (let i = 1; i <= 6; i++) {
                    if (images.length - i < 0) break;
                    let image = images[images.length - i];

                    switch (i) {
                        case 1:
                            document.getElementById("first_image").src = image["path"];
                            break;
                        case 2:
                            document.getElementById("second_image").src = image["path"];
                            break;
                        case 3:
                            document.getElementById("third_image").src = image["path"];
                            break;
                        case 4:
                            document.getElementById("forth_image").src = image["path"];
                            break;
                        case 5:
                            document.getElementById("fifth_image").src = image["path"];
                            break;
                        case 6:
                            document.getElementById("sixth_image").src = image["path"];
                            break;
                    }
                }
            }


            if (request.getResponseHeader("owner")==="true"){
                let change_avatar_button = document.getElementById("user_information_container_button_1");
                change_avatar_button.textContent = "Change avatar";
                document.getElementById("user_information_container_button_1").addEventListener("click",openModuleWindow);
                change_avatar_button.addEventListener('click',openAddPictureModelApplication)
            }else{
                document.getElementById("create_new_button").style["display"] = "none";
                if (request.getResponseHeader("is_friends")!=="true") {
                    let add_friend_button = document.getElementById("user_information_container_button_1");
                    add_friend_button.textContent = "Add friend";
                    add_friend_button.onclick = function () {
                        let add_friend_request = new XMLHttpRequest();
                        add_friend_request.open("POST","/addFriend/"+getUserIdFromURL());

                        add_friend_request.onreadystatechange = function (){
                            document.location.reload();
                        }
                        add_friend_request.send();
                    }
                }else{
                    let write_message_button = document.getElementById("user_information_container_button_1");
                    write_message_button.textContent = "Write message";
                    write_message_button.onclick = function (){
                        window.location = "/chat/"+request.getResponseHeader("chat_id");
                    }
                }
            }

            let create_new_button = document.getElementById("create_new_button");
            create_new_button.addEventListener("click",openModuleWindow);
            create_new_button.addEventListener("click",openAddNewsModelApplication);
            create_new_button.addEventListener("mouseover",overMenuButton);
            create_new_button.addEventListener("mouseout",outMenuButton);


            let news = user["news"];
            for (let news_index in news){
                showNews(news[news_index]);
            }
        }
    }

    request.send()

    showFriendsList(getUserIdFromURL());
}


function mouseIsOverButton(event){
    event.currentTarget.style["border"] = "1px solid #B8B6B6";
}

function mouseIsOutButton(event){
    event.currentTarget.style["border"] = "1px solid #D1CFCF";
}

function closeModuleWindow(){
    closeAllModuleApplication()
    document.getElementById("modal").style["display"] = "none";
}

function openModuleWindow(){
    closeModuleWindow();
    document.getElementById("modal").style["display"] = "block";
}


function closeAllModuleApplication(){
    document.getElementById("modal").children[1].style["display"]="none";
    document.getElementById("modal").children[2].style["display"]="none";
    document.getElementById("modal").children[3].style["display"]="none";
    document.getElementById("modal").children[4].style["display"]="none";
}

function openGalleryModelApplication(){
    loadUserImages(getUserIdFromURL());
    document.getElementById("all_pictures_container").style["display"]="block";
}

function openUserPictureModuleApplication(event){
    document.getElementById("picture_container").style["display"]="block";
    document.getElementById("single_user_picture").src = event.currentTarget["src"];
}

function openAddPictureModelApplication(){
    document.getElementById("add_photo_window").style["display"] = "block";
}

function openAddNewsModelApplication(){
    document.getElementById("add_news_window").style["display"] = "block";
}

function overMenuButton(event){

}

function showFriendsList(user_id){

    let request = new XMLHttpRequest();
    let friends;
    request.open("GET","/getFriends/"+user_id);

    request.onreadystatechange = function (){
        if (request.readyState == 4){
            friends = JSON.parse(request.responseText);
            for (let friend_index in friends){
                let friends_list = document.getElementById("friends_container");

                let friend_container = document.createElement("div");
                friend_container.addEventListener("mouseover",overMenuButton);
                friend_container.addEventListener("mouseout",outMenuButton);
                friend_container.className = "user";
                friend_container.onclick = function (){window.location = "/profile/"+friends[friend_index]["id"]}

                let avatar = document.createElement("img");
                avatar.className = "user_avatar";
                if (friends[friend_index]["avatar"]) avatar.src = friends[friend_index]["avatar"]["path"];
                else avatar.src = "/pictures/user_pictures/0.jpg";

                let name = document.createElement("p");
                name.textContent = friends[friend_index]["name"];
                name.className = "user_name";

                friend_container.appendChild(avatar);
                friend_container.appendChild(name);
                friends_list.appendChild(friend_container);
            }
        }
    }

    request.send();
}

function add_friend(){
    let request = new XMLHttpRequest();
    request.open("POST","/addFriend/"+getUserIdFromURL());

    request.onreadystatechange = function(){
        let button = document.getElementById("user_information_container_button_1");
        button.textContent = "Write message";
        button.onclick = function (){window.location = "/chat/"+request.getResponseHeader("chat_id")}
    }

    request.send();
}

function file_uploaded(){
    document.getElementsByClassName("uploaded_file_count")[0].textContent = "1 file";
}

function loadUserImages(user_id){
    let request = new XMLHttpRequest();
    request.open("GET","/getUserImages/"+user_id);

    request.onreadystatechange = function (){
        if (request.readyState==4){
            let images = JSON.parse(request.responseText);
            let images_container = document.getElementById("images_container");
            images_container.innerHTML = "";

            for (let image_index in images){
                let image = document.createElement("img");
                image.className = "user_image";
                image.src = images[image_index]["path"];
                images_container.appendChild(image);
            }
        }
    }
    request.send();
}