package fr.enlight.anima.cardmodel.model.spells;


public enum SpellActionType {
    ACTIVE("Active"),
    PASSIVE("Passive");

    public final String name;

    SpellActionType(String name) {
        this.name = name;
    }
}
