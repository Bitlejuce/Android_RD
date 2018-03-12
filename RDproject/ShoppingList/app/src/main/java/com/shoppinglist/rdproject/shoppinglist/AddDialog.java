package com.shoppinglist.rdproject.shoppinglist;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class AddDialog extends DialogFragment implements DialogInterface.OnClickListener{
    private EditText input;
    public OnTextInputListener onTextInputListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_product, null);
        input = dialogView.findViewById(R.id.enter_text);
        input.requestFocus();
        builder.setView(dialogView).
                setPositiveButton(R.string.add, this).
                setNegativeButton(R.string.cancel, this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        if (i == -1) {
            String inputString = input.getText().toString();
            if (!inputString.equals("")) {
                onTextInputListener.getUserInput(inputString);
                getDialog().dismiss();
            }
        }else {
            getDialog().dismiss();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onTextInputListener = (MainScreen)context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnTextInputListener{
        void getUserInput(String input);
    }
}


