package com.company.question.questionapplication.Activity;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.company.question.questionapplication.Adapter.Vp_adapter_exercise;
import com.company.question.questionapplication.Bean.QuestionInfo;
import com.company.question.questionapplication.Db.ExerciseDatabaseDao;
import com.company.question.questionapplication.R;

import java.util.List;

public class ExerciseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener;
    private ViewPager viewPager;
    private List<QuestionInfo> questionInfoList;
    private ExerciseDatabaseDao database;
    private Vp_adapter_exercise adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        toolbar = (Toolbar) findViewById(R.id.toolbar_exercise);
        setSupportActionBar(toolbar);
        initToolBar();
        database = new ExerciseDatabaseDao(this);
        questionInfoList = database.getQuestionList();
        adapter = new Vp_adapter_exercise(getSupportFragmentManager(),questionInfoList,this);
        viewPager = (ViewPager) findViewById(R.id.vp_exercise);
        viewPager.setAdapter(adapter);

    }
    private void initToolBar(){
        toolbar.setTitle("xxx练习");
        toolbar.setNavigationIcon(R.drawable.title_return);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        //toolbar的点击事件
        onMenuItemClickListener = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){

                }
                return true;
            }
        };
        //toolbar的左导航点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
