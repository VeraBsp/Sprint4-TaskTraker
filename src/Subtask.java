import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class Subtask extends Task {
    private int epicId;

    public Subtask(String title, String description, int epicId, TaskType taskType) {
        super(title, description, taskType);
        this.epicId = epicId;
    }

    public Subtask(TaskType taskType, String title, String description, TasksStatus status, int epicId) {
        super(taskType, title, description, status);
        this.epicId = epicId;
    }

    public Subtask(int id, TaskType taskType, String title, String description, TasksStatus status, int epicId) {
        super(id, taskType, title, description, status);
        this.epicId = epicId;
    }
    public Subtask(int id, TaskType taskType, String title, String description, TasksStatus status, LocalDateTime startTime, int duration, int epicId) {
        super(id, taskType, title, description, status, startTime, duration);
        this.epicId = epicId;
    }

    public Subtask(TaskType taskType, String title, String description, TasksStatus status, LocalDateTime startTime, int duration, int epicId) {
        super(taskType, title, description, status, startTime, duration);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subtask subtask = (Subtask) o;
        return  epicId == subtask.epicId
                &&  Objects.equals(super.getTitle(), subtask.getTitle())
                && Objects.equals(super.getId(), subtask.getId())
                && Objects.equals(super.getDescription(), subtask.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(epicId, super.getId());
    }

    @Override
    public String toString() {
        return super.getStartTime() + ", " + super.getId() + "," + super.getTaskType() + "," + super.getTitle() + "," + super.getStatus() + "," + super.getDescription() + "," + epicId +"," + super.getDuration() ;
    }

//    @Override
//    public String toString() {
//        return super.getId() + "," + super.getTaskType() + "," + super.getTitle() + "," + super.getStatus() + "," + super.getDescription() + "," + epicId;
//    }
}
