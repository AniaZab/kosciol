package com.apka.kosciol.entity;

public enum RecipientCategory {

    INNE("Inne"),
    MŁODZIEŻ("Młodzież"),
    DZIECI("Dzieci"),
    DOROŚLI("Dorośli"),
    WSZYSCY("Wszyscy"),
    LIDERZY("Liderzy"),
    MAŁŻEŃSTWA("Małżeństwa"),
    RODZINY("Rodziny"),
    SINGLE("Single");

    private final String displayValue;

    private RecipientCategory(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
