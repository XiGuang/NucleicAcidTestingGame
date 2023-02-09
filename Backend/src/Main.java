import java.sql.*;
public class Main {
    public static void main(String[] args) {
        Connection con = null;  // 定义Connection对象用于链接数据库
        Statement s = null;  // 定义Statement对象用于执行SQL语句
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("数据库驱动加载成功 ！");
            // 链接数据库school；
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/school；?severTimezone=GMT",
                    "root", "12345");
            System.out.println("连接成功，获取连接对象： " + con);
            s = con.createStatement();

            // 查询操作
            // 需要执行的SQL语句用string对象保存
            String sql = "SELECT c.kh" +
                    " FROM c WHERE c.kh NOT IN" +
                    "             (SELECT e.kh FROM e WHERE e.xh = (SELECT s.xh FROM s WHERE s.xm='刘晓明'));";
            ResultSet r = s.executeQuery(sql);
            String kh = null;
            System.out.println("刘晓明没选的课程课号：");
            while(r.next()){
                kh = r.getString("kh");
                System.out.println(kh);
            }

            /*// 数据插入
            PreparedStatement psql;
            psql = con.prepareStatement( "insert into d(yxh, Mc, dz, lxdh) values (?,?,?,?)" );
            String x ="上大东校区四号楼";
            psql.setString( 1 ,"03" );
            psql.setString( 2 , "材料学院" );
            psql.setString( 3 ,x);
            psql.setString( 4 , "65347890" );
            psql.executeUpdate();*/

            /*//删除操作
            sql="SET FOREIGN_KEY_CHECKS=0;";
            s.executeUpdate(sql);
            sql="delete from d\n" +
                    "where yxh not in(\n" +
                    "    select distinct c.yxh from c\n" +
                    "    where c.kh in (\n" +
                    "         select kh from o\n" +
                    "         )\n" +
                    "    );";
            s.executeUpdate(sql);
            sql="SET FOREIGN_KEY_CHECKS=1;";
            s.executeUpdate(sql);*/

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