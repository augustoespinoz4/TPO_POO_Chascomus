package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import Clases.CatalogoProductos;
import Clases.Producto;
import Clases.Electronico;
import Clases.Comida;

public class MostrarProductos extends JFrame {
    private JPanel mainPanel;
    private JPanel productoPanel;
    private JScrollPane scrollPane;
    private CatalogoProductos catalogoProductos;

    public MostrarProductos() {
        setTitle("Administrador de Productos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel = new JPanel(new BorderLayout());

        productoPanel = new JPanel();
        productoPanel.setLayout(new BoxLayout(productoPanel, BoxLayout.Y_AXIS));
        scrollPane = new JScrollPane(productoPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Ajusta la velocidad de desplazamiento vertical
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16); // Ajusta la velocidad de desplazamiento horizontal

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);

        catalogoProductos = new CatalogoProductos();

        // Load products initially
        cargarProductos();

        // Setup a timer to refresh the product list every 5 seconds
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarProductos();
            }
        });
        timer.start();
    }

    private void cargarProductos() {
        productoPanel.removeAll();
        ArrayList<Producto> productos = catalogoProductos.obtenerProductos();

        if (productos.isEmpty()) {
            JLabel noProductsLabel = new JLabel("No hay productos para ver");
            noProductsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            productoPanel.add(noProductsLabel);
        } else {
            for (Producto producto : productos) {
                JPanel productBox = crearCajaProducto(producto);
                productoPanel.add(productBox);
            }
        }

        productoPanel.revalidate();
        productoPanel.repaint();
    }

    private JPanel crearCajaProducto(Producto producto) {
        JPanel cajaProducto = new JPanel(new GridBagLayout());
        cajaProducto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        cajaProducto.setPreferredSize(new Dimension(300, 150)); // Set a fixed size for all product boxes

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding around the components
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Ensure components fill horizontally

        JLabel idLabel = new JLabel("ID: " + producto.getCodigo());
        idLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cajaProducto.add(idLabel, gbc);

        JLabel nameLabel = new JLabel("Nombre: " + producto.getNombre());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        cajaProducto.add(nameLabel, gbc);

        JLabel descLabel = new JLabel("Descripción: " + producto.getDescripcion());
        cajaProducto.add(descLabel, gbc);

        JLabel priceLabel = new JLabel("Precio: " + producto.getPrecio());
        cajaProducto.add(priceLabel, gbc);

        if (producto.getTipo().equals("Electronico")) {
            Electronico electronico = (Electronico) producto;
            JLabel garantiaLabel = new JLabel("Garantía: " + electronico.getGarantia() + " meses");
            cajaProducto.add(garantiaLabel, gbc);
        } else if (producto.getTipo().equals("Comida")) {
            Comida comida = (Comida) producto;
            JLabel fechaVencimientoLabel = new JLabel("Fecha de Vencimiento: " + comida.getFechaVencimiento());
            cajaProducto.add(fechaVencimientoLabel, gbc);

            JLabel pesoLabel = new JLabel("Peso: " + comida.getPeso() + " kg");
            cajaProducto.add(pesoLabel, gbc);
        }

        // Add a filler to ensure the product box maintains the same height
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH; // Ensure filler takes up remaining space
        cajaProducto.add(Box.createVerticalGlue(), gbc);

        return cajaProducto;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MostrarProductos().setVisible(true);
            }
        });
    }
}
