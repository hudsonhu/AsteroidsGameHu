package display;

import data.PersistentScoreKeeper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.GameCore;
import ucd.comp2011j.engine.GameManager;

public class GameStart extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, GameCore.SCREEN_WIDTH, GameCore.SCREEN_HEIGHT);
        PlayerListener playerListener = new PlayerListener();
        playerListener.setListeners(scene);
        MenuListener menuListener = new MenuListener();
        menuListener.setListeners(scene);
        primaryStage.setTitle("Asteroid Game Hu Edition");
        GameCore game = new GameCore(playerListener);
        GameScreen gameScreen = new GameScreen(game);
        MenuScreen menuScreen = new MenuScreen();
        PersistentScoreKeeper scoreKeeper = new PersistentScoreKeeper();
        GameManager mmm = new GameManager(game, root, menuListener, menuScreen,new AboutScreen(), new ScoreScreen(scoreKeeper),gameScreen,scoreKeeper);
        menuScreen.paint();
        primaryStage.setScene(scene);
        primaryStage.show();
        mmm.run();
    }
}