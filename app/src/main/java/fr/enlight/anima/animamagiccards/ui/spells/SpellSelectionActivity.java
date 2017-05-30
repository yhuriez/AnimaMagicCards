package fr.enlight.anima.animamagiccards.ui.spells;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;


public class SpellSelectionActivity extends AppCompatActivity {

    private static final String WITCHPATHS_PARAM = "WITCHPATHS_PARAM";

    public static Intent navigate(Context context, WitchspellsPath witchspellsPath){
        Intent intent = new Intent(context, SpellSelectionActivity.class);
        intent.putExtra(WITCHPATHS_PARAM, witchspellsPath);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        if(savedInstanceState == null){
            WitchspellsPath witchPath = getIntent().getParcelableExtra(WITCHPATHS_PARAM);
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_placeholder, SpellStackFragment.newInstanceForSelection(witchPath))
                    .addToBackStack(null)
                    .commit();
        }
    }
}
