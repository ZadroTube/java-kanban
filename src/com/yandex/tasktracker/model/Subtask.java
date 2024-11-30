package com.yandex.tasktracker.model;

public class Subtask extends Task {
    private final int parentEpicId;

    public Subtask(String name, String description, int parentEpicId) {
        super(name, description);
        this.parentEpicId = parentEpicId;
    }

    public int getEpicId() {
        return parentEpicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                " id=" + id +
                ", parentEpicId=" + parentEpicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
