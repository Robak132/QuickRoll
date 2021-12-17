package com.robak.android.quickroll;

import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.robak.android.quickroll.fragments.BowModifiers;
import com.robak.android.quickroll.fragments.MainTable;
import com.robak.android.quickroll.fragments.ShieldModifiers;
import com.robak.android.quickroll.fragments.SwordModifiers;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        List<MainTable> tables = new ArrayList<>();
        tables.add(new MainTable(new SwordModifiers()));
        tables.add(new MainTable(new ShieldModifiers()));
        tables.add(new MainTable(new BowModifiers()));

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.sword:
                    replaceFragment(tables.get(0), R.id.fragmentContainer);
                    return true;
                case R.id.shield:
                    replaceFragment(tables.get(1), R.id.fragmentContainer);
                    return true;
                case R.id.bow:
                    replaceFragment(tables.get(2), R.id.fragmentContainer);
                    return true;
            }
            return false;
        });
    }

    private void replaceFragment(Fragment fragment, int frame) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(frame, fragment);
        ft.commit();
    }
}