import java.util.Objects;

public class Task {
    private final String title;
    private final String description;
    private int id;
    private TasksStatus status;
    private TaskType taskType;

    public Task(String title, String description, TaskType taskType) {
        this.title = title;
        this.description = description;
        this.taskType = taskType;
    }
    public Task(int id, TaskType taskType, String title, String description, TasksStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.taskType = taskType;
        this.status = status;
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
        return "Task{ " +
                "Имя задачи= '" + title + '\'' +
                ", Описание= '" + description + '\'' +
                ", id= " + id +
                ", Статус= '" + status + '\'' +
                '}' + '\n';
    }
}

