function checkAdduserForm(form){
    return checkTitle(form);
}

function checkTitle(form){
    title = form.title.value;
    if (title == ''){
        alert ("Please enter title");
        return false;
    }
    return true;
}