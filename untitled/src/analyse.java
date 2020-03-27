import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.*;

public class analyse {
    String path = "source.txt";
    File file = new File(path);
    Long fileLength = file.length();
    byte[] fileContent = new byte[fileLength.intValue()];

    {
        try {

            FileInputStream  in = new FileInputStream(path);
            in.read(fileContent);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    String html;

    {
        try {
            html = new String(fileContent,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    Document doc = Jsoup.parse(html);


}
