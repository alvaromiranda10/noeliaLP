package Controlador;

import ModeloImplementDAO.usuarioDAO;
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
import Modelo.Cliente;
import Template.Thymeleaf;

/**
 *
 * @author alvaro
 */
@WebServlet(name = "registro", urlPatterns = {"/registro"})
public class Registro extends HttpServlet {

    private Thymeleaf primerThymeleaf;
    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        servletContext = config.getServletContext();
        primerThymeleaf = new Thymeleaf(servletContext);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombreUsuario2 = request.getParameter("nombreUsuario2");
        String clave2 = request.getParameter("clave2");
        String email = request.getParameter("email");

        Cliente c = new Cliente();
        c.setNombreUsuario(nombreUsuario2);
        c.setClave(clave2);
        c.setEmail(email);
        usuarioDAO u = new usuarioDAO();
        String resp = u.registrar(c);
        if (resp.equals("Usuario registrado")) {
            response.sendRedirect("./inicio");
        } else {

            //        RESPUESTA PLANTILLA
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

            ctx.setVariable("error", resp);

            TemplateEngine engine = primerThymeleaf.getTemplateEngine();
            engine.process("inicio", ctx, response.getWriter());

        }
    }

}