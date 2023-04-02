package Tasks;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Scanner;

public class Task_1 {
    public static void main(String[] args) {
        String filePath = "src/Tasks/resources/Library.xml";//Путь к файлу
        Scanner in = new Scanner(System.in);
        System.out.println("Какие действия в XML файлом библиотеки вы хотите совершить?(add, search, delete)");
        String command = in.nextLine();
        String bookName, author, year;
        switch (command){
            case "add"://Добавление книги в библиотеку
                System.out.println("Введите название книги: ");
                bookName = in.nextLine();
                System.out.println("Введите автора: ");
                author = in.nextLine();
                System.out.println("Введите год: ");
                year = in.nextLine();
                AddBook(filePath, bookName, author, year);
                break;
            case "search"://Поиск книг в библиотеке
                System.out.println("Введите автора искомой книги(Если не знаете оставьте поле пустым): ");
                author = in.nextLine();
                System.out.println("Введите год искомой книги(Если не знаете оставьте поле пустым): ");
                year = in.nextLine();
                if(author == null && year == null){
                    System.out.println("Поиск без данных невозможен!");
                    break;
                }
                SearchBook(filePath, author, year);
                break;
            case "delete"://Удаление книги из библиотеки
                System.out.println("Введите название книги, которую хотите удалить: ");
                bookName = in.nextLine();
                DeleteBook(filePath, bookName);
                break;
            default://Все остальное
                System.out.println("Такой команды нет!");
                break;
        }
    }
    //Тут методы лоя работы с XML библиотекой
    private static void AddBook(String filePath, String bookName, String author, String year){
        try{
            File inFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(inFile);
            doc.getDocumentElement().normalize();//Пока так

            Element newList = doc.getDocumentElement();

            Element newBook = doc.createElement("book");//создание новой книги

            Element title = doc.createElement("title");
            title.appendChild(doc.createTextNode(bookName));
            newBook.appendChild(title);

            Element authorOfBook = doc.createElement("author");
            authorOfBook.appendChild(doc.createTextNode(author));
            newBook.appendChild(authorOfBook);

            Element yearOfBook = doc.createElement("year");
            yearOfBook.appendChild(doc.createTextNode(year));
            newBook.appendChild(yearOfBook);

            newList.appendChild(newBook);//Добавляем новую книгу в библиотеку
            //ниже кусок кода для преобразования в XML
            doc.setXmlStandalone(true);
            doc.normalizeDocument();
            TransformerFactory tf = javax.xml.transform.TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
            StreamResult result =
                    new StreamResult(new File(filePath));
            transformer.transform(source, result);
            System.out.println("Книга добавлена");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void SearchBook(String filePath, String author, String year){
        try{
            File inFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(inFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("book");

            for(int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element elem = (Element) node;
                    String nodeAuthor = elem.getElementsByTagName("author").item(0).getTextContent();
                    String nodeYear = elem.getElementsByTagName("year").item(0).getTextContent();
                    if(nodeAuthor.equals(author) || nodeYear.equals(year)){
                        System.out.println("Искомая книга: ");
                        System.out.println("Название книги: "
                                + elem.getElementsByTagName("title").item(0).getTextContent());
                        System.out.println("Автор: "
                                + elem.getElementsByTagName("author").item(0).getTextContent());
                        System.out.println("Год издания: "
                                + elem.getElementsByTagName("year").item(0).getTextContent());

                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void DeleteBook(String filePath, String bookName){
        try{
            File inFile = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(inFile);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getElementsByTagName("book");

            for(int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
               // System.out.println(node.getTextContent());
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element elem = (Element) node;
                    String text = elem.getElementsByTagName("title").item(0).getTextContent();
                    if(text.equals(bookName)){
                        Node parentNode = node.getParentNode();
                        parentNode.removeChild(node);
                        break;
                    }
                }
            }
            // Далее нужно записать в файл
            doc.setXmlStandalone(true);
            doc.normalizeDocument();
            TransformerFactory tf = javax.xml.transform.TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.STANDALONE, "yes");
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            DOMSource source = new javax.xml.transform.dom.DOMSource(doc);
            StreamResult result =
                    new StreamResult(new File(filePath));
            transformer.transform(source, result);
            System.out.println("Книга " + bookName + " удалена");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

