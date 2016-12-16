import pandas as pd
import numpy as np
import pickle
import json


def printNMostInformative(feature_names, clf, N, metric_name):
   """Prints features with the highest coefficient values, per class"""
   # feature_names = vectorizer.get_feature_names()
   coefs_with_fns = sorted(zip(clf.coef_[0], feature_names))
   topClass1 = coefs_with_fns[:N]
   topClass2 = coefs_with_fns[:-(N + 1):-1]

   print("Class 1 best: ")
   for feat in topClass1:
       print(feat)

   print("Class 2 best: ")
   for feat in topClass2:
       print(feat)


def saveModel(classifier,name):
    f = open('resources/equian/python/' + name + '.pickle', 'wb')
    pickle.dump(classifier, f)
    f.close()

def loadModel(name):
    # f = open('resources/equian/python/PnC_classifier.pickle', 'rb')
    f = open('equian-resources/python/' + name + '.pickle', 'rb')
    classifier = pickle.load(f)
    f.close()
    return classifier

def cleanData(data):
    data['Third party involved'] = 'Yes'
    data.loc[data['Reasoning'] == 'NO THIRD PARTY INVOLVED', 'Third party involved'] = 'No'
    data.loc[data['Reasoning'] == 'WEATHER / STORM RELATED LOSS.', 'Third party involved'] = 'No'
    data.loc[data['Reasoning'] == 'WEATHER RELATED LOSS', 'Third party involved'] = 'No'
    data.loc[data['Reasoning'] == 'NO THIRD PARTY LIABILITY', 'Third party involved'] = 'Yes'
    data.loc[data['Reasoning'] == 'INSURED VS INSURED', 'Third party involved'] = 'No'
    data.loc[data['Reasoning'] == '1P DRIVER LIABILITY POTENTIAL', 'Third party involved'] = 'No'
    data.loc[data['Reasoning'] == 'CONTRIBUTORY NEGLIGENCE', 'Third party involved'] = 'Yes'

    data.loc[data['Subro Potential'] == 'No |No', 'Subro Potential'] = 'No'
    data.loc[data['Subro Potential'] == 'Yes |Yes', 'Subro Potential'] = 'Yes'
    data['CauseFeature'] = data['MaxCause']
    data['DamageFeature'] = data['MaxDamage']
    data['CauseFeature'] = data['CauseFeature'].astype('category')
    data['DamageFeature'] = data['CauseFeature'].astype('category')
    data['CauseFeature'] = data['CauseFeature'].cat.codes
    data['DamageFeature'] = data['DamageFeature'].cat.codes
    data = data.fillna('')
    return data

testData = pd.read_csv('equian-resources/python/' + 'docVec.tsv', encoding='utf_8', delimiter='\t') #, header=None
testData['CauseFeature'] = testData['MaxCause']
testData['DamageFeature'] = testData['MaxDamage']
testData['CauseFeature'] = testData['CauseFeature'].astype('category')
testData['DamageFeature'] = testData['CauseFeature'].astype('category')
testData['CauseFeature'] = testData['CauseFeature'].cat.codes
testData['DamageFeature'] = testData['DamageFeature'].cat.codes
columns = testData.columns
non_feature_list = ['MaxCause','MaxDamage']
feature_list = [i for i in columns if i not in non_feature_list]

# testData = testData.drop('MaxCause', axis=1, inplace=True)
# testData = testData.drop('MaxDamage', axis=1, inplace=True)
test = np.array(testData[feature_list].values)
# train = np.array(trainData[feature_list].values)
targets = ['Third party involved','Subro Potential']
# targets = ['Subro Potential']

df_output = pd.DataFrame()
output = {}
threshold = {'Third party involved':0.30,'Subro Potential':0.007}
for target in targets:
    # labelsTrain = np.array(trainData[target].values)
    # labelsTest = np.array(testData[target].values)

    # clf.fit(train,labelsTrain)
    clf = loadModel(target)
    predicted = clf.predict(test)
    predicted_prob = clf._predict_proba_lr(test)

    # print target,' predicted: ',predicted,' prediction confidence: ',np.maximum(predicted_prob[:,0],predicted_prob[:,1])
    pred_out = predicted[0]
    conf = round(np.maximum(predicted_prob[:,0],predicted_prob[:,1])[0],4)
    if(pred_out == 'No'):
        if(conf < (1-threshold[target])):
            pred_out = 'Yes'
        conf = 1-conf


    # output[target+' predicted'] = predicted[0]
    # output[target+' prediction confidence'] = round(np.maximum(predicted_prob[:,0],predicted_prob[:,1])[0],4)
    output[target+' predicted'] = pred_out
    #output[target+' prediction confidence'] = round(conf,4)
    output[target+' prediction confidence'] = '{0:.4f}'.format(conf)

json_data = json.dumps(output)
print json_data
# saveModel(clf)

# df_output.to_csv('../data/test/predictions_Test_without_TROVER_TRANSPAC_FOL.csv', encoding='utf_8', index_label='Equian_Id')


