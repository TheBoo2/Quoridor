package Quoridor;

public class Juego {
    public static void main(String[] args) {
JFrame cuadro = new JFrame();
cuadro.setLayout(new GridBagLayout());
cuadro.setMinimumSize(new Dimension(500, 500);
cuadro.setLocationRelativeTo(null);

Tablero tablero = new Tablero();
cuadro.add(tablero);
        
cuadro.setVisible(true);

    }
}
