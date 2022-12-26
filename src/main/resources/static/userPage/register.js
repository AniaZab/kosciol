function checkRegisterForm(form){
    if(checkLogin(form)){
        return checkIfPasswordsMatches(form);
    }
    else{
        return false;
    }
}

var checkAddUserForm = function() {
    if(checkIfPasswordsEqual()==true && checkIfEmailMeetsRequirments()==true){
        return true;
    }
    else{
        return false;
    }
}

var checkIfPasswordsEqual = function() {
    if (document.getElementById("inputPassword1").value == document.getElementById("inputPassword2").value) {
        return true;
    }
    else{
        alert ("\nPassword did not match: Please try again...")
        return false;
    }
}

const validateEmail = (email) => {
    return String(email)
        .toLowerCase()
        .match(
            /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
        );
};

var checkIfEmailMeetsRequirments = function() {
    if(validateEmail(document.getElementById("addUser_inputEmail").value)){
        return true;
    }
    else{
        alert ("\nEmail is not valid!")
        return false;
    }
}


function checkLogin(form){
    login = form.login.value;

    // If login not entered
    if (login == ''){
        alert ("Please enter login");
        //document.getElementById("errorLogin").value = "Please enter login";
        //document.getElementById("errorLogin").style.display="block";
        return false;
    }
    //document.getElementById("errorLogin").style.display="none";
    return true;
}

function checkPasswordMeetsExceptation(){
    var res;
    var str =
        document.getElementById("inputPassword1").value;
    if (str.match(/[a-z]/g) && str.match(
        /[A-Z]/g) && str.match(
        /[0-9]/g) && str.match(
        /[^a-zA-Z\d]/g) && str.length >= 8)
        res = "Poprawnie";
    else
        res = "Źle";
    //document.getElementById("errorPassword").style.display = 'block';
    //document.getElementById("errorPassword").value = res;
}

function checkIfPasswordsMatches() {

    if (document.getElementById("inputNewPassword").value == document.getElementById("inputConfirmPassword").value) {
        alert("Passwords match.")
        return true;
    }

    // If same return True.
    else{
        alert ("\nPassword did not match: Please try again...")
        return false;
    }
}




//A password is correct if it contains:
//
//     At least 1 uppercase character.
//     At least 1 lowercase character.
//     At least 1 digit.
//     At least 1 special character.
//     Minimum 8 characters.
