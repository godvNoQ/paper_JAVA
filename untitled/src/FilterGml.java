import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class FilterGml {

    public static List<Node> nodes = new ArrayList<>();
    public static List<Edge> edges = new ArrayList<>();
    public static HashMap<String,Edge> edgeHashMap = new HashMap<>();
    private String path;
    public FilterGml(String path){
        this.path = path;
    }
    public void readGraph(){
        BufferedReader bufferedReader = null;
        try {
            FileInputStream inputStream = new FileInputStream(path);
             bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"),1024);
            String str = null;
            while ((str=bufferedReader.readLine())!=null){
                if(str.contains("node")){
                    Node node = new Node();
                    //读取ID
                    str = bufferedReader.readLine();
                    node.setId(Integer.parseInt (str.substring(7)));
                    //截取label
                    str = bufferedReader.readLine();
                    node.setLabel(str.substring(11));
                    //截取出度
                    str = bufferedReader.readLine();
                    node.setExternal(Integer.parseInt(str.substring(13)));
                    //截取入度
                    str = bufferedReader.readLine();
                    node.setEntrypoint(Integer.parseInt(str.substring(15)));
                    nodes.add(node);
                   // System.out.println("xixi");
                }
                if(str.contains("edge")){
                    Edge edge = new Edge();
                    str =bufferedReader.readLine();
                    edge.setSource(Integer.parseInt(str.substring(11)));
                    str =bufferedReader.readLine();
                    edge.setTarget(Integer.parseInt(str.substring(11)));
                    if(!edgeHashMap.containsKey(edge.getSource()+","+edge.getTarget())){
                    edges.add(edge);
                    edgeHashMap.put(edge.getSource()+","+edge.getTarget(),edge);}
//                    System.out.println("source:"+edge.getSource()+"\t"+"target:"+edge.getTarget());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader!=null){
                try {
                    bufferedReader.close();

                }catch (IOException E){
                    E.printStackTrace();
                }
            }

        }
    }
    public void processLabel(HashMap<String,Integer> map){
        for (Node node:nodes){
            String label = node.getLabel();
            String[] tmp = label.split("->");
            for(String t:tmp){
                if(!t.contains("Landroid"))
                    continue;
                else{
                    String TmpLabel = t.split("Landroid",2)[1];
                    TmpLabel = TmpLabel.split(";",2)[0];
                    TmpLabel = "android"+TmpLabel;
                    TmpLabel = TmpLabel.replace("/",".");
                    TmpLabel = TmpLabel.replace("$",".");
                    try {
                        node.setLabel(map.get(TmpLabel).toString());
                    }catch (NullPointerException  e){
                        System.out.println(TmpLabel+"       "+node.getId());

                    }

                }

            }
        }
    }

    public void writeGraph(String path){
        if(edges!=null){
            BufferedWriter bufferedWriter = null;
            try {
                FileOutputStream outputStream = new FileOutputStream(path);
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                bufferedWriter.write("graph [");
                bufferedWriter.newLine();
                bufferedWriter.write("  directed 1");
                bufferedWriter.newLine();
                for(int i = 0;i<nodes.size();i++){
                    bufferedWriter.write("  node [");
                    bufferedWriter.newLine();
                    bufferedWriter.write("    id "+nodes.get(i).getId());
                    bufferedWriter.newLine();
                    bufferedWriter.write("    label \""+nodes.get(i).getLabel());
                    bufferedWriter.newLine();
                    bufferedWriter.write("    external "+nodes.get(i).getExternal());
                    bufferedWriter.newLine();
                    bufferedWriter.write("    entrypoint "+nodes.get(i).getEntrypoint());
                    bufferedWriter.newLine();
                    bufferedWriter.write("  ]");
                    bufferedWriter.newLine();
                }
                for(int i = 0;i<edges.size();i++){
                    bufferedWriter.write("  edge [");
                    bufferedWriter.newLine();
                    bufferedWriter.write("    source "+edges.get(i).getSource());
                    bufferedWriter.newLine();
                    bufferedWriter.write("    target "+edges.get(i).getTarget());
                    bufferedWriter.newLine();
                    bufferedWriter.write("  ]");
                    bufferedWriter.newLine();
                }
                bufferedWriter.write("]");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(bufferedWriter!=null){
                    try {
                        bufferedWriter.flush();
                        bufferedWriter.close();
                    }catch (IOException E){
                        E.printStackTrace();
                    }
               }
            }

        }

    }
    public void removeNode(Node node){
        int id  = node.getId();
        List<Edge> sourceEdge = new ArrayList<>();
        List<Edge> targetEdge = new ArrayList<>();
        //删除依赖EDGE
        List<Edge> tmpEdge = new ArrayList<>();
        tmpEdge.addAll(edges);
        for(int i  = 0;i<tmpEdge.size();i++){
            Edge edge = tmpEdge.get(i);
            if(edge.getTarget() == id) {
                sourceEdge.add(edge);
                edges.remove(edge);
                edgeHashMap.remove(edge.getSource()+","+edge.getTarget());
            }
            if(edge.getSource()==id){
                targetEdge.add(edge);
                edges.remove(edge);
                edgeHashMap.remove(edge.getSource()+","+edge.getTarget());
            }

        }
//        //创建新的依赖
//        if(sourceEdge.size()!=0&&targetEdge.size()!=0){
//            for(int i  = 0;i<tmpEdge.size();i++) {
//                Edge edge = tmpEdge.get(i);
//                if (edge.getTarget() == id) {
//                    edges.remove(edge);
//                    edgeHashMap.remove(edge.getSource()+","+edge.getTarget());
//                }
//                if (edge.getSource() == id) {
//                    sourceEdge.add(edge);
//                    edges.remove(edge);
//                    edgeHashMap.remove(edge.getSource()+","+edge.getTarget());
//                }
//          }


            for(int i = 0 ;i<sourceEdge.size();i++){
                    for(int j = 0;j<targetEdge.size();j++){
                        Edge edge = new Edge();
                        edge.setSource(sourceEdge.get(i).getSource());
                        edge.setTarget(targetEdge.get(j).getTarget());
                        if(!edgeHashMap.containsKey(edge.getSource()+","+edge.getTarget())){
                            edges.add(edge);
                            edgeHashMap.put(edge.getSource()+","+edge.getTarget(),edge);}
                    }
                }



//        edges.removeAll(sourceEdge);
//        edges.removeAll(targetEdge);
        nodes.remove(node);
    }


    public static void main(String[] args) throws IOException {
        GetAndroidApi getAndroidApi = new GetAndroidApi("C:\\Users\\LY\\Desktop\\a.txt");
        getAndroidApi.getApi();
        String path = "D:\\LY\\恶意文件\\000a067df9235aea987cd1e6b7768bcc1053e640b267c5b1f0deefc18be5dbe1.zip.gml";
        FilterGml f = new FilterGml(path);
        f.readGraph();
        List<Node> nodes = f.nodes;
        List<Edge> edges = f.edges;
        System.out.println(edges.size());
        System.out.println(nodes.size());
        List<Node> tmpNode = new ArrayList<>();
        tmpNode.addAll(nodes);
        for(int i = 0;i<tmpNode.size();i++){
            Node node = tmpNode.get(i);
            String label = node.getLabel();
            String[] tmps =label.split("->");
            if(!tmps[0].contains("Landroid")&&!tmps[1].contains("Landroid")){
                f.removeNode(node);

            }
        }
        f.processLabel(getAndroidApi.getApiClassData());
        f.writeGraph("C:\\Users\\LY\\Desktop"+"\\Z.GML");
        System.out.println(edges.size());
        System.out.println(nodes.size());
        System.out.println(getAndroidApi.getApiClassData().size());
    }
}
