package de.hhn.it.simulation;

import de.hhn.it.ui.ImageFileGraphic;
import de.hhn.it.ui.SimulationGraphic;

/**
 *Class {@link Food} creates food for ants
 * @author Alexandra-Valentina Stratulat
 * @version Aufgabe 2
 */

public class Food {

    private SimulationGraphic simulationGraphic;

    private double x, y, rotation;

    private String text;

    private int foodUnits;

    private boolean visibility;

    public Food(double x, double y, double rotation){
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.foodUnits = 0;
        this.text = "Food Pile";
        this.simulationGraphic = new ImageFileGraphic("food.png");
        this.visibility = true;
    }

    public boolean getVisibility(){
        return this.visibility;
    }

    public void setVisibility(boolean visibility){
        this.visibility = visibility;
    }

    /**
     * Fuehrt einen Simulationsschritt durch.
     */
    public void doSimulationStep() {
    }

    /**
     * @return Beschreibungstext des Futtereinheiten, kann {@code null} sein.
     */
    public String getText() {
        return (text + " mit " + this.foodUnits + " Futtereinheiten");
    }

    /**
     * Gibt die Anzahl der aktuell im Ameisenhaufen vorhandenen Futtereinheiten zurueck.
     * @return die aktuelle Anzahl der Futtereinheiten des Ameisenhaufens
     */
    public int getFoodUnits() {
        return foodUnits;
    }

    /**
     * Setzt die Anzahl der aktuell im Ameisenhaufen vorhandenen Futtereinheiten.
     * Hinweis: Eine negative Anzahl an Futtereinheiten wird ignoriert.
     * @param foodUnits die neue Anzahl der Futtereinheiten
     */
    public void setFoodUnits(int foodUnits) {
        if(foodUnits >= 0) {
            this.foodUnits = foodUnits;
        }
    }

    /**
     * Liefert die grafische Repräsentation des Futtereinheiten. Es
     * wird immer die gleiche {@link SimulationGraphic} Instanz zurueckgegeben.
     *
     * @return die grafische Repräsentation des Futtereinheiten, nie
     * {@code null}
     */
    public SimulationGraphic getSimulationGraphic() {
        return simulationGraphic;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRotation() {
        return rotation;
    }
}