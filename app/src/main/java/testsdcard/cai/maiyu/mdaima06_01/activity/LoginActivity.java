package testsdcard.cai.maiyu.mdaima06_01.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import testsdcard.cai.maiyu.mdaima06_01.R;

/**sharedPreference测试记住用户登录名字和密码例子
 * Created by maiyu on 2017/4/27.
 */

public class LoginActivity extends AppCompatActivity {

    //用户名
    @ViewInject(R.id.account)
    private EditText accountEdit;

    //密码
    @ViewInject(R.id.password)
    private EditText passwordEdit;

    //登录
    @ViewInject(R.id.login)
    private Button login;

    //记住密码checkBox
    @ViewInject(R.id.remember_pass)
    private CheckBox rememberPass;

    //pref，pref.editor对象
    private SharedPreferences pref;
    private  SharedPreferences.Editor editor ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //初始化inject
        ViewUtils.inject(this);

        //获取pref
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        //判断是否选中记住密码
        boolean isRemember = pref.getBoolean("remember_password" , false);
        if(isRemember){
            String account = pref.getString("account" , "");
            String password = pref.getString("password" , "");
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);  //设置选中
        }

    }


    /**
     * 登录
     * @param view
     */
    @OnClick(R.id.login)
    public void toLogin(View view){

        //获取用户名和密码
        String account = accountEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();

        //判断用户名和密码是否正确
        if(account.equals("maiyu") && password.equals("123456")){
            //获取pref.edit()
            editor = pref.edit();
            //判断是否选中记住密码，是的话存储pref
            if(rememberPass.isChecked()){
                editor.putBoolean("remember_password" , true);
                editor.putString("account" , account);
                editor.putString("password" , password);
            }else {
                editor.clear();     //不存储
            }
            editor.apply();
            //跳转
            Intent intent = new Intent(LoginActivity.this , TestActivity.class);
            startActivity(intent);
            finish();
        }else {
            //密码或用户名错误
            Toast.makeText(LoginActivity.this , "account or password is unvalid",Toast.LENGTH_SHORT).show();
        }
    }

}
