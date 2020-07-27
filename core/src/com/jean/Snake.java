package com.jean;

public class Snake {
    public boolean isRight, isLeft, isUp, isDown;
    public SnakeBody snakeHead;
    public int unit, ms;
    public boolean isOver;

    public Snake(int x, int y, int unit) {
        isOver = false;
        snakeHead = new SnakeBody(x, y);
        this.unit = unit;
        isRight = true;
        isLeft = false;
        isUp = false;
        isDown = false;
    }

    public void moveSide(int side) {
        System.out.println("Side changing.");
        isRight = false;
        isLeft = false;
        isUp = false;
        isDown = false;
        switch (side) {
            case 0: //+x
                isRight = true;
                break;
            case 1://-x
                isLeft = true;
                break;
            case 2://+y
                isUp = true;
                break;
            case 3://-y
                isDown = true;
                break;
            default:
                System.out.println("Unknown side.");
                break;
        }
        System.out.println("Side changed.");
    }

    public void move(int ms) {
        this.ms = ms;
        if (isRight) {
            snakeHead.move(snakeHead.x + ms, snakeHead.y);
        } else if (isLeft) {
            snakeHead.move(snakeHead.x - ms, snakeHead.y);
        } else if (isUp) {
            snakeHead.move(snakeHead.x, snakeHead.y + ms);
        } else if (isDown) {
            snakeHead.move(snakeHead.x, snakeHead.y - ms);
        }
        if (snakeHead.x > 640 - 16 || snakeHead.x < 0 || snakeHead.y > 480 - 16 || snakeHead.y < 0) {
            System.out.println("Game finished!");
            snakeHead.x = 0;
            snakeHead.y = 0;
            isOver = true;
        }
    }

    public void canGrow(int[][] area) {
        int i = snakeHead.x / unit;
        int j = snakeHead.y / unit;
        if (area[i][j] == 1) {
            area[i][j] = 0;
            addBody();
        }
    }

    public void addBody() {
        int x, y;
        x = snakeHead.x;
        y = snakeHead.y;
        move(ms);
        SnakeBody newBody = new SnakeBody(snakeHead.x, snakeHead.y);
        snakeHead.x = x;
        snakeHead.y = y;
        newBody.addBody(snakeHead);
        snakeHead = newBody;
    }
}
