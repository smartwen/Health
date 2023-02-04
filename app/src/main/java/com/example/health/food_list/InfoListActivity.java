package com.example.health.food_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.health.R;
import com.example.health.bean.FoodBean;
import com.example.health.bean.FoodUtils;
import com.example.health.food_grid.FoodDescActivity;

import java.util.ArrayList;
import java.util.List;

public class InfoListActivity extends AppCompatActivity implements View.OnClickListener {
    EditText searchEt;
    ImageView searchIv,flushIv;
    ListView showLv;
    //1. ListView内部数据根源
    List<FoodBean> mDatas;
    List<FoodBean> allFoodList;
    private InfoListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_list);
        initView();
        //2.找到listview 对应的数据源
        mDatas = new ArrayList<>();
        allFoodList = FoodUtils.getAllFoodList();
        mDatas.addAll(allFoodList);

        //3.创建适配器 BaseAdapter的子类
        adapter = new InfoListAdapter(this,mDatas);
        showLv.setAdapter(adapter);//4.设置适配器内容
    }

    private void initView() {
        searchEt = findViewById(R.id.info_et_search);
        searchIv = findViewById(R.id.info_iv_search);
        flushIv = findViewById(R.id.info_iv_flush);
        showLv = findViewById(R.id.infolist_lv);

        flushIv.setOnClickListener(this);
        searchIv.setOnClickListener(this);

        //设置单向点击监听功能
        setListener();
    }

    private void setListener() {
        showLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FoodBean foodBean = mDatas.get(position);
                Intent intent = new Intent(InfoListActivity.this, FoodDescActivity.class);
                intent.putExtra("food",foodBean);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_iv_flush:
                searchEt.setText("");
                mDatas.clear();
                mDatas.addAll(allFoodList);
                adapter.notifyDataSetChanged();

                break;

            case R.id.info_iv_search:
                String msg = searchEt.getText().toString().trim();
                if (TextUtils.isEmpty(msg)) {
                    Toast.makeText(this,"输入内容不能为空！",Toast.LENGTH_SHORT).show();
                }
                //将食物列表标题 进行判断是否包含有输入内容，如果包含则添加导小集合中
                List<FoodBean> list = new ArrayList<>();
                for(int i=0;i<allFoodList.size();i++){
                    String title = allFoodList.get(i).getTitle();
                    if (title.contains(msg)) {
                        list.add(allFoodList.get(i));
                    }
                    mDatas.clear();
                    mDatas.addAll(list);//添加新的数据源
                    adapter.notifyDataSetChanged();//提示适配器更新

                }
                break;
        }
    }
}