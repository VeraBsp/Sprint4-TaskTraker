import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

public class FileBackedTasksManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    String fileName = "./resources/data/testfile.csv";
    File file = new File(fileName);
    private FileBackedTasksManager fileBackedTasksManager ;


    @BeforeEach
    void setUp() {
        manager = new InMemoryTaskManager();
        fileBackedTasksManager = new FileBackedTasksManager(file);
    }

    @AfterEach
    void clearTestEnvironment() {
        try {
            Files.delete(file.toPath());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void saveAndLoadEmptyListShouldWorkCorrectly() {
        fileBackedTasksManager.save();
        FileBackedTasksManager loadedManager = fileBackedTasksManager.loadFromFile(file);
        assertTrue(loadedManager.getAllTask().isEmpty());
        assertTrue(loadedManager.getAllEpics().isEmpty());
        assertTrue(loadedManager.getAllSubtasks().isEmpty());
        assertTrue(loadedManager.getHistoryManager().getHistory().isEmpty());
    }

    @Test
    void saveAndLoadEpicWithoutSubtasksShouldWorkCorrectly() {
        Epic epic = new Epic(TaskType.EPIC, "Эпик", "Описание", TasksStatus.NEW);
        System.out.println("До создания: " + epic);
        fileBackedTasksManager.createEpicTask(epic);
        System.out.println("После установки ID: " + epic);
        System.out.println("После создания: " + epic);
        fileBackedTasksManager.getEpicTask(1);
        System.out.println("Файл существует после сохранения: " + file.exists());
        try {
            System.out.println("Содержимое файла:");
            Files.readAllLines(file.toPath()).forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        FileBackedTasksManager loadedManager = fileBackedTasksManager.loadFromFile(file);
        System.out.println("Загруженный менеджер не null: " + (loadedManager != null));
        Epic loadedEpic = loadedManager.getEpicTask(1);
        System.out.println("Загруженный эпик: " + loadedEpic);
        assertNotNull(loadedEpic);
        assertEquals(epic.getId(), loadedEpic.getId());
        assertEquals(epic.getDescription(), loadedEpic.getDescription());
        assertEquals(epic.getStatus(), loadedEpic.getStatus());
        assertTrue(epic.getSubTaskMap().isEmpty());
    }

    @Test
    void saveAndLoadEmptyHistoryShouldWorkCorrectly() {
        Task task = new Task(1, TaskType.TASK, "Задача", "Описание", TasksStatus.NEW);
        task.setStartTime((LocalDateTime.of(2025, 11, 24, 15, 00)));
        task.setDuration(25);
        fileBackedTasksManager.createTask(task);
        fileBackedTasksManager.save();
        FileBackedTasksManager loadedManager = fileBackedTasksManager.loadFromFile(file);
        assertTrue(loadedManager.getHistoryManager().getHistory().isEmpty());
    }

    @Test
    void saveAndLoadFullCycleShouldWorkCorrectly() {
        Task task = new Task(TaskType.TASK, "Задача", "Описание", TasksStatus.NEW,LocalDateTime.of(2025, 11, 24, 15, 00), 15);
        Epic epic = new Epic(TaskType.EPIC, "Эпик", "Описание эпика", TasksStatus.NEW);
        Subtask subtask = new Subtask(TaskType.SUBTASK, "Подзадача",
                "Описание подзадачи", TasksStatus.IN_PROGRESS, LocalDateTime.of(2025, 1, 1, 9, 00), 20,2);
        fileBackedTasksManager.createTask(task);
        fileBackedTasksManager.createEpicTask(epic);
        fileBackedTasksManager.createSubTask(subtask, epic.getId());
        fileBackedTasksManager.getTask(task.getId());
        fileBackedTasksManager.getEpicTask(epic.getId());
        fileBackedTasksManager.save();
        FileBackedTasksManager loadedManager = fileBackedTasksManager.loadFromFile(file);
        assertEquals(1, loadedManager.getAllTask().size());
        assertEquals(1, loadedManager.getAllEpics().size());
        assertEquals(1, loadedManager.getAllSubtasks().size());
        assertEquals(2, loadedManager.getHistoryManager().getHistory().size());
    }

    @Test
    void endTimeEpicShouldBeEqual2025_1_2_12_20(){
        LocalDateTime expectedEndTime = LocalDateTime.of(2025, 2, 1, 12, 20);
        LocalDateTime expectedStartTime = LocalDateTime.of(2025, 1, 1, 10, 00);
        Epic epic = new Epic(TaskType.EPIC, "Эпик", "Описание эпика", TasksStatus.NEW);
        Subtask subtask = new Subtask(TaskType.SUBTASK, "Подзадача",
                "Описание подзадачи", TasksStatus.IN_PROGRESS, LocalDateTime.of(2025, 2, 1, 12, 00), 20,2);
        Subtask subtask2 = new Subtask(TaskType.SUBTASK, "Подзадача2",
                "Описание подзадачи", TasksStatus.IN_PROGRESS, LocalDateTime.of(2025, 1, 1, 10, 00), 21,2);
        fileBackedTasksManager.createEpicTask(epic);
        fileBackedTasksManager.createSubTask(subtask, epic.getId());
        fileBackedTasksManager.createSubTask(subtask2, epic.getId());
        assertEquals(expectedEndTime,fileBackedTasksManager.getEpicTask(epic.getId()).getEndTimeEpicTask());
        assertEquals(expectedStartTime,fileBackedTasksManager.getEpicTask(epic.getId()).getStartTime());
    }

    @Test
    void overlapInTimeTaskTest() {
        Task task = new Task(TaskType.TASK, "Задача", "Описание", TasksStatus.NEW,
                LocalDateTime.of(2025, 1, 1, 9, 5), 15);
        fileBackedTasksManager.addPrioritizedTasks(task);

        Task overlappingTask = new Task(TaskType.TASK, "Пересекающаяся задача", "Описание", TasksStatus.NEW,
                LocalDateTime.of(2025, 1, 1, 9, 0), 20);

        assertThrows(IllegalArgumentException.class,
                () -> fileBackedTasksManager.overlapInTimeTask(overlappingTask));
    }
}
