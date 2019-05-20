package com.animal.scale.hodoo.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.LayoutNumberKeyboardBinding;
import com.animal.scale.hodoo.domain.PetAllInfos;
import com.animal.scale.hodoo.domain.PetPhysicalInfo;
import com.animal.scale.hodoo.domain.single.PetAllInfo;

import java.text.DecimalFormat;

/**
 * Created by SongSeokwoo on 2019-05-09.
 */
public class WeightDialog extends Dialog {
    private LayoutNumberKeyboardBinding keyboardBinding;
    private KeypadCallback callback;
    private final String suffixStr = "kg";
    private boolean state;
    private PetAllInfos selectPet;

    public interface KeypadCallback {
        void keypadCallback(PetAllInfos selectPet);
    }
    public WeightDialog(@NonNull Context context) {
        super(context);
        init( context );
    }

    public WeightDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init( context );
    }
    public WeightDialog(@NonNull Context context, int themeResId, KeypadCallback callback) {
        super(context, themeResId);
        init( context );
        this.callback = callback;
    }
    public WeightDialog(@NonNull Context context, int themeResId, PetAllInfos selectPet, KeypadCallback callback) {
        super(context, themeResId);
        this.selectPet = selectPet;
        init( context );
        this.callback = callback;

    }
    private void init ( Context context ) {
        keyboardBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_number_keyboard, null, false);
        if ( selectPet == null ) {
            selectPet = new PetAllInfos();
            PetPhysicalInfo data = new PetPhysicalInfo();
            data.setWeight("0");
            selectPet.setPetPhysicalInfo(data);
        } else {
            if ( Float.parseFloat( selectPet.petPhysicalInfo.getWeight() ) > 0 )
                keyboardBinding.setState(true);
        }

        keyboardBinding.setWeight(selectPet.petPhysicalInfo.getWeight());
        this.setContentView(keyboardBinding.getRoot());
        for (int i = 0; i < keyboardBinding.numericKeybord.getChildCount(); i++) {
            final int position = i;
            keyboardBinding.numericKeybord.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String kilogram = keyboardBinding.kilogram.getText().toString();
                    kilogram = kilogram.replaceAll(suffixStr, "");

                    float kilogramInt = Float.parseFloat(kilogram);

                    if ( view instanceof TextView) {
                        TextView textView = (TextView) view;
                        if ( kilogramInt == 0 ) {
                            kilogram = textView.getText().toString();
                        } else {
                            kilogram += textView.getText().toString();
                        }
                        state = true;
                    } else if ( view instanceof ImageView) {
                        kilogram = kilogram.substring(0, kilogram.length() - 1);
                        if ( kilogram.indexOf(".") > -1 ) {
                            String[] splitStr = kilogram.split("\\.");
                            if ( splitStr.length == 1 ) {
                                kilogram = splitStr[0];
                            }
                        }
                        if ( kilogram.equals("") ) {
                            kilogram = "0";
                            state = false;
                        } else
                            state = true;
                    }
                    keyboardBinding.setState(state);
                    keyboardBinding.kilogram.setText(kilogram + suffixStr);
                    selectPet.getPetPhysicalInfo().setWeight( kilogram );
                }
            });
        }
        keyboardBinding.closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeightDialog.this.dismiss();
            }
        });
        keyboardBinding.doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float kilogram = Float.valueOf(selectPet.getPetPhysicalInfo().getWeight());
                DecimalFormat df = new DecimalFormat("###.###");
                selectPet.getPetPhysicalInfo().setWeight(df.format(kilogram));
                if ( callback != null ) {
                    callback.keypadCallback( selectPet );
                }
            }
        });

        this.create();
        this.show();
    }
}
