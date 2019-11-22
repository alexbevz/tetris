package GameClass;

enum CurrentTouch {

    EMPTY(0), LEFT(1), DOWN(2), RIGHT(3);

    CurrentTouch(final int currentTouch) {
        this.currentTouch = currentTouch;
    }

    private int currentTouch;

    public int getCurrentTouch() {
        return currentTouch;
    }

}
