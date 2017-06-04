package fr.enlight.anima.animamagiccards.ui.spells;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.AnimaBaseActivity;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.utils.SpellUtils;


public class SpellSelectionActivity extends AnimaBaseActivity implements SpellStackFragment.Listener{

    public static final String SELECTED_SPELL_RESULT = "SELECTED_SPELL_RESULT";
    public static final String FREE_ACCESS_POSITION_PARAM = "FREE_ACCESS_POSITION_PARAM";

    private int freeAccessPosition;

    public static Intent navigate(Context context){
        return new Intent(context, SpellSelectionActivity.class);
    }

    public static Intent navigate(Context context, int freeAccessLimit){
        Intent intent = navigate(context);
        intent.putExtra(FREE_ACCESS_POSITION_PARAM, freeAccessLimit);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            Fragment fragment;

            if(getIntent().getExtras().containsKey(FREE_ACCESS_POSITION_PARAM)){
                freeAccessPosition = getIntent().getIntExtra(FREE_ACCESS_POSITION_PARAM, -1);
                fragment = SpellStackFragment.newInstanceForSelection(SpellUtils.getCeilingLevelForSpellPosition(freeAccessPosition));
            } else {
                fragment = SpellStackFragment.newInstance(SpellbookType.FREE_ACCESS.bookId);
            }

            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_placeholder, fragment)
                    .commit();
        }
    }

    @Override
    public void onSpellSelected(Spell mLastSelectedSpell) {
        Intent intent = new Intent();
        intent.putExtra(SELECTED_SPELL_RESULT, mLastSelectedSpell.spellId);
        intent.putExtra(FREE_ACCESS_POSITION_PARAM, freeAccessPosition);
        setResult(RESULT_OK, intent);
        finish();
    }
}
