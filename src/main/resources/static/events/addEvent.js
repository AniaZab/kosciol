function checkAddEventForm(form){
    if(checkTitle(form)==true && checkDates(form)==true && checkTimes(form)==true){
        //return true;
        return false;
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
    var format = 'hh:mm:ss'

// var time = moment() gives you current time. no format required.
//    startTime = form.idStartTime.value
//    finishTime = form.idFinishTime.value
    startTime = moment(form.idStartTime.value, format),
    finishTime = moment(form.idFinishTime.value, format);

    if (beforeTime > afterTime) {

        console.log('bad');
        return false;

    } else {

        console.log('good');

    }

    currentDate = new Date();

    startDate = new Date(currentDate.getTime());
    startDate.setHours(startTime.split(":")[0]);
    startDate.setMinutes(startTime.split(":")[1]);

    endDate = new Date(currentDate.getTime());
    endDate.setHours(finishTime.split(":")[0]);
    endDate.setMinutes(finishTime.split(":")[1]);

    if(checkDates(form)==true){
        if(startDate > endDate){
            alert ("Finish time can not be earlier than start time!");
            return false;
        }else{
            return true;
        }
    }
    else{
        return false;
    }
}
