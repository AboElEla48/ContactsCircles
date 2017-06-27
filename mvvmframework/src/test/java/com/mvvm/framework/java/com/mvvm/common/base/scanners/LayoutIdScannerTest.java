package com.mvvm.framework.java.com.mvvm.common.base.scanners;

import com.mvvm.framework.base.scanners.LayoutIdScanner;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by AboelelaA on 6/6/2017.
 *
 * This scanner to scan annotations of layouts in views to inflate it to views
 */
public class LayoutIdScannerTest
{
    @Test
    public void apply_NoReturnNull() throws Exception {
        // Declare
        LayoutIdScanner layoutIdScanner = new LayoutIdScanner();
        SampleViewLayoutScanner sampleViewLayoutScanner = new SampleViewLayoutScanner();

        //operation
        Integer resId = layoutIdScanner.apply(sampleViewLayoutScanner);

        // Assert
        Assert.assertTrue(resId != null);
    }

}