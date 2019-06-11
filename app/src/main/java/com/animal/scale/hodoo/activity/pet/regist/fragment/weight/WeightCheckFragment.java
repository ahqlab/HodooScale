package com.animal.scale.hodoo.activity.pet.regist.fragment.weight;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.NumberPicker;

import com.animal.scale.hodoo.HodooApplication;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.home.activity.HomeActivity;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.basic.BasicInformationRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.disease.DiseaseInformationRegistActivity;
import com.animal.scale.hodoo.activity.pet.regist.physique.PhysiqueInformationRegistActivity;
import com.animal.scale.hodoo.adapter.AdapterOfBfi;
import com.animal.scale.hodoo.base.PetRegistFragment;
import com.animal.scale.hodoo.databinding.FragmentWeightCheckBinding;
import com.animal.scale.hodoo.domain.BfiModel;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.PetWeightInfo;
import com.animal.scale.hodoo.util.VIewUtil;
import com.google.android.youtube.player.YouTubePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SongSeokwoo on 2019-04-02.
 *
 * 2019-05-28 현재 Weight 값이 아닌 BFI값 등록으로 변경되어 사용됨
 */
public class WeightCheckFragment extends PetRegistFragment implements WeightCheckIn.View {

    private String TAG = WeightCheckFragment.class.getSimpleName();

    public static Context mContext;
    //뷰플리퍼
    //인덱스
    List<ImageView> indexes;

    FragmentWeightCheckBinding binding;


    WeightCheckIn.Presenter presenter;

    private int petIdx;

    YouTubePlayer.OnInitializedListener listener;

    private boolean deleteAllPet;
    private int petType;
    private AlertDialog.Builder builder;
    private int[] result;

    private int count = 0;
    private int maxCount = 0;

    private ArrayList<String> items;
    private List<BfiModel> bfis;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weight_check, container, false);
        binding.setActivity(this);
        presenter = new WeightCheckPresenter(this);
        presenter.loadData(getContext());
//        presenter.setNavigation();

        if ( getArguments() != null ) {
            petIdx = getArguments().getInt("petIdx");
            deleteAllPet = getArguments().getBoolean("deleteAllPet");
        } else {
            petIdx = 0;
            deleteAllPet = false;
        }
        presenter.getWeightInformation(petIdx);

        return binding.getRoot();
    }

    public static PetRegistFragment newInstance() {
        return new WeightCheckFragment();
    }

    public AlertDialog.Builder showBasicOneBtnPopup(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(title != null){
            builder.setTitle(title);
        }
        if(message != null){
            builder.setMessage(message);
        }
        return builder;
    }

    @Override
    public void setDomain(PetWeightInfo petWeightInfo) {
        if (petWeightInfo != null) {
            binding.setDomain(petWeightInfo);
            setNumberPicker(binding.bcs, petWeightInfo);
        } else {
            binding.setDomain(new PetWeightInfo());
            setNumberPicker(binding.bcs);
        }
    }

    private void setNumberPicker(NumberPicker numberPicker, PetWeightInfo petWeightInfo) {
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        numberPicker.setValue(petWeightInfo.getBcs());
        numberPicker.setWrapSelectorWheel(true);
        //Set a value change listener for NumberPicker
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                binding.getDomain().setBcs(newVal);
            }
        });
    }

    private void setNumberPicker(NumberPicker numberPicker) {
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(5);
        numberPicker.setWrapSelectorWheel(true);
        //Set a value change listener for NumberPicker
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                binding.getDomain().setBcs(newVal);
            }
        });
    }

    /**
     * 펫의 bfi를 등록한다.
     *
     * @param
     * @return
     * @description
    */
    @Override
    public void registWeightInformation() {
        presenter.registWeightInfo(petIdx, binding.getDomain());
    }


    @Override
    public void registResult(Integer integer) {
        //Log.e("HJLEE", "deleteAllPet : " + deleteAllPet);
        if (integer != 0) {
            ((BasicInformationRegistActivity) BasicInformationRegistActivity.mContext).finish();
            ((DiseaseInformationRegistActivity) DiseaseInformationRegistActivity.mContext).finish();
            ((PhysiqueInformationRegistActivity) PhysiqueInformationRegistActivity.mContext).finish();
            if(deleteAllPet){
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);
            }
//            finish();
        }
    }

    /**
     * bfi 질문을 출력한다.
     *
     * @param bfis   가져온 bfi의 리스트
     * @return
     * @description
    */
    @Override
    public void setQuestion(final List<BfiModel> bfis) {
        boolean editType = false;
        maxCount = bfis.size();
        this.bfis = bfis;
        binding.mainTitle.setText( bfis.get(count).getQuestion() );

        if ( result == null )
            result = new int[3];

        int[] temp = new int[0];

        PetBasicInfo basicInfo = ((PetRegistActivity) getActivity()).getPetBasicInfo();
        if ( basicInfo.getSelectedBfi() != null && petType == ((PetRegistActivity) getActivity()).getPetBasicInfo().getPetType() ) {
            temp = new int[bfis.size()];
            editType = true;
            String[] split = basicInfo.getSelectedBfi().replaceAll(" ", "").split(",");
            for (int i = 0; i < split.length; i++) {
                temp[i] = Integer.parseInt(split[i]);
                result[i] = Integer.parseInt(split[i]) + 1;
            }
        }
        items = new ArrayList<>();
        for (int i = 0; i < bfis.get(count).getAnswers().size(); i++) {
            items.add( bfis.get(count).getAnswers().get(i).getAnswer() );
        }
        AdapterOfBfi adapter = new AdapterOfBfi(getContext(),items , result[count], new AdapterOfBfi.OnCheckedListener() {

            @Override
            public void onItemChecked(int position, View v) {
//                        CheckBox checkBox = (CheckBox) v;
//                        checkBox.setChecked(true);
                        result[count] = position + 1;
            }
        });
        binding.bfiView.setAdapter( adapter );
//        setBtnEnable(validation());
    }

    /**
     * 선택한 bfi를 액티비티에 전달한다.
     *
     * @param
     * @return
     * @description   2019-05-28 현재 bfi값으로 단계로 변환하는 알고리즘은 사용하지않음
    */
    public void onClickCompleateBtn(View view) {

        if ( count != maxCount - 1 ) {
            changeStep( 1 );
            return;
        }

        if ( ((HodooApplication) getActivity().getApplication()).isExperienceState() ) {
            ((PetRegistActivity) getActivity()).nextFragment();
            return;
        }

        double operandTarget = result.length;
        double operand = 0;
        int[] data = new int[result.length];
        int[] finishResult = new int[result.length];
        String selectedBfi = "";
        for (int i = 0; i < result.length; i++) {
            data[i] = i + 1;
            operand += result[i];
            finishResult[i] = result[i] - 1;
        }
        double resultOperand = operand / operandTarget;

        double min = Double.MAX_VALUE;    // 기준데이터 최소값 - Interger형의 최대값으로 값을 넣는다.
        int bfi = 0;                       // 가까운 값을 저장할 변수
        // 2. process
        for(int i=0;i<data.length;i++){
            double a = Math.abs(data[i]-resultOperand);  // 절대값을 취한다.
            if(min > a){
                min = a;
                bfi = data[i];
            }
        }
        bfi = (bfi + 1) * 10;
        PetWeightInfo info = binding.getDomain();
        info.setBcs(bfi);
        String bfiStr = "";
        for (int i = 0; i < finishResult.length; i++) {
            bfiStr += finishResult[i];
            if ( i != finishResult.length - 1 )
                bfiStr += ",";

        }
        info.setSelectedBfi(bfiStr);
        binding.setDomain(info);

        if ( !validation() )
            return;

        if (binding.getDomain().getBcs() > 0) {
            ((PetRegistActivity) getActivity()).setPetWeightInfo(binding.getDomain());
            ((PetRegistActivity) getActivity()).regist();
//            presenter.deleteWeightInfo(petIdx, binding.getDomain().getId());
        } else {
            showBasicOneBtnPopup(null, getResources().getString(R.string.istyle_required_select_bcs_message))
                    .setPositiveButton(getResources().getString(R.string.istyle_required_select_bcs_message), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }
                    ).show();
        }
    }

    /*@Override
    public void setViewFlipper() {
        //인덱스리스트
        indexes = new ArrayList<>();
        indexes.add(binding.depth1);
        indexes.add(binding.depth2);
        indexes.add(binding.depth3);
        indexes.add(binding.depth4);
        indexes.add(binding.depth5);
        //inflate
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view1 = inflater.inflate(R.layout.viewflipper1, binding.flipper, false);
        View view2 = inflater.inflate(R.layout.viewflipper2, binding.flipper, false);
        View view3 = inflater.inflate(R.layout.viewflipper3, binding.flipper, false);
        View view4 = inflater.inflate(R.layout.viewflipper4, binding.flipper, false);
        View view5 = inflater.inflate(R.layout.viewflipper5, binding.flipper, false);
        //inflate 한 view 추가
        binding.flipper.addView(view1);
        binding.flipper.addView(view2);
        binding.flipper.addView(view3);
        binding.flipper.addView(view4);
        binding.flipper.addView(view5);
        //리스너설정 - 좌우 터치시 화면넘어가기
        binding.flipper.setOnTouchListener(new ViewFlipperAction(this, binding.flipper));
    }



    public void setDisplayFirst(View view) {
        binding.flipper.setDisplayedChild(0);
        binding.getDomain().setBcs(0);
        indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_red_98_45);
        indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
        indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
        indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
        indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
    }

    public void setDisplaySecond(View view) {
        binding.flipper.setDisplayedChild(1);
        binding.getDomain().setBcs(1);
        indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
        indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_red_98_45);
        indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
        indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
        indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
    }

    public void setDisplayThird(View view) {
        binding.flipper.setDisplayedChild(2);
        binding.getDomain().setBcs(2);
        indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
        indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
        indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_red_98_45);
        indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
        indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
    }

    public void setDisplayFour(View view) {
        binding.flipper.setDisplayedChild(3);
        binding.getDomain().setBcs(3);
        indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
        indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
        indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
        indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_red_98_45);
        indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
    }

    public void setDisplayFive(View view) {
        binding.flipper.setDisplayedChild(4);
        binding.getDomain().setBcs(4);
        indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
        indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
        indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
        indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
        indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_red_98_45);
    }


    @Override
    public void onFlipperActionCallback(int position) {
        if (position == 0) {
            //binding.getDomain().setBcs(0);
            indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_red_98_45);
            indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
            indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
            indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
            indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
        } else if (position == 1) {
           // binding.getDomain().setBcs(1);
            indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
            indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_red_98_45);
            indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
            indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
            indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
        } else if (position == 2) {
            //binding.getDomain().setBcs(2);
            indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
            indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
            indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_red_98_45);
            indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
            indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
        } else if (position == 3) {
           //binding.getDomain().setBcs(3);
            indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
            indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
            indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
            indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_red_98_45);
            indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_grey_98_45);
        } else if (position == 4) {
            //binding.getDomain().setBcs(4);
            indexes.get(0).setImageResource(R.drawable.self_check_middle_1_step_grey_98_45);
            indexes.get(1).setImageResource(R.drawable.self_check_middle_2_step_grey_98_45);
            indexes.get(2).setImageResource(R.drawable.self_check_middle_3_step_grey_98_45);
            indexes.get(3).setImageResource(R.drawable.self_check_middle_4_step_grey_98_45);
            indexes.get(4).setImageResource(R.drawable.self_check_middle_5_step_red_98_45);
        }
    }*/


    @Override
    public void setNavigation() {
//        binding.addPetNavigation.basicBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
//        binding.addPetNavigation.diseaseBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
//        binding.addPetNavigation.physiqueBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
//        binding.addPetNavigation.weightBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
//        binding.addPetNavigation.basicBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /*Intent intent = new Intent(getApplicationContext(), BasicInformationRegistActivity.class);
//                intent.putExtra("petIdx", petIdx);
//                startActivity(intent);
//                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
//                finish();*/
//            }
//        });
//        binding.addPetNavigation.diseaseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /*Intent intent = new Intent(getApplicationContext(), DiseaseInformationRegistActivity.class);
//                intent.putExtra("petIdx", petIdx);
//                startActivity(intent);
//                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
//                finish();*/
//            }
//        });
//        binding.addPetNavigation.physiqueBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /*Intent intent = new Intent(getApplicationContext(), PhysiqueInformationRegistActivity.class);
//                intent.putExtra("petIdx", petIdx);
//                startActivity(intent);
//                overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
//                finish();*/
//            }
//        });
        /*binding.addPetNavigation.weightBtn.setOnClickListener(new View.OnClickListener() {
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
    public void setPetIdx ( int petType ) {
        if ( petType == 0 || petType < 0 )
            petType = 1;
        if ( this.petType != petType ) {
            binding.bfiWrap.removeAllViews();
            result = null;
            this.petType = petType;
            presenter.getBfiQuestion(VIewUtil.getMyLocationCode(getContext()), petType);
        }
        binding.bfiDesc.setText( ((PetRegistActivity) getActivity()).getPetBasicInfo().getPetName() + getContext().getString(R.string.bfi_desc) );
    }
    public boolean validation () {
        for (int i = 0; i < result.length; i++) {
            if ( result[i] == 0 )
                return false;
        }
        return true;

    }
    private void setBtnEnable(boolean state) {
        binding.nextStep.setEnabled(state);
        if (binding.nextStep.isEnabled()) {
            binding.nextStep.setTextColor(ContextCompat.getColor(getContext(), android.R.color.white));
        } else {
            binding.nextStep.setTextColor(ContextCompat.getColor(getContext(), R.color.mainRed));
        }
    }
    public boolean backState () {
        return count != 0;
    }
    public void changeStep( int state ) {
        if ( state > 0 )
            count++;
        else
            count--;
        binding.mainTitle.setText( bfis.get(count).getQuestion() );
//        binding.subTitle.setText( bfis.get(count).getInfo() );
        items.clear();
        for (int i = 0; i < bfis.get(count).getAnswers().size(); i++) {
            items.add( bfis.get(count).getAnswers().get(i).getAnswer() );
        }
        AdapterOfBfi adapter = (AdapterOfBfi) binding.bfiView.getAdapter();
        adapter.setSelected( result[count] );
        adapter.notifyDataSetChanged();
        binding.bfiView.setAdapter(adapter);
        return;
    }


}
