public class InvalidDateFormatException extends InvalidInputException {
    public InvalidDateFormatException() {
        super("Неверный формат даты. Ожидается dd.mm.yyyy.");
    }
}