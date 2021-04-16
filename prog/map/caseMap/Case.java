package prog.map.caseMap;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import prog.graphique.caseGraphique.CaseGraphique;
import prog.map.Coordonnees;
import prog.map.Map;
import prog.map.caseMap.state.Etat;
import prog.map.caseMap.state.EtatInconnu;
import prog.map.observator.ObsCase;
import prog.map.observator.ObsEtat;
import prog.map.observator.ObsValeur;

/**
 * Case de la {@link Map}.
 * @author ronan
 *
 */
public abstract class Case implements AcceptVisite {

	protected List<ObsEtat> listeObservateurEtat = new ArrayList<>();
	protected List<ObsCase> listeObservateurCase = new ArrayList<>();
	protected List<ObsValeur> listeObservateurValeur = new ArrayList<>();
	
	private double f = -1;
	private double g = -1;
	private double h = - 1;
	
	private Etat etat = new EtatInconnu();
	private Coordonnees coordonnees;
	
	public Case(Coordonnees coords) {
		this.coordonnees = coords;
	}
	
	public void setVisite(Etat etat) {
		this.etat = etat;
		for(ObsEtat obs : listeObservateurEtat)
			try {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						obs.notifierEtat(Case.this);
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public boolean addObs(ObsEtat obs) {
		return this.listeObservateurEtat.add(obs);
	}
	
	public boolean removeObs(ObsEtat obs) {
		return this.listeObservateurEtat.remove(obs);
	}
	
	public boolean addObsCase(ObsCase obs) {
		return this.listeObservateurCase.add(obs);
	}
	
	public boolean removeObsCase(ObsCase obs) {
		return this.listeObservateurCase.remove(obs);
	}
	
	public boolean addObsValeur(ObsValeur obs) {
		return this.listeObservateurValeur.add(obs);
	}
	
	public void setF(double f) {
		this.f = f;
		final double ft = (f == Double.POSITIVE_INFINITY ? -1: f), 
				gt = (g == Double.POSITIVE_INFINITY ? -1: g), 
				ht = (h == Double.POSITIVE_INFINITY ? -1: h);
		for(ObsValeur obs : listeObservateurValeur) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					obs.notifierValeur(ft, gt, ht);
				}
			});
		}
	}
	
	public double getG() {
		return g;
	}
	
	public void setG(double g) {
		this.g = g;
		final double ft = (f == Double.POSITIVE_INFINITY ? -1: f), 
				gt = (g == Double.POSITIVE_INFINITY ? -1: g), 
				ht = (h == Double.POSITIVE_INFINITY ? -1: h);
		for(ObsValeur obs : listeObservateurValeur) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					obs.notifierValeur(ft, gt, ht);
				}
			});
		}
	}
	
	public double getH() {
		return h;
	}
	
	public void setH(double h) {
		this.h = h;
		final double ft = (f == Double.POSITIVE_INFINITY ? -1: f), 
				gt = (g == Double.POSITIVE_INFINITY ? -1: g), 
				ht = (h == Double.POSITIVE_INFINITY ? -1: h);
		for(ObsValeur obs : listeObservateurValeur) {
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					obs.notifierValeur(ft, gt, ht);
				}
			});
		}
	}
	
	public Etat getEtat() {
		return etat;
	}
	
	public int getCoordX() {
		return this.coordonnees.getX();
	}
	
	public int getCoordY() {
		return this.coordonnees.getY();
	}
	
	public abstract Case clone();
	
	@Override
	public String toString() {
		return "[" + coordonnees.getX() + ";" + coordonnees.getY() + "]";
	}
	
	public abstract Case changeCase();
	public abstract CaseGraphique getGraphicCase();
}
