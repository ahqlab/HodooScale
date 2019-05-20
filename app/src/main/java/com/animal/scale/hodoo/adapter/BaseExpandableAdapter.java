package com.animal.scale.hodoo.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.domain.Notice;

import org.xml.sax.XMLReader;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BaseExpandableAdapter extends BaseExpandableListAdapter {

    public ArrayList<Notice> groupList = null;
    public ArrayList<ArrayList<Notice>> childList = null;
    private LayoutInflater inflater = null;
    private ViewHolder viewHolder = null;
    private Context mContext;

    public BaseExpandableAdapter(Context c, ArrayList<Notice> groupList, ArrayList<ArrayList<Notice>> childList){
        super();
        this.inflater = LayoutInflater.from(c);
        this.groupList = groupList;
        this.childList = childList;
        mContext = c;
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
            viewHolder.indicator = v.findViewById(R.id.group_indicator);
//            viewHolder.created = v.findViewById(R.id.created);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        Timestamp t = Timestamp.valueOf(getGroup(groupPosition).getCreateDate());
        Date date = new Date(t.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        String createdStr = " " +  sdf.format(date);


        // 그룹을 펼칠때와 닫을때 아이콘을 변경해 준다.
       /* if(isExpanded){
            viewHolder.iv_image.setBackgroundColor(Color.GREEN);
        }else{
            viewHolder.iv_image.setBackgroundColor(Color.WHITE);
        }*/
       String titleStr = getGroup(groupPosition).getTitle() + createdStr;
        SpannableStringBuilder ssb = new SpannableStringBuilder(titleStr);
        ssb.setSpan(new AbsoluteSizeSpan(35), getGroup(groupPosition).getTitle().length(), getGroup(groupPosition).getTitle().length() + createdStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.mainBlack)), 0, getGroup(groupPosition).getTitle().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.title.setText(ssb);
        viewHolder.indicator.setSelected(isExpanded);
//        viewHolder.created.setText(getGroup(groupPosition).getCreateDate());
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
            viewHolder.content = v.findViewById(R.id.notice_content);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)v.getTag();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            viewHolder.content.setText(Html.fromHtml(getChild(groupPosition, childPosition).getContent()), TextView.BufferType.SPANNABLE);
        } else {
            viewHolder.content.setText(Html.fromHtml(getChild(groupPosition, childPosition).getContent()), TextView.BufferType.SPANNABLE);
        }
//        viewHolder.content.setText(getChild(groupPosition, childPosition).getContent());
        return v;
    }

    @Override
    public boolean hasStableIds() { return true; }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) { return true; }

    class ViewHolder {

        public ImageView indicator;
        public TextView content;
        public TextView title;
        public TextView created;
//    public class UlTagHandler implements Html.TagHandler{
//        @Override
//        public void handleTag(boolean opening, String tag, Editable output,
//                              XMLReader xmlReader) {
//            if(tag.equals("ul") && !opening) output.append("\n");
//            if(tag.equals("li") && opening) output.append("\n\t•");
//        }
//    }
    }
}

