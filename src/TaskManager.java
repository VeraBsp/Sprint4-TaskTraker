import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeSet;

public interface TaskManager {
    void createTask(Task task);

    void createEpicTask(Epic epic);

    void createSubTask(Subtask subtask, int epicID);
    void updateTask(Task task);

    void updateSubTask(Subtask subtask);

    void deleteTask(Task task);

    void deleteSubTask(Subtask subtask);

    void deleteEpicTask(Epic epic);

    void clearTask();

    void clearEpicTask(Epic epic);
    Task getTask(int id);

    Epic getEpicTask(int id);

    Subtask getSubTask(int id);

    ArrayList<Task> getAllTask();
    ArrayList<Task> getAllEpics();

    ArrayList<Task> getAllSubtasks();
    HistoryManager getHistoryManager();
   TreeSet<Task> getPrioritizedTasks();

}
