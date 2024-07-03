package Quoridor17;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Tablero extends JFrame{

    private static final int Tamaño = 17; // Tamaño del tablero (9x9)
    private static final int Tamaño_Celda = 45; // Tamaño de cada celda en píxeles
    private static final int Tamaño_Tablero = Tamaño * Tamaño_Celda; // Tamaño total del tablero

    private JPanel pan; // Panel principal que contiene el tablero
    private JPanel info; // Panel de información de los jugadores
    private JPanel upinfo;
    private JPanel midinfo;
    private JLabel turno;
    private JPanel downinfo;
    private JButton[][] celdas; // Matriz que representa las celdas del tablero

    //Jugadores
    private JPanel jugador1; // Panel que representa al jugador 1
    private JPanel jugador2; // Panel que representa al jugador 2

    private int jugador1Fila = 0; // Fila inicial del jugador 1
    private int jugador1Columna = 4; // Columna inicial del jugador 1
    private int jugador2Fila = Tamaño - 1; // Fila inicial del jugador 2
    private int jugador2Columna = 4; // Columna inicial del jugador 2

    private boolean turnoJugador1 = true; // Variable que controla de quién es el turno
    //private boolean finJuego = false;

    
    public Tablero(){
    setSize(Tamaño_Tablero, Tamaño_Tablero); //Tamaño de ventana
    setTitle("Quoridor17");
    //setLocation(150,150);
    //setBounds(250, 250, 500, 500); //Posicion y tamaño de ventana
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(Tamaño_Tablero + 400, Tamaño_Tablero));
    pack();
    setResizable(false);


    Compos();
  

    setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    //Metodo
    private void Compos(){
        PanelDeJuego();
        PanelDeInfo();
        MensajeJugador1();
        MensajeJugador2();
        CeldasBoton();
        jugador1 = PaneldeJugador(Color.RED);
        PosicionJug(jugador1, jugador1Fila, jugador1Columna);
        jugador2 = PaneldeJugador(Color.CYAN);
        PosicionJug(jugador2, jugador2Fila, jugador2Columna);
        desactivarCeldas();
        //MovTecla();
    }   

    private void PanelDeJuego(){

        pan = new JPanel();

        pan.setBackground(Color.BLACK);
        pan.setLayout(new GridLayout(Tamaño, Tamaño));
        pan.setPreferredSize(new Dimension(Tamaño_Tablero + 100 , Tamaño_Tablero));
        getContentPane().add(pan, "West"); // Agregar el primer panel al JFrame
    }

    private void PanelDeInfo() {

        info = new JPanel();
        info.setLayout(new GridLayout(3, 1)); // Dividido en dos subpaneles verticalmente

        info.setPreferredSize(new Dimension(300, Tamaño_Tablero)); // Establecer tamaño preferido del panel de información
        getContentPane().add(info, "East"); // Agregar el segundo panel al JFrame


        upinfo = new JPanel();
        upinfo.setBackground(Color.WHITE);
        //upinfo.set IMAGEN
        upinfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        info.add(upinfo);


        midinfo = new JPanel();
        turno = new JLabel();
        turno.setText("Turno de Jugador 1");
        turno.setForeground(Color.BLACK);
        turno.setBackground(Color.CYAN);        
        turno.setOpaque(true);
        midinfo.add(turno);
        midinfo.setBackground(Color.BLUE);
        midinfo.add(BotonMoverse());
        info.add(midinfo);


        downinfo = new JPanel();
        downinfo.setBackground(Color.WHITE);
        downinfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        info.add(downinfo);
    }
    private void MensajeJugador1(){

        JLabel etiqueta = new JLabel();
        etiqueta.setText("Jugador1");
        etiqueta.setForeground(Color.BLACK);
        etiqueta.setBackground(Color.CYAN);        
        etiqueta.setOpaque(true);
        upinfo.add(etiqueta);
    }

    private void MensajeJugador2(){

        JLabel etiqueta = new JLabel();
        etiqueta.setText("Jugador2");
        etiqueta.setForeground(Color.BLACK);
        etiqueta.setBackground(Color.CYAN);        
        etiqueta.setOpaque(true);
        downinfo.add(etiqueta);
    }

    private void CeldasBoton(){

        celdas = new JButton[Tamaño][Tamaño]; // Inicializa la matriz de celdas

        for (int i = 0; i < Tamaño; i++) {
            for (int j = 0; j < Tamaño; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                final int row = i;
                final int col = j;

                celdas[i][j] = new JButton(); // Crea un nuevo boton para cada celda
                celdas[i][j].setPreferredSize(new Dimension(Tamaño_Celda, Tamaño_Celda));
                celdas[i][j].setBackground(Color.YELLOW);
                celdas[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Agrega un borde negro a cada celda
                pan.add(celdas[i][j]); // Agrega la celda al panel del tablero
                celdas[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me){
                        moverJugador(row, col);
                        actualizarTurno();
                    }
                });
            } else {
                JPanel emptyPanel = new JPanel();
                emptyPanel.setPreferredSize(new Dimension(20, 20));
                emptyPanel.setBackground(Color.GRAY); // Color for wall spaces
                emptyPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for visibility
                pan.add(emptyPanel);
                }
            }
        
        }
        
    }
    
    private void actualizarTurno() {
        if (turnoJugador1) {
            turno.setText("Turno de Jugador 1");
        } else {
            turno.setText("Turno de Jugador 2");
        }
        desactivarCeldas();
    }

    private JPanel PaneldeJugador(Color color) {
        JPanel panJug = new JPanel();
        panJug.setBackground(color); // Establece el color del panel
        panJug.setPreferredSize(new Dimension(Tamaño_Celda, Tamaño_Celda)); // Establece el tamaño preferido del panel
        return panJug;
    }

    // Coloca a un jugador en una celda específica
    private void PosicionJug(JPanel panJug, int fila, int columna) {
        if (celdas[fila][columna] != null) {
            celdas[fila][columna].setLayout(new BorderLayout());
            celdas[fila][columna].add(panJug); // Agrega el panel del jugador a la celda
            validate(); // Valida el contenedor de nivel superior del componente
            repaint(); // Repinta el componente
        }
    }

    private void moverJugador(int nuevaFila, int nuevaColumna) {
        int filActual = turnoJugador1 ? jugador1Fila : jugador2Fila; // Fila a la que se moverá el jugador
        int columnActual = turnoJugador1 ? jugador1Columna : jugador2Columna; // Columna a la que se moverá el jugador

        if (esMovimientoValido(filActual, columnActual, nuevaFila, nuevaColumna)) {
            JPanel jugador = turnoJugador1 ? jugador1 : jugador2;
            celdas[filActual][columnActual].remove(jugador);
            PosicionJug(jugador, nuevaFila, nuevaColumna);

            if (turnoJugador1) {
                jugador1Fila = nuevaFila;
                jugador1Columna = nuevaColumna;
            } else {
                jugador2Fila = nuevaFila;
                jugador2Columna = nuevaColumna;
            }

            turnoJugador1 = !turnoJugador1; // Cambia el turno al otro jugador
            desactivarCeldas();
        }
    }

    // Verifica si el movimiento es válido
    private boolean esMovimientoValido(int filaActual, int columnaActual, int nuevaFila, int nuevaColumna) {
        int diffFila = Math.abs(filaActual - nuevaFila);
        int diffColumna = Math.abs(columnaActual - nuevaColumna);
        return (diffFila == 2 && diffColumna == 0) || (diffFila == 0 && diffColumna == 2);
    }

    private JButton BotonMoverse(){
        JButton BMover = new JButton();
        BMover.setText("Mover");
        BMover.setBackground(Color.RED);
        BMover.setForeground(Color.BLACK);
        BMover.setBounds(getBounds());
        BMover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                activarCeldas();
            }
        });
        return(BMover);
    }

    private void activarCeldas() {
        for (int i = 0; i < Tamaño; i++) {
            for (int j = 0; j < Tamaño; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    celdas[i][j].setEnabled(true);
                }
            }
        }
    }

    private void desactivarCeldas() {
        for (int i = 0; i < Tamaño; i++) {
            for (int j = 0; j < Tamaño; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    celdas[i][j].setEnabled(false);
                }
            }
        }
    }
         // Muestra un mensaje de victoria y cierra el juego
    /*private void mostrarMensajeVictoria(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje); // Muestra el mensaje en un cuadro de diálogo
        System.exit(0); // Cierra la aplicación
    }*/

}
