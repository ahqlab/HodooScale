package com.animal.scale.hodoo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.domain.Notice;

import java.util.ArrayList;

public class BaseExpandableAdapter extends BaseExpandableListAdapter {

    public ArrayList<Notice> groupList = null;
    public ArrayList<ArrayList<Notice>> childList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;

    public BaseExpandableAdapter(Context c, ArrayList<Notice> groupList, ArrayList<ArrayList<Notice>> childList){
        super();
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
    }


    // 그룹 포지션을 반환한다.
    @Override
    public Notice getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    // 그룹 사이즈를 반환한다.
    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    // 그룹 ID를 반환한다.
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // 그룹뷰 각각의 ROW
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.notice_listview_item, parent, false);
            viewHolder.title = (TextView) v.findViewById(R.id.title);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
       /* if(isExpanded){
            viewHolder.iv_image.setBackgroundColor(Color.GREEN);
        }else{
            viewHolder.iv_image.setBackgroundColor(Color.WHITE);
        }*/
        viewHolder.title.setText(getGroup(groupPosition).getTitle());
        return v;
    }

    // 차일드뷰를 반환한다.
    @Override
    public Notice getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    // 차일드뷰 사이즈를 반환한다.
    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    // 차일드뷰 ID를 반환한다.
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    // 차일드뷰 각각의 ROW
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.notice_content_item, parent, false);
            viewHolder.content = (EditText) v.findViewById(R.id.notice_content);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }
        viewHolder.content.setText(getChild(groupPosition, childPosition).getContent());
        return v;
    }

    @Override
    public boolean hasStableIds() { return true; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    class ViewHolder {

        public EditText content;
        public TextView title;
    }
}

