package com.example.a00885912.myapplication;

import android.content.Intent;
        import android.database.sqlite.SQLiteDatabase;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
import android.view.View;

import java.io.IOException;

/**
 * This class is the main class of the program. It handles two intent checking.
 */
public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create db
        try {
            DatabaseHelper helper = new DatabaseHelper(this);
            db = helper.getWritableDatabase();
        } catch(IOException e) {}
        setContentView(R.layout.activity_main);
    }

    //direct to gallery
    public void onClickGallery(View view) {
        Intent intent;
        intent = new Intent(this,Navigation.class);
        startActivity(intent);
    }

    //direct to deckbuilder
    public void onClickDeckBuilder(View view) {
        Intent intent;
        intent = new Intent(this,DeckBuilder.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
