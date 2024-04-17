package com.mygdx.dbb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bubble {
    private Vector2 position;
    private Vector2 velocity;
    private Sprite sprite;
    private boolean active;

    public Bubble(Texture texture, Vector2 position, Vector2 velocity) {
        this.position = position;
        this.velocity = velocity;
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(position.x, position.y);
        this.active = true;
    }

    public void update(float delta) {
        if (active) {
            position.add(velocity.x * delta, velocity.y * delta);
            sprite.setPosition(position.x, position.y);
        }
    }

    public void render(SpriteBatch batch) {
        if (active) {
            sprite.draw(batch);
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
