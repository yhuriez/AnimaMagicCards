package fr.enlight.anima.cardmodel.dao;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import fr.enlight.anima.cardmodel.model.spells.SpellbookResponse;

import static android.content.ContentValues.TAG;

/**
 *
 */

public class SpellDao {

    private static final String SPELLBOOK_DIRECTORY_NAME = "spellbooks";
    private static final String SPELLBOOK_INDEX_FILE_NAME = "spellbooks_index.json";
    private static final String SPELLBOOK_FILE_NAME = "spellbook_";

    /**
     * @return the index of all existing spellbooks
     */
    public SpellbookIndexResponse getSpellbookIndex(Context context, String locale) {
        LocalizedFileDao localizedFileDao = new LocalizedFileDao(SPELLBOOK_DIRECTORY_NAME, locale);
        String filename = localizedFileDao.getLocalizedAssetFilename(SPELLBOOK_INDEX_FILE_NAME);
        return readFile(context, filename, SpellbookIndexResponse.class);
    }

    /**
     *
     * @param spellbookId the bookId of the spellbook to retrieve
     * @return the complete spellbook with all contained spells
     */
    public SpellbookResponse getSpellbook(Context context, int spellbookId, String locale) {
        LocalizedFileDao localizedFileDao = new LocalizedFileDao(SPELLBOOK_DIRECTORY_NAME, locale);

        String filename = SPELLBOOK_FILE_NAME + spellbookId + ".json";
        String completeFilename = localizedFileDao.getLocalizedAssetFilename(filename);

        return readFile(context, completeFilename, SpellbookResponse.class);
    }


    private <T> T readFile(Context context, String filename, Class<T> classToRead){
        InputStream is = null;
        InputStreamReader reader = null;
        try {
            final AssetManager assets = context.getAssets();

            is = assets.open(filename);
            reader = new InputStreamReader(is);

            final GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            return gson.fromJson(reader, classToRead);

        } catch (Exception e) {
            Log.d(TAG, "Error while parsing JSON into OMDBSearchResponse", e);
            return null;

        } finally {
            try {
                if(is != null){
                    is.close();
                }
                if(reader != null){
                    reader.close();
                }
            } catch (IOException e) {
                Log.d(TAG, "Error while closing assets files", e);
            }
        }
    }


}
