import javax.swing.*;
import java.awt.*;

public class Ghost {
    private int x;
    private int y;
    private ImageIcon img;

    public Ghost(){
        img = new ImageIcon("ghost.png");
        //img = new ImageIcon(getClass().getResource(".idea/ghost.png"));
        this.x = (int) (Math.floor(Math.random() * Main.column) * Main.CELL_SIZE); //隨機產生位置
        this.y = (int) (Math.floor(Math.random() * Main.row) * Main.CELL_SIZE);
    }

    public int getX(){ return this.x; }

    public int getY(){ return this.y; }

    public void drawGhost(Graphics g){
        //g.setColor(Color.red);  //水果顏色
        //g.fillOval(this.x, this.y, Main.CELL_SIZE, Main.CELL_SIZE); //水果位置
        img.paintIcon(null, g, this.x, this.y);
    }
}
