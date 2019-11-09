package Info;

public class myStats {

    private static float currentSpeed = 6;

    private static int currentScore = 0;

    public static float getCurrentSpeed() {
        return currentSpeed;
    }

    public static void setCurrentSpeed(float currentSpeed) {
        myStats.currentSpeed = currentSpeed;
    }

    public static float getCurrentScore() {
        return currentScore;
    }

    public static void setCurrentScore(int currentScore) {
        myStats.currentScore = currentScore;
    }
}
