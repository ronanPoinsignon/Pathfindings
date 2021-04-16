package prog.graphique.caseGraphique;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import prog.graphique.caseGraphique.etatChemin.EstChemin;
import prog.graphique.caseGraphique.etatChemin.EstPasChemin;
import prog.graphique.caseGraphique.etatChemin.EtatCalcule;
import prog.graphique.caseGraphique.etatChemin.EtatChemin;
import prog.graphique.caseGraphique.etatChemin.EtatVisite;
import prog.map.caseMap.Case;
import prog.map.observator.ObsEtat;
import prog.map.observator.ObsValeur;

public abstract class CaseGraphique extends StackPane implements ObsEtat, ObsValeur {

	public static final int TAILLE_CASE = 60;
	
	protected Color bg = Color.WHITE;
	private Rectangle rect = new Rectangle();
	private GridPane pane = new GridPane();
	private Label labelG = new Label("-10"), labelH = new Label("-10"), labelF = new Label("-10");
	
	protected EtatChemin etat;
	
	public CaseGraphique() {
		this.getChildren().add(rect);
		this.getChildren().add(pane);
		pane.setMouseTransparent(true);
		labelF.setFont(new Font(10));
		labelG.setFont(new Font(10));
		labelH.setFont(new Font(10));
		labelF.visibleProperty().bind(labelF.textProperty().isNotEqualTo("-10"));
		labelG.visibleProperty().bind(labelG.textProperty().isNotEqualTo("-10"));
		labelH.visibleProperty().bind(labelH.textProperty().isNotEqualTo("-10"));
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(60);
		pane.getColumnConstraints().add(column1);
		labelF.setMouseTransparent(true);
		labelG.setMouseTransparent(true);
		labelH.setMouseTransparent(true);
		pane.add(labelG, 0, 0);
		pane.add(labelH, 1, 0);
		pane.add(labelF, 0, 1);
		labelH.setAlignment(Pos.TOP_RIGHT);
		rect.setMouseTransparent(true);
		rect.setWidth(TAILLE_CASE);
		rect.setHeight(TAILLE_CASE);
	}
	
	public void setEtat(EtatChemin etat) {
		this.etat = etat;
		setFill(this.getShownColor());
	}
	
	public void setFill(Color c) {
		rect.setFill(c);
	}
	
	public Color getBaseColor() {
		return bg;
	}
	
	public Color getShownColor() {
		return etat.getColor();
	}
	
	public EtatChemin getEtat() {
		return etat;
	}
	
	@Override
	public void notifierEtat(Case c) { //TODO a modifier
		if(c.getEtat().getClass() == prog.map.caseMap.state.EtatChemin.class) {
			this.setEtat(new EstChemin(this));
		}
		else if(c.getEtat().getClass() == prog.map.caseMap.state.EtatCalcule.class) {
			this.setEtat(new EtatCalcule(this));
		}
		else if(c.getEtat().getClass() == prog.map.caseMap.state.EtatVisite.class) {
			this.setEtat(new EtatVisite(this));
		}
		else {
			this.setEtat(new EstPasChemin(this));
		}
	}
	
	@Override
	public void notifierValeur(double f, double g, double h) {
		labelF.setText((int) (f * 10) + "");
		labelG.setText((int) (g * 10) + "");
		labelH.setText((int) (h * 10) + "");
	}
	
	public abstract CaseGraphique changeCase();
	
}
