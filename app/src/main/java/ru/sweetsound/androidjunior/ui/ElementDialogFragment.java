package ru.sweetsound.androidjunior.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import ru.sweetsound.androidjunior.R;


public class ElementDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    public final static String DATA_KEY = "data_key";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.fragment_element_dialog, null);
        if (bundle!=null && bundle.getString(DATA_KEY)!=null)
            ((EditText) v.findViewById(R.id.dialog_edit)).setText(bundle.getString(DATA_KEY));

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
                //save
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }
    }


}
