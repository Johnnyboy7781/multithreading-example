import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Multithreading {
    public static void main(String[] args) throws InterruptedException {

        // List of 350 pieces of data than need validation
        final List<Integer> importantDataList = new ArrayList<>();
        for (int i = 1; i <= 350; i++) {
            importantDataList.add(i);
        }

        final long start = System.currentTimeMillis();

        CountDownLatch completedThreadCounter = new CountDownLatch( importantDataList.size() );

        // Multi thread to validate all data in parallel
        importantDataList.forEach((importantData) -> new Thread(() -> {
            try {
                lengthyValidation(importantData);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                completedThreadCounter.countDown();
            }
        }).start());

        // Tell main thread to wait until all other threads are complete
        completedThreadCounter.await();

        System.out.println("Execution took about " + ( System.currentTimeMillis() - start ) + "ms");
    }

    public static void lengthyValidation(Integer data) throws Exception {
        Random random = new Random();
        int sleepLength = random.nextInt(500) + 500;

        // Uncomment this line to see how if one thread throws an exception,
        // the others will continue without issue, including the main thread
//        if (data == 1) {
//            throw new Exception("WHOOPS");
//        }

        // When this is logged, notice how there is no guarantee of order
        System.out.println("Num = " + data + " || Sleeping for " + sleepLength + "ms");

        // Tell thread to sleep between 500-1000ms to simulate lengthy validation
        try {
            Thread.sleep( sleepLength );
        } catch (Exception ignored) {}

    }
}
