package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePlay extends JPanel implements KeyListener, ActionListener {
    private boolean play=false;
    //start score
    private int score=0;
    private int totalBricks=20;
    //time and speed ball
    private Timer timer;
    private int delay=0;
    private int playerX=310;
    private int ballposX=120;
    private int ballposY=350;
    private int ballXdr=-1;
    private int ballYdr=-2;
    private MapGaenerator map;
    public GamePlay(){
        map=new MapGaenerator(3,7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
        timer.start();
    }


    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,692,592);
        //draw map
        map.draw((Graphics2D)g);
        //borders
        g.setColor(Color.yellow);
        g.fillRect(0,0,3,592);
        g.fillRect(0,0,692,3);
        g.fillRect(691,0,3,592);
        //score
        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD,25));

        //the paddle
        g.setColor(Color.green);
        g.fillRect(playerX,550,175,8);
        //the ball
        g.setColor(Color.yellow);

        g.fillOval(ballposX,ballposY,20,20);
        if(totalBricks<=0){
            if(ballposY>570){
                play=false;
                ballXdr=0;
                ballposY=0;
                g.setColor(Color.RED);
                g.setFont(new Font("serif",Font.BOLD,30));
                g.drawString("You Win ",260,300);

                g.setFont(new Font("serif",Font.BOLD,20));
                g.drawString("press Enter to Restart",230,350);

            }
        }
        if(ballposY>570){
            play=false;
            ballXdr=0;
            ballposY=0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif",Font.BOLD,30));
            g.drawString("Game over,score",190,300);

            g.setFont(new Font("serif",Font.BOLD,20));
            g.drawString("press Enter to Restart",230,350);

        }

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(play){
        if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))){
                ballYdr=-ballYdr;
            }
        A:for (int i=0;i<map.map.length;i++){
            for (int j=0;j<map.map[0].length;j++){
                if(map.map[i][j]>0){
                    int brickX=j* map.brickWidth+80;
                    int brickY=i*map.brickHeight+50;
                    int brickWidth=map.brickWidth;
                    int brickHeight=map.brickHeight;
                    Rectangle rect=new Rectangle(brickX,brickY,brickWidth,brickHeight);
                    Rectangle ballRect=new Rectangle(ballposX,ballposY,20,20);
                    Rectangle brickRect=rect;
                    if(ballRect.intersects(brickRect)){
                        map.setBrickValue(0,i,j);
                        totalBricks--;
                        score+=5;
                        if(ballposX +19<=brickRect.x
                                ||ballposX+1>=brickRect.x+brickRect.width){
                            ballXdr=-ballXdr;
                        }else {
                            ballYdr=-ballYdr;
                        }
                        break A;
                    }
                }
            }
        }
            ballposX+=ballXdr;
            ballposY+=ballYdr;
            if(ballposX<0){
                ballXdr=-ballXdr;
            }
            if(ballposY<0){
                ballYdr= -ballYdr;
            }
            if(ballposX>670){
                ballXdr=-ballXdr;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }


    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;

            } else {
                moveLift();
            }
        }
        if(!play){
            play=true;
            ballposX=120;
            ballposY=350;
            ballXdr=-1;
            ballYdr=-2;
            playerX=310;
            score=0;
            totalBricks=21;
            map=new MapGaenerator(3,7);
            repaint();
        }

    }
        public void moveRight() {
            play=true;
            playerX+=20;
        }
        public void moveLift() {
            play=true;
            playerX-=20;
        }
    }


