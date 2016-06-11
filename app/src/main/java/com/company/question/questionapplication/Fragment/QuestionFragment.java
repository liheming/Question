package com.company.question.questionapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.company.question.questionapplication.R;

public class QuestionFragment extends android.support.v4.app.Fragment{

    private View questionView;
    private TextView question;
    private RadioButton optionA;
    private RadioButton optionB;
    private RadioButton optionC;
    private RadioButton optionD;
    private TextView explain;
    private Bundle args;
    Context context;
    private RadioGroup optionGroup;
    private RadioGroup optionGroup1;
    private int answer;
    private Intent intent;
    private int position;

    public void setArguments(Bundle args,Context context) {
        super.setArguments(args);
        this.args=args;
        this.context=context;
        position=args.getInt("position");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String[] questionInfo=context.getResources().getStringArray(R.array.QuestionInfo);
        questionView = inflater.inflate(R.layout.vp_exercise,container,false);
        question = (TextView) questionView.findViewById(R.id.question);
        optionGroup = (RadioGroup) questionView.findViewById(R.id.optionGroup);
        optionA = (RadioButton) questionView.findViewById(R.id.optionA);
        optionB = (RadioButton) questionView.findViewById(R.id.optionB);
        optionC = (RadioButton) questionView.findViewById(R.id.optionC);
        optionD = (RadioButton) questionView.findViewById(R.id.optionD);
        explain = (TextView) questionView.findViewById(R.id.explain);
        optionGroup1 = (RadioGroup) questionView.findViewById(R.id.optionGroup);
        question.setText(args.getString(questionInfo[0]));
        optionA.setText(args.getString(questionInfo[1]));
        optionB.setText(args.getString(questionInfo[2]));
        optionC.setText(args.getString(questionInfo[3]));
        optionD.setText(args.getString(questionInfo[4]));
        explain.setText(args.getString(questionInfo[6]));
        answer = args.getInt(questionInfo[5]);
        optionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checked=-1;
                boolean isTrue;
                switch (checkedId){
                    case R.id.optionA:
                        checked=1;
                        break;
                    case R.id.optionB:
                        checked=2;
                        break;
                    case R.id.optionC:
                        checked=3;
                        break;
                    case R.id.optionD:
                        checked=4;
                        break;
                }
                if (checked==answer){
                    isTrue=true;
                }else{
                    isTrue=false;
                }
                intent = new Intent();
                intent.setAction("com.question.choice");
                intent.putExtra("position",position);
                intent.putExtra("checked",checked);
                intent.putExtra("answer",answer);
                intent.putExtra("isTrue",isTrue);
                context.sendBroadcast(intent);
            }
        });
        return questionView;
    }


}