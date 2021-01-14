public class Main {
    static final int NUMBER_OF_PHILOSOPHERS = 5;
    static final int TIME_FOR_DINNER_SECONDS = 10;

    public static void main(String[] args) throws InterruptedException {
        var dinning = new Dinning(NUMBER_OF_PHILOSOPHERS, TIME_FOR_DINNER_SECONDS);
        dinning.start();
        dinning.showStats();
    }
}
