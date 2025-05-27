package com.example.demo.foundation.enums;

public enum PermissionsTypes {
    C("CREATE"),
    U("UPDATE"),
    R("READ"),
    D("DELETE");

    PermissionsTypes(String value) {
        this.value = value;
    }

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

}
