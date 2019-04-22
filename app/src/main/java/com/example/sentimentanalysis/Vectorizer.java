package com.example.sentimentanalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Vectorizer {


    public static int[][] vect_train;
    public static int[][] vect_test;
    public static int[] rev;
    static ArrayList<String> all_exmp = new ArrayList<String>();

    static ArrayList<String> str_Train;

    static ArrayList<String> str_Test;



    static ArrayList<Integer> y = new ArrayList<Integer>();

    public static HashMap<String ,Integer> words = new HashMap<String, Integer>();
    public static int value = 0;

    public static void findWords() {
        Tokenizer t = new Tokenizer();
        for(int i=0;i<all_exmp.size();i++) {
            String curr_str = all_exmp.get(i);
            ArrayList<String> tokens = t.tokenize(curr_str);
            for(String k:tokens) {
                if(!words.containsKey(k)) {
                    words.put(k, value);
                    value++;
                }
            }
        }
    }


    public static void makeVectors() {
        vect_train = new int[str_Train.size()][value];
        vect_test = new int[str_Test.size()][value];
        rev = new int[str_Test.size()];

        for(int i=0;i<Tokenizer.cleanSent.size();i++) {
            ArrayList<String> curr = Tokenizer.cleanSent.get(i);

            for(int j=0;j<curr.size();j++) {
                if(i<vect_train.length) {
                    vect_train[i][words.get(curr.get(j))] += 1;
                }else {
                    vect_test[i-vect_train.length][words.get(curr.get(j))] += 1;
                    if(curr.get(j).contentEquals("not")) {
                        rev[i-vect_train.length] = 1;
                    }
                }
            }

        }

    }

    public static void clear(){
        all_exmp = new ArrayList<String>();
        str_Test = new ArrayList<String>();
        str_Train = new ArrayList<String>();
    }


}
