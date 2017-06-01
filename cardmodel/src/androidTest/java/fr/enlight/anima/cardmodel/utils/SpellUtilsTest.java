package fr.enlight.anima.cardmodel.utils;


import org.junit.Test;

import java.util.Map;

import fr.enlight.anima.cardmodel.model.spells.Spellbook;
import fr.enlight.anima.cardmodel.model.spells.SpellbookType;
import fr.enlight.anima.cardmodel.model.witchspells.WitchspellsPath;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;


public class SpellUtilsTest {

    @Test
    public void should_have_right_set_of_levels_for_major_path_and_no_secondary_book(){
        // Given
        Spellbook spellbook = new Spellbook();
        spellbook.spellbookType = SpellbookType.LIGHT;
        spellbook.type = "Majeure";

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 100;

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.generateDefaultFreeAccessMap(spellbook, witchspellsPath);

        // Then
        assertEquals(10, levelMapping.size());
        assertThat(levelMapping.keySet(), contains(4, 14, 24, 34, 44, 54, 64, 74, 84, 94));
    }

    @Test
    public void should_have_right_set_of_levels_for_major_path_and_with_secondary_book(){
        // Given
        Spellbook spellbook = new Spellbook();
        spellbook.spellbookType = SpellbookType.LIGHT;
        spellbook.type = "Majeure";

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 2;
        witchspellsPath.pathLevel = 100;

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.generateDefaultFreeAccessMap(spellbook, witchspellsPath);

        // Then
        assertEquals(0, levelMapping.size());
    }

    @Test
    public void should_have_right_set_of_levels_for_minor_path_and_no_secondary_book(){
        // Given
        Spellbook spellbook = new Spellbook();
        spellbook.spellbookType = SpellbookType.FIRE;
        spellbook.type = "Mineur";

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 100;

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.generateDefaultFreeAccessMap(spellbook, witchspellsPath);

        // Then
        assertEquals(20, levelMapping.size());
        assertThat(levelMapping.keySet(), contains(4, 8, 14, 18, 24, 28, 34, 38, 44, 48, 54, 58, 64, 68, 74, 78, 84, 88, 94, 98));
    }

    @Test
    public void should_have_right_set_of_levels_for_minor_path_and_with_secondary_book(){
        // Given
        Spellbook spellbook = new Spellbook();
        spellbook.spellbookType = SpellbookType.FIRE;
        spellbook.type = "Mineur";

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 2;
        witchspellsPath.pathLevel = 100;

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.generateDefaultFreeAccessMap(spellbook, witchspellsPath);

        // Then
        assertEquals(10, levelMapping.size());
        assertThat(levelMapping.keySet(), contains(8, 18, 28, 38, 48, 58, 68, 78, 88, 98));
    }

    @Test
    public void should_have_right_set_of_levels_for_n2_level(){
        // Given
        Spellbook spellbook = new Spellbook();
        spellbook.spellbookType = SpellbookType.FIRE;
        spellbook.type = "Mineur";

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 12;

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.generateDefaultFreeAccessMap(spellbook, witchspellsPath);

        // Then
        assertThat(levelMapping.keySet(), contains(4, 8));
    }

    @Test
    public void should_have_right_set_of_levels_for_n4_level(){
        // Given
        Spellbook spellbook = new Spellbook();
        spellbook.spellbookType = SpellbookType.DARKNESS;
        spellbook.type = "Mineur";

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 14;

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.generateDefaultFreeAccessMap(spellbook, witchspellsPath);

        // Then
        assertThat(levelMapping.keySet(), contains(4, 8, 14));
    }

    @Test
    public void should_have_right_set_of_levels_for_n6_level(){
        // Given
        Spellbook spellbook = new Spellbook();
        spellbook.spellbookType = SpellbookType.DARKNESS;
        spellbook.type = "Mineur";

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 16;

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.generateDefaultFreeAccessMap(spellbook, witchspellsPath);

        // Then
        assertThat(levelMapping.keySet(), contains(4, 8, 14));
    }

    @Test
    public void should_have_right_set_of_levels_for_n8_level(){
        // Given
        Spellbook spellbook = new Spellbook();
        spellbook.spellbookType = SpellbookType.DARKNESS;
        spellbook.type = "Mineur";

        WitchspellsPath witchspellsPath = new WitchspellsPath();
        witchspellsPath.secondaryPathBookId = 0;
        witchspellsPath.pathLevel = 18;

        // When
        Map<Integer, Integer> levelMapping = SpellUtils.generateDefaultFreeAccessMap(spellbook, witchspellsPath);

        // Then
        assertThat(levelMapping.keySet(), contains(4, 8, 14, 18));
    }

    @Test
    public void should_have_right_ceiling_level_for_XX_level(){
        assertEquals(20, SpellUtils.getCeilingLevelForSpellPosition(18));
        assertEquals(30, SpellUtils.getCeilingLevelForSpellPosition(24));
        assertEquals(40, SpellUtils.getCeilingLevelForSpellPosition(35));
    }
}