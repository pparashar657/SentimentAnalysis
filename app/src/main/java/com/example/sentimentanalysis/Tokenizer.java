package com.example.sentimentanalysis;

import java.util.ArrayList;
import java.util.Arrays;

public class Tokenizer {

    ArrayList<String> outWords = new ArrayList<String>();
    static ArrayList<ArrayList<String>> cleanSent = new ArrayList<ArrayList<String>>();

    public Tokenizer() {

    }

    ArrayList<String> stop_words = new ArrayList<String>(
            Arrays.asList("","a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "could", "did", "do", "does", "doing", "down", "during", "each", "few", "for", "from", "further", "had", "has", "have", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "it", "it's", "its", "itself", "let's", "me", "more", "most", "my", "myself", "nor", "of", "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over", "own", "same", "she", "she'd", "she'll", "she's", "should", "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "we", "we'd", "we'll", "we're", "we've", "were", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "would", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves" 	)
    );

    ArrayList<String> suffixes = new ArrayList<String>(
            Arrays.asList("e","er","ion","ity","ment","iness","ness","or","sion","ship","th","able","ible","al","ant","ary","ful","ic","ous","ious","ive","less","y","ed","es","en","er","ing","ize","ise","ly","ward","wise")
    );

    boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    String removeSuffix(String str) {
        String temp;
        for(int i = 5;i>0;i--) {
            if(str.length()>i) {
                temp = str.substring(str.length()-i, str.length());
                if(suffixes.contains(temp)) {
                    str = str.substring(0, str.length()-i);
                    break;
                }
            }
        }
        return str;
    }

    boolean isStopWord(String str) {
        return stop_words.contains(str);
    }

    public ArrayList<String> tokenize(String sent) {
        outWords = new ArrayList<String>();
        sent = sent.toLowerCase();
        String[] tokens=sent.split(" ");

        ArrayList<Character> special_symbols = new ArrayList<Character>(Arrays.asList('.','?','!',',','/',';','<','>','&','%','@','$','*','(',')','-','_'));

        for(String i:tokens) {

            while(i.length()>0&&special_symbols.indexOf(i.charAt(i.length()-1))!=-1) {
                i = i.substring(0,i.length()-1);
            }

            if(!isNumeric(i)&&!isStopWord(i)) {
                i = removeSuffix(i);
                outWords.add(i);
            }
        }

        cleanSent.add(outWords);
        return outWords;

    }
}








