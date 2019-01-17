package com.animal.scale.hodoo.activity.pet.regist.physique;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.weight.WeightCheckActivity;
import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.disease.DiseaseInformationRegistActivity;
import com.animal.scale.hodoo.base.BaseActivity;
import com.animal.scale.hodoo.databinding.ActivityPhysiqueInformationRegistBinding;
import com.animal.scale.hodoo.domain.ActivityInfo;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.util.ValidationUtil;
import com.github.javiersantos.bottomdialogs.BottomDialog;
import com.tistory.dwfox.dwrulerviewlibrary.utils.DWUtils;
import com.tistory.dwfox.dwrulerviewlibrary.view.DWRulerSeekbar;
import com.tistory.dwfox.dwrulerviewlibrary.view.ObservableHorizontalScrollView;
import com.tistory.dwfox.dwrulerviewlibrary.view.ScrollingValuePicker;

public class PhysiqueInformationRegistActivity extends BaseActivity<PhysiqueInformationRegistActivity> implements PhysiqueInformationRegistIn.View{

    public static Context mContext;

    ActivityPhysiqueInformationRegistBinding binding;

    public int petId;

    BottomDialog builder;

    private ScrollingValuePicker myScrollingValuePicker;
    private DWRulerSeekbar dwRulerSeekbar;

    private static final float MIN_VALUE = 5;
    private static final float MAX_VALUE = 33;
    private static final float LINE_RULER_MULTIPLE_SIZE = 3.5f;

    PhysiqueInformationRegistIn.Presenter presenter;

    private int petIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_physique_information_regist);
        binding.setActivity(this);
        binding.setActivityInfo(new ActivityInfo(getString(R.string.physique_information_regist_title)));
        super.setToolbarColor();
        mContext = this;
        presenter = new PhysiqueInformationRegistPresenter(this);
        presenter.loadData(PhysiqueInformationRegistActivity.this);
        presenter.setNavigation();
        Intent intent = getIntent();
        petIdx = intent.getIntExtra("petIdx", 0);
        presenter.getPhysiqueInformation(petIdx);
        setTextWatcher( binding.editWidth, binding.editHeight, binding.editWeight );
    }

    @Override
    protected BaseActivity<PhysiqueInformationRegistActivity> getActivityClass() {
        return PhysiqueInformationRegistActivity.this;
    }

    @Override
    public void setDiseaseInfo(PetPhysicalInfo petPhysicalInfo) {
        if(petPhysicalInfo != null){
            binding.setDomain(petPhysicalInfo);
        }else{
            binding.setDomain(new PetPhysicalInfo());
        }
        validation();
    }

    public void onClickWidthEt(View view){
        showRulerBottomDlg(binding.editWidth);
    }

    public void onClickHightEt(View view){
        presenter.showRulerBottomDlg(binding.editHeight);

    }
    public void onClickWeightEt(View view){
        presenter.showRulerBottomDlg(binding.editWeight);
    }

    @Override
    public void showRulerBottomDlg(final EditText editText){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.dialog_custom_ruler_popup, null);
        builder =  new BottomDialog.Builder(this)
                .setCustomView(customView)
                .setNegativeText(getString(R.string.confirm))
                .show();

        myScrollingValuePicker = (ScrollingValuePicker) customView.findViewById(R.id.myScrollingValuePicker);
        myScrollingValuePicker.setViewMultipleSize(LINE_RULER_MULTIPLE_SIZE);
        myScrollingValuePicker.setMaxValue(MIN_VALUE, MAX_VALUE);
        myScrollingValuePicker.setValueTypeMultiple(5);
        myScrollingValuePicker.getScrollView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    myScrollingValuePicker.getScrollView().startScrollerTask();
                }
                return false;
            }
        });
        myScrollingValuePicker.setOnScrollChangedListener(new ObservableHorizontalScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(ObservableHorizontalScrollView view, int l, int t) {
            }
            @Override
            public void onScrollStopped(int l, int t) {
                editText.setText(String.valueOf(DWUtils.getValueAndScrollItemToCenter(myScrollingValuePicker.getScrollView() , l , t , MAX_VALUE , MIN_VALUE , myScrollingValuePicker.getViewMultipleSize())));
            }
        });
    }

    @Override
    public void registPhysiqueInformation() {
        presenter.registPhysiqueInformation(petIdx, binding.getDomain());
    }

    @Override
    public void registResult(Integer result) {
        Intent intent = new Intent(getApplicationContext(), WeightCheckActivity.class);
        intent.putExtra("petIdx", petIdx);
        startActivity(intent);
        overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
//        finish();
    }

    public void onClickNextBtn(View view){
        presenter.deletePhysiqueInformation(petIdx, binding.getDomain().getId());
    }

    @Override
    public void setNavigation() {
        binding.addPetNavigation.basicBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
        binding.addPetNavigation.diseaseBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
        binding.addPetNavigation.physiqueBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
        binding.addPetNavigation.basicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Intent intent = new Intent(getApplicationContext(), BasicInformationRegistActivity.class);
                intent.putExtra("petIdx", petIdx);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();*/
            }
        });
        binding.addPetNavigation.diseaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(getApplicationContext(), DiseaseInformationRegistActivity.class);
                intent.putExtra("petIdx", petIdx);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();*/
            }
        });
       /* binding.addPetNavigation.physiqueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PhysiqueInformationRegistActivity.class);
                intent.putExtra("petIdx", petIdx);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });
        binding.addPetNavigation.weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WeightCheckActivity.class);
                intent.putExtra("petIdx", petIdx);
                startActivity(intent);
                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
                finish();
            }
        });*/
    }
    private void setTextWatcher ( EditText... edts ) {
        for ( EditText edt : edts ) {
            edt.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    validation();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }
    }
    private void validation () {
        if (
                !ValidationUtil.isEmpty( binding.editWidth.getText().toString() ) &&
                !ValidationUtil.isEmpty( binding.editHeight.getText().toString() ) &&
                !ValidationUtil.isEmpty( binding.editWeight.getText().toString() )) {
            binding.nextStep.setEnabled(true);
        } else {
            binding.nextStep.setEnabled(false);
        }

    }


}
