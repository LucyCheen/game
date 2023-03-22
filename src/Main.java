import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
    private Bomb bomb;
    private Grape grape;
    private BlackBomb blackBomb;
    private Ghost ghost;
    private Lemon lemon;
    private Timer t;
    private int speed = 100;   //隔100毫秒
    private static String direction;
    private boolean allowKeyPress;
    private int score;
    private int highest_score;
    String  myFile = "HS.txt";

    public Main(){
        readHighestScore();
        reset();
        addKeyListener(this);
    }

    private void setTimer(){
        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                repaint();  //重新繪製
            }
        }, 0, speed); // speed-->隔幾秒

    }

    //重置遊戲(遊戲初始化
    private void reset(){
        score = 0;
        if(snake != null){
            snake.getSnakeBody().clear();
        }
        allowKeyPress = true;
        direction = "down";
        snake = new Snake();
        fruit = new Fruit();
        bomb = new Bomb();
        grape = new Grape();
        blackBomb = new BlackBomb();
        ghost = new Ghost();
        lemon = new Lemon();
        setTimer();
    }
    @Override  //視窗大小
    public void paintComponent(Graphics g){
        //System.out.println("Repaint component...");
        ArrayList<Node> snake_body = snake.getSnakeBody();
        Node head = snake_body.get(0);
        for(int i = 1; i < snake_body.size(); i++){
            if(snake_body.get(i).x == head.x && snake_body.get(i).y == head.y  //當蛇碰到自己的身體
                    ||snake.getSnakeBody().get(0).x == ghost.getX() && snake.getSnakeBody().get(0).y == ghost.getY() //當蛇碰到鬼

                    //碰到邊界
                    && head.x >= getWidth()  / width + CELL_SIZE
                    || head.x <  getWidth()  / width - CELL_SIZE   //左邊
                    && head.y >= getHeight() / height + CELL_SIZE
                    || head.y <  getHeight() / height - CELL_SIZE){  //上面
                allowKeyPress = false;
                t.cancel();
                t.purge();
                int response = JOptionPane.showOptionDialog(this,"Game Over... Your score is " + score + ". The highest was " + highest_score + ". Continue?", "Game Over",
                        JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null,JOptionPane.YES_NO_OPTION);
                writeNewScore(score);
                switch (response){
                    case JOptionPane.CLOSED_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.NO_OPTION:
                        System.exit(0);
                        break;
                    case JOptionPane.YES_OPTION:
                        reset();
                        return;
                }
            }
        }

        //改變視窗背景
        ImageIcon image = new ImageIcon("sky.png");
        //ImageIcon image = new ImageIcon(getClass().getResource(".idea/sky.png"));
        super.paintComponent(g);
        image.paintIcon(this,g,0,0);

        //g.fillRect(0,0,width,height);    //視窗塗黑
        snake.drawSnake(g); //將蛇畫出來
        fruit.drawFruit(g); //將水果畫出來
        bomb.drawBomb(g);   //將炸彈畫出來
        blackBomb.drawBlackBomb(g); //將黑色炸彈畫出來
        grape.drawGrape(g); //將葡萄畫出來
        ghost.drawGhost(g); //將鬼畫出來
        lemon.drawLemon(g); //將檸檬畫出來


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
        //當蛇吃到水果
        if (snake.getSnakeBody().get(0).x == fruit.getX() && snake.getSnakeBody().get(0).y == fruit.getY()) {
            //System.out.println("eat!!");
            fruit.setNewLocation(snake);
            fruit.drawFruit(g);
            score++; //吃到水果加一分

        //當蛇吃到葡萄
        }else if(snake.getSnakeBody().get(0).x == grape.getX() && snake.getSnakeBody().get(0).y == grape.getY()){
            grape.setNewLocation(snake);
            grape.drawGrape(g);
            score += 2; //吃到葡萄加兩分
            snake_body.add(snake_body.size()-1,new Node(0,0));

        //當蛇吃到檸檬
        }else if (snake.getSnakeBody().get(0).x == lemon.getX() && snake.getSnakeBody().get(0).y == lemon.getY()){
            lemon.setNewLocation(snake);
            lemon.drawLemon(g);
            score++; //吃到檸檬加一分

        }else {
            //當蛇吃到炸彈
            if(snake.getSnakeBody().get(0).x == bomb.getX() && snake.getSnakeBody().get(0).y == bomb.getY()){
                bomb.setNewLocation(snake);
                bomb.drawBomb(g);
                score--; //吃到炸彈減一分
                snake.getSnakeBody().remove(snake.getSnakeBody().size()-1); //移除蛇身體最後一個Node(刪尾巴
            }

            //當蛇吃到黑色炸彈
            if (snake.getSnakeBody().get(0).x == blackBomb.getX() && snake.getSnakeBody().get(0).y == blackBomb.getY()){
                blackBomb.setNewLocation(snake);
                blackBomb.drawBlackBomb(g);
                score -= 2; //吃到黑色炸彈減兩分
                snake.getSnakeBody().remove(snake.getSnakeBody().size()-1); //移除蛇身體最後一個Node(刪尾巴
                snake.getSnakeBody().remove(snake.getSnakeBody().size()-1); //移除蛇身體最後一個Node(刪尾巴

            }

            snake.getSnakeBody().remove(snake.getSnakeBody().size()-1); //移除蛇身體最後一個Node(刪尾巴
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
            if (e.getKeyCode() == 37 && !direction.equals(("right"))) {   //當不是往(右)的時候，可以往(左)走
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

    public void readHighestScore(){
        try{
            File myObj = new File(myFile);
            Scanner myReader = new Scanner(myObj);
            highest_score = myReader.nextInt();
            myReader.close();
        }catch (FileNotFoundException e){
            highest_score = 0;
            try {
                File myObj = new File(myFile);
                if(myObj.createNewFile()){
                    System.out.println("File created: " + myObj.getName());
                }
                FileWriter myWriter = new FileWriter(myObj.getName());
                myWriter.write(""+ 0);
                myWriter.close();
            }catch (IOException err){
                System.out.println("An error occurred!");
                err.printStackTrace();
            }
        }
    }

    public void writeNewScore(int score){
        try {
            if(score > highest_score){
                FileWriter myWrite = new FileWriter(myFile);
                System.out.println("Record Highest Score.");
                myWrite.write("" + score);
                highest_score = score;
                myWrite.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}


