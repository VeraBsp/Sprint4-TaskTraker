import java.io.File;

public class TaskTracker {
    public static void main(String[] args) {
      //  TaskManager manager = Managers.getDefault();
        FileBackedTasksManager manager = Managers.getDefaultFileBackedTasksManagers();
        Task task1 = new Task("task1", "Забрать заказ №125", TaskType.TASK);
        Task task2 = new Task("task2", "Сложить вещи в шкафу", TaskType.TASK);
        Epic epic1 = new Epic("epic1 ", "купить дом. должен быть удобный и красивый", TaskType.EPIC);
        Subtask subtask1 = new Subtask("subtask1 (относится к epic1)", "посмотреть объявления о продаже", epic1.getId(), TaskType.SUBTASK);
        Subtask subtask2 = new Subtask("subtask2 (относится к epic1)", "выбрать расположение дома", epic1.getId(), TaskType.SUBTASK);
        Subtask subtask4 = new Subtask("subtask4 (относится к epic1)", "сьездить на просмотр на машине", epic1.getId(), TaskType.SUBTASK);
        Epic epic3 = new Epic("epic3", "Выучить уроки история и математика", TaskType.EPIC);
        Epic epic2 = new Epic("epic2", "ораганизовать праздник день рождения", TaskType.EPIC);
        Subtask subtask3 = new Subtask("subtask3 (относится к epic2)", "заказать торт ко дню рождения", epic2.getId(), TaskType.SUBTASK);
        Task task3 = new Task("task3", "TASK3", TaskType.TASK);

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
//
//
//        System.out.println("task1= " + task1);
//        System.out.println("task2= " + task2);
//        System.out.println("epic1= " + epic1);
//        System.out.println("subtask1= " + subtask1);
//        System.out.println("subtask2= " + subtask2);
//        System.out.println("subtask4= " + subtask4);
//        System.out.println("epic2= " + epic2);
//        System.out.println("subtask3= " + subtask3);
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
        manager.loadFromFile(new File("./resources/currentstatusmanager.csv"));
    }
}
