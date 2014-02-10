import java.sql.*;

/**
 * Created with IntelliJ IDEA.
 * User: Admin
 * Date: 09.12.13
 * Time: 12:40
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");
        Class.forName("org.sqlite.JDBC");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
        Statement stat = conn.createStatement();
        stat.executeUpdate("drop table if exists people;");
        stat.executeUpdate("create table people (name, occupation);");
        PreparedStatement prep = conn.prepareStatement("insert into people values (?, ?);");
        prep.setString(1, "Gandi");
        prep.setString(2, "politics");
        prep.addBatch();
        prep.setString(1, "Truning");
        prep.setString(2, "computers");
        prep.addBatch();
        prep.setString(1, "Jobs");
        prep.setString(2, "computers");
        prep.addBatch();

        conn.setAutoCommit(false);
        prep.executeBatch();
        conn.setAutoCommit(true);

        ResultSet rs = stat.executeQuery("select * from people order by occupation;");
        while (rs.next()) {
            System.out.println("name = " + rs.getString("name") + " occupation = " + rs.getString("occupation"));
        }
        rs.close();
        conn.close();

    }
}
