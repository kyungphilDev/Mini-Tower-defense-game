package edu.postech.csed332.homework1;

import java.util.*;

/**
 * An air monster that moves towards to the goal position of the board, while
 * satisfying the game board invariants. The class AirMob implements the interface
 * Monster. TODO: implement all unimplemented methods.
 * Feel free to modify this file, e.g., adding new fields or methods. If needed,
 * you can define a new (abstract) super class that this class inherits.
 *
 * @see GameBoard#isValid
 */


public class AirMob implements Monster {
    class Pair {
        Position x;
        int y;

        Pair(Position x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private final GameBoard board;

    public AirMob(GameBoard board) {
        // TODO: implement this
        this.board = board;
    }

    @Override
    public Position move() {
        // TODO: implement this
        int[] dx = {1, 0, 0, -1};
        int[] dy = {0, 1, -1, 0};
        int boardWidth = this.board.getWidth();
        int boardHeight = this.board.getHeight();
        int goalHeight = boardHeight / 2;
        Position pos = this.board.getPosition(this);
        int x = pos.getX();
        int y = pos.getY();
        boolean airDangerList[][] = this.board.airDangerList;
        ArrayList<Monster> tmpBoardList[][] = this.board.tmpBoardList;
        // bfs
        int nextDir = 0;
        boolean isDone = false;
        int[][] d = new int[boardWidth][boardHeight];
        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                d[i][j] = -1;
            }
        }
        ArrayDeque<Pair> deque = new ArrayDeque<Pair>();
        deque.add(new Pair(new Position(x, y), -1));
        d[x][y] = 0;
        Loop1:
        while (!deque.isEmpty()) {
            Pair res = deque.poll();
            int tx = res.x.getX();
            int ty = res.x.getY();
            for (int k = 0; k < 4; k++) {
                int nx = tx + dx[k];
                int ny = ty + dy[k];
                if (nx < 0 || nx >= boardWidth || ny < 0 || ny >= boardHeight) continue;
                if (d[nx][ny] != -1 || airDangerList[nx][ny]) continue;
                d[nx][ny] = 1;
                if (res.y == -1) {
                    deque.add(new Pair(new Position(nx, ny), k));
                } else {
                    deque.add(new Pair(new Position(nx, ny), res.y));
                }
                if (nx == boardWidth - 1 && ny == goalHeight) {
                    isDone = true;
                    if (res.y == -1) {
                        nextDir = k;
                    } else {
                        nextDir = res.y;
                    }
                    break Loop1;
                }
            }
        }
        Position npos = new Position(x, y);
        if (isDone) {
            int nx = x + dx[nextDir];
            int ny = y + dy[nextDir];
            if (tmpBoardList[nx][ny].isEmpty()) {
                npos = new Position(nx, ny);
            } else {
                boolean isOk = true;
                for (Monster m : tmpBoardList[nx][ny]) {
                    if (!m.isGround()) {
                        isOk = false;
                        break;
                    }
                }
                if (isOk) {
                    npos = new Position(nx, ny);
                }
            }
        } else {
            int nx = x;
            int ny = y;
            for (int k = 0; k < 4; k++) {

                nx = x + dx[k];
                ny = y + dy[k];
                if (0 <= nx && nx < boardWidth && 0 <= ny && ny < boardHeight) {
                    break;
                }
            }
            npos = new Position(nx, ny);
        }
        return npos;
    }

    @Override
    public boolean isGround() {
        return false;
    }

    @Override
    public GameBoard getBoard() {
        // TODO: implement this
        return this.board;
    }
}
