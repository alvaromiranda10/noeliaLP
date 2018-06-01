package InterfacesDAO;

import Modelo.Carrito;
import java.util.ArrayList;

/**
 *
 * @author alvaro
 */
public interface CarritoDAO {
    
    public String agregarCompra(Carrito a);
    public String eliminarCompra(String id);
    public ArrayList listarCompras(String id);
}
