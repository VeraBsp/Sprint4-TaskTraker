import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final int maxSize = 10;
    private final Map<Integer, Node<Task>> historyTasks = new HashMap<>();

    private final CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        if (task != null) {
            if (historyTasks.containsKey(task.getId())) {
                remove(task.getId());
            }
            customLinkedList.linkLast(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return customLinkedList.getTasks();
    }

    @Override
    public void remove(int id) {
        customLinkedList.removeNode(historyTasks.get(id));
        historyTasks.remove(id);
    }

    class CustomLinkedList<T> {
        public Node<Task> head;
        public Node<Task> tail;
        public int size = 0;

        public void linkLast(Task task) {
            final Node<Task> oldTail = tail;
            final Node<Task> newNode = new Node<>(tail, task, null);
            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else {
                oldTail.setNext(newNode);
            }

            size++;
            historyTasks.put(task.getId(), newNode);
        }

        public List<Task> getTasks() {
            ArrayList<Task> arrayListTasks = new ArrayList<>();
            Node<Task> currentNode = head;
            while (currentNode != null) {
                arrayListTasks.add(currentNode.getData());
                currentNode = currentNode.getNext();
            }
            return arrayListTasks;
        }

        public void removeNode(Node<Task> node) {
            if (node == head && node != tail) {
                head = node.getNext();
                head.setPrev(null);
            } else if (node == tail && node != head) {
                tail = node.getPrev();
                tail.setNext(null);
            } else if (head == tail) {
                head = null;
                tail = null;
            } else {
                Node<Task> next = node.getNext();
                Node<Task> prev = node.getPrev();
                next.setPrev(prev);
                prev.setNext(next);
            }
        }
    }
}
