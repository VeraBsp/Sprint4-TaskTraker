import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {
    private int id = 0;

    private final HashMap<Integer, Task> simpleTasks = new HashMap<>();
    private final HashMap<Integer, Epic> epicTasks = new HashMap<>();
    private final HashMap<Integer, Subtask> subTasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    private final  TreeSet<Task> prioritizedTasks = new TreeSet<>();
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
        overlapInTimeTask(task);
        task.setStatus(TasksStatus.NEW);
        simpleTasks.put(task.getId(), task);
        addPrioritizedTasks(task);
    }

    public void overlapInTimeTask(Task task){
        if (task == null) {
            return;
        }
        LocalDateTime start = task.getStartTime();
        Integer duration = task.getDuration();
        if (start == null || duration == null || duration <= 0) {
            return;
        }
        LocalDateTime end = start.plusMinutes(duration);
        for (Task other : prioritizedTasks) {
            if (other == null || other.equals(task) || other.getTaskType()==TaskType.EPIC) {
                continue;
            }
            LocalDateTime otherStart = other.getStartTime();
            Integer otherDuration = other.getDuration();
            if (otherStart == null || otherDuration == null || otherDuration <= 0) {
                continue;
            }
            LocalDateTime otherEnd = otherStart.plusMinutes(otherDuration);
            boolean intersects = !start.isAfter(otherEnd) && !end.isBefore(otherStart);
            if (intersects) {
                String message = String.format(
                        "Вам нужно выбрать другое время для выполнения задачи %s — " +
                                "она пересекается по времени выполнения с задачей %s.%n",
                        task.getTitle(), other.getTitle());
                throw new IllegalArgumentException(message);
            }
        }
    }

    @Override
    public void createEpicTask(Epic epic) {
        id++;
        epic.setId(id);
        epic.setStatus(TasksStatus.NEW);
        epicTasks.put(epic.getId(), epic);
        addPrioritizedTasks(epic);
    }

    @Override
    public void createSubTask(Subtask subtask, int epicID) {
        id++;
        subtask.setId(id);
        subtask.setStatus(TasksStatus.NEW);
        subtask.setEpicId(epicID);
        overlapInTimeTask(subtask);
        subTasks.put(subtask.getId(), subtask);
        Epic epicFromEpicTasksMap = epicTasks.get(epicID);
        epicFromEpicTasksMap.putSubtask(subtask);
        addPrioritizedTasks(subtask);
        updateEpicTime(epicFromEpicTasksMap);
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks(){
        printTasks(prioritizedTasks);
        return prioritizedTasks;
    }
    public void printTasks(TreeSet<Task> tasks) {
        int dateWidth = 20;
        int typeWidth = 10;
        int idWidth = 5;
        int statusWidth = 8;
        int descWidth = 50;
        int durationWidth = 10;

        System.out.println(String.format(
                "%-" + dateWidth + "s | %-" + typeWidth + "s | %-" + idWidth + "s | %-" + statusWidth + "s | %-" + descWidth + "s | %-" + durationWidth +"s",
                "Дата", "Тип", "ID", "Статус", "Описание", "Продолжительность"
        ));
        System.out.println("-".repeat(dateWidth + typeWidth + idWidth + statusWidth + descWidth + durationWidth + 18));
        for (Task task : tasks) {
            System.out.println(String.format(
                    "%-" + dateWidth + "s | %-" + typeWidth + "s | %-" + idWidth + "d | %-" + statusWidth + "s | %-" + descWidth + "s | %-" + durationWidth + "s",
                    task.getStartTime(),
                    task.getTaskType(),
                    task.getId(),
                    task.getStatus(),
                    task.getDescription(),
                    task.getDuration()
            ));
        }
    }

    public void addPrioritizedTasks(Task task){
        prioritizedTasks.add(task);
    }

    @Override
    public void updateTask(Task task) {
        simpleTasks.put(task.getId(), task);
        overlapInTimeTask(task);
        addPrioritizedTasks(task);
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        updateEpicStatus(subtask);
        subTasks.put(subtask.getId(), subtask);
        overlapInTimeTask(subtask);
        addPrioritizedTasks(subtask);
    }

    @Override
    public void deleteTask(Task task) {
        simpleTasks.remove(task.getId(), task);
        prioritizedTasks.remove(task);
    }

    @Override
    public void deleteSubTask(Subtask subtask) {
        subTasks.remove(subtask.getId(), subtask);
        historyManager.remove(subtask.getId());
        prioritizedTasks.remove(subtask);
    }

    @Override
    public void deleteEpicTask(Epic epic) {
        HashMap<Integer, Subtask> tempSubTasks = new HashMap<>();
        tempSubTasks.putAll(subTasks);
        epicTasks.remove(epic.getId(), epic);
        prioritizedTasks.remove(epic);
        for (Subtask sub : tempSubTasks.values()) {
            if (epic.getId() == sub.getEpicId()) {
                deleteSubTask(sub);
                prioritizedTasks.remove(sub);
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
        if (simpleTask != null) {
            historyManager.add(simpleTask);
        }
        return simpleTask;
    }

    public void setSimpleTasks(Task task) {
        simpleTasks.put(task.getId(), task);
    }

    public void setEpicTasks(Epic epic) {
        epicTasks.put(epic.getId(), epic);
    }

    public void setSubTasks(Subtask subtask) {
        subTasks.put(subtask.getId(), subtask);
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

    public void addAllTasksToHistory(int id) {
        if (epicTasks.containsKey(id)) {
            Epic epicTask = epicTasks.get(id);
            historyManager.add(epicTask);
        } else if (subTasks.containsKey(id)) {
            Subtask subTask = subTasks.get(id);
            historyManager.add(subTask);
        } else {
            Task simpleTask = simpleTasks.get(id);
            historyManager.add(simpleTask);
        }
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
        addPrioritizedTasks(epic);
    }

    private void updateEpicTime(Epic epic){
        epic.getStartTime();
        epic.getDuration();
        epic.getEndTimeEpicTask();
    }
}
