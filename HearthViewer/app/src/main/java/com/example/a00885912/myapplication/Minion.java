package com.example.a00885912.myapplication;

/**
 * This class represents a minion, a child of card.
 */
public class Minion extends Card {
    //The attack attribute of the minion
    private int attack;
    //The health attribute of the minion
    private int health;
    //The race of the minion
    private Race race;

    /**
     * Creates a minion object
     * @param cardName The name of the card
     * @param cardImgSrc The image source of the card from drawable folder
     * @param rarity The rarity of the card
     * @param manaCost The mana crystal cost of the card
     * @param set The expansion set the card belongs to
     * @param collectable If the card is player collectible
     * @param classes The class of the card
     * @param description The description of the card
     * @param trait The trait of the card
     * @param flavorText The flavor text of the card
     * @param attack The attack attribute of the minion
     * @param health The health attribute of the minion
     * @param race The race of the minion
     */
    protected Minion(String cardName, int cardImgSrc, Rarity rarity, int manaCost, Set set,
                     boolean collectable,  Class classes, String description, Trait trait, String flavorText,
                     int attack, int health, Race race) {
        super(cardName, cardImgSrc, description, flavorText, manaCost, collectable, rarity, classes, trait, set);
        this.attack = attack;
        this.health = health;
        this.race = race;
    }

    /**
     * Gets the attack of the minion
     * @return The attack of the minion
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Gets the health of the minion
     * @return The health of the minion
     */
    public int getHealth() {
        return health;
    }

    /**
     * Gets the race of the card.
     * @return The race of the card.
     */
    public String getRace() {
        switch(race) {
            case MECH: return "Mech";
            case MURLOC: return "Murloc";
            case TOTEM: return "Totem";
            case BEAST: return "Beast";
            case DEMON: return "Demon";
            case DRAGON: return "Dragon";
            case PIRATE: return "Pirate";
        }
        return "";
    }

}
