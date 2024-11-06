package io.otilor.teek_tack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.InputAdapter;

public class TicTacToeGame implements Screen {
    private SpriteBatch batch;
    private BitmapFont font;
    private GlyphLayout layout;
    private ShapeRenderer shapeRenderer;
    private int[][] board;
    private boolean playerX;
    private int cellSize;
    private int gridSize;

    @Override
    public void show() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        layout = new GlyphLayout();
        shapeRenderer = new ShapeRenderer();
        board = new int[3][3];
        playerX = true;
        updateGridSize();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                int row = screenY / cellSize;
                int col = screenX / cellSize;
                if (row < 3 && col < 3 && board[row][col] == 0) {
                    board[row][col] = playerX ? 1 : 2;
                    playerX = !playerX;
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawGrid();
        shapeRenderer.end();

        batch.begin();
        drawBoard();
        batch.end();
    }

    private void drawGrid() {
        shapeRenderer.setColor(0, 0, 0, 1);
        for (int i = 1; i < 3; i++) {
            shapeRenderer.rectLine(i * cellSize, 0, i * cellSize, gridSize, 5);
            shapeRenderer.rectLine(0, i * cellSize, gridSize, i * cellSize, 5);
        }
    }

    private void drawBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col] != 0) {
                    String text = board[row][col] == 1 ? "X" : "O";
                    layout.setText(font, text);
                    float x = col * cellSize + (cellSize - layout.width) / 2;
                    float y = (row + 1) * cellSize - (cellSize - layout.height) / 2;
                    font.draw(batch, text, x, y);
                }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        updateGridSize();
    }

    private void updateGridSize() {
        gridSize = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cellSize = gridSize / 3;
    }

    @Override
    public void pause() {
        // Handle pause if necessary
    }

    @Override
    public void resume() {
        // Handle resume if necessary
    }

    @Override
    public void hide() {
        // Handle hide if necessary
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }
}
