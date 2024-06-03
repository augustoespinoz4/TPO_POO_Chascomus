package Controlador;

import Clases.*;
import DAO.UsuarioDAO;

import java.util.ArrayList;

public class Controlador {

    private final UsuarioDAO usuarioDAO;
    private final ArrayList<Usuario> listaUsuarios;
    private final CatalogoProductos catalogoProductos;
    private final Carrito carrito;

    public Controlador() {
        this.usuarioDAO = new UsuarioDAO();
        this.listaUsuarios = usuarioDAO.cargarUsuarios();
        this.catalogoProductos = new CatalogoProductos();
        this.carrito = new Carrito();
    }

    public Usuario login(String correoElectronico, String contrasena) {
        // Inicializar el resultado como null
        Usuario resultado = null;

        // Inicializar el índice
        int i = 0;

        // Buscar el usuario mientras no se haya encontrado y queden elementos por revisar
        while (resultado == null && i < listaUsuarios.size()) {
            Usuario usuario = listaUsuarios.get(i);
            // Verificar si el correo electrónico y la contraseña coinciden con algún usuario
            if (usuario.getCorreoElectronico().equals(correoElectronico) && usuario.getContrasena().equals(contrasena)) {
                resultado = usuario; // Asignar el usuario correspondiente al resultado
            }
            i++; // Incrementar el índice para pasar al siguiente usuario
        }

        // Devolver el resultado del inicio de sesión
        return resultado;
    }

    public boolean registro(String nombreCompleto, String correoElectronico, String contrasena) {
        // Inicializar la variable de registro como verdadera
        boolean noEstaRegistrado = true;

        // Inicializar el índice
        int i = 0;

        // Verificar si el correo electrónico ya está en uso
        while (noEstaRegistrado && i < listaUsuarios.size()) {
            Usuario usuario = listaUsuarios.get(i);
            if (usuario.getCorreoElectronico().equals(correoElectronico)) {
                noEstaRegistrado = false; // El correo electrónico ya está en uso
            }
            i++; // Incrementar el índice para pasar al siguiente usuario
        }

        // Si el correo electrónico no está en uso, proceder con el registro
        if (noEstaRegistrado) {
            // Crear un nuevo usuario
            Usuario nuevoUsuario = new Cliente(nombreCompleto, correoElectronico, contrasena);
            // Intentar guardar el nuevo usuario
            noEstaRegistrado = usuarioDAO.guardarUsuario(nuevoUsuario);
        }

        // Devolver el estado del registro
        return noEstaRegistrado;
    }

    public boolean darAltaProducto(Producto producto) {
        return catalogoProductos.agregarProducto(producto);
    }

    public int generarCodigoProductoUnico() {
        int codigo = 1;
        while (catalogoProductos.existeCodigoProducto(codigo)) {
            codigo++;
        }
        return codigo;
    }

    public boolean darBajaProducto(Producto producto) {
        return catalogoProductos.eliminarProducto(producto);
    }

    public boolean modificarProducto(Producto productoModificado) {
        return catalogoProductos.modificarProducto(productoModificado);
    }

    public Producto obtenerProductoPorCodigo(int codigo) {
        return catalogoProductos.obtenerProductoPorCodigo(codigo);
    }

    public ArrayList<Producto> obtenerProductos() {
        return catalogoProductos.obtenerProductos();
    }

    public Carrito getCarrito() {
        return carrito;
    }
}
