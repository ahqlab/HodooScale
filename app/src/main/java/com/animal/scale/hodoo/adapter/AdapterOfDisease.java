package com.animal.scale.hodoo.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.domain.Disease;
import com.animal.scale.hodoo.domain.PetChronicDisease;
import com.google.android.flexbox.AlignSelf;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

public class AdapterOfDisease extends RecyclerView.Adapter<AdapterOfDisease.ItemViewHolder> {
    private final String TAG = AdapterOfDisease.class.getSimpleName();
    public List<Disease> mItems;
    private PetChronicDisease mPetChronicDisease;
    int number = 0;
    public AdapterOfDisease ( List<Disease> items, PetChronicDisease petChronicDisease ) {
        mItems = items;
        mPetChronicDisease = petChronicDisease;
        if ( mPetChronicDisease != null )
            number = mPetChronicDisease.getDiseaseName();
        else
            number = 0;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_item, parent, false);
        return new ItemViewHolder(view);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        holder.name.setText(mItems.get(position).getName());
        if ( (number & (0x01<<position)) != 0 ) {
            holder.name.setChecked(true);
        }
        holder.name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ViewGroup linear = (ViewGroup) holder.name.getParent();
                ViewGroup parent = (ViewGroup) linear.getParent();

                if ( position != 0 ) {
                    ViewGroup inLinear = (ViewGroup) parent.getChildAt(0);
                    CheckBox checkBox = (CheckBox) inLinear.getChildAt(0);
                    checkBox.setChecked(false);
                    if ( b )
                        number += (0x01<<position);
                    else {
                        boolean isChecked = false;
                        for (int i = 0; i < parent.getChildCount(); i++) {
                            if ( parent.getChildAt(i) instanceof LinearLayout ) {
                                inLinear = (ViewGroup) parent.getChildAt(i);
                                checkBox = (CheckBox) inLinear.getChildAt(0);
                                if ( checkBox.isChecked() ) {
                                    isChecked = true;
                                    break;
                                }
                            }
                        }
                        if ( !isChecked ) {
                            inLinear = (ViewGroup) parent.getChildAt(0);
                            checkBox = (CheckBox) inLinear.getChildAt(0);
                            checkBox.setChecked(true);
                            return;
                        }
                        number -= (0x01<<position);
                    }

                }
                if ( position == 0 ) {
                    for (int i = 0; i < parent.getChildCount(); i++) {
                        if ( parent.getChildAt(i) instanceof LinearLayout ) {
                            ViewGroup inLinear = (ViewGroup) parent.getChildAt(i);
                            CheckBox checkBox = (CheckBox) inLinear.getChildAt(0);
                            if ( compoundButton != checkBox ) {
                                checkBox.setChecked(false);
                                number = 1;
                            }

                        }
                    }
                    if ( !b )
                        number -= (0x01<<position);
                }
                compoundButton.setChecked(b);
            }
        });
        ViewGroup.LayoutParams lp = holder.name.getLayoutParams();
        if (lp instanceof FlexboxLayoutManager.LayoutParams) {
            FlexboxLayoutManager.LayoutParams flexboxLp = (FlexboxLayoutManager.LayoutParams) lp;
            flexboxLp.setFlexGrow(2.0f);
            flexboxLp.setAlignSelf(AlignSelf.FLEX_END);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
    public int getCheckNumber() {
        return number;
    }
    public Disease getItem ( int position ) {
        return mItems.get(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private CheckBox name;
        public ItemViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.disease_name);
        }
    }
}
