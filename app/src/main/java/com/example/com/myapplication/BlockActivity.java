package com.example.com.myapplication;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by BlackWhite on 15/7/27.
 */
public class BlockActivity extends Activity{
    public static final int ADD_FROM_CONTACT = 1;
    public static final int ADD_BLOCK = 2;
    public static MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    ListView mListView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blockdetail);
        dbHelper = new MyDatabaseHelper(this,"BlockNum.db",null,1);
        db = dbHelper.getReadableDatabase();
        String [] choice = new String [] {"choose from contact","Add now","viewList"};
        ArrayAdapter<String>  adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,choice);
        mListView = (ListView) findViewById(R.id.blockDetail);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        Intent intent0 = new Intent();
                      //  intent0.setClass(this,PhoneSelectActivityNew.class);
                        startActivityForResult(intent0,ADD_FROM_CONTACT);
                        break;
                    case 1:
                        Intent intent1 = new Intent();
                        intent1.setClass(BlockActivity.this,AddBlock.class);
                        startActivityForResult(intent1,ADD_BLOCK);
                        break;
                    case 2:
                        Intent intent2 = new Intent();
                        intent2.setClass(BlockActivity.this,viewBlocks.class);
                        startActivity(intent2);
                        break;
                }
            }
        });
    }
    public static MyDatabaseHelper getdbHelper(){
        return  dbHelper;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==RESULT_OK)
        {
            switch (requestCode)
            {
                case ADD_FROM_CONTACT:
                    break;
                case ADD_BLOCK:
                    String name = data.getStringExtra("name");
                    String num  = data.getStringExtra("number");
                    AddtoSqlite(name,num);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void AddtoSqlite(String name,String number)
    {
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("number",number);
        db.insert("block",null,values);
        values.clear();
    }
}
