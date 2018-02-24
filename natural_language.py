import json
from watson_developer_cloud import NaturalLanguageUnderstandingV1
from watson_developer_cloud.natural_language_understanding_v1 \
  import Features, EntitiesOptions, KeywordsOptions, MetadataOptions

natural_language_understanding = NaturalLanguageUnderstandingV1(
  username='5ffbcf2d-bd95-4a08-9d83-b5e6ab1c1069',
  password='VNesmZYBhCuD',
  version='2017-02-27')

response = natural_language_understanding.analyze(
  text='LeBron James is a big time fucking pussy so what the fuck does he expect to win a championship what a loser!',
  features=Features(
    entities=EntitiesOptions(
      emotion=True,
      sentiment=True,
      limit=2),
    keywords=KeywordsOptions(
      emotion=True,
      sentiment=True,
      limit=2)))

#print(json.dumps(response, indent=2))
def profanityCheck():
    f = open('censoredList.txt','r') 

    censored = f.readline()
    vulgarity = 0
    for i in range(response["usage"]["features"]):
        if response["keywords"][i]["sentiment"]["score"]:
            for line in censored:
                if response["keywords"][i]["text"] in line:
                    vulgarity += (response["keywords"][i]["sentiment"]["score"])* \
                                 (response["keywords"][i]["relevance"])* \
                                 (response["keywords"][i]["emotion"]["sadness"]* \
                                  response["keywords"][i]["emotion"]["joy"]* \
                                  2*response["keywords"][i]["emotion"]["fear"]* \
                                  3*response["keywords"][i]["emotion"]["digust"]* \
                                  4*response["keywords"][i]["emotion"]["anger"]/11)
    f.close()
    return vulgarity

