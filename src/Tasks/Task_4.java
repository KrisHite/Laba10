package Tasks;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Task_4 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите имя файла(или введите exit для выхода): ");
        String fileName = in.nextLine();
        if(fileName.equals("exit")){
            System.out.println("Досвидания!");
            System.exit(0);
        }
        String filePath = "src/Tasks/resources/" + fileName;
        while(true){
        try{
            System.out.println("Открываем...");
            FileInputStream inputStream = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            System.out.println("Файл открыт.");
            System.out.print("Введите название страницы для отображения(или введите exit для выхода): ");
            String sheetName = in.nextLine();
            if(sheetName.equals("exit")){
                System.out.println("Досвидания!");
                System.exit(0);
            }
            while (true){
                try{
                    XSSFSheet sheet = workbook.getSheet(sheetName);
                    for(Row row : sheet){
                        for(Cell cell : row){
                            System.out.print(cell.toString() + "\t");
                        }
                        System.out.println();
                    }
                    break;
                }catch (NullPointerException nullEx){//Обработка исключения если отсутствуем страница
                    System.out.print("Страница не найдена!\n" +
                            "Пожалуйста введите название страницы(или введите exit для выхода): ");
                    sheetName = in.nextLine();
                    if(sheetName.equals("exit")){
                        System.out.println("Досвидания!");
                        System.exit(0);
                    }
                }
            }
            workbook.close();//Закрываю потоки
            inputStream.close();
            break;
        } catch (FileNotFoundException nFile){//Обработка исключения если файл не найден
            System.out.print("Файл не найден. Введите имя еще раз(или введите exit для выхода): ");
            fileName = in.nextLine();
            if(fileName.equals("exit")){
                System.out.println("Досвидания!");
                System.exit(0);
            }
            filePath = "src/Tasks/resources/" + fileName;
        } catch (IOException e){//Ошибка ввода вывода
            System.out.println("Какая-то ошибка " + e);
        }catch (NotOfficeXmlFileException notOffice){//Обработка исключения если файл не Excel
            System.out.print("Это не файл Excel! Введите имя EXCEL файла или exit для выхода: ");
            fileName = in.nextLine();
            if(fileName.equals("exit")){
                System.out.println("Досвидания!");
                System.exit(0);
            }
            filePath = "src/Tasks/resources/" + fileName;
        }
    }
}

}
