//import org.w3c.dom.Node;

import java.awt.*;
import java.util.ArrayList;

public class Snake {
    private ArrayList<Node> snakeBody;

    public Snake(){  //蛇的身體
        snakeBody = new ArrayList<>();
        snakeBody.add(new Node(80,0));
        snakeBody.add(new Node(60,0));
        snakeBody.add(new Node(40,0));
        snakeBody.add(new Node(20,0));
    }

    public ArrayList<Node> getSnakeBody(){
        return snakeBody;
    }
    public void drawSnake(Graphics g){
        g.setColor(Color.GREEN);  //蛇的顏色
        for(int i = 0; i < snakeBody.size(); i++){
            if(i==0){ //頭顏色
                g.setColor(Color.orange);
            }
            else {    //身體顏色
                g.setColor(Color.green);
            }
            Node n = snakeBody.get(i);
            if (n.x >= Main.width){
                n.x = 0;
            }
            if (n.y >= Main.height){
                n.y = 0;
            }
            if (n.x < 0){
                n.x = Main.width-Main.CELL_SIZE;
            }
            if (n.y < 0){
                n.y = Main.height-Main.CELL_SIZE;
            }
            g.fillOval(n.x, n.y, Main.CELL_SIZE, Main.CELL_SIZE);

        }
    }
}
