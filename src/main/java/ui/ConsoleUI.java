package ui;

import entities.Classbook;
import exceptions.WrongChooseException;
import entities.Subject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.ClassService;
import services.ClassbooksService;
import services.RatingsService;
import services.SubjectsService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConsoleUI {

    private static final Logger logger = LogManager.getLogger(ConsoleUI.class);
    private static SubjectsService subjectsService = new SubjectsService();
    private static ClassbooksService classbooksService = new ClassbooksService();
    private static ClassService classService = new ClassService();
    private static RatingsService ratingsService = new RatingsService();
    private static final Scanner scanner = new Scanner(System.in);

    // Массив возможных действий меню.
    private static String [] actions;
    private static String [] ratingsActions;

    // Метка того, что пользователь выбрал какой-то корректный вариант меню.
    private static boolean isChooseMaked = false;

    public static void main(String[] args) {
        init();
        // Показываем главное меню.
        while (!isChooseMaked) {
            try {
                showMainMenu();
            }
            catch (InputMismatchException e) {
                System.out.println("Некорректный ввод! Пожалуйста, используйте цифры для выбора варианта.");
            }
            catch (WrongChooseException wrongChoose) {
                System.out.println(wrongChoose.getMessage());
            }
        }
    }

    // Инициализируем массив возможных действий меню.
    private static void init() {
        // Главное меню.
        actions = new String[2];
        actions[0] = "1) Создать новый журнал";
        actions[1] = "2) Работать c оценками";
        // Работа с оценками.
        ratingsActions = new String[4];
        ratingsActions[0] = "1) Поставить оценку";
        ratingsActions[1] = "2) Посмотреть оценки";
        ratingsActions[2] = "3) Изменить оценку";
        ratingsActions[3] = "4) Удалить оценку";
    }

    // Метод отображения главного меню.
    private static void showMainMenu() throws WrongChooseException {
        logger.debug("Приложение запущено");

        System.out.println();
        System.out.println("Вас приветствует приложение 'Школьный журнал'");
        System.out.println("Пожалуйста, выберите нужное действие:");

        // Перебираем и выводим возможные варианты меню.
        for (String action : actions) {
            System.out.println(action);
        }

        int choose = scanner.nextInt();
        // Обрабатываем выбор пользователя.
        handleUserChoose(choose);
        isChooseMaked = false;
    }

    // Метод обработки пользовательского выбора.
    private static void handleUserChoose(int choose) throws WrongChooseException {
        if (choose < 1 || choose > actions.length) {
            logger.info("Пользователь выбрал несуществующий вариант");
            throw new WrongChooseException("Выбран несуществующий вариант. Пожалуйста, выберите среди возможных.");
        }
        // Отмечаем, что пользователь свой выбор сделал.
        isChooseMaked = true;
        switch (choose) {
            // Создание журнала.
            case 1:
                addNewClassbookOption();
                break;
            // Работа с оценками.
            case 2:
                workWithRatingsOption();
                break;
            default:
                throw new WrongChooseException("Выбран несуществующий вариант. Пожалуйста, выберите среди возможных.");
        }
    }

    // Метол, выводящий информацию о варианте добавления нового журнала.
    private static void addNewClassbookOption() {
        pringClassbooksInfo();
        System.out.println("Введите название предмета (допускается ввести новый предмет): ");
        scanner.nextLine();
        String subjectName = scanner.nextLine();
        System.out.println("Введите номер класса: ");
        int classId = scanner.nextInt();
        scanner.nextLine();
        classbooksService.addNewClassbook(classId, subjectName);
        }

    // Метод для работы с оценками.
    private static void workWithRatingsOption() {
        System.out.println("Что вы хотите сделать?");
        Arrays.asList(ratingsActions).stream()
                .forEach(x -> System.out.println(x));
        int option = scanner.nextInt();

        switch (option) {
            // Поставить оценку.
            case 1:
                setUpRating();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                break;
        }
    }

    // Метод для простановки оценки.
    /* Все проверки внутри метода можно было бы вынести в сервисы, но тогда пользователю
    приходилось бы каждый раз заново вводить данные. Ради удобства пользователя проверкы вынесены
    на слой UI.
     */
    private static void setUpRating() {
        // Получаем класс.
        int classId = printClassEnterDialog();

        // Получаем предмет
        String subjectName = printSubjectEnterDialog(classId);

        // Получаем ФИО ученика.
        String pupilName = printPupilNameEnterDialog(classId);

        // Получаем оценку.
        int rate = printRateEnterDialog();

        // Получаем дату
        Date rateDate = printRateDateEnterDialog();

        ratingsService.addNewRate(classId, subjectName, pupilName, rate, rateDate);
    }

    // Диалог установки класса.
    private static int printClassEnterDialog() {
        boolean classIsCorrect = false;
        int classId = 0;
        // Выводим диалог до тех пор, пока пользователь не введёт корректный класс.
        while (!classIsCorrect) {
            System.out.println("Введите класс:");
            scanner.nextLine();
            classId = scanner.nextInt();

            if (classService.isClassExist(classId)) {
                // Проверяем, что для класса вообще создан хотя бы один журнал.
                if (!classbooksService.checkIsClassbookExist(classId, null)) {
                    System.out.println("Для введённого класса не создано ни одного журнала :(");
                }
                else {
                    classIsCorrect = true;
                }
            }
            else {
                System.out.println("Введённого класса не существует. Пожалуйста, введите корректный.");
            }
        }
        return classId;
    }

    // Диалог установки предмета.
    private static String printSubjectEnterDialog(int classId) {
        boolean subjectIsCorrect = false;
        String subjectName = new String();

        // Выводим диалог до тех пор, пока пользователь не введёт корректный предмет.
        while (!subjectIsCorrect) {
            System.out.println("Введите название предмета:");
            scanner.nextLine();
            subjectName = scanner.nextLine();

            // Проверяем существование предмета.
            if (subjectsService.getSubjectByName(subjectName) == null) {
                System.out.println("Введённого предмета не существует. Пожалуйста, введите корректный.");
                continue;
            }

            // Проверяем существование журнала для этого класса и предмета.
            if (classbooksService.checkIsClassbookExist(classId, subjectName))
            {
                subjectIsCorrect = true;
            }
            else {
                System.out.println("Для указанного класса не существует журнала по этому предмету. Пожалуйста, введите предмет из существующего журнала.");
                pringClassbooksInfo();
            }
        }
        return subjectName;
    }

    // Диалог установки ФИО ученика.
    private static String printPupilNameEnterDialog(int classId) {
        boolean pupiltIsCorrect = false;
        String pupilName = new String();
        // Выводим диалог до тех пор, пока пользователь не введёт существующие ФИО.
        while (!pupiltIsCorrect) {
            System.out.println("Введите ФИО ученика:");
            pupilName = scanner.nextLine();

            if (classService.isPupilExistInClass(classId, pupilName)) {
                pupiltIsCorrect = true;
            }
            else {
                System.out.println("Введённого ученика не существует или указан неверный класс. Пожалуйста, введите верные данные.");
            }
        }
        return pupilName;
    }

    // Диалог установки оценки.
    private static int printRateEnterDialog() {
        boolean rateIsCorrect = false;
        int rate = 0;
        // Выводим диалог до тех пор, пока пользователь не введёт корректную оценку.
        while (!rateIsCorrect) {
            System.out.println("Введите оценку:");
            rate = scanner.nextInt();

            if (rate < 1 || rate > 5) {
                System.out.println("Введена некорректная оценка. Пожалуйста, введите верную.");
            }
            else {
                rateIsCorrect = true;
            }
        }
        return rate;
    }

    // Диалог установки даты оценки.
    private static Date printRateDateEnterDialog() {
        boolean rateDateIsCorrect = false;
        Date rateDate = new Date();
        // Выводим диалог до тех пор, пока пользователь не введёт корректную дату в нужном формате.
        while (!rateDateIsCorrect) {
            System.out.println("Введите дату оценки (формат dd.mm.yyyy):");
            scanner.nextLine();
            String rawRateDate = scanner.nextLine();

            try {
                //rateDate = new SimpleDateFormat("dd.MM.yyyy").parse(rawRateDate);
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                format.setLenient(false);
                rateDate = format.parse(rawRateDate);
                rateDateIsCorrect = true;
            } catch (ParseException e) {
                System.out.println("Введена некорректная дата или в неверном формате. Пожалуйста, введите корректную дату согласно формату");
            }
        }
        return rateDate;
    }

    // Метод для вывода существующих журналов.
    private static void pringClassbooksInfo() {
        System.out.println("Текущий список журналов:");
        for(Map.Entry<Classbook, Subject> entry : classbooksService.getClassbooks().entrySet()) {
            System.out.format(
                    String.format("Класс %d, предмет %s.",
                            entry.getKey().getClassId(),
                            entry.getValue().getName()));
            System.out.println();
        }
    }
}