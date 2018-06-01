package Modelo;

import java.util.ArrayList;

public class Producto {
   private String id;
   private String nombre;
   private String foto;
   private String fotob;
   private String color;
   private String precio;
   private String tipo;
   private String talleS;
   private String talleM;
   private String talleL;
   private String talleXL;
   private String descripcion;
   
//       variable ESPECIAL PARA CLASE productoDAO
   private ArrayList otrosColores;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFotob() {
        return fotob;
    }

    public void setFotob(String fotob) {
        this.fotob = fotob;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTalleS() {
        return talleS;
    }

    public void setTalleS(String talleS) {
        this.talleS = talleS;
    }

    public String getTalleM() {
        return talleM;
    }

    public void setTalleM(String talleM) {
        this.talleM = talleM;
    }

    public String getTalleL() {
        return talleL;
    }

    public void setTalleL(String talleL) {
        this.talleL = talleL;
    }
   
    public String getTalleXL() {
        return talleXL;
    }

    public void setTalleXL(String talleXL) {
        this.talleXL = talleXL;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
//    METODOS ESPECIALES PARA CLASE productoDAO
    public void setOtrosColores(ArrayList otroscolores){
        this.otrosColores = otroscolores;
    }
    public ArrayList getOtrosColores(){
        return this.otrosColores;
    
    }
    
}
