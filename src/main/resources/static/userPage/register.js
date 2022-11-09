function checkRegisterForm(form){
    return checkLogin(form) && checkIfPasswordsMatches(form);
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
function checkIfPasswordsMatches(form) {
    password1 = form.password1.value;
    password2 = form.password2.value;

    // If password not entered
    if (password1 == ''){
        alert ("Please enter Password");
        return false;
    }

    // If confirm password not entered
    else if (password2 == ''){
        alert ("Please enter confirm password");
        return false;
    }

    // If Not same return False.
    else if (password1 != password2) {
        alert ("\nPassword did not match: Please try again...")
        return false;
    }

    // If same return True.
    else{
        alert("Passwords match.")
        return true;
    }
}
//A password is correct if it contains:
//
//     At least 1 uppercase character.
//     At least 1 lowercase character.
//     At least 1 digit.
//     At least 1 special character.
//     Minimum 8 characters.
