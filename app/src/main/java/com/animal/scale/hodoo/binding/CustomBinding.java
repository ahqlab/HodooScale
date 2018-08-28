package com.animal.scale.hodoo.binding;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.animal.scale.hodoo.R;

public class CustomBinding {

    @BindingAdapter({"imageUrlOne"})
    public static void loadImageOne(ImageView imageView, int bcs) {
        if(bcs == 0){
            imageView.setImageResource(R.drawable.self_check_middle_1_step_red_98_45);
        }else{
            imageView.setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
        }
    }

    @BindingAdapter({"imageUrlTwo"})
    public static void loadImageTwo(ImageView imageView, int bcs) {
        if(bcs == 1){
            imageView.setImageResource(R.drawable.self_check_middle_2_step_red_98_45);
        }else{
            imageView.setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
        }
    }

    @BindingAdapter({"imageUrlThree"})
    public static void loadImageThree(ImageView imageView, int bcs) {
        if(bcs == 2){
            imageView.setImageResource(R.drawable.self_check_middle_3_step_red_98_45);
        }else{
            imageView.setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
        }
    }

    @BindingAdapter({"imageUrlFour"})
    public static void loadImageFour(ImageView imageView, int bcs) {
        if(bcs == 3){
            imageView.setImageResource(R.drawable.self_check_middle_4_step_red_98_45);
        }else{
            imageView.setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
        }
    }

    @BindingAdapter({"imageUrlFive"})
    public static void loadImageFive(ImageView imageView, int bcs) {
        if(bcs == 4){
            imageView.setImageResource(R.drawable.self_check_middle_5_step_red_98_45);
        }else{
            imageView.setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
        }
    }

    @BindingAdapter({"setViewFlipper"})
    public static void setViewFlipper(ViewFlipper flipper, int bcs) {
        flipper.setDisplayedChild(bcs);
    }

    @BindingAdapter({"imgRes"})
    public static void imgload(ImageView imageView, int resid) {
        imageView.setImageResource(resid);
    }

    @BindingAdapter({"changeLinearBg"})
    public static void LinearLayoutChangeBackgound(LinearLayout leLinearLayout, int position) {
        if(position % 2 == 0){
            leLinearLayout.setBackgroundColor(Color.parseColor("#fbe7e8"));
        }else{
            leLinearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }
}
