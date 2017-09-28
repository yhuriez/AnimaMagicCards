package fr.enlight.spellgenerator

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import fr.enlight.spellgenerator.spells.Spell
import fr.enlight.spellgenerator.spells.Spellbook
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.collection.IsCollectionWithSize.hasSize
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import java.io.InputStreamReader

/**
 *
 */
@RunWith(AndroidJUnit4::class)
class SpellFileParserTest {

    val descriptor = SpanishSpellFileDescriptor()

    @Test
    fun should_have_14_secondary_spellbooks_when_ask_parse_spanish() {
        // Given
        val spells = InputStreamReader(InstrumentationRegistry.getContext().assets.open("arcana_exxet_spells_spanish.csv"))
                .readLines()

        // When
        val spellFileParser = SpellFileParser(descriptor, true)
        val spellbooksLines = spellFileParser.getSpellbooksLines(spells)

        // Then
        assertThat(spellbooksLines, hasSize(14))
        assertThat(spellbooksLines[0], hasSize(123))

        val firstLines = spellbooksLines
                .fold(ArrayList<String>()) { acc, lineList -> acc.add(lineList[0]); acc }
                .map { it.split(":", ";")[1].trim() }
                .toMutableList()

        assertThat(firstLines, equalTo(listOf("CAOS", "GUERRA", "LITERAE", "MUERTE", "MUSICAL", "NOBLEZA",
                "PAZ", "PECADO", "CONOCIMIENTO", "SANGRE", "SUEÑOS", "TIEMPO", "UMBRAL", "VACÍO")))
    }

    @Test
    fun should_have_12_primary_spellbooks_when_ask_parse_spanish() {
        // Given
        val spells = InputStreamReader(InstrumentationRegistry.getContext().assets.open("core_exxet_spells_spanish.csv"))
                .readLines()

        // When
        val spellFileParser = SpellFileParser(descriptor, false)
        val spellbooksLines = spellFileParser.getSpellbooksLines(spells)

        // Then
        assertThat(spellbooksLines, hasSize(12))
        assertThat(spellbooksLines[0], hasSize(476))

        val firstLines = spellbooksLines
                .fold(ArrayList<String>()) { acc, lineList -> acc.add(lineList[0]); acc }
                .map { it.split(":", ";")[1].trim() }
                .toMutableList()

        Log.i(javaClass.simpleName, firstLines.toString())
        assertThat(firstLines, equalTo(listOf("Luz", "Oscuridad", "Creación", "Destrucción", "Aire",
                "Agua", "Fuego", "Tierra", "Esencia", "Ilusión", "Nigromancia", "Libre Acceso")))
    }

    @Test
    fun should_have_10_spells_for_every_spellbook_when_ask_parse_spanish() {
        // Given
        val spells = InputStreamReader(InstrumentationRegistry.getContext().assets.open("arcana_exxet_spells_spanish.csv"))
                .readLines()

        val spellFileParser = SpellFileParser(descriptor, true)

        val spellbooksLines = spellFileParser.getSpellbooksLines(spells)

        spellbooksLines.forEach {
            val spellbookName = it[0].split(";")[0].trim()

            // When
            val spellsLines = spellFileParser.getSpellsLines(it)

            val firstLines = spellsLines
                    .fold(ArrayList<String>()) { acc, lineList -> acc.add(lineList[0]); acc }
                    .map { it.split(";")[0].trim() }
                    .toMutableList()

            Log.i(javaClass.simpleName, firstLines.toString())

            // Then
            assertThat(spellbookName, spellsLines, hasSize(10))
        }
    }

    @Test
    fun should_create_secondary_path_spellbook_when_ask_parse_first_lines_in_spanish() {
        // Given
        val spellbookFirstLines = listOf(
                "Spellbook:CAOS;;;;;\n",
                "Esta vía maneja el principio absoluto del caos, el poder de lo impredecible y la alteración de los acontecimientos.;;;;;\n",
                "Vínculos: Cerrados, Nigromancia, Ilusión, Esencia, Fuego, Tierra, Agua.;;;;;\n",
                "Sentir el Caos;;;;;\n",
                "Nivel: 4 Acción: Activa;;;;;"
        )

        val spellFileParser = SpellFileParser(descriptor, true)

        // When
        val spellbook = spellFileParser.createSpellbook(spellbookFirstLines)

        // Then
        assertThat(spellbook.bookName, equalTo("CAOS"))
        assertThat(spellbook.description, equalTo("Esta vía maneja el principio absoluto del caos, el poder de lo impredecible y la alteración de los acontecimientos."))
        assertThat(spellbook.primaryBookUnaccessibles, equalTo(mutableListOf("Nigromancia", "Ilusión", "Esencia", "Fuego", "Tierra", "Agua")))
    }

    @Test
    fun should_create_primary_path_spellbook_when_ask_parse_first_lines_in_spanish() {
        // Given
        val spellbookFirstLines = listOf(
                "Spellbook:Luz;;;;;\n",
                "Sentir el Caos;;;;;\n",
                "Nivel: 4 Acción: Activa;;;;;"
        )

        val spellFileParser = SpellFileParser(descriptor, false)

        // When
        val spellbook = spellFileParser.createSpellbook(spellbookFirstLines)

        // Then
        assertThat(spellbook.bookName, equalTo("Luz"))
    }

    @Test
    fun should_map_primary_path_spells_line_when_ask_mapping_of_spells_in_spanish() {
        // Given
        val spellFirstLines = listOf(
                "Crear Luz;;;;;;;;;;;;;\n",
                "Nivel: 2 /Acción: Activa /Tipo: Efecto;;;;;;;;;;;;;\n",
                "Efecto: Crea una fuente de luz en un punto u objeto determinado por el;;;;;;;;;;;;;\n",
                "hechicero.;;;;;;;;;;;;;\n",
                "Grado;Base;Intermedio;Avanzado;Arcano;;;;;;;;;\n",
                "Zeon;20;50;100;200;;;;;;;;;\n",
                "Int. R.;5;8;10;12;;;;;;;;;\n",
                "Base: 5 metros de zona iluminada.;;;;;;;;;;;;;\n",
                "Intermedio: 25 metros de zona iluminada.;;;;;;;;;;;;;\n",
                "Avanzado: 100 metros de zona iluminada.;;;;;;;;;;;;;\n",
                "Arcano: 500 metros de zona iluminada.;;;;;;;;;;;;;\n",
                "Mantenimiento: 5 / 5 / 10 / 15 Diario;;;;;;;;;;;;;"
        )

        val spellFileParser = SpellFileParser(descriptor, true)

        // When
        val spellMap = spellFileParser.spellMapFromSpellLines(spellFirstLines)

        // Then
        assertThat(spellMap.entries, hasSize(11))
        assertThat(spellMap.keys, hasItems(
                descriptor.titleMarker,
                descriptor.levelMarker,
                descriptor.effectMarker,
                descriptor.zeonMarker,
                descriptor.intelligenceMarker,
                descriptor.baseGradeMarker,
                descriptor.intermediateGradeMarker,
                descriptor.advancedGradeMarker,
                descriptor.arcaneGradeMarker,
                descriptor.retentionMarker)
        )

        assertThat(spellMap[descriptor.titleMarker], equalTo("Title:Crear Luz;;;;;;;;;;;;;\n"))
        assertThat(spellMap[descriptor.levelMarker], equalTo("Nivel: 2 /Acción: Activa /Tipo: Efecto;;;;;;;;;;;;;\n"))
        assertThat(spellMap[descriptor.effectMarker], equalTo("Efecto: Crea una fuente de luz en un punto u objeto determinado por el;;;;;;;;;;;;;\n hechicero."))
        assertThat(spellMap[descriptor.zeonMarker], equalTo("Zeon;20;50;100;200;;;;;;;;;\n"))
        assertThat(spellMap[descriptor.intelligenceMarker], equalTo("Int. R.;5;8;10;12;;;;;;;;;\n"))
        assertThat(spellMap[descriptor.baseGradeMarker], equalTo("Base: 5 metros de zona iluminada.;;;;;;;;;;;;;\n"))
        assertThat(spellMap[descriptor.intermediateGradeMarker], equalTo("Intermedio: 25 metros de zona iluminada.;;;;;;;;;;;;;\n"))
        assertThat(spellMap[descriptor.advancedGradeMarker], equalTo("Avanzado: 100 metros de zona iluminada.;;;;;;;;;;;;;\n"))
        assertThat(spellMap[descriptor.arcaneGradeMarker], equalTo("Arcano: 500 metros de zona iluminada.;;;;;;;;;;;;;\n"))
        assertThat(spellMap[descriptor.retentionMarker], equalTo("Mantenimiento: 5 / 5 / 10 / 15 Diario;;;;;;;;;;;;;"))
    }

    @Test
    fun should_map_secondary_path_spells_line_when_ask_mapping_of_spells_in_spanish() {
        // Given
        val spellFirstLines = listOf(
                "Sentir el Caos;;;;;\n",
                "Nivel: 4 /Acción: Activa;;;;;\n",
                "Efecto: El hechicero es capaz de notar las fluctuaciones provocadas por el caos en el ambiente, permitiéndole sentir cualquier cosa innatural para la realidad que ocurra dentro del área del conjuro. No determina la localización exacta, pero si es consciente del nivel de influencia que el caos provoca en la existencia.;;;;;\n",
                "Grado Base Intermedio Avanzado Arcano;;;;;\n",
                "Zeon;50;80;100;120;\n",
                "Int. R.;5;8;10;12;\n",
                "Base: 50 metros de radio.;;;;;\n",
                "Intermedio: 100 metros de radio.;;;;;\n",
                "Avanzado: 250 metros de radio.;;;;;\n",
                "Arcano: 500 metros de radio / El hechicero percibe la presencia de cualquier ser con Gnosis superior a 20 o Natura superior a 10 que esté dentro de la zona de acción del conjuro si estos no superan una RM contra 160.;;;;;\n",
                "Mantenimiento: 5 / 10 / 10 / 15 /Diario;;;;;\n",
                "Tipo de Conjuro: Efecto, Detección;;;;;"
        )

        val spellFileParser = SpellFileParser(descriptor, true)

        // When
        val spellMap = spellFileParser.spellMapFromSpellLines(spellFirstLines)

        // Then
        assertThat(spellMap.entries, hasSize(12))
        assertThat(spellMap.keys, hasItems(
                descriptor.titleMarker,
                descriptor.levelMarker,
                descriptor.effectMarker,
                descriptor.zeonMarker,
                descriptor.intelligenceMarker,
                descriptor.baseGradeMarker,
                descriptor.intermediateGradeMarker,
                descriptor.advancedGradeMarker,
                descriptor.arcaneGradeMarker,
                descriptor.retentionMarker,
                descriptor.fullTypeMarker)
        )

        assertThat(spellMap[descriptor.titleMarker], equalTo("Title:Sentir el Caos;;;;;\n"))
        assertThat(spellMap[descriptor.levelMarker], equalTo("Nivel: 4 /Acción: Activa;;;;;\n"))
        assertThat(spellMap[descriptor.effectMarker], equalTo("Efecto: El hechicero es capaz de notar las fluctuaciones provocadas por el caos en el ambiente, permitiéndole sentir cualquier cosa innatural para la realidad que ocurra dentro del área del conjuro. No determina la localización exacta, pero si es consciente del nivel de influencia que el caos provoca en la existencia.;;;;;\n"))
        assertThat(spellMap[descriptor.zeonMarker], equalTo("Zeon;50;80;100;120;\n"))
        assertThat(spellMap[descriptor.intelligenceMarker], equalTo("Int. R.;5;8;10;12;\n"))
        assertThat(spellMap[descriptor.baseGradeMarker], equalTo("Base: 50 metros de radio.;;;;;\n"))
        assertThat(spellMap[descriptor.intermediateGradeMarker], equalTo("Intermedio: 100 metros de radio.;;;;;\n"))
        assertThat(spellMap[descriptor.advancedGradeMarker], equalTo("Avanzado: 250 metros de radio.;;;;;\n"))
        assertThat(spellMap[descriptor.arcaneGradeMarker], equalTo("Arcano: 500 metros de radio / El hechicero percibe la presencia de cualquier ser con Gnosis superior a 20 o Natura superior a 10 que esté dentro de la zona de acción del conjuro si estos no superan una RM contra 160.;;;;;\n"))
        assertThat(spellMap[descriptor.retentionMarker], equalTo("Mantenimiento: 5 / 10 / 10 / 15 /Diario;;;;;\n"))
        assertThat(spellMap[descriptor.fullTypeMarker], equalTo("Tipo de Conjuro: Efecto, Detección;;;;;"))
    }

    @Test
    fun should_create_secondary_path_spell_when_ask_spell_creation_with_spell_map_in_spanish() {
        // Given
        val spellFirstLines = listOf(
                "Sentir el Caos;;;;;\n",
                "Nivel: 4 /Acción: Activa;;;;;\n",
                "Efecto: El hechicero es capaz de notar las fluctuaciones provocadas por el caos en el ambiente, permitiéndole sentir cualquier cosa innatural para la realidad que ocurra dentro del área del conjuro. No determina la localización exacta, pero si es consciente del nivel de influencia que el caos provoca en la existencia.;;;;;\n",
                "Grado Base Intermedio Avanzado Arcano;;;;;\n",
                "Zeon;50;80;100;120;\n",
                "Int. R.;5;8;10;12;\n",
                "Base: 50 metros de radio.;;;;;\n",
                "Intermedio: 100 metros de radio.;;;;;\n",
                "Avanzado: 250 metros de radio.;;;;;\n",
                "Arcano: 500 metros de radio / El hechicero percibe la presencia de cualquier ser con Gnosis superior a 20 o Natura superior a 10 que esté dentro de la zona de acción del conjuro si estos no superan una RM contra 160.;;;;;\n",
                "Mantenimiento: 5 / 10 / 10 / 15 /Diario;;;;;\n",
                "Tipo de Conjuro: Efecto, Detección;;;;;"
        )

        val spellFileParser = SpellFileParser(descriptor, true)
        val spellMap = spellFileParser.spellMapFromSpellLines(spellFirstLines)

        // When
        val spell = spellFileParser.createSpell(spellMap)

        // Then
        assertThat(spell.name, equalTo("Sentir el Caos"))
        assertThat(spell.level, equalTo(4))
        assertThat(spell.actionType, equalTo("Activa"))
        assertThat(spell.effect, equalTo("El hechicero es capaz de notar las fluctuaciones provocadas por el caos en el ambiente, permitiéndole sentir cualquier cosa innatural para la realidad que ocurra dentro del área del conjuro. No determina la localización exacta, pero si es consciente del nivel de influencia que el caos provoca en la existencia."))
        assertThat(spell.withRetention, equalTo(true))
        assertThat(spell.dailyRetention, equalTo(true))
        assertThat(spell.type, equalTo("Efecto, Detección"))

        assertThat(spell.initialGrade.effect, equalTo("50 metros de radio."))
        assertThat(spell.initialGrade.zeon, equalTo(50))
        assertThat(spell.initialGrade.requiredIntelligence, equalTo(5))
        assertThat(spell.initialGrade.retention, equalTo(5))

        assertThat(spell.intermediateGrade.effect, equalTo("100 metros de radio."))
        assertThat(spell.intermediateGrade.zeon, equalTo(80))
        assertThat(spell.intermediateGrade.requiredIntelligence, equalTo(8))
        assertThat(spell.intermediateGrade.retention, equalTo(10))

        assertThat(spell.advancedGrade.effect, equalTo("250 metros de radio."))
        assertThat(spell.advancedGrade.zeon, equalTo(100))
        assertThat(spell.advancedGrade.requiredIntelligence, equalTo(10))
        assertThat(spell.advancedGrade.retention, equalTo(10))

        assertThat(spell.arcaneGrade.effect, equalTo("500 metros de radio / El hechicero percibe la presencia de cualquier ser con Gnosis superior a 20 o Natura superior a 10 que esté dentro de la zona de acción del conjuro si estos no superan una RM contra 160."))
        assertThat(spell.arcaneGrade.zeon, equalTo(120))
        assertThat(spell.arcaneGrade.requiredIntelligence, equalTo(12))
        assertThat(spell.arcaneGrade.retention, equalTo(15))
    }

    @Test
    fun should_create_primary_path_spell_when_ask_spell_creation_with_spell_map_in_spanish() {
        // Given
        val spellFirstLines = listOf(
                "Crear Luz;;;;;;;;;;;;;\n",
                "Nivel: 2 /Acción: Activa /Tipo: Efecto;;;;;;;;;;;;;\n",
                "Efecto: Crea una fuente de luz en un punto u objeto determinado por el;;;;;;;;;;;;;\n",
                "hechicero.;;;;;;;;;;;;;\n",
                "Grado;Base;Intermedio;Avanzado;Arcano;;;;;;;;;\n",
                "Zeon;20;50;100;200;;;;;;;;;\n",
                "Int. R.;5;8;10;12;;;;;;;;;\n",
                "Base: 5 metros de zona iluminada.;;;;;;;;;;;;;\n",
                "Intermedio: 25 metros de zona iluminada.;;;;;;;;;;;;;\n",
                "Avanzado: 100 metros de zona iluminada.;;;;;;;;;;;;;\n",
                "Arcano: 500 metros de zona iluminada.;;;;;;;;;;;;;\n",
                "Mantenimiento: 5 / 5 / 10 / 15 /Diario;;;;;;;;;;;;;"
        )

        val spellFileParser = SpellFileParser(descriptor, true)
        val spellMap = spellFileParser.spellMapFromSpellLines(spellFirstLines)

        // When
        val spell = spellFileParser.createSpell(spellMap)

        // Then
        assertThat(spell.name, equalTo("Crear Luz"))
        assertThat(spell.level, equalTo(2))
        assertThat(spell.actionType, equalTo("Activa"))
        assertThat(spell.effect, equalTo("Crea una fuente de luz en un punto u objeto determinado por el hechicero."))
        assertThat(spell.withRetention, equalTo(true))
        assertThat(spell.dailyRetention, equalTo(true))
        assertThat(spell.type, equalTo("Efecto"))

        assertThat(spell.initialGrade.effect, equalTo("5 metros de zona iluminada."))
        assertThat(spell.initialGrade.zeon, equalTo(20))
        assertThat(spell.initialGrade.requiredIntelligence, equalTo(5))
        assertThat(spell.initialGrade.retention, equalTo(5))

        assertThat(spell.intermediateGrade.effect, equalTo("25 metros de zona iluminada."))
        assertThat(spell.intermediateGrade.zeon, equalTo(50))
        assertThat(spell.intermediateGrade.requiredIntelligence, equalTo(8))
        assertThat(spell.intermediateGrade.retention, equalTo(5))

        assertThat(spell.advancedGrade.effect, equalTo("100 metros de zona iluminada."))
        assertThat(spell.advancedGrade.zeon, equalTo(100))
        assertThat(spell.advancedGrade.requiredIntelligence, equalTo(10))
        assertThat(spell.advancedGrade.retention, equalTo(10))

        assertThat(spell.arcaneGrade.effect, equalTo("500 metros de zona iluminada."))
        assertThat(spell.arcaneGrade.zeon, equalTo(200))
        assertThat(spell.arcaneGrade.requiredIntelligence, equalTo(12))
        assertThat(spell.arcaneGrade.retention, equalTo(15))
    }

    @Test
    fun should_create_spell_with_special_case_when_ask_spell_creation_with_spell_map_special_in_spanish() {
        // Given
        val spellFirstLines = listOf(
                "Sentir el Caos;;;;;\n",
                "Nivel: 4 /Acción: Activa;;;;;\n",
                "Efecto: El hechicero es capaz de notar las fluctuaciones provocadas por el caos en el ambiente, permitiéndole sentir cualquier cosa innatural para la realidad que ocurra dentro del área del conjuro. No determina la localización exacta, pero si es consciente del nivel de influencia que el caos provoca en la existencia.;;;;;\n",
                "Grado Base Intermedio Avanzado Arcano;;;;;\n",
                "Zeon;500;800;1.000;1.200;\n",
                "Int. R.;5;8;10;12;\n",
                "Base: 50 metros de radio.;;;;;\n",
                "Intermedio: 100 metros de radio.;;;;;\n",
                "Avanzado: 250 metros de radio.;;;;;\n",
                "Arcano: 500 metros de radio / El hechicero percibe la presencia de cualquier ser con Gnosis superior a 20 o Natura superior a 10 que esté dentro de la zona de acción del conjuro si estos no superan una RM contra 160.;;;;;\n",
                "Mantenimiento: No;;;;;\n",
                "Tipo de Conjuro: Efecto, Detección;;;;;"
        )

        val spellFileParser = SpellFileParser(descriptor, true)
        val spellMap = spellFileParser.spellMapFromSpellLines(spellFirstLines)

        // When
        val spell = spellFileParser.createSpell(spellMap)

        // Then
        assertThat(spell.withRetention, equalTo(false))
        assertThat(spell.dailyRetention, equalTo(false))

        assertThat(spell.initialGrade.zeon, equalTo(500))
        assertThat(spell.intermediateGrade.zeon, equalTo(800))
        assertThat(spell.advancedGrade.zeon, equalTo(1000))
        assertThat(spell.arcaneGrade.zeon, equalTo(1200))
    }

    @Test
    fun should_create_spell_with_all_effects_when_ask_spell_creation_with_multi_line_effect_spell_map_in_spanish() {
        // Given
        val spellFirstLines = listOf(
                "Hasta más Allá del Fin;;;;;\n",
                "Nivel: 94 /Acción: Activa;;;;;\n",
                "Efecto: Este hechizo lleva más allá del poder de los mortales el espíritu combativo de los aliados del brujo:;;;;;\n",
                "por combatir en dicho estado.;;;;;\n",
                "Grado;Base Intermedio Avanzado Arcano;;;;\n",
                "Zeon;250;500;750;1000;\n",
                "Int. R.;14;16;18;20;\n",
                "Base: 25 metros de radio.;;;;;\n",
                "Intermedio: 50 metros de radio.;;;;;\n",
                "Avanzado: 250 metros de radio.;;;;;\n",
                "Arcano: 500 metros de radio / Además de los efectos descritos,;;;;;\n",
                "un personaje afectado por este hechizo puede seguir luchando en plenas facultados hasta dos asaltos después de haber muerto.;;;;;\n",
                "Mantenimiento: 25 / 50 / 75 / 100;;;;;\n",
                "Tipo de Conjuro: Efecto;;;;;"
        )

        val spellFileParser = SpellFileParser(descriptor, true)
        val spellMap = spellFileParser.spellMapFromSpellLines(spellFirstLines)

        // When
        val spell = spellFileParser.createSpell(spellMap)

        // Then
        assertThat(spell.effect, equalTo("Este hechizo lleva más allá del poder de los mortales el espíritu combativo de los aliados del brujo: por combatir en dicho estado."))
        assertThat(spell.arcaneGrade.effect, equalTo("500 metros de radio / Además de los efectos descritos, un personaje afectado por este hechizo puede seguir luchando en plenas facultados hasta dos asaltos después de haber muerto."))
    }

    @Test
    fun should_parse_full_secondary_path_file_without_exception() {
        // Given
        val spellsLines = InputStreamReader(InstrumentationRegistry.getContext().assets.open("arcana_exxet_spells_spanish.csv"))
                .readLines()

        val spellFileParser = SpellFileParser(descriptor, true)

        // When
        val mapSpellbookSpells: Map<Spellbook, List<Spell>> = spellFileParser.parseFileLines(spellsLines)

        // Then
        assertThat(mapSpellbookSpells.keys, hasSize(14))
        mapSpellbookSpells.values.forEach {
            assertThat(it, hasSize(10))
        }
    }

    @Test
    fun should_parse_full_primary_path_file_without_exception() {
        // Given
        val spellsLines = InputStreamReader(InstrumentationRegistry.getContext().assets.open("core_exxet_spells_spanish.csv"))
                .readLines()

        val spellFileParser = SpellFileParser(descriptor, true)

        // When
        val mapSpellbookSpells: Map<Spellbook, List<Spell>> = spellFileParser.parseFileLines(spellsLines)

        // Then
        assertThat(mapSpellbookSpells.keys, hasSize(12))
    }
}