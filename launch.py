import json
import sys
from flask import Flask, render_template, request, redirect, url_for
from flask import make_response

app = Flask('launch')

@app.route("/start")
def webprint():
    return render_template('index.html')

@app.route("/computePicture", methods=['GET', 'POST'])
def computePicture():
    if request.method == 'POST':
        fromJs = request.json
        print (fromJs["tags"][0]["name"])
        result = "return this"
        resp = make_response('{"response": '+result+'}')
        return resp

@app.route("/computePost", methods=['GET', 'POST'])
def computePost():
    if request.method == 'POST':
        fromJs = request.json
        print (fromJs)
        result = "return this"
        resp = make_response('{"response": '+result+'}')
        return resp

if __name__ == '__main__':
    app.run(host = '0.0.0.0', port = 3000)
