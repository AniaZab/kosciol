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

var checkIfEmailMeetsRequirments_ChangeData = function() {
    if(validateEmail(document.getElementById("inputEmailChangeData").value)){
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
        alert ("\nPassword did not match: Please try again...")
        return false;
    }
}

//showPage("PageEvents");

