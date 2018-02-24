import json
from watson_developer_cloud import NaturalLanguageUnderstandingV1
from watson_developer_cloud.natural_language_understanding_v1 \
  import Features, EntitiesOptions, KeywordsOptions, MetadataOptions

natural_language_understanding = NaturalLanguageUnderstandingV1(
  username='5ffbcf2d-bd95-4a08-9d83-b5e6ab1c1069',
  password='VNesmZYBhCuD',
  version='2017-02-27')

response = natural_language_understanding.analyze(
  text='Why the fucking hell are you cannabis!',
  features=Features(
    entities=EntitiesOptions(
      emotion=True,
      sentiment=True,
      limit=2),
    keywords=KeywordsOptions(
      emotion=True,
      sentiment=True,
      limit=2)))

print(json.dumps(response, indent=2))
def profanityCheck():
    f = open('censorList.txt','r') 
    censored = f.read().splitlines()
    f.close()
    #print(censored)
    vulgarity = 1
    n = len(response["keywords"])
    for i in range(n):
        if response["keywords"][i]["sentiment"]["score"]<0:
            print('hhjnkhnjhjn')

            for j in range(len(censored)):
                #print(response["keywords"][i]["text"]+"|"+censored[j]+"|")
                if censored[j] in response["keywords"][i]["text"].split(' '):
                    #print('hhjnkhnjhjn')
                    vulgarity += (response["keywords"][i]["emotion"]["disgust"]+ \
                                  response["keywords"][i]["emotion"]["anger"])
    return vulgarity
print(profanityCheck())
