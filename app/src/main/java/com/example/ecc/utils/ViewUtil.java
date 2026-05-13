package com.example.ecc.utils;

import android.util.Log;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.ecc.R;

public class ViewUtil {
    private static final String TAG = "ViewUtil";

    public static void startFragment(FragmentActivity activity, Fragment fragment, int container_id, String tag, boolean atb) {
        if (!activity.getSupportFragmentManager().isDestroyed()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(
                    R.anim.fragment_slide_in,   // enter
                    R.anim.fragment_fade_out,   // exit
                    R.anim.fragment_fade_in,    // popEnter
                    R.anim.fragment_slide_out); // popExit
            ft.replace(container_id, fragment, tag);
            if (atb) ft.addToBackStack(tag);
            ft.commit();
        } else {
            Log.e(TAG, tag + " is destroyed");
        }
    }

    public static void showChildDialog(Fragment parent, DialogFragment dialog, String tag) {
        Log.e(TAG, "showChildDialog: " + parent.getTag());
        if (!parent.getChildFragmentManager().isDestroyed()) {
            dialog.show(parent.getChildFragmentManager(), tag);
        } else {
            Log.e(TAG, tag + " is destroyed");
        }
    }

}
