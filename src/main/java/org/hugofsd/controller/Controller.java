package org.hugofsd.controller;

import org.hugofsd.models.Grid;

public class Controller {

    private final Grid grid;

    public Controller() {
        this.grid = new Grid(100, 100);
    }

    public void populateGrid() {
        grid.populateGrid();
    }

    public boolean isAlive() {
        return grid.isAlive();
    }

    public void nextIteration() {
        grid.nextIteration();
        addGeneration();
    }

    public int getCols() {
        return grid.getCols();
    }

    public int getRows() {
        return grid.getRows();
    }

    public Integer[][] getGrid() {
        return grid.getGrid();
    }

    public int getGenerations() {
        return grid.getGenerations();
    }

    public void addGeneration() {
        grid.addGeneration();
    }

    public void resetGenerations() {
        grid.resetGenerations();
    }
}
