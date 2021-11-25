package com.robak.android.quickroll.tools;

import android.text.Editable;
import android.text.TextWatcher;


public interface TextChangedAdapter extends TextWatcher {
    void change(CharSequence s, int start, int before, int count);

    @Override
    default void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // Ignored
    }

    @Override
    default void onTextChanged(CharSequence s, int start, int before, int count) {
        change(s, start, before, count);
    }

    @Override
    default void afterTextChanged(Editable s) {
        // Ignored
    }
}
