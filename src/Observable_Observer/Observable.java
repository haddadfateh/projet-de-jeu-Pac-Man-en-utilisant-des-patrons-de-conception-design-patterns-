package Observable_Observer;

public interface Observable {
	public void AjouterObservateur(Observer o);
	public void SupprimerObservateur(Observer o);
	public void notifyObservers();

}
