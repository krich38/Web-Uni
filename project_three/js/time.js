/**
 * Created by Kyle on 3/01/2015.
 */

function showDays() {
    var now = new Date();
    var thisYear = now.getFullYear();
    var Xmas = "December 25, " + thisYear;
    var nextXmas = new Date(Xmas);
    // Number of milliseconds per day
    var msPerDay = 24 * 60 * 60 * 1000;
    var daysLeft = (nextXmas.getTime()
        - now.getTime()) / msPerDay;
    daysLeft = Math.round(daysLeft);
    alert("There are " + daysLeft + " sleeps until Christmas");
}

function showDate() {
    var currentDate = new Date()
    var day = currentDate.getDate()
    var month = currentDate.getMonth() + 1
    var year = currentDate.getFullYear()
    alert("The date is : " + day + "/" + month + "/" + year);
}

function showTime() {
    var time = "";
    var currentTime = new Date()
    var hours = currentTime.getHours()
    var minutes = currentTime.getMinutes()
    if (minutes < 10) {
        minutes = "0" + minutes
    }
    time = hours + ":" + minutes + " ";
    if (hours > 11) {
        time = time + "PM"
    } else {
        time = time + "AM"
    }
    alert("The time is : " + time);
}