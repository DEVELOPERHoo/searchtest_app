package com.example.practice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<Data> arrayList;

    public MyAdapter(Context context, ArrayList<Data> data){
        mContext = context;
        arrayList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Data getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.list, null);

        TextView menuView = (TextView)view.findViewById(R.id.tmenu);
        TextView codeView = (TextView)view.findViewById(R.id.tcode);
        TextView nameView = (TextView)view.findViewById(R.id.tname);
        TextView idxView = (TextView)view.findViewById(R.id.tidx);
        TextView gbnView = (TextView)view.findViewById(R.id.tgbn);
        TextView iconView = (TextView)view.findViewById(R.id.ticon);
        TextView validView = (TextView)view.findViewById(R.id.tvalid);

        menuView.setText(arrayList.get(position).getTmenu());
        codeView.setText(arrayList.get(position).getTcode());
        nameView.setText(arrayList.get(position).getTname());
        idxView.setText(arrayList.get(position).getTidx());
        gbnView.setText(arrayList.get(position).getTgbn());
        iconView.setText(arrayList.get(position).getTicon());
        validView.setText(arrayList.get(position).getTvalid());

        return view;
    }
}
