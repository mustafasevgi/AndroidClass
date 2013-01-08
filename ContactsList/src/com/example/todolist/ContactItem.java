package com.example.todolist;

public class ContactItem {
    private final String mDisplayName;

    public String getDisplayName() {
        return mDisplayName;
    }

    public ContactItem(String displayName) {
        mDisplayName = displayName;
    }

    @Override
    public String toString() {
        return mDisplayName;
    }
}