package com.example.todolist;

public class ToDoItem {
    private final String mTask;

    public String getTask() {
        return mTask;
    }

    public ToDoItem(String task) {
        mTask = task;
    }

    @Override
    public String toString() {
        return mTask;
    }
}