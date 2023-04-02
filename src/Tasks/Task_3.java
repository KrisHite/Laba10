package Tasks;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

public class Task_3 {
    public static void main(String[] args) throws InterruptedException {
        short numOfConnection = 3;//Число попыток подключения
        String filePath = "src/Tasks/resources/News.json";

        while (numOfConnection > 0){
            try{
                System.out.println("Подключение...");
                Document doc = Jsoup.connect("http://fat.urfu.ru/index.html").get();//Снова новости URFU
                System.out.println("Подключение завершено.");
                Elements newsParent = doc
                        .select("body > table > tbody > tr > td > div > table > tbody > tr:nth-child(5) > td:nth-child(3)" +
                                " > table > tbody> tr > td:nth-child(1)");

                JSONObject fileOfNews = new JSONObject();
                JSONArray news = new JSONArray();

                for(int i = 3; i < 20; i++){
                    if(!(i % 2 == 0)){
                        List<Node> nodes = newsParent.get(0).childNodes();
                        System.out.println("Тема: " +
                                ((Element) nodes.get(i))
                                        .getElementsByClass("blocktitle")
                                        .get(0).childNodes().get(0));
                        System.out.println("Дата: " +
                                ((Element) nodes.get(i))
                                        .getElementsByClass("blockdate")
                                        .get(0).childNodes().get(0) + "\n");
                        JSONObject newNews = new JSONObject();

                        newNews.put("text", ((Element) nodes.get(i))
                                .getElementsByClass("blocktitle")
                                .get(0).childNodes().get(0));
                        newNews.put("data", ((Element) nodes.get(i))
                                .getElementsByClass("blockdate")
                                .get(0).childNodes().get(0) + "\n");
                        news.add(newNews);
                    }
                }
                fileOfNews.put("news", news);

                try(FileWriter file = new FileWriter(filePath)){
                    file.write(fileOfNews.toJSONString());
                    System.out.println("Новости записаны в файл!");
                }catch(Exception e){
                    e.printStackTrace();
                }

                break;
            } catch(UnknownHostException host){
                numOfConnection--;
                if(numOfConnection <= 0){
                    System.out.println("Отсутствует подключение!" + host);
                    System.exit(0);
                }
                System.out.println("Ошибка подключения!");
                Thread.sleep(1000);
                System.out.println("Переподключение через: ");
                Thread.sleep(1000);
                for(int i = 3; i <= 0; i--){
                    System.out.println(i);
                    Thread.sleep(1000);
                }
            } catch(IOException e){
                e.printStackTrace();
            }

        }
        //Запись в файл новостей



    }
}
