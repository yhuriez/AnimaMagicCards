package fr.enlight.anima.animamagiccards.utils;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;

import fr.enlight.anima.animamagiccards.R;

public class DialogUtils {

    public static void showEditTextDialog(Context context, @StringRes int title, @StringRes int hint, String initialValue, final EditTextDialogListener listener){

        final ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.view_edit_text, null);
        final EditText editText = (EditText) viewGroup.findViewById(R.id.generic_edit_text);
        editText.setHint(hint);
        if(initialValue != null){
            editText.setText(initialValue);
        }

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setView(viewGroup)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onTextValidated(dialog, editText.getText().toString());
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public interface EditTextDialogListener{
        void onTextValidated(DialogInterface dialog, String textValue);
    }
}
