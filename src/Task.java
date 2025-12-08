import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task implements Comparable<Task>{
    private final String title;
    private final String description;
    private int id;
    private TasksStatus status;
    private TaskType taskType;
    private int duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Task(String title, String description, TaskType taskType) {
        this.title = title;
        this.description = description;
        this.taskType = taskType;
    }

    public Task(TaskType taskType, String title, String description, TasksStatus status) {
       // this.id = id;
        this.title = title;
        this.description = description;
        this.taskType = taskType;
        this.status = status;
    }

    public Task(int id, TaskType taskType, String title, String description, TasksStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.taskType = taskType;
        this.status = status;
    }

    public Task(int id, TaskType taskType, String title, String description, TasksStatus status, LocalDateTime startTime, int duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.taskType = taskType;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    public Task(TaskType taskType, String title, String description, TasksStatus status, LocalDateTime startTime, int duration) {
        //this.id = id;
        this.title = title;
        this.description = description;
        this.taskType = taskType;
        this.status = status;
        this.startTime = startTime;
        this.duration = duration;
    }

    @Override
    public int compareTo(Task o) {
        if (o.equals(this)){
            return 0;
        }
        if (this.getStartTime() == null) {
            return 1;
        }
        if (o.getStartTime() == null) {
            return -1;
        }
        return this.getStartTime().compareTo(o.getStartTime());
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime(LocalDateTime startTime, int duration) {
        LocalDateTime endTime;
        endTime = startTime.plusMinutes(duration);
        return endTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public void setStatus(TasksStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public TasksStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id
                && Objects.equals(title, task.title)
                && Objects.equals(description, task.description)
                && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, id, status);
    }

    @Override
    public String toString() {
        return startTime + ", "+ id + "," + taskType + "," + title + "," + status + "," + description +"," + duration;
    }
//    @Override
//    public String toString() {
//        return id + "," + taskType + "," + title + "," + status + "," + description;
//    }
}

