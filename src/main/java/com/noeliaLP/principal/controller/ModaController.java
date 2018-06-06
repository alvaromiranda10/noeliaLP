package com.noeliaLP.principal.controller;

import com.noeliaLP.principal.implementDAO.CarritoDAO;
import com.noeliaLP.principal.implementDAO.ProductoDAO;
import com.noeliaLP.principal.implementDAO.UsuarioDAO;
import com.noeliaLP.principal.prueba.prueba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class ModaController {

    @Autowired
    @Qualifier("UsuarioBEAN")
    private UsuarioDAO udao;


    @Autowired
    @Qualifier("CarritoBEAN")
    private CarritoDAO cdao;

    @Autowired
    @Qualifier("ProductoBEAN")
    private ProductoDAO pdao;

    @GetMapping("/moda")
    public String devuelveModa(Model template, HttpSession sesion,
                               @RequestParam(required=false, name = "categoria") String categoria,
                               @RequestParam(required=false, name = "color") String color,
                               @RequestParam(required=false, name = "precio") String precio){

    String id ="";
    String resp= "";
    boolean activo = udao.estadoSesion(sesion);

        if(activo){
        id = sesion.getAttribute("u_id").toString();
    }

    ArrayList<HashMap<String, String>> resul = cdao.listarCompras(id);
    int items = resul.size();

        if (categoria == null && color == null && precio == null) {

            ArrayList productos = pdao.listarProductos();
            ArrayList categorias = pdao.listarCategoria();
            template.addAttribute("productos", productos);
            template.addAttribute("categorias", categorias);
            template.addAttribute("moda", "moda");
            template.addAttribute("items", items);
            template.addAttribute("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));

            resp = "moda";

        } else if (categoria != null && color == null && precio == null) {

            ArrayList productos = pdao.listarProductosByCategoria(categoria);
            ArrayList colores = pdao.listarColoresByCategoria(categoria);
            int MAX = pdao.precioMaximoProductos(categoria);
            ArrayList<String> precios = this.preciosArray(MAX);

            template.addAttribute("productos", productos);
            template.addAttribute("colores", colores);
            template.addAttribute("precios", precios);
            template.addAttribute("categoria", categoria);
            template.addAttribute("moda", "moda");
            template.addAttribute("items", items);
            template.addAttribute("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));

            resp = "modaByCategoria";

        } else if (categoria != null && color != null && precio == null) {

            ArrayList productos = pdao.listarProductosByCategoriaColor(categoria, color);
            ArrayList colores = pdao.listarColoresByCategoria(categoria);
            prueba l = new prueba();
            HashMap listadoColores = l.split(color, colores);
            HashMap listadoColoreSeleccionados = l.listadoColoresSeleccionados(color, colores);
            int MAX = pdao.precioMaximoProductos(categoria);
            ArrayList<String> precios = this.preciosArray(MAX);



            template.addAttribute("productos", productos);
            template.addAttribute("precios", precios);
            template.addAttribute("LC", listadoColores);
            template.addAttribute("LCS", listadoColoreSeleccionados);
            template.addAttribute("categoria", categoria);
            template.addAttribute("colorParam", color);
            template.addAttribute("moda", "moda");
            template.addAttribute("items", items);
            template.addAttribute("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));

            resp = "modaByCategoriaColor";

        } else if (categoria != null && color != null && precio != null) {


            ArrayList productos = pdao.listarProductosByCategoriaColorPrecio(categoria, color, precio);
            ArrayList colores = pdao.listarColoresByCategoria(categoria);
            prueba l = new prueba();
            HashMap listadoColores = l.split(color, colores);
            HashMap listadoColoreSeleccionados = l.listadoColoresSeleccionados(color, colores);
            int MAX = pdao.precioMaximoProductos(categoria);
            ArrayList<String> precios = this.preciosArray(MAX);

            template.addAttribute("productos", productos);
            template.addAttribute("precios", precios);
            template.addAttribute("LC", listadoColores);
            template.addAttribute("LCS", listadoColoreSeleccionados);
            template.addAttribute("categoria", categoria);
            template.addAttribute("colorParam", color);
            template.addAttribute("precioParam", precio);
            template.addAttribute("moda", "moda");
            template.addAttribute("items", items);
            template.addAttribute("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));

            resp =  "modaByCategoriaPrecioColor";
        } else if(categoria != null && color == null && precio != null) {

            ArrayList productos = pdao.listarProductosByCategoriaPrecio(categoria, precio);
            ArrayList colores = pdao.listarColoresByCategoria(categoria);


            template.addAttribute("productos", productos);
            template.addAttribute("colores", colores);
            template.addAttribute("categoria", categoria);
            template.addAttribute("precioParam", precio);
            template.addAttribute("moda", "moda");
            template.addAttribute("items", items);
            template.addAttribute("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));

            resp =  "modaByCategoriaPrecio";
        }
        return resp;
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
