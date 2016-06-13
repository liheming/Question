package com.company.question.questionapplication.Adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.company.question.questionapplication.Bean.ErrorListInfo;
import com.company.question.questionapplication.R;

import java.util.List;


public class ErrorListAdapter extends BaseAdapter{
    Context context;
    List<ErrorListInfo> list;
    public ErrorListAdapter(Context context, List<ErrorListInfo> list) {
        this.context=context;
        this.list=list;
    }

    public int getSize(){
        return list.size();
    }

    public void delete(int position){
        list.remove(position);
    }

    public void deleteAll(){
        list.clear();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position).getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ErrorViewHolder holder;
        if (convertView==null){
            holder=new ErrorViewHolder();
            convertView=View.inflate(context, R.layout.adapter_errorlist,null);
            holder.question= (TextView) convertView.findViewById(R.id.question);
            holder.answer= (TextView) convertView.findViewById(R.id.answer);
            convertView.setTag(holder);
        }else{
            holder= (ErrorViewHolder) convertView.getTag();
        }
        holder.question.setText((position+1)+"."+list.get(position).getQuestion());
        holder.answer.setText("答案："+list.get(position).getAnswer());
        return convertView;
    }

}
