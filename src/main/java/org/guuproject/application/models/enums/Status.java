package org.guuproject.application.models.enums;

public enum Status {
    Student,
    WORKER,
    NONE;


    @Override
    public String toString() {
        return this.name();
    }
}
