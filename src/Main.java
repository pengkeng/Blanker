import java.util.Arrays;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        int n,m;
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入n和m");
        n = scanner.nextInt();
        m = scanner.nextInt();

        /*变量定义*/
        int[] Available  = new int[m];
        int[][] Max  = new int[n][m];
        int[][] Allocation  = new int[n][m];
        int[][] Need  = new int[n][m];
        int[] safequeue = new int[n];
        int process;
        int safequeueindex = 0;
        int[] Request  = new int[m];
        boolean StepOne = true;
        boolean StepTwo = true;

        /*数据输入*/
        for (int i = 0; i < m; i++) { Available[i] = scanner.nextInt(); }
        for (int i = 0; i < m; i++) { System.out.print(Available[i]+" "); }
        for (int i = 0; i < n; i++){ for (int j = 0; j < m; j++){ Allocation[i][j] = scanner.nextInt(); } }
        for (int i = 0; i < n; i++){ for (int j = 0; j < m; j++){ Need[i][j] = scanner.nextInt(); } }
        for (int i = 0; i < n; i++){ for (int j = 0; j < m; j++){ Max[i][j] = Need[i][j] + Allocation[i][j]; } }
        process = scanner.nextInt();
        for(int i = 0; i < m; i++){ Request[i] = scanner.nextInt(); }

        /*比较Request和Need*/
        for (int i = 0; i < m; i++) {
            if(Request[i]>Need[process][i]){
                StepOne = false;
                System.out.println("所需资源超过了最大宣布值");
                System.exit(0);
            }
        }

        /*比较Request和Available*/
        if(StepOne){
            for (int i = 0; i < m; i++) {
                if(Request[i]>Available[i]){
                    StepTwo = false;
                    System.out.println("尚无足够资源");
                    System.exit(0);
                }
            }
        }

        /*修改数据*/
        if (StepTwo){
            for (int j = 0; j < m; j++) {
                Available[j] = Available[j] - Request[j];
                Allocation[process][j] = Allocation[process][j] + Request[j];
                Need[process][j] = Need[process][j] - Request[j];
            }

            /*执行安全检测算法*/
            System.out.println("进入检查安全阶段");

            /*初始化数据*/
            int[] work = Available;
            boolean[] finish = new boolean[n];
            for (int i = 0; i < n; i++) { finish[i] = false; }

            for (int k = 0; k < n*10; k++) {
                for(int i = 0;i < n;i++){
                    boolean istonext = true;
                    if(!finish[i]){

                        /*比较Work与Need*/
                        for (int j = 0; j < m; j++) {
                            if(Need[i][j] > work[j]){
                                istonext = false;
                                break;
                            }
                        }
                        if (istonext){
                            for (int j = 0; j < m; j++){ work[j] = work[j] +Allocation[i][j]; }
                                finish[i] = true;
                                safequeue[safequeueindex] = i;
                                safequeueindex++;
                                System.out.print("安全队列"+i+" " );
                        }
                    }
                }
                boolean IsSafe = true;
                for (int i = 0; i < n; i++) { if (!finish[i]) IsSafe = false; }
                if (IsSafe){
                    System.out.println("系统处于安全状态");
                    System.out.println("安全队列");
                    for (int i = 0; i < n; i++) { System.out.print(safequeue[i]+" "); }
                    break;
                }
            }
        }
        System.out.println("进入不安全状态");
    }
}
