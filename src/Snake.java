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
        for(Node n: snakeBody){
            g.fillOval(n.x, n.y, Main.CELL_SIZE, Main.CELL_SIZE);

        }
    }
}
