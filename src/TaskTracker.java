import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskTracker {
    public static void main(String[] args) {
        //TaskManager manager = Managers.getDefault();
        FileBackedTasksManager manager = Managers.getDefaultFileBackedTasksManagers();
        Task task1 = new Task("task1", "task1 description", TaskType.TASK);
        Task task2 = new Task("task2", "task2 description", TaskType.TASK);
        Epic epic1 = new Epic("epic1 ", "epic1 description", TaskType.EPIC);
        Subtask subtask1 = new Subtask("subtask1", "subtask1 (description относится к epic1)", epic1.getId(), TaskType.SUBTASK);
        Subtask subtask2 = new Subtask("subtask2", "subtask2 (description относится к epic1)", epic1.getId(), TaskType.SUBTASK);
        Subtask subtask4 = new Subtask("subtask4", "subtask4 (description относится к epic1)", epic1.getId(), TaskType.SUBTASK);
//        Epic epic3 = new Epic("epic3", "Выучить уроки история и математика", TaskType.EPIC);
        Epic epic2 = new Epic("epic2", "epic2 description", TaskType.EPIC);
        Subtask subtask3 = new Subtask("subtask3", "subtask3 (description относится к epic2)", epic2.getId(), TaskType.SUBTASK);
        Subtask subtask5 = new Subtask("subtask5", "subtask5 (description относится к epic2)", epic2.getId(), TaskType.SUBTASK);
 //         Task task3 = new Task("task3", "TASK3", TaskType.TASK);

//        manager.createTask(task1);
//        manager.createTask(task2);
//        manager.createEpicTask(epic1);
//
//        manager.createSubTask(subtask1, epic1.getId());
//        manager.createSubTask(subtask2, epic1.getId());
//        manager.createSubTask(subtask4, epic1.getId());
//        manager.createEpicTask(epic2);
//        manager.createSubTask(subtask3, epic2.getId());
//        manager.createEpicTask(epic3);


        System.out.println("task1= " + task1);
       // System.out.println("task2= " + task2);
        System.out.println("epic1= " + epic1);
        System.out.println("subtask1= " + subtask1);
        System.out.println("subtask2= " + subtask2);
        System.out.println("subtask4= " + subtask4);
        System.out.println("epic2= " + epic2);
        System.out.println("subtask3= " + subtask3);
//       // System.out.println("История просмотров" + manager.getHistory());
//        System.out.println();
//
//        task1.setStatus(TasksStatus.IN_PROGRESS);
//        manager.updateTask(task1);
//        System.out.println(manager.getTask(task1.getId()));
//        task2.setStatus(TasksStatus.DONE);
//        manager.updateTask(task2);
//        System.out.println(manager.getTask(task2.getId()));
//        subtask1.setStatus(TasksStatus.IN_PROGRESS);
//        manager.updateSubTask(subtask1);
//        System.out.println(manager.getSubTask(subtask1.getId()));
//        subtask2.setStatus(TasksStatus.DONE);
//        manager.updateSubTask(subtask2);
//        subtask4.setStatus(TasksStatus.DONE);
//        manager.updateSubTask(subtask4);
//        System.out.println(manager.getSubTask(subtask2.getId()));
//        subtask3.setStatus(TasksStatus.IN_PROGRESS);
//        manager.updateSubTask(subtask3);
//        System.out.println(manager.getSubTask(subtask3.getId()));
//        System.out.println(manager.getEpicTask(epic1.getId()));
//        System.out.println(manager.getEpicTask(epic2.getId()));
//        System.out.println(manager.getSubTask(subtask3.getId()));
//        System.out.println(manager.getSubTask(subtask4.getId()));
//        System.out.println(manager.getEpicTask(epic2.getId()));
//        System.out.println(manager.getEpicTask(epic3.getId()));
//        System.out.println();
//
//        System.out.println("Получение списка всех задач: ");
//        System.out.println(manager.getAllTask());
//        System.out.println();
//
//        System.out.println("Получение списка всех эпиков: ");
//        System.out.println(manager.getAllEpics());
//        System.out.println();
//
//        System.out.println("Получение списка всех подзадач: ");
//        System.out.println(manager.getAllSubtasks());
//        System.out.println();
////
//////        System.out.println("Удаление всех задач: ");
//////        manager.clearTask();
//////        System.out.println(manager.getAllTask());
//////        System.out.println();
//////
//////        System.out.println("Удаление задачи task1: ");
//////        manager.deleteTask(task1);
//////        System.out.println(manager.getAllTask());
//////        System.out.println();
//////
//////        System.out.println("Удаление подзадачи subtask2: ");
//////        manager.deleteSubTask(subtask2);
//////        System.out.println(manager.getAllSubtasks());
//////        System.out.println();
//////
//////        System.out.println("Удаление эпика:");
//////        manager.deleteEpicTask(epic2);
//////        System.out.println(manager.getAllEpics());
//////        System.out.println(manager.getAllSubtasks());
//////
//////        System.out.println("Удаление всех эпиков: ");
//////        manager.clearEpicTask(epic1);
////
//        System.out.println(manager.getAllEpics());
//        System.out.println();
//        System.out.println("Получение списка всех подзадач (подзадачи удаляются после удаления эпиков): ");
//        System.out.println(manager.getAllSubtasks());
//        System.out.println("История просмотров:" + '\n' + manager.getHistoryManager().getHistory());
//     //   manager.getHistoryManager().remove(epic1.getId());
//        manager.deleteEpicTask(epic1);
//        System.out.println("***********************************************************************");
//        manager.getTask(task2.getId());
//       // manager.getEpicTask(epic2.getId());
//       // manager.getEpicTask(epic2.getId());
//        manager.getTask(task1.getId());
//     //   manager.getTask(task2.getId());
//        manager.getSubTask(subtask3.getId());
//        System.out.println("История просмотров:" + '\n' + manager.getHistoryManager().getHistory());
//        manager.createTask(task3);
//        manager.save();
//        manager.loadFromFile(new File("./resources/currentstatusmanager.csv"));
//        System.out.println("*****Спринт7*****");
        manager.createEpicTask(epic1);
        manager.createSubTask(subtask1, epic1.getId());
        manager.createSubTask(subtask2, epic1.getId());
        manager.createSubTask(subtask4, epic1.getId());
        manager.createEpicTask(epic2);
        manager.createSubTask(subtask3, epic2.getId());
        manager.createSubTask(subtask5, epic2.getId());
        subtask1.setDuration(15);
        subtask1.setStartTime(LocalDateTime.of(2025, 1, 1, 9, 00));
        subtask2.setStartTime(LocalDateTime.of(2025, 1, 1, 9, 10));
        subtask2.setDuration(22);
        subtask4.setStartTime(LocalDateTime.of(2025, 1, 2, 11, 00));
        subtask4.setDuration(15);
        subtask3.setDuration(14);
        subtask5.setDuration(14);
        subtask3.setStartTime(LocalDateTime.of(2025, 2, 1, 14, 00));
        subtask5.setStartTime(LocalDateTime.of(2025, 2, 1, 15, 00));
        manager.updateSubTask(subtask1);
        manager.updateSubTask(subtask2);
        manager.updateSubTask(subtask4);
        manager.updateSubTask(subtask3);
        manager.updateSubTask(subtask5);
        manager.getEpicTask(epic1.getId());
        manager.getSubTask(subtask1.getId());
        manager.getSubTask(subtask2.getId());
        manager.getSubTask(subtask4.getId());
        manager.getEpicTask(epic2.getId());
        manager.getSubTask(subtask3.getId());
        manager.getSubTask(subtask5.getId());
//        LocalDateTime startTime = manager.getStartTimeEpicTask(epic2);
        LocalDateTime startTime = epic2.getStartTime();
        System.out.println("Время начала эпика: " + startTime);
//        LocalDateTime endTime = manager.getEndTimeEpicTask(epic2);
        LocalDateTime endTime = epic2.getEndTimeEpicTask();
        System.out.println("Время завершения эпика: " + endTime);
        int duration = epic2.getDuration();
        System.out.println("На выполнение эпика потребовалось: " + duration);
        epic2.setDuration(duration);

        epic1.getStartTime();
        epic1.getEndTimeEpicTask();
        int durationEpic1 = epic1.getDuration();
        epic1.setDuration(durationEpic1);

        LocalDateTime endTimeSubtask3 = subtask3.getEndTime(subtask3.getStartTime(), subtask3.getDuration());
        System.out.println("Время завершения подзадачи subtask3: " + endTimeSubtask3);
        task1.setStartTime((LocalDateTime.of(2025, 11, 24, 15, 00)));
        task1.setDuration(25);
        manager.createTask(task1);
        task2.setStartTime((LocalDateTime.of(2025, 11, 24, 14, 45)));
        task2.setDuration(25);
        manager.createTask(task2);
        //manager.getTask(task1.getId());

        LocalDateTime startTimeTask1 = task1.getStartTime();
        System.out.println("Время начала задачи: " + startTimeTask1);
        int durationTask1 = task1.getDuration();
        System.out.println("На выполнение задачи потребовалось: " + durationTask1);
        LocalDateTime endTimeTask1 = task1.getEndTime(task1.getStartTime(),durationTask1);
        System.out.println("Время завершения задачи: " + endTimeTask1);
       // manager.getPrioritizedTasks();
        System.out.println("Список задач:" + "\n" + manager.getPrioritizedTasks());
        System.out.println("История просмотров:" + '\n' + manager.getHistoryManager().getHistory());
        manager.save();
        manager.loadFromFile(new File("./resources/currentstatusmanager.csv"));
    }

}
