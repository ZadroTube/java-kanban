package com.yandex.tasktracker.model;

import com.yandex.tasktracker.service.Managers;
import com.yandex.tasktracker.service.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = Managers.getDefault();
    }

    @Test
    void subtasksShouldBeEqualIfIdsAreEqual() {
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание 1", 1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание 2", 1);

        subtask1.setId(1);
        subtask2.setId(1);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковыми ID должны быть равны.");
    }

    @Test
    void subtasksShouldNotBeEqualIfIdsAreDifferent() {
        Subtask subtask1 = new Subtask("Подзадача 1", "Description 1", 1);
        Subtask subtask2 = new Subtask("Подзадача 2", "Description 2", 1);

        subtask1.setId(1);
        subtask2.setId(2);

        assertNotEquals(subtask1, subtask2, "Подзадача с разными ID не должны быть равны");
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
    void tasksOfDifferentTypesShouldNotBeEqual() {
        Subtask subtask = new Subtask("Подзадача", "описание", 1);
        Epic epic = new Epic("Эпик", "описание");

        subtask.setId(1);
        epic.setId(1);

        assertNotEquals(subtask, epic);
    }
}
