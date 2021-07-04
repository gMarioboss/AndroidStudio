package com.example.notes.UI;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.notes.Data.DataSource;
import com.example.notes.Data.DataSourceFirebaseImpl;
import com.example.notes.Data.DataSourceResponse;
import com.example.notes.R;

public class DialogDeleteFragment extends DialogFragment {

    private int deletePosition;
    private DataSource data = new DataSourceFirebaseImpl().init(new DataSourceResponse() {
        @Override
        public void initialized(DataSource noteList) {
        }
    });

    public DialogDeleteFragment () {
    }

    public DialogDeleteFragment (int deletePosition) {
        this.deletePosition = deletePosition;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setTitle(R.string.warning)
                .setCancelable(false)
                .setMessage(R.string.warning_to_delete)
                .setPositiveButton(R.string.yes, (d, i) ->{
                    data.deleteCardData(deletePosition);
                    requireActivity().recreate();
                    dismiss();
                })
                .setNegativeButton(R.string.no, (d, i) -> {
                    dismiss();
                });

        return builder.create();
    }
}
