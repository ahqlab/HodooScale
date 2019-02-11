package com.cmmakerclub.iot.esptouch.databinding;

import android.databinding.Bindable;
import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import com.cmmakerclub.iot.esptouch.model.AccessPoint;

public abstract class MainActivityBinding extends ViewDataBinding {
  @NonNull
  public final Button btnConfirm;

  @NonNull
  public final DrawerLayout drawerLayout;

  @NonNull
  public final EditText edtApPassword;

  @NonNull
  public final CoordinatorLayout rootLayout;

  @NonNull
  public final Spinner spinnerTaskResultCount;

  @NonNull
  public final Switch switchIsSsidHidden;

  @NonNull
  public final TextView tvApSsid;

  @NonNull
  public final TextView tvApSssidConnected;

  @Bindable
  protected AccessPoint mAp;

  protected MainActivityBinding(DataBindingComponent _bindingComponent, View _root,
      int _localFieldCount, Button btnConfirm, DrawerLayout drawerLayout, EditText edtApPassword,
      CoordinatorLayout rootLayout, Spinner spinnerTaskResultCount, Switch switchIsSsidHidden,
      TextView tvApSsid, TextView tvApSssidConnected) {
    super(_bindingComponent, _root, _localFieldCount);
    this.btnConfirm = btnConfirm;
    this.drawerLayout = drawerLayout;
    this.edtApPassword = edtApPassword;
    this.rootLayout = rootLayout;
    this.spinnerTaskResultCount = spinnerTaskResultCount;
    this.switchIsSsidHidden = switchIsSsidHidden;
    this.tvApSsid = tvApSsid;
    this.tvApSssidConnected = tvApSssidConnected;
  }

  public abstract void setAp(@Nullable AccessPoint ap);

  @Nullable
  public AccessPoint getAp() {
    return mAp;
  }

  @NonNull
  public static MainActivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot) {
    return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static MainActivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup root, boolean attachToRoot, @Nullable DataBindingComponent component) {
    return DataBindingUtil.<MainActivityBinding>inflate(inflater, com.cmmakerclub.iot.esptouch.R.layout.main_activity, root, attachToRoot, component);
  }

  @NonNull
  public static MainActivityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, DataBindingUtil.getDefaultComponent());
  }

  @NonNull
  public static MainActivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable DataBindingComponent component) {
    return DataBindingUtil.<MainActivityBinding>inflate(inflater, com.cmmakerclub.iot.esptouch.R.layout.main_activity, null, false, component);
  }

  public static MainActivityBinding bind(@NonNull View view) {
    return bind(view, DataBindingUtil.getDefaultComponent());
  }

  public static MainActivityBinding bind(@NonNull View view,
      @Nullable DataBindingComponent component) {
    return (MainActivityBinding)bind(component, view, com.cmmakerclub.iot.esptouch.R.layout.main_activity);
  }
}
