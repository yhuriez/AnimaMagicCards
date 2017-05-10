package fr.enlight.anima.animamagiccards.ui.witchspells;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import fr.enlight.anima.animamagiccards.R;

public class WitchspellsActivity extends AppCompatActivity implements WitchspellsListFragment.Listener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witchspells);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_placeholder, new WitchspellsListFragment())
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onAddWitchspells() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_placeholder, WitchspellsEditionFragment.newCreationInstance())
                .commit();
    }
}
