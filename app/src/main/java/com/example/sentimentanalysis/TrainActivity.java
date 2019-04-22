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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class TrainActivity extends AppCompatActivity {


    private static final String FILE_NAMEX = "xtrain.txt";
    private static final String FILE_NAMEY = "ytrain.txt";
    EditText edittext;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;
    Button submit;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        CheckPermission();
        edittext = findViewById(R.id.editText);

        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(v);
            }
        });

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

        findViewById(R.id.speakbutton).setOnTouchListener(new View.OnTouchListener() {
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

    }

    private void saveData(View v) {

        String text = edittext.getText().toString();
        FileOutputStream fos = null;
        FileOutputStream fosy = null;

        try {
            fos = openFileOutput(FILE_NAMEX, MODE_APPEND);
            fosy = openFileOutput(FILE_NAMEY,MODE_APPEND);
            text = text+"\n";
            fos.write(text.getBytes());
            String y = getY()+"\n";
            fosy.write(y.getBytes());
            edittext.getText().clear();
            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAMEX,
                    Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (fosy != null) {
                try {
                    fosy.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private int getY() {
        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if(selectedId == R.id.positive){
            Log.d("message for y","Positive");
            return 1;
        }else{
            Log.d("message for y","Negative");
            return 0;
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
