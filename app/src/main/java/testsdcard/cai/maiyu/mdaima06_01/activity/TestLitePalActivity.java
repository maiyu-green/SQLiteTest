package testsdcard.cai.maiyu.mdaima06_01.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

import testsdcard.cai.maiyu.mdaima06_01.R;
import testsdcard.cai.maiyu.mdaima06_01.bean.Book;

/**
 * Created by maiyu on 2017/4/28.
 */

public class TestLitePalActivity extends AppCompatActivity {

    //创建数据库
    @ViewInject(R.id.create_database)
    private Button mBtnCreate;

    //增加数据
    @ViewInject(R.id.add)
    private Button mBtnAdd;

    //更新数据
    @ViewInject(R.id.update)
    private Button mBtnUpdate;

    //删除数据
    @ViewInject(R.id.delete)
    private Button mBtnDelete;

    //查询数据
    @ViewInject(R.id.retrieve)
    private Button mBtnRetrieve;

    private static final String TAG = "TestLitePalActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        ViewUtils.inject(this);

    }

    //创建数据库
    @OnClick(R.id.create_database)
    public void createDatabase(View view){


        //获取数据库
        LitePal.getDatabase();
    }

    /**
     * 添加数据
     * @param view
     */
    @OnClick(R.id.add)
    public void addData(View view){

        Book book = new Book();
        book.setName("大话数据结构");
        book.setAuthor("程杰");
        book.setPages(516);
        book.setPrice(43.00);
        //book.setPress("Unknow");//增加出版社
        book.save();
    }


    /**
     * 删除数据
     * @param view
     */
    @OnClick(R.id.delete)
    public void deleteData(View view){

        DataSupport.deleteAll(Book.class , "price < ?" , "60");

    }

    /**
     * 更新数据
     * @param view
     */
    @OnClick(R.id.update)
    public void updateData(View view){

        Book book = new Book();
        book.setPrice(38.50);
        book.updateAll("name = ? and author = ?" , "大话数据结构" , "程杰");
//更新数据为默认值
//        book.setToDefault("pages");
//        book.updateAll();
    }

    /**
     * 查询数据
     * @param view
     */
    @OnClick(R.id.retrieve)
    public void retrieveData(View view){

        List<Book> books = DataSupport.findAll(Book.class);
        for(Book book : books){
            Log.d(TAG , "book name is " + book.getName());
            Log.d(TAG , "book author is " + book.getAuthor());
            Log.d(TAG , "book pages is " + book.getPages());
            Log.d(TAG , "book price is " + book.getPrice());

        }


        //(2)添加限制：select--选定哪几列
        //where--约束条件，   order--结果排序 ，limit---结果的数量
        //offset---查询结果的便宜了offset(1)代表查询表中的从第2条开始
//        List<Book> books = DataSupport.select("name" , "author" ,"pages")
//                .where("pages > ?" , "600")
//                .order("pages")
//                .limit(10)
//                .offset(10)
//                .find(Book.class);
        //(3)用原生数据库语句查询
//        Cursor cursor = DataSupport.findBySQL("select * from Book where pages > ?" +
//                " and price < ?" ,"700" ,"60");

    }
}
