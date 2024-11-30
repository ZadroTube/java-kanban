package com.yandex.tasktracker.service;

import com.yandex.tasktracker.model.Epic;
import com.yandex.tasktracker.model.Subtask;
import com.yandex.tasktracker.model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    int addTask(Task task);
    int addEpic(Epic epic);
    int addSubtask(Subtask subtask);

    boolean updateTask(Task updatedTask);
    boolean updateSubtask(Subtask updatedSubtask);
    boolean updateEpic(Epic updatedEpic);

    Task getTaskById(int id);
    Epic getEpicById(int id);
    Subtask getSubtaskById(int id);

    ArrayList<Subtask> getSubtasksByEpic(int epicId);
    ArrayList<Task> getAllTasks();
    ArrayList<Epic> getAllEpics();
    ArrayList<Subtask> getAllSubtasks();

    void deleteAllTasks();
    void deleteAllEpics();
    void deleteAllSubtasks();
    void deleteTaskById(int id);
    void deleteEpicById(int id);
    void deleteSubtaskById(int id);

    List<Task> getHistory();
}

