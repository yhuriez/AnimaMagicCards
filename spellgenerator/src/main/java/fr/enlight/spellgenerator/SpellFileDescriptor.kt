package fr.enlight.spellgenerator

/**
 *
 */
sealed class SpellFileDescriptor(
        val spellbookMarker: String,
        val closedPathMarker: String,
        val closedMarker: String,
        val levelMarker: String,
        val titleMarker: String,
        val actionMarker: String,
        val effectMarker: String,
        val zeonMarker: String,
        val intelligenceMarker: String,
        val baseGradeMarker: String,
        val intermediateGradeMarker: String,
        val advancedGradeMarker: String,
        val arcaneGradeMarker: String,
        val retentionMarker: String,
        val dailyRetentionMarker: String,
        val typeMarker: String,
        val noMarker: String,
        val gradeMarker: String,
        val spellsMarkers: List<String> = listOf(
                titleMarker,
                levelMarker,
                effectMarker,
                zeonMarker,
                intelligenceMarker,
                baseGradeMarker,
                intermediateGradeMarker,
                advancedGradeMarker,
                arcaneGradeMarker,
                retentionMarker,
                typeMarker,
                gradeMarker
        )
)

class SpanishSpellFileDescriptor: SpellFileDescriptor(
        spellbookMarker = "Spellbook",
        closedPathMarker = "Vínculos",
        closedMarker = "Cerrados",
        levelMarker = "Nivel",
        titleMarker = "Title",
        actionMarker = "Acción",
        effectMarker = "Efecto",
        zeonMarker = "Zeon",
        intelligenceMarker = "Int. R.",
        baseGradeMarker = "Base",
        intermediateGradeMarker = "Intermedio",
        advancedGradeMarker = "Avanzado",
        arcaneGradeMarker = "Arcano",
        retentionMarker = "Mantenimiento",
        dailyRetentionMarker = "Diario",
        typeMarker = "Tipo de Conjuro",
        noMarker = "No",
        gradeMarker = "Grado"
)