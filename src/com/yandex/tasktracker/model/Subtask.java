package com.yandex.tasktracker.model;

public class Subtask extends Task {
    private final int parentEpicId; // id эпика родителя теперь int

    public Subtask(String name, String description, int parentEpicId) {
        super(name, description);
        this.parentEpicId = parentEpicId;
    }

    public int getParentEpicId() {
        return parentEpicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                " id=" + id + // убран геттер
                ", parentEpicId=" + parentEpicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
