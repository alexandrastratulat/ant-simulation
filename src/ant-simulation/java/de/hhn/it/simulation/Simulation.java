package de.hhn.it.simulation;

import de.hhn.it.ui.AntApplication;
import de.hhn.it.ui.AntModelGraphic;
import de.hhn.it.ui.UiManager;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Dies Klasse bildet das zentrale Element der Simulation. Sie koordiniert die
 * Bewegungen, das Erscheinen und Verschwinden der Simulationsteilnehmer. Die
 * Methode {@link Simulation#doSimulationStep()} wird vom Taktgeber der
 * grafischen Oberfläche in regelmäßigen Abständen aufgerufen.
 *
 * @see AntApplication#SIMULATION_FRAME_LENGTH
 * @author Alexandra-Valentina Stratulat
 * @version Aufgabe 2
 */
public class Simulation {

    private List<Ant> antList= new ArrayList<>();
    private int totalAnts = 20;

    private List<AntHill> antHillList = new ArrayList<>();
    private int totalAntHills = 2;

    private List<Food> foodList = new ArrayList<>();
    private int totalFood;
    private int foodTotal = 2;

    private List<NaturalEnemy> naturalEnemyList = new ArrayList<>();
    private int totalNaturalEnemy = 2;

    private final double width;
    private final double height;

    private UiManager uiManager;

    private Timer timer;

    public Simulation(UiManager uiManager) {

        this.uiManager = uiManager;

    // width and height of the screen
        width = uiManager.getSimulationSurfaceWidth();
        height = uiManager.getSimulationSurfaceHeight();

    //create ant hills and ants
        createAntAndAntHill();
        for (AntHill antHill : antHillList) {
            uiManager.add(antHill);
        }
        for (Ant ant : antList) {
            uiManager.add(ant);
        }

    //create food
        createFood();
        for (Food food : foodList) {
            uiManager.add(food);
        }

    //create enemy
        createNaturalEnemy();
        for (NaturalEnemy naturalEnemy : naturalEnemyList) {
            uiManager.add(naturalEnemy);
        }


    //create timer to run every 10 seconds
        timer = new Timer();
        timer.schedule(new Run(), 10000, 10000);
    }

    /**
     * add anthills and ants
     */
    private void createAntAndAntHill(){
        for(int i = 0; i < totalAntHills; i++) {
            Random random = new Random();
            int r = random.nextInt(255);
            int g = random.nextInt(255);
            int b = random.nextInt(255);
            antHillList.add(new AntHill(ThreadLocalRandom.current().nextDouble(50, width-50),
                    ThreadLocalRandom.current().nextDouble(50, height-50), 0, Color.rgb(r, g, b)));
            antHillList.get(i).setFoodUnits(0);
            int antListSize = antList.size();
            for (int j = antListSize; j < (totalAnts + antListSize); j++) {
                antList.add(new Ant(ThreadLocalRandom.current().nextDouble(0, width - 25),
                        ThreadLocalRandom.current().nextDouble(0, height - 25),
                        ThreadLocalRandom.current().nextDouble(0, 360)));
                antList.get(j).rememberAntHill(antHillList.get(i));
                Color antColor = antHillList.get(i).getColor();
                antList.get(j).setGraphic(new AntModelGraphic(antColor,antColor,antColor,antColor,antColor,antColor));
            }
        }
    }

    /**
     * add food and set the food units
     */
    private void createFood(){
        for(int i = 0; i < foodTotal; i++){
            foodList.add(new Food(ThreadLocalRandom.current().nextDouble(0, width-25),
                    ThreadLocalRandom.current().nextDouble(0, height-25),
                    ThreadLocalRandom.current().nextDouble(0, 360)));
            foodList.get(i).setFoodUnits(150);
        }
    }

    private void createNaturalEnemy(){
        for(int i = 0; i < totalNaturalEnemy; i++){
            naturalEnemyList.add(new NaturalEnemy(ThreadLocalRandom.current().nextDouble(0, width-25),
                    ThreadLocalRandom.current().nextDouble(0, height-25),
                    ThreadLocalRandom.current().nextDouble(0, 360)));
        }

    }

    /**
     * class that extends timer task
     * create a new ant every 10 seconds
     */
    class Run extends TimerTask{
        public void run(){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        for (AntHill antHill : antHillList) {
                            totalFood = antHill.getFoodUnits();
                            if (totalFood / 10 > 1) {
                                int randomAnt = ThreadLocalRandom.current().nextInt(1, totalFood / 10);
                                int antListSize = antList.size();
                                for (int i = antListSize; i < antListSize + randomAnt; i++) {
                                    antHill.createChildren(antList);
                                    antList.get(i).rememberAntHill(antHill);
                                    Color antColor = antHill.getColor();
                                    antList.get(i).setGraphic(new AntModelGraphic(antColor, antColor, antColor, antColor, antColor, antColor));
                                    uiManager.add(antList.get(i));
                                }
                            }
                        }
                        if(foodTotal == 0){
                            timer.cancel();
                        }
//                        for (NaturalEnemy naturalEnemy: naturalEnemyList) {
//                            for (Food food: foodList) {
//
//                            }
//                        }
                    }
                });
        }
    }

    /**
     *
     * @param food
     * @param ant
     *  this method checks if any ant is near a food pile,
     *  the ant doesn't carry food and the food pile has food units
     *  then the ant takes food and goes to the hill
     *  if the food pile remains with 0 food units, then it disappears and
     *  the ants won't go to it anymore.
     */
    private void checkFood(Food food, Ant ant){
        if(ant.isNearObject(food.getX(), food.getY()) && !ant.isCarryingFood() && food.getFoodUnits() > 0){
            ant.getsFood();
            ant.rememberFood(food);
            food.setFoodUnits(food.getFoodUnits() - 1);
            ant.setGoRandom(false);
            ant.goToDirection(ant.getAntHill().getX(), ant.getAntHill().getY());
            ant.setGoingToHill(true);
        }
        if(food.getFoodUnits() == 0 && food.getVisibility()){
            for (Ant foodAnt: antList) {
                if (foodAnt.getFood() == food) {
                    foodAnt.setGoRandom(true);
                }
            }
            foodTotal--;
            food.setVisibility(false);
            uiManager.remove(food);
        }
    }

    /**
     *
     * @param ant
     * @param anotherAnt
     * if an ant is carrying food and meets another ant that doesn't
     * carry food, it directs it to the food pile
     */
    private void directAntToFood(Ant ant, Ant anotherAnt){
        if((ant != anotherAnt) && (ant.getAntHill() == anotherAnt.getAntHill()) && ant.isCarryingFood() &&
            !anotherAnt.isCarryingFood() &&
                ant.isNearObject(anotherAnt.getX(), anotherAnt.getY()) &&
                (ant.getFood().getFoodUnits() > 0)){
                        anotherAnt.setGoRandom(false);
                        anotherAnt.rememberFood(ant.getFood());
                        anotherAnt.goToDirection(ant.getFood().getX(), ant.getFood().getY());
        }
    }

    /**
     *
     * @param ant
     * when the ant arrives to the hill with food,
     * the ant puts the food down and goes back
     * to the food pile
     */
    private void putsFoodDown(Ant ant){
        if(ant.isNearObject(ant.getAntHill().getX(), ant.getAntHill().getY()) && ant.isCarryingFood()){
            ant.setGoingToHill(false);
            ant.getAntHill().setFoodUnits(ant.getAntHill().getFoodUnits() + 1);
            ant.putsFood();
            if(ant.getFood().getFoodUnits() > 0){
                ant.setGoRandom(false);
                ant.goToDirection(ant.getFood().getX(), ant.getFood().getY());
            }
        }
    }

    private void eatAnts(NaturalEnemy naturalEnemy, Ant ant){
        if(naturalEnemy.isNearObject(ant.getX(), ant.getY()) && naturalEnemy.getIsHungry() && naturalEnemy.getEatenAnts() <= 10){
            naturalEnemy.setEatenAnts(naturalEnemy.getEatenAnts() + 1);
            totalAnts--;
            ant.setVisibility(false);
            uiManager.remove(ant);
            //antList.remove(ant);
        }
        if(naturalEnemy.getEatenAnts() == 10){
            naturalEnemy.setIsHungry(false);
            naturalEnemy.numberSteps();
        }
    }

    /**
     * Führt einen Simulationsschritt durch.
     */
    public void doSimulationStep() {

        for (Ant ant: antList) {
            ant.doSimulationStep(width, height);
            if(foodTotal > 0)
                for (Food food: foodList) {
                    checkFood(food, ant);
                }
            putsFoodDown(ant);
            for (Ant anotherAnt: antList) {
                directAntToFood(ant, anotherAnt);
            }
        }
        for (NaturalEnemy naturalEnemy: naturalEnemyList) {
            naturalEnemy.doSimulationStep(width, height);
            if(totalAnts > 0)
                for (Ant ant: antList) {
                    eatAnts(naturalEnemy, ant);
                }
        }


    }
}
