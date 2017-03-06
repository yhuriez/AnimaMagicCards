package fr.enlight.anima.animamagiccards;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import fr.enlight.anima.animamagiccards.ui.spellbooks.SpellActivity;
import fr.enlight.anima.animamagiccards.ui.witchspells.WitchspellsActivity;

import static fr.enlight.anima.animamagiccards.R.id.spellbooks_goto_button;
import static fr.enlight.anima.animamagiccards.R.id.witchspells_goto_button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        findViewById(witchspells_goto_button).setOnClickListener(this);
        findViewById(spellbooks_goto_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case spellbooks_goto_button:
                startActivity(new Intent(this, SpellActivity.class));
                break;

            case witchspells_goto_button:
                startActivity(new Intent(this, WitchspellsActivity.class));
                break;
        }
    }
}
