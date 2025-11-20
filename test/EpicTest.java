import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    TaskManager manager;
    Task task;
    Epic epic1;
    Epic epic2;
    Subtask subtask1ForEpic1;
    Subtask subtask2ForEpic1;
    Subtask subtask3ForEpic1;
    HistoryManager historyManager;
    TasksStatus statusNew;
    TasksStatus statusDone;
    TasksStatus statusInProgress;

    @BeforeEach
    public void initData() {
        manager = Managers.getDefault();
        historyManager = new InMemoryHistoryManager();
        task = new Task("task1", "Забрать заказ №125", TaskType.TASK);
        epic1 = new Epic("epic1 ", "EPIC1", TaskType.EPIC);
        subtask1ForEpic1 = new Subtask("subtask1", "subtask1 (относится к epic1)", epic1.getId(), TaskType.SUBTASK);
        subtask2ForEpic1 = new Subtask("subtask2", "subtask2 (относится к epic1)", epic1.getId(), TaskType.SUBTASK);
        subtask3ForEpic1 = new Subtask("subtask3", "subtask3 (относится к epic1)", epic1.getId(), TaskType.SUBTASK);
        epic2 = new Epic("epic2", "EPIC2", TaskType.EPIC);
        statusNew = TasksStatus.NEW;
        statusInProgress = TasksStatus.IN_PROGRESS;
        statusDone = TasksStatus.DONE;
    }
    @BeforeEach
    void initTask() {
        manager.createEpicTask(epic1);
        manager.createSubTask(subtask1ForEpic1, epic1.getId());
        manager.createSubTask(subtask2ForEpic1, epic1.getId());
        manager.createSubTask(subtask3ForEpic1, epic1.getId());
        manager.createEpicTask(epic2);
    }

    @Test
    void createdEpicWithThreeSubtasksShouldHaveStatusNew(){
        subtask1ForEpic1.setStatus(TasksStatus.NEW);
        manager.updateSubTask(subtask1ForEpic1);
        subtask2ForEpic1.setStatus(TasksStatus.NEW);
        manager.updateSubTask(subtask2ForEpic1);
        subtask3ForEpic1.setStatus(TasksStatus.NEW);
        manager.updateSubTask(subtask3ForEpic1);
        assertEquals(statusNew,manager.getEpicTask(epic1.getId()).getStatus());
    }

    @Test
    void createdEpicWithoutSubtaskShouldHaveStatusNew(){
        assertEquals(statusNew,manager.getEpicTask(epic2.getId()).getStatus());
    }

    @Test
    void createdEpicWithThreeSubtasksShouldHaveStatusDone(){
        subtask1ForEpic1.setStatus(TasksStatus.DONE);
        manager.updateSubTask(subtask1ForEpic1);
        subtask2ForEpic1.setStatus(TasksStatus.DONE);
        manager.updateSubTask(subtask2ForEpic1);
        subtask3ForEpic1.setStatus(TasksStatus.DONE);
        manager.updateSubTask(subtask3ForEpic1);
        assertEquals(statusDone,manager.getEpicTask(epic1.getId()).getStatus());
    }

    @Test
    void createdEpicWithThreeSubtasksShouldHaveStatusInProgress1(){
        subtask1ForEpic1.setStatus(TasksStatus.DONE);
        manager.updateSubTask(subtask1ForEpic1);
        subtask2ForEpic1.setStatus(TasksStatus.NEW);
        manager.updateSubTask(subtask2ForEpic1);
        subtask3ForEpic1.setStatus(TasksStatus.DONE);
        manager.updateSubTask(subtask3ForEpic1);
        assertEquals(statusInProgress,manager.getEpicTask(epic1.getId()).getStatus());
    }

    @Test
    void createdEpicWithThreeSubtasksShouldHaveStatusInProgress2(){
        subtask1ForEpic1.setStatus(TasksStatus.NEW);
        manager.updateSubTask(subtask1ForEpic1);
        subtask2ForEpic1.setStatus(TasksStatus.DONE);
        manager.updateSubTask(subtask2ForEpic1);
        subtask3ForEpic1.setStatus(TasksStatus.IN_PROGRESS);
        manager.updateSubTask(subtask3ForEpic1);
        assertEquals(statusInProgress,manager.getEpicTask(epic1.getId()).getStatus());
    }

}