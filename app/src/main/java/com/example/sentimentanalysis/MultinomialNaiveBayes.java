package com.example.sentimentanalysis;


public class MultinomialNaiveBayes {


    float posteriorProb(int type) {
        float temp = 0;
        for(int i=0;i<Vectorizer.y.size();i++) {
            if(Vectorizer.y.get(i) == type) {
                temp++;
            }
        }
        return temp/Vectorizer.y.size();
    }

    float totalWords(int type) {
        float count = 0;
        for(int i=0;i<Vectorizer.str_Train.size();i++) {
            if(Vectorizer.y.get(i) == type) {
                for(int j=0;j<Vectorizer.vect_train[i].length;j++) {
                    count += Vectorizer.vect_train[i][j];
                }
            }
        }

        return count;
    }

    float likelihood(int index,int type) {
        float count = 0;
        for(int i=0;i<Vectorizer.str_Train.size();i++) {
            if(Vectorizer.y.get(i) == type) {
                count += Vectorizer.vect_train[i][index];
            }
        }

        return (count+1)/(totalWords(type)+Vectorizer.value);
    }

    float conditionalProb(int[] vec,int type) {
        float temp = 1;
        for(int i=0;i<vec.length;i++) {
            if(vec[i] > 0) {
                temp = temp*likelihood(i, type);
            }
        }

        return temp*posteriorProb(type);
    }

    float[] predict(int[] vec,int rev) {
        float[] res = new float[2];
        float pos = conditionalProb(vec, 1);
        float neg = conditionalProb(vec, 0);
        res[0] = neg/(neg+pos);
        res[1] = 1-res[0];
        if(rev == 1) {
            float temp = res[0];
            res[0] = res[1];
            res[1] = temp;
        }
        return res;
    }

}
