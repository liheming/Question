package com.company.question.questionapplication.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.company.question.questionapplication.R;


public class ErrorFragment extends Fragment {
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
    private int answer;
    private int position;
    private String answerOption;
    private String choiceOption;
    private TextView error_choice;
    private int choice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setArguments(Bundle args,Context context) {
        super.setArguments(args);
        this.args=args;
        this.context=context;
        position=args.getInt("position");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String[] questionInfo=context.getResources().getStringArray(R.array.QuestionInfo);
        questionView = inflater.inflate(R.layout.vp_error,container,false);
        question = (TextView) questionView.findViewById(R.id.question);
        optionGroup = (RadioGroup) questionView.findViewById(R.id.optionGroup);
        optionA = (RadioButton) questionView.findViewById(R.id.optionA);
        optionB = (RadioButton) questionView.findViewById(R.id.optionB);
        optionC = (RadioButton) questionView.findViewById(R.id.optionC);
        optionD = (RadioButton) questionView.findViewById(R.id.optionD);
        explain = (TextView) questionView.findViewById(R.id.explain);
        error_choice = (TextView) questionView.findViewById(R.id.error_choice);
        optionGroup = (RadioGroup) questionView.findViewById(R.id.optionGroup);
        question.setText(position+"·"+args.getString(questionInfo[0]));
        optionA.setText(args.getString(questionInfo[1]));
        optionB.setText(args.getString(questionInfo[2]));
        optionC.setText(args.getString(questionInfo[3]));
        optionD.setText(args.getString(questionInfo[4]));
        choice=args.getInt("choice");
        switch (choice){
            case 1:
                choiceOption="A";
                break;
            case 2:
                choiceOption="B";
                break;
            case 3:
                choiceOption="C";
                break;
            case 4:
                choiceOption="D";
                break;
        }
        error_choice.setText("以往错选："+choiceOption);
        answer = args.getInt(questionInfo[5]);
        switch (answer){
            case 1:
                answerOption = "A";
                break;
            case 2:
                answerOption="B";
                break;
            case 3:
                answerOption="C";
                break;
            case 4:
                answerOption="D";
                break;
        }
        explain.setText("答案是："+answerOption+"\n"+args.getString(questionInfo[6]));
        optionGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                error_choice.setVisibility(View.VISIBLE);
                explain.setVisibility(View.VISIBLE);
            }
        });
        return questionView;
    }
}
