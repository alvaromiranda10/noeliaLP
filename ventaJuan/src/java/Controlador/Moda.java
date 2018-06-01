package Controlador;

import ModeloImplementDAO.ProductoDAO;
import ModeloImplementDAO.carritoDAO;
import ModeloImplementDAO.usuarioDAO;
import Template.Thymeleaf;
import eliminar.prueba;
import java.io.IOException;
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

/**
 *
 * @author alvaro
 */
@WebServlet(name = "moda", urlPatterns = {"/moda"})
public class Moda extends HttpServlet {

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

        String categoria = request.getParameter("categoria");
        String color = request.getParameter("color");
        String precio = request.getParameter("precio");

        if (categoria == null && color == null && precio == null) {
            this.templateModa(request, response,items);

        } else if (categoria != null && color == null && precio == null) {
            this.templateByCategoria(categoria, request, response,items);

        } else if (categoria != null && color != null && precio == null) {
            this.templateByCategoriaColor(categoria, color, request, response,items);

        } else if (categoria != null && color != null && precio != null) {
            this.templateByCategoriaColorPrecio(categoria, color, precio, request, response,items);

        } else if (categoria != null && color == null && precio != null) {
            this.templateByCategoriaPrecio(categoria, precio, request, response,items);

        }
    }

    public void templateModa(HttpServletRequest request, HttpServletResponse response,int items) throws IOException {
        HttpSession sesion = request.getSession();

        ProductoDAO p = new ProductoDAO();
        ArrayList productos = p.listarProductos();
        ArrayList categorias = p.listarCategoria();

        //        RESPUESTA PLANTILLA
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("productos", productos);
        ctx.setVariable("categorias", categorias);
        ctx.setVariable("moda", "moda");
        ctx.setVariable("items", items);
        ctx.setVariable("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));


        TemplateEngine engine = primerThymeleaf.getTemplateEngine();
        engine.process("moda", ctx, response.getWriter());
    }

    public void templateByCategoria(String categoria, HttpServletRequest request, HttpServletResponse response, int items)
            throws IOException {
        HttpSession sesion = request.getSession();

        ProductoDAO p = new ProductoDAO();
        ArrayList productos = p.listarProductosByCategoria(categoria);
        ArrayList colores = p.listarColoresByCategoria(categoria);
        int MAX = p.precioMaximoProductos(categoria);
        ArrayList<String> precios = this.preciosArray(MAX);

        //        RESPUESTA PLANTILLA
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("productos", productos);
        ctx.setVariable("colores", colores);
        ctx.setVariable("precios", precios);
        ctx.setVariable("categoria", categoria);
        ctx.setVariable("moda", "moda");
        ctx.setVariable("items", items);
        ctx.setVariable("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));

        TemplateEngine engine = primerThymeleaf.getTemplateEngine();
        engine.process("modaByCategoria", ctx, response.getWriter());

    }

    public void templateByCategoriaColor(String categoria, String color, HttpServletRequest request, HttpServletResponse response,int items)
            throws IOException {
        HttpSession sesion = request.getSession();

        ProductoDAO p = new ProductoDAO();
        ArrayList productos = p.listarProductosByCategoriaColor(categoria, color);
        ArrayList colores = p.listarColoresByCategoria(categoria);
        prueba l = new prueba();
        HashMap listadoColores = l.split(color, colores);
        HashMap listadoColoreSeleccionados = l.listadoColoresSeleccionados(color, colores);
        int MAX = p.precioMaximoProductos(categoria);
        ArrayList<String> precios = this.preciosArray(MAX);

        //        RESPUESTA PLANTILLA
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("productos", productos);
        ctx.setVariable("precios", precios);
        ctx.setVariable("LC", listadoColores);
        ctx.setVariable("LCS", listadoColoreSeleccionados);
        ctx.setVariable("categoria", categoria);
        ctx.setVariable("colorParam", color);
        ctx.setVariable("moda", "moda");
        ctx.setVariable("items", items);
        ctx.setVariable("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));


        TemplateEngine engine = primerThymeleaf.getTemplateEngine();
        engine.process("modaByCategoriaColor", ctx, response.getWriter());
    }

    public void templateByCategoriaColorPrecio(String categoria, String color, String precio, HttpServletRequest request, HttpServletResponse response, int items)
            throws IOException {
        HttpSession sesion = request.getSession();

        ProductoDAO p = new ProductoDAO();
        ArrayList productos = p.listarProductosByCategoriaColorPrecio(categoria, color, precio);
        ArrayList colores = p.listarColoresByCategoria(categoria);
        prueba l = new prueba();
        HashMap listadoColores = l.split(color, colores);
        HashMap listadoColoreSeleccionados = l.listadoColoresSeleccionados(color, colores);
        int MAX = p.precioMaximoProductos(categoria);
        ArrayList<String> precios = this.preciosArray(MAX);

        //        RESPUESTA PLANTILLA
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("productos", productos);
        ctx.setVariable("precios", precios);
        ctx.setVariable("LC", listadoColores);
        ctx.setVariable("LCS", listadoColoreSeleccionados);
        ctx.setVariable("categoria", categoria);
        ctx.setVariable("colorParam", color);
        ctx.setVariable("precioParam", precio);
        ctx.setVariable("moda", "moda");
        ctx.setVariable("items", items);
        ctx.setVariable("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));


        TemplateEngine engine = primerThymeleaf.getTemplateEngine();
        engine.process("modaByCategoriaPrecioColor", ctx, response.getWriter());
        
    }

    public void templateByCategoriaPrecio(String categoria, String precio, HttpServletRequest request, HttpServletResponse response, int items)
            throws IOException {
        HttpSession sesion = request.getSession();

        ProductoDAO p = new ProductoDAO();
        ArrayList productos = p.listarProductosByCategoriaPrecio(categoria, precio);
        ArrayList colores = p.listarColoresByCategoria(categoria);

        //        RESPUESTA PLANTILLA
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());
        ctx.setVariable("productos", productos);
        ctx.setVariable("colores", colores);
        ctx.setVariable("categoria", categoria);
        ctx.setVariable("precioParam", precio);
        ctx.setVariable("moda", "moda");
        ctx.setVariable("items", items);
        ctx.setVariable("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));


        TemplateEngine engine = primerThymeleaf.getTemplateEngine();
        engine.process("modaByCategoriaPrecio", ctx, response.getWriter());
        
    }

    public ArrayList preciosArray(int MAX) {
        ArrayList<String> precios = new ArrayList();

        if (MAX > 0 && MAX < 1001) {
            precios.add("0-1000");

        } else if (MAX > 1000 && MAX < 2001) {
            precios.add("0-1000");
            precios.add("1001-2000");

        }
        return precios;
    }

}
