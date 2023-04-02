package Tasks;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Task_2 {
    public static void main(String[] args) {
        String filePath = "src/Tasks/resources/Library.json";//Путь к json файлу
        Scanner in = new Scanner(System.in);
        System.out.println("Какие действия в XML файлом библиотеки вы хотите совершить?(add, search, delete)");
        String command = in.nextLine();
        String bookName, author, year;
        switch (command) {
            case "add"://Добавление книги в библиотеку
                System.out.println("Введите название книги: ");
                bookName = in.nextLine();
                System.out.println("Введите автора: ");
                author = in.nextLine();
                System.out.println("Введите год: ");
                year = in.nextLine();
                AddBookJson(filePath, bookName, author, year);
                break;
            case "search"://Поиск книг в библиотеке
                System.out.println("Введите автора искомой книги: ");
                author = in.nextLine();
                SearchBookJson(filePath, author);
                break;
            case "delete"://Удаление книги из библиотеки
                System.out.println("Введите название книги, которую хотите удалить: ");
                bookName = in.nextLine();
                DeleteBookJson(filePath, bookName);
                break;
            default://Все остальное
                System.out.println("Такой команды нет!");
                break;
        }
    }
    private static void AddBookJson(String filePath, String bookName, String author, String year){
        try{
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filePath));
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray books = (JSONArray) jsonObject.get("books");

            JSONObject newBook = new JSONObject();
            newBook.put("title", bookName);
            newBook.put("author", author);
            newBook.put("year", year);
            books.add(newBook);
            jsonObject.put("books", books);
            //Записываем назад (Без try Записывает пустой файл)
            try(FileWriter file = new FileWriter(filePath)){
                file.write(jsonObject.toJSONString());
                System.out.println("Книга успешно добавлена!");
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private static void SearchBookJson(String filePath, String author){
        boolean flag = false;
        try{
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filePath));//Считываю файл и запихиваю в obj
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray jsonArray = (JSONArray) jsonObject.get("books");
            Iterator iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject book = (JSONObject) iterator.next();
                if (author.equals(book.get("author"))) {
                    System.out.println("Искомая книга: ");
                    System.out.println("Автор: " + book.get("author"));
                    System.out.println("Название: " + book.get("title"));
                    System.out.println("Год: " + book.get("year"));
                    flag = true;
                }
                if(!iterator.hasNext() && flag == false){
                    System.out.println("Нет такой книги!");
                    System.exit(0);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
        }


    }
    private static void DeleteBookJson(String filePath, String bookName){
        try{
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader(filePath));//Читаю файл
            JSONObject jsonObject = (JSONObject) obj;//JSON который надо записать
            JSONArray jsonArray = (JSONArray) jsonObject.get("books");
            //Перебираем эллементы и удаляем нужный
            Iterator iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject book = (JSONObject) iterator.next();
                if (bookName.equals(book.get("title"))) {
                    iterator.remove();
                    break;
                }
                if(!iterator.hasNext()){
                    System.out.println("Нет такой книги!");
                    System.exit(0);
                }
            }
            //Записываю назад
            try(FileWriter file = new FileWriter(filePath)){
                file.write(jsonObject.toJSONString());
                System.out.println("Книга успешно удалена!");
            }catch(Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}








