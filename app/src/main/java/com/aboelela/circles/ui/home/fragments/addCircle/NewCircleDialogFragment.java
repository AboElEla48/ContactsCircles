package com.aboelela.circles.ui.home.fragments.addCircle;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aboelela.circles.R;
import com.mvvm.framework.annotation.InflateLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewCircleDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@InflateLayout(R.layout.fragment_add_circle_dialog)
public class NewCircleDialogFragment extends DialogFragment
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_circle_dialog, container, false);
    }

}
