import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Multithreading {
    public static void main(String[] args) throws InterruptedException {

        final List<Integer> list = new ArrayList<>( Collections.nCopies(99,  0) );
        list.add(1);

        long start = System.currentTimeMillis();

        CountDownLatch completedThreadCounter = new CountDownLatch(list.size());

        list.forEach((num) -> {
            new Thread(() -> {
                try {
                    lengthyValidation(num, completedThreadCounter);
                } catch (Exception e) {
                    completedThreadCounter.countDown();
                    throw new RuntimeException(e);
                }
            }).start();
        });

        completedThreadCounter.await();

        System.out.println("Execution took about " + (System.currentTimeMillis() - start) + "ms");
    }

    public static void lengthyValidation(Integer num, CountDownLatch completedThreadCounter) throws Exception {
        Random random = new Random();
        int currInt = random.nextInt(500) + 500;

        if (num == 1) {
            throw new Exception("WHOOPS");
        }

        System.out.println("Num = " + num + " || Sleeping for " + currInt + "ms");
        try {
            Thread.sleep(currInt);
        } catch (Exception ignored) {}

        completedThreadCounter.countDown();
    }
}
