package fr.enlight.anima.animamagiccards.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.async.CreateWitchspellsAsyncTask;
import fr.enlight.anima.animamagiccards.async.DeleteWitchspellsAsyncTask;
import fr.enlight.anima.animamagiccards.ui.spells.SpellStackFragment;
import fr.enlight.anima.animamagiccards.ui.witchspells.WitchspellsMainPathChooserActivity;
import fr.enlight.anima.animamagiccards.utils.DialogUtils;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class HomePageActivity extends AnimaBaseActivity implements HomePageFragment.Callbacks, CreateWitchspellsAsyncTask.Listener {

    private static final String HOME_PAGE_FRAGMENT_TAG = "HOME_PAGE_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_placeholder, new HomePageFragment(), HOME_PAGE_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onSpellbookClicked(Spellbook spellbook) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, SpellStackFragment.newInstance(spellbook))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddWitchspells() {
        DialogUtils.showEditTextDialog(this, R.string.Witchspells_Choose_Witch_Name, R.string.Witchspells_Witch_Name, null, new DialogUtils.EditTextDialogListener() {
            @Override
            public void onTextValidated(DialogInterface dialog, String textValue) {
                if(TextUtils.isEmpty(textValue)){
                    Toast.makeText(HomePageActivity.this, R.string.Error_No_Witchspells_Name, Toast.LENGTH_SHORT).show();
                } else {
                    createNewWitchspells(textValue);
                }
            }
        });
    }

    private void createNewWitchspells(String witchName) {
        new CreateWitchspellsAsyncTask(this).execute(witchName);
    }

    @Override
    public void onCreatedWitchspells(Witchspells witchspells) {
        onModifyWitchspells(witchspells);
    }

    @Override
    public void onModifyWitchspells(Witchspells witchspells) {
        Intent intent = WitchspellsMainPathChooserActivity.navigateForEdition(this, witchspells);
        startActivity(intent);
    }

    @Override
    public void onViewWitchspells(Witchspells witchspells) {
        goToWitchspells(witchspells);
    }

    @Override
    public void onWitchspellsClicked(Witchspells witchspells) {
        goToWitchspells(witchspells);
    }

    private void goToWitchspells(Witchspells witchspells){
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, SpellStackFragment.newInstance(witchspells))
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void onDeleteWitchspells(final Witchspells witchspells) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.Witchspells_Delete_Confirmation_Title)
                .setMessage(R.string.Witchspells_Delete_Confirmation_Message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DeleteWitchspellsAsyncTask().execute(witchspells);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }


}
