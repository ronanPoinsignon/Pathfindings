package prog.graphique.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import prog.graphique.caseGraphique.CaseGraphique;
import prog.graphique.controller.etat.EtatCase;
import prog.graphique.controller.etat.EtatCaseVide;
import prog.graphique.controller.etat.EtatMur;
import prog.map.Coordonnees;
import prog.map.Map;
import prog.map.caseMap.Case;
import prog.map.caseMap.CaseArrivee;
import prog.map.caseMap.CaseDepart;
import prog.map.caseMap.EmptyCase;
import prog.map.caseMap.state.EtatChemin;
import prog.pathFinding.algorithm.AStar;
import prog.pathFinding.algorithm.Djikstra;
import prog.pathFinding.algorithm.Strategy;
import prog.pathFinding.algorithm.WAStar;
import prog.pathFinding.exception.CoordsOutOfMapException;
import prog.pathFinding.exception.PathFindingException;
import prog.pathFinding.findNeighbor.StrategyCross;
import prog.pathFinding.findNeighbor.StrategyFind;
import prog.pathFinding.findNeighbor.StrategyKnight;
import prog.pathFinding.findNeighbor.StrategyPlus;
import prog.pathFinding.findNeighbor.StrategyStar;

public class Controller implements Initializable {

	@FXML
	private GridPane map;
	
	@FXML
	private ComboBox<String> comboBox;
	
	@FXML
	Canvas canvas;
	
	@FXML
	private ComboBox<String> comboBoxStratFind;
	
	@FXML
	private Slider sliderTime;
	
	private EtatCase etatCase = new EtatMur();
	private Map plateau = new Map();
	private CaseDepart caseD = null;
	private CaseArrivee caseA = null;
	private boolean isFinished = true;
	private HashMap<String, Strategy> mapPathFinding = new HashMap<String, Strategy>(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("A*", new AStar());
		put("WA*", new WAStar());
		put("D", new Djikstra());
	}};
	private HashMap<String, StrategyFind> mapStrategyFind = new HashMap<String, StrategyFind>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
		put("*", new StrategyStar());
	    put("+", new StrategyPlus());
	    put("x", new StrategyCross());
	    put("k", new StrategyKnight());
	}};
	
	private Strategy strat = null;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		comboBox.getItems().addAll(mapPathFinding.keySet());
		comboBoxStratFind.getItems().addAll(mapStrategyFind.keySet());
		sliderTime.valueProperty().addListener((obs, oldV, newV) -> {
			if(strat != null)
				strat.setSleepTime(newV.longValue());
		});
		map.setOnMouseClicked(evt -> {
			refresh();
			if(!(evt.getButton() == MouseButton.PRIMARY))
				return;
			int coordX = (int) Math.floor(evt.getX()/map.getWidth() * plateau.getTailleX());
			int coordY = (int) Math.floor(evt.getY()/map.getHeight() * plateau.getTailleY());
			Case ce;
			try {
				ce = plateau.getCase(coordX, coordY);
				if(ce.getClass().equals(EmptyCase.class))
					etatCase = new EtatMur();
				else
					etatCase = new EtatCaseVide();
				System.out.println(ce);
			} catch (CoordsOutOfMapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(etatCase);
		});
		map.setOnMouseDragged(evt -> {
			if(!(evt.getButton() == MouseButton.PRIMARY))
				return;
			int coordX = (int) Math.floor(evt.getX()/map.getWidth() * plateau.getTailleX());
			int coordY = (int) Math.floor(evt.getY()/map.getHeight() * plateau.getTailleY());
			Case ce = etatCase.getCase(new Coordonnees(coordX, coordY));
			setNewCase(ce);
			if(!ce.getClass().equals(ce.getClass())) {
				if(caseA != null && caseA.equals(ce))
					caseA = null;
				if(caseD != null && caseD.equals(ce))
					caseD = null;
			}
		});
		map.setOnMousePressed(evt -> {
			System.out.println("pressed");
	        if(evt.getButton() == MouseButton.MIDDLE)
	        	return;
	        plateau.resetCase();
	        CaseGraphique source = (CaseGraphique) evt.getTarget();
	        Integer colIndex = GridPane.getColumnIndex(source);
	        Integer rowIndex = GridPane.getRowIndex(source);
			if(evt.getButton() == MouseButton.SECONDARY) {
				if(evt.isControlDown()) {
					if(caseA != null) {
						setNewCase(new EmptyCase(new Coordonnees(caseA.getCoordX(), caseA.getCoordY())));
					}
					caseA = new CaseArrivee(new Coordonnees(colIndex, rowIndex));
					setNewCase(caseA);
				}
				else {
					if(caseD != null) {
						setNewCase(new EmptyCase(new Coordonnees(caseD.getCoordX(), caseD.getCoordY())));
					}
					caseD = new CaseDepart(new Coordonnees(colIndex, rowIndex));
					setNewCase(caseD);
				}
				return;
			}
	        try {
	        	Case c = plateau.getCase(colIndex, rowIndex);
	        	setNewCase(c.changeCase());
				if(caseA != null && caseA.equals(c))
					caseA = null;
				if(caseD != null && caseD.equals(c))
					caseD = null;
			} catch (CoordsOutOfMapException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		plateau.initEmptyMap();
		refresh();
	}
	
	public void refresh() {
		plateau.resetPath();
		for(int i = 0; i < plateau.getTailleX(); i++) {
			for(int j = 0; j < plateau.getTailleY(); j++) {
				CaseGraphique cg = null;
				try {
					Case c = plateau.getCase(i, j);
					cg = c.getGraphicCase();
					c.addObs(cg);
					c.addObsValeur(cg);
					map.getChildren().remove(cg);
					map.add(cg, i, j);
				} catch (CoordsOutOfMapException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	@FXML
	public void start() {
		if(!isFinished)
			return;
		isFinished = false;
		if(strat != null)
			strat.cancel();
		plateau.resetPath();
		plateau.resetCase();
		Strategy s = mapPathFinding.get(comboBox.getValue());
		if(s == null) {
			showNoAlgo();
			return;
		}
		strat = mapPathFinding.get(comboBox.getValue()).clone();
		strat.setSleepTime((long) this.sliderTime.getValue());
		StrategyFind strF = mapStrategyFind.get(comboBoxStratFind.getValue());
		if(strF == null){
			showNoFindStrat();
			return;
		}
		if(this.caseA == null || this.caseD == null) {
			showNoDebutFin();
			return;
		}
		if(this.caseA.getCoordX() == this.caseD.getCoordX() && this.caseA.getCoordY() == this.caseD.getCoordY()) {
			showSameDebutFin();
			return;
		}
		//PathFinding pathfinding = new PathFinding(mapPathFinding.get(comboBox.getValue()), caseD, caseA, mapStrategyFind.get(comboBoxStratFind.getValue()));
		strat.setCaseD(caseD);
		strat.setCaseA(caseA);
		strat.setFindStrat(strF);
		strat.setMap(plateau);
		//Task<List<Case>> tache = new TacheFind(strat, plateau, caseD, caseA);
		Thread t = new Thread(strat);
		t.setDaemon(true);
		t.start();
		strat.setOnSucceeded(evt -> {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						List<Case> liste = strat.get();
						for(Case c : liste) {
							c.setVisite(new EtatChemin());
							
							try{
								Thread.sleep(25);
							}
							catch(InterruptedException e) {
								
							}
						}
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
					isFinished = true;
				}
			}).start();
		});
		strat.setOnFailed(evt -> {
			isFinished = true;
			showError((PathFindingException) strat.getException());
		});
		//List<Case> liste = pathfinding.find(plateau, caseD, caseA);
		/*Point2D point1 = null;
		Point2D point2 = null;*/
		/*for(Case c : liste) {
			c.setVisite(new EtatChemin());
			System.out.println(c.getGraphicCase().getLayoutX());
			double centreH = c.getGraphicCase().getLayoutX() + c.getGraphicCase().getWidth();
			double centreV = c.getGraphicCase().getLayoutY();
			if(point1 == null)
				point1 = new Point2D(centreH, centreV);
			else {
				point2 = new Point2D(centreH, centreV);
				Line line = new Line(point1.getX(), point1.getY(), point2.getX(), point2.getY());
				line.setStroke(Color.BLUE);
				line.setStrokeWidth(10);
				canvas.getGraphicsContext2D().strokeLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
				point1 = point2;
				point2 = null;
			}
		}*/
	}

	public void showNoSolution() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur de chemin");
		alert.setHeaderText("Aucune solution trouvée");
		alert.setContentText("Aucun chemin n'a pu être trouvé avec ce choix de stratégie et de map.");
		alert.showAndWait();
	}
	
	public void showCoordsOutOfMap() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur de coordonnées");
		alert.setHeaderText("Coordonnées impossibles");
		alert.setContentText("Les coordonnées de la case de départ ou d'arrivée sont incorrectes car hors de la carte.");
		alert.showAndWait();
	}
	
	public void showCheminImpossible() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur sur le chemin");
		alert.setHeaderText("Chemin impossible à tracer");
		alert.setContentText("Aucun chemin ne peut être trouvé avec cette carte et cette stratégie de pathfinding.");
		alert.showAndWait();
	}
	
	public void showNoDebutFin() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur sur le chemin");
		alert.setHeaderText("Chemin impossible à tracer");
		alert.setContentText("Il n'y a pas de début ou d'arrivée");
		alert.showAndWait();
	}
	
	public void showSameDebutFin() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur sur le chemin");
		alert.setHeaderText("Même case de début et de fin.");
		alert.setContentText("La case de début de chemin est la même que cele de fin.");
		alert.showAndWait();
	}
	
	public void showNoAlgo() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Pas d'algorithme");
		alert.setHeaderText("Aucun algorithme donné");
		alert.setContentText("Vous n'avez donné aucun algorithme de recherche.");
		alert.showAndWait();
	}
	
	public void showNoFindStrat() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Pas de stratégie");
		alert.setHeaderText("Aucune stratégie de recherche donnée");
		alert.setContentText("Vous n'avez donné aucune stratégie de recherche. Celle-ci est utile à l'algorithme pour diriger ses déplacements.");
		alert.showAndWait();
	}
	
	public void showError(PathFindingException e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(e.getTitle());
		alert.setHeaderText(e.getHeader());
		alert.setContentText(e.getMessage());
		alert.showAndWait();
	}
	
	public void showHelp() {
		Alert alert = new Alert(AlertType.INFORMATION);

	    alert.initModality(Modality.APPLICATION_MODAL);

	    DialogPane dialogPane = alert.getDialogPane();
	    GridPane grid = new GridPane();
	    ColumnConstraints graphicColumn = new ColumnConstraints();
	    graphicColumn.setFillWidth(false);
	    graphicColumn.setHgrow(Priority.NEVER);
	    ColumnConstraints textColumn = new ColumnConstraints();
	    textColumn.setFillWidth(true);
	    textColumn.setHgrow(Priority.ALWAYS);
	    grid.getColumnConstraints().setAll(graphicColumn, textColumn);
	    grid.setPadding(new Insets(5));

	    Image image1 = new Image(getClass().getResourceAsStream("/resources/image/aide.PNG"));
	    ImageView imageView = new ImageView(image1);
	    StackPane stackPane = new StackPane(imageView);
	    stackPane.setAlignment(Pos.CENTER);
	    grid.add(stackPane, 0, 0);

	    dialogPane.setHeader(grid);
	    dialogPane.setGraphic(null);

	    alert.showAndWait()
	        .filter(response -> response == ButtonType.OK)
	        .ifPresent(response -> System.out.println("The alert was approved"));
	}
	
	private void setNewCase(Case c) {
		try {
			plateau.setCase(c.getCoordX(), c.getCoordY(), c);
			CaseGraphique cg = c.getGraphicCase();
			c.addObs(cg);
			c.addObsValeur(cg);
			map.getChildren().remove(cg);
			map.add(cg, c.getCoordX(), c.getCoordY());
		}
		catch(ArrayIndexOutOfBoundsException e) {
			
		}
		if(!isFinished) {
			refresh();
			isFinished = true;
		}
	}
	
}
