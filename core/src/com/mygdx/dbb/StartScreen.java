package com.mygdx.dbb;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class StartScreen implements Screen {
    private SpriteBatch batch;
    private Texture startButton;
    private Rectangle startButtonBounds;
    private OrthographicCamera camera;
    private BitmapFont titleFont;

    public StartScreen() {
        batch = new SpriteBatch();
        // Remove startBackground texture

        // Set the background color to black
        Gdx.gl.glClearColor(0, 0, 0, 1);

        startButton = new Texture("start_button.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        float buttonX = (Gdx.graphics.getWidth() - startButton.getWidth()) / 2;
        float buttonY = (Gdx.graphics.getHeight() - startButton.getHeight()) / 2;
        startButtonBounds = new Rectangle(buttonX, buttonY, startButton.getWidth(), startButton.getHeight());
        titleFont = new BitmapFont();
        titleFont.setColor(1, 1, 1, 1);
        titleFont.getData().setScale(10);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Clear the screen with the specified color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        // Draw the start button
        batch.draw(startButton, startButtonBounds.x, startButtonBounds.y);

        // Draw the game title
        GlyphLayout layout = new GlyphLayout();
        String title = "Dino Bubble Blast";
        layout.setText(titleFont, title);
        float textWidth = layout.width;
        titleFont.draw(batch, layout, (Gdx.graphics.getWidth() - textWidth) / 2, Gdx.graphics.getHeight() - 50);

        batch.end();

        // Check if the start button is touched
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            if (startButtonBounds.contains(touchPos.x, touchPos.y)) {
                // Switch to the game screen when the button is clicked
                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        startButton.dispose();
        titleFont.dispose();
    }
}
