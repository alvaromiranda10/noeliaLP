package Controlador;

import ModeloImplementDAO.ProductoDAO;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import Modelo.Producto;
import ModeloImplementDAO.carritoDAO;
import ModeloImplementDAO.usuarioDAO;
import Template.Thymeleaf;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpSession;

@WebServlet(name = "detalleProducto", urlPatterns = {"/detalleProducto"})
public class detalleProducto extends HttpServlet {

    private Thymeleaf primerThymeleaf;
    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        primerThymeleaf = new Thymeleaf(servletContext);
    }

//DEVUEVE DATOS DEL PRODUCTO SEGUN ID - detalleProducto.js
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = "";

        //        INICIO RECUPERAR SESION
        HttpSession sesion = request.getSession();
        usuarioDAO u = new usuarioDAO();
        boolean activo = u.estadoSesion(sesion);
//        RECUPERAR SESION FIN
        if (activo == true) {
            id = sesion.getAttribute("u_id").toString();
        }
        carritoDAO cargar = new carritoDAO();
        ArrayList<HashMap<String, String>> resul = cargar.listarCompras(id);
        int items = resul.size();
        

        String idProducto = request.getParameter("producto");

        Producto resultado = new Producto();
        ProductoDAO pedir = new ProductoDAO();
        
        resultado = pedir.recuperarProducto(idProducto);

        //        RESPUESTA PLANTILLA
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("producto", resultado);
        ctx.setVariable("items", items);
        ctx.setVariable("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));


        TemplateEngine engine = primerThymeleaf.getTemplateEngine();
        engine.process("detalleProducto", ctx, response.getWriter());

    }

}
