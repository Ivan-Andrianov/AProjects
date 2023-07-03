package org.guuproject.application.models.enums;

public enum Status {
    STUDENT,
    WORKER,
    NONE;


    @Override
    public String toString() {
        return this.name();
    }
}
