export function getStringDateFromMilliseconds(dateInMilliseconds) {
    return new Date(dateInMilliseconds).toISOString().split('T')[0];
}

// Thanks stacky: https://stackoverflow.com/questions/1531093/how-do-i-get-the-current-date-in-javascript
export function getTodayDateString() {
    let today = new Date();
    let dd = String(today.getDate()).padStart(2, "0");
    let mm = String(today.getMonth() + 1).padStart(2, "0"); //January is 0!
    let yyyy = today.getFullYear();

    today = yyyy + "-" + mm + "-" + dd;
    return today;
}

export function isAvailableToCancelConsultation(consultationDate){
    let twentyFourHoursFromTodayDate =new Date();
    twentyFourHoursFromTodayDate.setDate(twentyFourHoursFromTodayDate.getDate() + 1);
    if(twentyFourHoursFromTodayDate.getTime() > consultationDate){
        return false;
    }
    return true;
}

export function getStringDateWithTimeFromMilliseconds(dateInMilliseconds) {
    var date = new Date(dateInMilliseconds).toISOString().split('T')[0];
    var time = new Date(dateInMilliseconds).toISOString().split('T')[1];
    var DateWithTime = date + " "+ time.substring(0,5);
    return DateWithTime;
}
