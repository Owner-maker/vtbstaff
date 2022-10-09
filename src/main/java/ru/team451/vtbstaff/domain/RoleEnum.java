package ru.team451.vtbstaff.domain;

public enum RoleEnum {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_MANAGER("ROLE_MANAGER"),
    ROLE_CREATOR("ROLE_CREATOR"),
    ROLE_HR("ROLE_CREATOR");

    private final String name;
    RoleEnum(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
