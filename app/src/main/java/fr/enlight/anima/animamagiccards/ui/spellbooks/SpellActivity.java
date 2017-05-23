package fr.enlight.anima.animamagiccards.ui.spellbooks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.animamagiccards.ui.witchspells.WitchspellsEditionActivity;

public class SpellActivity extends AppCompatActivity implements SpellbooksFragment.Callbacks{

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
                .replace(R.id.fragment_placeholder, new SpellbooksFragment())
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
        startActivity(new Intent(this, WitchspellsEditionActivity.class));
    }
}
