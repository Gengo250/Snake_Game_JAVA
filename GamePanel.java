package Snake_Game_JAVA;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener {

  static final int SREEN_WIDTH = 600;
  static final int SREEN_HEIGHT = 600;
  static final int UNIT_SIZE = 25;
  static final int GAME_UNITS = (SREEN_WIDTH * SREEN_HEIGHT)/UNIT_SIZE; 
  static final int DELAY = 75;
  final int x[] = new int[GAME_UNITS];
  final int y[] = new int[GAME_UNITS];
  int bodyParts = 6;
  int applesEaten;
  int appleX;
  int appleY;
  char direction = 'R';
  boolean running = false;
  Timer timer;
  Random random;

  GamePanel(){
    random = new Random();
    this.setPreferredSize(new Dimension(SREEN_WIDTH, SREEN_HEIGHT));
    this.setBackground(Color.BLACK);
    this.setFocusable(true);
    this.addKeyListener(new MyKeyAdapter());
    startGame();
  }
  public void startGame(){
    newApple();
    running = true;
    timer = new Timer(DELAY,this);
    timer.start();
  }
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    draw(g);

  }
  public void draw(Graphics g){
    if(running){
      
   /*    for(int i = 0; i < SREEN_HEIGHT/UNIT_SIZE;i++){
        g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SREEN_HEIGHT);
        g.drawLine(0, i*UNIT_SIZE, SREEN_WIDTH, i*UNIT_SIZE);
      }
       */
      g.setColor(Color.red);
      g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

      for(int i = 0; i < bodyParts; i++){
        if(i == 0){
          g.setColor(Color.green);
          g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
        else{
          g.setColor(new Color(45,180,0));
          g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
      }
      g.setColor(Color.red);
      g.setFont(new Font("Ink Free", Font.BOLD,40));
      FontMetrics metrics = getFontMetrics(g.getFont());
      g.drawString("Score: " +applesEaten, (SREEN_WIDTH - metrics.stringWidth("Score: " +applesEaten))/2, g.getFont().getSize());
    }
    else {
      gameOver(g);
    }
  }
  public void newApple(){
    appleX = random.nextInt((int)(SREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
    appleY = random.nextInt((int)(SREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;


  }
  public void move(){
    for(int i = bodyParts; i > 0; i--){
      x[i] = x[i - 1];
      y[i] = y[i - 1];

    }
    switch(direction){
      case 'U':
      y[0] = y[0] - UNIT_SIZE;
      break;
      case 'D':
      y[0] = y[0] + UNIT_SIZE;
      break;
      case 'L':
      x[0] = x[0] - UNIT_SIZE;
      break;
      case 'R':
      x[0] = x[0] + UNIT_SIZE;
      break;

    }

  }
  public void checkApple(){
    if((x[0] == appleX) && (y[0] == appleY)){
      bodyParts++;
      applesEaten++;
      newApple();
    }
  }
  public void checkCollisions(){
    for(int i = bodyParts; i > 0; i--){
      if((x[0] == x[i]) && (y[0] == y[i])){
        running = false;
      }
    }
    if(x[0] < 0){
      running = false;
    }
    if(x[0] > SREEN_WIDTH){
      running = false;
    }
    if(y[0] <0){
      running = false;
    }
    if(y[0] > SREEN_HEIGHT){
      running = false;
    }

    if(!running){
      timer.stop();
    }
  }
  public void gameOver(Graphics g){

    g.setColor(Color.red);
    g.setFont(new Font("Ink Free", Font.BOLD,40));
    FontMetrics metrics1 = getFontMetrics(g.getFont());
    g.drawString("Score: " +applesEaten, (SREEN_WIDTH - metrics1.stringWidth("Score: " +applesEaten))/2, g.getFont().getSize());

    g.setColor(Color.red);
    g.setFont(new Font("Ink Free", Font.BOLD,75));
    FontMetrics metrics2 = getFontMetrics(g.getFont());
    g.drawString("GAME OVER", (SREEN_WIDTH - metrics2.stringWidth("GAME OVER"))/2, SREEN_HEIGHT/2);

  }
  @Override
  public void actionPerformed(ActionEvent e){

    if(running){
      move();
      checkApple();
      checkCollisions();
    }
    repaint();

  }
  public class MyKeyAdapter extends KeyAdapter{
    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
          case KeyEvent.VK_LEFT:
            if(direction != 'R'){
              direction = 'L';
            }
            break;
            case KeyEvent.VK_RIGHT:
            if(direction != 'L'){
              direction = 'R';
            }
            break;
            case KeyEvent.VK_UP:
            if(direction != 'D'){
              direction = 'U';
            }
            break;
            case KeyEvent.VK_DOWN:
            if(direction != 'U'){
              direction = 'D';
            }
            break;
        }
    }
  }
}
