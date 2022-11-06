package Cumulative_Tests;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
 
 /*
* CREDIT FOR THIS CODE @ https://jojozhuang.github.io/programming/running-junit-tests-in-command-line/
  */
public class TestRunner {
   public static void main(String[] args) {
      boolean flag1 = true;
      boolean flag2 = true;
      boolean flag3 = true;
      boolean flag4 = true;
      if (args.length > 0) {
         if (args[0].equals("1")) {
            flag2 = false;
            flag3 = false;
            flag4 = false;
         }
         if (args[0].equals("2")) {
            flag1=false;
            flag3 = false;
            flag4 = false;
         }
         if (args[0].equals("3")) {
            flag1=false;
            flag2 = false;
            flag4 = false;
         }
         if (args[0].equals("4")) {
            flag1= false;
            flag2 = false;
            flag3 = false;
         }
      }
      if (flag1) {
         Result result = JUnitCore.runClasses(boardTests.class);
      
         System.out.printf("Test ran: %s, Failed: %s%n",
               result.getRunCount(), result.getFailureCount());
 
         for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
         }
 
      // final result, pass or fail
         System.out.println("All test cases are passed? " + result.wasSuccessful());
      }


      if (flag2) {
         Result result2 = JUnitCore.runClasses(stateTests.class);
      
         System.out.printf("Test ran: %s, Failed: %s%n",
               result2.getRunCount(), result2.getFailureCount());
 
         for (Failure failure : result2.getFailures()) {
            System.out.println(failure.toString());
         }
 
         // final result, pass or fail
         System.out.println("All test cases are passed? " + result2.wasSuccessful());

      }

      if (flag3) {
         Result result3 = JUnitCore.runClasses(strategyTests.class);

         System.out.printf("Test ran: %s, Failed: %s%n",
                 result3.getRunCount(), result3.getFailureCount());

         for (Failure failure : result3.getFailures()) {
            System.out.println(failure.toString());
         }

         // final result, pass or fail
         System.out.println("All test cases are passed? " + result3.wasSuccessful());

      }

      if (flag4) {
         Result result4 = JUnitCore.runClasses(ProtocolTests.class);

         System.out.printf("Test ran: %s, Failed: %s%n",
                 result4.getRunCount(), result4.getFailureCount());

         for (Failure failure : result4.getFailures()) {
            System.out.println(failure.toString());
         }

         // final result, pass or fail
         System.out.println("All test cases are passed? " + result4.wasSuccessful());

      }

   }
}
