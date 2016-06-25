package com.company.question.questionapplication.Activity;


import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.company.question.questionapplication.Adapter.Vp_adapter_exercise;
import com.company.question.questionapplication.Bean.ChoiceInfo;
import com.company.question.questionapplication.Bean.QuestionInfo;
import com.company.question.questionapplication.Bean.SubmitErrorInfo;
import com.company.question.questionapplication.Db.ExerciseDatabaseDao;
import com.company.question.questionapplication.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {
    private String TableName;
    private Toolbar toolbar;
    private Toolbar.OnMenuItemClickListener onMenuItemClickListener;
    private ViewPager viewPager;
    private List<QuestionInfo> questionInfoList;
    private ExerciseDatabaseDao database;
    private Vp_adapter_exercise adapter;
    private ChoiceReceiver choiceReceiver;
    private HashMap<Integer,ChoiceInfo> choiceList;
    private Button bt_finish;
//    private int scrollState;
//    private int lastPixels;
//    //左划，下一页：1
//    //右划，上一页：2
//    private int moveState;
    private Button bt_answer;
    private AlertDialog alertDialog;
    private Button bt_collect;
    //    Handler handler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what){
//                case 1:
//                    alertDialog.dismiss();
//                    Toast.makeText(ExerciseActivity.this, "提交错题成功", Toast.LENGTH_SHORT).show();
//            }
//        }
//    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        TableName=getIntent().getExtras().getString("tableName");
        toolbar = (Toolbar) findViewById(R.id.toolbar_exercise);
        setSupportActionBar(toolbar);
        initToolBar();
        bt_finish = (Button) findViewById(R.id.bt_finish);
        bt_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishExercise();
            }
        });
        bt_answer = (Button) findViewById(R.id.bt_answer);
        bt_collect = (Button) findViewById(R.id.bt_collect);

        database = new ExerciseDatabaseDao(this);
        //要获取那一套题
        questionInfoList = database.getQuestionList("MyQuestion");
        adapter = new Vp_adapter_exercise(getSupportFragmentManager(),questionInfoList,this);
        viewPager = (ViewPager) findViewById(R.id.vp_exercise);
        viewPager.setAdapter(adapter);
        bt_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewPager.getCurrentItem();
                View CurrentView = adapter.getFragment(position).getView();
                TextView explain = (TextView) CurrentView.findViewById(R.id.explain);
//                int checkedId=-1;
//                switch (adapter.getAnswer(position)){
//                    case 1:
//                        checkedId=R.id.optionA;
//                        break;
//                    case 2:
//                        checkedId=R.id.optionB;
//                        break;
//                    case 3:
//                        checkedId=R.id.optionC;
//                        break;
//                    case 4:
//                        checkedId=R.id.optionD;
//                        break;
//                }
//                RadioGroup optionGroup = (RadioGroup) CurrentView.findViewById(R.id.optionGroup);
                if(explain.getVisibility()==View.INVISIBLE){
                    explain.setVisibility(View.VISIBLE);
//                    if (checkedId!=-1){
//                        optionGroup.check(checkedId);
//                    }
                }else if(explain.getVisibility()==View.VISIBLE){
                    explain.setVisibility(View.INVISIBLE);
//                    optionGroup.clearCheck();
                }
            }
        });
        choiceList=new HashMap<Integer,ChoiceInfo>();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.i("onPageScrolled", "position: "+position);
//                Log.i("onPageScrolled", "positionOffset: "+positionOffset);
//                Log.i("onPageScrolled", "positionOffsetPixels: "+positionOffsetPixels);
//                int offset=0;
//                if (scrollState==1){
//                    offset = positionOffsetPixels - lastPixels;
//                    lastPixels=positionOffsetPixels;
//                }else if(scrollState==0){
//                    lastPixels=0;
//                }
//                if (offset>0){
//                    Log.i("滑动","左划，下一页");
//                    moveState=1;
//                }else if(offset<0){
//                    Log.i("滑动","右划，上一页");
//                    moveState=2;
//                }

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    return;
                }
                int newPosition = position - 1;
                Fragment fragment = adapter.getFragment(newPosition);
                if (fragment==null){
                    return;
                }
                RadioGroup optionGroup = (RadioGroup) fragment.getView().findViewById(R.id.optionGroup);
                int checkedId = optionGroup.getCheckedRadioButtonId();
                if (checkedId == -1) {
                    Intent intent = new Intent();
                    intent.setAction("com.question.choice");
                    intent.putExtra("position", newPosition);
                    intent.putExtra("checked", -1);
                    intent.putExtra("answer", adapter.getAnswer(newPosition));
                    intent.putExtra("isTrue", false);
                    ExerciseActivity.this.sendBroadcast(intent);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.i("onPageScrolled","state:"+state);
//                scrollState=state;
            }
        });

        //绑定监听
        choiceReceiver = new ChoiceReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.question.choice");
        intentFilter.addAction("com.question.submit");
        registerReceiver(choiceReceiver,intentFilter);

        bt_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = viewPager.getCurrentItem();
                QuestionInfo info = questionInfoList.get(currentItem);
                String question = info.getQuestion();
                String optionA = info.getOptionA();
                String optionB = info.getOptionB();
                String optionC = info.getOptionC();
                String optionD = info.getOptionD();
                String explain = info.getExplain();
                int answer = info.getAnswer();
                ContentValues values=new ContentValues();
                values.put("position",currentItem);
                values.put("fromTable",TableName);
                values.put("Question",question);
                values.put("OptionA",optionA);
                values.put("OptionB",optionB);
                values.put("OptionC",optionC);
                values.put("OptionD",optionD);
                values.put("Answer",answer);
                values.put("Explains",explain);
                boolean b = database.addCollect(values);
                if (b){
                    Toast.makeText(ExerciseActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ExerciseActivity.this, "收藏失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(choiceReceiver);
    }

    private void initToolBar(){
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

    class ChoiceReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("com.question.choice")) {
                Bundle extras = intent.getExtras();
                int position = extras.getInt("position");
                int checked = extras.getInt("checked");
                int answer = extras.getInt("answer");
                boolean isTrue = extras.getBoolean("isTrue");
                ChoiceInfo info = new ChoiceInfo();
                info.setPosition(position);
                info.setChecked(checked);
                info.setAnswer(answer);
                info.setTrue(isTrue);
                choiceList.put(position, info);
                Log.i("onReceive: ", info.toString());
                if (position < questionInfoList.size() - 1 && checked != -1) {
                    viewPager.setCurrentItem(position + 1);
                } else if (position == questionInfoList.size() - 1) {
                    finishExercise();
                }
            }else if(action.equals("com.question.submit")){

            }
        }
    }
        private void finishExercise(){
            int noFinishSize = questionInfoList.size() - choiceList.size();
            Collection<ChoiceInfo> c = choiceList.values();
            Iterator iterator = c.iterator();
            while(iterator.hasNext()) {
                ChoiceInfo info = (ChoiceInfo) iterator.next();
                if (info.getChecked()==-1){
                    noFinishSize++;
                }
            }
        if (noFinishSize>0){
            AlertDialog.Builder builder=new AlertDialog.Builder(ExerciseActivity.this);
            builder.setTitle("提示");
            builder.setMessage("还有"+noFinishSize+"道题目没做确定交卷？");
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    getResult();
                }
            });
            builder.create().show();
        }else if (noFinishSize==0){

        }else{
            //出错了
        }
    }

    private void getResult(){
        int noFinishSize = questionInfoList.size() - choiceList.size();
        int noTrue=0;
        int doTrue=0;
        Collection<ChoiceInfo> c = choiceList.values();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()) {
            ChoiceInfo info = (ChoiceInfo) iterator.next();
            if (info.getChecked()==-1){
                noFinishSize++;
            }
            if (info.isTrue()==false){
                noTrue++;
            }
            if (info.isTrue()==true){
                doTrue++;
            }
        }
        AlertDialog.Builder builder=new AlertDialog.Builder(ExerciseActivity.this);
        View dialogView = View.inflate(ExerciseActivity.this, R.layout.dialog_exercise_result,null);
        TextView tv_true = (TextView) dialogView.findViewById(R.id.tv_true);
        TextView tv_no_finish = (TextView) dialogView.findViewById(R.id.tv_no_finish);
        TextView tv_error = (TextView) dialogView.findViewById(R.id.tv_error);
        tv_true.setText("共答对"+doTrue+"道题");
        tv_no_finish.setText("未作答"+noFinishSize+"道题");
        tv_error.setText("答错"+noTrue+"道题");
        builder.setView(dialogView);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
                submitError();
            }
        });
        alertDialog = builder.create();
        alertDialog.show();
    }

    private void submitError(){
//        AlertDialog.Builder builder=new AlertDialog.Builder(ExerciseActivity.this);
//        View view=View.inflate(ExerciseActivity.this,R.layout.dialog_submit_error,null);
//        builder.setView(view);
//        submitDialog = builder.create();
//        submitDialog.show();
        Toast.makeText(ExerciseActivity.this, "正在提交错题", Toast.LENGTH_SHORT).show();
        List<Integer> errorList =new ArrayList<Integer>();
        List<SubmitErrorInfo> submitList=new ArrayList<SubmitErrorInfo>();
        Collection<ChoiceInfo> c = choiceList.values();
        Iterator iterator = c.iterator();
        while(iterator.hasNext()) {
            ChoiceInfo info = (ChoiceInfo) iterator.next();
            if(!info.isTrue()&&info.getChecked()!=-1){
                errorList.add(info.getPosition());
            }
        }
        for (int i = 0; i < questionInfoList.size(); i++) {
            for (Integer e:errorList) {
                if (e==i){
                    int checked = choiceList.get(e).getChecked();
                    QuestionInfo info = questionInfoList.get(e);
                    String question = info.getQuestion();
                    String optionA = info.getOptionA();
                    String optionB = info.getOptionB();
                    String optionC = info.getOptionC();
                    String optionD = info.getOptionD();
                    String explain= info.getExplain();
                    int answer = info.getAnswer();
                    SubmitErrorInfo submitErrorInfo=new SubmitErrorInfo();
                    submitErrorInfo.setPosition(e);
                    submitErrorInfo.setChecked(checked);
                    submitErrorInfo.setQuestion(question);
                    submitErrorInfo.setAnswer(answer);
                    submitErrorInfo.setOptionA(optionA);
                    submitErrorInfo.setOptionB(optionB);
                    submitErrorInfo.setOptionC(optionC);
                    submitErrorInfo.setOptionD(optionD);
                    submitErrorInfo.setExplain(explain);
                    submitList.add(submitErrorInfo);
                }
            }
            if (submitList.size()==errorList.size()){
                break;
            }
        }
        database.addErrors(submitList,TableName);
//        boolean b = database.addErrors(submitList, TableName);
//        if (b){
//            Message msg=handler.obtainMessage();
//            msg.what=1;
//            handler.sendMessage(msg);
//        }
        Toast.makeText(ExerciseActivity.this, "错题提交完成", Toast.LENGTH_SHORT).show();
        finish();
    }

}
