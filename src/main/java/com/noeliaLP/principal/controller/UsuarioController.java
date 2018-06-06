package com.noeliaLP.principal.controller;

import com.noeliaLP.principal.implementDAO.CarritoDAO;
import com.noeliaLP.principal.implementDAO.UsuarioDAO;
import com.noeliaLP.principal.model.Carrito;
import com.noeliaLP.principal.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class UsuarioController {


    @Autowired
    @Qualifier("UsuarioBEAN")
    private UsuarioDAO udao;

    @Autowired
    @Qualifier("CarritoBEAN")
    private CarritoDAO cdao;

    @Autowired
    @Qualifier("Cliente")
    private Cliente clmodel;

    @PostMapping("/ingreso")
    public String ingreso(Model template, HttpSession sesion, @RequestParam("nombreUsuario") String nombreUsuario,
                          @RequestParam("clave") String clave,
                          @RequestParam("producto") String producto,
                          @RequestParam("cantidad") String cantidad,
                          @RequestParam("color") String color,
                          @RequestParam("talle") String talle){

        Cliente c = new Cliente();
        c.setNombreUsuario(nombreUsuario);
        c.setClave(clave);

        String resultado = udao.ingresar(sesion, c);
        if(resultado.equals("Exito")){

            if (producto == "" || cantidad == "" || color == "" || talle == "") {

                return "redirect:/bienvenida";
            } else {
                String id = sesion.getAttribute("u_id").toString();
                Carrito bolsa = new Carrito();
                bolsa.setIdUsuario(id);
                bolsa.setIdProducto(producto);
                bolsa.setCantidad(cantidad);
                bolsa.setColor(color);
                bolsa.setTalle(talle);

                cdao.agregarCompra(bolsa);

                return "redirect:carrito";

            }
        }else{

            template.addAttribute("error", resultado);
            return "inicio";
        }

    }

    @PostMapping("/registro")
    public String registro(Model template, HttpSession sesion,
                           @RequestParam("nombreUsuario2") String nombreUsuario2,
                           @RequestParam("clave2") String clave2,
                           @RequestParam("email") String email){

        clmodel.setNombreUsuario(nombreUsuario2);
        clmodel.setClave(clave2);
        clmodel.setEmail(email);
        String resp = udao.registrar(clmodel);

        if(resp.equals("Usuario registrado")){

            return "redirect:inicio";
        }else{

            template.addAttribute("error", resp);
            return "inicio";
        }

    }

    @GetMapping("/salir")
    public String salir(HttpSession sesion){

        udao.deleteSesion(sesion);
        return "redirect:bienvenida";
    }



    @GetMapping("/inicio")
    public String inicio(HttpSession sesion){

        //        INICIO RECUPERAR SESION
        Boolean activo = udao.estadoSesion(sesion);
        //        RECUPERAR SESION FIN

        if(activo){

            return "redirect:bienvenida";
        }
        return "inicio";
    }

    @GetMapping("/bienvenida")
    public String bienvenida(Model template, HttpSession sesion){
        String id ="";

        //        INICIO RECUPERAR SESION
        Boolean activo = udao.estadoSesion(sesion);
        //        RECUPERAR SESION FIN

        if (activo) {
            id = sesion.getAttribute("u_id").toString();
        }

        ArrayList<HashMap<String, String>> resul = cdao.listarCompras(id);
        int items = resul.size();

        template.addAttribute("nombreUsuario", sesion.getAttribute("u_nombreUsuario"));
        template.addAttribute("items", items);

        return "bienvenido";
    }



}
