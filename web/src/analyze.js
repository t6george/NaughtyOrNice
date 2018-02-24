'use strict';

$(document).ready(initialize());

function startAnalysis() {
    var facebookUsername = document.getElementById("facebookUsername").value;
    var instagramUsername = document.getElementById("instagramUsername").value;
    $.getJSON('https://www.instagram.com/' + instagramUsername + '/?__a=1', function(userIdFind) {
        var userId = userIdFind.user.id;
        console.log(userId);
        document.getElementById("ody").innerHTML = "";
        displayInstagramPictures(userId);
        readyInstagramUserId(userId);
    });

    if (facebookUsername === "eterwiel") {
        facebookGetInfo();
    }
    return false;
}

function displayInstagramPictures(userId) {
    var intUserId = parseInt(userId);
    var block = new ody({
        get:"user",
        resolution:"standard_resolution",
        template: '<li><a href="{{link}}" target="_blank" style="background-image:url({{image}});background-size: cover;"><img src="https://lh3.googleusercontent.com/-P-gOTAfNfZ4/V2RPSYvECxI/AAAAAAAABng/Efqy2Oxjqm4lrmDhT07PBtlgYRb_MlJ7QCCo/s576/questyerror.png"></img><div class="instagrid-z"><div class="instagrids"><span class="instagrid-outter">{{likes}} <i class="fa fa-heart"></i><br/>{{comments}} <i class="fa fa-comment"></i></span></div></div></a></li>',
        userId: intUserId,
        accessToken:"265786819.d35b5f8.900bf4ccd93242d19036606f65670dd1"
    });
    console.log("Here " + userId);
    block.run();
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

function initialize() {
    window.fbAsyncInit = function() {
        FB.init({
            appId            : '161278361342995',
            autoLogAppEvents : true,
            xfbml            : true,
            version          : 'v2.12'
        });
    };

    (function(d, s, id){
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) {return;}
        js = d.createElement(s); js.id = id;
        js.src = "https://connect.facebook.net/en_US/sdk.js";
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
}

function facebookGetInfo() {
    FB.api('/me/posts', 'GET', {fields: 'first_name,last_name,id,picture'}, {access_token: 'EAACEdEose0cBACruhtt0eFfa5p8JoL0KRTsmSi8JmwZBqbaN8axv9c5Q3MNhslbYL4OXFJJFByc12XZA4ITeZA1xCh3hcAwrcHQURWBan2uEybYBxtAh93VqNFGCID478S1ZAdDiVN8SvzJC50FstUzrZC4mQBhH58rVJfZC2N7lZC4brJXx2QItIquKacxX7ZCBnFSOYSvJ0gZDZD'}, function(userIdFind) {
        console.log(JSON.stringify(userIdFind, null, 2))
    })
}