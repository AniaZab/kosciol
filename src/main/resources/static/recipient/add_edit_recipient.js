function checkRecipientForm(form){
    return checkEmailEmpty(form);
}

function checkEmailEmpty(form){
    email = form.email.value;
    if (email == ''){
        alert ("Please enter email.");
        return false;
    }
    return true;
}