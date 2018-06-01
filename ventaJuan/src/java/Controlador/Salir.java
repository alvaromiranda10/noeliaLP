package Controlador;

import ModeloImplementDAO.usuarioDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author alvaro
 */
@WebServlet(name = "salir", urlPatterns = {"/salir"})
public class Salir extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sesion= request.getSession();
        usuarioDAO u = new usuarioDAO();
        
        u.deleteSesion(sesion);
        response.sendRedirect("./bienvenida");
    }

}
