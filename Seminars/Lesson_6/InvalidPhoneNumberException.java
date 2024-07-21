public class InvalidPhoneNumberException extends InvalidInputException {
    public InvalidPhoneNumberException() {
        super("Неверный номер телефона. Ожидается целое беззнаковое число.");
    }

    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}