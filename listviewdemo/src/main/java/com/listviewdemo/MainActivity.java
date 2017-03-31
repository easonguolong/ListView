package com.listviewdemo;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnRefreshListener{

    private List<String> textList;
    private RefreshListeView rListView;
    private Myadapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rListView = (RefreshListeView)findViewById(R.id.refreshlistview);
        textList = new ArrayList<String>();
        for (int i = 0; i < 25; i++) {
            textList.add("这是一条ListView的数据" + i);
        }
        adapter = new Myadapter();
        rListView.setAdapter(adapter);
        rListView.setOnRefreshListener(this);
    }



    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(1000);
                for(int i =0;i<2;i++)
                {
                    textList.add(0,"下拉数据Eason"+i);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                rListView.hideHeaderView();
            }
        }.execute();
    }

    @Override
    public void onLoadingMore() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(1000);
                textList.add("Eason-1");
                textList.add("Eason-2");
                textList.add("Eason-3");
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                rListView.hideFooterView();
            }
        }.execute();
    }

    private class Myadapter extends BaseAdapter{

        @Override
        public int getCount() {
            return textList.size();
        }

        @Override
        public Object getItem(int position) {
            return textList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textview = new TextView(MainActivity.this);
            textview.setText(textList.get(position));
            textview.setTextColor(Color.RED);
            textview.setTextSize(18.0f);
            return textview;
        }
    }

}
