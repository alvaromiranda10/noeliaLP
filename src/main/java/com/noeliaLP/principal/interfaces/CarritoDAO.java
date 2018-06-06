package com.noeliaLP.principal.interfaces;

import com.noeliaLP.principal.model.Carrito;

import java.util.ArrayList;

public interface CarritoDAO {

    String agregarCompra(Carrito a);
    String eliminarCompra(String id);
    ArrayList listarCompras(String id);
}
