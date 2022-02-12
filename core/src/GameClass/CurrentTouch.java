package GameClass;

enum CurrentTouch {

    EMPTY(0), LEFT(1), DOWN(2), RIGHT(3);

    private int currentTouch;

    CurrentTouch(final int currentTouch) {
        this.currentTouch = currentTouch;
    }

    public int getCurrentTouch() {
        return currentTouch;
    }

}
