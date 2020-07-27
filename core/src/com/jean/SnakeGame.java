package com.jean;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SnakeGame extends ApplicationAdapter implements InputProcessor {

    public ShapeRenderer shapeRenderer;
    public int[][] area;
    public final int unit = 16;
    public Snake snake;
    public int curFPS = 0;
    public int curFT = 0;
    public final int maxFPS = 8;
    public final int maxFT = 256;
    public ArrayList<SnakeBody> snakes;
    public ArrayList<SnakeFood> foods;
    public int next;

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
        area = new int[40][30];
        snake = new Snake(0, 0, unit);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render() {
        int count = 0;
        next = 0;
        snakes = new ArrayList<SnakeBody>();
        if (curFPS == maxFPS) {
            snake.move(unit);
            curFPS = 0;
        } else {
            curFPS++;
        }
        if (curFT == maxFT) {
            createApple();
            curFT = 0;
        } else {
            curFT++;
        }
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //shapeRenderer.setColor(0 , 1, 0, (float) 0.75);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Color clr = new Color();
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 30; j++) {
                shapeRenderer.setColor(Color.OLIVE);
                if (area[i][j] == 0) {
                    shapeRenderer.rect(unit * i, unit * j, unit - 2, unit - 2);
                } else if (area[i][j] == 1) {
                    shapeRenderer.setColor(Color.GREEN);
                    SnakeFood food = new SnakeFood(unit * i, unit * j, unit);
                    shapeRenderer.rect(unit * i, unit * j, unit - 2, unit - 2);
                }
            }
        }
        //shapeRenderer.setColor(0, 1, 0, (float) 0.75);
        if (snake.isOver) {
            shapeRenderer.setColor(Color.RED);
        } else {
            shapeRenderer.setColor(Color.BLUE);
        }
        SnakeBody snakeBody = snake.snakeHead;
        while (snakeBody != null) {
            if (count > 0) {
                snakes.add(snakeBody);
            }
            shapeRenderer.rect(snakeBody.x, snakeBody.y, unit - 2, unit - 2);
            snakeBody = snakeBody.nextBody;
            count++;
        }
        Iterator it = snakes.iterator();
        while (it.hasNext()) {
            SnakeBody curSnake = (SnakeBody) it.next();
            if (curSnake.x == snake.snakeHead.x && curSnake.y == snake.snakeHead.y) {
                snake.isOver = true;
                next = 1;
            }
        }
        snake.canGrow(area);
        shapeRenderer.end();
        if (snake.isOver && next == 0) {
            JOptionPane.showMessageDialog(null, "Game over!");
            resetGame();
        }
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    public void resetGame() {
        snake.isOver = false;
        System.out.println("Resetting Game!");
        snake.isRight = true;
        snake.isLeft = false;
        snake.isOver = false;
        snake.isDown = false;
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 30; j++) {
                area[i][j] = 0;
            }
        }
        snake.snakeHead.nextBody = null;
        snake.snakeHead.x = 0;
        snake.snakeHead.y = 0;
    }

    public void createApple() {
        Iterator it = snakes.iterator();
        int i, j, control;
        control = 0;
        Random rnd = new Random();
        i = rnd.nextInt(40);
        j = rnd.nextInt(30);
        while (it.hasNext()) {
            SnakeBody snake = (SnakeBody) it.next();
            if (snake.x == i && snake.y == j) {
                control = 1;
            }
        }
        if (area[i][j] == 0 && control == 0) {
            area[i][j] = 1;
            System.out.println("Area creating.");
            System.out.println("Apple created!");
        } else {
            createApple();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.RIGHT) {
            snake.moveSide(0);
            return true;
        } else if (keycode == Input.Keys.LEFT) {
            snake.moveSide(1);
            return true;
        } else if (keycode == Input.Keys.UP) {
            snake.moveSide(2);
            return true;
        } else if (keycode == Input.Keys.DOWN) {
            snake.moveSide(3);
            return true;
        } else if (keycode == Input.Keys.SPACE) {
            snake.addBody();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
