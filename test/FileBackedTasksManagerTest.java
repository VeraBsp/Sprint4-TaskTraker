import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

public class FileBackedTasksManagerTest {
    private FileBackedTasksManager manager;
    private File tempFile;

    @BeforeEach
    void setUp() {
        String fileName = "./resources/testfile.csv";
        tempFile = new File(fileName);
        manager = new FileBackedTasksManager(tempFile);
    }

    @AfterEach
    void clearTestEnvironment() {
        if (!tempFile.delete()) {
            throw new IllegalStateException("Не удалось удалить временный файл: " + tempFile.getAbsolutePath());
        }
    }

    @Test
    void saveAndLoadEmptyListShouldWorkCorrectly() {
        manager.save();
        FileBackedTasksManager loadedManager = manager.loadFromFile(tempFile);
        assertTrue(loadedManager.getAllTask().isEmpty());
        assertTrue(loadedManager.getAllEpics().isEmpty());
        assertTrue(loadedManager.getAllSubtasks().isEmpty());
        assertTrue(loadedManager.getHistoryManager().getHistory().isEmpty());
    }

    @Test
    void saveAndLoadEpicWithoutSubtasksShouldWorkCorrectly() {
        Epic epic = new Epic(1, TaskType.EPIC, "Эпик", "Описание", TasksStatus.NEW);
        manager.createEpicTask(epic);
        manager.getEpicTask(epic.getId());
        manager.save();
        FileBackedTasksManager loadedManager = manager.loadFromFile(tempFile);
        Epic loadedEpic = loadedManager.getEpicTask(epic.getId());
        assertNotNull(loadedEpic);
        assertEquals(epic.getId(), loadedEpic.getId());
        assertEquals(epic.getDescription(), loadedEpic.getDescription());
        assertEquals(epic.getStatus(), loadedEpic.getStatus());
        assertTrue(epic.getSubTaskMap().isEmpty());
    }

    // Тест 3: Пустая история
    @Test
    void saveAndLoadEmptyHistoryShouldWorkCorrectly() {
        Task task = new Task(1, TaskType.TASK, "Задача", "Описание", TasksStatus.NEW);
        manager.createTask(task);
        manager.save();
        FileBackedTasksManager loadedManager = manager.loadFromFile(tempFile);
        assertTrue(loadedManager.getHistoryManager().getHistory().isEmpty());
    }

    @Test
    void saveAndLoadFullCycleShouldWorkCorrectly() {
        Task task = new Task(1, TaskType.TASK, "Задача", "Описание", TasksStatus.NEW);
        Epic epic = new Epic(2, TaskType.EPIC, "Эпик", "Описание эпика", TasksStatus.NEW);
        Subtask subtask = new Subtask(3, TaskType.SUBTASK, "Подзадача",
                "Описание подзадачи", TasksStatus.IN_PROGRESS, 2);
        manager.createTask(task);
        manager.createEpicTask(epic);
        manager.createSubTask(subtask, epic.getId());
        manager.getTask(task.getId());
        manager.save();
        FileBackedTasksManager loadedManager = manager.loadFromFile(tempFile);
        assertEquals(1, loadedManager.getAllTask().size());
        assertEquals(1, loadedManager.getAllEpics().size());
        assertEquals(1, loadedManager.getAllSubtasks().size());
        assertEquals(1, loadedManager.getHistoryManager().getHistory().size());
    }

}
