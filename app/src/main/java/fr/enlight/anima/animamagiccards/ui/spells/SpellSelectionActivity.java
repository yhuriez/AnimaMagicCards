package fr.enlight.anima.animamagiccards.ui.spells;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.ui.AnimaBaseActivity;
import fr.enlight.anima.animamagiccards.utils.OnBackPressedListener;
import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.utils.SpellUtils;


public class SpellSelectionActivity extends AnimaBaseActivity implements SpellStackFragment.Listener{

    public static final String SELECTED_SPELL_RESULT = "SELECTED_SPELL_RESULT";

    public static final String FREE_ACCESS_POSITION_PARAM = "FREE_ACCESS_POSITION_PARAM";

    public static final String SPELL_STACK_FRAGMENT_TAG = "SPELL_STACK_FRAGMENT_TAG";

    public static final String PREVIOUS_SPELL_KEY = "PREVIOUS_POSITION";

    private int mFreeAccessPosition = -1;

    private Spell mPreviousSpell = null;

    public static Intent navigate(Context context, int freeAccessLimit){
        Intent intent = new Intent(context, SpellSelectionActivity.class);
        intent.putExtra(FREE_ACCESS_POSITION_PARAM, freeAccessLimit);
        return intent;
    }

    public static Intent navigateWithPreviousSpell(Context context, Spell previousSpell) {
        Intent intent = new Intent(context, SpellSelectionActivity.class);
        intent.putExtra(PREVIOUS_SPELL_KEY, previousSpell);
        return intent;
    }

    public static Intent navigateForAllSpells(Context context) {
        return new Intent(context, SpellSelectionActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            mPreviousSpell = getIntent().getParcelableExtra(PREVIOUS_SPELL_KEY);
            mFreeAccessPosition = getIntent().getIntExtra(FREE_ACCESS_POSITION_PARAM, -1);
        }

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState == null){
            Fragment fragment;

            if(mFreeAccessPosition >= 0){
                fragment = SpellStackFragment.newInstanceForFreeSpellSelection(SpellUtils.getCeilingLevelForSpellPosition(mFreeAccessPosition));
            } else {
                fragment = SpellStackFragment.newInstanceForSpellSelection();
            }

            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_placeholder, fragment, SPELL_STACK_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onSpellSelected(Spell mLastSelectedSpell) {
        Intent intent = new Intent();
        intent.putExtra(SELECTED_SPELL_RESULT, mLastSelectedSpell);

        if (mPreviousSpell != null) {
            intent.putExtra(PREVIOUS_SPELL_KEY, mPreviousSpell);
        }

        if(mFreeAccessPosition >= 0){
            intent.putExtra(FREE_ACCESS_POSITION_PARAM, mFreeAccessPosition);
        }
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getFragmentManager().findFragmentByTag(SPELL_STACK_FRAGMENT_TAG);
        if (fragment != null && fragment instanceof OnBackPressedListener) {
            boolean catched = ((OnBackPressedListener) fragment).onBackPressed();
            if(!catched){
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }


}
