package com.company.question.questionapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.company.question.questionapplication.Db.ExerciseDatabaseDao;
import com.company.question.questionapplication.R;
import com.company.question.questionapplication.bmob.LoginActivity;
import com.company.question.questionapplication.bmob.ResetPasswordActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout exercise;
    private String DB_PATH;
    private String DB_NAME;
    private TextView username_app_bar,username_nav_head;
    private LinearLayout myError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //提供以下两种方式进行初始化操作：

        //第一：默认初始化
        Bmob.initialize(this, "1fe47f6bb8ec6a3eb640c3617952b5a6");

        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);


        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        username_app_bar = (TextView) findViewById(R.id.username_app_bar);
        username_nav_head = (TextView) findViewById(R.id.username_nav_head);
        getCurrentUser(username_app_bar);
        setSupportActionBar(toolbar);
        //抽屉布局
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //左导航事件
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        exercise = (LinearLayout) findViewById(R.id.exercise);
        myError = (LinearLayout) findViewById(R.id.myError);

        //拷贝数据库
        DB_PATH = "/data/data/" + getPackageName() + "/databases/";
        DB_NAME = "test.db";
        copyDB();


        exercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ExerciseActivity.class);
                intent.putExtra("tableName","MyQuestion");
                startActivity(intent);
            }
        });
        myError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ErrorListActivity.class));
            }
        });

        ExerciseDatabaseDao dao=new ExerciseDatabaseDao(this);
        dao.createErrorTable();
        dao.createCollectTable();

    }

    private void copyDB() {
        File db = new File(DB_PATH + DB_NAME);
        if (!db.exists()) {
            File dir = new File(DB_PATH);
            if (!dir.exists()) {
                dir.mkdir();
            }
            try {
                InputStream is = getResources().openRawResource(R.raw.test);
                OutputStream os = new FileOutputStream(db);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
                os.flush();
                os.close();
                is.close();
                Log.i("copyDB", "创建数据库成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("copyDB", "数据库已经存在");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_update:

                break;
            case R.id.nav_about:

                break;
            case R.id.nav_login:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            Toast.makeText(MainActivity.this,"我是注册",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_login_out:
                BmobUser.logOut(MainActivity.this);   //清除缓存用户对象
                BmobUser currentUser = BmobUser.getCurrentUser(MainActivity.this);
                // 现在的currentUser是null了
                username_app_bar.setText("用户名");
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                break;
            case R.id.nav_reset_pass:
                startActivity(new Intent(MainActivity.this,ResetPasswordActivity.class));
                break;
            case R.id.nav_send:

                break;
        }
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

         private void  getCurrentUser(TextView username_app_bar) {
        BmobUser bmobUser = BmobUser.getCurrentUser(this);
        if(bmobUser != null){
            //用户已经登录,允许用户使用应用
            String username = (String) BmobUser.getObjectByKey(MainActivity.this, "username");
           username_app_bar.setText(username);
//            username_nav_head.setText(username);
        }else{
            username_app_bar.setText("用户名");
            //缓存用户对象为空时， 可打开用户注册界面…
        }
    }
}
