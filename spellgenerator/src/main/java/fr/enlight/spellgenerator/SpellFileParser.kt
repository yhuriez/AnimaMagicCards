package fr.enlight.spellgenerator

import fr.enlight.spellgenerator.spells.Spell
import fr.enlight.spellgenerator.spells.SpellGrade
import fr.enlight.spellgenerator.spells.Spellbook

/**
 *
 */
class SpellFileParser(private val descriptor: SpellFileDescriptor, private val withSpellbookDescription: Boolean = false) {


    fun parseFileLines(fileLines: List<String>): Map<Spellbook, List<Spell>> {
        val spellbooksLines: List<List<String>> = getSpellbooksLines(fileLines)

        return spellbooksLines.associateBy(
                { createSpellbook(it) },
                {
                    getSpellsLines(it).map {
                        spellMapFromSpellLines(it).toSpell()
                    }
                }
        )
    }

    // Spellbooks

    fun getSpellbooksLines(fileLines: List<String>) = getSubLines(fileLines, descriptor.spellbookMarker, 0)

    fun getSpellsLines(fileLines: List<String>) = getSubLines(fileLines, descriptor.levelMarker, 1).drop(1)

    private fun getSubLines(fileLines: List<String>, descriptor: String, startDelta: Int): List<List<String>> {
        return fileLines.mapIndexed { index, line -> index to line }
                .filter { it.second.startsWith(descriptor, ignoreCase = true) }
                .fold(ArrayList<Pair<Int, Int>>()) { acc, (lineIndex, _) ->
                    val lastAccIndex = if (acc.isEmpty()) 0 else acc.last().second
                    acc.add(lastAccIndex to lineIndex - startDelta)
                    acc
                }
                .apply { add(last().second to fileLines.size) }
                .map { fileLines.subList(it.first, it.second) }
    }


    fun createSpellbook(lines: List<String>): Spellbook {
        val spellbook = Spellbook()
        spellbook.bookName = lines[0].split(":",";")[1]

        if(withSpellbookDescription) {
            if (lines.size < 2) throw IllegalStateException()

            spellbook.description = lines[1].split(";")[0]
            spellbook.primaryBookUnaccessibles = lines[2].split(":", ",", ";")
                    .map { it.removeSuffix(".").trim() }
                    .filterNot { it.isEmpty() }
                    .filterNot { it.contains(descriptor.closedMarker) || it.contains(descriptor.closedPathMarker) }
        }
        return spellbook
    }

    // Spells

    /**
     * Prepare a Map that will make the conversion to Spell object simplier
     */
    fun spellMapFromSpellLines(lines: List<String>): Map<String, String> {
        return lines
                // Map line with marker
                .mapIndexed { index, line ->
                    if (index == 0) descriptor.titleMarker to "${descriptor.titleMarker}:$line" // Add title entry
                    else line.split(";", ":")[0] to line  // Add other entries
                }
                // Merge multi-lines elements (for effects)
                .fold(ArrayList<Pair<String, String>>(), { acc, pair ->
                    if (descriptor.spellsMarkers.none { pair.first.startsWith(it, true) }) {
                        val last = acc.last()
                        acc.remove(last)
                        acc.add(last.first to "${last.second} ${pair.first}")
                    } else {
                        acc.add(pair)
                    }
                    acc
                })
                .associateBy({ it.first }, { it.second })
    }

    /**
     * Convert prepared Map to Spell
     */
    fun createSpell(spellMap: Map<String, String>): Spell {
        val result = Spell()
        result.initialGrade = SpellGrade()
        result.intermediateGrade = SpellGrade()
        result.advancedGrade = SpellGrade()
        result.arcaneGrade = SpellGrade()

        try {
            // Title
            result.name = spellMap[descriptor.titleMarker]?.toSingleItem()
                    ?: throw IllegalStateException("Should have a title at this point")


            // Level and Action Type
            val levelAndActionType = spellMap[descriptor.levelMarker]
                    ?.toSplitItems(":", ";", "/")
                    ?.filterNot { it.contains(descriptor.actionMarker) }
                    ?.filterNot { it.contains(descriptor.typeMarker) } ?: emptyList()

            if (levelAndActionType.size < 2)
                throw IllegalStateException("Should have at least two element (level and action) at this point")

            val level = levelAndActionType[0]
            if(level.contains("-")){
                result.level = level.split("-")[1].toInt()
            } else {
                result.level = level.toInt()
            }

            result.actionType = levelAndActionType[1]

            if(levelAndActionType.size > 2){
                result.type = levelAndActionType[2]
            }

            // Effect
            result.effect = spellMap[descriptor.effectMarker]?.toSingleItem()
                    ?: throw IllegalStateException("Should have an effect at this point")


            // Type
            if(result.type == null) {
                result.type = spellMap[descriptor.fullTypeMarker]?.toSingleItem()
                        ?: throw IllegalStateException("Should have a type at this point")
            }

            // Grade effects
            result.initialGrade.effect = spellMap[descriptor.baseGradeMarker]?.toSingleItem()
                    ?: throw IllegalStateException("Should have a base effect at this point")
            result.intermediateGrade.effect = spellMap[descriptor.intermediateGradeMarker]?.toSingleItem()
                    ?: throw IllegalStateException("Should have a intermediate effect at this point")
            result.advancedGrade.effect = spellMap[descriptor.advancedGradeMarker]?.toSingleItem()
                    ?: throw IllegalStateException("Should have a advanced effect at this point")
            result.arcaneGrade.effect = spellMap[descriptor.arcaneGradeMarker]?.toSingleItem()
                    ?: throw IllegalStateException("Should have a arcane effect at this point")


            // Grade Zeon
            val zeonArray = spellMap[descriptor.zeonMarker]?.toSplitItems(";")
                    ?: throw IllegalStateException("Should have a list of zeon grades at this point")
            if (zeonArray.size != 4)
                throw IllegalStateException("Should have four zeon grades at this point, but have ${zeonArray.size}")
            result.initialGrade.zeon = zeonArray[0].replace(".", "").toInt()
            result.intermediateGrade.zeon = zeonArray[1].replace(".", "").toInt()
            result.advancedGrade.zeon = zeonArray[2].replace(".", "").toInt()
            result.arcaneGrade.zeon = zeonArray[3].replace(".", "").toInt()


            // Grade intelligence requirement
            val requiredIntelligenceArray = spellMap[descriptor.intelligenceMarker]?.toSplitItems(";")
                    ?: throw IllegalStateException("Should have a list of intelligence requirement grades at this point")
            if (requiredIntelligenceArray.size != 4)
                throw IllegalStateException("Should have four intelligence requirement grades at this point, but have ${requiredIntelligenceArray.size}")
            result.initialGrade.requiredIntelligence = requiredIntelligenceArray[0].toInt()
            result.intermediateGrade.requiredIntelligence = requiredIntelligenceArray[1].toInt()
            result.advancedGrade.requiredIntelligence = requiredIntelligenceArray[2].toInt()
            result.arcaneGrade.requiredIntelligence = requiredIntelligenceArray[3].toInt()


            // Retention
            val retentionArray = spellMap[descriptor.retentionMarker]?.toSplitItems(":", ";", "/")
                    ?: throw IllegalStateException("Should have a retention indication at this point")

            result.withRetention = !retentionArray[0].contains(descriptor.noMarker)
            result.dailyRetention = retentionArray.any { it.contains(descriptor.dailyRetentionMarker) }


            if (retentionArray.size >= 4) {
                result.initialGrade.retention = retentionArray[0].toInt()
                result.intermediateGrade.retention = retentionArray[1].toInt()
                result.advancedGrade.retention = retentionArray[2].toInt()
                result.arcaneGrade.retention = retentionArray[3].toInt()
            }

        } catch (e: Exception) {
            throw SpellParsingException(spellMap, e)
        }

        return result
    }

    private fun Map<String, String>.toSpell(): Spell = createSpell(this)

    private fun String.toSingleItem(): String =
            split(";") // Merge all items
                    .map { it.trim() } // Remove black spaces
                    .filterNot { it.isEmpty() } // Remove empty items
                    .reduce { acc, item -> "$acc $item" } // Regroup items into one String
                    .split(":") // Separate text from marker
                    .drop(1) // Remove marker
                    .fold("", { acc, item -> if(acc.isEmpty()) item else "$acc:$item" }) // Put back missing ':' for formatting
                    .trim() // Remove black spaces

    private fun String.toSplitItems(vararg separators: String) =
            split(*separators)
                    .drop(1)
                    .map { it.trim() }
                    .filterNot { it.isEmpty() }
}