public class Metrics {
    private long startTime;

    public void start() {
        startTime = System.nanoTime();
    }

    public long elapsedMillis() {
        return (System.nanoTime() - startTime) / 1_000_000;
    }
}
