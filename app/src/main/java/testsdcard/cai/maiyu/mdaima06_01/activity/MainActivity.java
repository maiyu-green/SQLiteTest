package testsdcard.cai.maiyu.mdaima06_01.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import testsdcard.cai.maiyu.mdaima06_01.R;

/**
 * 测试：
 * 1.File存储与读取
 * 2.SharedPreferences存取
 */
public class MainActivity extends AppCompatActivity {

    //输入框
    @ViewInject(R.id.edit_input)
    private EditText mEdtInput;

    //File存储按钮
    @ViewInject(R.id.file_save)
    private Button mBtnSave;

    //File读取按钮
    @ViewInject(R.id.read)
    private Button mBtnRead;

    //pref存储按钮
    @ViewInject(R.id.sp_save)
    private Button mBtnSp;

    //pref读取按钮
    @ViewInject(R.id.sp_get)
    private Button mBtnGetSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化注解
        ViewUtils.inject(this);





    }

    /**
     * FiLe存储监听
     * @param view
     */
    @OnClick(R.id.file_save)
    public void save(View view){

        //获取输入
        String str = mEdtInput.getText().toString().trim();

        //判空：null或者空字符串
        if(!TextUtils.isEmpty(str)){
            //文件输出流与BufferedWriter
            FileOutputStream out = null;
            BufferedWriter writer = null;

            //写入
            try{
                out =   openFileOutput("data" , Context.MODE_PRIVATE);
                writer = new BufferedWriter(new OutputStreamWriter(out));
                writer.write(str);

            }catch (IOException e){
                e.printStackTrace();
            }finally {
                if(writer != null){
                    try {
                        //关闭流
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Toast.makeText(MainActivity.this , "save Success" , Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(MainActivity.this , "save failure" , Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 文件读取
     * @param view
     */
    @OnClick(R.id.read)
    public void read(View view){
        //文件输入流与BufferedReader对象
        FileInputStream in = null;
        BufferedReader reader = null;
        //用于追加结果
        StringBuffer content = new StringBuffer();

        //读取
        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null){
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //关闭流
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //结果：判空并显示
        String result = content.toString();
        if(!TextUtils.isEmpty(result)){
            mEdtInput.setText(result);
            mEdtInput.setSelection(result.length());
            Toast.makeText(MainActivity.this , "read Success" , Toast.LENGTH_SHORT).show();
        }else {
            mEdtInput.setText("");
            Toast.makeText(MainActivity.this , "read failure" , Toast.LENGTH_SHORT).show();
        }
    }


    /**commit与apply()的区别
     * 1. apply没有返回值而commit返回boolean表明修改是否提交成功
     2. apply是将修改数据原子提交到内存, 而后异步真正提交到硬件磁盘,
     而commit是同步的提交到硬件磁盘，因此，在多个并发的提交commit的时候，
     他们会等待正在处理的commit保存到磁盘后在操作，从而降低了效率。
     而apply只是原子的提交到内容，后面有调用apply的函数的将会直接覆盖前面的内存数据，
     这样从一定程度上提高了很多效率。
     3. apply方法不会提示任何失败的提示。
     由于在一个进程中，sharedPreference是单实例，一般不会出现并发冲突，
     如果对提交的结果不关心的话，建议使用apply，当然需要确保提交成功且有后续操作的话，
     还是需要用commit的*/

    /**
     * pref存储
     * @param view
     */
    @OnClick(R.id.sp_save)
    public void saveSp(View view){
        //: /data/data/包名/shared_prefs/目录下
        SharedPreferences sp = getSharedPreferences("mydata" , MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name" ,"JinYi");
        editor.putInt("age" , 18);
        editor.putBoolean("married" , false);
        editor.apply(); //以前都是用commit
        Toast.makeText(MainActivity.this , "sp存储成功" , Toast.LENGTH_SHORT).show();
    }

    //


    /**
     * pref读取
     * @param view
     */
    @OnClick(R.id.sp_get)
    public void getSp(View view){

        SharedPreferences pref = getSharedPreferences("mydata" , MODE_PRIVATE);
        String name = pref.getString("name" , "");
        int age = pref.getInt("age" ,0);
        boolean married = pref.getBoolean("married" , false);

        mEdtInput.setText("name= " + name + " ; age = " + age + " ; married = " + married);


    }

}
