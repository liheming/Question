package com.company.question.questionapplication.Activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.question.questionapplication.Adapter.ErrorListAdapter;
import com.company.question.questionapplication.Bean.ErrorListInfo;
import com.company.question.questionapplication.Db.ExerciseDatabaseDao;
import com.company.question.questionapplication.R;

import java.util.List;

public class ErrorListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private List<ErrorListInfo> list;
    private ExerciseDatabaseDao dao;
    private TextView error_size;
    private ErrorListAdapter adapter;
    private int positionId;
    private Button deleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.listView);
        error_size = (TextView) findViewById(R.id.error_size);
        deleteAll = (Button) findViewById(R.id.deleteAll);
        dao = new ExerciseDatabaseDao(this);
        list = dao.getErrorListInfos();
        adapter = new ErrorListAdapter(ErrorListActivity.this, list);
        listView.setAdapter(adapter);
        updateSize();
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                positionId = position;
                AlertDialog.Builder builder=new AlertDialog.Builder(ErrorListActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确定删除？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int DbId = (int) listView.getItemAtPosition(positionId);
                        boolean DbDelete = dao.deleteError(DbId);
                        adapter.delete(positionId);
                        adapter.notifyDataSetChanged();
                        updateSize();
                        if (DbDelete){
                            Toast.makeText(ErrorListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
                return true;
            }
        });
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean DbDelete = dao.deleteAllError();
                adapter.deleteAll();
                adapter.notifyDataSetChanged();
                updateSize();
                if (DbDelete){
                    Toast.makeText(ErrorListActivity.this, "清空成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ErrorListActivity.this, "清空失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updateSize(){
        error_size.setText("错题库数量为："+adapter.getSize());
    }
}
