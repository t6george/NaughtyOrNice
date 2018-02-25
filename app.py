import json, sys
from flask import Flask, render_template, request, redirect, url_for
from flask import make_response

from watson_developer_cloud import NaturalLanguageUnderstandingV1
from watson_developer_cloud.natural_language_understanding_v1 \
  import Features, EntitiesOptions, KeywordsOptions, MetadataOptions

################################Initialize Image Analysis###########################
class TrainedNetwork(object):
    def __init__(self,layers,inputBiases=trained_net[0],inputWeights=trained_net[1],hiddenBiases=trained_net[2],hiddenWeights=trained_net[3]):
        self.layers = layers
        self.inputBiases = inputBiases
        self.inputWeights = inputWeights
        self.hiddenBiases = hiddenBiases
        self.hiddenWeights = hiddenWeights
        
    def feedforward(self,activation):
        pass

##for i in range(4):
##    for j in range(len(trained_net[i])):
##        print(trained_net[i][j])
        
with open('dictionary.pickle', 'rb') as handle:
    dictionary = pickle.load(handle)

input_net = [[0 for i in range(291)]]
t_net = TrainedNetwork([291,50,7])

##################################Initialize NLP###################################

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

######################################Flask########################################

app = Flask('launch')

@app.route("/start")
def webprint():
    return render_template('index.html')

@app.route("/computePicture", methods=['GET', 'POST'])
def computePicture():
    if request.method == 'POST':
        fromJs = request.json
        try:
            result = str(t_net.feedforward())
        except:
            result = str([0 for i in range(7)])
            
        print (fromJs["tags"][0]["name"])
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
    app.run(host = '0.0.0.0', port = 3000)
    
