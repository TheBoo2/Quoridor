package Quoridor;
import javax.swing.*;
import java.awt.*;

public class Muros {
    private JButton[][] celdas;
    private Point primerMuro;
    private boolean turnoJugador1;
    private int Tamaño;
    private JFrame frame;

    public Muros(JButton[][] celdas, Point primerMuro, boolean turnoJugador1, int Tamaño, JFrame frame) {
        this.celdas = celdas;
        this.primerMuro = primerMuro;
        this.turnoJugador1 = turnoJugador1;
        this.Tamaño = Tamaño;
        this.frame = frame;
    }

    public void colocarMuro(int fila, int columna, int murosRestantesJugador1, int murosRestantesJugador2) {
        if (fila % 2 == 0 || columna % 2 == 0) {
            return; // Las paredes solo se pueden colocar en celdas impares
        }

        if (primerMuro == null) {
            primerMuro = new Point(fila, columna);
            celdas[fila][columna].setBackground(Color.BLACK);
            celdas[fila][columna].repaint(); // Añadir repaint() después de cambiar el color
        } else {
            if ((fila == primerMuro.x && Math.abs(columna - primerMuro.y) == 2) ||
                    (columna == primerMuro.y && Math.abs(fila - primerMuro.x) == 2)) {
                // Colocar el muro en las dos celdas pares correspondientes a las celdas impares seleccionadas
                if (fila == primerMuro.x) { // Mismo fila, muros en columnas
                    int col1 = Math.min(primerMuro.y, columna);
                    int col2 = Math.max(primerMuro.y, columna);
                    celdas[fila][col1].setBackground(Color.BLACK);
                    celdas[fila][col1].repaint();
                    celdas[fila][col2].setBackground(Color.BLACK);
                    celdas[fila][col2].repaint();
                } else { // Mismo columna, muros en filas
                    int fil1 = Math.min(primerMuro.x, fila);
                    int fil2 = Math.max(primerMuro.x, fila);
                    celdas[fil1][columna].setBackground(Color.BLACK);
                    celdas[fil1][columna].repaint();
                    celdas[fil2][columna].setBackground(Color.BLACK);
                    celdas[fil2][columna].repaint();
                }
                celdas[fila][columna].setBackground(Color.BLACK);
                celdas[fila][columna].repaint(); // Añadir repaint() después de cambiar el color

                // Decrementar el contador de muros restantes del jugador actual
                if (turnoJugador1) {
                    murosRestantesJugador1--;
                } else {
                    murosRestantesJugador2--;
                }
                // Actualizar el panel de información con los nuevos contadores
                actualizarInfoPanel(murosRestantesJugador1, murosRestantesJugador2);

                modoColocarMuros = false;
                actualizarModoColocarMuros();
                turnoJugador1 = !turnoJugador1; // Cambiar el turno después de colocar el muro
                actualizarTurno(); // Actualizar la visualización del turno
            } else {
                celdas[primerMuro.x][primerMuro.y].setBackground(Color.GRAY); // Restaurar el color si no se coloca un muro válido
                celdas[primerMuro.x][primerMuro.y].repaint(); // Añadir repaint() después de cambiar el color
                primerMuro = new Point(fila, columna);
                celdas[fila][columna].setBackground(Color.BLACK);
                celdas[fila][columna].repaint(); // Añadir repaint() después de cambiar el color
            }
        }
    }

    private void actualizarInfoPanel(int murosRestantesJugador1, int murosRestantesJugador2) {
        JLabel labelMurosJugador1 = new JLabel("Muros restantes jugador 1: " + murosRestantesJugador1);
        JLabel labelMurosJugador2 = new JLabel("Muros restantes jugador 2: " + murosRestantesJugador2);

        // Actualizar panel de información
        frame.remove(upinfo);
        upinfo.add(labelMurosJugador1);
        frame.add(upinfo, BorderLayout.NORTH);
        frame.revalidate();
        frame.repaint();

        frame.remove(downinfo);
        downinfo.add(labelMurosJugador2);
        frame.add(downinfo, BorderLayout.SOUTH);
        frame.revalidate();
        frame.repaint();
    }
}
