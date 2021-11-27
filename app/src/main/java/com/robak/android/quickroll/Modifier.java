package com.robak.android.quickroll;

public class Modifier {
    private int modifier;
    private int SLModifier;
    private int advantage;

    public Modifier() {
        modifier = 0;
        SLModifier = 0;
        advantage = 0;
    }

    public int getModifier() {
        return modifier;
    }
    public void setModifier(int modifier) {
        this.modifier = modifier;
    }
    public int getSLModifier() {
        return SLModifier;
    }
    public void setSLModifier(int SLModifier) {
        this.SLModifier = SLModifier;
    }
    public int getAdvantage() {
        return advantage;
    }
    public void setAdvantage(int advantage) {
        this.advantage = advantage;
    }
    public int getNumericModifier() {
        return advantage + modifier;
    }
}
