package com.shoppinglist.rdproject.shoppinglist;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Toast;

public class AddDialog extends DialogFragment implements DialogInterface.OnClickListener{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.add_product, null)).
                setPositiveButton(R.string.add, this).
                setNegativeButton(R.string.cancel, this);
        Dialog dialog = builder.create();

        //EditText input = dialog.findViewById(R.id.enter_text);
       // input.setText("ля");

        return dialog;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        if (i == -1) {
            Toast.makeText(getActivity(), "OK works "+"i = " + i, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getActivity(), "Cancel works,  i = " + i, Toast.LENGTH_SHORT).show();
        }
    }
}


