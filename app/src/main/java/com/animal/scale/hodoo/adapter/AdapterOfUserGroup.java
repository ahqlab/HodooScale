package com.animal.scale.hodoo.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.RequestUserGroupListviewBinding;
import com.animal.scale.hodoo.domain.InvitationUser;

import java.util.List;

public class AdapterOfUserGroup extends BaseAdapter {
    private List<InvitationUser> mUsers;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private RequestUserGroupListviewBinding binding;
    public AdapterOfUserGroup(Context context, List<InvitationUser> users) {
        mUsers = users;
        mContext = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return mUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.request_user_group_listview, null);
            binding = DataBindingUtil.bind(convertView);
            binding.setUsers(mUsers.get(i));
        }
        if ( mUsers.get(i).getState() > 0 ) {
            binding.stateWrap.setVisibility(View.VISIBLE);
        } else {
            binding.btnWrap.setVisibility(View.VISIBLE);
        }
        return binding.getRoot();
    }
}
