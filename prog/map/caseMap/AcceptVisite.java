package prog.map.caseMap;

import prog.pathFinding.algorithm.Visiteur;

/**
 * interface d'acceptation des {@link Visiteur}.
 * @author ronan
 *
 */
public interface AcceptVisite {

	public void accepte(Visiteur v) throws VisiteImpossibleException;
}
