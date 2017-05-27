package fr.enlight.anima.cardmodel.model.spells;


public enum SpellType {
    ATTACK("Attaque"),
    DEFENSE("Défense"),
    EFFECT("Effet"),
    ANIMISTIC("Animique"),
    AUTOMATIC("Automatique"),
    DETECTION("Détection");

    public final String name;

    SpellType(String name) {
        this.name = name;
    }
}
