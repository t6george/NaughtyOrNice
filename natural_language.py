import json
from watson_developer_cloud import NaturalLanguageUnderstandingV1
from watson_developer_cloud.natural_language_understanding_v1 \
  import Features, EntitiesOptions, KeywordsOptions, MetadataOptions

natural_language_understanding = NaturalLanguageUnderstandingV1(
  username='5ffbcf2d-bd95-4a08-9d83-b5e6ab1c1069',
  password='VNesmZYBhCuD',
  version='2017-02-27')

response = natural_language_understanding.analyze(
  text='Why the fucking hell are you fucked!',
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

    censored = f.read()
    vulgarity = 0
    n = len(response["keywords"])
    for i in range(n):
        if response["keywords"][i]["sentiment"]["score"]:
            if response["keywords"][i]["text"] in censored:
                print(response["keywords"][i]["emotion"]["digust"])
                vulgarity += (response["keywords"][i]["emotion"]["digust"]+ \
                              response["keywords"][i]["emotion"]["anger"])
    f.close()
    return vulgarity
print(profanityCheck())
