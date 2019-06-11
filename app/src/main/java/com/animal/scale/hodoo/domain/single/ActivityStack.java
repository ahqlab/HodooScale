package com.animal.scale.hodoo.domain.single;

import android.app.Activity;

import com.animal.scale.hodoo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ActivityStack<D extends Activity> {

    private List<BaseActivity<D>> classes = new ArrayList<BaseActivity<D>>();

    private static ActivityStack instance;

    private ActivityStack() {
    }

    public static ActivityStack getInstance() {
        if (instance == null)
            instance = new ActivityStack();
        return instance;
    }

    public void print() {
        System.out.println("It's print() method in LazyInitialization instance.");
        System.out.println("instance hashCode > " + instance.hashCode());
    }
}
