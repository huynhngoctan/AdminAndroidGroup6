package com.example.adminandroidgroup6.support;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

import com.example.adminandroidgroup6.R;

public class LoadingDialog {
    Context context;
    AlertDialog dialog;

    public LoadingDialog(Context context) {
        this.context = context;
    }

    public void startLoadingDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        builder.setView(inflater.inflate(R.layout.layout_dialog_loading,null));
        builder.setCancelable(true);

        dialog = builder.create();
        dialog.show();
    }
    public void dismissDialog(){
        dialog.dismiss();
    }
}
