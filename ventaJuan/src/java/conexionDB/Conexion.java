package conexionDB;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static Connection conn;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String clave = "";
    private static final String url = "jdbc:mysql://localhost:3306/e-comers";

    public Conexion() {
    conn = null;
        try {
            Class.forName(driver);
            conn = (Connection) DriverManager.getConnection(url,user,clave);
            
            if (conn != null) {
                System.out.println("La conexion fue exitosa");
            }
        } catch (ClassNotFoundException | SQLException e) {
             System.out.println("La conexion fallo: "+e);
        }
    }
    
    public Connection getConnection(){
    return conn;
    }
    
    public void desconectar (){
    conn = null;
        if (conn == null) {
            System.out.println("La conexion terminada..");
        }
    } 

}
