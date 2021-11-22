package com.robak.android.quickroll;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.robak.android.quickroll.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    static int distanceMode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.valueField.addTextChangedListener((TextChangedAdapter) (s, start, before, count) -> parseRoll());
        binding.rollField.addTextChangedListener((TextChangedAdapter) (s, start, before, count) -> parseRoll());
        binding.range0.setOnClickListener(v -> {
            if (distanceMode == 0) {
                binding.range0.setColorFilter(getColor(R.color.black));
                distanceMode = 1;
            } else {
                binding.range0.setColorFilter(getColor(R.color.green));
                distanceMode = 0;
            }
        });
//        binding.imageView.setOnClickListener(v -> {
//            binding.imageView.setImageResource(R.drawable.sword_ready);
//        });
//        binding.imageView7.setOnClickListener(v -> binding.imageView.setImageResource(R.drawable.sword));
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

            setInfo(binding.resultField, value, 0, roll);
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());

            System.out.print(e.getMessage());
            binding.resultField.setText("");
        }
    }
    private void setInfo(EditText textField, int value, int modifier, int roll) {
        boolean success = roll + modifier <= value;
        boolean doubleValue = (roll / 10) % 10 == roll % 10;

        int color = success ? Color.GREEN : Color.RED;
        textField.setTextColor(color);


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
}