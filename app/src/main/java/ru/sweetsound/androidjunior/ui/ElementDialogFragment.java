package ru.sweetsound.androidjunior.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import ru.sweetsound.androidjunior.R;
import ru.sweetsound.androidjunior.utils.DataListener;


public class ElementDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    public final static String DATA_KEY = "data_key";
    public final static String POSITION_KEY = "position_key";
    private boolean isNewEntry = true;
    private int mPosition;
    private EditText mEditText;
    private DataListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        mListener = (DataListener) getTargetFragment();
        View v = inflater.inflate(R.layout.fragment_element_dialog, null);
        mEditText = (EditText) v.findViewById(R.id.dialog_edit);
        if (bundle!=null && bundle.getString(DATA_KEY)!=null) {
            isNewEntry = false;
            mEditText.setText(bundle.getString(DATA_KEY));
            mPosition = bundle.getInt(POSITION_KEY);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(R.string.done, this)
                .setNegativeButton(R.string.revert, this);
        return builder.create();
    }



    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case Dialog.BUTTON_POSITIVE:
                if (!isNewEntry) mListener.onChange(mPosition, mEditText.getText().toString());
                else mListener.onAdd(mEditText.getText().toString());
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }
    }

}
