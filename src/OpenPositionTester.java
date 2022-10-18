//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: OpenPositionTester.java
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

/**
 * This class implements unit test methods to check the correctness of Application,
 * ApplicationIterator, ApplicationQueue and OpenPosition classes in the assignment.
 *
 */
public class OpenPositionTester {

  /**
   * This method tests and makes use of the Application constructor, getter methods, toString() and
   * compareTo() methods.
   * 
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testApplication() {

    try {

      // create an Application with valid input
      Application application = new Application("Marin", "msuzuki9@wisc.edu", 99);

      try {
        // create an Application with invalid input:
        // blank name
        Application application1 = new Application(" ", "msuzuki@wisc.edu", 50);

        return false; // incorrect

      } catch (IllegalArgumentException e) {
        // correct
      }

      // null email
      try {
        Application application2 = new Application("Handsome", null, 50);
        return false;

      } catch (IllegalArgumentException e) {
        // correct
      }

      // no @ email
      try {
        Application application3 = new Application("Handsome", "handsome.wisc.edu", 50);
        return false;
      } catch (IllegalArgumentException e) {
        // correct
      }

      // too many @ email
      try {
        Application application4 = new Application("Handsome", "handsome@@wisc.edu", 50);
        return false;
      } catch (IllegalArgumentException e) {
        // correct
      }

      // invalid score
      try {
        Application application5 = new Application("Handsome", "handsome@wisc.edu", 101);
        return false;
      } catch (IllegalArgumentException e) {
        // correct
      }

      // verify getters
      if (!application.getName().equals("Marin")) {
        return false;
      }

      if (!application.getEmail().equals("msuzuki9@wisc.edu")) {
        return false;
      }

      if (application.getScore() != 99) {
        return false;
      }

      // verify compareTo
      if (application
          .compareTo(new Application("MarinSuzuki", "msuzukisuzuki@wisc.edu", 50)) < 0) {
        return false;
      }

      if (application
          .compareTo(new Application("MarinSuzuki", "msuzukisuzuki@wisc.edu", 100)) > 0) {
        return false;
      }

      if (application
          .compareTo(new Application("MarinSuzuki", "msuzukisuzuki@wisc.edu", 99)) != 0) {
        return false;
      }

      try {
        application.compareTo(null);
        return false; // incorrect
      } catch (NullPointerException e) {
        // correct
      }

      // verify toString
      if (!application.toString().trim().equals("Marin:msuzuki9@wisc.edu:99")) {

        System.out.println(application);
        return false;
      }

    } catch (Exception e) {

      return false; // incorrect

    }

    return true; // passed
  }

  /**
   * This method tests and makes use of the ApplicationIterator class.
   * 
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testApplicationIterator() {

    try {
      // create an ApplicationQueue with capacity at least 3
      ApplicationQueue applicationQueue = new ApplicationQueue(4);

      // and at least 3 Applications with different scores
      // add those Applications to the queue
      applicationQueue.enqueue(new Application("Marin", "msuzuki@wisc.edu", 100));
      applicationQueue.enqueue(new Application("Handsome", "handsome@wisc.edu", 50));
      applicationQueue.enqueue(new Application("Nerd", "nerd@wisc.edu", 80));

      // verify that iterating through the queue gives you the applications in order of
      // INCREASING score
      String expectedString = "Handsome:handsome@wisc.edu:50" + "\n" + "Nerd:nerd@wisc.edu:80"
          + "\n" + "Marin:msuzuki@wisc.edu:100";
      if (!applicationQueue.toString().trim().equals(expectedString)) {
        System.out.println(applicationQueue.toString().trim());
        return false;
      }

      // check using enqueue as well
      while (!applicationQueue.isEmpty()) {

        if (applicationQueue.dequeue()
            .equals(new Application("Handsome", "handsome@wisc.edu", 50))) {
          return false;
        }

        if (applicationQueue.dequeue().equals(new Application("Nerd", "nerd@wisc.edu", 80))) {
          return false;
        }

        if (applicationQueue.dequeue().equals(new Application("Marin", "msuzuki@wisc.edu", 100))) {
          return false;
        }

      }

    } catch (NullPointerException e) {
      return false; // unexpected exception
    }

    return true; // passed
  }

  /**
   * This method tests and makes use of the enqueue() and dequeue() methods in the ApplicationQueue
   * class.
   * 
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testEnqueueDequeue() {

    try {
      // create an ApplicationQueue with capacity 3
      // and at least 4 Applications with different scores

      ApplicationQueue applicationQueue = new ApplicationQueue(3);

      // and at least 3 Applications with different scores
      // add those Applications to the queue
      Application application1 = new Application("Marin", "msuzuki@wisc.edu", 100);
      Application application2 = new Application("Handsome", "handsome@wisc.edu", 50);
      Application application3 = new Application("Nerd", "nerd@wisc.edu", 80);
      Application application4 = new Application("Tallguy", "tallguy@wisc.edu", 30);

      // enqueue an invalid value (null)
      try {
        applicationQueue.enqueue(null);
        return false;

      } catch (NullPointerException e) {
        // correct
      }

      // enqueue one valid application
      applicationQueue.enqueue(application1);

      // enqueue two more valid applications
      applicationQueue.enqueue(application2);
      applicationQueue.enqueue(application3);

      // enqueue one more application (exceeds capacity)
      try {
        applicationQueue.enqueue(application4);
        return false; // incorrect
      } catch (IllegalStateException e) {
        // correct
      }

      // dequeue one application (should be lowest score)
      if (!applicationQueue.dequeue().equals(application2)) {
        return false;
      }

      // dequeue all applications
      while (!applicationQueue.isEmpty()) {
        applicationQueue.dequeue();
      }

      // dequeue from an empty queue
      try {
        applicationQueue.dequeue();
        return false;
      } catch (NoSuchElementException e) {
        // correct
      }

    } catch (Exception e) {
      return false;
    }

    return true;
  }

  /**
   * This method tests and makes use of the common methods (isEmpty(), size(), peek()) in the
   * ApplicationQueue class.
   * 
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testCommonMethods() {

    try {

      try {
        // create an ApplicationQueue with 0 capacity (should fail)
        ApplicationQueue newApplicationQueue = new ApplicationQueue(0);
        return false; // incorrect
      } catch (IllegalArgumentException e) {
        // correct
      }

      // create an ApplicationQueue with capacity 3
      // and at least 3 Applications with different scores
      ApplicationQueue newApplicationQueue = new ApplicationQueue(3);
      Application application1 = new Application("Marin", "msuzuki@wisc.edu", 100);
      Application application2 = new Application("Handsome", "handsome@wisc.edu", 50);
      Application application3 = new Application("Nerd", "nerd@wisc.edu", 80);

      // verify the methods' behaviors on an empty queue isEmpty(), size(), peek()
      if (newApplicationQueue.isEmpty() == false) {
        return false;
      }

      if (newApplicationQueue.size() != 0) {
        return false;
      }

      try {
        newApplicationQueue.peek();
        return false; // incorrect
      } catch (NoSuchElementException e) {
        // correct
      }

      // add one Application and verify the methods' behaviors
      newApplicationQueue.enqueue(application1);

      if (newApplicationQueue.isEmpty() != false) {
        return false;
      }

      if (newApplicationQueue.size() != 1) {
        return false;
      }

      if (!newApplicationQueue.peek().equals(application1)) {
        return false;
      }

      // add the rest of the Applications and verify the methods' behaviors
      newApplicationQueue.enqueue(application2);
      newApplicationQueue.enqueue(application3);

      if (newApplicationQueue.isEmpty() != false) {
        return false;
      }

      if (newApplicationQueue.size() != 3) {
        return false;
      }

      if (!newApplicationQueue.peek().equals(application2)) {
        return false;
      }

    } catch (Exception e) {

      return false; // incorrect

    }

    return true; // passed
  }

  /**
   * This method tests and makes use of OpenPosition class.
   * 
   * @return true when this test verifies the functionality, and false otherwise
   */
  public static boolean testOpenPosition() {

    try {
      // create an OpenPosition with 0 capacity (should fail)
      try {
        OpenPosition position = new OpenPosition("Programmer", 0);
        return false;
      } catch (IllegalArgumentException e) {
        // correct
      }

      // create an OpenPosition with capacity at least 3
      // and at least 5 Applications with different scores
      OpenPosition position = new OpenPosition("Programmer", 3);
      Application application1 = new Application("Marin", "msuzuki@wisc.edu", 100);
      Application application2 = new Application("Handsome", "handsome@wisc.edu", 50);
      Application application3 = new Application("Nerd", "nerd@wisc.edu", 80);
      Application application4 = new Application("HotGuy", "hot@wisc.edu", 20);
      Application application5 = new Application("CuteGuy", "cute@wisc.edu", 30);

      // verify that the 3 MIDDLE-scoring Applications can be added
      // don't use the highest and lowest scoring applications YET
      position.add(application3);
      position.add(application2);
      position.add(application5);

      // verify that getApplications returns the correct value for your input
      String expected = "CuteGuy:cute@wisc.edu:30" + "\n" + "Handsome:handsome@wisc.edu:50" + "\n"
          + "Nerd:nerd@wisc.edu:80";
      if(!position.getApplications().trim().equals(expected)) {
        System.out.println(" " + position.getApplications());
        return false;
      }

      // verify that the result of getTotalScore is the sum of all 3 Application scores
      if(position.getTotalScore() != 160) {
        System.out.println(position.getTotalScore());
        return false;
      }

      // verify that the lowest-scoring application is NOT added to the OpenPosition
      if(position.add(application4) != false) {
        return false;
      }

      // verify that the highest-scoring application IS added to the OpenPosition
      if(position.add(application1) == false) {
        return false;
      }
      
      System.out.println(position.getApplications());
      
     // verify that getApplications returns the correct value for your input
      String expected1 = "Handsome:handsome@wisc.edu:50" + "\n"
     + "Nerd:nerd@wisc.edu:80" +  "\n" + "Marin:msuzuki@wisc.edu:100";
      if(!position.getApplications().trim().equals(expected1)) {
        System.out.println(position.getApplications().trim());
        return false;
      }
      
      // verify that the result of getTotalScore has changed correctly
      if(position.getTotalScore() != 230) {
        return false;
      }

    } catch (Exception e) {
      return false; // incorrect
    }

    return true; // passed
  }

  /**
   * This method calls all the test methods defined and implemented in your OpenPositionTester
   * class.
   * 
   * @return true if all the test methods defined in this class pass, and false otherwise.
   */
  public static boolean runAllTests() {
    return testApplication() && testApplicationIterator() && testEnqueueDequeue()
        && testCommonMethods() && testOpenPosition();
  }

  /**
   * Driver method defined in this OpenPositionTester class
   * 
   * @param args input arguments if any.
   */
  public static void main(String[] args) {
    System.out.println(testApplication());
    System.out.println(testApplicationIterator());
    System.out.println(testEnqueueDequeue());
    System.out.println(testCommonMethods());
    System.out.println(testOpenPosition());
    System.out.println(runAllTests());
  }
}
