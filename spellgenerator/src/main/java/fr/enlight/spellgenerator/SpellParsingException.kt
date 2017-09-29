package fr.enlight.spellgenerator

/**
 *
 */
class SpellParsingException(spellMap: Map<String, String>, exception: Exception): Exception("Exception occurs for spell map $spellMap", exception)