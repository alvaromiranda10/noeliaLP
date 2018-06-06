package com.noeliaLP.principal.implementDAO;

import com.noeliaLP.principal.model.OtrosColores;
import com.noeliaLP.principal.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;

@Service("ProductoBEAN")
public class ProductoDAO  {

    @Autowired
    private Environment env;

    private static final String SELECT_PRODUCTO = "SELECT id_producto, p_precio, p_nombre FROM `producto`";
    private static final String SELECT_PRODUCTO_FOR_TIPO_PRODUCTO = "SELECT id_producto, p_precio, p_nombre FROM `producto` WHERE p_tipo=?";
    private static final String SELECT_OTROCOLORES_FOR_ID_PRODUCTO = "SELECT o_color, o_foto, o_fotob, o_talleS, o_talleM, o_talleL, o_talleXL FROM otroscolores WHERE id_producto=?";
    private static final String SELECT_PRODUCTO_FOR_ID_PRODUCTO = "SELECT id_producto, p_precio, p_nombre, p_descripcion FROM `producto` WHERE id_producto=?";
    private static final String SELECT_PRODUCTO_FOR_TIPO_PRODUCTO_TO_FILTER_COLOR = "SELECT id_producto, p_precio, p_nombre FROM `producto` WHERE p_tipo=?";
    private static final String SELECT_PRODUCTO_FOR_TIPO_PRODUCTO_TO_FILTER_COLOR_PRECIO = "SELECT id_producto, p_precio, p_nombre FROM `producto` WHERE p_tipo=? AND p_precio BETWEEN ? AND ?";
    private static final String SELECT_COLORES_FOR_TIPO = "SELECT o_color FROM otroscolores , producto WHERE otroscolores.id_producto =producto.id_producto AND p_tipo = ? GROUP BY o_color";
    private static final String SELECT_MAX_PRECIO_PRODUCTO="SELECT MAX(p_precio) AS 'maximo' FROM `producto` WHERE p_tipo= ?";
    private static final String SELECT_GROUP_BY_TIPO="SELECT p_tipo FROM `producto` GROUP BY p_tipo";

    //BLOQUE 0
    public ArrayList listarProductos() {

        PreparedStatement prs = null;
        ArrayList resultado = new ArrayList<>();
        try {
        Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
            prs = reg.prepareStatement(SELECT_PRODUCTO);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                Producto a = new Producto();

                a.setId(rs.getString("id_producto"));
                a.setPrecio(rs.getString("p_precio"));
                a.setNombre(rs.getString("p_nombre"));
                ArrayList temp = recuperarOtroColores(a.getId());
                if (0 != temp.size()) {
                    a.setOtrosColores(temp);
                    resultado.add(a);
                }
            }
            prs.close();
            reg.close();

        } catch (SQLException e) {
            System.out.println(e);
            String msj = "fallo";
            resultado.add(msj);
        }
        return resultado;
    }

//BLOQUE 0

    //BLOQUE 1
    public ArrayList listarProductosByCategoria(String categoria) {

        PreparedStatement prs = null;
        ArrayList resultado = new ArrayList<>();
        try {
            Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
            prs = reg.prepareStatement(SELECT_PRODUCTO_FOR_TIPO_PRODUCTO);
            prs.setString(1, categoria);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                Producto a = new Producto();

                a.setId(rs.getString("id_producto"));
                a.setPrecio(rs.getString("p_precio"));
                a.setNombre(rs.getString("p_nombre"));
                ArrayList temp = recuperarOtroColores(a.getId());
                if (0 != temp.size()) {
                    a.setOtrosColores(temp);
                    resultado.add(a);
                }
            }
            prs.close();
            reg.close();

        } catch (SQLException e) {
            System.out.println(e);
            String msj = "fallo";
            resultado.add(msj);
        }
        return resultado;
    }

//    DEVULVE TODOS LOS COLORES POR ID NO ELIMINAR

    public ArrayList recuperarOtroColores(String id) {

        PreparedStatement prs = null;

        ArrayList resultado = new ArrayList<>();

        try {
            Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
            prs = reg.prepareStatement(SELECT_OTROCOLORES_FOR_ID_PRODUCTO);
            prs.setInt(1, Integer.parseInt(id));
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                OtrosColores b = new OtrosColores();
                b.setFoto(rs.getString("o_foto"));
                b.setFotob(rs.getString("o_fotob"));
                b.setColor(rs.getString("o_color"));
                b.setTalleS(rs.getInt("o_talleS"));
                b.setTalleM(rs.getInt("o_talleM"));
                b.setTalleL(rs.getInt("o_talleL"));
                b.setTalleXL(rs.getInt("o_talleXL"));

                resultado.add(b);
            }

            rs.close();
            reg.close();

        } catch (SQLException e) {

            System.out.println(e);
            String msj = "fallo";
            resultado.add(msj);
        }
        return resultado;

    }

//    BLOQUE 1

//    BLOQUE 2

    public ArrayList listarProductosByCategoriaColor(String categoria, String colores) {

        ArrayList resultado = new ArrayList<>();
        ArrayList idByCategoria = this.idProductosByCategoria(categoria);
        ArrayList idByCategoriaColor = this.idProductosByCategoriaColor(idByCategoria, colores);

        for (int i = 0; i < idByCategoriaColor.size(); i++) {
            PreparedStatement prs = null;
            try {
                Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
                prs = reg.prepareStatement(SELECT_PRODUCTO_FOR_ID_PRODUCTO);
                prs.setString(1, (String) idByCategoriaColor.get(i));
                ResultSet rs = prs.executeQuery();
                while (rs.next()) {
                    Producto a = new Producto();

                    a.setId(rs.getString("id_producto"));
                    a.setPrecio(rs.getString("p_precio"));
                    a.setNombre(rs.getString("p_nombre"));
                    ArrayList temp = recuperarOtroColores(a.getId());
                    if (0 != temp.size()) {
                        a.setOtrosColores(temp);
                        resultado.add(a);
                    }
                }
                prs.close();
                reg.close();

            } catch (SQLException e) {
                System.out.println(e);
                resultado.add("fallo");
            }

        }

        return resultado;
    }

    public ArrayList idProductosByCategoria(String categoria) {

        PreparedStatement prs = null;
        ArrayList resultado = new ArrayList<>();
        try {
            Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
            prs = reg.prepareStatement(SELECT_PRODUCTO_FOR_TIPO_PRODUCTO_TO_FILTER_COLOR);
            prs.setString(1, categoria);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                resultado.add(rs.getString("id_producto"));
            }
            prs.close();
            reg.close();

        } catch (SQLException e) {
            System.out.println(e);
            resultado.add("fallo");
        }
        return resultado;
    }

    public ArrayList idProductosByCategoriaColor(ArrayList idByCategoria, String colores) {

        ArrayList resultado = new ArrayList<>();
        for (int i = 0; i < idByCategoria.size(); i++) {

            String[] color = colores.split("-");
            ResultSet res = null;
            int acumulador = 0;

            for (int j = 0; j < color.length; j++) {

                String SELECT_id_producto_By_Colores = "SELECT o.id_producto, p.p_nombre, p.p_precio, p.p_descripcion FROM otroscolores o, producto p WHERE p.id_producto= o.id_producto AND p.id_producto =? AND o.o_color=?";

                PreparedStatement prs = null;

                try {

                Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
                    prs = reg.prepareStatement(SELECT_id_producto_By_Colores);
                    prs.setString(1, (String) idByCategoria.get(i));
                    prs.setString(2, color[j]);
                    res = prs.executeQuery();
                    if (res.next()) {
                        acumulador++;
                    }

                    prs.close();
                    reg.close();
                } catch (SQLException e) {
                    System.out.println(e.toString());
                }

            }
            if (acumulador > 0) {
                resultado.add(idByCategoria.get(i));
            }
        }
        return resultado;
    }

//    BLOQUE 2

//    BLOQUE 3

    public ArrayList listarProductosByCategoriaColorPrecio(String categoria, String colores, String precio) {

        ArrayList resultado = new ArrayList<>();
        ArrayList idByCategoria = this.idProductosByCategoriaPrecio(categoria,precio);
        ArrayList idByCategoriaColor = this.idProductosByCategoriaColor(idByCategoria, colores);

        for (int i = 0; i < idByCategoriaColor.size(); i++) {

            PreparedStatement prs = null;
            try {
                Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
                prs = reg.prepareStatement(SELECT_PRODUCTO_FOR_ID_PRODUCTO);
                prs.setString(1, (String) idByCategoriaColor.get(i));
                ResultSet rs = prs.executeQuery();
                while (rs.next()) {
                    Producto a = new Producto();

                    a.setId(rs.getString("id_producto"));
                    a.setPrecio(rs.getString("p_precio"));
                    a.setNombre(rs.getString("p_nombre"));
                    ArrayList temp = recuperarOtroColores(a.getId());
                    if (0 != temp.size()) {
                        a.setOtrosColores(temp);
                        resultado.add(a);
                    }
                }
                prs.close();
                reg.close();

            } catch (SQLException e) {
                System.out.println(e);
                resultado.add("fallo");
            }

        }

        return resultado;
    }

    private ArrayList idProductosByCategoriaPrecio(String categoria, String precio) {

        String[] algo = precio.split("-");

        PreparedStatement prs = null;
        ArrayList resultado = new ArrayList<>();
        try {
            Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
            prs = reg.prepareStatement(SELECT_PRODUCTO_FOR_TIPO_PRODUCTO_TO_FILTER_COLOR_PRECIO);
            prs.setString(1, categoria);
            prs.setString(2, algo[0]);
            prs.setString(3, algo[1]);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {
                resultado.add(rs.getString("id_producto"));
            }
            prs.close();
            reg.close();

        } catch (SQLException e) {
            System.out.println(e);
            resultado.add("fallo");
        }
        return resultado;
    }

//    BLOQUE 3

    //    BLOQUE 4
    public ArrayList listarProductosByCategoriaPrecio(String categoria, String precio) {

        ArrayList resultado = new ArrayList<>();
        ArrayList idByCategoria = this.idProductosByCategoriaPrecio(categoria,precio);
        for (int i = 0; i < idByCategoria.size(); i++) {
            PreparedStatement prs = null;
            try {
            Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
                prs = reg.prepareStatement(SELECT_PRODUCTO_FOR_ID_PRODUCTO);
                prs.setString(1, (String) idByCategoria.get(i));
                ResultSet rs = prs.executeQuery();
                while (rs.next()) {
                    Producto a = new Producto();

                    a.setId(rs.getString("id_producto"));
                    a.setPrecio(rs.getString("p_precio"));
                    a.setNombre(rs.getString("p_nombre"));
                    ArrayList temp = recuperarOtroColores(a.getId());
                    if (0 != temp.size()) {
                        a.setOtrosColores(temp);
                        resultado.add(a);
                    }
                }
                prs.close();
                reg.close();

            } catch (SQLException e) {
                System.out.println(e);
                resultado.add("fallo");
            }

        }

        return resultado;

    }
//    BLOQUE 4



    //    DEVUELVE TODOS LOS COLORES POR TIPO NO ELIMINAR
    public ArrayList listarColoresByCategoria(String categoria) {
        PreparedStatement prs = null;

        ArrayList resultado = new ArrayList<>();

        try {

            Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
            prs = reg.prepareStatement(SELECT_COLORES_FOR_TIPO);
            prs.setString(1, categoria);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {

                resultado.add(rs.getString("o_color"));
            }
            prs.close();
            reg.close();

        } catch (SQLException e) {
            System.out.println(e);
            String msj = "fallo";
            resultado.add(msj);
        }
        return resultado;
    }

    public Producto recuperarProducto(String s) {
        PreparedStatement prs = null;

        ArrayList otrosColores = new ArrayList<>();

        Producto a = new Producto();
        try {
            Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
            prs = reg.prepareStatement(SELECT_PRODUCTO_FOR_ID_PRODUCTO);
            prs.setString(1, s);
            ResultSet rs = prs.executeQuery();

            while (rs.next()) {
                a.setNombre(rs.getString("p_nombre"));
                a.setPrecio(rs.getString("p_precio"));
                a.setDescripcion(rs.getString("p_descripcion"));
            }

            otrosColores = this.recuperarOtroColores(s);

            a.setOtrosColores(otrosColores);

            prs.close();
            reg.close();
            return a;

        } catch (SQLException e) {
            System.out.println(e);
            String temp = e.toString();
            a.setNombre(temp);
            return a;
        }

    }

    //    PARA temoplate detalleProducto.html
    public int precioMaximoProductos(String categoria) {

        PreparedStatement prs = null;

        int resultado = 0;

        try {
            Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
            prs = reg.prepareStatement(SELECT_MAX_PRECIO_PRODUCTO);
            prs.setString(1,categoria);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {

                resultado = rs.getInt("maximo");
            }
            prs.close();
            reg.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
        return resultado;
    }

    public ArrayList listarCategoria(){
        PreparedStatement prs = null;

        ArrayList resultado = new ArrayList<>();

        try {
            Connection reg = DriverManager.getConnection(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password"));
            prs = reg.prepareStatement(SELECT_GROUP_BY_TIPO);
            ResultSet rs = prs.executeQuery();
            while (rs.next()) {

                resultado.add(rs.getString("p_tipo"));
            }
            prs.close();
            reg.close();

        } catch (SQLException e) {
            System.out.println(e);
            resultado.add("fallo");
        }
        return resultado;
    }

}
