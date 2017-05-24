package fr.enlight.anima.animamagiccards.async;

import android.content.AsyncTaskLoader;
import android.content.Context;

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
