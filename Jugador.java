package Quoridor;

public class Jugador {
        private String name;
    private int wallsLeft;
    private int row; 
    private int col; 

    public Jugador(String name, int initialWalls) {
        this.name = name;
        this.wallsLeft = initialWalls;
        
        this.row = 0;
        this.col = 4 ;   }

    public String getName() {
        return name;
    }

    public int getWallsLeft() {
        return wallsLeft;
    }

    public void decrementWallsLeft() {
        wallsLeft--;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
