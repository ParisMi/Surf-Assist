package net.falcon.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import net.falcon.maps.StatHandler;
import net.falcon.maps.SurfMSM;
import net.falcon.maps.SurfMap;
import net.falcon.maps.SurfTime;

/**
 * The Main UI Controller. It is responsibile for all ui input and
 * also all changes to the display. No logic of any kind should be in this class.
 */
public class MainController {

	@FXML TextField mapInput;
	@FXML ListView<String> mapList;
	@FXML ListView<SurfMSM> worldList;
	@FXML ListView<String> stageList;
	@FXML ListView<SurfMSM> personalList;
	@FXML AnchorPane mapPane;
	@FXML TextField commentField;

	public String currentViewedMap = "";


	/**
	 * Reads the search bar and updates the map lists with the correct results.
	 * Like all UI operations, this change can only occur in the UI thread.
	 */
	public void updateMapList()	 {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				ObservableList<String> newList = FXCollections.observableArrayList();
				newList.clear();
				newList.addAll(StatHandler.instance().findMapNamesStartingWith(mapInput.getText()));
				mapList.setItems(newList);
			}
		});
	}

	public void updateMapPane(String mapName)	 {
		updateMapPane(StatHandler.instance().findMap(mapName));
	}
	
	/**
	 * Updates the right pane with the new map's world record/personal records and comments.
	 * Like all UI operations, this change can only occur in the UI thread.
	 * @param mapName the map whose records are being loaded.
	 */
	public void updateMapPane(final SurfMap map)	 {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				mapPane.setVisible(true);
				ObservableList<String> stageLis = FXCollections.observableArrayList();
				ObservableList<SurfMSM> worldLis = FXCollections.observableArrayList();
				ObservableList<SurfMSM> personalLis = FXCollections.observableArrayList();
				currentViewedMap = map.name;
				commentField.setText(map.comment);
				if(map.overallTime.pr != null) {
					stageLis.add("Overall");
					worldLis.add(map.overallTime.wr);
					personalLis.add(map.overallTime.pr);
				}

				//Add stage time listings
				for(Integer i : map.getStages()) {
					stageLis.add("Stage " + i);
					SurfTime time = map.getStage(i);
					worldLis.add(time.wr);
					personalLis.add(time.pr);
				}

				stageList.setItems(stageLis);
				worldList.setItems(worldLis);
				personalList.setItems(personalLis);
			}
		});
	}

	@FXML
	public void onMapClick(MouseEvent e) {
		String mapName = mapList.getSelectionModel().getSelectedItem();
		updateMapPane(mapName);
	}

	/**
	 * Fired when the comment field is changed (the comment is being updated).
	 */
	@FXML void onCommentUpdate(KeyEvent e) {
		SurfMap s = StatHandler.instance().findMap(currentViewedMap);
		s.comment = commentField.getText() + e.getText();
	}
	
	/**
	 * Fired when the map search field is changed (user is searching for a map).
	 */
	@FXML
	public void keyTyped(KeyEvent e) {
		updateMapList();
	}

	/**
	 * When the "enter" key is pressed on the map search box
	 */
	@FXML
	public void onTextEnter(ActionEvent e) {
		if(mapList.getItems().size() == 0) {
			return;
		}
		mapList.getSelectionModel().select(0);
		updateMapPane(mapList.getItems().get(0));
	}

}
