//貪吃蛇的食物

import java.awt.*;

public class Fruit {
    private int x;
    private int y;

    public Fruit(){   //水果座標
        this.x = (int) (Math.floor(Math.random() * Main.column) * Main.CELL_SIZE); //隨機產生位置
        this.y = (int) (Math.floor(Math.random() * Main.row) * Main.CELL_SIZE);
    }
    //取得水果座標
    public int getX(){ return this.x; }
    public int getY(){ return this.y; }

    public void drawFruit(Graphics g){
        g.setColor(Color.red);  //水果顏色
        g.fillOval(this.x, this.y, Main.CELL_SIZE, Main.CELL_SIZE); //水果位置
    }
}
