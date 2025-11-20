import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private static final String FIRST_LINE = "id,type,name,status,description,epic";
    private final File FILE;

    public FileBackedTasksManager() {
        String fileName = "./resources/currentstatusmanager.csv";
        this.FILE = new File(fileName);
        try {
            if (!Files.exists(Paths.get(fileName))) {
             Files.createFile(Paths.get(fileName));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка при создании файла");
        }
    }

    public FileBackedTasksManager(File FILE) {
        this.FILE = FILE;
        try {
            if (!Files.exists(Paths.get(FILE.getName()))) {
                Files.createFile(Paths.get(FILE.getName()));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка при создании файла");
        }
    }

    public void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE, StandardCharsets.UTF_8))) {
            writer.write(FIRST_LINE);
            writer.newLine();
            for (Task task : getAllTask()) {
                writer.write(task.toString());
                writer.newLine();
            }
            for (Task epic : getAllEpics()) {
                writer.write(epic.toString());
                writer.newLine();
            }
            for (Task sub : getAllSubtasks()) {
                writer.write(sub.toString());
                writer.newLine();
            }
            writer.newLine();
            writer.write(historyToString(getHistoryManager()));
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    public FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            while (br.ready()) {
                String line = br.readLine();
                if (!line.isBlank()) {
                    Task task = fileBackedTasksManager.fromString(line);
                    if (task.getTaskType() == TaskType.TASK) {
                        fileBackedTasksManager.setSimpleTasks(task);
                    } else if (task.getTaskType() == TaskType.EPIC) {
                        fileBackedTasksManager.setEpicTasks((Epic) task);
                    } else {
                        fileBackedTasksManager.setSubTasks((Subtask) task);
                    }
                } else {
                    String lineLast = br.readLine();
                    List<Integer> listTaskID = historyFromString(lineLast);
                    for (Integer taskId : listTaskID) {
                        fileBackedTasksManager.addAllTasksToHistory(taskId);
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException(e.getMessage());
        }
        return fileBackedTasksManager;
    }

    //метод создания задачи из строки
    public Task fromString(String value) {
        //1,TASK,Task1,NEW,Description task1
        String[] line = value.split(",");
        TaskType type = TaskType.valueOf(line[1]);
        switch (type) {
            case TASK:
                return new Task(Integer.parseInt(line[0]), TaskType.valueOf(line[1]), line[2], line[4], TasksStatus.valueOf(line[3]));
            case EPIC:
                return new Epic(Integer.parseInt(line[0]), TaskType.valueOf(line[1]), line[2], line[4], TasksStatus.valueOf(line[3]));
            case SUBTASK:
                return new Subtask(Integer.parseInt(line[0]), TaskType.valueOf(line[1]), line[2], line[4], TasksStatus.valueOf(line[3]), Integer.parseInt(line[5].trim()));
            default:
                return null;
        }
    }

    //метод сохранения менеджера истории в CSV
    public static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        List<String> ids = new ArrayList<>();
        for (Task task : history) {
            ids.add(String.valueOf(task.getId()));
        }
        return String.join(",", ids);
    }

    //метод восстановления менеджера истории из CSV
    public static List<Integer> historyFromString(String value) {
        if (value != null) {
        List<Integer> taskID = new ArrayList<>();
        String[] values = value.split(",");
        for (String taskId : values) {
            taskID.add(Integer.valueOf(taskId));
        }
        return taskID;
        }
        return new ArrayList<>();
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
