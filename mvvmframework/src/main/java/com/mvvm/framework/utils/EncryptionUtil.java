package com.mvvm.framework.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by AboElEla on 3/16/2015.
 * Utility for encryption
 */
public class EncryptionUtil
{
    private EncryptionUtil()
    {

    }

    /**
     * hash to MD5
     * @param src
     * @return
     */
    public static String hashToMD5(String src)
    {
        final String algorithmName = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(algorithmName);
            digest.update(src.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            LogUtil.writeErrorLog(LOG_TAG, e);
        }
        return "";
    }

    private final static String LOG_TAG = "EncryptionUtil";
}
