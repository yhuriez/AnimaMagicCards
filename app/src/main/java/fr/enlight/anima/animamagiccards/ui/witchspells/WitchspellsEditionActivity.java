package fr.enlight.anima.animamagiccards.ui.witchspells;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;

public class WitchspellsEditionActivity extends AppCompatActivity implements WitchspellsEditionFragment.Listener, WitchspellsMainPathChooserFragment.Listener, WitchspellsSecondaryPathChooserFragment.Listener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witchspells);

        if(savedInstanceState == null){
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_placeholder, new WitchspellsEditionFragment())
                    .commit();
        }
    }

    @Override
    public void onAddPath() {
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_placeholder, new WitchspellsMainPathChooserFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onMainPathsChosen(Witchspells witchspells) {
        // TODO
    }



    @Override
    public void onSpellbookSelected(Spellbook spellbook) {
        // TODO
    }

    @Override
    public void onSpellbookLevelSelected(Spellbook spellbook, int levelSelected) {
        // TODO
    }

    @Override
    public void onShowSecondarySpellbookForMainPath(Spellbook spellbook) {
        // TODO
    }

    @Override
    public void onSecondaryPathChosen(Witchspells witchspells) {
        // TODO
    }
}
