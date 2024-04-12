package com.mygdx.dbb;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.Random;

public class Monster {
    private Sprite sprite;
    private Vector2 position;
    private float speed = 200; // Adjust as needed
    private float boundaryLeft;
    private float boundaryRight;
    private boolean movingLeft = true;
    private boolean movingRight = false;

    // Animation variables
    private Animation<TextureRegion> animation;
    private float stateTime = 0f;

    public Monster(Texture texture1, Texture texture2, float x, float y) {
        // Create animation from the two textures
        TextureRegion[] frames = new TextureRegion[2];
        frames[0] = new TextureRegion(texture1);
        frames[1] = new TextureRegion(texture2);
        animation = new Animation<>(0.5f, frames);

        sprite = new Sprite(texture1);
        position = new Vector2(x, y);
        boundaryLeft = Gdx.graphics.getWidth() * 0.08f; // 8% of the screen width
        boundaryRight = Gdx.graphics.getWidth() * 0.92f - sprite.getWidth(); // 92% of the screen width minus sprite width
    }

    public void update(float delta) {
        stateTime += delta; // Update the state time for animation

        // Move the monster horizontally
        if (movingLeft && position.x > boundaryLeft) {
            position.x -= speed * delta;
            if (position.x <= boundaryLeft) {
                movingLeft = false;
                movingRight = true;
            }
        } else if (movingRight && position.x < boundaryRight) {
            position.x += speed * delta;
            if (position.x >= boundaryRight) {
                movingLeft = true;
                movingRight = false;
            }
        }
        // Update sprite position
        sprite.setPosition(position.x, position.y);
        // Update sprite based on animation
        sprite.setRegion(animation.getKeyFrame(stateTime, true));
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Vector2 getPosition() {
        return position;
    }
}
