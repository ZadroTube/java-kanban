package com.yandex.tasktracker.service;

import com.yandex.tasktracker.model.*; // Добавил иерархию пакетов

import java.util.ArrayList;
import java.util.HashMap;

// убраны все static модификаторы так как мы создаем объект таск-менеджера и через него и его методы управляем
public class TaskManager {
    private int identificator = 0; // поле стало private
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    private int getNewId() {
        return ++identificator;
    }

    //add методы теперь возвращают id добавленных собою задач
    public int addTask(Task task) {
        task.setId(getNewId());
        tasks.put(task.getId(), task);
        return task.getId();
    }

    public int addEpic(Epic epic) {
        epic.setId(getNewId());
        epics.put(epic.getId(), epic);
        return epic.getId();
    }

    public int addSubtask(Subtask subtask) {
        if (!epics.containsKey(subtask.getParentEpicId())) {
            return -1;
        }
        subtask.setId(getNewId());
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getParentEpicId()).addSubtask(subtask);
        updateEpicStatus(epics.get(subtask.getParentEpicId()));
        return subtask.getId();
    }

    // Добавил отдельные методы обновления задач, эпиков и подзадач с проверками и выходом из методов
    public boolean updateTask(Task updatedTask) {
        if (!tasks.containsKey(updatedTask.getId())) {
            return false;
        }
        Task existingTask = tasks.get(updatedTask.getId());
        existingTask.setName(updatedTask.getName());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setStatus(updatedTask.getStatus());
        return true;
    }

    public boolean updateEpic(Epic updatedEpic) {
        if (!epics.containsKey(updatedEpic.getId())) {
            return false;
        }
        Epic existingEpic = epics.get(updatedEpic.getId());
        existingEpic.setName(updatedEpic.getName());
        existingEpic.setDescription(updatedEpic.getDescription());
        // Статус рассчитывается на основе подзадач
        updateEpicStatus(existingEpic);
        return true;
    }

    public boolean updateSubtask(Subtask updatedSubtask) {
        if (!subtasks.containsKey(updatedSubtask.getId())) {
            return false;
        }
        Subtask existingSubtask = subtasks.get(updatedSubtask.getId());
        existingSubtask.setName(updatedSubtask.getName());
        existingSubtask.setDescription(updatedSubtask.getDescription());
        existingSubtask.setStatus(updatedSubtask.getStatus());

        Epic parentEpic = epics.get(existingSubtask.getParentEpicId());
        updateEpicStatus(parentEpic); // Обновляем статус эпика, если подзадача изменилась
        return true;
    }

    // Остальное в TaskManager без изменений

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public ArrayList<Subtask> getSubtasksByEpic(int epicId) {
        Epic epic = epics.get(epicId);
        return (epic != null) ? epic.getSubtasks() : new ArrayList<>();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtasks();
            updateEpicStatus(epic);
        }
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    public void deleteEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (Subtask subtask : epic.getSubtasks()) {
                subtasks.remove(subtask.getId());
            }
        }
    }

    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic parentEpic = epics.get(subtask.getParentEpicId());
            if (parentEpic != null) {
                parentEpic.removeSubtask(subtask);
                updateEpicStatus(parentEpic);
            }
        }
    }

    private void updateEpicStatus(Epic epic) {
        if (epic.getSubtasks().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allDone = true;
        boolean allNew = true;

        for (Subtask subtask : epic.getSubtasks()) {
            if (subtask.getStatus() != Status.DONE) {
                allDone = false;
            }
            if (subtask.getStatus() != Status.NEW) {
                allNew = false;
            }
        }

        if (allDone) {
            epic.setStatus(Status.DONE);
        } else if (allNew) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }
}
