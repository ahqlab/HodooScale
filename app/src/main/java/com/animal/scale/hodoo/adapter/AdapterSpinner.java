package com.animal.scale.hodoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.animal.scale.hodoo.R;

import java.util.List;

public class AdapterSpinner extends BaseAdapter{
    Context context;
    List<String> data;
    LayoutInflater inflater;


    public AdapterSpinner(Context context, List<String> data){
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        if(data!=null) return data.size();
        else return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.my_spinner_style, parent, false);
        }

        if(data!=null){
            //데이터세팅
            String text = data.get(position);
            ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);
        }

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.my_spinner_content_style , parent, false);
        }
        //데이터세팅
        String text = data.get(position);
        ((TextView)convertView.findViewById(R.id.spinnerText)).setText(text);
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}




