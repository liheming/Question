package com.company.question.questionapplication.Db;

import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.company.question.questionapplication.Bean.QuestionInfo;
import com.company.question.questionapplication.R;

import java.util.ArrayList;
import java.util.List;


public class ExerciseDatabaseDao {
    private SQLiteDatabase db;
    String TABLE_NAME="MyQuestion";
    Context context;
    public ExerciseDatabaseDao(Context context) {
        db=SQLiteDatabase.openDatabase("/data/data/com.company.question.questionapplication/databases/test.db",null,SQLiteDatabase.OPEN_READWRITE);
        this.context=context;
    }

    public List<QuestionInfo> getQuestionList(){
        List<QuestionInfo> questionInfoList=new ArrayList<QuestionInfo>();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        String[] questionInfo=context.getResources().getStringArray(R.array.QuestionInfo);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            QuestionInfo info=new QuestionInfo();
            String question = cursor.getString(cursor.getColumnIndex(questionInfo[0]));
            String optionA = cursor.getString(cursor.getColumnIndex(questionInfo[1]));
            String optionB = cursor.getString(cursor.getColumnIndex(questionInfo[2]));
            String optionC = cursor.getString(cursor.getColumnIndex(questionInfo[3]));
            String optionD = cursor.getString(cursor.getColumnIndex(questionInfo[4]));
            int answer = cursor.getInt(cursor.getColumnIndex(questionInfo[5]));
            String explain = cursor.getString(cursor.getColumnIndex(questionInfo[6]));
            info.setQuestion(question);
            info.setOptionA(optionA);
            info.setOptionB(optionB);
            info.setOptionC(optionC);
            info.setOptionD(optionD);
            info.setAnswer(answer);
            info.setExplain(explain);
            questionInfoList.add(info);
        }
        return questionInfoList;
    }
}
