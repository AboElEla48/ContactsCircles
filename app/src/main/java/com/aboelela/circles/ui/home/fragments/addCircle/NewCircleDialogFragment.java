package com.aboelela.circles.ui.home.fragments.addCircle;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;

import com.aboelela.circles.R;
import com.mvvm.framework.annotation.InflateLayout;
import com.mvvm.framework.annotation.Presenter;
import com.mvvm.framework.base.views.BaseDialogFragment;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewCircleDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@InflateLayout(R.layout.fragment_add_circle_dialog)
public class NewCircleDialogFragment extends BaseDialogFragment
{
    @Presenter
    NewCircleDialogPresenter newCircleDialogPresenter;

    @BindView(R.id.fragment_add_circle_name_edit_text)
    EditText circleNameEditText;

    @BindView(R.id.fragment_add_circle_save_circle_btn)
    Button saveBtn;

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
