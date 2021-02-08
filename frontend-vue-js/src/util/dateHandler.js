export function getStringDateFromMilliseconds(dateInMilliseconds) {
    return new Date(dateInMilliseconds).toISOString().split('T')[0];
}