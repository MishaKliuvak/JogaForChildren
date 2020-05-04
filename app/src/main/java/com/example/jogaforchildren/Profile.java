package com.example.jogaforchildren;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.jogaforchildren.BottomContainers.CalendarFragment;
import com.example.jogaforchildren.BottomContainers.ListFragment;
import com.example.jogaforchildren.BottomContainers.TimerFragment;
import com.example.jogaforchildren.BottomContainers.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class Profile extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter vpa;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Intent intent = getIntent();
        String user_id = intent.getStringExtra("id");

        Log.d("user", user_id);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        vpa = new ViewPagerAdapter(getSupportFragmentManager());

        vpa.addFragment(new ListFragment(), "");
        vpa.addFragment(TimerFragment.getInstance(), "");
        vpa.addFragment(new CalendarFragment(), "");


        viewPager.setAdapter(vpa);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.list);
        tabLayout.getTabAt(1).setIcon(R.drawable.timer);
        tabLayout.getTabAt(2).setIcon(R.drawable.calendar);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position != 1 && TimerFragment.getInstance().started) TimerFragment.getInstance().clear();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
