import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private int maxSize = 5;

    private List<Task> historyTasks = new ArrayList<>(maxSize);
    @Override
    public void add(Task task) {
        if (historyTasks.size()==maxSize) {
            historyTasks.remove(0);
        }
        historyTasks.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyTasks;
    }
}
