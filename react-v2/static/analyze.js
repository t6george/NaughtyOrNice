'use strict';

$(document).ready(initialize());

function startAnalysis() {
    move(-1, -1, -1, -1, -1, 0, -1, -1)
    var facebookUsername = document.getElementById("fb-id").value;
    var instagramUsername = document.getElementById("ig-id").value;
    if (facebookUsername === "eterwiel") {
        facebookGetInfo();
    }
    if (instagramUsername !== "") {
        $.getJSON('https://www.instagram.com/' + instagramUsername + '/?__a=1', function(userIdFind) {
            var userId = userIdFind.user.id;
            console.log(userId);
            document.getElementById("ody").innerHTML = "";
            console.log(Object.keys(userIdFind.user.media.nodes).length);
            for (var i = 0; i < Object.keys(userIdFind.user.media.nodes).length; i++) {
                var pictureUrl = userIdFind.user.media.nodes[i].display_src;
                console.log(pictureUrl);
                var isLast = false;
                if (i === Object.keys(userIdFind.user.media.nodes).length - 1) {
                    isLast = true;
                }
                processImage(pictureUrl, isLast);
                $.ajax({
                    type:"POST",
                    url: "/computePost",
                    contentType: "application/json; charset=utf-8",
                    data: JSON.stringify(userIdFind.user.media.nodes[i].caption),
                    dataType: 'json',
                    success: function(data, textStatus, jqXHR) {
                        var parsed = data;
                        console.log(parsed);
                        var data = parsed.response;
                        move(-1, -1, -1, -1, -1, data, -1, -1);
                    },
                    error: function(request, status, error) {
                        console.log("ERRORRRRRRR");
                    }
                })
            }
            displayInstagramPictures(userId);
        });
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
    block.run();
}

function processImage(pictureUrl, isLast) {
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
        // console.log(JSON.stringify(data, null, 2));
        $.ajax({
            type:"POST",
            url: "/computePicture",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(data),
            dataType: 'json',
            success: function(response) {
                console.log(JSON.stringify(response, null, 2));
            },
            error: function(request, status, error) {
                if (isLast) {
                    $.ajax({
                        type:"GET",
                        url: "/pullPictureData",
                        success: function(response) {
                            var parsed = JSON.parse(response);
                            var data = parsed.response;
                            move(data[0], data[1], data[2], data[3], data[4], -1, data[5], data[6]);
                            console.log(data);
                        },
                        error: function(request, status, error) {
                            console.log("b");
                        }
                    })
                }
            }
        })
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
    FB.api('/me/posts', 'GET', {access_token: 'EAACEdEose0cBAH9GC0rZARqRoyKpOrRKkJAKbGGZClADZAJI8efngYadIvZC6a630kg0dnzQ3OwJfmL0K9zizmMZC1UCAXhzemtZAQV6oeetpmY8LLA8oycIoFSOZBO7ZAAQiP2pSwPNOtHJfRZA8lmwDEDyklj8rJhNL3fQq7mUK2cklNTEEnfZAoVVQrDxWpZAc3HpbaGo6YdIAZDZD'}, function(userIdFind) {
        for (var i = 0; i < (userIdFind.data).length; i++) {
            console.log(userIdFind.data[i].message);
            $.ajax({
                type:"POST",
                url: "/computePost",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(userIdFind.data[i].message),
                dataType: 'json',
                success: function(response) {
                    console.log(JSON.stringify(response, null, 2));
                },
                error: function(request, status, error) {
                    console.log(request.responseText);
                }
            })
            // analyzeText({ 'documents': [{'id': '1', 'language': 'en', 'text': userIdFind.data[i].message}]});
        }
    })
}

function analyzeText(post) {
    // let https = require('https');
    // let accessKey = 'ecb60692c9dd4e2f934784b785d4633c';
    // let uri = 'westus.api.cognitive.microsoft.com';
    // let path = '/text/analytics/v2.0/sentiment';
    //
    // let response_handler = function (response) {
    //     let body = '';
    //     response.on ('data', function (d) {
    //         body += d;
    //     });
    //     response.on ('end', function () {
    //         let body_ = JSON.parse (body);
    //         let body__ = JSON.stringify (body_, null, '  ');
    //         console.log (body__);
    //     });
    //     response.on ('error', function (e) {
    //         console.log ('Error: ' + e.message);
    //     });
    // };
    //
    // let get_sentiments = function (documents) {
    //     let body = JSON.stringify (documents);
    //
    //     let request_params = {
    //         method : 'POST',
    //         hostname : uri,
    //         path : path,
    //         headers : {
    //             'Ocp-Apim-Subscription-Key' : accessKey,
    //         }
    //     };
    //
    //     let req = https.request (request_params, response_handler);
    //     req.write (body);
    //     req.end ();
    // }
    //
    // get_sentiments(post);
}
