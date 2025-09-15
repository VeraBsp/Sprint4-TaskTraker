import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;

    private final HashMap<Integer, Task> simpleTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epicTasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subTasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public InMemoryTaskManager() {

    }

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public void createTask(Task task) {
        id++;
        task.setId(id);
        task.setStatus(TasksStatus.NEW);
        simpleTasks.put(task.getId(), task);
    }

    @Override
    public void createEpicTask(Epic epic) {
        id++;
        epic.setId(id);
        epic.setStatus(TasksStatus.NEW);
        epicTasks.put(epic.getId(), epic);
    }

    @Override
    public void createSubTask(Subtask subtask, int epicID) {
        id++;
        subtask.setId(id);
        subtask.setStatus(TasksStatus.NEW);
        subtask.setEpicId(epicID);
        subTasks.put(subtask.getId(), subtask);
        Epic epicFromEpicTasksMap = epicTasks.get(epicID);
        epicFromEpicTasksMap.putSubtask(subtask);
    }

    @Override
    public void updateTask(Task task) {
        simpleTasks.put(task.getId(), task);
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        updateEpicStatus(subtask);
        subTasks.put(subtask.getId(), subtask);
    }

    @Override
    public void deleteTask(Task task) {
        simpleTasks.remove(task.getId(), task);
    }

    @Override
    public void deleteSubTask(Subtask subtask) {
        subTasks.remove(subtask.getId(), subtask);
        historyManager.remove(subtask.getId());
    }

    @Override
    public void deleteEpicTask(Epic epic) {
        HashMap<Integer, Subtask> tempSubTasks = new HashMap<>();
        tempSubTasks.putAll(subTasks);
        epicTasks.remove(epic.getId(), epic);
        for (Subtask sub : tempSubTasks.values()) {
            if (epic.getId() == sub.getEpicId()) {
                deleteSubTask(sub);
            }
        }
        historyManager.remove(epic.getId());
    }

    @Override
    public void clearTask() {
        simpleTasks.clear();
    }

    @Override
    public void clearEpicTask(Epic epic) {
        epicTasks.clear();
        subTasks.clear();
    }

    @Override
    public Task getTask(int id) {
        Task simpleTask = simpleTasks.get(id);
        historyManager.add(simpleTask);
        return simpleTask;
    }

    @Override
    public Epic getEpicTask(int id) {
        Epic epicTask = epicTasks.get(id);
        historyManager.add(epicTask);
        return epicTask;
    }

    @Override
    public Subtask getSubTask(int id) {
        Subtask subTask = subTasks.get(id);
        historyManager.add(subTask);
        return subTask;
    }

    @Override
    public ArrayList<Task> getAllTask() {
        return new ArrayList<>(simpleTasks.values());
    }

    @Override
    public ArrayList<Task> getAllEpics() {
        return new ArrayList<>(epicTasks.values());
    }

    @Override
    public ArrayList<Task> getAllSubtasks() {
        return new ArrayList<>(subTasks.values());
    }

    private void updateEpicStatus(Subtask subtask) {
        Epic epic = epicTasks.get(subtask.getEpicId());
        int allDone = 0;

        TasksStatus statusInProgress = TasksStatus.IN_PROGRESS;
        TasksStatus statusDone = TasksStatus.DONE;
        HashMap<Integer, Subtask> subTaskMap = epic.getSubTaskMap();
        for (Subtask sub : subTaskMap.values()) {
            TasksStatus epicStatus = sub.getStatus();
            if (statusInProgress.equals(epicStatus)) {
                epic.setStatus(TasksStatus.IN_PROGRESS);
                break;
            }
            if ((epic.getSubTaskMap() != null) && statusDone.equals(epicStatus)) {
                allDone++;
            }
        }
        if (allDone == subTaskMap.size()) {
            epic.setStatus(TasksStatus.DONE);
        } else if (allDone > 0) {
            epic.setStatus(TasksStatus.IN_PROGRESS);
        }
        epicTasks.put(epic.getId(), epic);
    }
}
