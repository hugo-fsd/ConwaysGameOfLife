package org.hugofsd.views;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.hugofsd.controller.Controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class GUI extends Application {
    Controller controller = new Controller();
    private final Rectangle[][] rectangles = new Rectangle[controller.getRows()][controller.getCols()];

    ExecutorService executorService = Executors.newCachedThreadPool();

    public void start(Stage primaryStage) {
        VBox vbox = new VBox();
        GridPane gridPane = new GridPane();
        GridPane buttonGrid = new GridPane();
        vbox.getChildren().addAll(gridPane, buttonGrid);

        Scene scene = new Scene(vbox, 900, 930);

        primaryStage.setResizable(false);
        primaryStage.setTitle("Conways game of life by Hugo-fsd");
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

/*        Button nextIterationButton = new Button("Next Iteration");
        nextIterationButton.setId("nextIteration");
        nextIterationButton.setMinHeight(30);
        nextIterationButton.setMinWidth(115);
        nextIterationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                nextIteration();
            }
        });
        buttonGrid.add(nextIterationButton, 2, 120);*/


        int rows = controller.getRows();
        int cols = controller.getCols();

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Rectangle rec = new Rectangle();
                rec.setWidth(8);
                rec.setHeight(8);
                rectangles[row][col] = rec;
                rec.setStyle("-fx-fill: black; -fx-stroke: #2b2b2b; -fx-stroke-width: 1;");
                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);
                gridPane.getChildren().addAll(rec);
            }
        }
    }

    public void startSimulation() {
        controller.populateGrid();

        executorService.submit(() -> {
            while (controller.isAlive()) {
                Integer[][] grid = controller.getGrid();
                controller.nextIteration();
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        if (grid[i][j] == 1) {
                            rectangles[i][j].setFill(Color.WHITE);
                        }
                    }
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(135);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        rectangles[i][j].setFill(Color.BLACK);
                    }
                }
            }
        });
    }

    //TODO fix UI

    public static void main(String[] args) {
        launch(args);
    }
}