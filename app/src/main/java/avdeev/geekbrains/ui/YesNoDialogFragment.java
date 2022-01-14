package avdeev.geekbrains.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class YesNoDialogFragment extends DialogFragment {

    interface ClosingDialogListner {
        void Close();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Are you sure?");
        builder.setCancelable(true);

        builder.setNegativeButton("No", (dialog, which) -> {
           dialog.cancel();
        });

        builder.setPositiveButton("Yes", (dialog, which) -> {
            ((ClosingDialogListner)requireContext()).Close();
            dialog.dismiss();
        });

        return builder.create();
    }
}
