package Controlador;

import ModeloImplementDAO.carritoDAO;
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
import Template.Thymeleaf;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author alvaro
 */
@WebServlet(name = "bienvenida", urlPatterns = {"/bienvenida"})
public class Bienvenida extends HttpServlet {

    private Thymeleaf primerThymeleaf;
    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        primerThymeleaf = new Thymeleaf(servletContext);
    }

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

        //        RESPUESTA PLANTILLA
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));
        ctx.setVariable("items", items);

        TemplateEngine engine = primerThymeleaf.getTemplateEngine();
        engine.process("bienvenido", ctx, response.getWriter());

    }

}
