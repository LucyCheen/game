import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.Timer;

public class Main extends JPanel implements KeyListener {
    public static final int CELL_SIZE = 20;
    public static int width = 400;
    public static int height = 400;
    public static int row = height / CELL_SIZE; //高度
    public static int column = width / CELL_SIZE;  //寬度
    private Snake snake;
    private Fruit fruit;
    private Timer t;
    private int speed = 100;   //隔100毫秒
    private static String direction;
    private boolean allowKeyPress;

    public Main(){
        snake = new Snake();
        fruit = new Fruit();
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                repaint();  //重新繪製
            }
        }, 0, speed); // speed-->隔幾秒
        direction = "down";
        addKeyListener(this);
        allowKeyPress = true;
    }

    @Override  //視窗大小
    public void paintComponent(Graphics g){
        //System.out.println("Repaint component...");
        g.fillRect(0,0,width,height);    //視窗塗黑
        snake.drawSnake(g); //將蛇畫出來
        fruit.drawFruit(g); //將水果畫出來
        int snakeX = snake.getSnakeBody().get(0).x;  //取頭得位置
        int snakeY = snake.getSnakeBody().get(0).y;
        if(direction.equals("right")){
            snakeX += CELL_SIZE;
        }else if(direction.equals("left")){
            snakeX -= CELL_SIZE;
        }else if(direction.equals("up")){
            snakeY -= CELL_SIZE;
        }else if(direction.equals("down")){
            snakeY += CELL_SIZE;
        }
        Node newHead = new Node(snakeX, snakeY);
        if (snake.getSnakeBody().get(0).x == fruit.getX() && snake.getSnakeBody().get(0).y == fruit.getY()){
            //System.out.println("eat!!");
            fruit.setNewLocation(snake);
            fruit.drawFruit(g);
        }else {
            snake.getSnakeBody().remove(snake.getSnakeBody().size()-1); //移除蛇身體最後一個Node
        }
        snake.getSnakeBody().add(0, newHead); //在第一個位置加頭

        allowKeyPress = true;
        requestFocusInWindow();
    }
    @Override
    public Dimension getPreferredSize(){
        return new Dimension(width,height);
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Snake Eating");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new Main());
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setResizable(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //System.out.println(e.getKeyCode());
        if(allowKeyPress){
            if (e.getKeyCode() == 37 && !direction.equals(("right"))) {
                direction = "left";
            } else if (e.getKeyCode() == 38 && !direction.equals(("down"))) {
                direction = "up";
            } else if (e.getKeyCode() == 39 && !direction.equals(("left"))) {
                direction = "right";
            } else if (e.getKeyCode() == 40 && !direction.equals(("up"))) {
                direction = "down";
            }
            allowKeyPress = false;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}


