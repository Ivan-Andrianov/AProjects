window.onload = function (){
    attachReferenceOnMenuButton();
    let menu_buttons = document.getElementsByClassName("menu_button");
    for (let button_index in menu_buttons){
        if (menu_buttons[button_index].addEventListener==null) continue;
        menu_buttons[button_index].addEventListener("mouseover",overMenuButton);
        menu_buttons[button_index].addEventListener("mouseout",outMenuButton);
    }

    let request = new XMLHttpRequest();
    request.open("GET","/getNews");
    request.onreadystatechange = function(){
        if (request.readyState==4){
            let news = JSON.parse(request.responseText);

            for (let news_index in news){
                showNews(news[news_index]);
            }
        }
    }
    request.send();
}