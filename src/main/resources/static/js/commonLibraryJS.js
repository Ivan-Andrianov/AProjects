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
    let like_image = document.createElement("img");
    like_image.src = "/pictures/Like.png";
    like_button.appendChild(like_image);
    let like_content = document.createElement("p");
    if (news["likes_count"]>=7) like_content.textContent = Math.floor(news["likes_count"]/1000000)+"M";
    else if (news["likes_count"]>=4) like_content.textContent = Math.floor(news["likes_count"]/1000)+"K";
    else like_content.textContent = news["likes_count"];
    like_button.appendChild(like_content);
    buttons_container.appendChild(like_button);

    let comments_button = document.createElement("button");
    comments_button.addEventListener("mouseover",over_interactive_panel_button);
    comments_button.addEventListener("mouseout",out_interactive_panel_button);
    comments_button.className = "comment";
    let comment_image = document.createElement("img");
    comment_image.src = "/pictures/Comment.png";
    comments_button.appendChild(comment_image);
    let comment_content = document.createElement("p");
    if (news["comments_count"]>=7) comment_content.textContent = Math.floor(news["comments_count"]/1000000)+"M";
    else if (news["comments_count"]>=4) comment_content.textContent = Math.floor(news["comments_count"]/1000)+"K";
    else comment_content.textContent = news["comments"].length;
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
    comments.className = "comments";
    news_element.appendChild(comments);
    let comments_information = news["comments"];
    let comments_container = document.createElement("div");
    comments_container.className = "comment_container";
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

        let like_content = document.createElement("p");
        if (comment["likes_count"]>=7) like_content.textContent = Math.floor(news["likes_count"]/1000000)+"M";
        else if (comment["likes_count"]>=4) like_content.textContent = Math.floor(news["likes_count"]/1000)+"K";
        else like_content.textContent = comment["likes_count"];

        let button_for_comment = document.createElement("button");
        button_for_comment.className = "button_for_comment";
        let like_image = document.createElement("img");
        like_image.src = "images/Like.png";
        button_for_comment.appendChild(like_image);
        button_for_comment.appendChild(like_content);

        let like_panel_for_comment = document.createElement("div");
        like_panel_for_comment.appendChild(button_for_comment);
        comment_container.appendChild(like_panel_for_comment);

        comments.appendChild(comment_container);
    }
    news_container.appendChild(news_element);
}