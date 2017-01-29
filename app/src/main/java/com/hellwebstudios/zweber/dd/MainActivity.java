package com.hellwebstudios.zweber.dd;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.hellwebstudios.zweber.dd.Fragments.AboutFragment;
import com.hellwebstudios.zweber.dd.Fragments.Adventures.AttackRollsFragment;
import com.hellwebstudios.zweber.dd.Fragments.Adventures.ChapMenuFragment;
import com.hellwebstudios.zweber.dd.Fragments.Adventures.ChaptersFragment;
import com.hellwebstudios.zweber.dd.Fragments.Characters.CharClassesFrag;
import com.hellwebstudios.zweber.dd.Fragments.Characters.CharRacesFrag;
import com.hellwebstudios.zweber.dd.Fragments.Characters.CharactersFragment;
import com.hellwebstudios.zweber.dd.Fragments.DashboardFragment;
import com.hellwebstudios.zweber.dd.Fragments.DiceSets.DiceSetDieFragment;
import com.hellwebstudios.zweber.dd.Fragments.DiceSets.DiceSetsFragment;
import com.hellwebstudios.zweber.dd.Fragments.HelpFragment;
import com.hellwebstudios.zweber.dd.Fragments.Adventures.AdventuresFragment;
import com.hellwebstudios.zweber.dd.Fragments.Skills.NewSkillFrag;
import com.hellwebstudios.zweber.dd.Fragments.Skills.SkillsFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    DataHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set the fragment initially
        DashboardFragment fragment = new DashboardFragment();
        fragment.setRetainInstance(true);
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        //DB object.
        db = new DataHelper(this);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {

            //DashboardFragment
            DashboardFragment fragment = new DashboardFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_sessions) {

            //Display the SessionMenu Fragment.
            AdventuresFragment fragment = new AdventuresFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "adventuresFragment");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_characters) {

            //Display the CharactersMenu Fragment.
            CharactersFragment fragment = new CharactersFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "charactersFragment");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_skills) {

            //Display the SkillsMenu Fragment.
            SkillsFragment fragment = new SkillsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "skillsFragment");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_dice_sets) {

            //Display the DiceSets Menu Fragment.
            DiceSetsFragment fragment = new DiceSetsFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "diceSetsFragment");
            fragmentTransaction.commit();

        }
//        else if (id == R.id.nav_settings) {
//
//            //Display the Settings Menu Fragment.
//            SettingsFragment fragment = new SettingsFragment();
//            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.replace(R.id.fragment_container, fragment, "settingsFragment");
//            fragmentTransaction.commit();
//
//        }
        else if (id == R.id.nav_about) {

            //Display the About Menu fragment.
            AboutFragment fragment = new AboutFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment, "aboutFragment");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_help) {

            Intent webLink = new Intent(Intent.ACTION_VIEW);
            webLink.setData(Uri.parse("https://docs.google.com/document/d/1-7EwIJYdVmmivkyp-dV0eznPsDFqz100nZpOeoZEFF0/edit"));
            startActivity(webLink);
            //https://docs.google.com/document/d/1-7EwIJYdVmmivkyp-dV0eznPsDFqz100nZpOeoZEFF0/edit

        }

        //More section...
        else if (id == R.id.nav_facebook) {

            Intent webLink = new Intent(Intent.ACTION_VIEW);
            webLink.setData(Uri.parse("https://www.facebook.com/HellWebStudios"));
            startActivity(webLink);
            //https://www.facebook.com/HellWebStudios/

        } else if (id == R.id.nav_twitter) {

            Intent webLink = new Intent(Intent.ACTION_VIEW);
            webLink.setData(Uri.parse("https://twitter.com/HellWeb_Studios"));
            startActivity(webLink);
            //https://twitter.com/HellWeb_Studios

        }
        else if (id == R.id.nav_other_apps) {

            Intent webLink = new Intent(Intent.ACTION_VIEW);
            webLink.setData(Uri.parse("https://play.google.com/store/apps/developer?id=HellWeb+Studios"));
            startActivity(webLink);
            //https://play.google.com/store/apps/developer?id=HellWeb+Studios
        }
        else if (id == R.id.nav_gms_twitch) {

            Intent webLink = new Intent(Intent.ACTION_VIEW);
            webLink.setData(Uri.parse("https://www.twitch.tv/gentlemenssword"));
            startActivity(webLink);
            //https://www.twitch.tv/gentlemenssword
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Handle Fragment navigation...
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            FragmentManager fmgr = getSupportFragmentManager();

            //Adventures Frag
            AdventuresFragment adventuresFragment = (AdventuresFragment) fmgr.findFragmentByTag("adventuresFragment");
            if (adventuresFragment != null && adventuresFragment.isVisible())
                DashFrag();

            //Chapters Frag
            ChaptersFragment chaptersFragment = (ChaptersFragment) fmgr.findFragmentByTag("chaptersFragment");
            if (chaptersFragment != null && chaptersFragment.isVisible())
            {
                //Take the user back to the Characters menu fragment.
                AdventuresFragment fragment = new AdventuresFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fmgr.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "adventuresFragment");
                fragmentTransaction.commit();
            }

            //ChapMenuFragment
//            ChapMenuFragment chapMenuFragment = (ChapMenuFragment) fmgr.findFragmentByTag("chapMenuFragment");
//            if (chapMenuFragment != null && chapMenuFragment.isVisible())
//            {
//                //Take the user back to the Chapters fragment.
//                ChaptersFragment fragment = new ChaptersFragment();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = fmgr.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment, "chaptersFragment");
//                fragmentTransaction.commit();
//            }

            //AttackRolls Fragment
//            AttackRollsFragment attackRollsFragment = (AttackRollsFragment) fmgr.findFragmentByTag("attackRollsFragment");
//            if (attackRollsFragment != null && attackRollsFragment.isVisible())
//            {
//                //Take the user back to the ChapMenuFragment.
//                ChapMenuFragment fragment = new ChapMenuFragment();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = fmgr.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment, "chapMenuFragment");
//                fragmentTransaction.commit();
//            }

            //Characters Frag
            CharactersFragment charactersFragment = (CharactersFragment) fmgr.findFragmentByTag("charactersFragment");
            if (charactersFragment != null && charactersFragment.isVisible())
                DashFrag();

            //NewCharFrag
//            NewCharFrag newCharFrag = (NewCharFrag) fmgr.findFragmentByTag("newCharFrag");
//            if (newCharFrag != null && newCharFrag.isVisible())
//            {
//                //Take the user back to the Characters menu fragment.
//                CharactersFragment fragment = new CharactersFragment();
//                android.support.v4.app.FragmentTransaction fragmentTransaction = fmgr.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment_container, fragment, "charactersFragment");
//                fragmentTransaction.commit();
//            }

            //CharClassesFrag
            CharClassesFrag charClassesFrag = (CharClassesFrag) fmgr.findFragmentByTag("charClassesFrag");
            if (charClassesFrag != null && charClassesFrag.isVisible())
            {
                //Take the user back to the Characters menu fragment.
                CharactersFragment fragment = new CharactersFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fmgr.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "charactersFragment");
                fragmentTransaction.commit();
            }

            //NewCharClassFrag


            //CharRacesFrag
            CharRacesFrag charRacesFrag = (CharRacesFrag) fmgr.findFragmentByTag("charRacesFrag");
            if (charRacesFrag != null && charRacesFrag.isVisible())
            {
                //Take the user back to the Characters menu fragment.
                CharactersFragment fragment = new CharactersFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fmgr.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "charactersFragment");
                fragmentTransaction.commit();
            }

            //Skills Frag
            SkillsFragment skillsFragment = (SkillsFragment) fmgr.findFragmentByTag("skillsFragment");
            if (skillsFragment != null && skillsFragment.isVisible())
                DashFrag();

            //AddSkill Frag
            NewSkillFrag newSkillFrag = (NewSkillFrag) fmgr.findFragmentByTag("newSkillFragment");
            if (newSkillFrag != null && newSkillFrag.isVisible())
            {
                //Take the user back to the Skills menu fragment.
                SkillsFragment fragment = new SkillsFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fmgr.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "skillsFragment");
                fragmentTransaction.commit();
            }

            //DiceSets Frag
            DiceSetsFragment diceSetsFragment = (DiceSetsFragment) fmgr.findFragmentByTag("diceSetsFragment");
            if (diceSetsFragment != null && diceSetsFragment.isVisible())
                DashFrag();

            //DiceSetDie Frag
            DiceSetDieFragment dsdF = (DiceSetDieFragment) fmgr.findFragmentByTag("diceSetDieFragment");
            if (dsdF != null && dsdF.isVisible())
            {
                //Take the user back to the DiceSetsFragment.
                DiceSetsFragment fragment = new DiceSetsFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fmgr.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, fragment, "diceSetsFragment");
                fragmentTransaction.commit();
            }

            //Child DiceSets Frag
            //TO-DO:

            //Settings Frag
//            SettingsFragment settingsFragment = (SettingsFragment) fmgr.findFragmentByTag("settingsFragment");
//            if (settingsFragment != null && settingsFragment.isVisible())
//                DashFrag();

            //About Frag
            AboutFragment aboutFragment = (AboutFragment) fmgr.findFragmentByTag("aboutFragment");
            if (aboutFragment != null && aboutFragment.isVisible())
                DashFrag();

            //Help Frag
            HelpFragment helpFragment = (HelpFragment) fmgr.findFragmentByTag("helpFragment");
            if (helpFragment != null && helpFragment.isVisible())
                DashFrag();

        }

        return true;
    }

    //DashFrag()
    public void DashFrag()
    {
        FragmentManager fmgr = getSupportFragmentManager();

        //Take the user to the Dashboard fragment.
        DashboardFragment fragment = new DashboardFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fmgr.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
