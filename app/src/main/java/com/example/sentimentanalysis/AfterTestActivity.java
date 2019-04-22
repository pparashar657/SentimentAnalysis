package com.example.sentimentanalysis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class AfterTestActivity extends AppCompatActivity {


    TextView text1;
    TextView text2;
    TextView text3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_test);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);

        Bundle bundle = getIntent().getExtras();
        float[] res = bundle.getFloatArray("result");
        if(res[0]>res[1]){
            text1.setText("The Review belongs to the Negative Class");
            text2.setVisibility(View.VISIBLE);
            text3.setText(res[0]+"");
        }else {
            text1.setText("The Review belongs to the Positive Class");
            text2.setVisibility(View.VISIBLE);
            text3.setText(res[1] + "");
        }
        Vectorizer.clear();
        findViewById(R.id.gohome2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AfterTestActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}

