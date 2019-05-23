package com.animal.scale.hodoo.activity.pet.regist.fragment.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.animal.scale.hodoo.HodooApplication;
import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.activity.pet.regist.activity.PetRegistActivity;
import com.animal.scale.hodoo.adapter.AdapterOfPetUserSelectItem;
import com.animal.scale.hodoo.base.PetRegistFragment;
import com.animal.scale.hodoo.databinding.FragmentActivityQuestionBinding;
import com.animal.scale.hodoo.domain.CommonResponce;
import com.animal.scale.hodoo.domain.PetBasicInfo;
import com.animal.scale.hodoo.domain.PetUserSelectItem;
import com.animal.scale.hodoo.domain.PetUserSelectionQuestion;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by SongSeokwoo on 2019-04-24.
 */
public class ActivityQuestionFragment extends PetRegistFragment implements ActivityQuestionIn.View {
    private FragmentActivityQuestionBinding binding;
    private ActivityQuestionIn.Presenter presenter;

    private ArrayList<String> titleArr;
    private ArrayList<ArrayList<String>> childItems;
    private PetUserSelectionQuestion petUserSelectionQuestion;

    private int petIdx;

    public final int WALK_QUESTION_TYPE = 0;
    public final int ACTIVITY_QUESTION = 1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_activity_question, container, false);
        binding.setActivity(this);

        presenter = new ActivityQuestionPresenter(this);
        presenter.loadData(getContext());
        if ( getArguments() != null ) {
            petIdx = getArguments().getInt("petIdx");
            presenter.getPetUserSelectQuestion(petIdx);
        } else {
            petUserSelectionQuestion = new PetUserSelectionQuestion();
            setBtnEnable(false);
        }
        loadData(null);

        return binding.getRoot();
    }
    public static ActivityQuestionFragment newInstance() {
        return new ActivityQuestionFragment();
    }
    public void onClickCompleateBtn ( View v ) {
        if ( ((HodooApplication) getActivity().getApplication()).isExperienceState() ) {
            getActivity().finish();
            return;
        }
        ((PetRegistActivity) getActivity()).setPetUserSelectionQuestion(petUserSelectionQuestion);
        ((PetRegistActivity) getActivity()).regist();
    }

    private void loadData ( PetUserSelectionQuestion inPetUserSelectionQuestion ) {
        titleArr = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.activity_question_title)));
        ArrayList<PetUserSelectItem> items = new ArrayList<>();

        childItems = new ArrayList<>();
        childItems.add(new ArrayList<String>(Arrays.asList( getResources().getStringArray(R.array.walk_question) )));
        childItems.add(new ArrayList<String>(Arrays.asList( getResources().getStringArray(R.array.activity_question) )));

        for (int i = 0; i < titleArr.size(); i++) {
            PetUserSelectItem item = new PetUserSelectItem();
            item.setTitle(titleArr.get(i));
            if ( i == WALK_QUESTION_TYPE ) {
                item.setChildItem(new ArrayList<String>(Arrays.asList( getResources().getStringArray(R.array.walk_question) )));
            } else if ( i == ACTIVITY_QUESTION ) {
                item.setChildItem(new ArrayList<String>(Arrays.asList( getResources().getStringArray(R.array.activity_question) )));
            }
            items.add(item);
        }
        AdapterOfPetUserSelectItem adapter = new AdapterOfPetUserSelectItem(getContext(), items, new AdapterOfPetUserSelectItem.ItemClickListener() {
            @Override
            public void OnClickListener(final int position, final View view) {
                final ArrayAdapter<String> alertAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
                for (String item:childItems.get(position)
                        ) {
                    alertAdapter.add(item);
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                        .setTitle(titleArr.get(position))
                        .setAdapter(alertAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                switch (position) {
                                    case WALK_QUESTION_TYPE :
                                        petUserSelectionQuestion.setPlayTime(i);
                                        break;
                                    case ACTIVITY_QUESTION :
                                        petUserSelectionQuestion.setActive(i);
                                        break;
                                }
                                ((EditText) view).setText( alertAdapter.getItem(i) );
                                setBtnEnable(validation());
                            }
                        });
                builder.create().show();
            }
        });
//        binding.activityQuestionGridWrap
//        binding.activityQuestionGridWrap.setAdapter( adapter );
        binding.activityQuestionWrap.setAdapter(adapter);

        if ( inPetUserSelectionQuestion != null ) {
            items.get(WALK_QUESTION_TYPE).setSelectNum( inPetUserSelectionQuestion.getPlayTime() );
            items.get(ACTIVITY_QUESTION).setSelectNum( inPetUserSelectionQuestion.getActive() );
            adapter.notifyDataSetChanged();
//            binding.activityQuestionWrap.
        }
    }

    @Override
    public void setPetUserSelectQuestion(CommonResponce<PetUserSelectionQuestion> petUserSelectQuestion) {
        if ( petUserSelectQuestion.domain == null ) {
            petUserSelectionQuestion = new PetUserSelectionQuestion();
            loadData( null );
        } else {
            petUserSelectionQuestion = petUserSelectQuestion.domain;
            loadData(petUserSelectionQuestion);
        }
    }
    public boolean validation() {
        for (int i = 0; i < binding.activityQuestionWrap.getChildCount(); i++) {
            LinearLayout wrap = (LinearLayout) binding.activityQuestionWrap.getChildAt(i);
            for (int j = 0; j < wrap.getChildCount(); j++) {
                if ( !(wrap.getChildAt(i) instanceof EditText) )
                    continue;
                EditText edt = (EditText) wrap.getChildAt(i);
                if ( edt.getText().toString().equals("") )
                    return false;

            }
        }
        if ( binding.activityQuestionWrap.getChildCount() == 0 )
            return false;
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

    @Override
    public void setNavigation() {
//        binding.addPetNavigation.basicBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
//        binding.addPetNavigation.diseaseBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
//        binding.addPetNavigation.physiqueBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
//        binding.addPetNavigation.weightBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
//        binding.addPetNavigation.activityBtn.setBackgroundResource(R.drawable.rounded_pink_btn);
    }

    @Override
    public void setPetBasicInfo(PetBasicInfo petBasicInfo) {
        super.setPetBasicInfo(petBasicInfo);
        loadData(petUserSelectionQuestion != null ? petUserSelectionQuestion : null);
        setBtnEnable(petUserSelectionQuestion != null ? true : false);
    }
}
