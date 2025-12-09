import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SubTaskTest {
    TaskManager manager;
    Task task;
    Epic epic1;
    Epic epic2;
    Subtask subtask1ForEpic1;
    Subtask subtask2ForEpic1;
    Subtask subtask3ForEpic1;
    HistoryManager historyManager;

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
    }

    @Test
    void Subtask1ForEpic1MustBeRelaterToEpic1(){
        manager.createEpicTask(epic1);
        manager.createSubTask(subtask1ForEpic1, epic1.getId());
        manager.createSubTask(subtask2ForEpic1, epic1.getId());
        int expectedEpicID = epic1.getId();
        int actualEpicID = subtask1ForEpic1.getEpicId();
        int actual2EpicID = subtask2ForEpic1.getEpicId();
        assertEquals(expectedEpicID,actualEpicID);
        assertEquals(expectedEpicID,actual2EpicID);
    }

}
