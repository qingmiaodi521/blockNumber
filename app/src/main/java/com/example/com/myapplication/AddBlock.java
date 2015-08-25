package com.example.com.myapplication;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by BlackWhite on 15/7/28.
 */
public class AddBlock extends Activity {
    EditText mName,mNumber;
    Button bt1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addblock);
        mName = (EditText) findViewById(R.id.id_name);
        mNumber = (EditText) findViewById(R.id.id_number);
        bt1 = (Button) findViewById(R.id.id_confirm);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String num  = mNumber.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("name",name);
                intent.putExtra("number",num);



                setResult(RESULT_OK,intent);
                finish();
            }
        });

    }
}
