package com.mycompany.clinic.entity;

public enum Priority {
    NORMAL("Нормальный"), CITO("Срочный"), STAIM("Немедленный");
    private String alias;

    Priority(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String toString() {
        return alias;
    }
}
