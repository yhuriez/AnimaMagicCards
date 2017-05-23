package fr.enlight.anima.animamagiccards.ui.spellbooks.utils;

import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;

import java.util.Arrays;
import java.util.List;

import fr.enlight.anima.animamagiccards.R;

/**
 *
 */
public enum SpellbookType {

    LIGHT(1, R.drawable.card_background_book_light, R.drawable.card_icon_book_light, R.string.spellbook_title_light),
    DARKNESS(2, R.drawable.card_background_book_darkness, R.drawable.card_icon_book_darkness, R.string.spellbook_title_darkness),
    CREATION(3, R.drawable.card_background_book_creation, R.drawable.card_icon_book_creation, R.string.spellbook_title_creation),
    DESTRUCTION(4, R.drawable.card_background_book_destruction, R.drawable.card_icon_book_destruction, R.string.spellbook_title_destruction),
    FIRE(5, R.drawable.card_background_book_fire, R.drawable.card_icon_book_fire, R.string.spellbook_title_fire),
    WATER(6, R.drawable.card_background_book_water, R.drawable.card_icon_book_water, R.string.spellbook_title_water),
    EARTH(7, R.drawable.card_background_book_earth, R.drawable.card_icon_book_earth, R.string.spellbook_title_earth),
    WIND(8, R.drawable.card_background_book_wind, R.drawable.card_icon_book_wind, R.string.spellbook_title_wind),
    ESSENCE(9, R.drawable.card_background_book_essence, R.drawable.card_icon_book_essence, R.string.spellbook_title_essence),
    ILLUSION(10, R.drawable.card_background_book_illusion, R.drawable.card_icon_book_illusion, R.string.spellbook_title_illusion),
    NECROMANCER(11, R.drawable.card_background_book_necromancer, R.drawable.card_icon_book_necromancer, R.string.spellbook_title_necromancer),
    FREE_ACCESS(12, R.drawable.card_background_book_free_access, R.drawable.card_icon_book_free_access, R.string.spellbook_title_free_access),
    CHAOS(13, R.drawable.card_background_book_chaos, R.drawable.card_icon_book_chaos, R.string.spellbook_title_chaos),
    WAR(14, R.drawable.card_background_book_war, R.drawable.card_icon_book_war, R.string.spellbook_title_war),
    LITERAE(15, R.drawable.card_background_book_literae, R.drawable.card_icon_book_literae, R.string.spellbook_title_literae),
    DEATH(16, R.drawable.card_background_book_death, R.drawable.card_icon_book_death, R.string.spellbook_title_death),
    MUSIC(17, R.drawable.card_background_book_music, R.drawable.card_icon_book_music, R.string.spellbook_title_music),
    NOBILITY(18, R.drawable.card_background_book_nobility, R.drawable.card_icon_book_nobility, R.string.spellbook_title_nobility),
    PEACE(19, R.drawable.card_background_book_peace, R.drawable.card_icon_book_peace, R.string.spellbook_title_peace),
    SIN(20, R.drawable.card_background_book_sin, R.drawable.card_icon_book_sin, R.string.spellbook_title_sin),
    KNOWLEDGE(21, R.drawable.card_background_book_knowledge, R.drawable.card_icon_book_knowledge, R.string.spellbook_title_knowledge),
    BLOOD(22, R.drawable.card_background_book_blood, R.drawable.card_icon_book_blood, R.string.spellbook_title_blood),
    DREAM(23, R.drawable.card_background_book_dream, R.drawable.card_icon_book_dream, R.string.spellbook_title_dream),
    TIME(24, R.drawable.card_background_book_time, R.drawable.card_icon_book_time, R.string.spellbook_title_time),
    LIMIT(25, R.drawable.card_background_book_limit, R.drawable.card_icon_book_limit, R.string.spellbook_title_limit),
    EMPTYNESS(26, R.drawable.card_background_book_emptyness, R.drawable.card_icon_book_emptyness, R.string.spellbook_title_emptyness);

    public final int bookId;
    public final @DrawableRes int cardBackgroundRes;
    public final @DrawableRes int iconRes;
    public final @StringRes int titleRes;

    SpellbookType(int bookId, @DrawableRes int backgroundRes, @DrawableRes int iconRes, @StringRes int titleRes) {
        this.bookId = bookId;
        this.cardBackgroundRes = backgroundRes;
        this.iconRes = iconRes;
        this.titleRes = titleRes;
    }

    // /////////////
    // Static access
    // /////////////

    public static final List<SpellbookType> MAIN_SPELLBOOKS = Arrays.asList(
            LIGHT, DARKNESS,
            CREATION, DESTRUCTION,
            FIRE, WATER,
            EARTH, WIND,
            ESSENCE, ILLUSION,
            NECROMANCER
    );

    public static final List<SpellbookType> SECONDARY_SPELLBOOKS = Arrays.asList(
            CHAOS, WAR, LITERAE, DEATH, MUSIC, NOBILITY, PEACE, SIN, KNOWLEDGE, BLOOD, DREAM, TIME,
            LIMIT, EMPTYNESS
    );

    public static SpellbookType getTypeFromBookId(int bookId){
        for (SpellbookType spellbookType : values()) {
            if(spellbookType.bookId == bookId){
                return spellbookType;
            }
        }
        return null;
    }
}
