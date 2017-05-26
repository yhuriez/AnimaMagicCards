package fr.enlight.anima.animamagiccards.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.async.DeleteWitchspellsAsyncTask;
import fr.enlight.anima.animamagiccards.ui.spellbooks.SpellStackFragment;
import fr.enlight.anima.cardmodel.business.WitchspellsBusinessService;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.ui.witchspells.WitchspellsEditionActivity;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class HomePageActivity extends AppCompatActivity implements HomePageFragment.Callbacks, DeleteWitchspellsAsyncTask.Listener {

    private static final int WITCHSPELLS_REQUEST_CODE = 712;

    private static final String HOME_PAGE_FRAGMENT_TAG = "HOME_PAGE_FRAGMENT_TAG";

    private WitchspellsBusinessService mWitchspellsBusinessService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        mWitchspellsBusinessService = new WitchspellsBusinessService(this);

        if (savedInstanceState == null) {
            goToSpellbookFragment();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.spell_stack_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    private void goToSpellbookFragment() {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, new HomePageFragment(), HOME_PAGE_FRAGMENT_TAG)
                .commit();
    }

    private void goToSpellStackFragment(int spellbookId, SpellbookType type) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, SpellStackFragment.newInstance(spellbookId, type))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSpellbookClicked(int spellbookId, SpellbookType type) {
        goToSpellStackFragment(spellbookId, type);
    }

    @Override
    public void onAddWitchspells() {
        startActivityForResult(WitchspellsEditionActivity.navigate(this), WITCHSPELLS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WITCHSPELLS_REQUEST_CODE && resultCode == RESULT_OK) {
            reloadHomePage();
        }
    }

    @Override
    public void onModifyWitchspells(Witchspells witchspells) {
        Intent intent = WitchspellsEditionActivity.navigateForEdition(this, witchspells);
        startActivityForResult(intent, WITCHSPELLS_REQUEST_CODE);
    }

    @Override
    public void onViewWitchspells(Witchspells witchspells) {
        // TODO
    }

    @Override
    public void onDeleteWitchspells(final Witchspells witchspells) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.Witchspells_Delete_Confirmation_Title)
                .setMessage(R.string.Witchspells_Delete_Confirmation_Message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DeleteWitchspellsAsyncTask(HomePageActivity.this).execute(witchspells);
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


    private void reloadHomePage(){
        Fragment spellbookFragment = getFragmentManager().findFragmentByTag(HOME_PAGE_FRAGMENT_TAG);
        if (spellbookFragment != null && spellbookFragment instanceof HomePageFragment) {
            ((HomePageFragment) spellbookFragment).resetSpellbooks();
        }
    }

    @Override
    public void onDeleteStarted() {
        // Nothing to do
    }

    @Override
    public void onDeleteFinished() {
        reloadHomePage();
    }
}
