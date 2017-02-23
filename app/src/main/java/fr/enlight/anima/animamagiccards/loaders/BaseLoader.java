package fr.enlight.anima.animamagiccards.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 *
 */
public abstract class BaseLoader<T> extends AsyncTaskLoader<T> {

    public BaseLoader(final Context context) {
        super(context);
    }

    /* =======================================================================
     * Fallback management
     * =======================================================================*/

    T mLocalResult;

    @Override
    protected void onStartLoading() {
        if (mLocalResult == null) {
            forceLoad();
        } else {
            deliverResult(mLocalResult);
        }
    }

    @Override
    public void deliverResult(T data) {
        mLocalResult = data;
        super.deliverResult(data);
    }

}
