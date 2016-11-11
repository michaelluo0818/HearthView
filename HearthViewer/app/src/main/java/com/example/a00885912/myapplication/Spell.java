package com.example.a00885912.myapplication;

/**
 * This class represents a Spell, a child of card class.
 */
public class Spell extends Card {

    /**
     * Creates a spell object
     * @param cardName The name of the card.
     * @param cardImgSrc The image source of the card from drawable
     * @param rarity The rarity of the card
     * @param manaCost The mana cost of the card
     * @param set The expansion set the card belongs to
     * @param collectable If the card is player collectible
     * @param classes The class the card belongs to
     * @param description The description of the card
     * @param trait The trait of the card
     * @param flavorText The flavor text of the card.
     */
    protected Spell(String cardName, int cardImgSrc, Rarity rarity, int manaCost, Set set,
                    boolean collectable,  Class classes, String description, Trait trait, String flavorText) {
        super(cardName, cardImgSrc, description, flavorText, manaCost, collectable, rarity, classes, trait, set);
    }
}
