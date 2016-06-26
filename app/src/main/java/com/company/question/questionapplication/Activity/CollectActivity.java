package com.company.question.questionapplication.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.company.question.questionapplication.Adapter.Vp_adapter_collect;
import com.company.question.questionapplication.Adapter.Vp_adapter_error;
import com.company.question.questionapplication.Bean.CollectInfo;
import com.company.question.questionapplication.Bean.SubmitErrorInfo;
import com.company.question.questionapplication.Db.ExerciseDatabaseDao;
import com.company.question.questionapplication.R;

import java.util.List;

public class CollectActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ViewPager viewPager;
    private ExerciseDatabaseDao dao;
    private List<CollectInfo> collectInfoList;
    private Vp_adapter_collect adapter;
    private int position;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        intent = getIntent();
        position = intent.getExtras().getInt("position");
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        dao = new ExerciseDatabaseDao(this);
        collectInfoList = dao.getCollectlist();
        adapter = new Vp_adapter_collect(getSupportFragmentManager(),collectInfoList,this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}
