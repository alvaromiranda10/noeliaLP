package Controlador;

import ModeloImplementDAO.carritoDAO;
import ModeloImplementDAO.usuarioDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import Modelo.Carrito;
import Template.Thymeleaf;

@WebServlet(name = "carrito", urlPatterns = {"/carrito"})
public class CarritoCompras extends HttpServlet {

    private Thymeleaf primerThymeleaf;
    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        primerThymeleaf = new Thymeleaf(servletContext);
    }

//    DEVUELVE TODOSS LOS PRODUCTO COMPRADOS SEGUN USUARIO- carrito.js
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = "";
        //        INICIO RECUPERAR SESION
        HttpSession sesion = request.getSession();
        usuarioDAO u = new usuarioDAO();
        boolean activo = u.estadoSesion(sesion);
//        RECUPERAR SESION FIN
        if(activo == true){
            id = sesion.getAttribute("u_id").toString();
        }else{
        
            response.sendRedirect("./inicio");
        }
        
        carritoDAO cargar = new carritoDAO();
        ArrayList<HashMap<String, String>> resul = cargar.listarCompras(id);
        int total = 0;
        int items = resul.size();
        for (int i = 0; i < resul.size(); i++) {
            total += Integer.parseInt(resul.get(i).get("p_precio")) * Integer.parseInt(resul.get(i).get("c_cantidad"));
        }

        //        RESPUESTA PLANTILLA
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("compra", resul);
        ctx.setVariable("total", total);
        ctx.setVariable("items", items);
        ctx.setVariable("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));

        TemplateEngine engine = primerThymeleaf.getTemplateEngine();
        engine.process("carrito", ctx, response.getWriter());

    }

//    AGREGA UNA COMPRA AL CARRITO- detalleProducto.js
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

         String id = "";
        //        INICIO RECUPERAR SESION
        HttpSession sesion = request.getSession();
        usuarioDAO u = new usuarioDAO();
        boolean activo = u.estadoSesion(sesion);
//        RECUPERAR SESION FIN

            String idProducto = request.getParameter("idProducto");
            String cantidad = request.getParameter("cantidad");
            String color = request.getParameter("colores");
            String talle = request.getParameter("talles");
        if(activo == true){
            id = sesion.getAttribute("u_id").toString();

            Carrito bolsa = new Carrito();
            bolsa.setIdUsuario(id);
            bolsa.setIdProducto(idProducto);
            bolsa.setCantidad(cantidad);
            bolsa.setColor(color);
            bolsa.setTalle(talle);
            carritoDAO a = new carritoDAO();
            a.agregarCompra(bolsa);
            response.sendRedirect("./carrito");
        }else{        
            response.sendRedirect("./inicio?producto="+idProducto+"&cantidad="+cantidad+"&color="+color+"&talle="+talle);
        }
        
    }

//    ELIMINAR UNA COMPRA DEL CARRITO - carrito.js
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String id = request.getParameter("idCompra");

        PrintWriter out = response.getWriter();

        carritoDAO a = new carritoDAO();
        String resp = a.eliminarCompra(id);
        out.print(resp);

    }

}
