package com.mvvm.framework.utils.permissions;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.mvvm.framework.utils.DialogMsgUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by aboelela on 14/07/17.
 * Activity for requesting permission
 */

public class PermissionsActivity extends AppCompatActivity
{

    private static final String LOG_TAG = "PermissionsActivity";
    public static String Bundle_Permission_Str_Arr_Key = "Bundle_Permission_Map_Key";
    public static String Bundle_Permission_Msgs_Str_Arr_Key = "Bundle_Permission_Msgs_Str_Arr_Key";

    private String title = "Permission";

    private android.app.AlertDialog alertDialog;

    private class PermissionMsg {
        String permission;
        String msg;
    }

    int index;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> permissionsStr = Arrays.asList(getIntent().getStringArrayExtra(Bundle_Permission_Str_Arr_Key));
        List<String> messagesStr = Arrays.asList(getIntent().getStringArrayExtra(Bundle_Permission_Msgs_Str_Arr_Key));

        final ArrayList<PermissionMsg> permissions = new ArrayList<>();
        Observable.fromIterable(permissionsStr)
                .map(new Function<String, PermissionMsg>()
                {
                    @Override
                    public PermissionMsg apply(@NonNull String s) throws Exception {
                        PermissionMsg permissionMsg = new PermissionMsg();
                        permissionMsg.permission = s;
                        return permissionMsg;
                    }
                })
                .blockingSubscribe(new Consumer<PermissionMsg>()
                {

                    @Override
                    public void accept(@io.reactivex.annotations.NonNull PermissionMsg permissionMsg) throws Exception {
                        permissions.add(permissionMsg);
                    }
                });

        index = 0;
        Observable.fromIterable(messagesStr)
                .subscribe(new Consumer<String>()
                {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        permissions.get(index).msg = s;
                        index++;
                    }
                });

        Observable.fromIterable(permissions)
                .blockingSubscribe(new Consumer<PermissionMsg>()
                {
                    @Override
                    public void accept(@NonNull PermissionMsg permissionMsg) throws Exception {
                        if(!checkPermission(permissionMsg.permission)) {
                            // Not granted
                            requestPermission(permissionMsg);
                        }
                    }
                });

        finish();
    }

    public boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(PermissionMsg permissionMsg) {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                permissionMsg.permission)) {

            List<DialogMsgUtil.DialogMsgButton> btns = Arrays.asList(new DialogMsgUtil.DialogMsgButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialog.dismiss();
                        }
                    }));
            alertDialog = DialogMsgUtil.createAlertDialog(this, title, permissionMsg.msg, btns, 0);
            alertDialog.show();

        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{permissionMsg.permission}, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
