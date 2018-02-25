function move() {

    var scores = [];

    var i = 0;
    var x;
    var bar= ['progBar1', 'progBar2', 'progBar3', 'progBar4', 'progBar5', 'progBar6','progBar7' ,'progBar8'];

    var width = 10;

    for (var i = 0; i < 8; i++) {
        x = document.getElementById(bar[i]);
        x.style.width = 0 + '%';
        x.innerHTML = 0 + '%';
    }

    for (var i = 0; i < 8; i++) {
        x = document.getElementById(bar[i]);
        for (var j = 0; j < scores[i]; j++) {
            x.style.width = j + '%';
            x.innerHTML = j + '%';
        }
    }
    return false;
}