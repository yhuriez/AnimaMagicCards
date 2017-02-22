package fr.enlight.anima.cardmodel.model;

/**
 *
 */
public class Spell {

    public int spellId;
    public int bookId;
    public String name;
    public int level;
    public String actionType;
    public String type;
    public String effect;
    public SpellGrade initialGrade;
    public SpellGrade intermediateGrade;
    public SpellGrade advancedGrade;
    public SpellGrade arcaneGrade;
    public boolean withRetention;
    public boolean dailyRetention;

}
