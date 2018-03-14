package com.shoppinglist.rdproject.shoppinglist;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

// UNUSED CLASS
public class RecyclerOnClickListener implements View.OnClickListener {
    Context context;
    public RecyclerOnClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {

    }
}
/*
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddDialog extends DialogFragment {
    private EditText input, qty;
    private Button add, cancel;
    public OnTextInputListener onTextInputListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_product, null);
        qty = dialogView.findViewById(R.id.enter_quantity);
        input = dialogView.findViewById(R.id.enter_text);
        input.requestFocus();  // it doesnt work
        add = dialogView.findViewById(R.id.addButton);
        cancel = dialogView.findViewById(R.id.cancelButton);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputString = input.getText().toString();
                String inputQty = qty.getText().toString();
                if (!inputString.equals("")) {
                    Toast.makeText(getActivity(),"captured " + inputString, Toast.LENGTH_SHORT).show();
                    onTextInputListener.getUserInput(inputString, inputQty);
                    getDialog().dismiss();
                } else {
                    getDialog().dismiss();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        builder.setView(dialogView);
        //  setPositiveButton(R.string.add, this).
        //  setNegativeButton(R.string.cancel, this);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onTextInputListener = (OnTextInputListener)getActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnTextInputListener{
        void getUserInput(String input, String qty);
    }
}


 */