package com.yandex.tasktracker.service;

import com.yandex.tasktracker.model.Task;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int HISTORY_LIMIT = 10;
    private final LinkedList<Task> history = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        if (!history.isEmpty() && history.getLast().equals(task)) {
            return; // Если задача уже последняя в истории, не добавляем её снова
        }
        history.remove(task); // Удаляем дубликат
        if (history.size() == HISTORY_LIMIT) {
            history.removeFirst(); // Удаляем самый старый элемент
        }
        history.addLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }
}