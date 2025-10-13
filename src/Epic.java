import java.util.HashMap;
import java.util.Objects;

public class Epic extends Task{
    private final HashMap<Integer, Subtask> subTaskMap = new HashMap<>();

    public Epic(String title, String description, TaskType taskType) {
        super(title, description,taskType);
    }

    public Epic(int id, TaskType taskType, String title, String description, TasksStatus status) {
        super(id, taskType, title, description, status);
    }

    public HashMap<Integer, Subtask> getSubTaskMap() {
        return subTaskMap;
    }

    public void putSubtask (Subtask subtask){
        subTaskMap.put(subtask.getId(), subtask);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Epic epic1 = (Epic) o;
        return Objects.equals(subTaskMap, epic1.subTaskMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subTaskMap);
    }

    @Override
    public String toString() {
        return super.getId() + "," + super.getTaskType() + "," + super.getTitle() + "," + super.getStatus() + "," + super.getDescription();
    }
}
