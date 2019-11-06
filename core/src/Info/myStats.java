package Info;

public class myStats {

    private static final myStats stats = new myStats();

    private static float currentSpeed = 48;

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
}
