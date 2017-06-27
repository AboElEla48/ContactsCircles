package com.mvvm.framework.interfaces;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by AboelelaA on 6/8/2017.
 * This interface for the lifecycle of fragment
 */

public interface FragmentLifeCycle extends ViewLifeCycle
{
    View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    void onActivityCreated(@Nullable Bundle savedInstanceState);
}
