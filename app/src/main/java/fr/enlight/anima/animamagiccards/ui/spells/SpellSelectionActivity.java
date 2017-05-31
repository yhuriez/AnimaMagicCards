package fr.enlight.anima.animamagiccards.ui.spells;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;


public class SpellSelectionActivity extends AppCompatActivity {

    private static final String FREE_ACCESS_LIMIT_PARAM = "FREE_ACCESS_LIMIT_PARAM";

    public static Intent navigate(Context context){
        return new Intent(context, SpellSelectionActivity.class);
    }

    public static Intent navigate(Context context, int freeAccessLimit){
        Intent intent = navigate(context);
        intent.putExtra(FREE_ACCESS_LIMIT_PARAM, freeAccessLimit);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        if(savedInstanceState == null){
            Fragment fragment;

            if(getIntent().getExtras().containsKey(FREE_ACCESS_LIMIT_PARAM)){
                fragment = SpellStackFragment.newInstanceForSelection(getIntent().getIntExtra(FREE_ACCESS_LIMIT_PARAM, -1));
            } else {
                fragment = SpellStackFragment.newInstance(SpellbookType.FREE_ACCESS.bookId);
            }

            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_placeholder, fragment)
                    .commit();
        }
    }
}
