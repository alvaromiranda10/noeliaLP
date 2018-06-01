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
import javax.servlet.http.HttpSession;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import Template.Thymeleaf;

/**
 *
 * @author alvaro
 */
@WebServlet(name = "inicio", urlPatterns = {"/inicio"})
public class Inicio extends HttpServlet {

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
        
//        INICIO RECUPERAR SESION
        HttpSession sesion = request.getSession();
        usuarioDAO u = new usuarioDAO();
        boolean activo = u.estadoSesion(sesion);
//        RECUPERAR SESION FIN

        if(activo == true){
            response.sendRedirect("./bienvenida");
            
        }else{
            //        RESPUESTA PLANTILLA
            response.setContentType("text/html;charset=UTF-8");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);

            WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

            TemplateEngine engine = primerThymeleaf.getTemplateEngine();
            engine.process("inicio", ctx, response.getWriter());
        }

    }
    
    

}
