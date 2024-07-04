package Quoridor;
import javax.swing.*;

public class Jugador {
    private JPanel panelJugador;
    private Color color;
    private int fila;
    private int columna;

    public Jugador(Color color) {
        this.color = color;
        this.panelJugador = new JPanel();
        this.panelJugador.setBackground(color);
        this.panelJugador.setPreferredSize(new Dimension(Tamaño_Celda, Tamaño_Celda));
    }

    public JPanel getPanelJugador() {
        return panelJugador;
    }

    public void setPosicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    public int getFila() {
        return fila;
    }

    public int getColumna() {
        return columna;
    }
}
