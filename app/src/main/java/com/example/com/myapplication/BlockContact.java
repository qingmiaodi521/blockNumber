package com.example.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class BlockContact extends Activity {
    private Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_contact);
        Intent service=new Intent(this, BlockNow.class);
        startService(service);
        bt1 = (Button) findViewById(R.id.block);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(BlockContact.this,BlockActivity.class);
                startActivity(intent);
            }
        });

    }
}
