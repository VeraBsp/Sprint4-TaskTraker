import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {
    TaskManager manager;
    Task task1;
    Task task2;
    Task task3;
    HistoryManager historyManager;
    @BeforeEach
    public void initData() {
        manager = Managers.getDefault();
        historyManager = new InMemoryHistoryManager();
        task1 = new Task("task1", "Забрать заказ №1", TaskType.TASK);
        task2 = new Task("task2", "Забрать заказ №2", TaskType.TASK);
        task3 = new Task("task3", "Забрать заказ №3", TaskType.TASK);
    }

    @Test
    void addTaskInHistoryTest() {
        manager.createTask(task1);
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "В истории должна быть одна задача");
    }

    @Test
    void addingTaskWithTheSameIDToHistoryTest() {
        manager.createTask(task1);
        manager.getHistoryManager().add(task1);
        manager.getHistoryManager().add(task1);
        final List<Task> history = manager.getHistoryManager().getHistory();
       assertEquals(1, history.size(), "В истории должна быть одна задача");
    }

    @Test
    void removeSingleTaskInHistoryTest() {
        manager.createTask(task1);
        historyManager.add(task1);
        manager.getTask(task1.getId());
        historyManager.remove(task1.getId());
        final List<Task> history = historyManager.getHistory();
        assertTrue(history.isEmpty(), "История должна быть пустой");
    }

    @Test
    void removeFirstTaskOfHistoryTest() {
        manager.createTask(task1);
        historyManager.add(task1);
        manager.createTask(task2);
        historyManager.add(task2);
        manager.createTask(task3);
        historyManager.add(task3);
        historyManager.remove(task1.getId());
        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать две задачи");
        assertFalse(history.contains(task1), "Первая задача должна быть удалена");
        assertTrue(history.contains(task2), "Вторая задача должна быть сохранена");
        assertTrue(history.contains(task3), "Третья задача должна быть сохранена");
    }

    @Test
    void removeTaskFromTheMiddleOfHistoryTest() {
        manager.createTask(task1);
        historyManager.add(task1);
        manager.createTask(task2);
        historyManager.add(task2);
        manager.createTask(task3);
        historyManager.add(task3);
        historyManager.remove(task2.getId());
        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать две задачи");
        assertTrue(history.contains(task1), "Первая задача должна быть сохранена");
        assertFalse(history.contains(task2), "Вторая задача должна быть удалена");
        assertTrue(history.contains(task3), "Третья задача должна быть сохранена");
    }

    @Test
    void removeTaskFromTheEndOfHistoryTest() {
        manager.createTask(task1);
        historyManager.add(task1);
        manager.createTask(task2);
        historyManager.add(task2);
        manager.createTask(task3);
        historyManager.add(task3);
        historyManager.remove(task3.getId());
        List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать две задачи");
        assertTrue(history.contains(task1), "Первая задача должна быть сохранена");
        assertTrue(history.contains(task2), "Вторая задача должна быть сохранена");
        assertFalse(history.contains(task3), "Третья задача должна быть удалена");
    }
}
