package com.animal.scale.hodoo.custom.binding;

import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.common.SharedPrefManager;
import com.animal.scale.hodoo.common.SharedPrefVariable;
import com.animal.scale.hodoo.util.CheckConnect;
import com.animal.scale.hodoo.util.MathUtil;
import com.animal.scale.hodoo.util.ValidationUtil;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomBinding {


    @BindingAdapter({"imageUrlOne"})
    public static void loadImageOne(ImageView imageView, int bcs) {
        if (bcs == 0) {
            imageView.setImageResource(R.drawable.self_check_middle_1_step_red_98_45);
        } else {
            imageView.setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
        }
    }

    @BindingAdapter({"imageUrlTwo"})
    public static void loadImageTwo(ImageView imageView, int bcs) {
        if (bcs == 1) {
            imageView.setImageResource(R.drawable.self_check_middle_2_step_red_98_45);
        } else {
            imageView.setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
        }
    }

    @BindingAdapter({"imageUrlThree"})
    public static void loadImageThree(ImageView imageView, int bcs) {
        if (bcs == 2) {
            imageView.setImageResource(R.drawable.self_check_middle_3_step_red_98_45);
        } else {
            imageView.setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
        }
    }

    @BindingAdapter({"imageUrlFour"})
    public static void loadImageFour(ImageView imageView, int bcs) {
        if (bcs == 3) {
            imageView.setImageResource(R.drawable.self_check_middle_4_step_red_98_45);
        } else {
            imageView.setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
        }
    }

    @BindingAdapter({"imageUrlFive"})
    public static void loadImageFive(ImageView imageView, int bcs) {
        if (bcs == 4) {
            imageView.setImageResource(R.drawable.self_check_middle_5_step_red_98_45);
        } else {
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

    @BindingAdapter({"loadPicasoImage"})
    public static void loadPicasoImage(ImageView imageView, String url) {
        if (url.matches("add")) {
            Picasso.with(imageView.getContext())
                    .load(R.drawable.pet_account_midle_add_icon_143_143)
                    .into(imageView);
        } else {
            Picasso.with(imageView.getContext())
                    .load(SharedPrefVariable.SERVER_ROOT + url)
                    .into(imageView);
        }

    }

    @BindingAdapter({"loadUserAccountImage"})
    public static void loadUserAccountImage(ImageView imageView, String sex) {
        if (sex.matches(imageView.getContext().getString(R.string.woman))) {
            Picasso.with(imageView.getContext())
                    .load(R.drawable.user_middle_hodoo_profile_icon_143_143)
                    .into(imageView);
        } else if (sex.matches(imageView.getContext().getString(R.string.men))) {
            Picasso.with(imageView.getContext())
                    .load(R.drawable.user_middle_jehoo_profile_icon_143_143)
                    .into(imageView);
        } else if (sex.matches(imageView.getContext().getString(R.string.istyle_new_group_user))) {
            Picasso.with(imageView.getContext())
                    .load(R.drawable.pet_account_midle_add_icon_143_143)
                    .into(imageView);
        }
    }


    @BindingAdapter({"changeLinearBg"})
    public static void LinearLayoutChangeBackgound(LinearLayout leLinearLayout, int position) {
        /*if (position % 2 == 0) {
            leLinearLayout.setBackgroundColor(Color.parseColor("#fbe7e8"));
        } else {
            leLinearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }*/
        leLinearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @BindingAdapter({"conFloatToString", "intake",  "unitIndex",  "unitString"})
    public static void converterFlotToStringForEditText(TextView textView, float value, float intake, int unitIndex, String unitString) {
        if(unitString.matches("g")){
            textView.setText(intake + unitString + "/" + MathUtil.DecimalCut((value * 0.01) * intake) + " Kcal");
        }else if(unitString.matches(textView.getContext().getString(R.string.ea))){
            textView.setText(intake + unitString + "/" + MathUtil.DecimalCut(value * intake) + " Kcal");
        }else if(unitString.matches(textView.getContext().getString(R.string.cup))){
            textView.setText(intake + unitString + "/" + MathUtil.DecimalCut(value * intake) + " Kcal");
        }

    }

    @BindingAdapter({"conOnlyFloatToString"})
    public static void converterOnlyFlotToStringForEditText(TextView textView, float value) {
        textView.setText(String.valueOf(value) + " Kcal");
    }

    @BindingAdapter({"conOnlyFloatToStringPersent"})
    public static void conOnlyFloatToStringPersent(TextView textView, float value) {
        textView.setText(String.valueOf(value) + " %");
    }

    @BindingAdapter({"dateTimeConverter"})
    public static void dateTimeConverter(TextView textView, String value) {
        Date date;
        String strDate;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(value);
            DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
            strDate = dateFormat.format(date);
            textView.setText(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @BindingAdapter({"isCurrentPet", "petIdx"})
    public static void currentPetCheck(RadioButton checkBox, int isCurrentPet, int petIdx){
       SharedPrefManager mSharedPrefManager = SharedPrefManager.getInstance(checkBox.getContext());
        if(mSharedPrefManager.getIntExtra(SharedPrefVariable.CURRENT_PET_IDX) == petIdx){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }
    }
}
