package com.animal.scale.hodoo.activity.setting.user.account;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.UserAccountGridBinding;
import com.animal.scale.hodoo.domain.User;
import com.animal.scale.hodoo.util.VIewUtil;

import java.util.List;
import java.util.regex.Pattern;

public class UserAccountGridAdapter extends BaseAdapter{
    private String TAG = UserAccountGridAdapter.class.getSimpleName();
    Activity activity;
    private LayoutInflater inflater;
    private List<User> data;

    UserAccountGridBinding binding;

    private int mIdx;
    private boolean editState = false;
    private EditBtnClickListener clickListener;

    private ImageView[] removeBtns;
    public interface EditBtnClickListener {
        void onClick(User user);
    }

    public UserAccountGridAdapter(Activity activity, int idx, List<User> data) {
        this.activity = activity;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        removeBtns = new ImageView[data.size()];
        mIdx = idx;
        setConvertNicName(this.data);
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
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(R.layout.user_account_grid, null);
            binding = DataBindingUtil.bind(convertView);
            binding.setDomain(data.get(position));

            convertView.setTag(binding);
        } else {
            binding = (UserAccountGridBinding) convertView.getTag();
            binding.setDomain(data.get(position));
        }

        if ( data.get(position).getUserIdx() == mIdx ) {
            binding.placeholder.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(activity.getApplicationContext(), R.color.mainRed)));
        } else {
            if ( editState ) {
                binding.removeBtn.setVisibility(View.VISIBLE);
                binding.removeBtn.animate().scaleX(1).scaleY(1);
            } else {
                binding.removeBtn.animate().scaleX(0).scaleY(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < removeBtns.length; i++) {
                            removeBtns[i].setVisibility(View.GONE);
                        }
                    }
                });
            }

            if (data.get(position).getAccessType() == 1) {
                binding.masterBadge.setVisibility(View.VISIBLE);
            }
        }

        removeBtns[position] = binding.removeBtn;

        binding.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( clickListener != null )
                    clickListener.onClick(data.get(position));
            }
        });

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
    public void setEditState( boolean state ) {
        editState = state;
        notifyDataSetChanged();
    }
    public void setClickListener ( EditBtnClickListener clickListener ) {
        this.clickListener = clickListener;
    }
    public void setData ( List<User> newData ) {
        data = newData;
        setConvertNicName(data);
        notifyDataSetChanged();
    }
    public void setConvertNicName( List<User> data ) {
        for (int i = 0; i < data.size(); i++) {
            if ( i == 0 ) {
                data.get(i).setConvertNickname( data.get(i).getNickname() );
                data.get(i).setNickname("");
                continue;
            }
            data.get(i).setConvertNickname( matches( data.get(i).getNickname() ) );
        }
        this.data = data;
    }

}
