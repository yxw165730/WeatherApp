package com.cs.utd.weatherapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cs.utd.weatherapp.Data.MyDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    EditText location;
    Button confirm;
    private MyDatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this);

        location = (EditText) findViewById(R.id.location);
        confirm = (Button) findViewById(R.id.confirm);
    }

    public void ConfirmBtn(View v) {
        String loc = location.getText().toString();
        if(!TextUtils.isEmpty(loc)) {
            //Log.v("TAG", "-----------------------------I'm here!-----------------------------");
            dbHelper.addLocation(loc);
        }

        /*if(!TextUtils.isEmpty(loc)) {
            intent.putExtra("Location", loc);
        } else {
            intent.putExtra("Location", "Allen");
        }*/
        Intent intent = new Intent();
        //intent.setClass(MainActivity.this, ShowActivity.class);
        intent.setClass(MainActivity.this, ListActivity.class);
        startActivity(intent);
        finish();
    }
}
