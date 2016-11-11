package com.example.a00885912.myapplication;

/**
 * This class represent a weapon object, a child of card.
 */
public class Weapon extends Card {
    //The durability of the weapon
    private int durability;
    //The damage attribute of the weapon
    private int damage;

    /**
     * This creates a weapon object
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
     * @param durability The durability of the weapon
     * @param damage The damage of the weapon
     */
    protected Weapon(String cardName, int cardImgSrc, Rarity rarity, int manaCost, Set set,
                     boolean collectable,  Class classes, String description, Trait trait, String flavorText,
                     int durability, int damage) {
        super(cardName, cardImgSrc, description, flavorText, manaCost, collectable, rarity, classes, trait, set);
        this.damage = damage;
        this.durability = durability;
    }

    /**
     * Gets the durability of the weapon
     * @return Durability of the weapon
     */
    public int getDur() {
        return durability;
    }

    /**
     * Gets the damage of the weapon
     * @return Damage of the weapon
     */
    public int getDamage() {
        return damage;
    }

}
