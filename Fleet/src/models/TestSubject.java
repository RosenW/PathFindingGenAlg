package models;

import core.Game;

import java.awt.*;
import java.util.Random;

public class TestSubject {
    private int x;
    private int y;
    private int[][] DNA;
    private int fitness;
    private boolean objectiveReached;
    private int moves;
    private static Random RAND = new Random();
    private Color color;
    boolean crashed;

    public TestSubject(int[][] DNA) {
        color = new Color(RAND.nextInt(100) + 100, RAND.nextInt(100) + 100, RAND.nextInt(100) + 100);
        this.x = 2;
        this.y = 14;
        this.DNA = DNA;
        fitness = 0;
        moves = 0;
        crashed = false;
        objectiveReached = false;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public int[][] getDNA() {
        int[][] newDNA = new int[29][29];
        for (int i = 0; i < newDNA.length; i++) {
            for (int j = 0; j < newDNA.length; j++) {
                newDNA[i][j] = DNA[i][j];
            }
        }
        return newDNA;
    }

    public void setDNA(int[][] DNA) {
        this.DNA = DNA;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void tick() {
        int destinationX = 0;
        int destinationY = 0;

        for (int i = 0; i < Game.GRID.length; i++) {
            for (int j = 0; j < Game.GRID.length; j++) {
                if (Game.GRID[i][j] == 2) {
                    destinationX = i;
                    destinationY = j;
                    break;
                }
            }
        }

        if (this.getX() == destinationX && this.getY() == destinationY) {
            objectiveReached = true;
            if (!Game.MAP_COMPLETED){
                Game.MAP_COMPLETED = true;
            }
        }
        if (objectiveReached) {
            return;
        }
        int direction = 0;
        try {
            direction = DNA[x][y];

            switch (direction) {
                case 1:
                    if (Game.GRID[x + 1][y] != 1) {
                        x++;
                    }else{
                        crashed = true;
                    }
                    break;
                case 2:
                    if (Game.GRID[x][y + 1] != 1) {
                        y++;
                    }else{
                        crashed = true;
                    }
                    break;
                case 3:
                    if (Game.GRID[x - 1][y] != 1) {
                        x--;
                    }else{
                        crashed = true;
                    }
                    break;
                case 4:
                    if (Game.GRID[x][y - 1] != 1) {
                        y--;
                    }else{
                        crashed = true;
                    }
                    break;
                case 5:
                    if (Game.GRID[x + 1][y + 1] != 1) {
                        x++;
                        y++;
                    }else{
                        crashed = true;
                    }
                    break;
                case 6:
                    if (Game.GRID[x - 1][y + 1] != 1) {
                        x--;
                        y++;
                    }else{
                        crashed = true;
                    }
                    break;
                case 7:
                    if (Game.GRID[x - 1][y - 1] != 1) {
                        x--;
                        y--;
                    }else{
                        crashed = true;
                    }
                    break;
                case 8:
                    if (Game.GRID[x + 1][y - 1] != 1) {
                        x++;
                        y--;
                    }else{
                        crashed = true;
                    }
                    break;
                default:
                    break;
            }
            moves++;
        } catch (Exception ignored) {
            moves++;
        }
    }

    public void render(Graphics g) {
        g.setColor(this.color);
        g.fillRect(x * 24 - 1, y * 24 - 1, 25, 25);
    }

    public void evaluate() {
        int destinationX = 0;
        int destinationY = 0;
        int distance;
        int objective = 0;
        int crashValue = 0;

        if (objectiveReached) {
            objective = 50;
        }

        for (int i = 0; i < Game.GRID.length; i++) {
            for (int j = 0; j < Game.GRID.length; j++) {
                if (Game.GRID[i][j] == 2) {
                    destinationX = i;
                    destinationY = j;
                    break;
                }
            }
        }

        distance = Math.abs(x - destinationX) + Math.abs(y - destinationY);
        if (crashed){
            crashValue = 10;
        }
        fitness = 100 + objective - moves - distance - crashValue;
        if (fitness > Game.TOPSCORE) {
            Game.TOPSCORE = fitness;
        }
    }

    public void mutate() {
        for (int i = 0; i < this.DNA.length; i++) {
            for (int j = 0; j < this.DNA.length; j++) {
                int chance = RAND.nextInt(100);
                int calcChance = 0;
                if (Game.MAP_COMPLETED) {
                    calcChance = 2;
                } else {
                    calcChance = 15;
                }
                if (chance < calcChance) {
                    this.DNA[i][j] = RAND.nextInt(8) + 1;
                }
            }
        }
    }

    public boolean isObjectiveReached() {
        return objectiveReached;
    }

    public void setObjectiveReached(boolean objectiveReached) {
        this.objectiveReached = objectiveReached;
    }

    public int getMoves() {
        return moves;
    }

    public void setMoves(int moves) {
        this.moves = moves;
    }

    public static Random getRAND() {
        return RAND;
    }

    public static void setRAND(Random RAND) {
        TestSubject.RAND = RAND;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }
}
