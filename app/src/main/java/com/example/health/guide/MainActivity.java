package com.example.health.guide;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.example.health.HomeMenuActivity;
import com.example.health.R;

public class MainActivity extends AppCompatActivity {
//健康饮食主界面经过倒计时5秒进入菜单引导界面 和引导界面判断在何时显示
    TextView tv;
    int time=5;
    SharedPreferences preferences;//存储键值对数据
    private SharedPreferences.Editor editor;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            if (msg.what == 1){
                time--;

                if (time == 0){
                    Intent intent =new Intent();

                    boolean isFirst = preferences.getBoolean("isFirst",true);
                    if (isFirst){
                        intent.setClass(MainActivity.this, GuideActivity.class);
                        editor.putBoolean("isFirst",false);//写入不是第一次进入的记录
                        editor.commit();//提交本次修改
                    }else{
                        intent.setClass(MainActivity.this, HomeMenuActivity.class);

                    }
                    //跳转
                    startActivity(intent);
                    finish();
                }else{
                    tv.setText(time+"");
                    handler.sendEmptyMessageDelayed(1,1000);
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.main_tv);
        handler.sendEmptyMessageDelayed(1,1000);

        preferences = getSharedPreferences("health_pref",MODE_PRIVATE);
         editor = preferences.edit();

    }
}