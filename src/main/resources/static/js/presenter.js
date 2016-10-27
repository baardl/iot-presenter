
var slideNum = 1;
var imageElement = document.getElementById("imageElement");
var maxImages = 2;
var lastUpdated = Date.now();

document.onkeydown = isArrow;

function start(){
    slideNum = 1;
    showSlide(slideNum);
}
function next() {
    var current = slideNum + 1;
    if (current <= maxImages){
        showSlide(current);
    }

}

function previous() {
    var current = slideNum - 1;
    if (current > 0) {
        showSlide(current);
    }
}

function showSlide(slide) {
    slideNum = slide;
    var imageSrc = "images/image" + slide + ".jpg";
    console.log("Show image " + imageSrc);
    imageElement.src = imageSrc;
}

/*
 Expected payload
 {"data":"169","payload":{"servoAngle":"169","sensorId":"servoAngle"},"coreid":"31003d000a47343432313031","event":"servoAngle","published_at":"2016-10-26T20:47:44.517Z","deviceId":"baardlTi","observedTimestamp":1476182054867}
 */
function updatePresentation(payload) {
    if (imageElement==null)
    {imageElement = document.getElementById("imageElement");}
    if (typeof payload !== 'undefined'){
        var servoAngle = payload.servoAngle;
        if (typeof servoAngle !== 'undefined') {
            if (servoAngle > 100) {
                lastUpdated = Date.now();
                next();
            }
        }
    }
}

function isArrow(e){
    e = e || window.event;

    if (e.keyCode == '38') {
        // up arrow
    }
    else if (e.keyCode == '40') {
        // down arrow
    }
    else if (e.keyCode == '37') {
        previous()
    }
    else if (e.keyCode == '39') {
        next();
    }
}