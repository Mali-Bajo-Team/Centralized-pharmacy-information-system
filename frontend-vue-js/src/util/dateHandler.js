export function getStringDateFromMilliseconds(dateInMilliseconds) {
    let today = new Date(dateInMilliseconds);
    let dd = String(today.getDate()).padStart(2, "0");
    let mm = String(today.getMonth() + 1).padStart(2, "0"); //January is 0!
    let yyyy = today.getFullYear();

    today = yyyy + "-" + mm + "-" + dd;
    return today;
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

export function isAvailableToCancelConsultation(consultationDate) {
    let twentyFourHoursFromTodayDate = new Date();
    twentyFourHoursFromTodayDate.setDate(twentyFourHoursFromTodayDate.getDate() + 1);
    if (twentyFourHoursFromTodayDate.getTime() > consultationDate) {
        return false;
    }
    return true;
}

export function getStringDateWithTimeFromMilliseconds(dateInMilliseconds) {
    var date = getStringDateFromMilliseconds(dateInMilliseconds);

    var withTime = new Date(dateInMilliseconds);
    return date + " " + String(withTime.getHours()).padStart(2, "0") + ":" + String(withTime.getMinutes()).padStart(2, "0");
}

export function getMillisecondsFromStringDate(date) {
    if (!date)
        return null;

    const [year, month, day] = date.split('-');

    return new Date(parseInt(year), parseInt(month) - 1, parseInt(day)).getTime()
}

export function getMillisecondsFromStringTimeAndDate(time, date) {
    let realdate = new Date(getMillisecondsFromStringDate(date));
    const [hours, minutes] = time.split(':');
    realdate.setHours(hours);
    realdate.setMinutes(minutes);
    return realdate.getTime();
}

export function getCurrentDate() {
    let date = new Date();
    return new Date(date.getFullYear, date.getMonth, date.getDate);
}

export function getTimeStringFromMilliseconds(milliseconds) {
    let date = new Date(milliseconds)
    return String(date.getHours()).padStart(2, "0") + ":" + String(date.getMinutes()).padStart(2, "0");
}
