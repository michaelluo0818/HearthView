package com.example.a00885912.myapplication;

/**
 * This enum contains all possible rarity for a card and its equivalent dust cost.
 */
public enum Rarity {
    BASIC(0),COMMON(40),RARE(100),EPIC(400),LEGENDARY(1600);
    private final int dust;
    Rarity(int dust) {
        this.dust = dust;
    }

    //returns dust cost
    int getDust() {
        return dust;
    }
}
