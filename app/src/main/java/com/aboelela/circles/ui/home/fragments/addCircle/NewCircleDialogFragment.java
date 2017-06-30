package com.aboelela.circles.ui.home.fragments.addCircle;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.aboelela.circles.R;
import com.mvvm.framework.annotation.InflateLayout;
import com.mvvm.framework.base.views.BaseDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewCircleDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@InflateLayout(R.layout.fragment_add_circle_dialog)
public class NewCircleDialogFragment extends BaseDialogFragment
{
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     */
    public static NewCircleDialogFragment newInstance() {
        NewCircleDialogFragment fragment = new NewCircleDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
}
