$(document).ready(initialize());

function initialize() {
    window.fbAsyncInit = function() {
        FB.init({
            appId            : '161278361342995',
            autoLogAppEvents : true,
            xfbml            : true,
            version          : 'v2.12'
        });

        FB.getLoginStatus(function(response) {
            if (response.status === "connected") {
                document.getElementById("s-status").innerHTML = "Connected";
            } else if (response.status === "not_authorized") {
                document.getElementById("s-status").innerHTML = "Not logged in";
            } else {
                document.getElementById("s-status").innerHTML = "Not logged in else";
            }
        })
    };

    (function(d, s, id){
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) {return;}
        js = d.createElement(s); js.id = id;
        js.src = "https://connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
}

function startAnalysis() {
    // var instagramUsername = document.getElementById("instagramUsername").value;
    // $.getJSON('https://www.instagram.com/' + instagramUsername + '/?__a=1', function(userIdFind) {
    //     var userId = userIdFind.user.id;
    //     console.log(userId);
    //     readyInstagramUserId(userId);
    // });

    facebookGetInfo();
    return false;
}

function readyInstagramUserId(userId) {
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

function login() {
    FB.login(function(response) {
        if (response.status === "connected") {
            document.getElementById("s-status").innerHTML = "Connected";
        } else if (response.status === "not_authorized") {
            document.getElementById("s-status").innerHTML = "Not logged in";
        } else {
            document.getElementById("s-status").innerHTML = "Not logged in else";
        }
    })
    return false;
}

function facebookGetInfo() {
    FB.api('/1452006591', 'GET', {fields: 'first_name,last_name,id,picture'}, {access_token: 'EAACEdEose0cBAEcL9dPyfcgVtJhDPQFozu0GvDYhUFKYWUUMgBceqCEkEn9VeZA5ZBHuhbpZB6KjAX20UVrSiFJZAwDK0cdUDCFhZAaZC115UyIxZB7tqU5TjSRmEpWBXezNFtH7NIjKU0RPhvIS1qCgDlgwUMBaX8iE2HFbST2gRrACKx6yhuuuybhKshjNxdqaXDbSEA2LAZDZD'}, function(userIdFind) {
        console.log(JSON.stringify(userIdFind, null, 2))
    })
}