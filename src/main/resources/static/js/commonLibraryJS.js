function onFocus(){
    this.style["fontSize"] = `${parseInt(/\d+/.exec(window.getComputedStyle(this).fontSize)[0])+2}px`
    this.style["borderBottom"] = "#16A000 2px solid";
}

function onBlur(){
    this.style["borderBottom"] = "black 2px solid";
    this.style["fontSize"] = `${parseInt(/\d+/.exec(window.getComputedStyle(this).fontSize)[0])-2}px`
}

function overSubmitButton(){
    this.style["backgroundColor"] = "#426C33";
}

function outSubmitButton(){
    this.style["backgroundColor"] = "#648359"
}

//изменяет стиль границы поля при неправильном заполнении
function errorOccurrence(field){
    field.style["borderBottom"] = "red 2px solid";
}

function overMenuButton(){
    this.style["backgroundColor"] = "#D9D4D4";
}

function outMenuButton(){
    this.style["backgroundColor"] = "#F2F2F2"
}

function getUserIdFromURL(){
    let url = new URL(window.location);
    let pathname = url.pathname.split("/");
    return pathname[pathname.length-1];
}

function over_interactive_panel_button(event){
    this.style["backgroundColor"]="#FDA2A2";
}

function out_interactive_panel_button(event){
    this.style["backgroundColor"] = "#FFCBCB";
}

function attachReferenceOnMenuButton(){
    let request = new XMLHttpRequest();
    request.open("GET","/getAuthenticatedUserId");
    request.onreadystatechange = function (){
        if (request.readyState==4) {
            let authenticated_user_id = parseInt(request.responseText);
            document.getElementById("profile_button").addEventListener("click", function () {
                window.location = "/"
            });
            document.getElementById("friend_button").addEventListener("click", function () {
                window.location = "/friends/" + authenticated_user_id
            });
            document.getElementById("messages_button").addEventListener("click", function () {
                window.location = "/messages/" + authenticated_user_id
            });
            document.getElementById("news_button").addEventListener("click", function () {
                window.location = "/news/"
            });
            document.getElementById("setting_button").addEventListener("click", function () {
                window.location = "/settings/"
            });
        }
    }
    request.send();
}

function showNews(news){
    let news_container = document.getElementById("news_container");

    let news_element = document.createElement("div");
    news_element.id = news["id"];
    news_element.className = "new";

    let topic = document.createElement("div");
    topic.className = "topic";

    let avatar = document.createElement("img");
    avatar.className = "avatars";
    avatar.src = news["writer"]["avatar"]["path"];
    let topic_name = document.createElement("p");
    topic_name.textContent = news["topic"];
    topic.appendChild(avatar);
    topic.appendChild(topic_name);
    news_element.appendChild(topic);

    let content = document.createElement("div");
    content.className = "content";
    news_element.appendChild(content);

    let news_img = document.createElement("img");
    news_img.className = "news_image";
    news_img.src = news["image"]["path"];
    content.appendChild(news_img);


    let interactive_panel = document.createElement("div");
    interactive_panel.className = "interactive_panel";
    let buttons_container = document.createElement("div");
    buttons_container.className = "buttons_container";
    interactive_panel.appendChild(buttons_container);
    news_element.appendChild(interactive_panel);

    let like_button = document.createElement("button");
    like_button.addEventListener("mouseover",over_interactive_panel_button);
    like_button.addEventListener("mouseout",out_interactive_panel_button);
    like_button.className = "like";
    like_button.onclick = create_like;
    let like_image = document.createElement("img");
    like_image.src = "/pictures/Like.png";
    like_button.appendChild(like_image);
    let like_content = document.createElement("p");
    like_button.appendChild(like_content);
    show_like_count(news["likes"].length,like_button);
    buttons_container.appendChild(like_button);

    let comments_button = document.createElement("button");
    comments_button.addEventListener("mouseover",over_interactive_panel_button);
    comments_button.addEventListener("mouseout",out_interactive_panel_button);
    comments_button.className = "comment";
    comments_button["id"] = "comment_"+news["id"];
    let comment_image = document.createElement("img");
    comment_image.src = "/pictures/Comment.png";
    comments_button.appendChild(comment_image);
    let comment_content = document.createElement("p");
    show_comment_count(news["comments"].length,comment_content);
    comments_button.onclick = open_or_close_comments_container;
    comments_button.appendChild(comment_content);
    buttons_container.appendChild(comments_button);

    let repost_button = document.createElement("button");
    repost_button.addEventListener("mouseover",over_interactive_panel_button);
    repost_button.addEventListener("mouseout",out_interactive_panel_button);
    repost_button.className = "repost";
    let repost_image = document.createElement("img");
    repost_image.src = "/pictures/Repost.png";
    repost_button.appendChild(repost_image);
    let repost_content = document.createElement("p");
    if (news["reposts_count"]>=7) repost_content.textContent = Math.floor(news["reposts_count"]/1000000)+"M";
    else if (news["reposts_count"]>=4) repost_content.textContent = Math.floor(news["reposts_count"]/1000)+"K";
    else repost_content.textContent = news["reposts_count"];
    repost_button.appendChild(repost_content);
    buttons_container.appendChild(repost_button);


    let comments = document.createElement("div");
    comments["id"] = news["id"];
    comments.className = "comments";

    let comments_information = news["comments"];
    for (let comment_index in comments_information){
        let comment = comments_information[comment_index];
        let comment_container = document.createElement("div");
        comment_container.className = "comment_container";
        let avatar = document.createElement("img");
        avatar.className = "avatars";
        avatar.src = comment["sender"]["avatar"]["path"];
        comment_container.appendChild(avatar);
        let text = document.createElement("p");
        text.textContent = comment["message"];
        comment_container.appendChild(text);
        comments.appendChild(comment_container);
    }

    let write_comment_panel = document.createElement("div");
    write_comment_panel.className = "write_comment_panel";
    let comment_field = document.createElement("input");
    comment_field.className = "comment_field";
    comment_field.placeholder = "Write comment...";
    let send = document.createElement("input");
    send.className = "send";
    send.onclick = create_comment;
    send.type = "image";
    send.src = "/pictures/Plane.png";
    let emotion = document.createElement("input");
    emotion.className = "emotion";
    emotion.type = "image";
    emotion.src = "/pictures/Emotion.png";

    write_comment_panel.appendChild(comment_field);
    write_comment_panel.appendChild(send);
    write_comment_panel.appendChild(emotion);

    comments.appendChild(write_comment_panel);
    news_element.appendChild(comments);
    news_container.appendChild(news_element);

}

function open_or_close_comments_container(){
    let comment_container = this.parentNode.parentNode.parentNode.childNodes[3];

    if (comment_container.style["display"]=="none") comment_container.style["display"]="block";
    else comment_container.style["display"]="none";
}

function create_comment(){
    let comment_text = this.parentNode.childNodes[0].value;
    let news_id = this.parentNode.parentNode.parentNode.id;
    let write_comment_panel = this.parentNode;

    if (comment_text.trim()=="") return;

    let request = new XMLHttpRequest();
    request.open("POST","/createComment/"+news_id+"/"+comment_text);

    request.onreadystatechange = function (){
        if (request.readyState===4){
            let writer = JSON.parse(request.responseText);
            let comment_container = document.createElement("div");
            comment_container.className = "comment_container";
            let avatar = document.createElement("img");
            avatar.className = "avatars";
            avatar.src = writer["avatar"]["path"];
            comment_container.appendChild(avatar);
            let text = document.createElement("p");
            text.textContent = comment_text;
            comment_container.appendChild(text);
            write_comment_panel.parentNode.insertBefore(comment_container,write_comment_panel);
            write_comment_panel.childNodes[0].value = "";
            let comments_button_content = document.getElementById(news_id).childNodes[2].childNodes[0].childNodes[1].childNodes[1];
            show_comment_count(parseInt(comments_button_content.textContent)+1,comments_button_content);
        }
    }
    request.send();
}


function show_comment_count(count,comment_content){
    if ((count+"").length>=7) comment_content.textContent = Math.floor(count/1000000)+"M";
    else if ((count+"").length>=4) comment_content.textContent = Math.floor(count/1000)+"K";
    else comment_content.textContent = count;
}

function create_like(){
    let request = new XMLHttpRequest();
    let like_button = this;
    request.open("POST","/createLike/"+this.parentNode.parentNode.parentNode.id);

    request.onreadystatechange = function (){
        if (request.readyState === 4){
            if (request.responseText.length==4){
                show_like_count(parseInt(like_button.textContent)+1,like_button)
            }else{
                return;
            }
        }
    }

    request.send();
}

function show_like_count(count,like_button){
    if ((count+"").length>=7) like_button.childNodes[1].textContent = Math.floor(count/1000000)+"M";
    else if ((count+"").length>=4) like_button.childNodes[1].textContent = Math.floor(count/1000)+"K";
    else like_button.childNodes[1].textContent = count;
}