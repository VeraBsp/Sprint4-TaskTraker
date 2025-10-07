import java.io.File;

public final class Managers {

    static TaskManager getDefault(){
        return new InMemoryTaskManager();
    }

    static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }

    static FileBackedTasksManager getDefaultFileBackedTasksManagers(){
        return new FileBackedTasksManager(new File("./resources/currentstatusmanager.csv"));
    }
}
