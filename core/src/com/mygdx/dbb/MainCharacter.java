package com.mygdx.dbb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class MainCharacter {
    private Sprite sprite;
    private Vector2 position;
    private float speed = 600;
    private float boundaryLeft;
    private float boundaryRight;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    public MainCharacter(Texture texture, float x, float y) {
        sprite = new Sprite(texture);
        position = new Vector2(x, y);
        boundaryLeft = 0;
        boundaryRight = Gdx.graphics.getWidth() - sprite.getWidth();
    }

    public void update(float delta) {
        // Handle input (left and right movement)
        if (movingLeft && position.x > boundaryLeft) {
            position.x -= speed * delta;
        } else if (movingRight && position.x < boundaryRight) {
            position.x += speed * delta;
        }

        // Update sprite position
        sprite.setPosition(position.x, position.y);
    }

    public void moveLeft() {
        movingLeft = true;
        movingRight = false;
        sprite.setFlip(true, false); // Flip the sprite horizontally when moving left
    }

    public void moveRight() {
        movingRight = true;
        movingLeft = false;
        sprite.setFlip(false, false); // Set sprite back to its original orientation when moving right
    }

    public void stopMoving() {
        movingLeft = false;
        movingRight = false;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSpeed(){
        return speed;
    }
}
