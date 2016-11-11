package com.example.a00885912.myapplication;

import java.util.ArrayList;

/**
 * Drawer adapter data structure
 */
public class DrawerItem {
    //The name of the label
    public String tag;
    //The content of the label
    public ArrayList<String> items;

    public DrawerItem(String tag, ArrayList<String> items) {
        this.tag = tag;
        this.items = items;
    }

}
