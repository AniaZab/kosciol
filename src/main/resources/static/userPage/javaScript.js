const pagesInWebsite = ["PageEvents", "PageChangeUserData", "PageChangeUserPassword", "PageRecipients"];

function showPage(shown) {
    try {
        pagesInWebsite.forEach(page => {
            document.getElementById(page).style.display = 'none'
        })
        document.getElementById(shown).style.display = 'block';
    }
    catch (error){
        console.error(error);
    }
    return false;
}

var serverContext = "[[@{/}]]"
    function savePass(){
        var pass = $("#pass").val();
        var valid = pass == $("#passConfirm").val();
        if(!valid) {
            $("#error").show();
            return;
        }
        $.post(serverContext + "user/updatePassword",
            {password: pass, oldpassword: $("#oldpass").val()} ,function(data){
                window.location.href = serverContext +"/home.html?message="+data.message;
            })
            .fail(function(data) {
                $("#errormsg").show().html(data.responseJSON.message);
            });
    }

var checkIfEmailMeetsRequirments_ChangeData = function(elementID) {
    if(validateEmail(document.getElementById(elementID).value)){
        return true;
    }
    else{
        alert ("\nEmail is not valid!")
        return false;
    }
}
var checkIfPasswordsEqual_ChangePassword = function() {
    if (document.getElementById("inputConfirmPassword").value == document.getElementById("inputNewPassword").value) {
        return true;
    }
    else{
        alert ("\nPasswords did not match: Please try again...")
        return false;
    }
}

const validateName = (name) => {
    return String(name)
        .toLowerCase()
        .match(
            /^([a-zA-Z\\xC0-\\uFFFF]+([ \-']{0,1}[a-zA-Z\\xC0-\\uFFFF]+)*[.]{0,1}){1,2}$/
        );
};

var checkIfName = function(elementID, name) {
    validName = /^[a-z ,.'-]+$/i
    if(validName.test(document.getElementById(elementID).value)){
        return true;
    }
    else{
        if(name == 'l'){
            alert ("\nLastname is not valid!")
        }
        else{
            alert ("\nFirstname is not valid!")
        }
        return false;
    }
}

var checkChangeUserDataForm = function(inputIdLastName, inputIdFirstName, inputIdEmail) {
    if(checkIfName(inputIdLastName, 'l')==true &&
        checkIfName(inputIdFirstName, 'f')==true &&
        checkIfEmailMeetsRequirments_ChangeData(inputIdEmail)==true){
        return true;
    }
    else{
        return false;
    }
}

//showPage("PageEvents");

