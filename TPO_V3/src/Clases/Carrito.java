package Clases;

import java.util.ArrayList;

public class Carrito {
    private ArrayList<Producto> productos;
    private Cliente cliente;
    private int cantidad;
    private float precioTotal;

    public Carrito() {
        this.productos = new ArrayList<>();
        this.cantidad = 0;
        this.precioTotal = 0;
    }
    public void agregarItem(Producto producto){
        this.cantidad++;
        this.precioTotal += producto.getPrecio();
        productos.add(producto);
    }
    public void eliminarItem(Producto producto){
        this.cantidad--;
        this.precioTotal -= producto.getPrecio();
        int i=0;
        boolean borrado=false;
        while (i < productos.size() && !borrado ){
            if (producto.getCodigo() == productos.get(i).getCodigo()){
                productos.remove(i);
                borrado=true;
            }
            i++;
        }
    }
    public void borrarCarrito(){ // BONUS PUNTO
        this.cantidad = 0;
        this.precioTotal = 0;
        this.productos.clear();
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public float getPrecioTotal() {
        return precioTotal;
    }
}
