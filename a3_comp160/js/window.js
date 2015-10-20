/**
 * @author: Kyle
 * Created by IntelliJ IDEA, 3/01/2015.
 */
function prepareWindow() {
    var changeInterval = 3000; // 3000 = 3 seconds, as per assignment instructions
    setInterval(moveReindeer, changeInterval);
    setInterval(moveCane, changeInterval);
}

function moveReindeer() {
    var image_Element = document.getElementById("reindeer");

    var availVertical = window.innerHeight - image_Element.clientHeight;
    var availHorizontal = window.innerWidth - image_Element.clientWidth;
    var randNum_V = Math.round(Math.random() * availVertical);
    var randNum_H = Math.round(Math.random() * availHorizontal);

    image_Element.style.top = randNum_V + "px";
    image_Element.style.left = randNum_H + "px";
}

function moveCane() {
    var image_Element = document.getElementById("cane");

    var availVertical = window.innerHeight - image_Element.clientHeight;
    var availHorizontal = window.innerWidth - image_Element.clientWidth;
    var randNum_V = Math.round(Math.random() * availVertical);
    var randNum_H = Math.round(Math.random() * availHorizontal);

    image_Element.style.top = randNum_V + "px";
    image_Element.style.left = randNum_H + "px";
}

