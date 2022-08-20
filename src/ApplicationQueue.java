//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: ApplicationQueue.java
// Course: CS 300 Spring 2022
//
// Author: Marin Suzuki
// Email: msuzuki9@wisc.edu
// Lecturer: Hobbes LeGault
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: None
// Online Sources: None
//
///////////////////////////////////////////////////////////////////////////////

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Array-based heap implementation of a priority queue containing Applications. Guarantees the
 * min-heap invariant, so that the Application at the root should have the lowest score, and
 * children always have a higher or equal score as their parent. The root of a non-empty queue is
 * always at index 0 of this array-heap.
 */
public class ApplicationQueue implements PriorityQueueADT<Application>, Iterable<Application> {
  private Application[] queue; // array min-heap of applications representing this priority queue
  private int size; // size of this priority queue

  /**
   * Creates a new empty ApplicationQueue with the given capacity
   * 
   * @param capacity Capacity of this ApplicationQueue
   * @throws IllegalArgumentException with a descriptive error message if the capacity is not a
   *                                  positive integer
   */
  public ApplicationQueue(int capacity) {

    // check capacity
    if (capacity <= 0) {
      throw new IllegalArgumentException("capacity is invalid.");
    }

    // create new queue
    queue = new Application[capacity];
    
  }

  /**
   * Checks whether this ApplicationQueue is empty
   * 
   * @return {@code true} if this ApplicationQueue is empty
   */
  @Override
  public boolean isEmpty() {

    if (this.size == 0) {
      return true; // empty
    }

    return false; // else there is an element
  }

  /**
   * Returns the size of this ApplicationQueue
   * 
   * @return the size of this ApplicationQueue
   */
  @Override
  public int size() {
    return this.size;
  }

  /**
   * Adds the given Application to this ApplicationQueue and use the percolateUp() method to
   * maintain min-heap invariant of ApplicationQueue. Application should be compared using the
   * Application.compareTo() method.
   * 
   * 
   * @param o Application to add to this ApplicationQueue
   * @throws NullPointerException  if the given Application is null
   * @throws IllegalStateException with a descriptive error message if this ApplicationQueue is full
   */
  @Override
  public void enqueue(Application o) {

    // verify the application
    if (o == null) {
      throw new NullPointerException("the given Application is null");
    }

    // verify that the queue is not full
    if (this.size == this.queue.length) {
      throw new IllegalStateException("queue is full");
    }
    
    
    // if allowed, add the application to the queue and percolate to restore the heap condition
    this.queue[size] = o; // add
    this.size = this.size + 1; // size changed since it is array, we should do manually
    percolateUp(size-1);
    
  }

  /**
   * Removes and returns the Application at the root of this ApplicationQueue, i.e. the Application
   * with the lowest score.
   * 
   * @return the Application in this ApplicationQueue with the smallest score
   * @throws NoSuchElementException with a descriptive error message if this ApplicationQueue is
   *                                empty
   */
  @Override
  public Application dequeue() {

    // verify that the queue is not empty
    if (this.isEmpty()) {
      throw new NoSuchElementException("queue is empty");
    }

    // save the lowest-scoring application
    Application temp = this.peek();

    // replace the root of the heap and percolate to restore the heap condition
    this.queue[0] = this.queue[size - 1];
    this.queue[size - 1] = null;
    size = size - 1; // size changed

    if (size != 0) {
      percolateDown(0); // percolate down of the root node
    }

    // return the lowest-scoring application
    return temp;

  }

  /**
   * An implementation of percolateDown() method. Restores the min-heap invariant of a given subtree
   * by percolating its root down the tree. If the element at the given index does not violate the
   * min-heap invariant (it is due before its children), then this method does not modify the heap.
   * Otherwise, if there is a heap violation, then swap the element with the correct child and
   * continue percolating the element down the heap.
   * 
   * This method may be implemented recursively OR iteratively.
   * 
   * @param i index of the element in the heap to percolate downwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  private void percolateDown(int i) {

    // check if the index is valid
    if (i > this.size - 1 || i < 0) {

      throw new IndexOutOfBoundsException("index is invalid");

    }

    // Base case 1: there is no child
    if ((i * 2 + 1) > this.size - 1) {

      // do not modify

    }

    // Base case 2: there is only left child
    if ((i * 2 + 1) <= this.size - 1 && (i * 2 + 2) > this.size - 1) {

      // if the left child is smaller than the parent
      if (this.queue[(i * 2 + 1)].getScore() < queue[i].getScore()) {

        // swap
        Application temp = queue[(i * 2 + 1)]; // store

        queue[(i * 2 + 1)] = queue[i]; // swap
        queue[i] = temp;

        // no more child and do not have to swap anymore
      }

    }

    // Base case 3: there is two children
    Application lowestApplication;

    if ((i * 2 + 1) <= this.size - 1 && (i * 2 + 2) <= this.size - 1) {

      // get the application which has the lowest score among two children
      if (queue[(i * 2 + 1)].getScore() >= queue[i * 2 + 2].getScore()) {

        lowestApplication = queue[(i * 2 + 2)]; // right is lower

        // check if this child is lower than parent
        if (queue[(i * 2 + 2)].getScore() < queue[i].getScore()) {

          queue[(i * 2 + 2)] = queue[i]; // swap
          queue[i] = lowestApplication;

          // percolate process again
          percolateDown(i * 2 + 2);

        }

      } else {

        lowestApplication = queue[(i * 2 + 1)]; // left is lower

        // check if this child is lower than parent
        if (queue[(i * 2 + 1)].getScore() < queue[i].getScore()) {
          queue[(i * 2 + 1)] = queue[i]; // swap
          queue[i] = lowestApplication;

          // percolate process again
          percolateDown(i * 2 + 1);
        }

      }

    }

  }

  /**
   * An implementation of percolateUp() method. Restores the min-heap invariant of the tree by
   * percolating a leaf up the tree. If the element at the given index does not violate the min-heap
   * invariant (it occurs after its parent), then this method does not modify the heap. Otherwise,
   * if there is a heap violation, swap the element with its parent and continue percolating the
   * element up the heap.
   * 
   * This method may be implemented recursively OR iteratively.
   * 
   * Feel free to add private helper methods if you need them.
   * 
   * @param i index of the element in the heap to percolate upwards
   * @throws IndexOutOfBoundsException if index is out of bounds - do not catch the exception
   */
  private void percolateUp(int i) {

    // check if the index is valid
    if (i > this.size - 1 || i < 0) {

      throw new IndexOutOfBoundsException("index is invalid");

    }

    // Base case1: child index is now index of root
    if (i == 0) {

      // stop, do not do anything

    }

    // index is not in the root yet
    if (i != 0) {

      // compare with parent node
      if (this.queue[i].compareTo(this.queue[(i - 1) / 2]) < 0) { // child is lower

        Application temp = this.queue[(i - 1) / 2]; // save and swap
        this.queue[(i - 1) / 2] = this.queue[i];
        this.queue[i] = temp;
        percolateUp((i - 1) / 2);
      }

    }

  }

  /**
   * Returns the Application at the root of this ApplicationQueue, i.e. the Application with the
   * lowest score.
   * 
   * @return the Application in this ApplicationQueue with the smallest score
   * @throws NoSuchElementException if this ApplicationQueue is empty
   */
  @Override
  public Application peek() {
    // verify that the queue is not empty
    if (this.isEmpty()) {
      throw new NoSuchElementException("ApplicationQueue is empty");
    }

    // return the lowest-scoring application
    return this.queue[0];

  }

  /**
   * Returns a deep copy of this ApplicationQueue containing all of its elements in the same order.
   * This method does not return the deepest copy, meaning that you do not need to duplicate
   * applications. Only the instance of the heap (including the array and its size) will be
   * duplicated.
   * 
   * @return a deep copy of this ApplicationQueue. The returned new application queue has the same
   *         length and size as this queue.
   */
  public ApplicationQueue deepCopy() {

    ApplicationQueue newApplication = new ApplicationQueue(this.queue.length);

   for (int i = 0; i < this.size; i++) {
     newApplication.queue[i] = this.queue[i]; // copy all the elements
    }
    
    //for (Application a : queue) {
    //   newApplication.enqueue(a);
    //}

    newApplication.size = this.size; // size should be updated

    return newApplication;
  }

  /**
   * Returns a String representing this ApplicationQueue, where each element (application) of the
   * queue is listed on a separate line, in order from the lowest score to the highest score.
   * 
   * This implementation is provided.
   * 
   * @see Application#toString()
   * @see ApplicationIterator
   * @return a String representing this ApplicationQueue
   */
  @Override
  public String toString() {
    StringBuilder val = new StringBuilder();

    for (Application a : this) {
      
      val.append(a).append("\n");
    }

    return val.toString();
  }

  /**
   * Returns an Iterator for this ApplicationQueue which proceeds from the lowest-scored to the
   * highest-scored Application in the queue.
   * 
   * This implementation is provided.
   * 
   * @see ApplicationIterator
   * @return an Iterator for this ApplicationQueue
   */
  @Override
  public Iterator<Application> iterator() {
    return new ApplicationIterator(this);
  }
}
