import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private final File FILE;

    public FileBackedTasksManager(File FILE) {
        this.FILE = FILE;
    }
    private static final String FIRST_LINE = "id,type,name,status,description,epic";

    public static class ManagerSaveException extends IOException{
        public ManagerSaveException(){

        }
    }

    public void save(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./resources/currentstatusmanager.csv"), StandardCharsets.UTF_8))){
            writer.write(FIRST_LINE);
            writer.newLine();
            for (Task task: getAllTask()) {
                writer.write(toString(task));
                writer.newLine();
            }
            for (Task epic: getAllEpics()) {
                writer.write(toString(epic));
                writer.newLine();
            }
            for (Task sub: getAllSubtasks()) {
                writer.write(toStringSubtask((Subtask) sub));
                writer.newLine();
            }
            writer.newLine();
            writer.write(historyToString(getHistoryManager()));
        } catch (IOException e) {
            try {
                throw new ManagerSaveException();
            } catch (ManagerSaveException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public  FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try {
            String value = Files.readString(Path.of("./resources/currentstatusmanager.csv"));
            String[] lines = value.split("\n");
            for (String line: lines){
                if (line.trim().equals(FIRST_LINE)){
                    continue;
                }
                if (line.trim().isEmpty()) {
                    continue;
                }
                if (line.matches("\\d+(,\\d+)*")) {

                   List<Integer> listTaskID = historyFromString(line);
                   for (Integer taskId: listTaskID){
                       addAllTasksToHistory(taskId);
                   }
                   continue;
                }
                Task task = fromString(line);
                if(task.getTaskType()==TaskType.TASK){

                   setSimpleTasks(task);
                } else if (task.getTaskType()==TaskType.EPIC) {
                   setEpicTasks((Epic)task);
                } else {
                    setSubTasks((Subtask) task);
                }
            }
        } catch (IOException e) {
            try {
                throw new ManagerSaveException();
            } catch (ManagerSaveException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return fileBackedTasksManager;
    }

    //метод сохранения задачи в строку
    public String toString(Task task){
        return task.getId() +","+ task.getTaskType()+","+task.getTitle() +","+ task.getStatus() +","+ task.getDescription();
    }

    //метод сохранения подзадачи в строку
    public String toStringSubtask(Subtask subtask){
        return subtask.getId() +"," + subtask.getTaskType()+","+ subtask.getTitle() +","+ subtask.getStatus() +","+ subtask.getDescription() +","+ subtask.getEpicId();
    }

    //метод создания задачи из строки
    public Task fromString(String value){
        //1,TASK,Task1,NEW,Description task1
        String[] line = value.split(",");
        TaskType type = TaskType.valueOf(line[1]);
        if(type==TaskType.TASK){
            return new Task(Integer.parseInt(line[0]),TaskType.valueOf(line[1]), line[2], line[4], TasksStatus.valueOf(line[3]));
        } else if (type==TaskType.SUBTASK) {
            return new Subtask(Integer.parseInt(line[0]),TaskType.valueOf(line[1]), line[2], line[4], TasksStatus.valueOf(line[3]), Integer.parseInt(line[5].trim()));
        } else {
            return new Epic(Integer.parseInt(line[0]),TaskType.valueOf(line[1]), line[2], line[4], TasksStatus.valueOf(line[3]));
        }
    }

    //метод сохранения менеджера истории в CSV
    public static String historyToString(HistoryManager manager){
        List<Task> history = manager.getHistory();
        List<String> ids = new ArrayList<>();
        for (Task task : history) {
            ids.add(String.valueOf(task.getId()));
        }
        return String.join(",", ids);
    }

    //метод восстановления менеджера истории из CSV
    public static List<Integer> historyFromString(String value){
        List<Integer> taskID = new ArrayList<>();
        String[] values = value.split(",");
        for (String taskId: values){
            taskID.add(Integer.valueOf(taskId));
        }
        return  taskID;
    }


    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createEpicTask(Epic epic) {
        super.createEpicTask(epic);
        save();
    }

    @Override
    public void createSubTask(Subtask subtask, int epicID) {
        super.createSubTask(subtask, epicID);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubTask(Subtask subtask) {
        super.updateSubTask(subtask);
        save();
    }

    @Override
    public void deleteTask(Task task) {
        super.deleteTask(task);
        save();
    }

    @Override
    public void deleteSubTask(Subtask subtask) {
        super.deleteSubTask(subtask);
        save();
    }

    @Override
    public void deleteEpicTask(Epic epic) {
        super.deleteEpicTask(epic);
        save();
    }

    @Override
    public void clearTask() {
        super.clearTask();
        save();
    }

    @Override
    public void clearEpicTask(Epic epic) {
        super.clearEpicTask(epic);
        save();
    }
}
