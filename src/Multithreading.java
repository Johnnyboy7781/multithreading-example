import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Multithreading {
    public static void main(String[] args) throws InterruptedException {

        final List<Integer> importantDataList = new ArrayList<>( Collections.nCopies(350,  0) );
//        importantDataList.set(50, 1);

        final long start = System.currentTimeMillis();

        CountDownLatch completedThreadCounter = new CountDownLatch( importantDataList.size() );

        importantDataList.forEach((importantData) -> new Thread(() -> {
            try {
                lengthyValidation(importantData);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                completedThreadCounter.countDown();
            }
        }).start());

        completedThreadCounter.await();

        System.out.println("Execution took about " + ( System.currentTimeMillis() - start ) + "ms");
    }

    public static void lengthyValidation(Integer data) throws Exception {
        Random random = new Random();
        int sleepLength = random.nextInt(500) + 500;

//        if (data == 1) {
//            throw new Exception("WHOOPS");
//        }

        System.out.println("Num = " + data + " || Sleeping for " + sleepLength + "ms");

        try {
            Thread.sleep( sleepLength );
        } catch (Exception ignored) {}

    }
}
