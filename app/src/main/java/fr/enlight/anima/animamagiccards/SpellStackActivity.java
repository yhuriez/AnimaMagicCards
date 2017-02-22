package fr.enlight.anima.animamagiccards;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.loopeer.cardstack.CardStackView;

import java.util.List;

import fr.enlight.anima.animamagiccards.databinding.ActivitySpellsStackBinding;
import fr.enlight.anima.animamagiccards.loaders.SpellsLoader;
import fr.enlight.anima.animamagiccards.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.utils.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.viewmodels.ListBindableViewModel;

/**
 *
 */
public class SpellStackActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BindableViewModel>>, CardStackView.ItemExpendListener {

    private static final String SPELLBOOK_ID = "SPELLBOOK_ID";
    private static final String SPELLBOOK_TYPE = "SPELLBOOK_TYPE";

    private ListBindableViewModel spellViewModels;

    public static Intent navigate(Context context, int spellbookId, SpellbookType type){
        Intent intent = new Intent(context, SpellStackActivity.class);
        intent.putExtra(SPELLBOOK_ID, spellbookId);
        intent.putExtra(SPELLBOOK_TYPE, type.name());
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySpellsStackBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_spells_stack);

        spellViewModels = new ListBindableViewModel();
        binding.setModel(spellViewModels);
        binding.setListener(this);

        getLoaderManager().initLoader(1, getIntent().getExtras(), this);
    }

    @Override
    public Loader<List<BindableViewModel>> onCreateLoader(int id, Bundle args) {
        if(args.containsKey(SPELLBOOK_ID)){
            String bookType = args.getString(SPELLBOOK_TYPE);
            return new SpellsLoader(this, args.getInt(SPELLBOOK_ID), SpellbookType.valueOf(bookType));
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<BindableViewModel>> loader, List<BindableViewModel> data) {
        spellViewModels.setViewModels(data);
    }

    @Override
    public void onLoaderReset(Loader<List<BindableViewModel>> loader) {
    }

    @Override
    public void onItemExpend(boolean expend) {

    }
}
