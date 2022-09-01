class PathIdNotFoundException extends Exception {       // 包含题目[1]

    private /*@ spec_public @*/ static int count = 0;
    private /*@ spec_public @*/ int id;

    //@ ensures count == \old(count) + 1 && id == pathId; //TODO
    PathIdNotFoundException(final int pathId) {
        count = count + 1;
        this.id = pathId;
    }

    public void print() {
        System.err.println("PathId : " + id + " Not Found!" +
                String.format(" (This exception has occurred %d times)", count));
    }
}
