package ModeloImplementDAO;

import com.mysql.jdbc.Connection;
import conexionDB.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import Modelo.Cliente;

public class usuarioDAO {

    private static final String INSERT_USUARIO = "INSERT INTO `usuario` ( `u_mail`, `u_nombreUsuario`, `u_clave`, `u_tipo`) VALUES (?,?,?,?)";

    public String registrar(Cliente c) {
        Conexion con = new Conexion();
        Connection reg = con.getConnection();
        PreparedStatement prs = null;
        String respuesta = null;
        try {
            prs = reg.prepareStatement(INSERT_USUARIO);
            prs.setString(1, c.getEmail());
            prs.setString(2, c.getNombreUsuario());
            prs.setString(3, c.getClave());
            prs.setString(4, c.getTipo());

            prs.executeUpdate();

            prs.close();
            reg.close();
            respuesta = "Usuario registrado";
        } catch (SQLException e) {
            System.out.println(e);
            respuesta = "Usuario no registrado";
        }
        return respuesta;

    }

    private static final String SELECT_USUARIO = "SELECT u_id, u_nombreUsuario FROM `usuario` WHERE u_nombreUsuario = ? AND u_clave = ?;";

    public String ingresar(HttpSession sesion, Cliente c) {

        String respuesta = null;
        Conexion con = new Conexion();
        Connection reg = con.getConnection();
        PreparedStatement prs = null;
        try {
            prs = reg.prepareStatement(SELECT_USUARIO);
            prs.setString(1, c.getNombreUsuario());
            prs.setString(2, c.getClave());

            ResultSet rs = prs.executeQuery();
            if (rs.next()) {
                this.getSesion(sesion, rs.getString("u_id"),rs.getString("u_nombreUsuario"));
                respuesta = "Exito";
            } else {
                respuesta = "La clave o el usuario no existe o es incorrecto";
            }

            prs.close();
            reg.close();
            return respuesta;
        } catch (SQLException e) {
            System.out.println(e);

            return e.toString();
        }
    }

    public void getSesion(HttpSession sesion, String u_id, String u_nombreUsuario ) {
        Conexion con = new Conexion();
        Connection reg = con.getConnection();
        String codigo = UUID.randomUUID().toString();
        sesion.setAttribute("codigo-autorizacion", codigo);
        sesion.setAttribute("u_id", u_id);
        sesion.setAttribute("u_nombreUsuario", u_nombreUsuario);

        try {
            PreparedStatement consulta = reg.prepareStatement("UPDATE `usuario` SET activo = ? WHERE u_id = ?");
            consulta.setString(1, codigo);
            consulta.setString(2, u_id);
            consulta.executeUpdate();

            consulta.close();
            reg.close();
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    public boolean estadoSesion(HttpSession sesion) {
        String codigo = (String) sesion.getAttribute("codigo-autorizacion");
        boolean respuesta = false;
        if (codigo == null) {
            return respuesta;
        } else {
            Conexion con = new Conexion();
            Connection reg = con.getConnection();

            try {
                PreparedStatement consulta = reg.prepareStatement("SELECT * FROM  `usuario`  WHERE activo = ?;");
                consulta.setString(1, codigo);
                ResultSet rs = consulta.executeQuery();
                if (rs.next()) {
                    respuesta = true;
                } else {
                    
                    this.deleteSesion(sesion);
                    respuesta = false;
                }

                consulta.close();
                reg.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
            return respuesta;
        }

    }

    public void deleteSesion(HttpSession sesion) {

        String codigo = (String) sesion.getAttribute("codigo-autorizacion");

        sesion.removeAttribute("codigo-autorizacion");
        sesion.removeAttribute("u_id");
        sesion.removeAttribute("u_nombreUsuario");
        Conexion con = new Conexion();
        Connection reg = con.getConnection();
        try {

            PreparedStatement consulta = reg.prepareStatement("UPDATE `usuario` SET activo = NULL WHERE activo = ?;");
            consulta.setString(1, codigo);
            consulta.executeUpdate();

            consulta.close();
            reg.close();

        } catch (SQLException e) {
            System.out.println(e);
        }

    }

}
