import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class UserDataApp {
    private static final int REQUIRED_FIELDS = 6;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в следующем формате:");
        System.out.println("Фамилия Имя Отчество датарождения номертелефона пол");
        System.out.println("где:");
        System.out.println(" - Фамилия, Имя, Отчество - строки");
        System.out.println(" - Дата рождения - строка формата dd.mm.yyyy");
        System.out.println(" - Номер телефона - целое беззнаковое число без форматирования");
        System.out.println(" - Пол - символ латиницей 'f' или 'm'");
        System.out.println("Пример: Иванов Иван Иванович 01.01.2000 89001234567 m");

        String input = scanner.nextLine();
        String[] fields = input.split(" ");

        try {
            if (fields.length != REQUIRED_FIELDS) {
                throw new InvalidInputException("Ошибка: количество введенных данных не соответствует требуемому. Ожидается 6 полей.");
            }

            String surname = fields[0];
            String name = fields[1];
            String patronymic = fields[2];
            String birthDate = fields[3];
            long phoneNumber = parsePhoneNumber(fields[4]);
            char gender = parseGender(fields[5]);

            validateNames(surname, name, patronymic); // Проверка фамилии, имени и отчества
            validateBirthDate(birthDate); // Проверка формата даты

            writeToFile(surname, name, patronymic, birthDate, phoneNumber, gender);
            System.out.println("Данные успешно записаны в файл: " + surname);

        } catch (InvalidInputException e) {
            System.err.println(e.getMessage());
        } catch (ParseException e) {
            System.err.println("Ошибка: неверный формат даты. Ожидается формат dd.mm.yyyy.");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void validateNames(String surname, String name, String patronymic) throws InvalidInputException {
        if (!isValidName(surname)) {
            throw new InvalidInputException("Ошибка: Фамилия содержит недопустимые символы или пуста.");
        }
        if (!isValidName(name)) {
            throw new InvalidInputException("Ошибка: Имя содержит недопустимые символы или пусто.");
        }
        if (!isValidName(patronymic)) {
            throw new InvalidInputException("Ошибка: Отчество содержит недопустимые символы или пусто.");
        }
    }

    private static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && name.matches("[а-яА-ЯёЁa-zA-Z]+");
    }

    private static long parsePhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
        try {
            return Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            throw new InvalidPhoneNumberException("Ошибка: неверный номер телефона. Ожидается целое беззнаковое число.");
        }
    }

    private static char parseGender(String gender) throws InvalidGenderException {
        if (gender.length() != 1 || (gender.charAt(0) != 'f' && gender.charAt(0) != 'm')) {
            throw new InvalidGenderException("Ошибка: неверный пол. Ожидается символ 'f' или 'm'.");
        }
        return gender.charAt(0);
    }

    private static void validateBirthDate(String birthDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(false); // Устанавливаем строгий режим
        sdf.parse(birthDate); // Попытка распарсить дату
    }

    private static void writeToFile(String surname, String name, String patronymic, String birthDate, long phoneNumber, char gender) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(surname + ".txt", true))) {
            writer.write(String.format("%s %s %s %s %d %c%n", surname, name, patronymic, birthDate, phoneNumber, gender));
        }
    }
}