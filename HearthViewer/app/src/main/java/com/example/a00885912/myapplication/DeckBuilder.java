package com.example.a00885912.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the deckbuilder functionality
 */
public class DeckBuilder extends AppCompatActivity {
    //deck size limit
    private final int MAX = 30;

    //db
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    //2 listviews and 2 maps for algorithm
    ArrayList<Card> allCards;
    HashMap<Integer,Card> deckCards = new HashMap<Integer,Card>();
    Map<String,Integer> duplicate = new HashMap();
    int deckCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set layout
        setContentView(R.layout.activity_deck_builder);
        //add checking constraints.
        handleList();
    }

    /**
     * This function checks if the input is valid and fits a deck's requirements
     */
    public void handleList() {
        //The 2 listviews to display cards with
        final ListView lv = (ListView)findViewById(R.id.cards);
        final ListView lv2 = (ListView)findViewById(R.id.decks);

        allCards = new ArrayList<>();
        //grab all cards from db
        try{
            helper = new DatabaseHelper(this);
            db = helper.getReadableDatabase();
        } catch(IOException e) {}
        allCards = helper.getAllCards(db);

        //populate listview 1 with all cards
        ListAdapter adapter = new ListAdapter(this,R.layout.list_view,allCards.toArray(new Card[allCards.size()]));
        lv.setAdapter(adapter);

        //add requirements for deck
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View v, int i, long a) {
                Card temp = (Card) av.getAdapter().getItem(i);
                if(deckCount < MAX) {

                   if(temp instanceof Minion || temp instanceof Spell || temp instanceof Weapon) {
                       //add 1st card
                       if(duplicate.get(temp.getName()) == null) {
                           duplicate.put(temp.getName(), 0);
                           Toast.makeText(DeckBuilder.this, "Added Card", Toast.LENGTH_SHORT).show();
                       }
                       if (temp.getRarity().equals("LEGENDARY") && duplicate.get(temp.getName()) < 1) {
                           deckCards.put(deckCount++, temp);
                           duplicate.put(temp.getName(), 1 + (duplicate.get(temp.getName())));
                           Toast.makeText(DeckBuilder.this, "Added Card", Toast.LENGTH_SHORT).show();
                       }else if (duplicate.get(temp.getName()) < 2 && !temp.getRarity().equals("LEGENDARY")) {
                           deckCards.put(deckCount++, temp);
                           duplicate.put(temp.getName(), 1 + duplicate.get(temp.getName()));
                           Toast.makeText(DeckBuilder.this, "Added Card", Toast.LENGTH_SHORT).show();
                       } else {
                           Toast.makeText(DeckBuilder.this, temp.getName() + " has exceed deck limits", Toast.LENGTH_SHORT).show();
                       }
                   }

                    ListAdapter list2adapter = new ListAdapter(DeckBuilder.this, R.layout.list_view,deckCards.values().toArray(new Card[deckCards.size()]));
                    lv2.setAdapter(list2adapter);
                } else {
                    Toast.makeText(DeckBuilder.this, "Too many cards", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    //need to add onclicklistener to items

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deck_builder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
