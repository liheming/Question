package com.company.question.questionapplication.bmob;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.company.question.questionapplication.R;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.ResetPasswordByCodeListener;

/**
 * Created by haily on 2016/6/9.
 */
public class ResetPasswordActivity extends AppCompatActivity {
    private EditText phone_editText, password_editText, confirmpassword_editText, identify_input;
    private Button getIdentity_button, phone_resetpass_buton;

    public ResetPasswordActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        phone_editText = (EditText) findViewById(R.id.phone_editText);
        getNumber(phone_editText);
        password_editText = (EditText) findViewById(R.id.password_editText);
        confirmpassword_editText = (EditText) findViewById(R.id.confirmpassword_editText);
//        ed_username = (EditText) findViewById(R.id.ed_username);
        identify_input = (EditText) findViewById(R.id.identify_input);
        phone_resetpass_buton = (Button) findViewById(R.id.phone_resetpass_buton);
        getIdentity_button = (Button) findViewById(R.id.getIdentity_button);
        getIdentity_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BmobSMS.requestSMSCode(ResetPasswordActivity.this, phone_editText.getText().toString(), "mode1", new RequestSMSCodeListener() {
                    @Override
                    public void done(Integer smsId,BmobException ex) {
                        // TODO Auto-generated method stub
                        if(ex==null){//验证码发送成功
                            Toast.makeText(getApplicationContext(), "验证码发送成功,短信id", Toast.LENGTH_SHORT).show();
                            Log.i("smile", "验证码发送成功,短信id："+smsId);//用于查询本次短信发送详情
                        }
                    }
                });
//                BmobSMS.requestSMSCode(ResetPasswordActivity.this, phone_editText.getText().toString(), "mode1", new RequestSMSCodeListener() {
//
//                    @Override
//                    public void done(Integer integer, BmobException e) {
//                        if (e == null) {
//                            Toast.makeText(getApplicationContext(), "短信验证码发送成功" + "短信id：" + integer, Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
            }
        });

        phone_resetpass_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                BmobSMS.verifySmsCode(ResetPasswordActivity.this, "18942433927", identify_input.getText().toString(), new VerifySMSCodeListener() {
//                    @Override
//                    public void done(BmobException ex) {
//                        // TODO Auto-generated method stub
//                        if (ex == null) {//短信验证码已验证成功
//                            Log.i("smile", "验证通过");
//
//
//                        } else {
//                            Log.i("smile", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
//                        }
//                    }
//                });


              if (password_editText.length() == 0 || (confirmpassword_editText.length() == 0)) {
                    Log.i("ResetPassword", "onClick:editPassword==null  ");
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();

                } else if (password_editText.length() < 6 || (confirmpassword_editText.length() < 6)) {
                    Log.i("ResetPassword", "onClick:editPassword==null  ");
                    Toast.makeText(getApplicationContext(), "密码不少于6位", Toast.LENGTH_SHORT).show();

                } else {
                    Log.i("ResetPassword", "onClick:可以重置密码  ");


                    if (password_editText.getText().toString().equals(confirmpassword_editText.getText().toString())) {

                        BmobUser.resetPasswordBySMSCode(ResetPasswordActivity.this, identify_input.getText().toString(),password_editText.getText().toString(), new ResetPasswordByCodeListener() {
                            @Override
                            public void done(BmobException ex) {
                                // TODO Auto-generated method stub
                                if(ex==null){
                                    Log.i("smile", "密码重置成功");
                                    Toast.makeText(getApplicationContext(), "密码重置成功,请重新用新密码登录", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ResetPasswordActivity.this,LoginActivity.class));
                                }else{
                                    Toast.makeText(getApplicationContext(), "重置失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    Log.i("smile", "重置失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                                }
                            }
                        });



//                        MyUser user = new MyUser();
//                        user.setMobilePhoneNumber(password_editText.getText().toString());     //设置手机号码（必填）
//                        user.setPassword(password_editText.getText().toString());              //设置用户密码
////                        user.setUsername(ed_username.getText().toString());                  //设置用户名，如果没有传用户名，则默认为手机号码
////                user.setAge(18);                        //设置额外信息：此处为年龄
//                        user.signOrLogin(ResetPasswordActivity.this, identify_input.getText().toString(), new SaveListener() {
//
//                            @Override
//                            public void onSuccess() {
//                                // TODO Auto-generated method stub
//                                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
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
//                                if(code==207){
//                                    msg = "短信验证码错误";
//                                Toast.makeText(getApplicationContext(), "注册失败" + msg + "错误码" + code, Toast.LENGTH_SHORT).show();
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
 public void getNumber(EditText phone_editText){
     BmobUser bmobUser = BmobUser.getCurrentUser(ResetPasswordActivity.this);
     if(bmobUser != null){
         //用户已经登录,允许用户使用应用
         String username = (String) BmobUser.getObjectByKey(ResetPasswordActivity.this, "mobilePhoneNumber");
         Log.i("resetpass", "手机号码是:"+username);
         phone_editText.setText(username);
//            username_nav_head.setText(username);
     }else{
         Toast.makeText(ResetPasswordActivity.this,"用户未登录",Toast.LENGTH_SHORT).show();
         Log.i("resetpass", "用户w未登录");
         //缓存用户对象为空时， 可打开用户注册界面…
     }
 }
}
