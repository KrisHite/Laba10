package Examples;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class Example_8 {
    public static void main(String[] args) throws IOException {
        //Открываю файл
        String filePath = "src/Examples/example_excel.xlsx";
        FileInputStream inputStream = new FileInputStream(filePath);
        //Создаю экземпляр книги Excel из файла
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        //Получаю лист из книги по его имени
        XSSFSheet sheet = workbook.getSheet("товары");
        //Перебираю строки и ячейки листа
        for(Row row : sheet){
            for(Cell cell : row){
                //Вывожу знаяение ячейки
                System.out.print(cell.toString() + "\t");
            }
            System.out.println();
        }
        workbook.close();
        inputStream.close();
    }
}
