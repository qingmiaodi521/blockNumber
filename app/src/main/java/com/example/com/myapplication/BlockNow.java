package com.example.com.myapplication;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;


/**
 * Created by BlackWhite on 15/7/28.
 */
public class BlockNow extends Service {
    TelephonyManager tManager;
    // 声明监听通话状态的监听器
    CustomPhoneCallListener cpListener;
    MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        // 获取系统的TelephonyManager管理器
        tManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        cpListener = new CustomPhoneCallListener();
        // 通过TelephonyManager监听通话状态的改变
        tManager.listen(cpListener, PhoneStateListener.LISTEN_CALL_STATE);
        Log.e("serviceSuccess", "success");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class CustomPhoneCallListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    // 如果该号码属于黑名单
                    Log.e("number",incomingNumber);
                    if (isBlock(incomingNumber)) {
                        try {
                            TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

                            Class tmClazz = Class.forName(tm.getClass().getName());

                            Method m = tmClazz.getDeclaredMethod("getITelephony");
                            m.setAccessible(true);

                            com.android.internal.telephony.ITelephony telephony = (ITelephony) m.invoke(tm);

                            telephony.endCall();
                        }catch (Exception e){e.printStackTrace();}

                    }
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    public boolean isBlock(String phone) {
        dbHelper = new MyDatabaseHelper(this,"BlockNum.db",null,1);
        db  = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from block", null);
        while (cursor.moveToNext())
        {
            String num= cursor.getString(cursor.getColumnIndex("number"));
            Log.e("cursor",cursor.getString(cursor.getColumnIndex("number")));
            if (num.equals(phone))
            {
                return true;
            }
        }
        cursor.close();
        return false;
    }
}