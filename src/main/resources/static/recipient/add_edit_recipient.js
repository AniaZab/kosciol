function checkRecipientForm(form){
    if(checkEmailEmpty(form)==true && checkIfEmailMeetsRequirments==true){
        return true;
    }
    else{
        return false;
    }
}

function checkEmailEmpty(form){
    email = form.email.value;
    if (email == ''){
        alert ("Please enter email.");
        return false;
    }
    return true;
}
const validateEmail = (email) => {
    return String(email)
        .toLowerCase()
        .match(
            /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        );
};

var checkIfEmailMeetsRequirments = function() {
    if (validateEmail(document.getElementById("email").value)) {
        return true;
    } else {
        alert("\nEmail is not valid!")
        return false;
    }
}
