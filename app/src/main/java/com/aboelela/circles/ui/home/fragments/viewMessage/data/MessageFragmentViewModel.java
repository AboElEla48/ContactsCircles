package com.aboelela.circles.ui.home.fragments.viewMessage.data;

import com.aboelela.circles.R;
import com.aboelela.circles.ui.home.fragments.viewMessage.MessageFragment;
import com.mvvm.framework.annotation.viewmodelfields.ViewModelTextField;
import com.mvvm.framework.annotation.viewmodelfields.ViewModelViewVisibilityField;
import com.mvvm.framework.base.viewmodels.BaseViewModel;
import com.mvvm.framework.utils.LogUtil;

/**
 * Created by aboelela on 12/08/17.
 * ViewModel for message fragment
 */

public class MessageFragmentViewModel extends BaseViewModel<MessageFragment, MessageFragmentViewModel>
{
    @ViewModelViewVisibilityField(R.id.fragment_message_buttons_layout)
    private Integer messageViewLayoutVisibility;

    @ViewModelTextField(R.id.fragment_message_text_view)
    private String messageVal;

    /**
     * set visibility of buttons layout
     * @param messageViewLayoutVisibility :  the visibility value of buttons layout
     */
    public void setMessageViewLayoutVisibility(Integer messageViewLayoutVisibility) {
        setField("messageViewLayoutVisibility", messageViewLayoutVisibility);
    }

    /**
     * set the message value
     * @param messageVal : the string value to set
     */
    public void setMessageVal(String messageVal) {
        setField("messageVal", messageVal);
    }

    /**
     * set field value
     * @param fieldName : the field name
     * @param val : the value
     */
    private void setField(String fieldName, Object val) {
        try {
            setViewModelFieldValue(this, fieldName, val);
        }
        catch (Exception ex) {
            LogUtil.writeErrorLog(LOG_TAG, "Error setting field value: " + fieldName + ", " + val, ex);
        }

    }

    private final String LOG_TAG = "MessageFragmentViewModel";
}
