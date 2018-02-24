function startInstagramAnalysis() {
    var username = document.getElementById("instagramUsername").value;
    $.getJSON('https://www.instagram.com/' + username + '/?__a=1', function(userIdFind) {
        var userId = userIdFind.user.id;
        console.log(userId);
        readyUserId(userId);
    });
    return false;
}

function readyUserId(userId) {
    var userUrl = 'https://api.instagram.com/v1/users/' + userId + '/media/recent/?access_token=265786819.d35b5f8.900bf4ccd93242d19036606f65670dd1';
    console.log(userUrl);
    $.getJSON(userUrl, function(userFind) {
        for (var i = 0; i < Object.keys(userFind.data).length; i++) {
            var pictureUrl = userFind.data[i].images.standard_resolution.url
            console.log(pictureUrl);
            processImage(pictureUrl)
        }
    });
}

function processImage(pictureUrl) {
    var key = "77606c7b568d4ad38cce114c66acd02c";
    var uriBase = "https://westcentralus.api.cognitive.microsoft.com/vision/v1.0/analyze";
    var params = {
        "visualFeatures": "Categories,Tags,Faces,Description,Adult",
        "details": "",
        "language": "en"
    };

    $.ajax({
        url: uriBase + "?" + $.param(params),
        beforeSend: function(xhrObj){
            xhrObj.setRequestHeader("Content-Type","application/json");
            xhrObj.setRequestHeader("Ocp-Apim-Subscription-Key", key);
        },
        type: "POST",
        data: '{"url": ' + '"' + pictureUrl + '"}'
    }).done(function(data) {
        console.log(JSON.stringify(data, null, 2));
    }).fail(function(jqXHR, textStatus, errorThrown) {
        console.log("Error: " + errorThrown);
    });
}