package com.robak.android.quickroll;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.robak.android.quickroll.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(v -> {
            try {
                String valueText = binding.valueField.getText().toString();
                int value = Integer.parseInt(valueText);

                String rollText = binding.rollField.getText().toString();
                int roll = Integer.parseInt(rollText);

                setInfo(binding.editTextPhone3, value, 0, roll);
            } catch (Exception e) {
                System.out.print(e.getMessage());
                binding.editTextPhone3.setText("");
            }
        });
    }

    private void setInfo(EditText textField, int value, int modifier, int roll) {
        boolean doubleValue = roll / 10 == roll % 10;
        String comment = "";

        if (roll + modifier > value) {
            textField.setTextColor(Color.RED);
            if (doubleValue) {
                comment = "Fumble";
            }
        } else {
            textField.setTextColor(Color.GREEN);
            if (doubleValue) {
                comment = "Critical";
            }
        }

        binding.editTextPhone3.setText(String.format("%s %d PS", (value + modifier)/ 10 - roll / 10, comment));
    }
}