package com.boxbird.boxbird;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by riasat.ullah on 12/5/2015.
 */
import java.util.Random;

public class Glove extends GameObject{
    private int score;
    private int speed;
    private Random rand = new Random();
    private Animation animation = new Animation();
    private Bitmap spritesheet;
    private boolean ground;
    private boolean shoot = false;
    private int stretch = 0;
    private int spring_speed = 40;
    private int direction = 1;
    private int spring_length;

    public Glove(Bitmap res, int x, int w, int h, int s, int numFrames, boolean ground)
    {
        super.x = x;
        super.y = GamePanel.HEIGHT - h;
        if (ground == false){
            super.y = h;
        }

        this.ground = ground;
        spring_length = (int) (GamePanel.HEIGHT/3 - h);
        width = w;
        height = h;
        score = s;

        speed = GamePanel.MOVESPEED;

        Bitmap[] image = new Bitmap[numFrames];

        spritesheet = res;

        for(int i = 0; i<image.length;i++)
        {
            image[i] = Bitmap.createBitmap(spritesheet, 0, i*height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(100-speed);
    }

    public void update()
    {
        shoot_update();
        x += speed;
        animation.update();
    }

    private void shoot_update(){
        if (shoot == false){
            if (ground){
                y = GamePanel.HEIGHT - height;
            }else{
                y = height;
            }
            int start = new Random().nextInt(20);
            if (start % 3 == 0){
                shoot = true;
            }
        }
        if (shoot){
            if (ground){
                y -= direction * spring_speed;
            }else{
                y += direction * spring_speed;
            }
            stretch += spring_speed;
        }
        if (stretch > spring_length){
            direction = -1 * direction;
        }
        if (stretch < 0){
            stretch = 0;
            direction = 1;
            shoot = false;
        }
    }

    public void draw(Canvas canvas)
    {
        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }catch(Exception e){}
    }

    @Override
    public int getWidth()
    {
        //offset slightly for more realistic collision detection
        return width-10;
    }

}