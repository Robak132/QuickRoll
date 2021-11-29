package com.robak.android.quickroll;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.robak.android.quickroll.databinding.*;
import com.robak.android.quickroll.fragments.BowModifiers;
import com.robak.android.quickroll.fragments.MainTable;
import com.robak.android.quickroll.fragments.ShieldModifiers;
import com.robak.android.quickroll.fragments.SwordModifiers;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static int generalMode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AdManagerAdView adView = new AdManagerAdView(this);
        adView.setAdSizes(AdSize.BANNER);
        adView.setAdUnitId("/6499/example/banner");
        // TODO: Add adView to your view hierarchy.

        com.robak.android.quickroll.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        List<MainTable> tables = new ArrayList<>();
        tables.add(new MainTable(new SwordModifiers()));
        tables.add(new MainTable(new ShieldModifiers()));
        tables.add(new MainTable(new BowModifiers()));
        replaceFragment(tables.get(generalMode), R.id.mainTableFrame);

        for (int i=0; i<3; i++) {
            int finalI = i;
            ImageView view = getImageViewByName("category" + i);
            view.setOnClickListener(v -> {
                if (generalMode != finalI) {
                    getImageViewByName("category" + generalMode).setColorFilter(getColor(R.color.black));
                    generalMode = finalI;
                    getImageViewByName("category" + generalMode).setColorFilter(getColor(R.color.purple_primary));
                    replaceFragment(tables.get(generalMode), R.id.mainTableFrame);
                }
            });
        }
    }

    private void replaceFragment(Fragment fragment, int frame) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(frame, fragment);
        ft.commit();
    }
    private ImageView getImageViewByName(String name) {
        int id = getResources().getIdentifier(name, "id", getPackageName());
        return findViewById(id);
    }
    private void setImageColor(String name, int color) {
        getImageViewByName(name).setColorFilter(getColor(color));
    }
}