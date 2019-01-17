package com.animal.scale.hodoo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.domain.SettingMenu;

import java.util.ArrayList;
import java.util.List;

public class AdapterOfExpandable extends BaseExpandableListAdapter {

    private String TAG = AdapterOfExpandable.class.getSimpleName();

    private Context mContext;
    private ArrayList<String> mTitle;
    public ArrayList<List<SettingMenu>> mContent;
    private LayoutInflater mInflater = null;
    private ViewHolder mViewHolder;

    public AdapterOfExpandable (Context context, ArrayList<String> title, ArrayList<List<SettingMenu>> content) {
        mContext = context;
        mTitle = title;
        mContent = content;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return mTitle.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mContent.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return mTitle.get(i);
    }

    @Override
    public SettingMenu getChild(int titlePosition, int contentPosition) {
        return mContent.get(titlePosition).get(contentPosition);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int titlePosition, int contentPosition) {
        return contentPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View convertView, ViewGroup viewGroup) {
        if ( convertView == null ) {
            mViewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.setting_listview_title, viewGroup, false);
            mViewHolder.title = convertView.findViewById(R.id.expandable_title);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.title.setText( mTitle.get(i) );

        return convertView;
    }

    @Override
    public View getChildView(int titlePosition, int contentPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if ( convertView == null ) {
            mViewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.setting_listview_content, null);
            mViewHolder.content = convertView.findViewById(R.id.expandable_content);
            mViewHolder.badge = convertView.findViewById(R.id.setting_badge);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.badge.setVisibility( getChild(titlePosition, contentPosition).getBadgeCount() > 0 ? View.VISIBLE : View.GONE );
        mViewHolder.badge.setText( String.valueOf(Math.min(getChild(titlePosition, contentPosition).getBadgeCount(), 99)) );
        mViewHolder.content.setText( getChild(titlePosition, contentPosition).getName() );
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    public class ViewHolder {
        private TextView title;
        private TextView content;
        private TextView badge;
    }
    public void setBadge( int titlePosition, int contentPosition, int count ) {
        getChild(titlePosition, contentPosition).setBadgeCount( count );
        notifyDataSetChanged();
    }
}
