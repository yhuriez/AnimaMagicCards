package fr.enlight.anima.cardmodel.model.spells;

import java.util.List;

/**
 *
 */
public class Spellbook {

    public int bookId;
    public String bookName;
    public String description;
    public String type;
    public String oppositeBook;
    public List<String> secondaryBookAccessibles;
    public List<String> primaryBookUnaccessibles;
}
