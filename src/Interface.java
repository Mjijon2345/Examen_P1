import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Interface {
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JTextField nombreField;
    private JTextField edadField;
    private JTextField idField;
    private JButton agregarButton;
    private JTextField idField1;
    private JTextArea resultadoArea;
    private JComboBox comboBox1;
    private JButton mostrarButton;
    private JTextArea textArea2;
    private JButton MOSTRARButton;
    private JButton activarSiguienteButton;
    private JButton activarTodosButton;
    private JTextField activarField;
    private JTextField eliminartField;
    private JButton eliminarSiguenteButton;
    private JButton eliminarTodosButton;
    private JTextField restaurarField;
    private JButton restaurarRecientesButton;
    private JButton restaurarUltimoButton;
    private JComboBox prioridadField;

    private void createUIComponents() {
    }

    public static class SocialNetwork {
        private Queue<Persona> colaEspera;
        private PriorityQueue<Persona> colaPrioridad;
        private Stack<Persona> pilaEliminados;

        public SocialNetwork() {
            colaEspera = new LinkedList<>();
            colaPrioridad = new PriorityQueue<>(Comparator.comparingInt(Persona::getPrioridad).reversed());
            pilaEliminados = new Stack<>();
        }

        public void agregarPersona(Persona persona) {
            colaEspera.offer(persona);
        }

        public Persona activarSiguiente() {
            Persona persona = colaEspera.poll();
            if (persona != null) {
                colaPrioridad.offer(persona);
            }
            return persona;
        }

        public void activarTodos() {
            while (!colaEspera.isEmpty()) {
                activarSiguiente();
            }
        }

        public Persona eliminarSiguiente() {
            Persona persona = colaPrioridad.poll();
            if (persona != null) {
                pilaEliminados.push(persona);
            }
            return persona;
        }

        public void eliminarTodos() {
            while (!colaPrioridad.isEmpty()) {
                eliminarSiguiente();
            }
        }

        public Persona restaurarUltimoEliminado() {
            Persona persona = pilaEliminados.pop();
            if (persona != null) {
                colaPrioridad.offer(persona);
            }
            return persona;
        }

        public void restaurarTodosEliminados() {
            while (!pilaEliminados.isEmpty()) {
                restaurarUltimoEliminado();
            }
        }

        public List<Persona> buscarPorId(int id) {
            List<Persona> resultado = new ArrayList<>();
            for (Persona persona : colaEspera) {
                if (persona.getId() == id) {
                    resultado.add(persona);
                }
            }
            for (Persona persona : colaPrioridad) {
                if (persona.getId() == id) {
                    resultado.add(persona);
                }
            }
            return resultado;
        }

        public List<Persona> buscarPrioridad() {
            List<Persona> resultado = new ArrayList<>();
            for (Persona persona : colaPrioridad) {
                if (persona.getPrioridad() >= 50) {
                    resultado.add(persona);
                }
            }
            return resultado;
        }

        public Persona[] getPersonas() {
            return new Persona[0];
        }

    }

    public static class Persona {
        private int id;
        private String nombreCompleto;
        private int edad;
        private int prioridad;

        public Persona(int id, String nombre, int edad, int prioridad) {

        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNombreCompleto() {
            return nombreCompleto;
        }

        public void setNombreCompleto(String nombreCompleto) {
            this.nombreCompleto = nombreCompleto;
        }

        public int getEdad() {
            return edad;
        }

        public void setEdad(int edad) {
            this.edad = edad;
        }

        public int getPrioridad() {
            return prioridad;
        }

        public void setPrioridad(int prioridad) {
            this.prioridad = prioridad;
        }
        public void imprimirPersona() {
            System.out.println("ID: " + this.id);
            System.out.println("Nombre completo: " + this.nombreCompleto);
            System.out.println("Edad: " + this.edad);
            System.out.println("Prioridad: " + this.prioridad);
        }

    }

    public class PersonaGUI extends JFrame implements ActionListener {
        private JTextField idField, nombreField, edadField, prioridadField;
        private JButton agregarButton;
        private final JButton mostrarButton;
        private JTextArea resultadoArea;
        private SocialNetwork socialNetwork;


        public PersonaGUI() {
            super("Gestión de Personas");
            socialNetwork = new SocialNetwork();


            idField = new JTextField(10);
            nombreField = new JTextField(20);
            edadField = new JTextField(5);
            prioridadField = new JTextField(5);
            agregarButton = new JButton("Agregar");
            mostrarButton = new JButton("Mostrar");
            resultadoArea = new JTextArea(10, 30);


            JPanel inputPanel = new JPanel(new GridLayout(4, 2));
            inputPanel.add(new JLabel("ID:"));
            inputPanel.add(idField);
            inputPanel.add(new JLabel("Nombre:"));
            inputPanel.add(nombreField);
            inputPanel.add(new JLabel("Edad:"));
            inputPanel.add(edadField);
            inputPanel.add(new JLabel("Prioridad:"));
            inputPanel.add(prioridadField);


            JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
            buttonPanel.add(agregarButton);
            buttonPanel.add(mostrarButton);


            Container contentPane = getContentPane();
            contentPane.setLayout(new BorderLayout());
            contentPane.add(inputPanel, BorderLayout.CENTER);
            contentPane.add(buttonPanel, BorderLayout.SOUTH);
            contentPane.add(new JScrollPane(resultadoArea), BorderLayout.EAST);


            agregarButton.addActionListener(this);
            mostrarButton.addActionListener(this);


            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            pack();
            setVisible(true);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == agregarButton) {

                int id = Integer.parseInt(idField.getText());
                String nombre = nombreField.getText();
                int edad = Integer.parseInt(edadField.getText());
                int prioridad = Integer.parseInt(prioridadField.getText());
                Persona nuevaPersona = new Persona(id, nombre, edad, prioridad);
                socialNetwork.agregarPersona(nuevaPersona);
            } else if (e.getSource() == mostrarButton) {

                resultadoArea.setText("");
                for (Persona p : socialNetwork.getPersonas()) {
                    resultadoArea.append(p.toString() + "\n");
                }
            }
        }

        public void main(String[] args) {
            PersonaGUI gui = new PersonaGUI();
        }
    }

}
