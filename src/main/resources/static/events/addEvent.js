function checkAddEventForm(form){
    if(checkTitle(form)==true && checkDates(form)==true && checkTimes(form)==true){
        return true;
    }
    else{
        return false;
    }
}

function checkTitle(form){
    title = form.title.value;
    if (title == ''){
        alert ("Please enter title.");
        return false;
    }
    return true;
}
var checkDates = function(form){
    finishDate = form.idFinishDate.value
    startDate = form.idStartDate.value

    if(Date.parse(startDate) > Date.parse(finishDate)){
        alert ("Finish date can not be earlier than start date!");
        return false;
    }else{
        return true;
    }
}
var checkTimes = function(form){
    finishDate = form.idFinishDate.value
    startDate = form.idStartDate.value
    const startTime = new Date('2020-01-01 ' + form.idStartTime.value);
    const finishTime = new Date('2020-01-01 ' + form.idFinishTime.value);

    if (startTime.getTime() > finishTime.getTime() && Date.parse(startDate) == Date.parse(finishDate)) {
        alert ("Bad time! Finish time can not be earlier than start time!");
        console.log('bad');
        return false;

    } else {
        console.log('good');
        return true;

    }
}
