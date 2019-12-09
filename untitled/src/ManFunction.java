import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManFunction {
    public static void main(String[] args) throws IOException {
        String path = "D:\\data\\ml";
        String dest = "D:\\data\\afterfilterml";
        File dir = new File(path);
        if(dir.isDirectory()){
            File next[] = dir.listFiles();
             for(File file:next){
                 try {
                     FilterGml f = new FilterGml(path+"\\"+file.getName());
                     f.readGraph();
                     List<Node> nodes = f.nodes;
                     List<Edge> edges = f.edges;
                     HashMap<String,Edge> map = f.edgeHashMap;
                     System.out.print(nodes.size()+"  "+edges.size());
                     List<Node> tmpNode = new ArrayList<>();
                     tmpNode.addAll(nodes);
                     for(int i = 0;i<tmpNode.size();i++){
                         Node node = tmpNode.get(i);
                         String label = node.getLabel();
                         String[] tmps =label.split("->");
                         if(!tmps[0].contains("Ljava")&&!tmps[0].contains("Landroid")){
//                         System.out.println("deleting ..."+System.currentTimeMillis());
                             f.removeNode(node);
                         }
                     }
                     String name[]=file.getName().split(".zip");
                     f.writeGraph(dest+"\\"+name[0]+".gml");
                     System.out.print(" "+nodes.size()+"  "+edges.size());
                     System.out.println("done");
                     nodes.clear();
                     edges.clear();
                     map.clear();
                 }catch (OutOfMemoryError E){
                     System.out.println("error"+file.getName());

                 }

             }

        }

    }
}

