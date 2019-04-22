package com.example.sentimentanalysis;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button trainbutton = findViewById(R.id.trainbutton);
        Button testbutton = findViewById(R.id.testbutton);

        trainbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTrain();
            }
        });

        testbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTest();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This is a Open Source Sentiment Analysis Tool", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void startTest() {
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }

    private void startTrain() {
        Intent intent = new Intent(this, TrainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {

            Intent intent = new Intent(this,AboutActivity.class);
            startActivity(intent);

        }

        if(id == R.id.clear){
            String FILE_NAMEX = "xtrain.txt";
            String FILE_NAMEY = "ytrain.txt";
            FileOutputStream fos = null;
            FileOutputStream fosy = null;
            try {
                fos = openFileOutput(FILE_NAMEX,MODE_PRIVATE);
                fosy = openFileOutput(FILE_NAMEY,MODE_PRIVATE);
                fos.write(("").getBytes());
                fosy.write(("").getBytes());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    fos.close();
                    fosy.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

        return super.onOptionsItemSelected(item);
    }
}
