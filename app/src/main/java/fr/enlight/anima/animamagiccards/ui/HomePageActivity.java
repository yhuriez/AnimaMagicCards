package fr.enlight.anima.animamagiccards.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.spellbooks.SpellStackFragment;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.ui.witchspells.WitchspellsEditionActivity;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class HomePageActivity extends AppCompatActivity implements HomePageFragment.Callbacks {

    private static final int WITCHSPELLS_REQUEST_CODE = 712;

    private static final String HOME_PAGE_FRAGMENT_TAG = "HOME_PAGE_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        if(savedInstanceState == null){
            goToSpellbookFragment();
        }
    }

    private void goToSpellbookFragment(){
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, new HomePageFragment(), HOME_PAGE_FRAGMENT_TAG)
                .commit();
    }

    private void goToSpellStackFragment(int spellbookId, SpellbookType type){
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
        startActivityForResult(new Intent(this, WitchspellsEditionActivity.class), WITCHSPELLS_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == WITCHSPELLS_REQUEST_CODE && resultCode == RESULT_OK){
            Fragment spellbookFragment = getFragmentManager().findFragmentByTag(HOME_PAGE_FRAGMENT_TAG);
            if(spellbookFragment != null && spellbookFragment instanceof HomePageFragment){
                ((HomePageFragment) spellbookFragment).resetSpellbooks();
            }
        }
    }

    @Override
    public void onModifyWitchspells(Witchspells witchspells) {
        // TODO
    }

    @Override
    public void onViewWitchspells(Witchspells witchspells) {
        // TODO
    }

    @Override
    public void onDeleteWitchspells(Witchspells witchspells) {
        // TODO
    }
}
