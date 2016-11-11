package com.example.a00885912.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Expandable list view adapter to be used for navigation drawer
 */
public class ExpanableAdapter extends BaseExpandableListAdapter {
    //the navigation options
    private ArrayList<DrawerItem> arr;
    //get the activity of parent
    private Activity c;

    public ExpanableAdapter(Activity c, ArrayList<DrawerItem> arr) {
        this.c = c;
        this.arr = arr;
    }

    @Override
    public int getGroupCount() {
        return arr.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return arr.get(groupPosition).items.size();
    }

    @Override
    public DrawerItem getGroup(int groupPosition) {
        return arr.get(groupPosition);
    }

    @Override
    public String getChild(int groupPosition, int childPosition) {
        return arr.get(groupPosition).items.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Set the layout for the labels
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return label's view
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = c.getLayoutInflater();
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.expandable_view,null);
        }
        TextView tx = (TextView)convertView.findViewById(R.id.tag);
        tx.setText(arr.get(groupPosition).tag);
        return convertView;
    }

    /**
     * Set the layout for the label's contents
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return label's child view
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = c.getLayoutInflater();
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.expanded_view,null);
        }
        TextView tx = (TextView)convertView.findViewById(R.id.item);
        ImageView img = (ImageView) convertView.findViewById(R.id.classIcon);
        if(arr.get(groupPosition).tag.equals("Classes")) {
            switch(childPosition) {
                case 0 :
                    img.setImageResource(R.drawable.druid);
                    break;
                case 1 :
                    img.setImageResource(R.drawable.hunter);
                    break;
                case 2 :
                    img.setImageResource(R.drawable.mage);
                    break;
                case 3 :
                    img.setImageResource(R.drawable.paladin);
                    break;
                case 4 :
                    img.setImageResource(R.drawable.priest);
                    break;
                case 5 :
                    img.setImageResource(R.drawable.rogue);
                    break;
                case 6 :
                    img.setImageResource(R.drawable.shaman);
                    break;
                case 7 :
                    img.setImageResource(R.drawable.warlock);
                    break;
                case 8 :
                    img.setImageResource(R.drawable.warrior);
                    break;
                case 9 :
                    img.setImageResource(R.drawable.neutral);
                    break;
            }
        } else {
            img.setImageResource(0);
        }
        tx.setText(arr.get(groupPosition).items.get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
