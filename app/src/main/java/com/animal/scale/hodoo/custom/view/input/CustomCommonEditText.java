package com.animal.scale.hodoo.custom.view.input;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.util.VIewUtil;

public class CustomCommonEditText extends LinearLayout implements CustomCommonEditTextIn {

    Context context;

    TextView titleView;

    TextView errorMessageView;

    String titleMessage;

    int contentAttrResourceId;

    float titleSize;

    float messageSize;

    public EditText editText;

    boolean status = false;
    boolean focusble = false;

    String hint;
    String text;


    public CustomCommonEditText(Context context) {
        super(context);
        this.context = context;
    }

    public CustomCommonEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getAttrs(attrs);
    }

    public CustomCommonEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        this.context = context;
        getAttrs(attrs, defStyle);
    }

    public void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CommonEditText);
         setTypeArray(typedArray);
    }

    public void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CommonEditText, defStyle, 0);
        setTypeArray(typedArray);
    }

    public void setTypeArray(TypedArray typedArray) {
        contentAttrResourceId = typedArray.getResourceId(R.styleable.CommonEditText_editTextResourceId, 0);
        titleMessage = typedArray.getString(R.styleable.CommonEditText_title);
        titleSize = typedArray.getFloat(R.styleable.CommonEditText_titleSize, 0);
        messageSize = typedArray.getFloat(R.styleable.CommonEditText_errorMessageSize, 0);
        hint = typedArray.getString(R.styleable.CommonEditText_hint);
        focusble = typedArray.getBoolean(R.styleable.CommonEditText_edtFocusble, true);
        text = typedArray.getString(R.styleable.CommonEditText_edtText);
        initView();
        typedArray.recycle();
    }
    public void setOnEdtClick (  ) {

    }

    public void initView() {
        this.setOrientation(LinearLayout.VERTICAL);
        this.addView(createTitleView(titleMessage));
        this.createContentView();
        this.addView(createErrorMessageView());
        errorMessageView.setVisibility(View.GONE);
    }

    public View createTitleView(String titleMessage) {
        VIewUtil vIewUtil = new VIewUtil(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(vIewUtil.numberToDP(5),vIewUtil.numberToDP(0),vIewUtil.numberToDP(0),vIewUtil.numberToDP(0));
        titleView = new TextView(context);
        titleView.setText(titleMessage);
        titleView.setTypeface(Typeface.DEFAULT_BOLD);
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSize);
        titleView.setLayoutParams(params);
        titleView.setTextColor(Color.parseColor("#9b9b9b"));
        titleView.setBackground(null);
        return titleView;
    }

    public void createContentView(){
        VIewUtil vIewUtil = new VIewUtil(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(vIewUtil.numberToDP(0),vIewUtil.numberToDP(-5),vIewUtil.numberToDP(0),vIewUtil.numberToDP(-5));

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(infService);

        LinearLayout contentView = (LinearLayout) inflater.inflate(contentAttrResourceId, this, false);
        editText = contentView.findViewById(R.id.edittext);
        if ( text != null && !text.equals("") )
            editText.setText(text);
        editText.setHint(hint);
        editText.setFocusable(focusble);
        editText.setLayoutParams(params);
        this.addView(contentView);
    }

    public View createErrorMessageView() {
        VIewUtil vIewUtil = new VIewUtil(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        params.setMargins(vIewUtil.numberToDP(5),vIewUtil.numberToDP(0),vIewUtil.numberToDP(0),vIewUtil.numberToDP(0));
        errorMessageView = new TextView(context);
        errorMessageView.setTypeface(Typeface.DEFAULT_BOLD);
        errorMessageView.setTextSize(TypedValue.COMPLEX_UNIT_SP, messageSize);
        errorMessageView.setTextColor(context.getColor(R.color.mainRed));
        errorMessageView.setLayoutParams(params);
        return errorMessageView;
    }

    @Override
    public void setTitle(String titleMessage) {
        titleView.setText(titleMessage);
    }

    @Override
    public void setErrorMessage(String message) {
        errorMessageView.setText(message);
    }

    @Override
    public void setErrorMessageViewisExposed(Boolean exposed) {
        if(true){
            errorMessageView.setVisibility(View.VISIBLE);
        }else{
            errorMessageView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean getStatus() {
        return status;
    }

    @Override
    public String getText() {
        return editText.getText().toString();
    }

    public String getTitleMessage () {
        return titleMessage;
    }

    public void changeContentBg(int drawable) {

    }
}
