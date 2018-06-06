package com.noeliaLP.principal.controller;

import com.noeliaLP.principal.implementDAO.CarritoDAO;
import com.noeliaLP.principal.implementDAO.ProductoDAO;
import com.noeliaLP.principal.implementDAO.UsuarioDAO;
import com.noeliaLP.principal.model.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class DetalleProductoController {

    @Autowired
    @Qualifier("UsuarioBEAN")
    private UsuarioDAO udao;


    @Autowired
    @Qualifier("CarritoBEAN")
    private CarritoDAO cdao;

    @Autowired
    @Qualifier("Producto")
    private Producto pmodel;

    @Autowired
    @Qualifier("ProductoBEAN")
    private ProductoDAO pdao;


    @GetMapping("/detalleProducto")
//    @RequestMapping(value="/detalleProducto/{idProducto}", method=RequestMethod.GET)
    public String detalleProducto(Model template, HttpSession sesion,
                                  @RequestParam("producto") String idProducto){
        String id ="";

        boolean activo = udao.estadoSesion(sesion);

        if(activo){
            id = sesion.getAttribute("u_id").toString();
        }

        ArrayList<HashMap<String, String>> resul = cdao.listarCompras(id);
        int items = resul.size();

        pmodel = pdao.recuperarProducto(idProducto);

        template.addAttribute("producto", pmodel);
        template.addAttribute("items", items);
        template.addAttribute("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));


        return "detalleProducto";
    }
}
