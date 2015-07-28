package com.example.com.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by BlackWhite on 15/7/28.
 */
public class viewBlocks extends Activity {
    // 存储数据的数组列表
    ArrayList<HashMap<String, Object>> listData;
    // 适配器
    SimpleAdapter listItemAdapter;

    SQLiteDatabase db;
    SQLiteDatabaseDao dao;
    MyDatabaseHelper dbHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewblock);
        dao = new SQLiteDatabaseDao();

        ListView list = (ListView) findViewById(R.id.blocklist);
        listItemAdapter = new SimpleAdapter(viewBlocks.this,
                listData,
                R.layout.viewblockitem,
                new String[]{"name", "number"},
                new int[]{R.id.user_name, R.id.user_number});
        list.setAdapter(listItemAdapter);
        list.setOnCreateContextMenuListener(listViewLongPress);
    }

    // 简单的数据库操作类

    class SQLiteDatabaseDao {

        public SQLiteDatabaseDao() {

            dbHelper = BlockActivity.getdbHelper();
            db = dbHelper.getWritableDatabase();
            getAllData("block");

        }

        // 查询所有数据
        public void getAllData(String table) {
            Cursor c = db.rawQuery("select * from " + table, null);
            int columnsSize = c.getColumnCount();
            listData = new ArrayList<>();
            while (c.moveToNext()) {
                HashMap<String, Object> map = new HashMap<>();
                for (int i = 0; i < columnsSize; i++) {
                    map.put("id", c.getString(c.getColumnIndex("id")));
                    map.put("name", c.getString(c.getColumnIndex("name")));
                    map.put("number", c.getString(c.getColumnIndex("number")));
                }
                listData.add(map);
            }
            c.close();
        }

        public boolean delete(SQLiteDatabase db, String table, int id) {
            String whereClause = "id=?";
            String[] whereArgs = new String[]{String.valueOf(id)};
            db.delete(table, whereClause, whereArgs);
            return true;
        }
    }

    View.OnCreateContextMenuListener listViewLongPress = new View.OnCreateContextMenuListener() {
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v,
                                        ContextMenu.ContextMenuInfo menuInfo) {
            // TODO Auto-generated method stub
            final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            new AlertDialog.Builder(viewBlocks.this)
                    .setTitle("删除当前数据")
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setMessage("确定删除当前记录")
                    .setPositiveButton("是",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                    int mListPos = info.position;
                                    HashMap<String, Object> map = listData
                                            .get(mListPos);
                                    int id = Integer.valueOf((map.get("id")
                                            .toString()));
                                    if (dao.delete(db, "block", id)) {
                                        listData.remove(mListPos);
                                        listItemAdapter.notifyDataSetChanged();
                                    }
                                }
                            })
                    .setNegativeButton("否",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialoginterface, int i) {
                                }
                            }).show();
        }
    };

    public void finish() {
        super.finish();
        db.close();
    }
}
