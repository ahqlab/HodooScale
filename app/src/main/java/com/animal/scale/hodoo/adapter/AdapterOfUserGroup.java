package com.animal.scale.hodoo.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.setting.user.group.list.UserGroupListActivity;
import com.animal.scale.hodoo.databinding.RequestUserGroupListviewBinding;
import com.animal.scale.hodoo.domain.InvitationUser;

import java.util.List;

public class AdapterOfUserGroup extends BaseAdapter {
    public interface OnBtnClickListener {
        void onDeclineBtn( int toIdx, int fromIdx );
        void onAcceptBtn( int toIdx, int fromIdx );
    }
    private String TAG = AdapterOfUserGroup.class.getSimpleName();
    private List<InvitationUser> mUsers;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private RequestUserGroupListviewBinding binding;
    private OnBtnClickListener mOnBtnClickListener;
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
    public View getView(final int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.request_user_group_listview, null);
            binding = DataBindingUtil.bind(convertView);
            binding.setUsers(mUsers.get(i));
            binding.setAdapter(this);
            convertView.setTag(binding);
        } else {
            binding = (RequestUserGroupListviewBinding) convertView.getTag();
            binding.setUsers(mUsers.get(i));
            binding.setAdapter(this);

        }
        if ( mUsers.get(i).getState() > 0 ) {
            binding.stateWrap.setVisibility(View.VISIBLE);
            binding.btnWrap.setVisibility(View.GONE);
            switch (mUsers.get(i).getState()) {
                case UserGroupListActivity.ACCEPT_TYPE:
                    binding.state.setText("Accept");
                    break;
                case UserGroupListActivity.DECLINE_TYPE:
                    binding.state.setText("Decline");
                    break;
            }

        } else {
            binding.btnWrap.setVisibility(View.VISIBLE);
            binding.stateWrap.setVisibility(View.GONE);
        }
        binding.declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( mOnBtnClickListener != null ) {
                    mOnBtnClickListener.onDeclineBtn(mUsers.get(i).getToUserIdx(), mUsers.get(i).getFromUserIdx());
                }
                Log.e(TAG, mUsers.get(i).getNickname());
            }
        });
        binding.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnBtnClickListener.onAcceptBtn(mUsers.get(i).getToUserIdx(), mUsers.get(i).getFromUserIdx());
            }
        });
        return binding.getRoot();
    }
    public void setOnBtnClickListener ( OnBtnClickListener onBtnClickListener ) {
        mOnBtnClickListener = onBtnClickListener;
    }
    public void setData ( List<InvitationUser> users ) {
        mUsers = users;
        notifyDataSetChanged();
    }
}
