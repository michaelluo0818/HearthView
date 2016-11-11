package com.example.a00885912.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * This class is a list view adapter to be used in the deck builder.
 */
public class ListAdapter extends ArrayAdapter<Card> {
    Card[] cards;
    Context c;
    int res;

    /**
     * Sets listview
     * @param context parent activity
     * @param resource layout
     * @param objects cards to be put in the listview
     */
    public ListAdapter(Context context, int resource, Card[] objects) {
        super(context, resource, objects);
        cards = objects;
        c = context;
        res = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Card card = cards[position];
        //set listview layout
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(res, parent,false);
        }

        //add card name and mana cost to the listview layout
        //ImageView img = (ImageView)convertView.findViewById(R.id.imageView);
        TextView txt = (TextView)convertView.findViewById(R.id.cardName);
        TextView mana = (TextView)convertView.findViewById(R.id.mana);

        //img.setImageResource(card.getImgSrc());
        txt.setText(card.getName());
        mana.setText("" + card.getManaCost());
        return convertView;
    }
}
