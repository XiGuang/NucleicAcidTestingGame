import org.json.simple.JSONObject;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.spec.ECField;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try{
            System.out.println("服务器已在运行！");
            ServerSocket severSocket = new ServerSocket(9999);
            while(true){
                Socket socket = severSocket.accept();
                new Thread(new Server_listen(socket)).start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

class Server_listen implements Runnable{
    private Socket socket;

    Server_listen(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run() {
        try{
            ObjectInputStream ois=new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            while(true){
                JSONObject jsonObject = new JSONObject((Map) ois.readObject());
                String Function = (String) jsonObject.get("Function");
                switch (Function){
                    case "1":  //功能1.登录
                        String name = (String) jsonObject.get("Name");
                        String password = (String) jsonObject.get("Password");
                        user x = new user(name,password);
                        String res;
                        res = x.login();
                        JSONObject object_r1 = new JSONObject();
                        object_r1.put("result",res);
                        oos.writeObject(object_r1);
                        oos.flush();
                        break;

                    case "2":  //功能2.注册
                        String name2 = (String) jsonObject.get("Name");
                        String password2 = (String) jsonObject.get("Password");
                        user y = new user(name2,password2);
                        String res2;
                        res2 = y.Registration();
                        JSONObject object_r2 = new JSONObject();
                        object_r2.put("result",res2);
                        oos.writeObject(object_r2);
                        oos.flush();
                        break;

                    case "3":  //功能3.登记分数
                        String name3 = (String) jsonObject.get("Name");
                        int number3 = (int) jsonObject.get("Number");
                        user z = new user(name3,"password3");
                        z.setNumber(number3);
                        break;

                    case "4":  //功能4.排名
                        user w = new user("temp","password");
                        List<JSONObject> list = new LinkedList<JSONObject>();
                        list = w.Ranking();
                        oos.writeObject(list);
                        oos.flush();
                        break;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
