package com.apka.kosciol.entity;

public enum MeetingCategory {
    MODLITWA("Modlitwa"),
    UWIELBIENIE("Uwielbienie"),
    INTEGRACJA("Integracja"),
    STUDIUM("Studium biblijne"),
    KONFERENCJA("Konferencja"),
    OBÓZ("Obóz"),
    INNE("Inne");

    private final String displayValue;

    private MeetingCategory(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
