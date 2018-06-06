package com.noeliaLP.principal.controller;

import com.noeliaLP.principal.implementDAO.CarritoDAO;
import com.noeliaLP.principal.implementDAO.UsuarioDAO;
import com.noeliaLP.principal.model.Carrito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class CarritoController {

    @Autowired
    @Qualifier("UsuarioBEAN")
    private UsuarioDAO ubean;

    @Autowired
    @Qualifier("CarritoBEAN")
    private CarritoDAO cbean;

    @Autowired
    @Qualifier("Carrito")
    private Carrito cmodel;



    @GetMapping("/carrito")
    public String dameMisCompras(Model template, HttpSession session){

        String id = "";

        boolean activo = ubean.estadoSesion(session);

        if(activo){
            id = session.getAttribute("u_id").toString();
        }else{
            return "redirect:inicio";
        }

        ArrayList<HashMap<String, String>> resul = cbean.listarCompras(id);
        int total = 0;
        int items = resul.size();

        for (int i = 0; i < resul.size(); i++) {
            total += Integer.parseInt(resul.get(i).get("p_precio")) * Integer.parseInt(resul.get(i).get("c_cantidad"));
        }

        template.addAttribute("compra", resul);
        template.addAttribute("total", total);
        template.addAttribute("items", items);
        template.addAttribute("nombreUsuario", session.getAttribute("u_nombreUsuario"));

        return "carrito";
    }


    @PostMapping("/carrito")
    public String agregarCarrito(Model template, HttpSession sesion,
                                 @RequestParam("idProducto") String idProducto,
                                 @RequestParam("cantidad") String cantidad,
                                 @RequestParam("colores") String color,
                                 @RequestParam("talles") String talle){

        String id = "";

        boolean activo = ubean.estadoSesion(sesion);

        if(activo){
            id = sesion.getAttribute("u_id").toString();

            cmodel.setIdUsuario(id);
            cmodel.setIdProducto(idProducto);
            cmodel.setCantidad(cantidad);
            cmodel.setColor(color);
            cmodel.setTalle(talle);
            cbean.agregarCompra(cmodel);

            return "redirect:carrito";
        }

        return "redirect:inicio?producto="+idProducto+"&cantidad="+cantidad+"&color="+color+"&talle="+talle;
    }

    @DeleteMapping("/carrito")
    @ResponseBody
    public String eliminarProductoDelCarrito( @RequestParam(name = "idCompra") String id){

        return cbean.eliminarCompra(id);
    }
}
