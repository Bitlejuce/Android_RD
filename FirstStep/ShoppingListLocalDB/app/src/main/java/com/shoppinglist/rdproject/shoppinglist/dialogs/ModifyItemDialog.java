package com.shoppinglist.rdproject.shoppinglist.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.shoppinglist.rdproject.shoppinglist.Product;
import com.shoppinglist.rdproject.shoppinglist.R;

import java.util.List;

public class ModifyItemDialog extends DialogFragment {

        private EditText inputListName;
        private Button renameItem, deleteItem, cancelItemModification;
        public OnItemModifyListener onItemModifyListener;
        private List<Product> productList;
        private int itemPosition;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogAddList = inflater.inflate(R.layout.modify_item_dialog, null);
            inputListName = dialogAddList.findViewById(R.id.enter_product_name);
            inputListName.setHint(productList.get(itemPosition).getName());
            renameItem = dialogAddList.findViewById(R.id.rename_item);
            deleteItem = dialogAddList.findViewById(R.id.delete_item);
            cancelItemModification = dialogAddList.findViewById(R.id.cancel_add_item_button);

            renameItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String inputString = inputListName.getText().toString();
                    if (inputString.isEmpty()){
                        inputListName.setError("Enter as least one symbol");
                    }else {
                        onItemModifyListener.getItemModificationInput(inputString.trim(), productList, itemPosition);
                        getDialog().dismiss();
                    }
                }
            });
            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemModifyListener.getItemModificationInput(null, productList, itemPosition);
                    getDialog().dismiss();
                }
            });
            cancelItemModification.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });
            builder.setView(dialogAddList);
            Dialog dialog = builder.create();
            dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            return dialog;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            try {
                onItemModifyListener = (OnItemModifyListener)getActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public void setItemToModify(List<Product> product, int position) {
        productList = product;
        itemPosition = position;
    }

    public interface OnItemModifyListener{
            void getItemModificationInput(String input, List<Product> product, int position);
        }
    }


