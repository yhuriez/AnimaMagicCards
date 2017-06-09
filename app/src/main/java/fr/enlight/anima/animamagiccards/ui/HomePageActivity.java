package fr.enlight.anima.animamagiccards.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.async.DeleteWitchspellsAsyncTask;
import fr.enlight.anima.animamagiccards.ui.spells.SpellStackFragment;
import fr.enlight.anima.animamagiccards.ui.witchspells.WitchspellsMainPathChooserActivity;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class HomePageActivity extends AnimaBaseActivity implements HomePageFragment.Callbacks {

    private static final int WITCHSPELLS_REQUEST_CODE = 712;

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
    public void onSpellbookClicked(int spellbookId) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, SpellStackFragment.newInstance(spellbookId))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddWitchspells() {

        // TODO Edit dialog for witchspells

    }

    @Override
    public void onModifyWitchspells(Witchspells witchspells) {
        Intent intent = WitchspellsMainPathChooserActivity.navigateForEdition(this, witchspells);
        startActivityForResult(intent, WITCHSPELLS_REQUEST_CODE);
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
