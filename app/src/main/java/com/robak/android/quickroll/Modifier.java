package com.robak.android.quickroll;

public class Modifier {
    Integer modifier;
    Integer SLModifier;

    public Modifier() {
        modifier = 0;
        SLModifier = 0;
    }

    public Integer getModifier() {
        return modifier;
    }
    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }
    public Integer getSLModifier() {
        return SLModifier;
    }
    public void setSLModifier(Integer SLModifier) {
        this.SLModifier = SLModifier;
    }
}
