package com.example.wang.game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener{
    Button button;
    EditText edit1,edit2;
    CheckBox checkbox;
    ProgressBar bar;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button) findViewById(R.id.login_button);
        edit1=(EditText) findViewById(R.id.input1);
        edit2=(EditText) findViewById(R.id.input2);
        checkbox=(CheckBox) findViewById(R.id.remember_button);
        bar=(ProgressBar) findViewById(R.id.progress);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember=pref.getBoolean("rem",false); //用于给是否保存密码赋值

        if(isRemember) {
            //将账号和密码设置到文本框中
            String account=pref.getString("account","");
            String password=pref.getString("password","");
            edit1.setText(account);
            edit2.setText(password);
            checkbox.setChecked(true);
        }
        button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        new Thread(new Runnable(){  //开启线程运行进度条
            @Override
            public void run() {
                for (int i = 0; i < 25; i++) {
                    int progress = bar.getProgress();
                    progress = progress + 10;
                    bar.setProgress(progress);
                }
            }
        }).start();

        String account=edit1.getText().toString();
        String password=edit2.getText().toString();
        if(account.equals("admin") && password.equals("123456")) {
            editor = pref.edit();
            if(checkbox.isChecked()) {
                editor.putBoolean("rem",true);
                editor.putString("account",account);
                editor.putString("password",password);
            }
            else {
                editor.clear();
            }
            editor.commit();
            Intent intent=new Intent(MainActivity.this,SecondActivity.class);
            startActivity(intent);

        }
        else{
            Toast.makeText(MainActivity.this,"账号或用户名错误",Toast.LENGTH_SHORT).show();
        }

    }
}
