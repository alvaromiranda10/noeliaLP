package com.noeliaLP.principal.implementDAO;

import com.noeliaLP.principal.model.Carrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

@Service("CarritoBEAN")
public class CarritoDAO implements com.noeliaLP.principal.interfaces.CarritoDAO {

    @Autowired
    private Environment env;

    private static final String INSERT_CARRITO_FOR_ID = "INSERT INTO carrito (id_usuario, id_producto, c_talle, c_cantidad, c_color) VALUES (?,?,?,?,?)";
    private static final String SELECT_CARRITO_FOR_USER = "SELECT c.id_carrito, p.p_nombre, p.p_precio, c.c_color, c.c_cantidad, c.c_talle , o.o_foto FROM carrito c, producto p, otroscolores o WHERE c.id_producto= p.id_producto AND o.o_color = c.c_color AND o.id_producto = p.id_producto AND c.id_usuario =?;";
    private static final String ELIMINAR_COMPRA_POR_ID_CARRITO = "DELETE FROM carrito WHERE id_carrito = ?";


    //  CarritoCompras.java

    @Override
    public String agregarCompra(Carrito a) {
        PreparedStatement prs = null;

        try {
            Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
            prs = reg.prepareStatement(INSERT_CARRITO_FOR_ID);
            prs.setString(1, a.getIdUsuario());
            prs.setString(2, a.getIdProducto());
            prs.setString(3, a.getTalle());
            prs.setString(4, a.getCantidad());
            prs.setString(5, a.getColor());
            prs.executeUpdate();

            prs.close();
            reg.close();
            return "ok";

        } catch (SQLException e) {

            System.out.println(e);
            return e.toString();

        }
    }

    // CarritoCompras.java

    @Override
    public String eliminarCompra(String id) {
        PreparedStatement prs = null;

        try {
            Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
            prs = reg.prepareStatement(ELIMINAR_COMPRA_POR_ID_CARRITO);
            prs.setString(1, id);
            prs.executeUpdate();

            prs.close();
            reg.close();

            return "ok";
        } catch (SQLException e) {
            return e.toString();
        }
    }

    // CarritoCompras.java
    @Override
    public ArrayList listarCompras(String id) {

        PreparedStatement prs = null;
        ArrayList<HashMap<String, String>> resultado = new ArrayList<>();
        try {
            Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
            prs = reg.prepareStatement(SELECT_CARRITO_FOR_USER);
            prs.setString(1, id);

            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                HashMap row = new HashMap();

                row.put("id_carrito", rs.getString("id_carrito"));
                row.put("p_nombre", rs.getString("p_nombre"));
                row.put("p_precio", rs.getString("p_precio"));
                row.put("p_img", rs.getString("o_foto"));
                row.put("c_color", rs.getString("c_color"));
                row.put("c_cantidad", rs.getString("c_cantidad"));
                row.put("c_talle", rs.getString("c_talle"));
                resultado.add(row);

            }
            prs.close();
            reg.close();
            return resultado;

        } catch (SQLException e) {
            System.out.println(e);
            HashMap row = new HashMap();
            row.put("error", e);
            resultado.add(row);
            return resultado;
        }

    }

}
