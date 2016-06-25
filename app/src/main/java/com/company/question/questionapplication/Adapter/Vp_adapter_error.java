package com.company.question.questionapplication.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.company.question.questionapplication.Bean.QuestionInfo;
import com.company.question.questionapplication.Bean.SubmitErrorInfo;
import com.company.question.questionapplication.Fragment.ErrorFragment;
import com.company.question.questionapplication.Fragment.QuestionFragment;
import com.company.question.questionapplication.R;

import java.util.HashMap;
import java.util.List;


public class Vp_adapter_error extends FragmentStatePagerAdapter {


    Context context;
    HashMap<Integer,Fragment> fragmentHashMap;

    static List<SubmitErrorInfo> errorInfoList;
    private final String[] questionInfo;

    public Vp_adapter_error(FragmentManager fm, List<SubmitErrorInfo> submitErrorInfoList, Context context) {
        super(fm);
        this.errorInfoList=submitErrorInfoList;
        this.context=context;
        questionInfo = this.context.getResources().getStringArray(R.array.QuestionInfo);
        fragmentHashMap=new HashMap<Integer, Fragment>();
    }

    @Override
    public Fragment getItem(int position) {
        ErrorFragment fragment=new ErrorFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("position",position);
        bundle.putString(questionInfo[0],errorInfoList.get(position).getQuestion());
        bundle.putString(questionInfo[1],errorInfoList.get(position).getOptionA());
        bundle.putString(questionInfo[2],errorInfoList.get(position).getOptionB());
        bundle.putString(questionInfo[3],errorInfoList.get(position).getOptionC());
        bundle.putString(questionInfo[4],errorInfoList.get(position).getOptionD());
        bundle.putInt(questionInfo[5],errorInfoList.get(position).getAnswer());
        bundle.putString(questionInfo[6],errorInfoList.get(position).getExplain());
        bundle.putInt("choice",errorInfoList.get(position).getChecked());
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
        return errorInfoList.size();
    }

    public Fragment getFragment(int position){
        return fragmentHashMap.get(position);
    }

    public int getAnswer(int position){
        return errorInfoList.get(position).getAnswer();
    }
}
