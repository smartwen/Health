package com.example.health;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener{
    ViewPager aboutvp;
    TextView shareTv;
    LinearLayout pointLayout;

    List<View> viewList;//ViewPager的数据源
    int[] picIds = {R.mipmap.ab1,R.mipmap.ab2,R.mipmap.ab3,R.mipmap.ab4,R.mipmap.ab5};
    private AboutAdapter adapter;
    List<ImageView> pointList;//存放显示器小点点的集合

    //实现定时播放功能
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            //和做火车一样，自将行李放到传送带上，自己取下
            if(msg.what == 1){
                //接受导消息，需要使viewPager页面乡下滑动一页
                int currentItem = aboutvp.getCurrentItem();
                aboutvp.setCurrentItem(currentItem+1);
                handler.sendEmptyMessageDelayed(1,5000);

            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        aboutvp = findViewById(R.id.about_vp);
        shareTv = findViewById(R.id.about_tv_share);
        pointLayout = findViewById(R.id.about_layout_point);
        shareTv.setOnClickListener(this);
        viewList = new ArrayList<>();
        pointList = new ArrayList<>();


        //初始化ViewPager的页面信息
        for(int i=0;i<picIds.length;i++){
            View view = LayoutInflater.from(this).inflate(R.layout.item_aboutvp,null);
            ImageView iv = view.findViewById(R.id.item_aboutvp_iv);
            iv.setImageResource(picIds[i]);
            viewList.add(view);
            //new 指示器内容
            ImageView pointIv = new ImageView(this);
            //在代码中设置控件的宽高和外边距等属性
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0,0,20,0);
            //将布局参数设置给imageView
            pointIv.setLayoutParams(lp);
            pointIv.setImageResource(R.mipmap.a2);
            pointList.add(pointIv);
            pointLayout.addView(pointIv);//添加导布局中显示出来
        }
        pointList.get(0).setImageResource(R.mipmap.a3);//设置第一个小圆点为选中状态



        //创建适配器对象
        adapter = new AboutAdapter(viewList);
        aboutvp.setAdapter(adapter);
        //发送切换页面消息
        handler.sendEmptyMessageDelayed(1,5000);

        //设置viewPager的监听器
        setVPListener();
    }

    private void setVPListener() {
        aboutvp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<pointList.size();i++){
                    pointList.get(i).setImageResource(R.mipmap.a2);
                }
                //只有固定position位置才改成红色
                pointList.get(position%pointList.size()).setImageResource(R.mipmap.a3);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v){
        //调用系统自带的分享功能
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        String msg = "健康饮食非常重要，了解饮食各种营养和热量，摄入正确的食物";
        intent.putExtra(Intent.EXTRA_TEXT,msg);
        startActivity(Intent.createChooser(intent,"健康饮食分享"));

    }
}