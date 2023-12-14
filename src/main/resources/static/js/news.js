window.onload = function (){
    attachReferenceOnMenuButton();
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