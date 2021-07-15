package org.hugofsd.models;

import java.util.Random;

public class Grid {
    private Integer[][] matrix;
    private int generations;
    private final int rows;
    private final int cols;

    public Grid(int rows, int cols) {
        this.matrix = new Integer[rows][cols];
        this.generations = 0;
        this.rows = rows;
        this.cols = cols;
    }

    public void populateGrid() {
        Random random = new Random();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = Math.toIntExact(Math.round((0 + (0.55 - 0) * random.nextDouble())));
            }
        }
    }

    public void addGeneration() {
        this.generations++;
    }

    public boolean isAlive() {
        int cellsAlive = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 1) {
                    cellsAlive++;
                }
            }
        }
        return cellsAlive != 0;
    }

    public void nextIteration() {
        Integer [][] gridCopy = new Integer[matrix.length][];
        for(int i = 0; i < matrix.length; i++)
            gridCopy[i] = matrix[i].clone();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (checkNeighboursAlive(i, j) < 2) {
                    gridCopy[i][j] = 0;
                } else if (checkNeighboursAlive(i, j) > 3) {
                    gridCopy[i][j] = 0;
                } else if (checkNeighboursAlive(i, j) == 2) {
                    gridCopy[i][j] = matrix[i][j];
                } else {
                    gridCopy[i][j] = 1;
                }
            }
        }
        matrix = gridCopy;
        addGeneration();
    }

    public int checkNeighboursAlive(int y, int x) {
        int neighboursAlive = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isCoordInBoard(y, i, x, j) && (!isCellItself(i, j)) && matrix[y + i][x + j] == 1) {
                    neighboursAlive++;
                }
            }
        }
        return neighboursAlive;
    }

    public boolean isCoordInBoard(int y, int i, int x, int j) {
        return y + i > -1 && y + i < matrix.length && x + j > -1 && x + j < matrix[0].length;
    }

    public boolean isCellItself(int i, int j) {
        return i == 0 && j == 0;
    }

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public Integer[][] getGrid() {
        return this.matrix;
    }

    public int getGenerations() {
        return this.generations;
    }
}
