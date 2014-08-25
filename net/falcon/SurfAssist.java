package net.falcon;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.falcon.gui.MainController;
import net.falcon.log.LogUpdater;
import net.falcon.maps.StatHandler;


public class SurfAssist extends Application {

	
	//Features:
	//map database
	//comments
	//saves your times per-stage
	//saves your overall time
	//TODO timer - time until map change (calculated by you, or autoset after map change...)
	//TODO allow username change

	/**
	 * The Main GUI controller.
	 */
	public static MainController controller;
	
	public static final String LOG_URL = "C:\\Program Files (x86)\\Steam\\steamapps\\common\\Team Fortress 2\\tf\\console.log";


	@Override
	public void start(Stage primaryStage) {
		StatHandler.instance().loadMaps();

		//Set the stage as resizable and make sure it saves data when closed
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				System.out.println("Saving and Exiting...");
				File log = new File(LOG_URL);
				log.delete();
				StatHandler.instance().saveAllMaps();
				System.exit(0);
			}
		});

		//Build the GUI from the XML file
		URL location = getClass().getResource("MainGui.fxml");
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(location);
		fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());

		//Set details about the ui pane, set a controller
		try {
			AnchorPane page = (AnchorPane) fxmlLoader.load(location.openStream());
			controller = fxmlLoader.getController();
			controller.updateMapList();
			Scene scene = new Scene(page);	
			primaryStage.setScene(scene);
			primaryStage.setTitle("Surf Assist v.1");
			primaryStage.show();
		} catch(IOException e) {
			//Error trying to load the XML...the UI will not load. Abort
			e.printStackTrace();
			System.exit(0);
		}
		
		//Start the thread that will regularly read the TF2 log
		new Thread(new LogUpdater()).start();

	}




	public static void main(String[] args) {
		launch(args);
	}
}
