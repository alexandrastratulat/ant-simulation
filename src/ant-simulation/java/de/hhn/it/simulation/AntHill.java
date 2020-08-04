package de.hhn.it.simulation;

import de.hhn.it.ui.ImageFileGraphic;
import de.hhn.it.ui.SimulationGraphic;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Class {@link AntHill} creates anthills and creates new children ants
 * @author Alexandra-Valentina Stratulat
 * @version Aufgabe 2
 */

public class AntHill {

    private SimulationGraphic simulationGraphic;

    private int foodUnits;

    private String text;

    private double x, y, rotation;

    private Color color;

    public AntHill(double x, double y, double rotation, Color color) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.color = color;
        this.foodUnits = 0;
        this.text = "Ameisenhaufen";
        this.simulationGraphic = new ImageFileGraphic("ant-hill.png");
    }

    public Color getColor() {
        return color;
    }

    /**
     * create a new ANT
     * @param antList list
     */
    public void createChildren(List<Ant> antList){
        antList.add(new Ant(this.x, this.y,
                ThreadLocalRandom.current().nextDouble(0, 360)));
    }


    /**
     * Fuehrt einen Simulationsschritt durch.
     */
    public void doSimulationStep() {
    }


    /**
     * @return Beschreibungstext des Ameisenhaufens, kann {@code null} sein.
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
     * Liefert die grafische Repräsentation des AntHill. Es
     * wird immer die gleiche {@link SimulationGraphic} Instanz zurueckgegeben.
     *
     * @return die grafische Repräsentation des AntHill, nie
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