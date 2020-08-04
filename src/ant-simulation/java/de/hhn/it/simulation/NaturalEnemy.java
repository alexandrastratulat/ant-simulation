package de.hhn.it.simulation;

import de.hhn.it.ui.ImageFileGraphic;
import de.hhn.it.ui.SimulationGraphic;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static de.hhn.it.simulation.Helper.degreeToRadian;

/**
 * Class {@link NaturalEnemy} create a bug which is the enemy of an ant
 * @author Alexandra-Valentina Stratulat
 * @version Aufgabe 3
 */
public class NaturalEnemy extends SimulationMember {

    private boolean isHungry = true;

    private int eatenAnts = 0;

    private String text;

    public NaturalEnemy(double x, double y, double rotation) {
        super(x, y, rotation, new ImageFileGraphic("bug.png"));
        this.text = "I ate " + this.eatenAnts + " ants";
    }

    public void doSimulationStep(double width, double height) {

        if (y <= 10 || y >= height) {
            this.rotation =-this.rotation;
        } else if (x <= 10 || x >= width) {
            this.rotation = 180 - this.rotation;
        }

        this.rotation = ThreadLocalRandom.current().nextDouble(this.rotation - 5, this.rotation + 5);
        double angle = degreeToRadian(360 - this.rotation);
        x += Math.cos(angle) * 5;
        y += Math.sin(angle) * 5;
    }

    public boolean getIsHungry(){
        return this.isHungry;
    }

    public void setIsHungry(boolean hunger){
        this.isHungry = hunger;
    }

    public boolean isNearObject(double x, double y){
        return this.x >= (x - 20) && this.x <= (x + 20) &&
                this.y >= (y - 20) && this.y <= (y + 20);
    }

    public int getEatenAnts(){
        return eatenAnts;
    }

    public void setEatenAnts(int eatenAnts){
        this.eatenAnts = eatenAnts;
    }

    public void numberSteps(){
        int steps =0;
        if(!isHungry)
            do{
                steps++;
            }while(steps == 250);
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String getText() {
        return text;
    }
}
