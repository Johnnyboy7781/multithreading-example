import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Multithreading {
    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>(Arrays.asList(new Integer[1_000]));

        long start = System.currentTimeMillis();

        CountDownLatch completedThreadCounter = new CountDownLatch(list.size());

        list.forEach((num) -> {
            new Thread(() -> lengthyValidation(num, completedThreadCounter)).start();
        });

        completedThreadCounter.await();

        System.out.println("Execution took about " + (System.currentTimeMillis() - start) + "ms");
    }

    public static void lengthyValidation(Integer num, CountDownLatch completedThreadCounter) {
        Random random = new Random();
        int currInt = random.nextInt(500) + 500;

        System.out.println("Num = " + num + " || Sleeping for " + currInt + "ms");
        try {
            Thread.sleep(currInt);
        } catch (Exception ignored) {}

        completedThreadCounter.countDown();
    }
}
