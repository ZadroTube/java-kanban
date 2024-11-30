package com.yandex.tasktracker.service;

import com.yandex.tasktracker.model.Task;
import java.util.List;

public interface HistoryManager {
    void add(Task task);
    List<Task> getHistory();
}
