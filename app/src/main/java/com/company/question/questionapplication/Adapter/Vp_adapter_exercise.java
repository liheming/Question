package com.company.question.questionapplication.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.company.question.questionapplication.Bean.QuestionInfo;
import com.company.question.questionapplication.Fragment.QuestionFragment;
import com.company.question.questionapplication.R;

import java.util.List;


public class Vp_adapter_exercise extends FragmentStatePagerAdapter {


    Context context;

    static List<QuestionInfo> questionInfoList;
    private final String[] questionInfo;

    public Vp_adapter_exercise(FragmentManager fm, List<QuestionInfo> questionInfoList,Context context) {
        super(fm);
        this.questionInfoList=questionInfoList;
        this.context=context;
        questionInfo = this.context.getResources().getStringArray(R.array.QuestionInfo);
    }

    @Override
    public Fragment getItem(int position) {
        QuestionFragment fragment=new QuestionFragment();
        Bundle bundle=new Bundle();
        bundle.putString(questionInfo[0],questionInfoList.get(position).getQuestion());
        bundle.putString(questionInfo[1],questionInfoList.get(position).getOptionA());
        bundle.putString(questionInfo[2],questionInfoList.get(position).getOptionB());
        bundle.putString(questionInfo[3],questionInfoList.get(position).getOptionC());
        bundle.putString(questionInfo[4],questionInfoList.get(position).getOptionD());
        bundle.putString(questionInfo[6],questionInfoList.get(position).getExplain());
        fragment.setArguments(bundle,context);
        return fragment;
    }

    @Override
    public int getCount() {
        return questionInfoList.size();
    }

}
