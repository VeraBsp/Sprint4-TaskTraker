public class ManagerSaveException extends RuntimeException {
    private String message;

    public ManagerSaveException(final String message) {
        super(message);
    }

    public ManagerSaveException(final Throwable cause) {
        super(cause);
        message = "Ошибка сохранения файла";
    }
}
