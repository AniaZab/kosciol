const pagesInWebsite = ["PageEvents", "PageChangeUserData", "PageAddUser"];

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


showPage("PageEvents");

