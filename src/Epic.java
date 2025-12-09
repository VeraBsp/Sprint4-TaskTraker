import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Epic extends Task{
    private final HashMap<Integer, Subtask> subTaskMap = new HashMap<>();
    public Epic(String title, String description, TaskType taskType) {
        super(title, description,taskType);
    }

    public Epic(TaskType taskType, String title, String description, TasksStatus status) {
        super(taskType, title, description, status);
    }

    public Epic(int id, TaskType taskType, String title, String description, TasksStatus status) {
        super(id, taskType, title, description, status);
    }

    public Epic(int id, TaskType taskType, String title, String description, TasksStatus status, LocalDateTime startTime, int duration) {
        super(id, taskType, title, description, status, startTime, duration);
    }

    public HashMap<Integer, Subtask> getSubTaskMap() {
        return subTaskMap;
    }

    public void putSubtask (Subtask subtask){
        subTaskMap.put(subtask.getId(), subtask);
    }

    @Override
    public LocalDateTime getStartTime() {
        LocalDateTime startSubtask = null;
        for (Map.Entry<Integer, Subtask> entry: subTaskMap.entrySet()) {
            LocalDateTime tempStartTime = entry.getValue().getStartTime();
            if (tempStartTime != null && startSubtask == null) {
                startSubtask = tempStartTime;
            }
            if (tempStartTime != null && tempStartTime.isBefore(startSubtask)){
                startSubtask = tempStartTime;
            }
        }
        this.setStartTime(startSubtask);
        return startSubtask;
    }

    public LocalDateTime getEndTimeEpicTask() {
        LocalDateTime endSubtask = null;
        for (Map.Entry<Integer, Subtask> entry: subTaskMap.entrySet()) {
            LocalDateTime tempStartTime = entry.getValue().getStartTime();
            int duration = entry.getValue().getDuration();
            if (tempStartTime != null && endSubtask == null) {
                endSubtask = tempStartTime.plusMinutes(duration);
            }
            if (tempStartTime != null && tempStartTime.isAfter(endSubtask)){
                endSubtask = tempStartTime.plusMinutes(duration);;
            }
        }
       // this.setStartTime(endSubtask);
        return endSubtask;
    }
  @Override
    public int getDuration() {
        int duration = 0;
        for (Subtask sub : subTaskMap.values()) {
            duration= duration + sub.getDuration();
        }
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(this.getId(), epic.getId()) && Objects.equals(this.getTitle(), epic.getTitle()) && Objects.equals(this.getDescription(), epic.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), super.getDescription(), super.getTitle());
    }

    @Override
    public String toString() {
        return super.getStartTime() + ", " + super.getId() + "," + super.getTaskType() + "," + super.getTitle() + "," + super.getStatus() + "," + super.getDescription() +","+super.getDuration();
    }

//    @Override
//    public String toString() {
//        return super.getId() + "," + super.getTaskType() + "," + super.getTitle() + "," + super.getStatus() + "," + super.getDescription();
//    }
}
