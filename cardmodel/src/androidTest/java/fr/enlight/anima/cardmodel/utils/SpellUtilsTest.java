package fr.enlight.anima.cardmodel.utils;


import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


public class SpellUtilsTest {

    @Test
    public void should_have_right_set_of_levels_for_major_path_and_no_secondary_book(){
        // Given
        SpellbookType spellbookType = SpellbookType.LIGHT;

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 100;
        witchspellsPath.freeAccessSpellsIds = new TreeMap<>();

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.reevaluateFreeAccessMap(witchspellsPath, spellbookType);

        // Then
        assertEquals(10, levelMapping.size());
        assertThat(levelMapping.keySet(), contains(4, 14, 24, 34, 44, 54, 64, 74, 84, 94));
    }

    @Test
    public void should_have_right_set_of_levels_for_major_path_and_with_secondary_book(){
        // Given
        SpellbookType spellbookType = SpellbookType.LIGHT;

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 2;
        witchspellsPath.pathLevel = 100;
        witchspellsPath.freeAccessSpellsIds = new TreeMap<>();

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.reevaluateFreeAccessMap(witchspellsPath, spellbookType);

        // Then
        assertEquals(0, levelMapping.size());
    }

    @Test
    public void should_have_right_set_of_levels_for_minor_path_and_no_secondary_book(){
        // Given
        SpellbookType spellbookType = SpellbookType.FIRE;

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 100;
        witchspellsPath.freeAccessSpellsIds = new TreeMap<>();

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.reevaluateFreeAccessMap(witchspellsPath, spellbookType);

        // Then
        assertEquals(20, levelMapping.size());
        assertThat(levelMapping.keySet(), contains(4, 8, 14, 18, 24, 28, 34, 38, 44, 48, 54, 58, 64, 68, 74, 78, 84, 88, 94, 98));
    }

    @Test
    public void should_have_right_set_of_levels_for_minor_path_and_with_secondary_book(){
        // Given
        SpellbookType spellbookType = SpellbookType.FIRE;

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 2;
        witchspellsPath.pathLevel = 100;
        witchspellsPath.freeAccessSpellsIds = new TreeMap<>();

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.reevaluateFreeAccessMap(witchspellsPath, spellbookType);

        // Then
        assertEquals(10, levelMapping.size());
        assertThat(levelMapping.keySet(), contains(8, 18, 28, 38, 48, 58, 68, 78, 88, 98));
    }

    @Test
    public void should_have_right_set_of_levels_for_n2_level(){
        // Given
        SpellbookType spellbookType = SpellbookType.FIRE;

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 12;
        witchspellsPath.freeAccessSpellsIds = new TreeMap<>();

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.reevaluateFreeAccessMap(witchspellsPath, spellbookType);

        // Then
        assertThat(levelMapping.keySet(), contains(4, 8));
    }

    @Test
    public void should_have_right_set_of_levels_for_n4_level(){
        // Given
        SpellbookType spellbookType = SpellbookType.FIRE;

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 14;
        witchspellsPath.freeAccessSpellsIds = new TreeMap<>();

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.reevaluateFreeAccessMap(witchspellsPath, spellbookType);

        // Then
        assertThat(levelMapping.keySet(), contains(4, 8, 14));
    }

    @Test
    public void should_have_right_set_of_levels_for_n6_level(){
        // Given
        SpellbookType spellbookType = SpellbookType.FIRE;

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 16;
        witchspellsPath.freeAccessSpellsIds = new TreeMap<>();

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.reevaluateFreeAccessMap(witchspellsPath, spellbookType);

        // Then
        assertThat(levelMapping.keySet(), contains(4, 8, 14));
    }

    @Test
    public void should_have_right_set_of_levels_for_n8_level(){
        // Given
        SpellbookType spellbookType = SpellbookType.FIRE;

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 18;
        witchspellsPath.freeAccessSpellsIds = new TreeMap<>();

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.reevaluateFreeAccessMap(witchspellsPath, spellbookType);

        // Then
        assertThat(levelMapping.keySet(), contains(4, 8, 14, 18));
    }

    @Test
    public void should_keep_selected_spells_after_secondary_path_deleted(){
        // Given
        SpellbookType spellbookType = SpellbookType.FIRE;

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 30;
        witchspellsPath.freeAccessSpellsIds = new TreeMap<>();
        witchspellsPath.freeAccessSpellsIds.put(8, 1);
        witchspellsPath.freeAccessSpellsIds.put(18, 2);
        witchspellsPath.freeAccessSpellsIds.put(28, 3);

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.reevaluateFreeAccessMap(witchspellsPath, spellbookType);

        // Then
        assertThat(levelMapping.keySet(), contains(4, 8, 14, 18, 24, 28));
        assertThat(levelMapping.get(8), equalTo(1));
        assertThat(levelMapping.get(18), equalTo(2));
        assertThat(levelMapping.get(28), equalTo(3));
    }

    @Test
    public void should_keep_selected_spells_after_level_increase(){
        // Given
        SpellbookType spellbookType = SpellbookType.FIRE;

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 30;
        witchspellsPath.freeAccessSpellsIds = new TreeMap<>();
        witchspellsPath.freeAccessSpellsIds.put(4, 1);
        witchspellsPath.freeAccessSpellsIds.put(8, 2);
        witchspellsPath.freeAccessSpellsIds.put(14, 3);
        witchspellsPath.freeAccessSpellsIds.put(18, 4);

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.reevaluateFreeAccessMap(witchspellsPath, spellbookType);

        // Then
        assertThat(levelMapping.keySet(), contains(4, 8, 14, 18, 24, 28));
        assertThat(levelMapping.get(4), equalTo(1));
        assertThat(levelMapping.get(8), equalTo(2));
        assertThat(levelMapping.get(14), equalTo(3));
        assertThat(levelMapping.get(18), equalTo(4));
        assertThat(levelMapping.get(24), equalTo(-1));
        assertThat(levelMapping.get(28), equalTo(-1));
    }

    @Test
    public void should_delete_spells_after_secondary_path_added(){
        // Given
        SpellbookType spellbookType = SpellbookType.FIRE;

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 2;
        witchspellsPath.pathLevel = 20;
        witchspellsPath.freeAccessSpellsIds = new TreeMap<>();
        witchspellsPath.freeAccessSpellsIds.put(4, 1);
        witchspellsPath.freeAccessSpellsIds.put(8, 2);
        witchspellsPath.freeAccessSpellsIds.put(14, 3);
        witchspellsPath.freeAccessSpellsIds.put(18, 4);

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.reevaluateFreeAccessMap(witchspellsPath, spellbookType);

        // Then
        assertThat(levelMapping.keySet(), contains(8, 18));
        assertThat(levelMapping.get(4), equalTo(null));
        assertThat(levelMapping.get(8), equalTo(2));
        assertThat(levelMapping.get(14), equalTo(null));
        assertThat(levelMapping.get(18), equalTo(4));
    }

    @Test
    public void should_delete_spells_after_level_decrease(){
        // Given
        SpellbookType spellbookType = SpellbookType.FIRE;

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 20;
        witchspellsPath.freeAccessSpellsIds = new TreeMap<>();
        witchspellsPath.freeAccessSpellsIds.put(4, 1);
        witchspellsPath.freeAccessSpellsIds.put(8, 2);
        witchspellsPath.freeAccessSpellsIds.put(14, 3);
        witchspellsPath.freeAccessSpellsIds.put(18, 4);
        witchspellsPath.freeAccessSpellsIds.put(24, 5);
        witchspellsPath.freeAccessSpellsIds.put(28, 6);

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.reevaluateFreeAccessMap(witchspellsPath, spellbookType);

        // Then
        assertThat(levelMapping.keySet(), contains(4, 8, 14, 18));
        assertThat(levelMapping.get(4), equalTo(1));
        assertThat(levelMapping.get(8), equalTo(2));
        assertThat(levelMapping.get(14), equalTo(3));
        assertThat(levelMapping.get(18), equalTo(4));
        assertThat(levelMapping.get(24), equalTo(null));
        assertThat(levelMapping.get(28), equalTo(null));
    }

    @Test
    public void should_have_right_set_of_levels_with_null_spellbook(){
        // Given
        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.pathBookId = 1;
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 100;
        witchspellsPath.freeAccessSpellsIds = new TreeMap<>();

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.reevaluateFreeAccessMap(witchspellsPath, null);

        // Then
        assertEquals(10, levelMapping.size());
        assertThat(levelMapping.keySet(), contains(4, 14, 24, 34, 44, 54, 64, 74, 84, 94));
    }

    @Test
    public void should_have_right_ceiling_level_for_XX_level(){
        assertEquals(20, SpellUtils.getCeilingLevelForSpellPosition(18));
        assertEquals(30, SpellUtils.getCeilingLevelForSpellPosition(24));
        assertEquals(40, SpellUtils.getCeilingLevelForSpellPosition(35));
    }
}