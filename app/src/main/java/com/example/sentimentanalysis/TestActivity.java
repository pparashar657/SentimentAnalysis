package com.example.sentimentanalysis;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

public class TestActivity extends AppCompatActivity {


    private static final String FILE_NAMEX = "xtrain.txt";
    private static final String FILE_NAMEY = "ytrain.txt";


    Button test;
    EditText edittext;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Vectorizer.clear();
        load();

        test = findViewById(R.id.test);
        CheckPermission();
        edittext = findViewById(R.id.editTextTest);
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);

        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                if(matches != null){
                    edittext.setText(matches.get(0));
                }

            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        findViewById(R.id.speakbutton2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()){
                    case MotionEvent.ACTION_UP:
                        mSpeechRecognizer.stopListening();
                        edittext.setHint("You will see the input Here");
                        break;


                    case MotionEvent.ACTION_DOWN:
                        edittext.setText("");
                        edittext.setHint("Listening......");
                        mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
                        break;
                }
                return false;
            }
        });


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Vectorizer.all_exmp.size() == 0){
                    Snackbar.make(v, "Training Set Is Empty!!!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }else{
                    String text = edittext.getText().toString();
                    if(text.length() == 0){
                        Snackbar.make(v, "Empty Test Statement", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else {
                        Vectorizer.all_exmp.add(text);
                        Vectorizer.str_Train = new ArrayList<String>(Vectorizer.all_exmp.subList(0,Vectorizer.all_exmp.size()-1));
                        Vectorizer.str_Test = new ArrayList<String>(Vectorizer.all_exmp.subList(Vectorizer.all_exmp.size()-1,Vectorizer.all_exmp.size()));
                        Vectorizer.findWords();
                        Vectorizer.makeVectors();
                        MultinomialNaiveBayes mnb = new MultinomialNaiveBayes();
                        float[] res = mnb.predict(Vectorizer.vect_test[0],Vectorizer.rev[0]);
                        Intent intent = new Intent(TestActivity.this,AfterTestActivity.class);
                        intent.putExtra("result",res);
                        startActivity(intent);
                    }
                }

            }
        });

    }


    public void load() {
        FileInputStream fisx = null;
        FileInputStream fisy = null;

        try {
            fisx = openFileInput(FILE_NAMEX);
            fisy = openFileInput(FILE_NAMEY);
            InputStreamReader isr = new InputStreamReader(fisx);
            BufferedReader br = new BufferedReader(isr);

            InputStreamReader isry = new InputStreamReader(fisy);
            BufferedReader bry = new BufferedReader(isry);

            String text;

            while ((text = br.readLine()) != null) {
                Vectorizer.all_exmp.add(text);
            }
            while ((text = bry.readLine()) != null) {
                Vectorizer.y.add(Integer.parseInt(text));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fisx != null) {
                try {
                    fisx.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fisy != null) {
                try {
                    fisy.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void CheckPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if(!(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED)){

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:"+getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }

}
