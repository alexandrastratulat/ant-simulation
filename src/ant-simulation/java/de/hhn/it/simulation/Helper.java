package de.hhn.it.simulation;

import java.util.Random;

/**
 * Klasse mit Hilfsmethoden zum erzeugen von Zufallszahlen und zum Umrechnen von
 * Grad in Bogenmass und umgekehrt. Für alle weiteren mathematischen Funktionen
 * siehe {@link Math}.
 */
public class Helper {
    private static final Random random = new Random();

    /**
     *
     * @param upperBound upperbound
     * @return Eine gleich verteilte Pseudozufallszahl zwischen 0 (inklusive)
     * und der gegebenen oberen Grenze (exclusive)
     * @throws IllegalArgumentException wenn bound nicht positiv ist
     */
    public static int randomInt(int upperBound) {
        return random.nextInt(upperBound);
    }

    /**
     *
     * @param upperBound upperbound
     * @return Eine gleich verteilte Pseudozufallszahl zwischen 0 (inklusive)
     * und der gegebenen oberen Grenze (exklusive)
     */
    public static double randomDouble(double upperBound) {
        return random.nextDouble() * upperBound;
    }

    /**
     *
     * @param bounds bounds
     * @return Eine gleich verteilte Pseudozufallszahl zwischen den gegebenen
     * Grenzen (inklusive)
     */
    public static double randomDoubleUpperLowerBound(double bounds) {
        return random.nextDouble() * 2 * bounds - bounds;
    }

    /**
     *
     * @param degree der zu transformierende Wert in Grad
     * @return den berechneten Wert in Bogenmass
     */
    public static double degreeToRadian(double degree) {
        return degree / 360 * 2 * Math.PI;
    }

    /**
     *
     * @param radian der zu transformierende Wert im Bogenmass
     * @return den berechneten Wert in Grad
     */
    public static double radianToDegree(double radian) {
        return radian / (2 * Math.PI) * 360;
    }
}
