import java.util.Scanner;

public class b{
/**
 * 打扑克 有0-9分别代表A,2,3,4,5,6,7,8,9,10
 *并且每种牌的数量0-4
 * 输入数组a【10】,a[0]代表A的张数
 * 可以打联队 AABBCC,单张，一对，连排 A2345
 * 求最快打完需要多少步
 * **/
    private static int minTime=65535;
    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        int[] a = new int[10];
        int[] a = {2,2,2,1,1,1,1,1,1,1};
        int totalNum=0;
//        for(int i = 0;i<10;i++){
//            a[i]=in.nextInt();
//            totalNum+=a[i];
//        }
        push(a,13,0);
        System.out.println(minTime);

    }
    public static void push(int[] a,int total,int times){
            if(total==0){
                if(times<minTime){
                    minTime=times;
                }
                return;
            }

            for(int i =0;i<10;i++){
                //出单张
                int[] tmp = a.clone();
                if(tmp[i]<1){
                    continue;
                }
                tmp[i]=tmp[i]-1;
                push(tmp,total-1,times+1);


                //出一对
                if(tmp[i]>1){
                    int[] two = a.clone();
                    two[i]=two[i]-2;
                    push(two,total-2,times+1);
                }
                //出顺子
                int a_=i;
                for(int j =0;j<8;j++){
                    int[] five = a.clone();
                    if(a_>=0 && a_+4<10 && five[a_]!=0 && five[a_+1]!=0&& five[a_+2]!=0&& five[a_+3]!=0 && five[a_+4]!=0) {
                        five[a_] = five[a_] - 1;
                        five[a_ + 1] = five[a_ + 1] - 1;
                        five[a_ + 2] = five[a_ + 2] - 1;
                        five[a_ + 3] = five[a_ + 3] - 1;
                        five[a_ + 4] = five[a_ + 4] - 1;
                        push(five, total - 5, times + 1);
                    }
                    a_--;
                    }
                    //出联队

                for(int j =0;j<4;j++){
                        int[] six=a.clone();
                        if(a_>=0 && a_+4<10 && six[a_]>1 && six[a_+1]>10&& six[a_+2]>10){
                            six[a_]=six[a_]-2;
                            six[a_+1]=six[a_+1]-2;
                            six[a_+2]=six[a_+2]-2;

                            push(tmp,total-6,times+1);
                        }
                    a_--;


                    }


                }

            }

    }


