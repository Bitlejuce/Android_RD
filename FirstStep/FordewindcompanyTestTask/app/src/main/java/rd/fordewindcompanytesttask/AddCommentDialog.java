package rd.fordewindcompanytesttask;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class AddCommentDialog extends DialogFragment {
    private EditText inputComment;
    private Button add, cancel;
    public OnCommentInputListener onCommentInputListener;
    private String comment;
    private int itemPosition;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View commentAddView = inflater.inflate(R.layout.add_comment, null);
        inputComment = commentAddView.findViewById(R.id.enter_comment);
        inputComment.setText(comment);
        add = commentAddView.findViewById(R.id.add_comment_button);
        cancel = commentAddView.findViewById(R.id.cancel_add_comment_button);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputString = inputComment.getText().toString();
                    onCommentInputListener.getCommentInput(inputString.trim(), itemPosition);
                    getDialog().dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
        builder.setView(commentAddView);
        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return dialog;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onCommentInputListener = (OnCommentInputListener)getActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface OnCommentInputListener {
        void getCommentInput(String input, int index);
    }
}

