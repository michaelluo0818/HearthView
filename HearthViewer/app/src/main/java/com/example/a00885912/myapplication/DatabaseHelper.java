package com.example.a00885912.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class provides sorted card data from the database for other classes to use.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    //Database name
    private static String DB_NAME = "Cards";
    //card info
    private String cardName,desc,flavorText;
    private int manaC,cardImgSrc, attack, health, damage, durability;
    private boolean collectable;
    private Rarity rarity;
    private Class classes;
    private Trait trait;
    private Set set;
    private Race race;

    /**
     * Creates a database with Tables Minion, Spell and Weapon
     * @param db The database to be created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("DROP TABLE IF EXISTS Minion");
            db.execSQL("DROP TABLE IF EXISTS Spell");
            db.execSQL("DROP TABLE IF EXISTS Weapon");

            //minion
            db.execSQL("CREATE TABLE Minion (\n" +
                    "cardName    TEXT    NOT NULL,\n" +
                    "cardImgSrc  INT     NOT NULL,\n" +
                    "rarity      TEXT    NOT NULL,\n" +
                    "manaCost    INT     NOT NULL,\n" +
                    "sets        TEXT    NOT NULL,\n" +
                    "collectible BOOLEAN NOT NULL,\n" +
                    "class       TEXT    NOT NULL,\n" +
                    "description TEXT,\n" +
                    "trait       TEXT    NOT NULL,\n" +
                    "flavorText  TEXT    NOT NULL,\n" +
                    "attack      INT     NOT NULL,\n" +
                    "health      INT     NOT NULL,\n" +
                    "race        TEXT    NOT NULL\n" +
                    ");");

            //weapon
            db.execSQL("CREATE TABLE Weapon (\n" +
                    "cardName    TEXT    NOT NULL,\n" +
                    "cardImgSrc  INT     NOT NULL,\n" +
                    "rarity      TEXT    NOT NULL,\n" +
                    "manaCost    INT     NOT NULL,\n" +
                    "sets        TEXT    NOT NULL,\n" +
                    "collectible BOOLEAN NOT NULL,\n" +
                    "class       TEXT    NOT NULL,\n" +
                    "description TEXT,\n" +
                    "trait       TEXT    NOT NULL,\n" +
                    "flavorText  TEXT    NOT NULL,\n" +
                    "durability  INT     NOT NULL,\n" +
                    "damage      INT     NOT NULL\n" +
                    ");");

            //spell
            db.execSQL("CREATE TABLE Spell (\n" +
                    "cardName    TEXT    NOT NULL,\n" +
                    "cardImgSrc  INT     NOT NULL,\n" +
                    "rarity      TEXT    NOT NULL,\n" +
                    "manaCost    INT     NOT NULL,\n" +
                    "sets        TEXT    NOT NULL,\n" +
                    "collectible BOOLEAN NOT NULL,\n" +
                    "class       TEXT    NOT NULL,\n" +
                    "description TEXT    NOT NULL,\n" +
                    "trait       TEXT    NOT NULL,\n" +
                    "flavorText  TEXT    NOT NULL\n" +
                    ");");

            //add cards to db
            insert(db);
        } catch (Exception e) {
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public DatabaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);
    }

    /**
     * Sort the entire db and return all cards
     * @param db The db to grab information from
     * @return Every card in the database as an arraylist
     */
    public ArrayList<Card> getAllCards(SQLiteDatabase db) {
        ArrayList<Card> cards = new ArrayList<>();
        String minions = "SELECT * FROM Minion";
        String spells = "SELECT * FROM Spell";
        String weapons = "SELECT * FROM Weapon";

        Cursor cursor;

        //minion
        cursor = db.rawQuery(minions,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cardName = cursor.getString(0);
            cardImgSrc = cursor.getInt(1);
            rarity = Rarity.valueOf(cursor.getString(2));
            manaC = cursor.getInt(3);
            set = Set.valueOf(cursor.getString(4));
            collectable = cursor.getInt(5)>0;
            classes = Class.valueOf(cursor.getString(6));
            desc = cursor.getString(7);
            trait = Trait.valueOf(cursor.getString(8));
            flavorText = cursor.getString(9);
            attack = cursor.getInt(10);
            health = cursor.getInt(11);
            race = Race.valueOf(cursor.getString(12));
            Minion minion = new Minion(cardName,cardImgSrc,rarity,manaC,set,collectable,classes,desc,
                    trait,flavorText,attack,health,race);
            cards.add(minion);
            cursor.moveToNext();
        }

        //weapon
        cursor = db.rawQuery(weapons,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cardName = cursor.getString(0);
            cardImgSrc = cursor.getInt(1);
            rarity = Rarity.valueOf(cursor.getString(2));
            manaC = cursor.getInt(3);
            set = Set.valueOf(cursor.getString(4));
            collectable = cursor.getInt(5)>0;
            classes = Class.valueOf(cursor.getString(6));
            desc = cursor.getString(7);
            trait = Trait.valueOf(cursor.getString(8));
            flavorText = cursor.getString(9);
            durability = cursor.getInt(10);
            damage = cursor.getInt(11);
            Weapon weapon = new Weapon(cardName,cardImgSrc,rarity,manaC,set,collectable,classes,desc,
                    trait,flavorText,durability,damage);
            cards.add(weapon);
            cursor.moveToNext();
        }

        //spell
        cursor = db.rawQuery(spells,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            cardName = cursor.getString(0);
            cardImgSrc = cursor.getInt(1);
            rarity = Rarity.valueOf(cursor.getString(2));
            manaC = cursor.getInt(3);
            set = Set.valueOf(cursor.getString(4));
            collectable = cursor.getInt(5)>0;
            classes = Class.valueOf(cursor.getString(6));
            desc = cursor.getString(7);
            trait = Trait.valueOf(cursor.getString(8));
            flavorText = cursor.getString(9);
            Spell spell = new Spell(cardName,cardImgSrc,rarity,manaC,set,collectable,classes,desc,
                    trait,flavorText);
            cards.add(spell);
            cursor.moveToNext();
        }
        return cards;
    }

    /**
     * Sorts the database by the race of the minion
     * @param db The database to grab information from
     * @param selectedTribe The tribe to be sorted
     * @return Minions with the specified tribe
     */
    public ArrayList<Card> getTribeCards(SQLiteDatabase db, String selectedTribe) {
        ArrayList<Card> cards = new ArrayList<>();
        String minions = "SELECT * FROM Minion";

        Cursor cursor;
        cursor = db.rawQuery(minions,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if(cursor.getString(12).equals(selectedTribe)) {
                cardName = cursor.getString(0);
                cardImgSrc = cursor.getInt(1);
                rarity = Rarity.valueOf(cursor.getString(2));
                manaC = cursor.getInt(3);
                set = Set.valueOf(cursor.getString(4));
                collectable = cursor.getInt(5)>0;
                classes = Class.valueOf(cursor.getString(6));
                desc = cursor.getString(7);
                trait = Trait.valueOf(cursor.getString(8));
                flavorText = cursor.getString(9);
                attack = cursor.getInt(10);
                health = cursor.getInt(11);
                race = Race.valueOf(cursor.getString(12));
                Minion minion = new Minion(cardName,cardImgSrc,rarity,manaC,set,collectable,classes,desc,
                        trait,flavorText,attack,health,race);
                cards.add(minion);
            }
            cursor.moveToNext();
        }

        return cards;
    }

    /**
     * Sorts the db and return all cards with specified mana cost
     * @param db Db to grab information from
     * @param mana The mana crystal cost to sort with
     * @return All cards with specified mana crystal cost
     */
    public ArrayList<Card> getManaCards(SQLiteDatabase db, int mana) {
        ArrayList<Card> cards = new ArrayList<>();
        String minions = "SELECT * FROM Minion";
        String spells = "SELECT * FROM Spell";
        String weapons = "SELECT * FROM Weapon";

        Cursor cursor;

        //minion
        cursor = db.rawQuery(minions,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if(cursor.getInt(3) == mana) {
                cardName = cursor.getString(0);
                cardImgSrc = cursor.getInt(1);
                rarity = Rarity.valueOf(cursor.getString(2));
                manaC = cursor.getInt(3);
                set = Set.valueOf(cursor.getString(4));
                collectable = cursor.getInt(5)>0;
                classes = Class.valueOf(cursor.getString(6));
                desc = cursor.getString(7);
                trait = Trait.valueOf(cursor.getString(8));
                flavorText = cursor.getString(9);
                attack = cursor.getInt(10);
                health = cursor.getInt(11);
                race = Race.valueOf(cursor.getString(12));
                Minion minion = new Minion(cardName,cardImgSrc,rarity,manaC,set,collectable,classes,desc,
                        trait,flavorText,attack,health,race);
                cards.add(minion);
            }
            cursor.moveToNext();
        }

        //weapon
        cursor = db.rawQuery(weapons,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if(cursor.getInt(3) == mana) {
                cardName = cursor.getString(0);
                cardImgSrc = cursor.getInt(1);
                rarity = Rarity.valueOf(cursor.getString(2));
                manaC = cursor.getInt(3);
                set = Set.valueOf(cursor.getString(4));
                collectable = cursor.getInt(5)>0;
                classes = Class.valueOf(cursor.getString(6));
                desc = cursor.getString(7);
                trait = Trait.valueOf(cursor.getString(8));
                flavorText = cursor.getString(9);
                durability = cursor.getInt(10);
                damage = cursor.getInt(11);
                Weapon weapon = new Weapon(cardName,cardImgSrc,rarity,manaC,set,collectable,classes,desc,
                        trait,flavorText,durability,damage);
                cards.add(weapon);
            }
            cursor.moveToNext();
        }

        //spell
        cursor = db.rawQuery(spells,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if(cursor.getInt(3) == mana) {
                cardName = cursor.getString(0);
                cardImgSrc = cursor.getInt(1);
                rarity = Rarity.valueOf(cursor.getString(2));
                manaC = cursor.getInt(3);
                set = Set.valueOf(cursor.getString(4));
                collectable = cursor.getInt(5)>0;
                classes = Class.valueOf(cursor.getString(6));
                desc = cursor.getString(7);
                trait = Trait.valueOf(cursor.getString(8));
                flavorText = cursor.getString(9);
                Spell spell = new Spell(cardName,cardImgSrc,rarity,manaC,set,collectable,classes,desc,
                        trait,flavorText);
                cards.add(spell);
            }
            cursor.moveToNext();
        }
        return cards;
    }

    /**
     * Sorts db and returns all cards with specified class
     * @param db The DB to grab information from
     * @param selectedClass The class specified
     * @return All cards belonging to the specified class
     */
    public ArrayList<Card> getClassCards(SQLiteDatabase db, String selectedClass) {
        ArrayList<Card> cards = new ArrayList<>();
        String minions = "SELECT * FROM Minion";
        String spells = "SELECT * FROM Spell";
        String weapons = "SELECT * FROM Weapon";

        Cursor cursor;
        cursor = db.rawQuery(minions,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if(cursor.getString(6).equals(selectedClass)) {
                cardName = cursor.getString(0);
                cardImgSrc = cursor.getInt(1);
                rarity = Rarity.valueOf(cursor.getString(2));
                manaC = cursor.getInt(3);
                set = Set.valueOf(cursor.getString(4));
                collectable = cursor.getInt(5)>0;
                classes = Class.valueOf(cursor.getString(6));
                desc = cursor.getString(7);
                trait = Trait.valueOf(cursor.getString(8));
                flavorText = cursor.getString(9);
                attack = cursor.getInt(10);
                health = cursor.getInt(11);
                race = Race.valueOf(cursor.getString(12));
                Minion minion = new Minion(cardName,cardImgSrc,rarity,manaC,set,collectable,classes,desc,
                        trait,flavorText,attack,health,race);
                cards.add(minion);
            }
            cursor.moveToNext();
        }
        cursor = db.rawQuery(weapons,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if(cursor.getString(6).equals(selectedClass)) {
                cardName = cursor.getString(0);
                cardImgSrc = cursor.getInt(1);
                rarity = Rarity.valueOf(cursor.getString(2));
                manaC = cursor.getInt(3);
                set = Set.valueOf(cursor.getString(4));
                collectable = cursor.getInt(5)>0;
                classes = Class.valueOf(cursor.getString(6));
                desc = cursor.getString(7);
                trait = Trait.valueOf(cursor.getString(8));
                flavorText = cursor.getString(9);
                durability = cursor.getInt(10);
                damage = cursor.getInt(11);
                Weapon weapon = new Weapon(cardName,cardImgSrc,rarity,manaC,set,collectable,classes,desc,
                        trait,flavorText,durability,damage);
                cards.add(weapon);
            }
            cursor.moveToNext();
        }
        cursor = db.rawQuery(spells,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            if(cursor.getString(6).equals(selectedClass)) {
                cardName = cursor.getString(0);
                cardImgSrc = cursor.getInt(1);
                rarity = Rarity.valueOf(cursor.getString(2));
                manaC = cursor.getInt(3);
                set = Set.valueOf(cursor.getString(4));
                collectable = cursor.getInt(5)>0;
                classes = Class.valueOf(cursor.getString(6));
                desc = cursor.getString(7);
                trait = Trait.valueOf(cursor.getString(8));
                flavorText = cursor.getString(9);
                Spell spell = new Spell(cardName,cardImgSrc,rarity,manaC,set,collectable,classes,desc,
                        trait,flavorText);
                cards.add(spell);
            }
            cursor.moveToNext();
        }
        return cards;
    }


    /**
     * Populate the database with cards data
     * @param db The databsae to store the data into.
     */
    public void insert(SQLiteDatabase db) {
        //armorsmith
        ContentValues courseValues;
        courseValues = new ContentValues();
        courseValues.put("cardName","Armorsmith");
        courseValues.put("cardImgSrc", R.drawable.armorsmith);
        courseValues.put("rarity","RARE");
        courseValues.put("manaCost", 2);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "WARRIOR");
        courseValues.put("description","Whenever a friendly mminion takes damage, gain 1 Armor");
        courseValues.put("trait", "AURA");
        courseValues.put("flavorText","She accepts guild funds for repairs!");
        courseValues.put("attack", 1);
        courseValues.put("health", 4);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //cruel taskmaster
        courseValues = new ContentValues();
        courseValues.put("cardName","Cruel Taskmaster");
        courseValues.put("cardImgSrc", R.drawable.crueltaskmaster);
        courseValues.put("rarity","BASIC");
        courseValues.put("manaCost", 2);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "WARRIOR");
        courseValues.put("description","Deal 1 damage to a minion and give it +2 Attack");
        courseValues.put("trait", "BATTLECRY");
        courseValues.put("flavorText","\"I'm going to need you to come in on Sunday'\" -Cruel Taskmaster");
        courseValues.put("attack", 2);
        courseValues.put("health",2);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //shieldmaiden
        courseValues = new ContentValues();
        courseValues.put("cardName","Shieldmaiden");
        courseValues.put("cardImgSrc", R.drawable.shieldmaiden);
        courseValues.put("rarity","RARE");
        courseValues.put("manaCost", 6);
        courseValues.put("sets", "GVG");
        courseValues.put("collectible",true);
        courseValues.put("class", "WARRIOR");
        courseValues.put("description","Gain 5 Armor");
        courseValues.put("trait", "BATTLECRY");
        courseValues.put("flavorText","She has three shieldbearers in her party to supply her with back ups when she gets low on durability");
        courseValues.put("attack", 5);
        courseValues.put("health",5);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //grommash
        courseValues = new ContentValues();
        courseValues.put("cardName","Grommash Hellscream");
        courseValues.put("cardImgSrc", R.drawable.grommash);
        courseValues.put("rarity","LEGENDARY");
        courseValues.put("manaCost", 8);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "WARRIOR");
        courseValues.put("description","+6 Attack");
        courseValues.put("trait", "ENRAGE");
        courseValues.put("flavorText","Grommash drank the tainted bloof of Mannoroth, dooming the orcs to green skin and red eyes! Maybe not his best decision");
        courseValues.put("attack", 4);
        courseValues.put("health",9);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //acolyte
        courseValues = new ContentValues();
        courseValues.put("cardName","Acolyte of Pain");
        courseValues.put("cardImgSrc", R.drawable.acolyteofpain);
        courseValues.put("rarity","COMMON");
        courseValues.put("manaCost", 3);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "NEUTRAL");
        courseValues.put("description","Whenever this minion takes damage, draw a card");
        courseValues.put("trait", "AURA");
        courseValues.put("flavorText","He trained when he was younger to be an acolyte of joy, buy things didnt work out like he thought they would");
        courseValues.put("attack", 1);
        courseValues.put("health",3);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //bgh
        courseValues = new ContentValues();
        courseValues.put("cardName","Big Game Hunter");
        courseValues.put("cardImgSrc", R.drawable.biggamehunter);
        courseValues.put("rarity","EPIC");
        courseValues.put("manaCost", 3);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "NEUTRAL");
        courseValues.put("description","Destroy a minion with an Attack of 7 or more");
        courseValues.put("trait", "BATTLECRY");
        courseValues.put("flavorText","More devilsaurs no longer excite him. Soon he'll be trying to catch Onyxia with only a dull Krol Blade");
        courseValues.put("attack", 4);
        courseValues.put("health",2);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //harrison
        courseValues = new ContentValues();
        courseValues.put("cardName","Harrison Jones");
        courseValues.put("cardImgSrc", R.drawable.harrisonjones);
        courseValues.put("rarity","LEGENDARY");
        courseValues.put("manaCost", 5);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "NEUTRAL");
        courseValues.put("description","Destroy your opponent's weapon and draw cards equal to its Durability");
        courseValues.put("trait", "BATTLECRY");
        courseValues.put("flavorText","The belongs in the Hall of Explorers!");
        courseValues.put("attack", 5);
        courseValues.put("health",4);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //sludge
        courseValues = new ContentValues();
        courseValues.put("cardName","Sludge Belcher");
        courseValues.put("cardImgSrc", R.drawable.sludgebelcher);
        courseValues.put("rarity","RARE");
        courseValues.put("manaCost", 5);
        courseValues.put("sets", "NAX");
        courseValues.put("collectible",true);
        courseValues.put("class", "NEUTRAL");
        courseValues.put("description","Summon a 1/2 Slime with Taunt");
        courseValues.put("trait", "DEATHRATTLE");
        courseValues.put("flavorText","DO NOT GIVE HIM A ROOT BEER");
        courseValues.put("attack",3);
        courseValues.put("health",5);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //sylvanas
        courseValues = new ContentValues();
        courseValues.put("cardName","Sylvanas Windrunner");
        courseValues.put("cardImgSrc", R.drawable.sylvanas);
        courseValues.put("rarity","LEGENDARY");
        courseValues.put("manaCost", 6);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "NEUTRAL");
        courseValues.put("description","Take control of a random enemy minion");
        courseValues.put("trait", "DEATHRATTLE");
        courseValues.put("flavorText","Sylvanas was turned into the Banshee Queen by Arthas, buy he probably should have just killed her because it just pissed her off");
        courseValues.put("attack", 5);
        courseValues.put("health",5);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //Baron
        courseValues = new ContentValues();
        courseValues.put("cardName","Baron Geddon");
        courseValues.put("cardImgSrc", R.drawable.barongeddon);
        courseValues.put("rarity","LEGENDARY");
        courseValues.put("manaCost", 7);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "NEUTRAL");
        courseValues.put("description","At the end of your turn, deal 2 damage to ALL other characters");
        courseValues.put("trait", "AURA");
        courseValues.put("flavorText","Baron Geddon was Ragnaros' foremost lieutenant until he got FIRED");
        courseValues.put("attack", 7);
        courseValues.put("health",5);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //Dr boom
        courseValues = new ContentValues();
        courseValues.put("cardName","Dr. Boom");
        courseValues.put("cardImgSrc", R.drawable.drboom);
        courseValues.put("rarity","LEGENDARY");
        courseValues.put("manaCost", 7);
        courseValues.put("sets", "GVG");
        courseValues.put("collectible",true);
        courseValues.put("class", "NEUTRAL");
        courseValues.put("description","Summon two 1/1 Boom Bots. WARNING: Bots may explode");
        courseValues.put("trait", "BATTLECRY");
        courseValues.put("flavorText","MARVEL AT HIS MIGHT");
        courseValues.put("attack", 7);
        courseValues.put("health",7);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //ragnaros
        courseValues = new ContentValues();
        courseValues.put("cardName","Ragnaros the Firelord");
        courseValues.put("cardImgSrc", R.drawable.ragnaros);
        courseValues.put("rarity","LEGENDARY");
        courseValues.put("manaCost", 8);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "NEUTRAL");
        courseValues.put("description","Can't Attack. At the end of your turn, deal 8 damage to a random enemy");
        courseValues.put("trait", "AURA");
        courseValues.put("flavorText","Ragnaros was summoned by the Dark Iron dwarves, who were eventually enslaved by the Firelord. Summoning Ragnaros often doesn't work out the way you want it to");
        courseValues.put("attack", 8);
        courseValues.put("health",8);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //alex
        courseValues = new ContentValues();
        courseValues.put("cardName","Alexstrasza");
        courseValues.put("cardImgSrc", R.drawable.alexstrasza);
        courseValues.put("rarity","LEGENDARY");
        courseValues.put("manaCost", 9);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "NEUTRAL");
        courseValues.put("description","Set a hero's remaining Health to 15");
        courseValues.put("trait", "BATTLECRY");
        courseValues.put("flavorText","Alexstrasza the Life-Binder brings life and hope to everyone. Exept Deathwing. And Malygos. And Nekros");
        courseValues.put("attack", 8);
        courseValues.put("health",8);
        courseValues.put("race", "DRAGON");
        db.insert("Minion", null, courseValues);

        //fiery war axe
        courseValues = new ContentValues();
        courseValues.put("cardName", "Fiery War Axe");
        courseValues.put("cardImgSrc", R.drawable.fierywaraxe);
        courseValues.put("rarity","BASIC");
        courseValues.put("manaCost", 2);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "WARRIOR");
        courseValues.put("description","");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText","During times of tranquility and harmony, this weapon was called by its less popular name, Chilly Peace Axe");
        courseValues.put("durability", 3);
        courseValues.put("damage", 2);
        db.insert("Weapon", null, courseValues);

        //deaths bite
        courseValues = new ContentValues();
        courseValues.put("cardName", "Death's Bite");
        courseValues.put("cardImgSrc", R.drawable.deathsbite);
        courseValues.put("rarity","COMMON");
        courseValues.put("manaCost", 4);
        courseValues.put("sets", "NAX");
        courseValues.put("collectible",true);
        courseValues.put("class", "WARRIOR");
        courseValues.put("description","Deal 1 damage to all minions");
        courseValues.put("trait", "DEATHRATTLE");
        courseValues.put("flavorText","\"Take a bite outta Death.\" - McScruff the Deathlord");
        courseValues.put("durability", 4);
        courseValues.put("damage", 2);
        db.insert("Weapon", null, courseValues);

        //exdecute
        courseValues = new ContentValues();
        courseValues.put("cardName","Execute");
        courseValues.put("cardImgSrc", R.drawable.execute);
        courseValues.put("rarity","BASIC");
        courseValues.put("manaCost", 1);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "WARRIOR");
        courseValues.put("description","Destroy a damaged enemy minion");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "It's okay, he deserved it");
        db.insert("Spell", null, courseValues);

        //shieldslam
        courseValues = new ContentValues();
        courseValues.put("cardName","Shield Slam");
        courseValues.put("cardImgSrc", R.drawable.shieldslam);
        courseValues.put("rarity","EPIC");
        courseValues.put("manaCost", 1);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "WARRIOR");
        courseValues.put("description","Deal 1 damage to a minion for each Armor you have");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "\"What is a better weapon? The sharp one your enemy expects, or the blunt one they ignore?\" -The Art of Warrior, Chapter 9");
        db.insert("Spell", null, courseValues);

        //whirlwind
        courseValues = new ContentValues();
        courseValues.put("cardName","Whirlwind");
        courseValues.put("cardImgSrc", R.drawable.whirlwind);
        courseValues.put("rarity","BASIC");
        courseValues.put("manaCost", 1);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "WARRIOR");
        courseValues.put("description","Deal 1 damage to ALL minion");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "The way to tell seasoned warriors from novice ones: the novies yell \"wheeee\" while whirlwinding");
        db.insert("Spell", null, courseValues);

        //shieldblock
        courseValues = new ContentValues();
        courseValues.put("cardName","Shield Block");
        courseValues.put("cardImgSrc", R.drawable.shieldblock);
        courseValues.put("rarity","BASIC");
        courseValues.put("manaCost", 3);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "WARRIOR");
        courseValues.put("description","Gain 5 Armor. Draw a card");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "Shields were invented because Face Block is USELESS");
        db.insert("Spell", null, courseValues);

        //brawl
        courseValues = new ContentValues();
        courseValues.put("cardName","Brawl");
        courseValues.put("cardImgSrc", R.drawable.brawl);
        courseValues.put("rarity","EPIC");
        courseValues.put("manaCost", 5);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "WARRIOR");
        courseValues.put("description","Destroy all minions except one. (chosen randomly)");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "Do you know the first rule of Brawl Club?");
        db.insert("Spell", null, courseValues);

        //apprentice
        courseValues = new ContentValues();
        courseValues.put("cardName","Sorcerer's Apprentice");
        courseValues.put("cardImgSrc", R.drawable.apprentice);
        courseValues.put("rarity","COMMON");
        courseValues.put("manaCost", 2);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "MAGE");
        courseValues.put("description","Your slepps cost (1) less");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "");
        courseValues.put("attack", 3);
        courseValues.put("health",2);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //assassin
        courseValues = new ContentValues();
        courseValues.put("cardName","Patient Assassin");
        courseValues.put("cardImgSrc", R.drawable.assassin);
        courseValues.put("rarity","EPIC");
        courseValues.put("manaCost", 2);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "ROGUE");
        courseValues.put("description","Destroy any minion damaged by this minion");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "");
        courseValues.put("attack", 1);
        courseValues.put("health",1);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //Coin
        courseValues = new ContentValues();
        courseValues.put("cardName","The Coin");
        courseValues.put("cardImgSrc", R.drawable.coin);
        courseValues.put("rarity","BASIC");
        courseValues.put("manaCost", 0);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "NEUTRAL");
        courseValues.put("description","Gain 1 Mana Crystal this turn only");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "");
        db.insert("Spell", null, courseValues);

        //grove
        courseValues = new ContentValues();
        courseValues.put("cardName","Keeper of the Grove");
        courseValues.put("cardImgSrc", R.drawable.grove);
        courseValues.put("rarity","RARE");
        courseValues.put("manaCost", 4);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "DRUID");
        courseValues.put("description","");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "");
        courseValues.put("attack", 2);
        courseValues.put("health",4);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);

        //imp
        courseValues = new ContentValues();
        courseValues.put("cardName","Flame Imp");
        courseValues.put("cardImgSrc", R.drawable.imp);
        courseValues.put("rarity","COMMON");
        courseValues.put("manaCost", 1);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "WARLOCK");
        courseValues.put("description","");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "");
        courseValues.put("attack", 3);
        courseValues.put("health",2);
        courseValues.put("race", "DEMON");
        db.insert("Minion", null, courseValues);

        //Kings
        courseValues = new ContentValues();
        courseValues.put("cardName","Blessing of the Kings");
        courseValues.put("cardImgSrc", R.drawable.kings);
        courseValues.put("rarity","BASIC");
        courseValues.put("manaCost", 4);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "PALADIN");
        courseValues.put("description","Give a minion +4/+4");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "");
        db.insert("Spell", null, courseValues);

        //bolt
        courseValues = new ContentValues();
        courseValues.put("cardName","Lightning Bolt");
        courseValues.put("cardImgSrc", R.drawable.lightningbolt);
        courseValues.put("rarity","COMMON");
        courseValues.put("manaCost", 1);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "SHAMAN");
        courseValues.put("description","");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "");
        db.insert("Spell", null, courseValues);

        //longbow
        courseValues = new ContentValues();
        courseValues.put("cardName","Gladiator's Longbow");
        courseValues.put("cardImgSrc", R.drawable.longbow);
        courseValues.put("rarity","EPIC");
        courseValues.put("manaCost", 7);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "HUNTER");
        courseValues.put("description","");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "");
        courseValues.put("durability", 4);
        courseValues.put("damage", 2);
        db.insert("Weapon", null, courseValues);

        //Velen
        courseValues = new ContentValues();
        courseValues.put("cardName","Prophet Velen");
        courseValues.put("cardImgSrc", R.drawable.velen);
        courseValues.put("rarity","LEGENDARY");
        courseValues.put("manaCost", 7);
        courseValues.put("sets", "VAN");
        courseValues.put("collectible",true);
        courseValues.put("class", "PRIEST");
        courseValues.put("description","");
        courseValues.put("trait", "NONE");
        courseValues.put("flavorText", "");
        courseValues.put("attack", 7);
        courseValues.put("health",7);
        courseValues.put("race", "NONE");
        db.insert("Minion", null, courseValues);
    }

}