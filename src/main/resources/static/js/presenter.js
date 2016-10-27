
var slideNum = 1;
var imageElement = document.getElementById("imageElement");
function next() {
    var showSlide = slideNum + 1;
    slideNum = showSlide;
    var imageSrc =  "images/image" + showSlide + ".jpg";
    console.log("Show image " + imageSrc);
    imageElement.src = imageSrc;
}
function previous() {
    var showSlide = slideNum - 1;
    slideNum = showSlide;
    var imageSrc =  "images/image" + showSlide + ".jpg";
    console.log("Show image " + imageSrc);
    imageElement.src = imageSrc;
}