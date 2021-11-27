package com.robak.android.quickroll.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.robak.android.quickroll.Modifier;
import com.robak.android.quickroll.R;
import com.robak.android.quickroll.tools.FragmentWithTools;
import com.robak.android.quickroll.tools.ItemSelectedAdapter;
import com.robak.android.quickroll.tools.ObservableModifier;
import com.robak.android.quickroll.tools.TextChangedAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MainTable extends FragmentWithTools {
    private ObservableModifier viewModel;

    Modifier modifier = new Modifier();
    Fragment modifierFragment;

    public MainTable(Fragment _modifierFragment) {
        modifierFragment = _modifierFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main_table, container, false);
        createFragment(modifierFragment, R.id.modifiersFrame);
        return view;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ObservableModifier.class);
        viewModel.observe(this, modifier -> updateModifier());

        EditText valueField = view.findViewById(R.id.value_field);
        valueField.addTextChangedListener((TextChangedAdapter) (s, start, before, count) -> parseRoll());
        valueField.setOnLongClickListener(this::clearEditView);

        EditText rollField = view.findViewById(R.id.roll_field);
        rollField.addTextChangedListener((TextChangedAdapter) (s, start, before, count) -> parseRoll());
        rollField.setOnLongClickListener(this::clearEditView);

        Spinner spinner = view.findViewById(R.id.advantage_field);
        Integer[] items = IntStream.range(0, 9).boxed().toArray(Integer[]::new);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener((ItemSelectedAdapter) (par, v, pos, id) -> {
            modifier.setAdvantage(items[pos] * 10);
            parseRoll();
        });
    }

    private void parseRoll() {
        try {
            String valueText = ((EditText) view.findViewById(R.id.value_field)).getText().toString();
            int value = Integer.parseInt(valueText);

            String rollText = ((EditText) view.findViewById(R.id.roll_field)).getText().toString();
            int roll = Integer.parseInt(rollText);
            if (roll > 100 || roll < 1) {
                throw new Exception("Invalid roll value");
            }

            calculateResult(value, roll, modifier.getNumericModifier(), modifier.getSLModifier());
        } catch (Exception e) {
            ((EditText) view.findViewById(R.id.result_field)).setText("");
        }
    }
    private void calculateResult(int value, int roll, int modifier, int SLModifier) {
        int PS = (value + modifier) / 10 - roll / 10 + SLModifier;
        boolean success = (roll <= value + modifier);
        boolean doubleValue = (roll / 10) % 100 == roll % 10;

        EditText editText = view.findViewById(R.id.result_field);
        editText.setTextColor(success ? getActivity().getColor(R.color.green) : getActivity().getColor(R.color.red));

        List<String> stringList = new ArrayList<>();
        if (doubleValue) {
            stringList.add(success ? getString(R.string.critical) : getString(R.string.fumbled));
        }
        if (roll <= 5 && roll >= 1)    stringList.add(getString(R.string.auto_success));
        if (roll <= 100 && roll >= 96) stringList.add(getString(R.string.auto_failure));

        Log.d("PS", String.format("%d [%d] / %d [%d]", value, value / 10, roll, roll / 10));
        stringList.add(String.format(getString(R.string.SL), PS));
        editText.setText(String.join(" ", stringList));
    }
    protected void updateModifier() {
        modifier = viewModel.getModifierObject();
        ((EditText) view.findViewById(R.id.modifier_field)).setText(String.valueOf(modifier.getModifier()));
        ((EditText) view.findViewById(R.id.SL_modifier_field)).setText(String.format(getString(R.string.SL), modifier.getSLModifier()));
        parseRoll();
    }

    private boolean clearEditView(View view) {
        ((EditText) view).setText("");
        return false;
    }
}
