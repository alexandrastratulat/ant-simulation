package de.hhn.it.simulation;

import de.hhn.it.ui.AntModelGraphic;

import java.util.concurrent.ThreadLocalRandom;

import static de.hhn.it.simulation.Helper.*;

/**
 * Mit der Klasse {@link Ant} werden Ameisen in der Simulation funktional
 * abgebildet. Ameisen definieren ihre Position und Rotation (in Grad).
 *
 * @see AntModelGraphic
 * @author Alexandra-Valentina Stratulat
 * @version Aufgabe 2
 */
public class Ant {
    private AntModelGraphic graphic;

    private String antText;

    private double x, y, rotation;

    private boolean isCarryingFood = false,
                    goRandom = true,
                    goingToHill = false,
                    visibility = true;

    private Food food;

    private AntHill homeAntHill;

    public Ant(double x, double y, double rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.antText = "";
        this.graphic = new AntModelGraphic();
    }

    public boolean getVisibility(){
        return this.visibility;
    }

    public void setVisibility(boolean visibility){
        this.visibility = visibility;
    }


    /**
     *
     * @param graphic graphic of the ant
     * change the color of the ant
     */
    public void setGraphic(AntModelGraphic graphic) {
        this.graphic = graphic;
    }


    /**
     *
     * @param width of the screen
     * @param height of the screen
     * Führt einen Simulationsschritt durch und lässt die Ameise jeweils einen
     * Schritt nach rechts machen.
     */
    public void doSimulationStep(double width, double height) {

        if (y <= 10 || y >= height) {
            this.rotation =-this.rotation;
        } else if (x <= 10 || x >= width) {
            this.rotation = 180 - this.rotation;
        }
        if(goRandom && !goingToHill){
            this.rotation = ThreadLocalRandom.current().nextDouble(this.rotation - 5, this.rotation + 5);
        }

        double angle = degreeToRadian(360 - this.rotation);
        x += Math.cos(angle) * 5;
        y += Math.sin(angle) * 5;

        //make new ant when it goes on the right side
//        if(x >= 910){
//            x = 100;
//            y = 100;
    }


    /**
     * @return {@code true} wenn diese Ameise Nahrung transportiert.
     */
    public boolean isCarryingFood() {
        return this.isCarryingFood;
    }

    /**
     * the ant gets food from the food pile
     * then the ant is carrying food
     */
    public void getsFood(){
        this.isCarryingFood = true;
    }

    /**
     * the ant puts food in the anthill
     * then the ant is not carrying food
     */
    public void putsFood(){
        this.isCarryingFood = false;
    }

    /**
     *
     * @param food
     * the ant will remember where the food is located
     */
    public void rememberFood(Food food){
        this.food = food;
    }

    /**
     *
     * @return the location of the food
     */
    public Food getFood(){
        return this.food;
    }

    /**
     *
     * @param antHill
     * the ant will remember where the home anthill is located
     */
    public void rememberAntHill(AntHill antHill){
        this.homeAntHill = antHill;
    }

    /**
     *
     * @return the location of the home anthill
     */
    public AntHill getAntHill(){
        return this.homeAntHill;
    }

    /**
     *
     * @param goRandom ant goes random(true or false)
     * ant is going in a random direction
     */
    public void setGoRandom(boolean goRandom) {
        this.goRandom = goRandom;
    }

    /**
     *
     * @param x x value
     * @param y y value
     * @return true if teh ant is near an object
     * like ant, food, hill
     */
    public boolean isNearObject(double x, double y){
        return this.x >= (x - 20) && this.x <= (x + 20) &&
                this.y >= (y - 20) && this.y <= (y + 20);
    }

    /**
     *
     * @param x value
     * @param y value
     * ant goes to a given direction,
     * like food, hill
     */
    public void goToDirection(double x, double y){
        if(this.x > x){
            this.rotation = 180 - radianToDegree(Math.atan((y - this.y)/(x - this.x)));
        }
        else{
            this.rotation = 360 - radianToDegree(Math.atan((y - this.y)/(x - this.x)));
        }
    }

    /**
     * Führt einen Simulationsschritt durch und lässt die Ameise jeweils eine
     * Methode damit die Ameise auf der Diagonale gehen kann.
     */
    public void doSimulationStepDiagonal(){
        double angle = 122.5;
        double angleInRadians = degreeToRadian(angle);
        x += Math.sin(angleInRadians);
        y += Math.cos(angleInRadians);
    }

    /**
     *Führt einen Simulationsschritt durch und lässt die Ameise jeweils eine
     * Schritt random machen.
     */
    public void doSimulationStepRandom(){
        double angle = randomDoubleUpperLowerBound(30);
        double angleInRadians = degreeToRadian(angle);
        x += Math.sin(angleInRadians);
        y += Math.cos(angleInRadians);
    }

    public void setGoingToHill(boolean goingToHill) {
        this.goingToHill = goingToHill;
    }

    public double getRotation() {
        return rotation;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Liefert die grafische Repräsentation der Ameise. Es wird immer die
     * gleiche {@link AntModelGraphic} Instanz zurückgegeben.
     *
     * @return die grafische Repräsentation dieser Ameise, nie {@code null}
     */
    public AntModelGraphic getSimulationGraphic() {
        return graphic;
    }

    /**
     * @return Beschreibungstext, kann {@code null} sein.
     */
    public String getText() {
        // Frei nach Joachim Rigelnatz: "Die Ameisen"
        return this.antText;
    }

    public void setAntText(String antText){
        this.antText = antText;
    }
}