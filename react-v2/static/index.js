function move() {

    var i = 0;
    var bar = [];
    while(i < 9) {
        i++;
        console.log(i);
        bar[i] = document.getElementById('progBar'+ i);

    }
    var width = 10;
    var id = setInterval(frame, 80);
    var j = 0;
    function frame() {

        if (width >= 100) {
            clearInterval(id);
            j++;
        } else {
            width++;
            bar[j].style.width = width + '%';
            bar[j].innerHTML = width + '%';
            console.log(j);
        }

    }
    return false;
}