package com.company.question.questionapplication.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.company.question.questionapplication.Bean.CollectInfo;
import com.company.question.questionapplication.Bean.SubmitErrorInfo;
import com.company.question.questionapplication.Fragment.CollectFragment;
import com.company.question.questionapplication.Fragment.ErrorFragment;
import com.company.question.questionapplication.R;

import java.util.HashMap;
import java.util.List;


public class Vp_adapter_collect extends FragmentStatePagerAdapter {


    Context context;
    HashMap<Integer,Fragment> fragmentHashMap;

    static List<CollectInfo> collectInfoList;
    private final String[] questionInfo;

    public Vp_adapter_collect(FragmentManager fm, List<CollectInfo> collectInfoList, Context context) {
        super(fm);
        this.collectInfoList=collectInfoList;
        this.context=context;
        questionInfo = this.context.getResources().getStringArray(R.array.QuestionInfo);
        fragmentHashMap=new HashMap<Integer, Fragment>();
    }

    @Override
    public Fragment getItem(int position) {
        CollectFragment fragment=new CollectFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("position",position);
        bundle.putString(questionInfo[0],collectInfoList.get(position).getQuestion());
        bundle.putString(questionInfo[1],collectInfoList.get(position).getOptionA());
        bundle.putString(questionInfo[2],collectInfoList.get(position).getOptionB());
        bundle.putString(questionInfo[3],collectInfoList.get(position).getOptionC());
        bundle.putString(questionInfo[4],collectInfoList.get(position).getOptionD());
        bundle.putInt(questionInfo[5],collectInfoList.get(position).getAnswer());
        bundle.putString(questionInfo[6],collectInfoList.get(position).getExplain());
        bundle.putString("fromTable",collectInfoList.get(position).getFromTable());
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
        return collectInfoList.size();
    }

    public Fragment getFragment(int position){
        return fragmentHashMap.get(position);
    }

    public int getAnswer(int position){
        return collectInfoList.get(position).getAnswer();
    }
}
