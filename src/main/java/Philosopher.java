import java.util.concurrent.atomic.AtomicBoolean;

public class Philosopher implements Runnable{

    private final Fork leftFork;
    private final Fork rightFork;
    private final AtomicBoolean cancellationToken;
    private final int philosopherNumber;
    private int eats = 0;

    public Philosopher(int number, Fork leftFork, Fork rightFork, AtomicBoolean token) {
        this.philosopherNumber = number;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.cancellationToken = token;
    }

    @Override
    public void run() {
        eats = 0;

        try {
            while (!cancellationToken.get()) {
                doAction("Thinking");
                synchronized (leftFork) {
                    doAction("Picked up left fork");
                    synchronized (rightFork) {
                        doAction("Picked up right fork");
                        doAction("Eating");
                        eats++;
                        doAction("Put down right fork");
                    }
                    doAction("Put down left fork");
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            doAction("Leaving");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getEats() {
        return eats;
    }

    public int getNumber() {
        return philosopherNumber;
    }

    private void doAction(String action) throws InterruptedException {
        System.out.println("Philosopher " + this.philosopherNumber + ": " + action);
        Thread.sleep(((int) (Math.random() * 100)));
    }
}