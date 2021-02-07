export function getStringDateFromMilliseconds(dateInMilliseconds) {
    return new Date(dateInMilliseconds).toLocaleDateString("en-UE");
}