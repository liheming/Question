package com.company.question.questionapplication.Db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.company.question.questionapplication.Bean.ChoiceInfo;
import com.company.question.questionapplication.Bean.ErrorListInfo;
import com.company.question.questionapplication.Bean.QuestionInfo;
import com.company.question.questionapplication.Bean.SubmitErrorInfo;
import com.company.question.questionapplication.R;

import java.util.ArrayList;
import java.util.List;


public class ExerciseDatabaseDao {

    String TABLE_NAME="MyQuestion";
    Context context;
    public ExerciseDatabaseDao(Context context) {
        this.context=context;
    }
    public SQLiteDatabase openDb(){
        SQLiteDatabase db;
        db=SQLiteDatabase.openDatabase("/data/data/com.company.question.questionapplication/databases/test.db",null,SQLiteDatabase.OPEN_READWRITE);
        return db;
    }
    private void closeDb(SQLiteDatabase db){
        db.close();
    }

    public List<QuestionInfo> getQuestionList(String tableName){
        SQLiteDatabase db=openDb();
        List<QuestionInfo> questionInfoList=new ArrayList<QuestionInfo>();
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
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
        closeDb(db);
        return questionInfoList;
    }
    public List<SubmitErrorInfo> getErrorList(){
        SQLiteDatabase db=openDb();
        List<SubmitErrorInfo> questionInfoList=new ArrayList<SubmitErrorInfo>();
        Cursor cursor = db.query("Error", null, null, null, null, null, null);
        String[] questionInfo=context.getResources().getStringArray(R.array.QuestionInfo);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            SubmitErrorInfo info=new SubmitErrorInfo();
            String question = cursor.getString(cursor.getColumnIndex(questionInfo[0]));
            String optionA = cursor.getString(cursor.getColumnIndex(questionInfo[1]));
            String optionB = cursor.getString(cursor.getColumnIndex(questionInfo[2]));
            String optionC = cursor.getString(cursor.getColumnIndex(questionInfo[3]));
            String optionD = cursor.getString(cursor.getColumnIndex(questionInfo[4]));
            int answer = cursor.getInt(cursor.getColumnIndex(questionInfo[5]));
            String explain = cursor.getString(cursor.getColumnIndex(questionInfo[6]));
            int position = cursor.getInt(cursor.getColumnIndex("position"));
            int Checked = cursor.getInt(cursor.getColumnIndex("Checked"));
            info.setQuestion(question);
            info.setOptionA(optionA);
            info.setOptionB(optionB);
            info.setOptionC(optionC);
            info.setOptionD(optionD);
            info.setAnswer(answer);
            info.setExplain(explain);
            info.setPosition(position);
            info.setChecked(Checked);
            questionInfoList.add(info);
        }
        closeDb(db);
        return questionInfoList;
    }

    public List<ErrorListInfo> getErrorListInfos(){
        SQLiteDatabase db=openDb();
        List<ErrorListInfo> ErrorInfoList=new ArrayList<ErrorListInfo>();
        Cursor cursor = db.query("Error", null, null, null, null, null, null);
        ErrorListInfo info;
        while(cursor.moveToNext()){
            info=new ErrorListInfo();
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String question = cursor.getString(cursor.getColumnIndex("Question"));
            int answer = cursor.getInt(cursor.getColumnIndex("Answer"));
            String answerString=null;
            switch (answer){
                case 1:
                    answerString= cursor.getString(cursor.getColumnIndex("OptionA"));
                    break;
                case 2:
                    answerString=cursor.getString(cursor.getColumnIndex("OptionB"));
                    break;
                case 3:
                    answerString=cursor.getString(cursor.getColumnIndex("OptionC"));
                    break;
                case 4:
                    answerString=cursor.getString(cursor.getColumnIndex("OptionD"));
                    break;
            }
            info.setPosition(id);
            info.setQuestion(question);
            info.setAnswer(answerString);
            ErrorInfoList.add(info);
        }
        return ErrorInfoList;
    }

    public void createCollectTable(){
        SQLiteDatabase db = openDb();
        db.execSQL("create table if not exists Collect(_id integer primary key autoincrement,position integer,fromTable varchar(20)," +
                "Question varchar(50),OptionA varchar(20),OptionB varchar(20),OptionC varchar(20),OptionD varchar(20)," +
                "Answer integer,Explains varchar(20))");
        closeDb(db);
    }
    public void createErrorTable(){
        SQLiteDatabase db = openDb();
        db.execSQL("create table if not exists Error(_id integer primary key autoincrement,position integer,fromTable varchar(20)," +
                "Question varchar(50),OptionA varchar(20),OptionB varchar(20),OptionC varchar(20),OptionD varchar(20)," +
                "Answer integer,Explains varchar(20),Checked integer)");
        closeDb(db);
    }
    public boolean addErrors(List<SubmitErrorInfo> submitList,String fromTable){
        SQLiteDatabase db = openDb();
        ContentValues values=new ContentValues();
        for (SubmitErrorInfo info:submitList){
            values.clear();
            int position = info.getPosition();
            int checked = info.getChecked();
            String question = info.getQuestion();
            String optionA = info.getOptionA();
            String optionB = info.getOptionB();
            String optionC = info.getOptionC();
            String optionD = info.getOptionD();
            int answer = info.getAnswer();
            String explain = info.getExplain();
            values.put("position",position);
            values.put("Checked",checked);
            values.put("Question",question);
            values.put("OptionA",optionA);
            values.put("OptionB",optionB);
            values.put("OptionC",optionC);
            values.put("OptionD",optionD);
            values.put("Answer",answer);
            values.put("Explains",explain);
            values.put("fromTable",fromTable);
            long error = db.insert("Error", null, values);
            Log.i("成功插入错题",error+"");
        }
        closeDb(db);
        return true;
    }
    public boolean deleteAllError(){
        SQLiteDatabase db = openDb();
        //清空表数据
        db.execSQL("DELETE FROM Error");
        //清空自增
        db.execSQL("UPDATE sqlite_sequence SET seq = 0 WHERE name='Error';");
        return true;
    }
    public boolean deleteError(int id){
        SQLiteDatabase db = openDb();
        int delete = db.delete("Error", "_id=?", new String[]{
                id + ""
        });
        if (delete!=-1){
            return true;
        }else{
            return false;
        }
    }

}
