import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FilterGml {
    public List<Node> nodes = new ArrayList<>();
    public List<Edge> edges = new ArrayList<>();
    private String path;
    public FilterGml(String path){
        this.path = path;
    }
    public void readGraph(){
        try {
            FileInputStream inputStream = new FileInputStream(path);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
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
                    edges.add(edge);
//                    System.out.println("source:"+edge.getSource()+"\t"+"target:"+edge.getTarget());
                }

            }
            bufferedReader.close();
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeGraph(String path) throws FileNotFoundException,UnsupportedEncodingException,IOException{
        if(edges!=null){
        FileOutputStream outputStream = new FileOutputStream(path);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
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
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
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
            }
            if(edge.getSource()==id){
                targetEdge.add(edge);
            }

        }
        //创建新的依赖
        if(sourceEdge.size()!=0){
            for(int i  = 0;i<tmpEdge.size();i++) {
                Edge edge = tmpEdge.get(i);
                if (edge.getTarget() == id) {
                    edges.remove(edge);
                }
                if (edge.getSource() == id) {
                    sourceEdge.add(edge);
                    edges.remove(edge);
                }
            }


            for(int i = 0 ;i<sourceEdge.size();i++){
                    for(int j = 0;j<targetEdge.size();j++){
                        Edge edge = new Edge();
                        edge.setSource(sourceEdge.get(i).getSource());
                        edge.setTarget(targetEdge.get(j).getTarget());
                        edges.add(edge);
                    }
                }

        nodes.remove(node);}

    }

    public static void main(String[] args) throws IOException {
        String path = "D:\\data\\ml\\00ceaa5f8f9be7a9ce5ffe96b5b6fb2e7e73ad87c2f023db9fa399c40ac59b62.zip.gml";
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
            String lable = node.getLabel();
            String[] tmps =lable.split("->");
            if(!tmps[0].contains("Ljava")&&!tmps[0].contains("Landroid")){
                f.removeNode(node);
            }
        }
        f.writeGraph("C:\\Users\\BLG\\Desktop"+"\\Z.GML");
        System.out.println(edges.size());
        System.out.println(nodes.size());
    }
}
