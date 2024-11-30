// Вместо мэйна используем тесты

/*package com.yandex.tasktracker;

import com.yandex.tasktracker.model.*;
import com.yandex.tasktracker.service.Managers;
import com.yandex.tasktracker.service.TaskManager;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        // Создаем 2 простые задачи
        Task task1 = new Task("Задача №1", "Описание задачи №1");
        Task task2 = new Task("Задача №2", "Описание задачи №2");
        int task1Id = taskManager.addTask(task1);
        int task2Id = taskManager.addTask(task2);

        // Создаем 2 эпика и 2 подзадачи к 1ому, 1 подзадачу ко 2му
        Epic epic1 = new Epic("Эпик №1", "Описание эпика №1");
        Epic epic2 = new Epic("Эпик №2", "Описание эпика №2");
        int epic1Id = taskManager.addEpic(epic1);
        int epic2Id = taskManager.addEpic(epic2);

        // и создаем 2 подзадачи к 1ому эпику, 1 подзадачу ко 2му
        Subtask subtask1 = new Subtask("Подзадача №1", "Описание подзадачи №1", epic1Id);
        Subtask subtask2 = new Subtask("Подзадача №2", "Описание подзадачи №2", epic1Id);
        Subtask subtask3 = new Subtask("Подзадача №3", "Описание подзадачи №3", epic2Id);

        int subtask1Id = taskManager.addSubtask(subtask1);
        int subtask2Id = taskManager.addSubtask(subtask2);
        int subtask3Id = taskManager.addSubtask(subtask3);

        // Вывод списков эпиков, задач и подзадач
        System.out.println("Список всех задач: " + taskManager.getAllTasks());
        System.out.println("Список всех эпиков: " + taskManager.getAllEpics());
        System.out.println("Список всех подзадач: " + taskManager.getAllSubtasks());

        // Изменение статусов и вывод обновленных значений
        task1.setStatus(Status.DONE);
        task2.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task1);
        taskManager.updateTask(task2);

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.IN_PROGRESS);
        subtask3.setStatus(Status.DONE);
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        taskManager.updateSubtask(subtask3);

        System.out.println("Обновленные задачи: ");
        System.out.println(taskManager.getTaskById(task1Id));
        System.out.println(taskManager.getTaskById(task2Id));

        System.out.println("Обновленные эпики: ");
        System.out.println(taskManager.getEpicById(epic1Id)); // Статус эпика с 2 подзадачами
        System.out.println(taskManager.getEpicById(epic2Id)); // Статус эпика с 1

        // Удаляем одну задачу и один эпик, выводим обновленные списки
        taskManager.deleteTaskById(task1Id);
        taskManager.deleteEpicById(epic1Id);

        System.out.println("После удаления: ");
        System.out.println("Все задачи: " + taskManager.getAllTasks());
        System.out.println("Все эпики: " + taskManager.getAllEpics());
        System.out.println("Все подзадачи: " + taskManager.getAllSubtasks());
    }
}*/

