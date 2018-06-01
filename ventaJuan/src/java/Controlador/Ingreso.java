package Controlador;

import Modelo.Carrito;
import ModeloImplementDAO.usuarioDAO;
import java.io.IOException;
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
import Modelo.Cliente;
import ModeloImplementDAO.carritoDAO;
import Template.Thymeleaf;

@WebServlet(name = "ingreso", urlPatterns = {"/ingreso"})
public class Ingreso extends HttpServlet {

    private Thymeleaf primerThymeleaf;
    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        primerThymeleaf = new Thymeleaf(servletContext);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreUsuario = request.getParameter("nombreUsuario");
        String clave = request.getParameter("clave");
        String producto = request.getParameter("producto");
        String cantidad = request.getParameter("cantidad");
        String color = request.getParameter("color");
        String talle = request.getParameter("talle");

        Cliente c = new Cliente();
        c.setNombreUsuario(nombreUsuario);
        c.setClave(clave);

        usuarioDAO u = new usuarioDAO();
        HttpSession sesion = request.getSession();
        String resultado = u.ingresar(sesion, c);

        if (resultado.equals("Exito")) {

            if (producto == "" || cantidad == "" || color == "" || talle == "") {

                response.sendRedirect("./bienvenida");
            } else {

                String id = sesion.getAttribute("u_id").toString();

                Carrito bolsa = new Carrito();
                bolsa.setIdUsuario(id);
                bolsa.setIdProducto(producto);
                bolsa.setCantidad(cantidad);
                bolsa.setColor(color);
                bolsa.setTalle(talle);
                carritoDAO a = new carritoDAO();
                a.agregarCompra(bolsa);
                response.sendRedirect("./carrito");
            }

        } else {

            //        RESPUESTA PLANTILLA
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
            ctx.setVariable("error", resultado);

            TemplateEngine engine = primerThymeleaf.getTemplateEngine();
            engine.process("inicio", ctx, response.getWriter());

        }

    }

}
