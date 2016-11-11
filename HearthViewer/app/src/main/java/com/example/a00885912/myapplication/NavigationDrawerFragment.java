package com.example.a00885912.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;
    private ArrayList<DrawerItem> arr = new ArrayList<>();
    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private ExpandableListView mDrawerListView;
    private View mFragmentContainerView;

    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private SQLiteDatabase db;
    private DatabaseHelper helper;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    /**
     * Sets up view pager adapter to work with all sortings
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDrawerListView = (ExpandableListView) inflater.inflate(
                R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        addDrawers();
        ExpanableAdapter adapter = new ExpanableAdapter(getActivity(),arr);
        mDrawerListView.setAdapter(adapter);

        //set different adapters for each navigation selection
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);
        mDrawerListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                ViewPager vp;
                vp = (ViewPager) getActivity().findViewById(R.id.pager);
                switch (groupPosition) {
                    case 0:
                        setClassPager(vp, childPosition);
                        break;
                    case 1:
                        setManaPager(vp, childPosition);
                        break;
                    case 2:
                        setTribePager(vp, childPosition);
                        break;
                    case 3:
                        setOtherPager(vp,childPosition);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        return mDrawerListView;
    }

    /**
     * Sets up view pager with Home page and Clear filter functionality
     * @param vps The view pager to set adapter with
     * @param childPosition The position selected in other list.
     */
    public void setOtherPager(ViewPager vps, int childPosition) {
        ArrayList<Card> cards = new ArrayList<>();
        try {
            helper = new DatabaseHelper(getActivity());
            db = helper.getReadableDatabase();
        } catch(IOException e) {}
        switch(childPosition) {
            //grab all cards
            case 0:
                cards = helper.getAllCards(db);
                break;
            //direct back to main page
            case 1:
                Intent intent;
                intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        SwipeAdapter adapter = new SwipeAdapter(getActivity(), cards);
        vps.setAdapter(adapter);
    }

    /**
     * Sets view pager to handle mana crystal sortings
     * @param vps The view pager to set adapter with
     * @param childPosition The desired mana cost requirement
     */
    public void setManaPager(ViewPager vps, int childPosition) {
        ArrayList<Card> cards = new ArrayList<>();
        try {
            helper = new DatabaseHelper(getActivity());
            db = helper.getReadableDatabase();
        } catch(IOException e) {}
        switch(childPosition) {
            case 0:
                cards = helper.getManaCards(db, 0);
                break;
            case 1:
                cards = helper.getManaCards(db, 1);
                break;
            case 2:
                cards = helper.getManaCards(db, 2);
                break;
            case 3:
                cards = helper.getManaCards(db, 3);
                break;
            case 4:
                cards = helper.getManaCards(db, 4);
                break;
            case 5:
                cards = helper.getManaCards(db, 5);
                break;
            case 6:
                cards = helper.getManaCards(db, 6);
                break;
            case 7:
                cards = helper.getManaCards(db, 7);
                break;
            case 8:
                cards = helper.getManaCards(db, 8);
                break;
            case 9:
                cards = helper.getManaCards(db, 9);
                break;
            case 10:
                cards = helper.getManaCards(db, 10);
                break;
        }
        SwipeAdapter adapter = new SwipeAdapter(getActivity(), cards);
        vps.setAdapter(adapter);
    }

    /**
     * Sets view pager to handle Race sortings
     * @param vps The view pager to set adapter with
     * @param childPosition The desired Race requirement
     */
    public void setTribePager(ViewPager vps, int childPosition) {
        ArrayList<Card> cards = new ArrayList<>();
        try {
            helper = new DatabaseHelper(getActivity());
            db = helper.getReadableDatabase();
        } catch(IOException e) {}
        switch(childPosition) {
            case 0:
                cards = helper.getTribeCards(db, "BEAST");
                break;
            case 1:
                cards = helper.getTribeCards(db, "DEMON");
                break;
            case 2:
                cards = helper.getTribeCards(db, "DRAGON");
                break;
            case 3:
                cards = helper.getTribeCards(db, "MECH");
                break;
            case 4:
                cards = helper.getTribeCards(db, "MURLOC");
                break;
            case 5:
                cards = helper.getTribeCards(db, "PIRATE");
                break;
            case 6:
                cards = helper.getTribeCards(db, "TOTEM");
                break;
            case 7:
                cards = helper.getTribeCards(db, "NONE");
                break;
            default:
                break;
        }
        SwipeAdapter adapter = new SwipeAdapter(getActivity(), cards);
        vps.setAdapter(adapter);
    }


    /**
     * Sets view pager to handle Class sortings
     * @param vps The view pager to set adapter with
     * @param childPosition The desired Class requirement
     */
    public void setClassPager(ViewPager vps, int childPosition) {
        ArrayList<Card> cards = new ArrayList<>();
        try {
            helper = new DatabaseHelper(getActivity());
            db = helper.getReadableDatabase();
        } catch(IOException e) {}
        switch(childPosition) {
            case 0:
                cards = helper.getClassCards(db, "DRUID");
                break;
            case 1:
                cards = helper.getClassCards(db, "HUNTER");
                break;
            case 2:
                cards = helper.getClassCards(db, "MAGE");
                break;
            case 3:
                cards = helper.getClassCards(db, "PALADIN");
                break;
            case 4:
                cards = helper.getClassCards(db, "PRIEST");
                break;
            case 5:
                cards = helper.getClassCards(db, "ROGUE");
                break;
            case 6:
                cards = helper.getClassCards(db, "SHAMAN");
                break;
            case 7:
                cards = helper.getClassCards(db, "WARLOCK");
                break;
            case 8:
                cards = helper.getClassCards(db, "WARRIOR");
                break;
            case 9:
                cards = helper.getClassCards(db, "NEUTRAL");
                break;
            default:
                break;
        }
        SwipeAdapter adapter = new SwipeAdapter(getActivity(), cards);
        vps.setAdapter(adapter);
    }

    /**
     * Populates the lists with items
     */
    public void addDrawers() {
        ArrayList<String> cla = new ArrayList<>();
        cla.add("Druid");
        cla.add("Hunter");
        cla.add("Mage");
        cla.add("Paladin");
        cla.add("Priest");
        cla.add("Rogue");
        cla.add("Shaman");
        cla.add("Warlock");
        cla.add("Warrior");
        cla.add("Neutral");
        arr.add(new DrawerItem("Classes",cla));
        ArrayList<String> manas = new ArrayList<>();
        manas.add("0");
        manas.add("1");
        manas.add("2");
        manas.add("3");
        manas.add("4");
        manas.add("5");
        manas.add("6");
        manas.add("7");
        manas.add("8");
        manas.add("9");
        manas.add("10");
        arr.add(new DrawerItem("Mana Cost",manas));
        ArrayList<String> tribe = new ArrayList<>();
        tribe.add("Beast");
        tribe.add("Demon");
        tribe.add("Dragon");
        tribe.add("Mech");
        tribe.add("Murloc");
        tribe.add("Pirate");
        tribe.add("Totem");
        tribe.add("None");
        arr.add(new DrawerItem("Tribes", tribe));
        ArrayList<String> others = new ArrayList<>();
        others.add("Clear Filter");
        others.add("Home Page");
        arr.add(new DrawerItem("Others", others));

    }
    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                    /* host Activity */
                mDrawerLayout,                    /* DrawerLayout object */
                R.drawable.ic_drawer,             /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager
                            .getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().supportInvalidateOptionsMenu(); // calls onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        mCurrentSelectedPosition = position;
        if (mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {
            inflater.inflate(R.menu.global, menu);
            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        if (item.getItemId() == R.id.action_example) {
            Toast.makeText(getActivity(), "Example action.", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to show the global app
     * 'context', rather than just what's in the current screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setTitle(R.string.app_name);
    }

    private ActionBar getActionBar() {
        return ((AppCompatActivity) getActivity()).getSupportActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(int position);
    }
}
