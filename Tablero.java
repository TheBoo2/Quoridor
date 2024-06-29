package Quoridor;
import javax.swing.*;
import java.awt.*;


public class Tablero extends JPanel{
public int TileSize = 90;
int colum = 17;
int fil = 17;

public Tablero(){
this.setPreferredSize(new Dimension(colum * TileSize, fil * TileSize));
this.setBackground(Color.gray);
}
        
}
