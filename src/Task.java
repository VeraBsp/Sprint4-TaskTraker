import java.util.Objects;

public class Task {
    private String title;
    private String description;
    private int id;
    private TasksStatus status;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
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
                '}';
    }
}

