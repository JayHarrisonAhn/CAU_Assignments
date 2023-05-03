package prob1;
import java.util.concurrent.ArrayBlockingQueue;

class ParkingGarage_ABQ {
  private ArrayBlockingQueue<Car_ABQ> parkingLot;
  public ParkingGarage_ABQ(int places) {
    if (places < 0)
      places = 0;
    parkingLot = new ArrayBlockingQueue<>(places);
  }
  public void enter(Car_ABQ car) { // enter parking garage
    try {
      parkingLot.put(car);
    } catch (InterruptedException e) {}
  }
  public void leave(Car_ABQ car) { // leave parking garage
    parkingLot.remove(car);
  }
  public int getPlaces()
  {
    return parkingLot.remainingCapacity();
  }
}


class Car_ABQ extends Thread {
  private ParkingGarage_ABQ parkingGarage;
  public Car_ABQ(String name, ParkingGarage_ABQ p) {
    super(name);
    this.parkingGarage = p;
    start();
  }

  private void tryingEnter()
  {
      System.out.println(getName()+": trying to enter");
  }


  private void justEntered()
  {
      System.out.println(getName()+": just entered");

  }

  private void aboutToLeave()
  {
      System.out.println(getName()+":                                     about to leave");
  }

  private void Left()
  {
      System.out.println(getName()+":                                     have been left");
  }

  public void run() {
    while (true) {
      try {
        sleep((int)(Math.random() * 10000)); // drive before parking
      } catch (InterruptedException e) {}
      tryingEnter();
      parkingGarage.enter(this);
      justEntered();
      try {
        sleep((int)(Math.random() * 20000)); // stay within the parking garage
      } catch (InterruptedException e) {}
      aboutToLeave();
      parkingGarage.leave(this);
      Left();

    }
  }
}


public class ParkingGarageOperation_ABQ {
  public static void main(String[] args){
    ParkingGarage_ABQ parkingGarage = new ParkingGarage_ABQ(7);
    for (int i=1; i<= 10; i++) {
      Car_ABQ c = new Car_ABQ("Car "+i, parkingGarage);
    }
  }
}

