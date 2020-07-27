package com.jean;

public class SnakeBody {
    public SnakeBody nextBody;
    public int x, y;
    public SnakeBody(int x, int y) {
        this.x = x;
        this.y = y;
        this.nextBody = null;
    }
    public void addBody (SnakeBody nextBody) {
        this.nextBody = nextBody;
    }
    public void move(int nextX, int nextY) {
        if (nextBody != null) {
            nextBody.move(x, y);
        }
        x = nextX;
        y = nextY;
    }
}
