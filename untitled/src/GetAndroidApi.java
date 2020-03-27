import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class GetAndroidApi {
    private String path;
    private int flag = 0;
    private String father = null;
    private HashMap<String,Integer> apiClassData = new HashMap<>();
    private HashMap<String,Integer> apiInterfaceData = new HashMap<>();
    private List<String> classInfo = new ArrayList<>();
    private List<String> interfaceInfo = new ArrayList<>();
    public  GetAndroidApi(String path){
        this.path = path;

    }


    public HashMap<String, Integer> getApiClassData() {
        return apiClassData;
    }
    public void getApi() throws IOException {
        FileInputStream inputStream = new FileInputStream(this.path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        String str=null;
        while ((str = reader.readLine())!=null){
            if(str.equals("Interfaces")) {
                flag = 2;
                continue;
            }
            else if(str.equals("Classes")) {
                flag = 1;
                continue;
            }
            if(str.equals("概览")&&flag==0)
                continue;
            else if(str.equals("概览")&&flag==1){
                father = classInfo.get(classInfo.size()-1).substring(father.length()+1);
                classInfo.remove(classInfo.size()-1);
                continue;
            }else if(str.equals("概览")&&flag==2){
                father= interfaceInfo.get(interfaceInfo.size()-1).substring(father.length()+1);

                interfaceInfo.remove(interfaceInfo.size()-1);
                continue;
            }
            if(flag==0) {
                father = str;
                continue;
            }
            if(flag==1){
                classInfo.add(father+'.'+str);
            }
            if(flag==2){
                interfaceInfo.add(father+'.'+str);
            }
        }
        for(int i=0;i<classInfo.size();i++){
            apiClassData.put(classInfo.get(i),i);

        }
        for(int i=0;i<interfaceInfo.size();i++){

            apiClassData.put(interfaceInfo.get(i),i+classInfo.size());

        }
    }
    public void write(String p){
        BufferedWriter bufferedWriter = null;
        try {
            FileOutputStream outputStream = new FileOutputStream(p);
           bufferedWriter  = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"),1024);
            for(int i = 0;i<classInfo.size();i++){

                    bufferedWriter.write(classInfo.get(i)+"    "+i);
                    bufferedWriter.newLine();
            }
            for(int i = 0;i<interfaceInfo.size();i++){
                int tmp=i+classInfo.size();
                bufferedWriter.write(interfaceInfo.get(i)+"    "+tmp);
                bufferedWriter.newLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if ( bufferedWriter   != null) {
                try {
                    //刷新缓存区
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException e) {
                   e.printStackTrace();
                }
            }
        }



    }

    public static void main(String[] args) throws IOException {
        GetAndroidApi getAndroidApi = new GetAndroidApi("C:\\Users\\LY\\Desktop\\a.txt");
        getAndroidApi.getApi();
//        getAndroidApi.write("C:\\Users\\LY\\Desktop\\b.txt");
        System.out.println(getAndroidApi.getApiClassData().size());

//        System.out.println(getAndroidApi.classInfo.size()+"?"+getAndroidApi.interfaceInfo.size());
//        System.out.println(getAndroidApi.classInfo.size());
//        System.out.println(getAndroidApi.classInfo.get(3988));

//        for (int i = 0; i < getAndroidApi.classInfo.size(); i++) {
//            System.out.println(getAndroidApi.classInfo.get(i));
//
//        }
//        for (int i = 0; i < getAndroidApi.interfaceInfo.size(); i++) {
//            System.out.println(getAndroidApi.interfaceInfo.get(i));
//
//        }
    }
}

