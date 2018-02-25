function move(sc0, sc1, sc2, sc3, sc4, sc5, sc6, sc7,) {

    

    var scores = [sc0 * 100, sc1 * 100, sc2 * 100, sc3 * 100, sc4 * 100, sc5 * 100, sc6 * 100, sc7 * 100];

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
            if (scores[i] !== -1) {
                x.style.width = j + '%';
                x.innerHTML = j + '%';    
            }
        }
    }
    return false;
}
