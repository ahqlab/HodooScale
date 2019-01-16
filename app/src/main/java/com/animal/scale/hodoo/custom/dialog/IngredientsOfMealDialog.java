package com.animal.scale.hodoo.custom.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.animal.scale.hodoo.R;
import com.animal.scale.hodoo.databinding.IngredientsOfMealDialogBinding;
import com.animal.scale.hodoo.domain.Feed;

import lombok.NonNull;

public class IngredientsOfMealDialog  extends DialogFragment {

    private static final String ARGS_KEY_FEED = "_ARG_KEY_FEED_";
    private static final String ARGS_KEY_EDIT = "_ARG_KEY_EDIT_";

    IngredientsOfMealDialogBinding binding;

    public static IngredientsOfMealDialog newInstance(Feed feed) {
        IngredientsOfMealDialog fragment = new IngredientsOfMealDialog();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_KEY_FEED, feed);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.ingredients_of_meal_dialog, null, false);
        Feed feed  = grubEditingFeed(savedInstanceState, grubOriginalFeed());
        binding = IngredientsOfMealDialogBinding.bind(view);
        binding.setDomain(feed);
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), com.github.javiersantos.bottomdialogs.R.style.BottomDialogs);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }

    @NonNull
    private Feed grubEditingFeed(Bundle savedInstanceState, @NonNull Feed originalFeed) {
        Feed targetFeed = null;
        if (savedInstanceState != null && savedInstanceState.containsKey(ARGS_KEY_EDIT)) {
            targetFeed = (Feed) savedInstanceState.getSerializable(ARGS_KEY_EDIT);
        } else {
            Bundle args = getArguments();
            if (args != null && args.containsKey(ARGS_KEY_EDIT)) {
                targetFeed = (Feed) args.getSerializable(ARGS_KEY_EDIT);
            }
        }
        return targetFeed == null ? originalFeed : targetFeed;
    }
    
    
    @NonNull
    private Feed grubOriginalFeed() {
        Feed targetFeed = null;
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARGS_KEY_FEED)) {
            targetFeed = (Feed) args.getSerializable(ARGS_KEY_FEED);
        }
        return targetFeed == null ? new Feed() : targetFeed;
    }
}
