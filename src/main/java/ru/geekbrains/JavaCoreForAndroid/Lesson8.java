package ru.geekbrains.JavaCoreForAndroid;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

/**
 * Сourse: java core for android
 * Faculty of Geek University Android Development
 *
 * @Author Student Dmitry Veremeenko aka StDimensiy
 * Group 24.12.2020
 * <p>
 * HomeWork for lesson 8
 * Created 10.02.2021
 * v2.0
 */
public class Lesson8 {
    private static final Scanner SCANNER = new Scanner(System.in);
    static ArrayList<File> dir = new ArrayList<>();
    static String str1 = "Верьте аль не верьте, а жил на белом свете Федот-стрелец, удалой молодец.\n" +
            "Был Федот ни красавец, ни урод, ни румян, ни бледен, ни богат, ни беден,\n" +
            "ни в парше, ни в парче, а так, вообче. aaabbs\n";
    static String str2 = "Служба у Федота — рыбалка да охота. Царю — дичь да рыба, Федоту — спасибо.\n" +
            "Гостей во дворце — как семян в огурце.\n" +
            "Один из Швеции, другой из Греции, третий с Гавай — и всем жрать подавай!\n";

    public static void main(String[] args) throws IOException {
        // Создаем "окружение"  информационное пространство в проекте для работы с файлами
        // туда мы и будем писать все новые файлы данного урока
        // за одно закрепим и опробуем методы которые узнали на лекции

        //создаем директорию
        File file = new File("vdvDir");
        //System.out.println("Тестовая директория объявлена: " + file);
        System.out.println("Тестовая директория создана: " + file.mkdir());

        // создаем несколько директорий
        file = new File("vdvDir\\vavTempFiles\\homework\\lesson8");
        //System.out.println("Тестовый путь объявлен: " + file);
        System.out.println("Тестовый путь создан: " + file.mkdirs());
        //запомним это решение как path
        File path = file;

        // Создадим самостоятельно пустой файл проекта для тестовых файлов проекта
        File emptyfile = new File(file + "\\vdvTest.html");
        //System.out.println("Тестовый файл объявлен: " + emptyfile);
        System.out.println("Тестовый файл создан: " + emptyfile.createNewFile());

        // Задача №1
        // Создаем два файла с разным текстом
        createNewFile(file + "\\part_1.txt", str1);
        createNewFile(file + "\\part_2.txt", str2);
        // Задача №2
        // Объединяем первый и второй файл (добавляем к первому второй
        combineFiles(file + "\\part_1.txt", file + "\\part_2.txt", file + "\\part_result.txt");

        //создаем еще несколько файлов и поддиректорий для тестирования поиска
        // создаем несколько директорий и набор похожих файлов в них
        File file2 = new File(file + "\\dir_1");
        System.out.println("Тестовый путь создан: " + file2.mkdir());
        createNewFile(file + "\\dir_1\\part_1.txt", str1);
        createNewFile(file + "\\dir_1\\part_2.txt", str2);
        combineFiles(file + "\\dir_1\\part_1.txt", file + "\\dir_1\\part_2.txt", file
                + "\\dir_1\\part_result.txt");

        file2 = new File(file + "\\dir_2");
        System.out.println("Тестовый путь создан: " + file2.mkdir());
        createNewFile(file + "\\dir_2\\part_1.txt", str1);
        createNewFile(file + "\\dir_2\\part_2.txt", str2);
        combineFiles(file + "\\dir_2\\part_1.txt", file + "\\dir_2\\part_2.txt", file
                + "\\dir_2\\part_result.txt");

        file2 = new File(file + "\\dir_1\\dir_1_1");
        System.out.println("Тестовый путь создан: " + file2.mkdir());
        createNewFile(file + "\\dir_1\\dir_1_1\\part_1.txt", str1);
        createNewFile(file + "\\dir_1\\dir_1_1\\part_2.txt", str2);
        combineFiles(file + "\\dir_1\\dir_1_1\\part_1.txt", file
                + "\\dir_1\\dir_1_1\\part_2.txt", file + "\\dir_1\\dir_1_1\\part_result.txt");

        //задание 3 проверяем наличие поисковой фразы в конкретном файле (ответ true или False)
        System.out.println("Задание №3. Метод поиска фрагмента \"вообче\" в файле " + file + "\\part_1.txt., вернул: "
                + isInFile(file + "\\part_1.txt", "вообче"));

        //задание 4
        System.out.println("Задание №4 - введите поисковую фразу и нажмите Enter");
        String searchPhrase;
        do {
            searchPhrase = SCANNER.nextLine();
        } while (searchPhrase.equals(""));
        SCANNER.close();
        System.out.println("Результаты поиска: '" + searchPhrase + "' в " + path);
        System.out.println("Обнаружено " + multiSearch(path, searchPhrase) + " файлов, содержащих поисковую фразу.");

    }

    // метод создает новый файл
    public static void createNewFile(String fileName, String str) {
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(str.getBytes());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    // метод объединяющий два файла в один с созданием нового файла результата
    // Создает третий файл объединяя первый и второй
    public static void combineFiles(String inFileName1, String inFileName2, String outFileName) {
        try (FileOutputStream fos = new FileOutputStream(outFileName);
             FileInputStream fis1 = new FileInputStream(inFileName1);
             FileInputStream fis2 = new FileInputStream(inFileName2)) {
            int oneByteTransport;
            while ((oneByteTransport = fis1.read()) != -1) //читаем побайтово пока не получим отказ
                fos.write(oneByteTransport);
            while ((oneByteTransport = fis2.read()) != -1) //читаем побайтово пока не получим отказ
                fos.write(oneByteTransport);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }

    }

    // промежуточный метод управляет буффером данных для поиска
    private static void putToArray(byte[] buffer, byte b) {
        for (int i = 0; i < buffer.length - 1; i++) {
            buffer[i] = buffer[i + 1]; //сначала смещаем все элементы влево
        }
        buffer[buffer.length - 1] = b; // добавляем в конец полученный байт
    }

    // метод ищет поисковую фразу в указанном файле
    private static boolean isInFile(String fileName, String searchPhrase) {
        try (FileInputStream fis = new FileInputStream(fileName)) {
            byte[] arrSearchS = searchPhrase.getBytes();           // перевожу поисковый запрос в набор байтов
            int oneByteTransport;
            int lengthSearchS = arrSearchS.length;
            byte[] buffer = new byte[lengthSearchS];               //инициализирую буфер в виде массива длиной равной длине поискового массива
            while ((oneByteTransport = fis.read()) != -1) {        //читаем побайтово пока не получим отказ
                putToArray(buffer, (byte) oneByteTransport);       //записываю данные в буфер
                if (Arrays.equals(arrSearchS, buffer)){
                    return true;
                }
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
        return false;
    }

    // метод осуществляет проверку наличия поисковой фразы в файлах внутри директории и всех вложенных поддиректорий
    private static int multiSearch(File path, String searchPhrase) {
        int success = 0;
        for (File elem : multiScan(path)) {
            if (isInFile(elem.getPath(), searchPhrase)) {
                System.out.println(elem.getAbsolutePath());
                success++;
            }
        }
        return success;
    }

    // Метод рекурсивно сканирует указанную директорию и возвращает список файлов для осуществления поиска.
    private static ArrayList<File> multiScan(File path) {
        for (File e : Objects.requireNonNull(path.listFiles())) {
            if (e.isDirectory()) multiScan(e);
            else dir.add(e);
        }
        return dir;
    }
}
