package com.example.wang.game;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;


public class MusicActivity extends Activity implements OnClickListener{
    private SharedPreferences saved;
    private SharedPreferences.Editor editor;
    private CheckBox on_button;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        saved = PreferenceManager.getDefaultSharedPreferences(this);
        on_button= (CheckBox) findViewById(R.id.button_on);
        boolean playMusic = saved.getBoolean("ifon" ,true);
        on_button.setChecked(playMusic);
        on_button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        editor=saved.edit();
        if(on_button.isChecked()){
            editor.putBoolean("ifon",true);
        }
        else{
            editor.putBoolean("ifon",false);
        }
        editor.commit();
    }

}
