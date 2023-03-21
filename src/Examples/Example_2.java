package Examples;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
public class Example_2 {
    public static void main(String[] args) {
        try{
            File inFile = new File("src\\Examples\\example_1.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
            Document doc = dbBuilder.parse(inFile);
            doc.getDocumentElement().normalize();
            System.out.println("Корневой эллемент" + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("book");
            for(int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                System.out.println("\nТекущий эллемент: " + node.getNodeName());
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element elem = (Element) node;
                    System.out.println("Название книги: "
                            + elem.getElementsByTagName("title").item(0).getTextContent());
                    System.out.println("Автор: "
                            + elem.getElementsByTagName("author").item(0).getTextContent());
                    System.out.println("Год издания: "
                            + elem.getElementsByTagName("year").item(0).getTextContent());
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
