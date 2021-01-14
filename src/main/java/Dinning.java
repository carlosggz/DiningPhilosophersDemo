import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Dinning {

    final int numberOfPhilosophers;
    final int seconds;
    private final AtomicBoolean cancellationToken;
    private final List<Fork> forks;
    private final List<Philosopher> philosophers;

    public Dinning(int numberOfPhilosophers, int seconds) {
        this.numberOfPhilosophers = numberOfPhilosophers;
        this.seconds = seconds;

        this.cancellationToken = new AtomicBoolean(false);
        this.forks = Stream.generate(Fork::new).limit(numberOfPhilosophers).collect(Collectors.toList());
        this.philosophers = IntStream
                .range(0, numberOfPhilosophers)
                .mapToObj(index -> {
                    var leftFork = forks.get(index);
                    var rightFork = forks.get((index+1)%numberOfPhilosophers);
                    return (index < numberOfPhilosophers - 1)
                            ? new Philosopher(index + 1, leftFork, rightFork, cancellationToken)
                            : new Philosopher(index + 1, rightFork, leftFork, cancellationToken); // The last philosopher picks up the right fork first
                })
                .collect(Collectors.toList());
    }

    public void start() throws InterruptedException {

        System.out.println("Starting dinner for " + numberOfPhilosophers + " during " + seconds + " seconds");

        cancellationToken.set(false);

        var executorService = Executors.newFixedThreadPool(numberOfPhilosophers);
        philosophers.forEach(executorService::submit);
        executorService.shutdown();
        executorService.awaitTermination(seconds, TimeUnit.SECONDS);

        cancellationToken.set(true);

        System.out.println("Finishing dinner...");

        while (!executorService.isTerminated()){
            Thread.sleep(1000);
        }

        System.out.println("Dinner finished");
    }

    public void showStats() {
        philosophers.forEach(x -> System.out.println("Philosopher " + x.getNumber() + " ate " + x.getEats() + " times"));
    }
}
