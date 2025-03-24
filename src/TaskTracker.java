public class TaskTracker {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Task task1 = new Task("забрать заказ", "заказ №125");
        Task task2 = new Task("сложить вещи в шкаф", "вещи должны лежать на своих полках");
        Epic epic1 = new Epic("купить дом", "дом должен быть удобный и красивый");
        Subtask subtask1 = new Subtask("посмотреть объявления о продаже", "объявления 21212", epic1.getId());
        Subtask subtask2 = new Subtask("выбрать расположение", "расположение в деревне", epic1.getId());
        Epic epic2 = new Epic("ораганизовать праздник", "день рождения");
        Subtask subtask3 = new Subtask("заказать торт", "торт большой, вкусный", epic2.getId());

        manager.createTask(task1);
        manager.createTask(task2);
        manager.createEpicTask(epic1);
        manager.createSubTask(subtask1, epic1.getId());
        manager.createSubTask(subtask2, epic1.getId());
        manager.createEpicTask(epic2);
        manager.createSubTask(subtask3, epic2.getId());

        System.out.println("task1= " + task1);
        System.out.println("task2= " + task2);
        System.out.println("epic1= " + epic1);
        System.out.println("subtask1= " + subtask1);
        System.out.println("subtask2= " + subtask2);
        System.out.println("epic2= " + epic2);
        System.out.println("subtask3= " + subtask3);
       // System.out.println("История просмотров" + manager.getHistory());
        System.out.println();

        task1.setStatus(TasksStatus.IN_PROGRESS);
        manager.updateTask(task1);
        System.out.println(manager.getTask(task1.getId()));
        task2.setStatus(TasksStatus.DONE);
        manager.updateTask(task2);
        System.out.println(manager.getTask(task2.getId()));
        subtask1.setStatus(TasksStatus.IN_PROGRESS);
        manager.updateSubTask(subtask1);
        System.out.println(manager.getSubTask(subtask1.getId()));
        subtask2.setStatus(TasksStatus.DONE);
        manager.updateSubTask(subtask2);
        System.out.println(manager.getSubTask(subtask2.getId()));
        subtask3.setStatus(TasksStatus.IN_PROGRESS);
        manager.updateSubTask(subtask3);
        System.out.println(manager.getSubTask(subtask3.getId()));
        System.out.println(manager.getEpicTask(epic1.getId()));
        System.out.println(manager.getEpicTask(epic2.getId()));
        System.out.println();

        System.out.println("Получение списка всех задач: ");
        System.out.println(manager.getAllTask());
        System.out.println();

        System.out.println("Получение списка всех эпиков: ");
        System.out.println(manager.getAllEpics());
        System.out.println();

        System.out.println("Получение списка всех подзадач: ");
        System.out.println(manager.getAllSubtasks());
        System.out.println();

        System.out.println("Удаление всех задач: ");
        manager.clearTask();
        System.out.println(manager.getAllTask());
        System.out.println();

        System.out.println("Удаление задачи task1: ");
        manager.deleteTask(task1);
        System.out.println(manager.getAllTask());
        System.out.println();

        System.out.println("Удаление подзадачи subtask2: ");
        manager.deleteSubTask(subtask2);
        System.out.println(manager.getAllSubtasks());
        System.out.println();

        System.out.println("Удаление эпика:");
        manager.deleteEpicTask(epic2);
        System.out.println(manager.getAllEpics());
        System.out.println(manager.getAllSubtasks());

        System.out.println("Удаление всех эпиков: ");
        manager.clearEpicTask(epic1);
        System.out.println(manager.getAllEpics());
        System.out.println();
        System.out.println("Получение списка всех подзадач (подзадачи удаляются после удаления эпиков): ");
        System.out.println(manager.getAllSubtasks());
        System.out.println("История просмотров" + manager.getHistoryManager().getHistory());
    }
}
