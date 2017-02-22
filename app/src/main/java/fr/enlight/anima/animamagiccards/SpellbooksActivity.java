package fr.enlight.anima.animamagiccards;

import android.app.LoaderManager;
import android.content.Loader;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;

import java.util.List;

import fr.enlight.anima.animamagiccards.databinding.ActivitySpellbooksIndexBinding;
import fr.enlight.anima.animamagiccards.loaders.SpellbooksIndexLoader;
import fr.enlight.anima.animamagiccards.utils.SpellbookType;
import fr.enlight.anima.animamagiccards.utils.bindingrecyclerview.BindableViewModel;
import fr.enlight.anima.animamagiccards.viewmodels.RecyclerViewModel;
import fr.enlight.anima.animamagiccards.viewmodels.SpellbookViewModel;

public class SpellbooksActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BindableViewModel>>,
        SpellbookViewModel.Listener {

    private RecyclerViewModel recyclerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySpellbooksIndexBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_spellbooks_index);

        recyclerViewModel = new RecyclerViewModel();
        recyclerViewModel.setLayoutManager(new GridLayoutManager(this, 2));

        binding.setModel(recyclerViewModel);

        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public Loader<List<BindableViewModel>> onCreateLoader(int i, Bundle bundle) {
        return new SpellbooksIndexLoader(this, this);
    }

    @Override
    public void onLoadFinished(Loader<List<BindableViewModel>> loader, List<BindableViewModel> spellbookViewModels) {
        recyclerViewModel.setViewModels(spellbookViewModels);
    }

    @Override
    public void onLoaderReset(Loader<List<BindableViewModel>> loader) {
        // Nothing to do
    }

    @Override
    public void onSpellbookClicked(int spellbookId, SpellbookType type) {
        startActivity(SpellStackActivity.navigate(this, spellbookId, type));
    }
}
