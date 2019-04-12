package com.animal.scale.hodoo.activity.pet.regist.fragment.type;

import android.content.Context;

/**
 * Created by SongSeokwoo on 2019-04-09.
 */
public interface PetTypeIn {
    interface View {
        void setType( int petType );
        void setNavigation();
    }
    interface Presenter {
        void getType( int petIdx );
        void loadData(Context context);
        void setNavigation();
    }
}
