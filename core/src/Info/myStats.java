package Info;

public class myStats {

    private static final myStats stats = new myStats();

    private static float currentSpeed = 28;
    private static float currentScore = 0;

    public static myStats getInstance() {
        return stats;
    }

    private myStats() {
    }

    public static float getCurrentSpeed() {
        return currentSpeed;
    }

    public static void setCurrentSpeed(float currentSpeed) {
        myStats.currentSpeed = currentSpeed;
    }

    public static float getCurrentScore() {
        return currentScore;
    }

    public static void setCurrentScore(float currentScore) {
        myStats.currentScore = currentScore;
    }
}
