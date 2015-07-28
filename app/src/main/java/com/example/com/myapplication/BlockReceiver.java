package  com.example.com.myapplication;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BlockReceiver extends BroadcastReceiver{

    public void onReceive(Context context, Intent intent) {
        Intent service=new Intent(context, BlockNow.class);
        context.startService(service);
    }
}