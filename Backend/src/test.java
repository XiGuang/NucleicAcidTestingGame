import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Client cl = new Client();
        while (!cl.connection_state) {
            Scanner scanner = new Scanner(System.in);
            String Name = scanner.nextLine();
            String Password = scanner.nextLine();
            int Number = 1000;
            //cl.login(Name,Password);    //登录函数，需要传递用户姓名，密码
            //cl.Registration(Name,Password);   //注册函数，需要传递用户姓名，密码
            int i = cl.setNumber(Name,Number);  //登记分数函数，需要传递用户姓名，分数
            //cl.Ranking();  //排名函数，不需要提供参数
            System.out.println("i="+i);
            try {
                Thread.sleep(3000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}