package com.example.wang.game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;


public class GameActivity extends Activity implements OnClickListener{
    private SharedPreferences saved;
    private static String ICICLE_KEY = "snake-view";
    private SnakeView mSnakeView;
    private ImageButton change_stop,change_start,change_quit;
    private ImageButton mLeft;
    private ImageButton mRight;
    private ImageButton mUp;
    private ImageButton mDown;
    private static final int UP = 1;
    private static final int DOWN = 2;
    private static final int RIGHT = 3;
    private static final int LEFT = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mSnakeView = (SnakeView) findViewById(R.id.snake);
        mSnakeView.setTextView((TextView) findViewById(R.id.text_show));

        change_stop = (ImageButton) findViewById(R.id.game_stop);
        change_start = (ImageButton) findViewById(R.id.game_start);
        change_quit = (ImageButton) findViewById(R.id.game_quit);

        mLeft = (ImageButton) findViewById(R.id.left);
        mRight = (ImageButton) findViewById(R.id.right);
        mUp = (ImageButton) findViewById(R.id.up);
        mDown = (ImageButton) findViewById(R.id.down);

        change_start = (ImageButton) findViewById(R.id.game_start);
        change_stop = (ImageButton) findViewById(R.id.game_stop);
        change_quit = (ImageButton) findViewById(R.id.game_quit);

        saved = PreferenceManager.getDefaultSharedPreferences(this);
        boolean playMusic = saved.getBoolean("ifon" ,true); //获取背景音乐开关的状态
        if(playMusic) {
            Intent intent_service = new Intent(GameActivity.this, MusicService.class);
            startService(intent_service);
        }
        SnakeView.mMoveDelay=saved.getInt("nandu",500);

        //判断是否有保存数据，没有的话就重新开始游戏
        if (savedInstanceState == null) {
            mSnakeView.setMode(SnakeView.READY);
        } else {
            //暂停后的恢复
            Bundle map = savedInstanceState.getBundle(ICICLE_KEY);
            if (map != null) {
                mSnakeView.restoreState(map);
            } else {
                mSnakeView.setMode(SnakeView.PAUSE);
            }
        }

        mDown.setOnClickListener(this);
        mUp.setOnClickListener(this);
        mRight.setOnClickListener(this);
        mLeft.setOnClickListener(this);
        change_start.setOnClickListener(this);
        change_stop.setOnClickListener(this);
        change_quit.setOnClickListener(this);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();

        saved = PreferenceManager.getDefaultSharedPreferences(this);
        boolean playMusic = saved.getBoolean("ifon" ,true);
        if(playMusic) {
            Intent intent_service = new Intent(GameActivity.this, MusicService.class);
            stopService(intent_service);
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.game_start:
                if ( mSnakeView.mMode == SnakeView.READY || mSnakeView.mMode == SnakeView.LOSE) {
                    SnakeView.mMoveDelay=saved.getInt("nandu",500);
                    mSnakeView.initNewGame();
                    mSnakeView.setMode(SnakeView.RUNNING);
                    mSnakeView.update();
                }
                if ( mSnakeView.mMode == SnakeView.PAUSE) {
                    mSnakeView.setMode(SnakeView.RUNNING);
                    mSnakeView.update();
                }
                break;
            case R.id.game_stop:
                if(mSnakeView.mMode == SnakeView.RUNNING) {
                    mSnakeView.setMode(SnakeView.PAUSE);
                }
                break;
            case R.id.game_quit:
                mSnakeView.setMode(SnakeView.QUIT);
                finish();
                break;
            case R.id.left:
                if (SnakeView.mDirection != RIGHT) {
                    SnakeView.mNextDirection = LEFT;
                }
                break;
            case R.id.right:
                if (SnakeView.mDirection != LEFT) {
                    SnakeView.mNextDirection = RIGHT;
                }
                break;
            case R.id.up:
                if (SnakeView.mDirection != DOWN) {
                    SnakeView.mNextDirection = UP;
                }
                break;
            case R.id.down:
                if (SnakeView.mDirection != UP) {
                    SnakeView.mNextDirection = DOWN;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSnakeView.setMode(SnakeView.PAUSE);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //保存游戏状态
        outState.putBundle(ICICLE_KEY, mSnakeView.saveState());
    }
}
