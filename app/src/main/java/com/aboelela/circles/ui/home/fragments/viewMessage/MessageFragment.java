package com.aboelela.circles.ui.home.fragments.viewMessage;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.aboelela.circles.R;
import com.aboelela.circles.data.HomeMessageModel;
import com.mvvm.framework.annotation.InflateLayout;
import com.mvvm.framework.annotation.Presenter;
import com.mvvm.framework.base.views.BaseFragment;

import butterknife.BindView;

/**
 * Define fragment for showing message in home activity
 */
@InflateLayout(R.layout.fragment_message)
public class MessageFragment extends BaseFragment
{
    @Presenter
    MessageFragmentPresenter presenter;

    @BindView(R.id.fragment_message_buttons_layout)
    View msgButtonsLayout;

    @BindView(R.id.fragment_message_text_view)
    TextView msgTextView;

    @BindView(R.id.fragment_message_ok_btn)
    Button okBtn;

    @BindView(R.id.fragment_message_cancel_btn)
    Button cancelBtn;

    static final String Bundle_Home_Message = "Bundle_Home_Message";

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param homeMessageModel : model for representing home message
     *
     */
    public static MessageFragment newInstance(HomeMessageModel homeMessageModel) {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();

        args.putParcelable(Bundle_Home_Message, homeMessageModel);

        fragment.setArguments(args);
        return fragment;
    }

}
