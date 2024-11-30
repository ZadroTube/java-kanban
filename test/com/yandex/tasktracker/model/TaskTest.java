package com.yandex.tasktracker.model;

import com.yandex.tasktracker.service.Managers;
import com.yandex.tasktracker.service.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = Managers.getDefault();
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
    void tasksShouldNotBeEqualIfIdsAreDifferent() {
        Task task1 = new Task("Задача 1", "Описание 1");
        Task task2 = new Task("Задача 2", "Описание 2");

        task1.setId(1);
        task2.setId(2);

        assertNotEquals(task1, task2, "Задачи с разными идентификаторами должны быть не равны");
    }
}
