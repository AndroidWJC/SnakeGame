package com.example.wang.game;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * Created by wang on 16-7-16.
 */

public class SecondActivity extends Activity implements OnClickListener{

    ImageButton button1,button2,button3,button4;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        button1=(ImageButton) findViewById(R.id.button_start);
        button2=(ImageButton) findViewById(R.id.button_difficulty);
        button3=(ImageButton) findViewById(R.id.button_music);
        button4=(ImageButton) findViewById(R.id.button_about);
        button4.setOnClickListener(this);
        button3.setOnClickListener(this);
        button2.setOnClickListener(this);
        button1.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.button_about:
                Intent intent1 = new Intent(SecondActivity.this, AboutActivity.class);
                startActivity(intent1);
                break;
            case R.id.button_music:
                Intent intent2 = new Intent(SecondActivity.this, MusicActivity.class);
                startActivity(intent2);
                break;
            case R.id.button_difficulty:
                Intent intent3 = new Intent(SecondActivity.this, DifficultyActivity.class);
                startActivity(intent3);
                break;
            case R.id.button_start:
                Intent intent4 = new Intent(SecondActivity.this, GameActivity.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }
}
