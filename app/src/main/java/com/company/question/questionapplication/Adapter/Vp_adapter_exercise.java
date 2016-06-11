package com.company.question.questionapplication.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.company.question.questionapplication.Bean.QuestionInfo;
import com.company.question.questionapplication.Fragment.QuestionFragment;
import com.company.question.questionapplication.R;

import java.util.HashMap;
import java.util.List;


public class Vp_adapter_exercise extends FragmentStatePagerAdapter {


    Context context;
    HashMap<Integer,Fragment> fragmentHashMap;

    static List<QuestionInfo> questionInfoList;
    private final String[] questionInfo;

    public Vp_adapter_exercise(FragmentManager fm, List<QuestionInfo> questionInfoList,Context context) {
        super(fm);
        this.questionInfoList=questionInfoList;
        this.context=context;
        questionInfo = this.context.getResources().getStringArray(R.array.QuestionInfo);
        fragmentHashMap=new HashMap<Integer, Fragment>();
    }

    @Override
    public Fragment getItem(int position) {
        QuestionFragment fragment=new QuestionFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("position",position);
        bundle.putString(questionInfo[0],questionInfoList.get(position).getQuestion());
        bundle.putString(questionInfo[1],questionInfoList.get(position).getOptionA());
        bundle.putString(questionInfo[2],questionInfoList.get(position).getOptionB());
        bundle.putString(questionInfo[3],questionInfoList.get(position).getOptionC());
        bundle.putString(questionInfo[4],questionInfoList.get(position).getOptionD());
        bundle.putInt(questionInfo[5],questionInfoList.get(position).getAnswer());
        bundle.putString(questionInfo[6],questionInfoList.get(position).getExplain());
        fragment.setArguments(bundle,context);
        fragmentHashMap.put(position,fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        fragmentHashMap.remove(position);
    }

    @Override
    public int getCount() {
        return questionInfoList.size();
    }

    public Fragment getFragment(int position){
        return fragmentHashMap.get(position);
    }

    public int getAnswer(int position){
        return questionInfoList.get(position).getAnswer();
    }
}
