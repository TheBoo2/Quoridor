package Quoridor    ;

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
    private JButton turno;
    private JPanel downinfo;
    private JButton[][] celdas; // Matriz que representa las celdas del tablero
    private JLabel posicionJugador1;
    private JLabel posicionJugador2;
    

    // Jugadores
    private JPanel jugador1; // Panel que representa al jugador 1
    private JPanel jugador2; // Panel que representa al jugador 2

    private int jugador1Fila = 0; // Fila inicial del jugador 1
    private int jugador1Columna = 8; // Columna inicial del jugador 1
    private int jugador2Fila = Tamaño - 1; // Fila inicial del jugador 2
    private int jugador2Columna = 8; // Columna inicial del jugador 2
    private int murosRestantesJugador1 = 10;
    private int murosRestantesJugador2 = 10;
    JLabel murosDeJ1 = new JLabel("Muros restantes jugador 1: " + murosRestantesJugador1);
    JLabel murosDeJ2 = new JLabel("Muros restantes jugador 2: " + murosRestantesJugador2);


    private boolean turnoJugador1 = true; // Variable que controla de quién es el turno
    private boolean celdasActivadas = false; // Variable que controla si las celdas están activadas para movimiento
    private boolean modoColocarMuros = false; // Variable que controla si estamos en modo de colocar muros
    private Point primerMuro = null; // Variable para guardar la primera celda de muro seleccionada

    public Tablero() {

        setSize(Tamaño_Tablero, Tamaño_Tablero); // Tamaño de ventana
        setTitle("Quoridor");
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(Tamaño_Tablero + 400, Tamaño_Tablero));
        pack();
        setResizable(false);

        LlamarMetodos();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    // Metodo
    private void LlamarMetodos() {
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
        actualizarPosiciones();
        actualizarmurosrestantes();
    }

    private void PanelDeJuego() {
        pan = new JPanel();
        pan.setBackground(Color.BLACK);
        pan.setLayout(new GridLayout(Tamaño, Tamaño));
        pan.setPreferredSize(new Dimension(Tamaño_Tablero + 85, Tamaño_Tablero));
        getContentPane().add(pan, "West"); // Agregar el primer panel al JFrame
    }

    private void PanelDeInfo() {
        info = new JPanel();
        info.setLayout(new GridLayout(3, 1)); // Dividido en dos subpaneles verticalmente

        info.setPreferredSize(new Dimension(300, Tamaño_Tablero)); // Establecer tamaño preferido del panel de información
        getContentPane().add(info, "East"); // Agregar el segundo panel al JFrame

        upinfo = new JPanel();
        upinfo.setBackground(Color.WHITE);
        upinfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        info.add(upinfo);

        midinfo = new JPanel();
        midinfo.setLayout(new GridLayout(4, 1));
        midinfo.setBackground(Color.DARK_GRAY);
        turno = new JButton();
        turno.setText("Turno de Jugador 1");
        turno.setForeground(Color.WHITE);
        turno.setBackground(Color.GRAY);
        turno.setBounds(getBounds());

        midinfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        midinfo.add(turno);
        midinfo.add(BotonMoverse());
        midinfo.add(BotonColocarMuros());
        midinfo.add(Bsalida());
        info.add(midinfo);

        downinfo = new JPanel();
        downinfo.setBackground(Color.WHITE);
        downinfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        info.add(downinfo);

        posicionJugador1 = new JLabel("Posición: (" + jugador1Fila + ", " + jugador1Columna + ")");
        posicionJugador2 = new JLabel("Posición: (" + jugador2Fila + ", " + jugador2Columna + ")");

        upinfo.add(posicionJugador1, BorderLayout.CENTER);
        downinfo.add(posicionJugador2, BorderLayout.CENTER);

        murosDeJ1 = new JLabel("Muros restantes jugador 1: " + murosRestantesJugador1);
        murosDeJ2 = new JLabel("Muros restantes jugador 2: " + murosRestantesJugador2);

        upinfo.add(murosDeJ1, BorderLayout.CENTER);
        downinfo.add(murosDeJ2, BorderLayout.CENTER);
    }

    private void MensajeJugador1() {
        JLabel etiqueta = new JLabel();
        etiqueta.setText("Jugador1");
        etiqueta.setForeground(Color.BLACK);
        etiqueta.setBackground(Color.CYAN);
        etiqueta.setOpaque(true);
        upinfo.add(etiqueta);
    }

    private void MensajeJugador2() {
        JLabel etiqueta = new JLabel();
        etiqueta.setText("Jugador2");
        etiqueta.setForeground(Color.BLACK);
        etiqueta.setBackground(Color.CYAN);
        etiqueta.setOpaque(true);
        downinfo.add(etiqueta);
    }

    private void CeldasBoton() {
        celdas = new JButton[Tamaño][Tamaño]; // Initialize the matrix of cells
    
        for (int i = 0; i < Tamaño; i++) {
            for (int j = 0; j < Tamaño; j++) {
                final int row = i;
                final int col = j;
    
                if (i % 2 == 0 && j % 2 == 0) {
                    celdas[i][j] = new JButton(); // Create a new button for each cell
                    celdas[i][j].setPreferredSize(new Dimension(Tamaño_Celda, Tamaño_Celda));
                    celdas[i][j].setBackground(Color.YELLOW);
                    celdas[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    pan.add(celdas[i][j]);
                    celdas[i][j].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent me) {
                            if (modoColocarMuros) {
                                colocarMuro(row, col);
                            } else {
                                moverJugador(row, col);
                                actualizarTurno();
                                verificarGanador();
                            }
                        }
                    });
                } else {
                    celdas[i][j] = new JButton(); // Initialize the button even for non-playable cells
                    celdas[i][j].setPreferredSize(new Dimension(Tamaño_Celda, Tamaño_Celda));
                    celdas[i][j].setBackground(Color.GRAY); // Set color for wall spaces
                    celdas[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    pan.add(celdas[i][j]);
                    celdas[i][j].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent me) {
                            if (modoColocarMuros) {
                                colocarMuro(row, col);
                            }
                        }
                    });
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
        if (!celdasActivadas) {
            return; // No permitir el movimiento si las celdas no están activadas
        }

        int filActual = turnoJugador1 ? jugador1Fila : jugador2Fila; // Fila a la que se moverá el jugador
        int columnActual = turnoJugador1 ? jugador1Columna : jugador2Columna; // Columna a la que se moverá el jugador

        if (esMovimientoValido(filActual, columnActual, nuevaFila, nuevaColumna) && !esMovimientoBloqueado(filActual, columnActual, nuevaFila, nuevaColumna)) {
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
            verificarGanador();
            actualizarPosiciones();
            actualizarmurosrestantes();
        }
    }

    // Verifica si el movimiento es válido
    private boolean esMovimientoValido(int filaActual, int columnaActual, int nuevaFila, int nuevaColumna) {
        int diffFila = Math.abs(filaActual - nuevaFila);
        int diffColumna = Math.abs(columnaActual - nuevaColumna);
        return (diffFila == 2 && diffColumna == 0) || (diffFila == 0 && diffColumna == 2);
    }

    private JButton BotonMoverse() {
        JButton BMover = new JButton();
        BMover.setText("Mover");
        BMover.setBackground(Color.RED);
        BMover.setForeground(Color.BLACK);
        BMover.setBounds(getBounds());
        BMover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activarCeldas();
            }
        });
        return BMover;
    }

    private JButton Bsalida(){
        JButton Bsalir = new JButton();
        Bsalir.setText("Pulsar para salir y cerrar el programa");
        Bsalir.setBackground(Color.RED);
        Bsalir.setForeground(Color.BLACK);
        Bsalir.setBounds(getBounds());
        Bsalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        return Bsalir;
    }

    private JButton BotonColocarMuros() {
        JButton BColocarMuros = new JButton();
        BColocarMuros.setText("Colocar Muros");
        BColocarMuros.setBackground(Color.GREEN);
        BColocarMuros.setForeground(Color.BLACK);
        BColocarMuros.setBounds(getBounds());
        BColocarMuros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modoColocarMuros = !modoColocarMuros;
                actualizarModoColocarMuros();
            }
        });
        return BColocarMuros;
    }

    private void actualizarModoColocarMuros() {
        for (int i = 0; i < Tamaño; i++) {
            for (int j = 0; j < Tamaño; j++) {
                if (i % 2 != 0 || j % 2 != 0) {
                    if (celdas[i][j].getBackground() != Color.BLACK) { // No cambiar el color de las celdas con muros
                        celdas[i][j].setEnabled(modoColocarMuros);
                        if (modoColocarMuros) {
                            celdas[i][j].setBackground(Color.ORANGE);
                        }
                        else{
                            celdas[i][j].setBackground(Color.GRAY);
                        }
                    }
                }
            }
        }
        if (!modoColocarMuros) {
            primerMuro = null;
        }
    }
    

// Método para colocar un muro
    private void colocarMuro(int fila, int columna) {
    // Verificar si la posición es válida para colocar un muro
    if ((fila % 2 == 0 && columna % 2 != 0) || (fila % 2 != 0 && columna % 2 == 0)) {
        if (primerMuro == null) {
            primerMuro = new Point(fila, columna);
            celdas[fila][columna].setBackground(Color.BLACK);
            celdas[fila][columna].repaint();
        } else {
            // Verificar si la segunda celda está en línea con la primera
            if ((fila == primerMuro.x && Math.abs(columna - primerMuro.y) == 2) ||
                (columna == primerMuro.y && Math.abs(fila - primerMuro.x) == 2)) {
                
                // Colocar la segunda parte del muro
                celdas[fila][columna].setBackground(Color.BLACK);
                celdas[(fila + primerMuro.x) / 2][(columna + primerMuro.y) / 2].setBackground(Color.BLACK);
                celdas[fila][columna].repaint();
                celdas[(fila + primerMuro.x) / 2][(columna + primerMuro.y) / 2].repaint();

                // Decrementar el contador de muros restantes del jugador actual
                if (turnoJugador1) {
                    murosRestantesJugador1--;
                } else {
                    murosRestantesJugador2--;
                }

                // Actualizar el panel de información con los nuevos contadores
                actualizarmurosrestantes();

                // Restablecer variables y actualizar el turno
                modoColocarMuros = false;
                actualizarModoColocarMuros();
                turnoJugador1 = !turnoJugador1;
                actualizarTurno();
            } else {
                // Si la segunda celda no es válida, restablecer el primer muro
                celdas[primerMuro.x][primerMuro.y].setBackground(Color.GRAY);
                celdas[primerMuro.x][primerMuro.y].repaint();
                primerMuro = new Point(fila, columna);
                celdas[fila][columna].setBackground(Color.BLACK);
                celdas[fila][columna].repaint();
            }
        }
    }
}   

// Método para actualizar el panel de información con los contadores de muros restantes
    private void actualizarmurosrestantes() {
        murosDeJ1.setText("Muros restantes jugador 1: " + murosRestantesJugador1);
        murosDeJ2.setText("Muros restantes jugador 2: " + murosRestantesJugador2);
    }

    private void activarCeldas() {
        celdasActivadas = true;
        for (int i = 0; i < Tamaño; i++) {
            for (int j = 0; j < Tamaño; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    celdas[i][j].setEnabled(true);
                    celdas[i][j].setBackground(Color.YELLOW);
                }
            }
        }
    }

    private void desactivarCeldas() {
        celdasActivadas = false;
        for (int i = 0; i < Tamaño; i++) {
            for (int j = 0; j < Tamaño; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    celdas[i][j].setEnabled(false);
                    if (celdas[i][j].getBackground() != Color.BLACK) { // No cambiar el color de las celdas con muros
                        celdas[i][j].setBackground(Color.WHITE);
                    }
                }
            }
        }
    }
    

    // Verifica si el movimiento está bloqueado por un muro
    private boolean esMovimientoBloqueado(int filaActual, int columnaActual, int nuevaFila, int nuevaColumna) {
        int midFila = (filaActual + nuevaFila) / 2;
        int midColumna = (columnaActual + nuevaColumna) / 2;
        return celdas[midFila][midColumna].getBackground() == Color.BLACK;
    }

    // Método para actualizar las etiquetas de posición de los jugadores
    private void actualizarPosiciones() {
        posicionJugador1.setText("Posición: (" + jugador1Fila + ", " + jugador1Columna + ")");
        posicionJugador2.setText("Posición: (" + jugador2Fila + ", " + jugador2Columna + ")");
    }

    // Método para verificar si hay un ganador
    private void verificarGanador() {
        if (jugador1Fila == Tamaño - 1) {
            mostrarGanador(1, murosRestantesJugador1);
        } else if (jugador2Fila == 0) {
            mostrarGanador(2, murosRestantesJugador2);
        }
    }

    // Método para mostrar el ganador
    private void mostrarGanador(int numeroJugador, int murosRestantes) {
        String mensaje = "Jugador " + numeroJugador + " ha ganado! Muros restantes: " + murosRestantes;
        JLabel etiquetaGanador = new JLabel(mensaje);
        etiquetaGanador.setForeground(Color.BLACK);
        etiquetaGanador.setBackground(Color.YELLOW);
        etiquetaGanador.setOpaque(true);
        info.removeAll();
        info.add(etiquetaGanador);
        info.revalidate();
        info.repaint();

        iluminarCeldasGanador(numeroJugador);
    }

    // Método para iluminar las celdas por donde pasó el ganador
    private void iluminarCeldasGanador(int numeroJugador) {
        if (numeroJugador == 1) {
            // Iluminar el camino del Jugador 1
            for (int i = 0; i <= jugador1Fila; i += 2) {
                celdas[i][jugador1Columna].setBackground(Color.YELLOW);
                celdas[i][jugador1Columna].repaint();
            }
        } else if (numeroJugador == 2) {
            // Iluminar el camino del Jugador 2
            for (int i = Tamaño - 1; i >= jugador2Fila; i -= 2) {
                celdas[i][jugador2Columna].setBackground(Color.YELLOW);
                celdas[i][jugador2Columna].repaint();
            }
        }
    }
}
