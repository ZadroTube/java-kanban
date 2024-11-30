package com.yandex.tasktracker.service;

import com.yandex.tasktracker.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTaskManagerTest {
    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = Managers.getDefault();
    }

    @Test
    void shouldAddTasksAndFindById() {
        Task task = new Task("Задача 1", "Описание 1");
        int taskId = manager.addTask(task);
        Task savedTask = manager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не сохранена");
        assertEquals("Задача 1", savedTask.getName(), "Имя не совпадает");
    }

    @Test
    void shouldAddEpicAndSubtasks() {
        Epic epic = new Epic("Эпик 1", "Описание эпика");
        int epicId = manager.addEpic(epic);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", epicId);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", epicId);
        int subtask1Id = manager.addSubtask(subtask1);
        int subtask2Id = manager.addSubtask(subtask2);

        List<Subtask> subtasks = manager.getSubtasksByEpic(epicId);

        assertNotNull(manager.getEpicById(epicId), "Эпик не сохранен");
        assertEquals(2, subtasks.size(), "У эпика должно быть 2 задачи");
    }

    @Test
    void shouldUpdateTask() {
        Task task = new Task("Задача 1", "Описание 1");
        int taskId = manager.addTask(task);

        // Изменяем поля задачи
        Task updatedTask = manager.getTaskById(taskId);
        updatedTask.setName("Обновленная задача");
        updatedTask.setDescription("Обновленное описание");
        updatedTask.setStatus(Status.IN_PROGRESS);

        // Добавляем задачу обратно в менеджер
        manager.addTask(updatedTask);

        // Проверяем изменения
        Task savedTask = manager.getTaskById(taskId);
        assertNotNull(savedTask);
        assertEquals("Обновленная задача", savedTask.getName());
        assertEquals("Обновленное описание", savedTask.getDescription());
        assertEquals(Status.IN_PROGRESS, savedTask.getStatus());
    }

    @Test
    void shouldUpdateEpic() {
        Epic epic = new Epic("Эпик 1", "Описание 1");
        int epicId = manager.addEpic(epic);

        // Изменяем поля эпика
        Epic updatedEpic = manager.getEpicById(epicId);
        updatedEpic.setName("Обновленный эпик");
        updatedEpic.setDescription("Обновленное описание");

        // Добавляем эпик обратно в менеджер
        manager.addEpic(updatedEpic);

        // Проверяем изменения
        Epic savedEpic = manager.getEpicById(epicId);
        assertNotNull(savedEpic, "Эпик должен существовать");
        assertEquals("Обновленный эпик", savedEpic.getName(), "имя эпика должно обновиться");
        assertEquals("Обновленное описание", savedEpic.getDescription(), "описание должно обновиться");
    }

    @Test
    void shouldUpdateSubtask() {
        Epic epic = new Epic("Эпик 1", "Описание 1");
        int epicId = manager.addEpic(epic);

        Subtask subtask = new Subtask("Подзадача 1", "описание", epicId);
        int subtaskId = manager.addSubtask(subtask);

        // Изменяем поля подзадачи
        Subtask updatedSubtask = manager.getSubtaskById(subtaskId);
        updatedSubtask.setName("подзадача обновлена");
        updatedSubtask.setDescription("новое описание");
        updatedSubtask.setStatus(Status.DONE);

        // Добавляем подзадачу обратно в менеджер
        manager.addSubtask(updatedSubtask);

        // Проверяем изменения
        Subtask savedSubtask = manager.getSubtaskById(subtaskId);
        assertNotNull(savedSubtask, "подзадача должна быть");
        assertEquals("подзадача обновлена", savedSubtask.getName());
        assertEquals("новое описание", savedSubtask.getDescription());
        assertEquals(Status.DONE, savedSubtask.getStatus());
    }

    @Test
    void tasksShouldBeEqualIfIdsAreEqual() {
        Task task1 = new Task("Задача 1", "Описание 1");
        Task task2 = new Task("Задача 2", "Описание 2");

        task1.setId(1);
        task2.setId(1);

        assertEquals(task1, task2);
    }

    @Test
    void subtasksAndEpicsShouldNotBeEqualEvenWithSameIds() {
        // Создаём подзадачу и эпик с одинаковыми ID
        Subtask subtask = new Subtask("Подзадача", "описание", 1);
        Epic epic = new Epic("Эпик", "описание");

        subtask.setId(1);
        epic.setId(1);

        // Проверяем, что они не равны
        assertNotEquals(subtask, epic);
    }

    @Test
    void shouldNotAllowSubtaskForNonExistentEpic() {
        Subtask subtask = new Subtask("Подзадача 1", "описание", 999); // Невалидный ID эпика

        int result = manager.addSubtask(subtask);

        assertEquals(-1, result);
    }

    @Test
    void shouldAddSubtaskCorrectly() {
        Epic epic = new Epic("Эпик 1", "описание");
        int epicId = manager.addEpic(epic);

        Subtask subtask = new Subtask("Подзадача 1", "описание", epicId);
        int subtaskId = manager.addSubtask(subtask);

        assertTrue(subtaskId > 0);
        Subtask savedSubtask = manager.getSubtaskById(subtaskId);
        assertNotNull(savedSubtask);
        assertEquals(subtask.getName(), savedSubtask.getName());
        assertEquals(subtask.getEpicId(), savedSubtask.getEpicId());
    }

    @Test
    void shouldReturnInitializedManagers() {
        TaskManager taskManager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        assertNotNull(taskManager);
        assertNotNull(historyManager);
    }

    @Test
    void shouldMaintainCorrectHistory() {
        Task task = new Task("Задача 1", "Описание 1");
        int taskId = manager.addTask(task);

        manager.getTaskById(taskId);
        List<Task> history = manager.getHistory();

        assertEquals(1, history.size());
        assertEquals("Задача 1", history.get(0).getName());
    }

    @Test
    void shouldUpdateEpicStatusWhenSubtasksChange() {
        Epic epic = new Epic("Эпик 1", "Эпика описание");
        int epicId = manager.addEpic(epic);

        Subtask subtask1 = new Subtask("Подзадача 1", "Описание 1", epicId);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание 2", epicId);
        int subtask1Id = manager.addSubtask(subtask1);
        int subtask2Id = manager.addSubtask(subtask2);

        // Проверяем начальный статус эпика
        Epic savedEpic = manager.getEpicById(epicId);
        assertEquals(Status.NEW, savedEpic.getStatus());

        // Изменяем статус подзадачи
        Subtask updatedSubtask1 = manager.getSubtaskById(subtask1Id);
        updatedSubtask1.setStatus(Status.IN_PROGRESS);
        manager.addSubtask(updatedSubtask1);

        // Проверяем, что статус эпика обновился
        savedEpic = manager.getEpicById(epicId);
        assertEquals(Status.IN_PROGRESS, savedEpic.getStatus());

        // Изменяем статус всех подзадач на DONE
        Subtask updatedSubtask2 = manager.getSubtaskById(subtask2Id);
        updatedSubtask1.setStatus(Status.DONE);
        updatedSubtask2.setStatus(Status.DONE);
        manager.addSubtask(updatedSubtask1);
        manager.addSubtask(updatedSubtask2);

        // Проверяем, что статус эпика обновился на DONE
        savedEpic = manager.getEpicById(epicId);
        assertEquals(Status.DONE, savedEpic.getStatus());
    }

    @Test
    void shouldNotModifyTaskWhenAddingToManager() {
        Task task = new Task("Задача 1", "Описание 1");
        task.setId(1);

        manager.addTask(task);

        Task savedTask = manager.getTaskById(task.getId());

        assertEquals(task.getName(), savedTask.getName());
        assertEquals(task.getDescription(), savedTask.getDescription());
        assertEquals(task.getStatus(), savedTask.getStatus());
    }

    @Test
    void tasksOfDifferentTypesShouldNotBeEqual() {
        Subtask subtask = new Subtask("Подзадача", "описание", 1);
        Epic epic = new Epic("Эпик", "описание");

        subtask.setId(1);
        epic.setId(1);

        assertNotEquals(subtask, epic);
    }
}
