package com.cmmakerclub.iot.esptouch.databinding;
import com.cmmakerclub.iot.esptouch.R;
import com.cmmakerclub.iot.esptouch.BR;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class MainActivityBindingImpl extends MainActivityBinding  {

    @Nullable
    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rootLayout, 3);
        sViewsWithIds.put(R.id.tvApSsid, 4);
        sViewsWithIds.put(R.id.switchIsSsidHidden, 5);
        sViewsWithIds.put(R.id.spinnerTaskResultCount, 6);
        sViewsWithIds.put(R.id.btnConfirm, 7);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public MainActivityBindingImpl(@Nullable android.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds));
    }
    private MainActivityBindingImpl(android.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.Button) bindings[7]
            , (android.support.v4.widget.DrawerLayout) bindings[0]
            , (android.widget.EditText) bindings[2]
            , (android.support.design.widget.CoordinatorLayout) bindings[3]
            , (android.widget.Spinner) bindings[6]
            , (android.widget.Switch) bindings[5]
            , (android.widget.TextView) bindings[4]
            , (android.widget.TextView) bindings[1]
            );
        this.drawerLayout.setTag(null);
        this.edtApPassword.setTag(null);
        this.tvApSssidConnected.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.ap == variableId) {
            setAp((com.cmmakerclub.iot.esptouch.model.AccessPoint) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setAp(@Nullable com.cmmakerclub.iot.esptouch.model.AccessPoint Ap) {
        this.mAp = Ap;
        synchronized(this) {
            mDirtyFlags |= 0x1L;
        }
        notifyPropertyChanged(BR.ap);
        super.requestRebind();
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        java.lang.String apSsid = null;
        com.cmmakerclub.iot.esptouch.model.AccessPoint ap = mAp;
        java.lang.String apPassword = null;

        if ((dirtyFlags & 0x3L) != 0) {



                if (ap != null) {
                    // read ap.ssid
                    apSsid = ap.ssid;
                    // read ap.password
                    apPassword = ap.password;
                }
        }
        // batch finished
        if ((dirtyFlags & 0x3L) != 0) {
            // api target 1

            android.databinding.adapters.TextViewBindingAdapter.setText(this.edtApPassword, apPassword);
            android.databinding.adapters.TextViewBindingAdapter.setText(this.tvApSssidConnected, apSsid);
        }
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): ap
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}