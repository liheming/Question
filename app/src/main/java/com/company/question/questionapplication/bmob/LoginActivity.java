package com.company.question.questionapplication.bmob;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.company.question.questionapplication.Activity.MainActivity;
import com.company.question.questionapplication.R;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    EditText phone, password;
    static String QUESTION="Question";
    static String OPTION_A="OptionA";
    static String OPTION_B="OptionB";
    static String OPTION_C="OptionC";
    static String OPTION_D="OptionD";
    static String ANSWER="Answer";
    static String EXPLAIN="Explains";
    private LinearLayout exercise;
    public Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Toast.makeText(LoginActivity.this, "数据库创建完成", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


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

        // Set up the login form.
        setContentView(R.layout.activity_login);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.add_data_button).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });
        findViewById(R.id.to_register_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


        Button mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                login();

            }
        });
    }

    private void login() {
        BmobUser bu2 = new BmobUser();
        bu2.setUsername(phone.getText().toString());
        bu2.setPassword(password.getText().toString());
        bu2.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
//                        Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(Loginew.this, MainActivity.class));


                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                Log.i("login", "btnLogin  else  ");
                Toast.makeText(getApplicationContext(), "登陆成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(getApplicationContext(), "登陆失败" + msg + "错误码" + code, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

