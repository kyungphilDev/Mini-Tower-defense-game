package edu.postech.csed332.homework1;

import java.util.Set;
import java.util.HashSet;

/**
 * An air tower that can attack nearby air monsters within 1 tile of distance.
 * For example, an air tower at (x,y) can attack any air monsters at (x-1, y),
 * (x+1, y), (x, y-1), and (x, y+1). The class AirTower implements the interface
 * Tower. TODO: implement all unimplemented methods.
 * Feel free to modify this file, e.g., adding new fields or methods. If needed,
 * you can define a new (abstract) super class that this class inherits.
 */
public class AirTower implements Tower {

    private final GameBoard board;

    public AirTower(GameBoard board) {
        // TODO: implement this
        this.board = board;
    }

    @Override
    public Set<Monster> attack() {
        // TODO: implement this
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        Set<Monster> monsterKilled = new HashSet<Monster>();

        Position pos = this.board.getPosition(this);
        int x = pos.getX();
        int y = pos.getY();
        int boardWidth = this.board.getWidth();
        int boardHeight = this.board.getHeight();
        for (int k = 0; k < 4; k++) {
            int nx = x + dx[k];
            int ny = y + dy[k];
            if (0 <= nx && nx < boardWidth && 0 <= ny  && ny < boardHeight) {
                Position npos = new Position(nx, ny);
                Set<Unit> checkSet = this.board.getUnitsAt(npos);
                if (checkSet.isEmpty()) {
                    continue;
                }
                for (Unit obj : checkSet) {
                    if (obj instanceof Monster && !obj.isGround()) {
                        monsterKilled.add((Monster) obj);
                    }
                }
            }
        }
        return monsterKilled;
    }

    @Override
    public GameBoard getBoard() {
        // TODO: implement this
        return this.board;
    }
}
