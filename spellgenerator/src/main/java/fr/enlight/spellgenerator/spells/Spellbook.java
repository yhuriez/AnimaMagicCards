package fr.enlight.spellgenerator.spells;

import java.util.List;

/**
 *
 */
public class Spellbook {

    private static final String MAJOR_PATH_TYPE = "Majeure";

    public int bookId;
    public String bookName;
    public String description;
    public String type;
    public String oppositeBook;
    public List<String> secondaryBookAccessibles;
    public List<String> primaryBookUnaccessibles;
    public List<Spell> spells;
}
