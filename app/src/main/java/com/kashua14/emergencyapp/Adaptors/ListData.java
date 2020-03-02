package com.kashua14.emergencyapp.Adaptors;

import androidx.annotation.NonNull;

public class ListData {
    private String name, phone, relationship;

    public ListData(String name, String phone, String relationship) {
        this.setName(name);
        this.setPhone(phone);
        this.setRelationship(relationship);
    }

    String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    String getPhone() {
        return phone;
    }

    private void setPhone(String phone) {
        this.phone = phone;
    }

    String getRelationship() {
        return relationship;
    }

    private void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @NonNull
    @Override
    public String toString() {
        return "[" + name + ", " + phone + ", " + relationship + "]";
    }
}
