package com.animal.scale.hodoo.activity.setting.user.account;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.UserAccountGridBinding;
import com.animal.scale.hodoo.domain.User;

import java.util.List;
import java.util.regex.Pattern;

public class UserAccountGridAdapter extends BaseAdapter{
    Activity activity;
    private LayoutInflater inflater;
    private List<User> data;
    private List<User> convertUser;

    private int KO_TYPE = 0;

    UserAccountGridBinding binding;

    public UserAccountGridAdapter(Activity activity, List<User> data) {
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        convertUser = data;
        for (int i = 0; i < convertUser.size(); i++) {
            convertUser.get(i).setNickname( matches( convertUser.get(i).getNickname() ) );
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.user_account_grid, null);
            binding = DataBindingUtil.bind(convertView);
            binding.setDomain(convertUser.get(position));
            convertView.setTag(binding);
        } else {
            binding = (UserAccountGridBinding) convertView.getTag();
            if ( position == 0 ) {
//                binding.placeholder.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                binding.placeholder.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2eabf7")));
            }
            binding.setDomain(convertUser.get(position));
        }
        return binding.getRoot();
    }
    private String matches ( String name ) {
        String convertName = "";
        int endNum = 1;
        if ( Pattern.matches("^[ㄱ-ㅎ가-힣]*$", name) ) {
            endNum = 2;
        } else if ( Pattern.matches("^[a-zA-Z]*$", name) ) {
            name = name.toUpperCase(); //세로 가운데 정렬을 위한 대문자 처리
            endNum = 1;
        }
        convertName = name.substring(0, endNum);
        return convertName;
    }
}
