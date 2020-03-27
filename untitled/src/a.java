import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 阿里笔试题
 * 将A插入B中，在新的数组中a[i]的下表必须小于a[i+1]
 * 然后新数组，两两相乘，取得最小结果
 * **/
public class a {

    private static List<List<Integer>> total = new ArrayList<>();
    private static int sum = 65535;
    private static List<Integer> bestRanks = new ArrayList<>();
   // n为剩余b个数
    public static void main(String[] args) {


        long start=System.currentTimeMillis();
//code

        int[] A={0,1,2,3,4};
        int[] B={5,6,7,8,9};
        List<Integer> B__ = new ArrayList<>();
        for(int k =0;k<B.length;k++){
            B__.add(B[k]);
        }
        List<Integer> A__ = new ArrayList<>();
        for(int k =0;k<A.length;k++){
            A__.add(A[k]);
        }


        List<Integer> tmp = new ArrayList<>();
//        minSum(A.length,tmp,A.length);
        minSum(5,tmp,5);

        System.out.println(total.size());
        for(int i=0;i<total.size();i++){
            List<Integer> cal = new ArrayList<>();
            List<Integer> b = new ArrayList<>();
            List<Integer> a = new ArrayList<>();
            b.addAll(B__);
            a.addAll(A__);
            for(int j=0;j<total.get(i).size();j++){
                if(total.get(i).get(j)==0){
                    cal.add(a.get(0));
                    a.remove(0);
                }
                if(total.get(i).get(j)==1){
                    cal.add(b.get(0));
                    b.remove(0);
                    cal.add(a.get(0));
                    a.remove(0);
                }
                if(total.get(i).get(j)==2){
                    cal.add(b.get(0));
                    b.remove(0);
                    cal.add(b.get(0));
                    b.remove(0);
                    cal.add(a.get(0));
                    a.remove(0);

                }
                if(total.get(i).get(j)==3){
                    cal.add(b.get(0));
                    b.remove(0);
                    cal.add(b.get(0));
                    b.remove(0);
                    cal.add(b.get(0));
                    b.remove(0);
                    cal.add(a.get(0));
                    a.remove(0);

                }
                if(total.get(i).get(j)==4){
                    cal.add(b.get(0));
                    b.remove(0);

                    cal.add(b.get(0));
                    b.remove(0);

                    cal.add(b.get(0));
                    b.remove(0);

                    cal.add(b.get(0));
                    b.remove(0);

                    cal.add(a.get(0));
                    a.remove(0);

                }
                if(total.get(i).get(j)==5){
                    cal.add(b.get(0));
                    b.remove(0);

                    cal.add(b.get(0));
                    b.remove(0);

                    cal.add(b.get(0));
                    b.remove(0);

                    cal.add(b.get(0));
                    b.remove(0);

                    cal.add(b.get(0));
                    b.remove(0);

                    cal.add(a.get(0));
                    a.remove(0);

                }


                }
            if(a.size()!=0){
                cal.addAll(a);
            }
            if(b.size()!=0){
                cal.addAll(b);
            }
             int to =1;
           for(int z =0;z<(cal.size()-1);z+=2){
               to+=cal.get(z)*cal.get(z+1);

           }
           if(sum>to){
               sum=to;
               bestRanks.clear();
               bestRanks.addAll(cal);
           }

        }
        System.out.println(sum);
        for(Integer p:bestRanks){
            System.out.print(p);
        }
        System.out.println("");
        long end=System.currentTimeMillis();
        System.out.println(start-end);
    }

    static void minSum(int n,List<Integer> a,int maxSize) {
        if(n==0||a.size()==maxSize){
            total.add(a);
            return;
        }

        for (int i = 0; i <= n; i++) {
                List<Integer> b = new ArrayList<>();
                b.addAll(a);
                b.add(i);
                minSum(n-i,b,maxSize);
            }

    }
}
