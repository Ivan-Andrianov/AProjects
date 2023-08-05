window.onload = function (){
    let request = new XMLHttpRequest();

    request.onreadystatechange = function (){
        if (request.status===3){
            let user = JSON.parse(request.responseText);
        }
    }
}