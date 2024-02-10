import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JFrame {

    private JTextField usuarioLoginField, usuarioRegistroField, nombreField, apellidosField, correoField, telefonoField;
    private JPasswordField contraseniaLoginField, contraseniaRegistroField;

    private List<Usuario> usuariosRegistrados = new ArrayList<>();

    public Main() {
        this.usuarioLoginField = new JTextField();
        this.contraseniaLoginField = new JPasswordField();

        setTitle("Registro e Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JButton botonRegistro = new JButton("Registrarse");
        JButton botonInicioSesion = new JButton("Iniciar Sesión");

        botonRegistro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaRegistro();
            }
        });

        botonInicioSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaInicioSesion();
            }
        });

        setLayout(new GridLayout(1, 2));
        add(botonRegistro);
        add(botonInicioSesion);
    }

    private void mostrarVentanaRegistro() {
        SimuladorRegistro simulador = new SimuladorRegistro(this);
        simulador.iniciarInterfaz();
    }

    private void mostrarVentanaInicioSesion() {
        SimuladorInicioSesion simuladorInicioSesion = new SimuladorInicioSesion(this);
        simuladorInicioSesion.iniciarInterfaz();
    }

    private void iniciarSesion(String usuario, char[] contraseniaChars) {
        String contrasenia = new String(contraseniaChars);

        for (Usuario u : usuariosRegistrados) {
            if (u.getNombreUsuario().equals(usuario) && u.getContrasenia().equals(contrasenia)) {
                mostrarMensaje("Inicio de Sesión Exitoso", "¡Bienvenido, " + usuario + "!");
                abrirVentanaProductos();
                limpiarCamposLogin();
                return;
            }
        }

        mostrarMensaje("Error de Inicio de Sesión", "Nombre de usuario o contraseña incorrectos.");
    }

    private void abrirVentanaProductos() {
        VentanaProductos ventanaProductos = new VentanaProductos(this);
        ventanaProductos.setVisible(true);
        setVisible(false);
    }

    private void limpiarCamposLogin() {
        usuarioLoginField.setText("");
        contraseniaLoginField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main interfaz = new Main();
            interfaz.setVisible(true);
        });
    }

    private class SimuladorRegistro extends JFrame {

        private Main interfazRegistroLogin;

        public SimuladorRegistro(Main interfazRegistroLogin) {
            this.interfazRegistroLogin = interfazRegistroLogin;
        }

        public void iniciarInterfaz() {
            JFrame frame = new JFrame("Pantalla de Registro");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(8, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel usuarioLabel = new JLabel("Nombre de Usuario:");
            usuarioRegistroField = new JTextField();

            JLabel contraseniaLabel = new JLabel("Contraseña:");
            contraseniaRegistroField = new JPasswordField();

            JLabel nombreLabel = new JLabel("Nombre:");
            nombreField = new JTextField();

            JLabel apellidosLabel = new JLabel("Apellidos:");
            apellidosField = new JTextField();

            JLabel correoLabel = new JLabel("Correo Electrónico:");
            correoField = new JTextField();

            JLabel telefonoLabel = new JLabel("Teléfono:");
            telefonoField = new JTextField();

            JButton registrarButton = new JButton("Registrar Usuario");

            registrarButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    registrarUsuario();
                }
            });

            panel.add(usuarioLabel);
            panel.add(usuarioRegistroField);
            panel.add(contraseniaLabel);
            panel.add(contraseniaRegistroField);
            panel.add(nombreLabel);
            panel.add(nombreField);
            panel.add(apellidosLabel);
            panel.add(apellidosField);
            panel.add(correoLabel);
            panel.add(correoField);
            panel.add(telefonoLabel);
            panel.add(telefonoField);
            panel.add(registrarButton);

            frame.getContentPane().add(panel);
            frame.setSize(300, 250);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        private void registrarUsuario() {
            String usuario = usuarioRegistroField.getText();
            if (existeUsuarioConNombre(usuario)) {
                interfazRegistroLogin.mostrarMensaje("Error", "Ya existe un usuario con este nombre de usuario. Por favor, elija otro.");
                return;
            }
            char[] contraseniaChars = contraseniaRegistroField.getPassword();
            String contrasenia = new String(contraseniaChars);
            String nombre = nombreField.getText();
            String apellidos = apellidosField.getText();
            String correo = correoField.getText();
            String telefono = telefonoField.getText();

            Usuario nuevoUsuario = new Usuario(usuario, contrasenia, nombre, apellidos, correo, telefono);
            usuariosRegistrados.add(nuevoUsuario);
            interfazRegistroLogin.mostrarMensaje("Registro Exitoso", "Usuario registrado:\nNombre de Usuario: " + usuario +
                    "\nNombre: " + nombre + "\nApellidos: " + apellidos + "\nCorreo Electrónico: " +
                    correo + "\nTeléfono: " + telefono);

            limpiarCamposRegistro();
        }

        private boolean existeUsuarioConNombre(String nombreUsuario) {
            for (Usuario usuario : usuariosRegistrados) {
                if (usuario.getNombreUsuario().equals(nombreUsuario)) {
                    return true;
                }
            }
            return false;
        }

        private void limpiarCamposRegistro() {
            usuarioRegistroField.setText("");
            contraseniaRegistroField.setText("");
            nombreField.setText("");
            apellidosField.setText("");
            correoField.setText("");
            telefonoField.setText("");
        }
    }

    private class SimuladorInicioSesion extends JFrame {

        private Main interfazInicioSesion;

        public SimuladorInicioSesion(Main interfazInicioSesion) {
            this.interfazInicioSesion = interfazInicioSesion;
        }

        public void iniciarInterfaz() {
            JFrame frame = new JFrame("Pantalla de Inicio de Sesión");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel usuarioLabel = new JLabel("Nombre de Usuario:");
            usuarioLoginField = new JTextField();

            JLabel contraseniaLabel = new JLabel("Contraseña:");
            contraseniaLoginField = new JPasswordField();

            JButton iniciarSesionButton = new JButton("Iniciar Sesión");

            iniciarSesionButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String usuario = usuarioLoginField.getText();
                    char[] contraseniaChars = contraseniaLoginField.getPassword();
                    iniciarSesion(usuario, contraseniaChars);
                }
            });

            panel.add(usuarioLabel);
            panel.add(usuarioLoginField);
            panel.add(contraseniaLabel);
            panel.add(contraseniaLoginField);
            panel.add(iniciarSesionButton);

            frame.getContentPane().add(panel);
            frame.setSize(300, 150);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    private void mostrarMensaje(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    private class Usuario {
        private String nombreUsuario, contrasenia, nombre, apellidos, correo, telefono;

        public Usuario(String nombreUsuario, String contrasenia, String nombre, String apellidos, String correo, String telefono) {
            this.nombreUsuario = nombreUsuario;
            this.contrasenia = contrasenia;
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.correo = correo;
            this.telefono = telefono;
        }

        public String getNombreUsuario() {
            return nombreUsuario;
        }

        public String getContrasenia() {
            return contrasenia;
        }
    }

    private class VentanaProductos extends JFrame {

        private Main interfazPrincipal;
        private JComboBox<String> tiposCesta;
        private JTextArea listaProductos;

        private Map<String, List<String>> productosPorTipo = new HashMap<>();

        public VentanaProductos(Main interfazPrincipal) {
            this.interfazPrincipal = interfazPrincipal;


            List<String> navidadProductos = new ArrayList<>();
            navidadProductos.add("Bola de Navidad");
            navidadProductos.add("Papel de Regalo");
            navidadProductos.add("Adornos festivos");

            List<String> cumpleanosProductos = new ArrayList<>();
            cumpleanosProductos.add("Globos de Cumpleaños");
            cumpleanosProductos.add("Velas de Cumpleaños");
            cumpleanosProductos.add("Tarjeta de Feliz Cumpleaños");

            List<String> sanValentinProductos = new ArrayList<>();
            sanValentinProductos.add("Rosas Rojas");
            sanValentinProductos.add("Tarjeta de San Valentín");
            sanValentinProductos.add("Chocolate en Forma de Corazón");

            productosPorTipo.put("Navidad", navidadProductos);
            productosPorTipo.put("Cumpleaños", cumpleanosProductos);
            productosPorTipo.put("San Valentín", sanValentinProductos);


            setTitle("Productos");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(600, 400);
            setLocationRelativeTo(null);


            tiposCesta = new JComboBox<>(productosPorTipo.keySet().toArray(new String[0]));
            tiposCesta.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mostrarProductosSeleccionados();
                }
            });


            listaProductos = new JTextArea();
            listaProductos.setEditable(false);


            JButton volverButton = new JButton("Volver");
            volverButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    volverAInterfazPrincipal();
                }
            });


            JPanel panel = new JPanel(new BorderLayout());
            panel.add(tiposCesta, BorderLayout.NORTH);
            panel.add(new JScrollPane(listaProductos), BorderLayout.CENTER);
            panel.add(volverButton, BorderLayout.SOUTH);


            getContentPane().add(panel);
        }

        private void mostrarProductosSeleccionados() {
            String tipoSeleccionado = (String) tiposCesta.getSelectedItem();
            if (tipoSeleccionado != null) {
                List<String> productos = productosPorTipo.get(tipoSeleccionado);
                if (productos != null) {
                    StringBuilder sb = new StringBuilder();
                    for (String producto : productos) {
                        sb.append(producto).append("\n");
                    }
                    listaProductos.setText(sb.toString());
                }
            }
        }

        private void volverAInterfazPrincipal() {
            setVisible(false);
            interfazPrincipal.setVisible(true);
        }
    }
}
