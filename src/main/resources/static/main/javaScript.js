const pagesInWebsite = ["PageRegularEvents", "PageNewEvents", "PageRegister", "PageResetPassword"];

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
showPage("PageRegularEvents");
