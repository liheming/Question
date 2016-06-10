package com.company.question.questionapplication.Fragment;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
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

    public void setArguments(Bundle args,Context context) {
        super.setArguments(args);
        this.args=args;
        this.context=context;
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
        optionA = (RadioButton) questionView.findViewById(R.id.optionA);
        optionB = (RadioButton) questionView.findViewById(R.id.optionB);
        optionC = (RadioButton) questionView.findViewById(R.id.optionC);
        optionD = (RadioButton) questionView.findViewById(R.id.optionD);
        explain = (TextView) questionView.findViewById(R.id.explain);
        question.setText(args.getString(questionInfo[0]));
        optionA.setText(args.getString(questionInfo[1]));
        optionB.setText(args.getString(questionInfo[2]));
        optionC.setText(args.getString(questionInfo[3]));
        optionD.setText(args.getString(questionInfo[4]));
        explain.setText(args.getString(questionInfo[6]));
        return questionView;
    }


}