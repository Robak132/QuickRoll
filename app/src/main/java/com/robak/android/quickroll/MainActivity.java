package com.robak.android.quickroll;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.robak.android.quickroll.databinding.*;
import com.robak.android.quickroll.tools.ItemSelectedAdapter;
import com.robak.android.quickroll.tools.ObservableModifier;
import com.robak.android.quickroll.tools.TextChangedAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private ObservableModifier viewModel;

    static int generalMode = 2;
    int modifier = 0;
    int SLModifier = 0;
    int advantage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BowModifiers activeFragment = (BowModifiers) createFragment(new BowModifiers());
        viewModel = new ViewModelProvider(this).get(ObservableModifier.class);
        viewModel.observe(this, modifier -> updateModifier());

        binding.valueField.addTextChangedListener((TextChangedAdapter) (s, start, before, count) -> parseRoll());
        binding.rollField.addTextChangedListener((TextChangedAdapter) (s, start, before, count) -> parseRoll());
        binding.bowImage.setOnClickListener(v -> {
            if (generalMode == 2) {
                binding.bowImage.setColorFilter(getColor(R.color.black));
//                createFragment(new SwordModifiers());
                generalMode = 0;
            } else {
                binding.bowImage.setColorFilter(getColor(R.color.purple_primary));
                createFragment(new BowModifiers());
                generalMode = 2;
            }
        });

        Spinner spinner = binding.advantageField;
        Integer[] items = new Integer[9];
        for (int i=0; i < 9; i++) {
            items[i] = i;
        }
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((ItemSelectedAdapter) (parent, view, position, id) -> {
            advantage = items[position] * 10;
            parseRoll();
        });
    }

    private void parseRoll() {
        try {
            String valueText = binding.valueField.getText().toString();
            int value = Integer.parseInt(valueText);

            String rollText = binding.rollField.getText().toString();
            int roll = Integer.parseInt(rollText);
            if (roll > 100 || roll < 1) {
                throw new Exception("Invalid roll value");
            }

            setInfo(binding.resultField, value, roll, modifier + advantage);
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
            binding.resultField.setText("");
        }
    }
    private void setInfo(EditText textField, int value, int roll, int modifier) {
        boolean success = roll <= value + modifier;
        boolean doubleValue = (roll / 10) % 10 == roll % 10;

        textField.setTextColor(success ? getColor(R.color.green) : getColor(R.color.red));

        List<String> stringList = new ArrayList<>();
        if (doubleValue) {
            stringList.add(success ? getString(R.string.critical) : getString(R.string.fumbled));
        }
        if (roll <= 5 && roll >= 1)    stringList.add(getString(R.string.auto_success));
        if (roll <= 100 && roll >= 96) stringList.add(getString(R.string.auto_failure));

        int PS = (value + modifier) / 10 - roll / 10;
        Log.d("PS", String.format("%d [%d] / %d [%d]", value, value / 10, roll, roll / 10));
        stringList.add(String.format(getString(R.string.SL), PS));
        binding.resultField.setText(String.join(" ", stringList));
    }
    private void updateModifier() {
        modifier = viewModel.getModifier();
        SLModifier = viewModel.getSLModifier();
        binding.modifierField.setText(String.valueOf(modifier));
        binding.SLModifierField.setText(String.format(getString(R.string.SL), SLModifier));
        parseRoll();
    }
    private Fragment createFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
        return fragment;
    }

    ImageView getImageViewByName(String name) {
        int id = getResources().getIdentifier(name, "id", getPackageName());
        return findViewById(id);
    }
    void setImageColor(String name, int color) {
        ImageView imageView = getImageViewByName(name);
        imageView.setColorFilter(getColor(color));
    }
}