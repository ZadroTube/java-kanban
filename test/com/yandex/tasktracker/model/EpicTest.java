package com.yandex.tasktracker.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.yandex.tasktracker.service.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = Managers.getDefault();
    }

    @Test
    void epicsShouldBeEqualIfIdsAreEqual() {
        Epic epic1 = new Epic("Эпик 1", "описание 1");
        Epic epic2 = new Epic("Эпик 2", "описание 2");

        epic1.setId(1);
        epic2.setId(1);

        assertEquals(epic1, epic2, "Эпики с одинаковыми ID должны быть равны.");
    }

    @Test
    void epicsShouldNotBeEqualIfIdsAreDifferent() {
        Epic epic1 = new Epic("Эпик 1", "описание 1");
        Epic epic2 = new Epic("Эпик 2", "описание 2");

        epic1.setId(1);
        epic2.setId(2);

        assertNotEquals(epic1, epic2, "Эпики с разными  ID не должны быть равны.");
    }
}
