import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest <T extends  TaskManager>{
    TaskManager manager;
    Task task;
    Epic epic1;
    Subtask subtask1;
    Subtask subtask2;
    HistoryManager historyManager;
    @BeforeEach
    public void initData() {
        manager = Managers.getDefault();
        historyManager = new InMemoryHistoryManager();
        task = new Task("task1", "Забрать заказ №125", TaskType.TASK);
        epic1 = new Epic("epic1 ", "купить дом. должен быть удобный и красивый", TaskType.EPIC);
        subtask1 = new Subtask("subtask1 (относится к epic1)", "посмотреть объявления о продаже", epic1.getId(), TaskType.SUBTASK);
        subtask2 = new Subtask("subtask2 (относится к epic1)", "выбрать расположение дома", epic1.getId(), TaskType.SUBTASK);
    }

    @Test
    void createTaskTest(){
        manager.createTask(task);
        assertEquals(task,manager.getTask(task.getId()));
    }

    @Test
    void createTaskNegativeTest(){
        manager.createTask(task);
        Task expectedTask = task;
        Task actualTask = manager.getTask(22);
       // assertNull(actualTask,"Задачa найдена");
        assertNull(actualTask, "Задачи не совпадают.");
      //  assertNotNull(expectedTask, "Задача не найдена.");
        final List<Task> tasks = manager.getAllTask();
        assertEquals(1, tasks.size(), "Неверное количество задач.");

    }
}
