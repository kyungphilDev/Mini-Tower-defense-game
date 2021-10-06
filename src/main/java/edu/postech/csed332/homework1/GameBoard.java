package edu.postech.csed332.homework1;

import java.util.*;

/**
 * A game board contains a set of units and a goal position. A game consists
 * of a number of rounds. For each round, all units perform their actions:
 * a monster can escape or move, and a tower can attack nearby monsters.
 * The following invariant conditions must be maintained throught the game:
 * <p>
 * (a) The position of each unit is inside the boundaries.
 * (b) Different ground units cannot be on the same tile.
 * (c) Different air units cannot be on the same tile.
 * <p>
 * TODO: implements all the unimplemented methods (marked with TODO)
 * NOTE: do not modify the signature of public methods (which will be
 * used for GUI and grading). But you can feel free to add new fields
 * or new private methods if needed.
 */
public class GameBoard {
    private final Position goal;
    private final int width, height;

    // TODO: add more fields to implement this class
    Set<Monster> monsterEscaped = new HashSet<Monster>();
    Set<Monster> monsterKilled = new HashSet<Monster>();
    ArrayList<Unit>[][] boardList;
    ArrayList<Monster>[][] tmpBoardList;
    boolean airDangerList[][];
    boolean groundDangerList[][];

    /**
     * Creates a game board with a given width and height. The goal position
     * is set to the middle of the end column.
     *
     * @param width  of this board
     * @param height of this board
     */
    public GameBoard(int width, int height) {
        this.width = width;
        this.height = height;
        goal = new Position(width - 1, height / 2);

        // TODO: add more lines if needed.
        boardList = new ArrayList[width][height];
        airDangerList = new boolean[width][height];
        groundDangerList = new boolean[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                boardList[i][j] = new ArrayList<Unit>();
                airDangerList[i][j] = false;
                groundDangerList[i][j] = false;
            }
        }
    }

    /**
     * Places a unit at a given position into this board.
     *
     * @param obj a unit to be placed
     * @param p   the position of obj
     * @throws IllegalArgumentException if p is outside the bounds of the board.
     */
    public void placeUnit(Unit obj, Position p) {
        // TODO: implement this
        if (p == null) {
            throw new IllegalArgumentException("p is null.");
        }
        int x = p.getX();
        int y = p.getY();
        if (x < 0 || x >= this.width || y < 0 || y >= this.height) {
            throw new IllegalArgumentException("p is outside the bounds of the board.");
        }
        // check is valid
        if (!boardList[x][y].isEmpty()) {
            if (obj.isGround()) {
                for (Unit u : boardList[x][y]) {
                    if (u.isGround()) {
                        throw new IllegalArgumentException("Two ground objs cannot be on the same tile.");
                    }
                }
            } else {
                for (Unit u : boardList[x][y]) {
                    if (!u.isGround()) {
                        throw new IllegalArgumentException("Two flying monsters cannot be on the same tile.");
                    }
                }
            }
        }
        if (obj instanceof Tower) {
            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};
            int boardWidth = this.getWidth();
            int boardHeight = this.getHeight();
            boolean t_isGround = obj.isGround();
            groundDangerList[x][y] = true;
            for (int k = 0; k < 4; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];
                if (0 <= nx && nx < boardWidth && 0 <= ny && ny < boardHeight) {
                    if (obj instanceof GroundTower) {
                        groundDangerList[nx][ny] = true;
                    } else {
                        airDangerList[nx][ny] = true;
                    }
                }
            }
        }
        boardList[x][y].add(obj);
    }

    /**
     * Clears this game board. That is, all units are removed, and all numbers
     * for game statistics are reset to 0.
     */
    public void clear() {
        // TODO: implement this
        this.monsterEscaped = new HashSet<Monster>();
        this.monsterKilled = new HashSet<Monster>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                boardList[i][j] = new ArrayList<Unit>();
                groundDangerList[i][j] = false;
                airDangerList[i][j] = false;
            }
        }
    }

    /**
     * Returns the set of units at a given position in the board. Note that
     * multiple units can exist at the same position (e.g., ground and air)
     *
     * @param p a position
     * @return the set of units at position p
     */
    public Set<Unit> getUnitsAt(Position p) {
        // TODO: implement this
        Set<Unit> tmpUnitSet = new HashSet<Unit>();
        int x = p.getX();
        int y = p.getY();
        for (Unit obj : boardList[x][y]) {
            tmpUnitSet.add(obj);
        }
        return tmpUnitSet;
    }

    /**
     * Returns the set of all monsters in this board.
     *
     * @return the set of all monsters
     */
    public Set<Monster> getMonsters() {
        // TODO: implement this
        Set<Monster> monsterSet = new HashSet<Monster>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (boardList[i][j].isEmpty()) {
                    continue;
                }
                for (Unit u : boardList[i][j]) {
                    if (u instanceof Monster) {
                        monsterSet.add((Monster) u);
                    }
                }
            }
        }
        return monsterSet;
    }

    /**
     * Returns the set of all towers in this board.
     *
     * @return the set of all towers
     */
    public Set<Tower> getTowers() {
        // TODO: implement this
        Set<Tower> towerSet = new HashSet<Tower>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (boardList[i][j].isEmpty()) {
                    continue;
                }
                for (Unit u : boardList[i][j]) {
                    if (u instanceof Tower) {
                        towerSet.add((Tower) u);
                    }
                }
            }
        }
        return towerSet;
    }

    /**
     * Returns the position of a given unit
     *
     * @param obj a unit
     * @return the position of obj
     */
    public Position getPosition(Unit obj) {
        // TODO: implement this
        Position pos = null;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (boardList[i][j].isEmpty()) {
                    continue;
                }
                for (Unit u : boardList[i][j]) {
                    if (u.equals(obj)) {
                        pos = new Position(i, j);
                    }
                }
            }
        }
        return pos;
    }

    /**
     * Proceeds one round of a game. The behavior of this method is as follows:
     * (1) Every monster at the goal position escapes from the game.
     * (2) Each tower attacks nearby remaining monsters (using the attack method).
     * (3) All remaining monsters (neither escaped nor attacked) moves (using the goal method).
     */
    public void step() {
        // TODO: implement this
        // (1) Every monster at the goal position escapes from the game.
        for (Monster m : this.getMonsters()) {
            Position pos = this.getPosition((Unit) m);
            if (pos.equals(this.goal)) {
                this.monsterEscaped.add(m);
                boardList[pos.getX()][pos.getY()].remove(m);
            }
        }
        // (2) Each tower attacks nearby remaining monsters (using the attack method).
        for (Tower t : this.getTowers()) {
            Set<Monster> now_monsterKilled = t.attack();
            if (now_monsterKilled.isEmpty()) {
                continue;
            }
            for (Monster m : now_monsterKilled) {
                this.monsterKilled.add(m);
                Position pos = this.getPosition((Unit) m);
                boardList[pos.getX()][pos.getY()].remove(m);
            }
        }
        // (3) All remaining monsters (neither escaped nor attacked) moves (using the goal method).
        tmpBoardList = new ArrayList[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tmpBoardList[i][j] = new ArrayList<Monster>();
            }
        }
        for (Monster m : this.getMonsters()) {
            Position pos = this.getPosition(m);
            int x = pos.getX();
            int y = pos.getY();
            Position npos = m.move();
            int nx = npos.getX();
            int ny = npos.getY();
            this.boardList[x][y].remove(m);
            this.tmpBoardList[nx][ny].add(m);
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for(Monster m: tmpBoardList[i][j]) {
                    boardList[i][j].add(m);
                }
            }
        }
    }

    /**
     * Checks whether the following invariants hold in the current game board:
     * (a) All units are in the boundaries.
     * (b) Different ground units cannot be on the same tile.
     * (c) Different air units cannot be on the same tile.
     *
     * @return true if there is no problem. false otherwise.
     */
    public boolean isValid() {
        // TODO: implement this
        return true;
    }

    /**
     * Returns the number of the monsters in this board.
     *
     * @return the number of the monsters
     */
    public int getNumMobs() {
        // TODO: implement this
        return this.getMonsters().size();
    }

    /**
     * Returns the number of the towers in this board.
     *
     * @return the number of the towers
     */
    public int getNumTowers() {
        // TODO: implement this
        return this.getTowers().size();
    }

    /**
     * Returns the number of the monsters removed so far in this game.
     * This number will be reset to 0 by the clear method.
     *
     * @return the number of the monsters removed
     */
    public int getNumMobsKilled() {
        // TODO: implement this
        return monsterKilled.size();

    }

    /**
     * Returns the number of the monsters escaped so far in this game
     * This number will be reset to 0 by the clear method.
     *
     * @return the number of the monsters escaped
     */
    public int getNumMobsEscaped() {
        return monsterEscaped.size();
    }

    /**
     * Returns the width of this board.
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of this board.
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the goal position where the monster can escape.
     *
     * @return the goal position of this game
     */
    public Position getGoalPosition() {
        return goal;
    }
}
