package fr.enlight.anima.animamagiccards;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import fr.enlight.anima.animamagiccards.utils.SpellbookType;

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
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, new SpellbooksFragment())
                .commit();
    }

    private void goToSpellStackFragment(int spellbookId, SpellbookType type){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, SpellStackFragment.newInstance(spellbookId, type))
                .commit();
    }

    @Override
    public void onSpellbookClicked(int spellbookId, SpellbookType type) {
        goToSpellStackFragment(spellbookId, type);
    }
}
