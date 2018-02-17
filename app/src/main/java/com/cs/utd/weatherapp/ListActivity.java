package com.cs.utd.weatherapp;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.utd.weatherapp.Data.MyDatabaseHelper;

public class ListActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;
    private RecyclerView mRecyclerView;
    private List<String> mData;
    private HomeAdapter mAdapter;
    Button toMainPage;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_list);

        toMainPage = (Button) findViewById(R.id.toMainPage);

        dbHelper = new MyDatabaseHelper(this);
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.setAdapter(mAdapter = new HomeAdapter());

        Context context = getBaseContext();

        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(context, mRecyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        // do whatever
                        String loc = mData.get(position);

                        Intent intent = new Intent();
                        intent.putExtra("Location", loc);
                        intent.setClass(ListActivity.this, ShowActivity.class);
                        startActivity(intent);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever

                    }
                })
        );
    }

    public void toMainPage(View view) {
        Intent intent = new Intent();
        intent.setClass(ListActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    protected void initData() {
        mData = new ArrayList<String>();

        mData = dbHelper.getAllLocations();
        /*dbHelper = new MyDatabaseHelper(this,"WeatherLocation.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("Location",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                //然后通过Cursor的getColumnIndex()获取某一列中所对应的位置的索引
                String location = cursor.getString(cursor.getColumnIndex("location"));
                if(location != null) {
                    mDatas.add(location);
                }
            }while(cursor.moveToNext());
        }
        cursor.close();*/
        /*for (int i = 'A'; i < 'z'; i++)
        {
            mDatas.add("" + (char) i);
        }*/
    }


    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        private LayoutInflater mInflater;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    ListActivity.this).inflate(R.layout.activity_list_items, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            if(mData != null) {
                holder.tv.setText(mData.get(position));
            }
        }

        @Override
        public int getItemCount()
        {
            return mData.size();
        }

        class MyViewHolder extends ViewHolder {
            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.weather);
            }

        }
    }
}
