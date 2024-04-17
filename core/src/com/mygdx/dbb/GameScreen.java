package com.mygdx.dbb;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

class GameScreen implements Screen {
    private final Camera camera;
    private final SpriteBatch batch;
    private Texture background;
    private Texture background2;
    private MainCharacter mainCharacter;
    private Monster[] monsters;
    private Texture monsterTexture1;
    private Texture monsterTexture2;

    private List<Bubble> bubbles;
    private long lastClickTime;

    private long lastTapTime = 0; // For tracking time between taps
    private final long doubleTapThreshold = 400; // Milliseconds within which a second tap counts as a double tap

    GameScreen(){
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        background = new Texture("background.png");
        background2 = new Texture("background2.png");
        mainCharacter = new MainCharacter(new Texture("dino.png"), 200, 90);

        monsters = new Monster[4];
        monsterTexture1 = new Texture("monster1.png");
        monsterTexture2 = new Texture("monster2.png");

        // Spawn monsters at random positions
        for (int i = 0; i < monsters.length; i++) {
            float randomX = MathUtils.random(178, 1949);
            float randomY = getRandomYPosition();
            monsters[i] = new Monster(monsterTexture1, monsterTexture2, randomX, randomY);
        }

        bubbles = new ArrayList<>();

        // Add input processor to handle touch events
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                long currentTime = System.currentTimeMillis();
                if (screenX < Gdx.graphics.getWidth() / 2) {
                    // Left side of the screen touched, move left
                    if ( currentTime - lastTapTime < doubleTapThreshold ) {
                        // Double tap detected
                        mainCharacter.jump();
                    } else {
                        // Single tap detected
                        mainCharacter.moveLeft();
                    }
                   // lastTapTime = currentTime;
                } else {
                    // Right side of the screen touched, move right
                    mainCharacter.moveRight();
                }
                if (currentTime - lastTapTime < 300) {
                    Vector2 clickPosition = new Vector2(screenX, Gdx.graphics.getHeight() - screenY);
                    shootBubble(clickPosition);
                }
                lastTapTime = currentTime;
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                // Stop character movement when touch is released
                mainCharacter.stopMoving();
                return true;
            }
        });
    }

    private float getRandomYPosition() {
        // Define the possible Y positions
        int[] possibleYPositions = {100,280, 470, 650};

        // Select a random index from the array
        int randomIndex = MathUtils.random(0, possibleYPositions.length - 1);

        // Return the corresponding Y position
        return possibleYPositions[randomIndex];
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Update main character
        mainCharacter.update(delta);

        // Update monsters
        for (Monster monster : monsters) {
            monster.update(delta);
        }

        // Clear screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draw backgrounds, monsters, and main character
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(background2, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for (Monster monster : monsters) {
            batch.draw(monster.getSprite(), monster.getPosition().x, monster.getPosition().y);
        }
        batch.draw(mainCharacter.getSprite(), mainCharacter.getPosition().x, mainCharacter.getPosition().y);
        for (int i = bubbles.size() - 1; i >= 0; i--) {
            Bubble bubble = bubbles.get(i);
            bubble.update(delta);
            bubble.render(batch);
            // Check if the bubble is beyond the screen bounds
            if (bubble.getPosition().y > Gdx.graphics.getHeight() || bubble.getPosition().x < 0 || bubble.getPosition().x > Gdx.graphics.getWidth()) {
                bubble.setActive(false); // Set the bubble inactive
            }
            if (!bubble.isActive()) {
                bubbles.remove(i);
            }
        }
        batch.end();
    }

    private void shootBubble(Vector2 targetPosition) {
        Vector2 startPosition = mainCharacter.getPosition().cpy();
        Vector2 direction = targetPosition.cpy().sub(startPosition).nor();
        float speedMultiplier = 2.0f; // Adjust the speed multiplier as needed
        Bubble bubble = new Bubble(new Texture("bubble.png"), startPosition, direction.scl(200 * speedMultiplier));
        bubbles.add(bubble);
    }
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        background2.dispose();
    }
}
