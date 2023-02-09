import java.sql.*;
import java.util.Objects;

public class user {
    private String name;
    private String password;
    private int number;

    public user(String n,String p){
        name=n;
        password=p;
        number=0;
    }
    public void login(){
        Connection con = null;  // 定义Connection对象用于链接数据库
        Statement s;  // 定义Statement对象用于执行SQL语句
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功 ！");
            // 链接数据库java_hs_database
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/java_hs_database?severTimezone=GMT",
                    "root", "12345");
            System.out.println("连接成功，获取连接对象： " + con);
            s = con.createStatement();

            // 查询操作，查询是否存在此用户
            // 需要执行的SQL语句用string对象保存
            String sql = "SELECT user_id,password" +
                    " FROM user WHERE user_id=?;";
            PreparedStatement psql= con.prepareStatement(sql);
            psql.setString(1,name);
            ResultSet r = psql.executeQuery();
            String us_id ;
            if(r.next()){
                us_id = r.getString("user_id");
                System.out.println(us_id);
                String pswd;
                pswd = r.getString("password");
                if(Objects.equals(pswd, password)){
                    System.out.println("登录成功！");
                }
                else{
                    System.out.println(password);
                    System.out.println(pswd);
                    System.out.println("密码错误，请重试！");
                }
            }
            else System.out.println("账户不存在，请注册");

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(con!=null)
        {
            try {
                con.close();
                System.out.println("连接关闭！");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                con=null;
            }
        }
    }
    public void Registration(){
        Connection con = null;  // 定义Connection对象用于链接数据库
        Statement s;  // 定义Statement对象用于执行SQL语句
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功 ！");
            // 链接数据库java_hs_database
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/java_hs_database?severTimezone=GMT",
                    "root", "12345");
            System.out.println("连接成功，获取连接对象： " + con);
            s = con.createStatement();

            //查询操作，判断是否重名
            String sql = "SELECT user_id,password" +
                    " FROM user WHERE user_id=?;";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet r = ps.executeQuery();
            String us_id ;
            if(r.next()){
                System.out.println("账户名重复，请重新注册");
            }
            else {
                // 数据插入,用于用户注册
                PreparedStatement psql;
                psql = con.prepareStatement( "insert into user(user_id, password, number) values (?,?,?)" );
                int x =0;
                psql.setString( 1 ,name );
                psql.setString( 2 , password );
                psql.setInt( 3 ,x);
                psql.executeUpdate();
            }

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(con!=null)
        {
            try {
                con.close();
                System.out.println("连接关闭！");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                con=null;
            }
        }
    }

    public void setNumber(int num) {
        Connection con = null;  // 定义Connection对象用于链接数据库
        Statement s;  // 定义Statement对象用于执行SQL语句
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功 ！");
            // 链接数据库java_hs_database
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/java_hs_database?severTimezone=GMT",
                    "root", "12345");
            System.out.println("连接成功，获取连接对象： " + con);
            s = con.createStatement();

            //查询操作，判断是否重名
            String sql = "SELECT number" +
                    " FROM user WHERE user_id=?;";
            PreparedStatement ps= con.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet r = ps.executeQuery();
            if(r.next()){
                number = r.getInt("number");
                if(num>number){
                    number = num;
                    // 修改积分
                    PreparedStatement psql= con.prepareStatement("update user set number =? where user_id=?");
                    psql.setInt(1,num);
                    psql.setString(2,name);
                    psql.executeUpdate();
                }
            }
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(con!=null)
        {
            try {
                con.close();
                System.out.println("连接关闭！");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                con=null;
            }
        }
    }
    public void Ranking(){
        Connection con = null;  // 定义Connection对象用于链接数据库
        Statement s;  // 定义Statement对象用于执行SQL语句
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功 ！");
            // 链接数据库java_hs_database
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/java_hs_database?severTimezone=GMT",
                    "root", "12345");
            System.out.println("连接成功，获取连接对象： " + con);
            s = con.createStatement();

            // 查询操作，按number降序输出
            // 需要执行的SQL语句用string对象保存
            String sql = "SELECT user_id,number" +
                    " FROM user ORDER BY number DESC;";
            PreparedStatement psql= con.prepareStatement(sql);
            ResultSet r = psql.executeQuery();
            String user_id ;
            int num;
            int i = 1;
            while(r.next()){
                user_id = r.getString("user_id");
                num = r.getInt("number");
                System.out.println(i+"\t"+user_id+"\t"+num);
                i+=1;
            }

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(con!=null)
        {
            try {
                con.close();
                System.out.println("连接关闭！");
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                con=null;
            }
        }
    }

}

