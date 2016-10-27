
var slideNum = 1;
var imageElement = document.getElementById("imageElement");
var maxImages = 2;

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