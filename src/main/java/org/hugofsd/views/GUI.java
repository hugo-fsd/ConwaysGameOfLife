package org.hugofsd.views;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.hugofsd.controller.Controller;


import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class GUI extends Application {
    Controller controller = new Controller();

    private final Rectangle[][] rectangles = new Rectangle[controller.getRows()][controller.getCols()];
    private boolean isRunning;


    ExecutorService executorService = Executors.newCachedThreadPool();

    public void start(Stage primaryStage) {
        VBox vbox = new VBox();
        GridPane gridPane = new GridPane();
        GridPane buttonGrid = new GridPane();
        vbox.getChildren().addAll(gridPane, buttonGrid);

        Scene scene = new Scene(vbox, 900, 930);

        Image image = new Image("/logo.jpg");

        primaryStage.getIcons().add(image);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Conways game of life by github.com/hugo-fsd");
        primaryStage.setScene(scene);
        primaryStage.show();

        Button startButton = new Button("Start Simulation");
        startButton.setId("startButton");
        startButton.setMinHeight(30);
        startButton.setMinWidth(115);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startSimulation();
            }
        });
        buttonGrid.add(startButton, 0, 120);

        Button nextIterationButton = new Button("Next Iteration");
        nextIterationButton.setId("nextIteration");
        nextIterationButton.setMinHeight(30);
        nextIterationButton.setMinWidth(115);
        nextIterationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                nextIteration();
            }
        });
        buttonGrid.add(nextIterationButton, 2, 120);

        Button stopButton = new Button("Stop Iteration");
        stopButton.setId("stopIteration");
        stopButton.setMinHeight(30);
        stopButton.setMinWidth(115);
        stopButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stopSimulation();
            }
        });
        buttonGrid.add(stopButton, 3, 120);

        Button resumeButton = new Button("Resume Iteration");
        resumeButton.setId("resumeIteration");
        resumeButton.setMinHeight(30);
        resumeButton.setMinWidth(115);
        resumeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startSimulation();
            }
        });
        buttonGrid.add(resumeButton, 4, 120);

        Button randomisePopulationButton = new Button("Randomise Population");
        randomisePopulationButton.setId("Randomise Population");
        randomisePopulationButton.setMinHeight(30);
        randomisePopulationButton.setMinWidth(115);
        randomisePopulationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                randomisePopulation();
            }
        });
        buttonGrid.add(randomisePopulationButton, 5, 120);

        Button resetButton = new Button("Reset");
        resetButton.setId("Reset");
        resetButton.setMinHeight(30);
        resetButton.setMinWidth(115);
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                reset();
            }
        });
        buttonGrid.add(resetButton, 6, 120);

        gridPane.setOnDragDetected(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                event.consume();
                gridPane.startFullDrag();
            }
        });


        int rows = controller.getRows();
        int cols = controller.getCols();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Rectangle rec = new Rectangle();
                rec.setWidth(8);
                rec.setHeight(8);
                rectangles[row][col] = rec;
                rec.setStyle("-fx-fill: black; -fx-stroke: #2b2b2b; -fx-stroke-width: 1;");
                rec.setOnMouseDragEntered(event -> {
                    event.consume();
                    rec.setFill(Color.WHITE);
                });
                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);
                gridPane.getChildren().addAll(rec);
            }
        }
    }

    public void startSimulation() {
        runSimulation();
        executorService.submit(() -> {
            while (isRunning()) {
                nextIteration();
                try {
                    TimeUnit.MILLISECONDS.sleep(135);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void nextIteration() {
        paintToValue();
        controller.nextIteration();
        valueToPaint();
    }


    public void valueToPaint() {
        Integer[][] grid = controller.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                rectangles[i][j].setFill(Color.BLACK);
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    rectangles[i][j].setFill(Color.WHITE);
                }
            }
        }
    }

    public void paintToValue() {
        Integer[][] grid = controller.getGrid();
        for (int i = 0; i < rectangles.length; i++) {
            for (int j = 0; j < rectangles.length; j++) {
                if (rectangles[i][j].getFill().equals(Color.WHITE)) {
                    grid[i][j] = 1;
                } else {
                    grid[i][j] = 0;
                }
            }
        }
        controller.addGeneration();
    }

    public void randomisePopulation() {
        controller.populateGrid();
        valueToPaint();
    }

    public void runSimulation() {
        this.isRunning = true;
    }

    public void stopSimulation() {
        this.isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void reset() {
        Integer[][] grid = controller.getGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = 0;
            }
        }
        valueToPaint();
        controller.resetGenerations();
        stopSimulation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}