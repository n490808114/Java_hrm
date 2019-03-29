function showPic(whichpoint){
    if(!document.getElementById) return false;
    var imgSource = whichpoint.getAttribute("href");
    var imgTitle = whichpoint.getAttribute("title");
    var imgpoint = document.getElementById("showimg");
    imgpoint.setAttribute("src",imgSource);
    imgpoint.setAttribute("alt",imgTitle);
    return true;
}
function preparelinks(){
    prepareimg();
    var a = document.getElementById("showPic").getElementsByTagName("a");
    for(var i=0;i<a.length;i++){
        a[i].setAttribute("onclick","return !showPic(this)");
    }
}
function prepareimg(){
    var img = document.createElement("img");
    insertAfter(img,document.getElementById("showPic"));
    img.setAttribute("id","showimg");
    img.setAttribute("src","");
}

