package com.example.matheus.arduinotemp;

import android.app.Dialog;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Matheus on 12/10/2017.
 */

public class CustomDialog {

    private static final CustomDialog ourInstance = new CustomDialog();
    public static CustomDialog getInstance() {
        return ourInstance;
    }

    public void showInputDialog(final Context context, String title){
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().requestFeature(1);
        dialog.setContentView(R.layout.dialog_ip_input);

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        tvTitle.setText(title);

        final EditText etIpAddress = (EditText) dialog.findViewById(R.id.etIpAddress);
        if (!Preferences.getInstance().retrieve(context).equals("default")){
            etIpAddress.setText(Preferences.getInstance().retrieve(context));
        }

        Button btnSave = (Button) dialog.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.getInstance().save(context,etIpAddress.getText().toString());
                Toast.makeText(context,context.getString(R.string.saved),Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
