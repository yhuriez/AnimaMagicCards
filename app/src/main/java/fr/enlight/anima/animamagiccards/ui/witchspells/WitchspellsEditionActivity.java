package fr.enlight.anima.animamagiccards.ui.witchspells;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;

import fr.enlight.anima.animamagiccards.R;
import fr.enlight.anima.animamagiccards.async.SaveWitchspellsAsyncTask;
import fr.enlight.anima.animamagiccards.databinding.ActivityWitchspellsEditionBinding;
import fr.enlight.anima.animamagiccards.ui.AnimaBaseActivity;
import fr.enlight.anima.animamagiccards.ui.witchspells.viewmodels.WitchspellsEditionViewModel;
import fr.enlight.anima.animamagiccards.utils.DeviceUtils;
import fr.enlight.anima.animamagiccards.views.BindingDialogFragment;
import fr.enlight.anima.animamagiccards.views.viewmodels.ProgressViewModel;
import fr.enlight.anima.cardmodel.model.witchspells.Witchspells;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

public class WitchspellsEditionActivity extends AnimaBaseActivity implements WitchspellsEditionViewModel.Listener, SaveWitchspellsAsyncTask.Listener {

    private static final int MAIN_PATH_CHOOSER_REQUEST_CODE = 456;

    private static final String DIALOG_FRAGMENT_TAG = "DIALOG_FRAGMENT_TAG";

    private static String WITCHSPELLS_PARAM = "WITCHSPELLS_PARAM";

    private Witchspells mWitchspells;
    private WitchspellsEditionViewModel viewModel;

    private BindingDialogFragment progressDialog;


    public static Intent navigate(Context context){
        return new Intent(context, WitchspellsEditionActivity.class);
    }

    public static Intent navigateForEdition(Context context, Witchspells witchspells){
        Intent intent = navigate(context);
        intent.putExtra(WITCHSPELLS_PARAM, witchspells);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWitchspellsEditionBinding mBinding = DataBindingUtil.setContentView(this, R.layout.activity_witchspells_edition);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWitchspells = getIntent().getParcelableExtra(WITCHSPELLS_PARAM);
        if(mWitchspells == null){
            mWitchspells = new Witchspells();
            mWitchspells.witchPaths = new ArrayList<>();

            getSupportActionBar().setTitle(R.string.Witchspells_Title_Create);

        } else {
            getSupportActionBar().setTitle(getString(R.string.Witchspells_Name_Format, mWitchspells.witchName));
        }

        viewModel = new WitchspellsEditionViewModel(mWitchspells, this);
        mBinding.setModel(viewModel);
    }

    @Override
    public void onAddPath() {
        DeviceUtils.hideSoftKeyboard(getWindow().getCurrentFocus());

        Intent intent = WitchspellsMainPathChooserActivity.navigateForEdition(this, new ArrayList<>(mWitchspells.witchPaths));
        startActivityForResult(intent, MAIN_PATH_CHOOSER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MAIN_PATH_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK){
            mWitchspells.witchPaths = data.getParcelableArrayListExtra(WitchspellsMainPathChooserActivity.WITCHSPELLS_PATHS_RESULT);
            viewModel.refreshWitchPath();
        }
    }

    @Override
    public void onPathSelected(WitchspellsPath witchspellsPath) {
        Intent intent = WitchspellsMainPathChooserActivity.navigateForEdition(this, new ArrayList<>(mWitchspells.witchPaths));
        startActivityForResult(intent, MAIN_PATH_CHOOSER_REQUEST_CODE);
    }

    @Override
    public void onModifyWitchPath() {
        DeviceUtils.hideSoftKeyboard(getWindow().getCurrentFocus());

        Intent intent = WitchspellsMainPathChooserActivity.navigateForEdition(this, new ArrayList<>(mWitchspells.witchPaths));
        startActivityForResult(intent, MAIN_PATH_CHOOSER_REQUEST_CODE);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onValidateWitchspells() {
        String witchName = mWitchspells.witchName;
        if(TextUtils.isEmpty(witchName)){
            Toast.makeText(this, getString(R.string.Error_No_Witchspells_Name), Toast.LENGTH_LONG).show();
            return;
        }

        new SaveWitchspellsAsyncTask(this).execute(mWitchspells);
    }

    @Override
    public void onSaveStarted() {
        ProgressViewModel progressViewModel = new ProgressViewModel(getString(R.string.Witchspells_Save_Waiting_Message));
        progressDialog = BindingDialogFragment.newInstance(progressViewModel);
        progressDialog.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
    }

    @Override
    public void onSaveFinished() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.containsKey(WITCHSPELLS_PARAM)) {
            // Case of Witchspells edition
            super.onBackPressed();

        } else {
            // Case of Witchspells creation
            new AlertDialog.Builder(this)
                    .setTitle(R.string.Common_Leave)
                    .setMessage(R.string.Witchspells_Cancel_Creation)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            WitchspellsEditionActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
    }
}
