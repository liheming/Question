package com.company.question.questionapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.company.question.questionapplication.Bean.MyUser;
import com.company.question.questionapplication.R;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.VerifySMSCodeListener;

/**
 * Created by haily on 2016/6/9.
 */
public class RegisterActivity extends AppCompatActivity {
    private EditText phone_editText, password_editText, confirmpassword_editText, ed_username, identify_input;
    private Button getIdentity_button, phone_register_buton;

    public RegisterActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        phone_editText = (EditText) findViewById(R.id.phone_editText);
        password_editText = (EditText) findViewById(R.id.password_editText);
        confirmpassword_editText = (EditText) findViewById(R.id.confirmpassword_editText);
//        ed_username = (EditText) findViewById(R.id.ed_username);
        identify_input = (EditText) findViewById(R.id.identify_input);
        ed_username = (EditText) findViewById(R.id.username_editText);
        phone_register_buton = (Button) findViewById(R.id.phone_register_buton);
        getIdentity_button = (Button) findViewById(R.id.getIdentity_button);
        getIdentity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobSMS.requestSMSCode(RegisterActivity.this, phone_editText.getText().toString(), "mode1", new RequestSMSCodeListener() {

                    @Override
                    public void done(Integer integer, BmobException e) {
                        if (e == null) {
                            Toast.makeText(getApplicationContext(), "短信验证码发送成功" + "短信id：" + integer, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        phone_register_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                BmobSMS.verifySmsCode(RegisterActivity.this, phone_editText.getText().toString(), identify_input.getText().toString(), new VerifySMSCodeListener() {
                    @Override
                    public void done(BmobException ex) {
                        // TODO Auto-generated method stub
                        if (ex == null) {//短信验证码已验证成功
                            Log.i("smile", "验证通过");


                        } else {
                            Log.i("smile", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                        }
                    }
                });


                if (password_editText.length() == 0) {
                    Log.i("login", "onClick:editUsername==null  ");
                    Toast.makeText(getApplicationContext(), "请输入11手机号码", Toast.LENGTH_SHORT).show();
                } else if (phone_editText.length() != 11) {
                    Log.i("login", "onClick:editUsername==null  ");
                    Toast.makeText(getApplicationContext(), "手机号为11位", Toast.LENGTH_SHORT).show();
                } else if (password_editText.length() == 0 || (confirmpassword_editText.length() == 0)) {
                    Log.i("login", "onClick:editPassword==null  ");
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();

                } else if (password_editText.length() < 6 || (confirmpassword_editText.length() < 6)) {
                    Log.i("login", "onClick:editPassword==null  ");
                    Toast.makeText(getApplicationContext(), "密码不少于6位", Toast.LENGTH_SHORT).show();

                } else {
                    Log.i("login", "onClick:register  ");


                    if (password_editText.getText().toString().equals(confirmpassword_editText.getText().toString())) {

                        MyUser user = new MyUser();
                        user.setMobilePhoneNumber(phone_editText.getText().toString());//设置手机号码（必填）
                        user.setUsername(ed_username.getText().toString());                  //设置用户名，如果没有传用户名，则默认为手机号码
                        user.setPassword(password_editText.getText().toString());                  //设置用户密码
//                        user.setAge(18);                        //设置额外信息：此处为年龄
                        user.signOrLogin(RegisterActivity.this, identify_input.getText().toString(), new SaveListener() {

                            @Override
                            public void onSuccess() {
                                // TODO Auto-generated method stub
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                Log.i("smile", "注册成功");
//                                Log.i("smile", ""+user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId());
                            }

                            @Override
                            public void onFailure(int code, String msg) {
                                // TODO Auto-generated method stub
                                Toast.makeText(RegisterActivity.this, "注册失败" + msg + "错误码" + code, Toast.LENGTH_SHORT).show();
                                    Log.i("smile", "注册失败" + msg + "错误码" + code);
                            }
                        });



//                        MyUser user = new MyUser();
//                        user.setMobilePhoneNumber(phone_editText.getText().toString());     //设置手机号码（必填）
//                        user.setPassword(password_editText.getText().toString());              //设置用户密码（必填）
//                        user.setUsername(ed_username.getText().toString());
//                        Log.i("RegisterActivity", "验证码是: " + identify_input.getText().toString());
//                        //设置用户名，如果没有传用户名，则默认为手机号码
////                user.setAge(18);                        //设置额外信息：此处为年龄
//                        user.signOrLogin(RegisterActivity.this, identify_input.getText().toString(), new SaveListener() {
//
//                            @Override
//                            public void onSuccess() {
//                                // TODO Auto-generated method stub
//                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                                Toast.makeText(getApplicationContext(), "注册成功", Toast.LENGTH_SHORT).show();
//                                Log.i("smile", "注册成功");
//
////                        toast("注册或登录成功");
////                        Log.i("smile", ""+user.getUsername()+"-"+user.getAge()+"-"+user.getObjectId());
//                            }
//
//                            @Override
//                            public void onFailure(int code, String msg) {
//                                // TODO Auto-generated method stub
//                                if (code == 207) {
//                                    msg = "短信验证码错误";
//                                    Toast.makeText(getApplicationContext(), "注册失败" + msg + "错误码" + code, Toast.LENGTH_SHORT).show();
//                                    Log.i("smile", "注册失败" + msg + "错误码" + code);
//                                }
//                                Log.i("smile", "注册失败");
////                        toast("错误码："+code+",错误原因："+msg);
//                            }
//                        });



                    } else {
                        Toast.makeText(getApplicationContext(), "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });

    }
}
