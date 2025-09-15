import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final int maxSize = 10;

    private final Map<Integer, Node<Task>> historyTasks = new HashMap<>();

    private final CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();

    @Override
    public void add(Task task) {
        if (historyTasks.containsKey(task.getId())) {
            remove(task.getId());
        }
        customLinkedList.linkLast(task);
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
            if (node == head) {
                head = node.getNext();
                head.setPrev(null);
                node.setNext(null);
            } else if (node == tail) {
                tail = node.getPrev();
                tail.setNext(null);
                node.setPrev(null);
            } else {
                Node<Task> prevNode = node.getPrev();
                Node<Task> nextNode = node.getNext();

                if (prevNode != null) {
                    prevNode.setNext(nextNode);
                }
                if (nextNode != null) {
                    nextNode.setPrev(prevNode);
                }
                node.setPrev(null);
                node.setNext(null);
            }
        }
    }
}
