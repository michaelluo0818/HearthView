package com.example.a00885912.myapplication;

/**
 * This class represents the basic data structure that will be included in all card objects.
 */
public abstract class Card {

    //The name, description and flavor text of the card.
    private String cardName,description,flavorText;

    //The mana crystal cost and image source from Drawable folder of the card.

    private int manaCost,cardImgSrc;

    //If the card is collectable by the player.

    private boolean collectable;

    //The rarity of the card.
    private Rarity rarity;

    //The class the card belongs to.
    private Class classes;

    //The trait the card has.
    private Trait trait;

    //Which expansion set the card belongs to.
    private Set set;

    /**
     * Constructs a card object
     * @param cardName Name of the card.
     * @param cardImgSrc Drawable source.
     * @param description Description of the card.
     * @param flavorText Flavor Text of the card.
     * @param manaCost Mana crystal cost of the card.
     * @param collectable If the card is collectible by the player.
     * @param rarity The rarity of the card.
     * @param classes The class of the card.
     * @param trait The trait the card has.
     * @param set The expansion set the card belongs to.
     */
    protected Card(String cardName, int cardImgSrc, String description,
                String flavorText, int manaCost, boolean collectable,
                   Rarity rarity, Class classes, Trait trait, Set set) {
        this.cardName = cardName;
        this.cardImgSrc = cardImgSrc;
        this.description = description;
        this.flavorText = flavorText;
        this.manaCost = manaCost;
        this.collectable = collectable;
        this.rarity = rarity;
        this.classes = classes;
        this.set = set;
        this.trait = trait;
    }


    /**
     * Gets the name of the card.
     * @return The name of the card.
     */
    public String getName() {
        return cardName;
    }

    /**
     * Gets the Drawable folder image source of the card.
     * @return The image source of the card.
     */
    public int getImgSrc() {
        return cardImgSrc;
    }

    /**
     * Gets the description of the card.
     * @return The description of the card.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the flavor text of the card.
     * @return The flavor text of the card.
     */
    public String getFlavorText() {
        return flavorText;
    }

    /**
     * Gets the mana crystal cost of the card.
     * @return the mana crystal cost of the card.
     */
    public int getManaCost() {
        return manaCost;
    }

    /**
     * Gets if the card is player collectible.
     * @return If player collectible
     */
    public boolean getCollectable() {
        return collectable;
    }

    /**
     * Get the rarity of the card
     * @return The rarity of the card.
     */
    public String getRarity() {
        switch(rarity) {
            case BASIC : return "BASIC";
            case COMMON : return "COMMON";
            case RARE : return "RARE";
            case EPIC : return "EPIC";
            case LEGENDARY : return "LEGENDARY";
        }
        return "ERROR";
    }

    /**
     * Gets the class of the card.
     * @return The class the card belongs to.
     */
    public String getClasses() {
        switch(classes) {
            case MAGE:  return "Mage";
            case HUNTER:  return "Hunter";
            case DRUID:  return "Druid";
            case SHAMAN:  return "Shaman";
            case WARLOCK:  return "Warlock";
            case WARRIOR: return "Warrior";
            case PRIEST: return "Priest";
            case PALADIN: return "Paladin";
            case ROGUE: return "Rogue";
            case NEUTRAL: return "ERROR, spell cannot be neutral";
        }
        return "ERROR";
    }

    /**
     * Gets the trait of the card.
     * @return The trait that a card has
     */
    public String getTrait() {
        switch(trait) {
            case BATTLECRY:  return "Mage";
            case DEATHRATTLE:  return "Hunter";
            case DSHIELD:  return "Druid";
            case SECRET:  return "Shaman";
            case INSPIRE:  return "Warlock";
            case AURA: return "Warrior";
            case CHARGE: return "Priest";
            case TAUNT: return "Paladin";
            case ENRAGE: return "Warrior";
            case NONE: return "";
        }
        return "";
    }

    /**
     * Gets which expansion set the card belongs to.
     * @return The expansion set of the card.
     */
    public String getSet() {
        switch(set) {
            case VAN:  return "Vanilla";
            case NAX:  return "Naxxramas";
            case GVG:  return "Goblins VS Gnomes";
            case TGT:  return "The Grand Tournament";
            case LOE:  return "League of Explorers";
            case BRM: return "Black Rock Mountain";
        }
        return "ERROR";
    }

    /**
     * Gets the enchanted dust cost of the card.
     * @return The dust cost of the card.
     */
    public int getDust() {
        return rarity.getDust();
    }

}
