public class InvalidGenderException extends InvalidInputException {
    public InvalidGenderException() {
        super("Неверный пол. Ожидается 'f' или 'm'.");
    }

    public InvalidGenderException(String message) {
        super(message);
    }
}