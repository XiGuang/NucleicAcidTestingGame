import org.json.simple.JSONObject;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Client {
    private static Socket socket;
    public static boolean connection_state = false;

    public static JSONObject login(String Name,String Password)
    //登录函数，需要传递用户姓名，密码
    {
        JSONObject res = null;
        try {
            socket = new Socket("127.0.0.1", 9999);
            connection_state = true;
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String Function = "1";
            JSONObject object = new JSONObject();
            object.put("user", "client");
            object.put("Function", Function);
            object.put("Name", Name);
            object.put("Password", Password);
            oos.writeObject(object);
            oos.flush();
            res = (JSONObject) ois.readObject();
            System.out.println(res);
        }catch (Exception e){
            e.printStackTrace();
            connection_state = false;
        }
        return res;
    }

    public static JSONObject Registration(String Name,String Password)
    //注册函数，需要传递用户姓名，密码
    {
        JSONObject res = null;
        try {
            socket = new Socket("127.0.0.1", 9999);
            connection_state = true;
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String Function = "2";
            JSONObject object = new JSONObject();
            object.put("user", "client");
            object.put("Function", Function);
            object.put("Name", Name);
            object.put("Password", Password);
            oos.writeObject(object);
            oos.flush();
            res= (JSONObject) ois.readObject();
            System.out.println(res);
        }catch (Exception e){
            e.printStackTrace();
            connection_state = false;
        }
        return res;
    }

    public static int setNumber(String Name,int number)
    //登记分数函数，需要传递用户姓名，分数
    {
        JSONObject res = null;
        try {
            socket = new Socket("127.0.0.1", 9999);
            connection_state = true;
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String Function = "3";
            JSONObject object = new JSONObject();
            object.put("user", "client");
            object.put("Function", Function);
            object.put("Name", Name);
            object.put("Number", number);
            oos.writeObject(object);
            oos.flush();
            res = (JSONObject) ois.readObject();
            System.out.println(res);
        }catch (Exception e){
            e.printStackTrace();
            connection_state = false;
        }
        return (int) res.get("result");
    }

    public static List<JSONObject> Ranking()
    //排名函数，不需要提供参数
    {
        List<JSONObject> res = null;
        try {
            socket = new Socket("127.0.0.1", 9999);
            connection_state = true;
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            String Function = "4";
            JSONObject object = new JSONObject();
            object.put("user", "client");
            object.put("Function", Function);
            object.put("Name", "Name");
            object.put("Password", "Password");
            oos.writeObject(object);
            oos.flush();
            res= (List<JSONObject>) ois.readObject();
            System.out.println(res);
        }catch (Exception e){
            e.printStackTrace();
            connection_state = false;
        }
        return res;
    }
}



