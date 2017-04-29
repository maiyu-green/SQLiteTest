package testsdcard.cai.maiyu.mdaima06_01.activity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import testsdcard.cai.maiyu.mdaima06_01.R;
import testsdcard.cai.maiyu.mdaima06_01.activity.helper.MyDatabaseHelper;

/**
 * Created by maiyu on 2017/4/28.
 */

public class DataBaseActivity extends AppCompatActivity {

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

    //MyDatabaseHelper对象
    private MyDatabaseHelper dbHelper;
    //SQLiteDatabase对象
    private SQLiteDatabase db;

    private static final String TAG = "DataBaseActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        ViewUtils.inject(this);

        //初始化：4个参数：上下文，数据库名字，CursorFactory对象，版本号；
        //当需要更新数据库版本时可以+1
        dbHelper = new MyDatabaseHelper(this , "BookStore.db" , null ,1);
        db = dbHelper.getWritableDatabase();
    }


    /**
     * 创建数据库表
     * @param view
     */
    @OnClick(R.id.create_database)
    public void CreateDatabase(View view){

        //创建或打开一个现有的数据库
        //getReadableDatabase():当磁盘空间满时，将以只读方式去打开
        //getWritableDatabase():当磁盘已满时，抛出异常
        dbHelper.getWritableDatabase();
    }


    /**
     * 增加数据：public long insert(String table, String nullColumnHack, ContentValues values)
     * table:表名
     * nullColunmHack:用于在未指定添加数据时，给某些空列自动赋值null
     * values:ContentValues对象，添加相应数据
     * @param view
     */
    @OnClick(R.id.add)
    public void addDate(View view){

        ContentValues values = new ContentValues();
        //
        values.put("name" , "第一行代码");
        values.put("author" , "郭霖");
        values.put("pages" , 570);
        values.put("price" , 79.00);

        db.insert("Book" , null , values);
        values.clear();

        //
        values.put("name" , "编译原理");
        values.put("author" ,"MouMou");
        values.put("pages" , 500);
        values.put("price" , 66.00);
        db.insert("Book" , null , values);
    }


    /**
     * 更新数据：4个参数
     * 假如突然打折，更新某个商品价格
     * public int update(String table, ContentValues values, String whereClause, String[] whereArgs)
     * whereClause:约束某些行
     * whereArgs:约束某些行的更新数据
     * @param view
     */
    @OnClick(R.id.update)
    public void updateData(View view){

        ContentValues values = new ContentValues();
        values.put("price" , 58.99);
        db.update("Book" , values , "name = ?",new String[]{"编译原理"});

    }

    /**
     * 删除数据：4个参数
     * public int delete(String table, String whereClause, String[] whereArgs)
     * 与update类似
     * @param view
     */
    @OnClick(R.id.delete)
    public void deleteData(View view){

        db.delete("Book" , "price < ?" ,new  String[]{"60"});
    }


    /**
     * 查询数据：7个参数
     *     public Cursor query(String table, String[] columns, String selection,
     * String[] selectionArgs, String groupBy, String having,
     * String orderBy)
     * table--查询的表名，columns--指定要查询的列名(select column1,column2...)
     * selection ---- 指定where的约束条件（where column = value)
     * selectionArgs---为where中的占位符提供具体值
     * groupBy  --- 指定需要group by 的列（group by column)
     * having -- 对group by后的结果进行约束（having column = value)
     * orderBy    指定查询结果的排序方式(order by column1,column2,,,)
     * @param view
     */
    @OnClick(R.id.retrieve)
    public void retrieveData(View view){

        Cursor cursor = db.query("Book" , null ,null, null ,null , null ,null);
        if(cursor.moveToFirst()){
            do{
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                double price = cursor.getDouble(cursor.getColumnIndex("price"));

                Log.d(TAG , "book name is " + name);
                Log.d(TAG , "book author is " + author);
                Log.d(TAG , "book pages is " + pages);
                Log.d(TAG , "book price is " + price);

            }while (cursor.moveToNext());

        }//if
        cursor.close();
    }
}
