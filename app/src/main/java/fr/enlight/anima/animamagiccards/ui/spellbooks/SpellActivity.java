package fr.enlight.anima.animamagiccards.ui.spellbooks;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.ui.witchspells.WitchspellsEditionActivity;

public class SpellActivity extends AppCompatActivity implements SpellbooksFragment.Callbacks{

    private static final int WITCHSPELLS_REQUEST_CODE = 712;

    private static final String SPELLBOOK_FRAGMENT_TAG = "SPELLBOOK_FRAGMENT_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell);

        if(savedInstanceState == null){
            goToSpellbookFragment();
        }
    }

    private void goToSpellbookFragment(){
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, new SpellbooksFragment(), SPELLBOOK_FRAGMENT_TAG)
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
            Fragment spellbookFragment = getFragmentManager().findFragmentByTag(SPELLBOOK_FRAGMENT_TAG);
            if(spellbookFragment != null && spellbookFragment instanceof SpellbooksFragment){
                ((SpellbooksFragment) spellbookFragment).resetSpellbooks();
            }
        }
    }
}
