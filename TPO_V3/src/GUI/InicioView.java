package GUI;

import Clases.Usuario;
import Controlador.Controlador;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class InicioView extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public InicioView(Usuario usuario, Controlador controlador) {
        setTitle("Inicio");
        // Establecer el icono de la ventana
        try {
            Image icono = ImageIO.read(new File("Imagenes/Iconos/Logo_Chascomus.png"));
            setIconImage(icono);
        } catch (IOException e) {
            // Manejar cualquier error de lectura de archivo aquí
            e.printStackTrace();
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) (screenSize.width * 0.7), (int) (screenSize.height * 0.8));
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Color azul definido por su código hexadecimal
        Color azulPersonalizado = new Color(0x004987);

        // Configuración del header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(azulPersonalizado); // Azul personalizado
        headerPanel.setOpaque(true);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Aquí se ajusta el tamaño del headerPanel
        headerPanel.setPreferredSize(new Dimension(this.getWidth(), 125)); // Por ejemplo, 125 píxeles de alto
        headerPanel.setMinimumSize(new Dimension(this.getWidth(), 125));
        headerPanel.setMaximumSize(new Dimension(this.getWidth(), 125));

        // Imagen del ícono de la aplicación
        ImageIcon iconoApp = new ImageIcon("Imagenes/Iconos/Logo_Chascomus.png"); // Ruta de tu imagen
        Image imagenIconoApp = iconoApp.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Redimensionar imagen
        ImageIcon iconoAppRedimensionado = new ImageIcon(imagenIconoApp);
        JLabel lblIconoApp = new JLabel(iconoAppRedimensionado);
        lblIconoApp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Cambiar a la vista de inicio en lugar de abrir una nueva ventana
                cardLayout.show(mainPanel, "inicio");
            }
        });
        headerPanel.add(lblIconoApp, BorderLayout.WEST); // Alineado a la izquierda

        // Imagen del logo de perfil
        ImageIcon iconoPerfil = new ImageIcon("Imagenes/Iconos/Cliente_User.png"); // Ruta de tu imagen de perfil
        Image imagenIconoPerfil = iconoPerfil.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Redimensionar imagen
        ImageIcon iconoPerfilRedimensionado = new ImageIcon(imagenIconoPerfil);
        JLabel lblIconoPerfil = new JLabel(iconoPerfilRedimensionado);
        JLabel lblNombreCompleto = new JLabel(usuario.getNombreCompleto());
        lblNombreCompleto.setForeground(Color.white); // cambiar el color del texto.
        lblNombreCompleto.setHorizontalAlignment(SwingConstants.RIGHT);

        // Panel para contener el nombre del usuario y su foto de perfil
        JPanel usuarioPanel = new JPanel(new BorderLayout());
        usuarioPanel.setOpaque(false);
        usuarioPanel.add(lblNombreCompleto, BorderLayout.CENTER);
        usuarioPanel.add(lblIconoPerfil, BorderLayout.EAST);

        // Botón de herramientas (Configuración)
        JLabel lblSettings = new JLabel();
        Image imgSettingsScaled = null;
        try {
            // Cargar la imagen desde el archivo
            Image imgSettings = ImageIO.read(new File("Imagenes/Iconos/Settings.png"));
            // Escalar la imagen al tamaño deseado
            imgSettingsScaled = imgSettings.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            // Establecer la imagen escalada como icono del JLabel
            lblSettings.setIcon(new ImageIcon(imgSettingsScaled));
        } catch (IOException e) {
            // Manejar cualquier error de lectura de archivo aquí
            e.printStackTrace();
        }
        lblSettings.setPreferredSize(new Dimension(25, 25)); // Tamaño del icono
        // ACCIONES DE LAS OPCIONES
        Image finalImgSettingsScaled = imgSettingsScaled;
        lblSettings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Mostrar el menú de opciones cuando se hace clic en el botón de herramientas
                JPopupMenu menu = new JPopupMenu();

                // Opciones típicas del menú
                JMenuItem opcion1 = new JMenuItem("Ver Electrónicos");
                opcion1.addActionListener(e1 -> {
                    // Cambiar a la vista de electrónicos
                    cardLayout.show(mainPanel, "electronicos");
                });

                JMenuItem opcion2 = new JMenuItem("Ver Comida");
                opcion2.addActionListener(e1 -> {
                    // Cambiar a la vista de comida
                    cardLayout.show(mainPanel, "comida");
                });

                // Opción para cerrar sesión
                JMenuItem cerrarSesion = new JMenuItem("Cerrar Sesión");
                cerrarSesion.addActionListener(e1 -> {
                    // Preguntar al usuario si desea cerrar sesión
                    int opcion = JOptionPane.showConfirmDialog(null, "¿Está seguro de que desea cerrar sesión?",
                            "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
                    if (opcion == JOptionPane.YES_OPTION) {
                        // Si el usuario confirma, cerrar la sesión y volver a la vista de login
                        dispose(); // Cerrar la ventana actual
                        LoginView loginView = new LoginView();
                        loginView.setVisible(true);
                    }
                });

                // Agregar opciones al menú
                menu.add(opcion1);
                menu.add(opcion2);
                menu.addSeparator(); // Separador entre las opciones típicas y cerrar sesión
                menu.add(cerrarSesion);

                // Mostrar el menú en la posición del botón de herramientas
                menu.show(lblSettings, 0, lblSettings.getHeight());
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar el tamaño de la imagen al pasar el ratón por encima
                int newWidth = (int) (finalImgSettingsScaled.getWidth(null) * 1.1); // Aumentar el ancho un 10%
                int newHeight = (int) (finalImgSettingsScaled.getHeight(null) * 1.1); // Aumentar la altura un 10%
                Image resizedImage = finalImgSettingsScaled.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                lblSettings.setIcon(new ImageIcon(resizedImage));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar el tamaño original de la imagen al salir el ratón
                lblSettings.setIcon(new ImageIcon(finalImgSettingsScaled));
            }
        });

        // Botón del carrito
        JButton btnCarrito = new JButton();
        btnCarrito.setOpaque(false); // Hace que el fondo del botón sea transparente
        btnCarrito.setContentAreaFilled(false); // Elimina el fondo del botón
        btnCarrito.setBorderPainted(false); // Elimina el borde del botón
        btnCarrito.setFocusable(false);
        try {
            // Cargar la imagen desde el archivo
            Image imgCarrito = ImageIO.read(new File("Imagenes/Iconos/Shopping_Cart.png"));
            // Escalar la imagen al tamaño deseado
            Image imgCarritoScaled = imgCarrito.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            // Establecer la imagen escalada como icono del JButton
            btnCarrito.setIcon(new ImageIcon(imgCarritoScaled));
        } catch (IOException e) {
            // Manejar cualquier error de lectura de archivo aquí
            e.printStackTrace();
        }

        // Configurar el borde del botón cuando el ratón entra y sale
        btnCarrito.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambiar el borde del botón al pasar el ratón por encima
                btnCarrito.setBorderPainted(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Restaurar el borde del botón al salir el ratón
                btnCarrito.setBorderPainted(false);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Mostrar la opción "Ver carrito" cuando se hace clic en el botón del carrito
                JPopupMenu menu = new JPopupMenu();
                JMenuItem verCarrito = new JMenuItem("Ver Carrito");
                verCarrito.addActionListener(e1 -> {
                    // Cambiar a la vista del carrito
                    cardLayout.show(mainPanel, "carrito");
                });
                menu.add(verCarrito);
                menu.show(btnCarrito, 0, btnCarrito.getHeight());
            }
        });

        btnCarrito.setPreferredSize(new Dimension(35, 35)); // Tamaño del icono

        // Panel para contener los botones de herramientas
        JPanel herramientasPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        herramientasPanel.setOpaque(false);
        herramientasPanel.add(btnCarrito);
        herramientasPanel.add(lblSettings);

        // Añadir componentes al headerPanel
        headerPanel.add(usuarioPanel, BorderLayout.CENTER);
        headerPanel.add(herramientasPanel, BorderLayout.EAST);

        // Añadir el header al frame
        add(headerPanel, BorderLayout.NORTH);

        // Crear el panel principal con CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Crear los diferentes paneles de contenido
        JPanel inicioPanel = new JPanel();
        inicioPanel.add(new JLabel("Bienvenido a la vista de inicio"));

        JPanel electronicosPanel = new JPanel();
        electronicosPanel.add(new JLabel("Aquí puedes ver los electrónicos"));

        JPanel comidaPanel = new JPanel();
        comidaPanel.add(new JLabel("Aquí puedes ver la comida"));

        JPanel carritoPanel = new JPanel();
        carritoPanel.add(new JLabel("Aquí puedes ver tu carrito"));

        // Añadir los paneles al mainPanel con un identificador
        mainPanel.add(inicioPanel, "inicio");
        mainPanel.add(electronicosPanel, "electronicos");
        mainPanel.add(comidaPanel, "comida");
        mainPanel.add(carritoPanel, "carrito");

        // Añadir el mainPanel al frame
        add(mainPanel, BorderLayout.CENTER);

        // Hacer la ventana redimensionable
        setMinimumSize(new Dimension(900, 600)); // Tamaño mínimo para que no se deforme

        // Mostrar la ventana
        setVisible(true);
    }
}
