import json
import sys
import os
from flask import Flask, render_template, request, redirect, url_for, send_file
from flask import make_response
from angular_flask import app

from watson_developer_cloud import NaturalLanguageUnderstandingV1
from watson_developer_cloud.natural_language_understanding_v1 \
  import Features, EntitiesOptions, KeywordsOptions, MetadataOptions

natural_language_understanding = NaturalLanguageUnderstandingV1(
  username='5ffbcf2d-bd95-4a08-9d83-b5e6ab1c1069',
  password='VNesmZYBhCuD',
  version='2017-02-27')

def profanityCheck(nlp):
    f = open('censorList.txt','r')
    censored = f.read().splitlines()
    f.close()

    vulgarity = 0
    n = len(nlp["keywords"])

    for i in range(n):
        if nlp["keywords"][i]["sentiment"]["score"]<0:
            for j in range(len(censored)):
                if censored[j] in nlp["keywords"][i]["text"].split(' '):
                    vulgarity += (nlp["keywords"][i]["emotion"]["disgust"]+ \
                                  nlp["keywords"][i]["emotion"]["anger"]+ \
                                  nlp["keywords"][i]["sentiment"]["score"]*(-1))

    return vulgarity


app = Flask('app')

@app.route("/start")
def runserver():
    app.run(host='0.0.0.0', port = 3000)

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
        try:
            response = natural_language_understanding.analyze(
              text=fromJs,
              features=Features(
                entities=EntitiesOptions(
                  emotion=True,
                  sentiment=True,
                  limit=2),
                keywords=KeywordsOptions(
                  emotion=True,
                  sentiment=True,
                  limit=2)))

            result = str(profanityCheck(response))
        except:
            result = '0'
        resp = make_response('{"response": '+result+'}')
        return resp


if __name__ == '__main__':
    runserver()

print ("fuck")
