import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Tablero extends JFrame {

    private static final int Tamaño = 17; // Tamaño del tablero (17x17)
    private static final int Tamaño_Celda = 45; // Tamaño de cada celda en píxeles
    private static final int Tamaño_Tablero = Tamaño * Tamaño_Celda; // Tamaño total del tablero

    private JPanel pan; // Panel principal que contiene el tablero
    private JPanel info; // Panel de información de los jugadores
    private JPanel upinfo;
    private JPanel midinfo;
    private JLabel turno;
    private JPanel downinfo;
    private JButton[][] celdas; // Matriz que representa las celdas del tablero

    // Jugadores
    private Jugador jugador1; // Representa al jugador 1
    private Jugador jugador2; // Representa al jugador 2

    private int jugador1Fila = 0; // Fila inicial del jugador 1
    private int jugador1Columna = 8; // Columna inicial del jugador 1
    private int jugador2Fila = Tamaño - 1; // Fila inicial del jugador 2
    private int jugador2Columna = 8; // Columna inicial del jugador 2
    private int murosRestantesJugador1 = 10;
    private int murosRestantesJugador2 = 10;

    private boolean turnoJugador1 = true; // Variable que controla de quién es el turno
    private boolean celdasActivadas = false; // Variable que controla si las celdas están activadas para movimiento
    private boolean modoColocarMuros = false; // Variable que controla si estamos en modo de colocar muros
    private Point primerMuro = null; // Variable para guardar la primera celda de muro seleccionada

    public Tablero() {
        setSize(Tamaño_Tablero, Tamaño_Tablero); // Tamaño de ventana
        setTitle("Quoridor17");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Tamaño_Tablero + 400, Tamaño_Tablero));
        pack();
        setResizable(false);

        LlamarMetodos();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Método para inicializar y configurar componentes
    private void LlamarMetodos() {
        PanelDeJuego();
        PanelDeInfo();
        MensajeJugador1();
        MensajeJugador2();
        CeldasBoton();
        jugador1 = new Jugador(Color.RED);
        jugador1.setPosicion(jugador1Fila, jugador1Columna);
        jugador2 = new Jugador(Color.CYAN);
        jugador2.setPosicion(jugador2Fila, jugador2Columna);
        PosicionJug(jugador1.getPanelJugador(), jugador1Fila, jugador1Columna);
        PosicionJug(jugador2.getPanelJugador(), jugador2Fila, jugador2Columna);
        desactivarCeldas();
        actualizarPosiciones();
    }

    private void PanelDeJuego() {
        pan = new JPanel();
        pan.setBackground(Color.BLACK);
        pan.setLayout(new GridLayout(Tamaño, Tamaño));
        pan.setPreferredSize(new Dimension(Tamaño_Tablero + 100, Tamaño_Tablero));
        getContentPane().add(pan, BorderLayout.WEST); // Agregar el panel al JFrame
    }

    private void PanelDeInfo() {
        info = new JPanel();
        info.setLayout(new BorderLayout());

        upinfo = new JPanel();
        upinfo.setBackground(Color.WHITE);
        upinfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        info.add(upinfo, BorderLayout.NORTH);

        midinfo = new JPanel();
        turno = new JLabel("Turno del Jugador 1");
        midinfo.add(turno);
        info.add(midinfo, BorderLayout.CENTER);

        downinfo = new JPanel();
        downinfo.setBackground(Color.WHITE);
        downinfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        info.add(downinfo, BorderLayout.SOUTH);

        add(info, BorderLayout.EAST);
    }

    private void MensajeJugador1() {
        JOptionPane.showMessageDialog(this, "Jugador1, muévete con las teclas W-A-S-D. Tienes que ir a la fila 16 para ganar. Juegas con ficha roja. Usa la fila y la columna 8 para muros.");
    }

    private void MensajeJugador2() {
        JOptionPane.showMessageDialog(this, "Jugador2, muévete con las flechas del teclado. Tienes que ir a la fila 0 para ganar. Juegas con ficha azul. Usa la fila y la columna 8 para muros.");
    }

    private void CeldasBoton() {
        celdas = new JButton[Tamaño][Tamaño];

        for (int fila = 0; fila < Tamaño; fila++) {
            for (int columna = 0; columna < Tamaño; columna++) {
                celdas[fila][columna] = new JButton();
                celdas[fila][columna].setPreferredSize(new Dimension(Tamaño_Celda, Tamaño_Celda));
                celdas[fila][columna].setBackground(Color.WHITE);
                celdas[fila][columna].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                celdas[fila][columna].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton boton = (JButton) e.getSource();
                        for (int fila = 0; fila < Tamaño; fila++) {
                            for (int columna = 0; columna < Tamaño; columna++) {
                                if (celdas[fila][columna] == boton) {
                                    if (modoColocarMuros) {
                                        colocarMuro(fila, columna, murosRestantesJugador1, murosRestantesJugador2);
                                    } else {
                                        moverJugador(fila, columna);
                                    }
                                    return;
                                }
                            }
                        }
                    }
                });
                pan.add(celdas[fila][columna]); // Agregar botón al panel
            }
        }
    }

    private void desactivarCeldas() {
        for (int fila = 0; fila < Tamaño; fila++) {
            for (int columna = 0; columna < Tamaño; columna++) {
                if ((fila % 2 == 0 && columna % 2 == 0) || (fila % 2 != 0 && columna % 2 != 0)) {
                    celdas[fila][columna].setBackground(Color.BLACK);
                }
            }
        }
    }

    private void actualizarPosiciones() {
        PosicionJug(jugador1.getPanelJugador(), jugador1.getFila(), jugador1.getColumna());
        PosicionJug(jugador2.getPanelJugador(), jugador2.getFila(), jugador2.getColumna());
    }

    private void PosicionJug(JPanel panelJugador, int fila, int columna) {
        celdas[fila][columna].add(panelJugador); // Agregar el panel del jugador a la celda
    }

    private void moverJugador(int fila, int columna) {
        if (turnoJugador1) {
            if ((jugador1.getFila() == fila && Math.abs(jugador1.getColumna() - columna) == 1) ||
                    (jugador1.getColumna() == columna && Math.abs(jugador1.getFila() - fila) == 1)) {
                jugador1.setPosicion(fila, columna);
                PosicionJug(jugador1.getPanelJugador(), fila, columna);
                turnoJugador1 = false;
                turno.setText("Turno del Jugador 2");
            }
        } else {
            if ((jugador2.getFila() == fila && Math.abs(jugador2.getColumna() - columna) == 1) ||
                    (jugador2.getColumna() == columna && Math.abs(jugador2.getFila() - fila) == 1)) {
                jugador2.setPosicion(fila, columna);
                PosicionJug(jugador2.getPanelJugador(), fila, columna);
                turnoJugador1 = true;
                turno.setText("Turno del Jugador 1");
            }
        }
        actualizarPosiciones();
        if (jugador1.getFila() == Tamaño - 1) {
            JOptionPane.showMessageDialog(this, "El jugador 1 ha ganado.");
            System.exit(0);
        }
        if (jugador2.getFila() == 0) {
            JOptionPane.showMessageDialog(this, "El jugador 2 ha ganado.");
            System.exit(0);
        }
    }

    private void colocarMuro(int fila, int columna, int murosRestantesJugador1, int murosRestantesJugador2) {
        Muros m = new Muros(celdas, primerMuro, turnoJugador1, Tamaño, this);
        m.colocarMuro(fila, columna, murosRestantesJugador1, murosRestantesJugador2);
    }
}
