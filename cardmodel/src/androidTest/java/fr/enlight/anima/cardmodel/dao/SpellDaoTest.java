package fr.enlight.anima.cardmodel.dao;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import fr.enlight.anima.cardmodel.model.spells.Spell;
import fr.enlight.anima.cardmodel.model.spells.SpellbookResponse;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;

import static fr.enlight.anima.cardmodel.model.spells.SpellbookType.FREE_ACCESS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

/**
 *
 */
@RunWith(AndroidJUnit4.class)
public class SpellDaoTest {

    private final SpellDao spellDao = new SpellDao();

    private Context context;
    private String mLanguage;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getTargetContext();
        mLanguage = Locale.getDefault().toString();
    }

    @Test
    public void should_have_right_count_of_spellbooks_when_load_all_spells() {
        // When
        SpellbookIndexResponse spellbookIndex = spellDao.getSpellbookIndex(context, mLanguage);

        // Then
        assertThat(spellbookIndex.spellbooks, notNullValue());
        assertThat(spellbookIndex.spellbooks.size(), equalTo(26));
    }

    @Test
    public void should_have_right_count_of_spell_when_load_all_spells() {
        for (SpellbookType spellbookType : SpellbookType.values()) {

            String spellbookName = spellbookType.name();

            int expectedNumberOfSpells = -1;
            switch (spellbookType.pathType) {
                case MAJOR_PATH:
                    expectedNumberOfSpells = 40;
                    break;
                case MINOR_PATH:
                    expectedNumberOfSpells = 30;
                    break;
                case FREE_ACCESS_PATH:
                    expectedNumberOfSpells = 120;
                    break;
                case SECONDARY_PATH:
                    expectedNumberOfSpells = 10;
                    break;
            }

            // When
            SpellbookResponse spellbook = spellDao.getSpellbook(context, spellbookType.bookId, mLanguage);

            // Then
            assertThat(spellbookName, spellbook.spells, notNullValue());
            assertThat(spellbookName, spellbook.spells.size(), equalTo(expectedNumberOfSpells));
        }
    }

    @Test
    public void should_have_free_spells_right_count_of_spell_for_every_level_when_load_free_access_spells() {
        // When
        SpellbookResponse spellbook = spellDao.getSpellbook(context, FREE_ACCESS.bookId, mLanguage);

        // Then
        int levelCount = 0;
        int currentLevel = spellbook.spells.get(0).level;
        for (Spell spell : spellbook.spells) {

            if (currentLevel != spell.level) {

                assertThat("Level " + currentLevel, levelCount, equalTo(12));

                currentLevel = spell.level;
                levelCount = 0;
            }

            levelCount++;
        }
    }

    @Test
    public void should_have_spells_filled_when_load_all_spells() {
        for (SpellbookType spellbookType : SpellbookType.values()) {

            String spellbookName = spellbookType.name();

            // When
            SpellbookResponse spellbook = spellDao.getSpellbook(context, spellbookType.bookId, mLanguage);

            // Then
            for (Spell spell : spellbook.spells) {
                String tag = spellbookName + "(" + spellbook.bookId + ") - spell " + spell.spellId + " => " + spell.name;

                assertThat(tag, spell, notNullValue());
                assertThat(tag, spell.actionType, not(isEmptyString()));
                assertThat(tag, spell.effect, not(isEmptyString()));
                assertThat(tag, spell.type, not(isEmptyString()));

                assertThat(tag, spell.initialGrade, notNullValue());
                assertThat(tag, spell.initialGrade.effect, not(isEmptyString()));

                assertThat(tag, spell.intermediateGrade, notNullValue());
                assertThat(tag, spell.intermediateGrade.effect, not(isEmptyString()));

                assertThat(tag, spell.advancedGrade, notNullValue());
                assertThat(tag, spell.advancedGrade.effect, not(isEmptyString()));

                assertThat(tag, spell.arcaneGrade, notNullValue());
                assertThat(tag, spell.arcaneGrade.effect, not(isEmptyString()));
            }
        }
    }

    @Test
    public void should_have_same_values_between_language_when_load_all_spells_from_managed_languages() {
        String[] languages = new String[]{
                Locale.FRENCH.toString(),
                Locale.US.toString()
        };

        for (SpellbookType spellbookType : SpellbookType.values()) {

            List<Map<String, Spell>> allSpellLanguage = new ArrayList<>();

            for (String language : languages) {
                SpellbookResponse spellbook = spellDao.getSpellbook(context, spellbookType.bookId, language);

                for (int index = 0; index < spellbook.spells.size(); index++) {
                    Map<String, Spell> languageSpellMap = null;

                    if(allSpellLanguage.size() > index){
                        languageSpellMap = allSpellLanguage.get(index);
                    }

                    if (languageSpellMap == null) {
                        languageSpellMap = new HashMap<>();
                        allSpellLanguage.add(languageSpellMap);
                    }
                    languageSpellMap.put(language, spellbook.spells.get(index));
                }
            }

            // Then
            for (Map<String, Spell> languageSpellMap : allSpellLanguage) {
                Spell firstReference = languageSpellMap.get(Locale.FRENCH.toString());

                for (Map.Entry<String, Spell> languageSpellEntry : languageSpellMap.entrySet()) {

                    String language = languageSpellEntry.getKey();
                    if (language.equals(Locale.FRENCH.toString())) {
                        continue;
                    }

                    Spell spell = languageSpellEntry.getValue();

                    String tag = spellbookType.name() + "(" + spellbookType.bookId + ") - spell " + spell.spellId + " (" + language + ") => " ;

                    assertThat(tag + "bookId", spell.bookId, equalTo(firstReference.bookId));
                    assertThat(tag + "freeAccessAssociatedType", spell.freeAccessAssociatedType, equalTo(firstReference.freeAccessAssociatedType));
                    assertThat(tag + "level", spell.level, equalTo(firstReference.level));
                    assertThat(tag + "spellId", spell.spellId, equalTo(firstReference.spellId));
                    assertThat(tag + "withRetention", spell.withRetention, equalTo(firstReference.withRetention));
                    assertThat(tag + "dailyRetention", spell.dailyRetention, equalTo(firstReference.dailyRetention));

                    assertThat(tag + "initialGrade.requiredIntelligence",
                            spell.initialGrade.requiredIntelligence, equalTo(firstReference.initialGrade.requiredIntelligence));
                    assertThat(tag + "initialGrade.retention",
                            spell.initialGrade.retention, equalTo(firstReference.initialGrade.retention));
                    assertThat(tag + "initialGrade.zeon",
                            spell.initialGrade.zeon, equalTo(firstReference.initialGrade.zeon));

                    assertThat(tag + "intermediateGrade.requiredIntelligence",
                            spell.intermediateGrade.requiredIntelligence, equalTo(firstReference.intermediateGrade.requiredIntelligence));
                    assertThat(tag + "intermediateGrade.retention",
                            spell.intermediateGrade.retention, equalTo(firstReference.intermediateGrade.retention));
                    assertThat(tag + "intermediateGrade.zeon",
                            spell.intermediateGrade.zeon, equalTo(firstReference.intermediateGrade.zeon));

                    assertThat(tag + "advancedGrade.requiredIntelligence",
                            spell.advancedGrade.requiredIntelligence, equalTo(firstReference.advancedGrade.requiredIntelligence));
                    assertThat(tag + "advancedGrade.retention",
                            spell.advancedGrade.retention, equalTo(firstReference.advancedGrade.retention));
                    assertThat(tag + "advancedGrade.zeon",
                            spell.advancedGrade.zeon, equalTo(firstReference.advancedGrade.zeon));

                    assertThat(tag + "arcaneGrade.requiredIntelligence",
                            spell.arcaneGrade.requiredIntelligence, equalTo(firstReference.arcaneGrade.requiredIntelligence));
                    assertThat(tag + "arcaneGrade.retention",
                            spell.arcaneGrade.retention, equalTo(firstReference.arcaneGrade.retention));
                    assertThat(tag + "arcaneGrade.zeon",
                            spell.arcaneGrade.zeon, equalTo(firstReference.arcaneGrade.zeon));
                }
            }
        }

    }
}