package com.animal.scale.hodoo;

import android.app.Application;

/**
 * Created by SongSeokwoo on 2019-05-08.
 */
public class HodooApplication extends Application {
    private boolean experienceState = false;

    public boolean isExperienceState() {
        return experienceState;
    }

    public void setExperienceState(boolean experienceState) {
        this.experienceState = experienceState;
    }
}
