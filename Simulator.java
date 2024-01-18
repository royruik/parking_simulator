/**
 * @author Mehrdad Sabetzadeh, University of Ottawa
 *
 */
public class Simulator {

	/**
	 * Length of car plate numbers
	 */
	public static final int PLATE_NUM_LENGTH = 3;

	/**
	 * Number of seconds in one hour
	 */
	public static final int NUM_SECONDS_IN_1H = 3600;

	/**
	 * Maximum duration a car can be parked in the lot
	 */
	public static final int MAX_PARKING_DURATION = 8 * NUM_SECONDS_IN_1H;

	/**
	 * Total duration of the simulation in (simulated) seconds
	 */
	public static final int SIMULATION_DURATION = 24 * NUM_SECONDS_IN_1H;

	/**
	 * The probability distribution for a car leaving the lot based on the duration
	 * that the car has been parked in the lot
	 */
	public static final TriangularDistribution departurePDF = new TriangularDistribution(0, MAX_PARKING_DURATION / 2,
		MAX_PARKING_DURATION);

	/**
	 * The probability that a car would arrive at any given (simulated) second
	 */
	private Rational probabilityOfArrivalPerSec;

	/**
	 * The simulation clock. Initially the clock should be set to zero; the clock
	 * should then be incremented by one unit after each (simulated) second
	 */
	private int clock;

	/**
	 * Total number of steps (simulated seconds) that the simulation should run for.
	 * This value is fixed at the start of the simulation. The simulation loop
	 * should be executed for as long as clock < steps. When clock == steps, the
	 * simulation is finished.
	 */
	private int steps;

	/**
	 * Instance of the parking lot being simulated.
	 */
	private ParkingLot lot;

	/**
	 * Queue for the cars wanting to enter the parking lot
	 */
	private Queue<Spot> incomingQueue;

	/**
	 * Queue for the cars wanting to leave the parking lot
	 */
	private Queue<Spot> outgoingQueue;

	/**
	 * @param lot   is the parking lot to be simulated
	 * @param steps is the total number of steps for simulation
	 */
	public Simulator(ParkingLot lot, int perHourArrivalRate, int steps) {
		
		if (lot==null) { //Throw Exception when passing a null ParkingLot variable
			throw new NullPointerException("Can't add null reference to enable initiating Simulator; The ParkingLot variable can not be null.");
		}

		this.lot = lot; 

		this.steps = steps;

		this.clock = 0;

		this.probabilityOfArrivalPerSec = new Rational(perHourArrivalRate,3600);

		//initiating the Linked Queue variable
		incomingQueue = new LinkedQueue<Spot>();
		outgoingQueue = new LinkedQueue<Spot>();
		
	}


	/**
	 * Simulate the parking lot for the number of steps specified by the steps
	 * instance variable
	 * NOTE: Make sure your implementation of simulate() uses peek() from the Queue interface.
	 */
	public void simulate() {


		this.clock = 0; //set time to be 0

		int duration;//set up duration variable to enable determining when will a car will leave the lot

		while (clock < steps) {

			//CAR ENTER THE POT INCOMING QUEUE
			boolean havenotparkedthissecond=true;
			boolean havenotleftthissecond=true;
			if(RandomGenerator.eventOccurred(probabilityOfArrivalPerSec)){ //passed the probability
				Car come=new Car(RandomGenerator.generateRandomString(3));//get cars
				Spot incoming=new Spot(come,clock);//create a Spot object
				incomingQueue.enqueue(incoming);//pass it to the incomingQueue

			}
			//CARS ABOUT TO LEAVING THE PARKING LOT
			if (lot.getOccupancy()!=0) {//As the occupancy variable represents how many cars already in the lot.
				for(int i = 0; i < lot.getOccupancy(); i++){
					duration=clock-lot.getSpotAt(i).getTimestamp();
						if (duration==MAX_PARKING_DURATION) {//reachs the max parking duration
							outgoingQueue.enqueue(lot.remove(i));//force to move
						}
						else{
							if (RandomGenerator.eventOccurred(departurePDF.pdf(duration))) {//probability for a car leaving
								outgoingQueue.enqueue(lot.remove(i));

							}
						}



					}
				}

			//INCOMING QUEUE
				if (!incomingQueue.isEmpty()) {	//when the incomingQueue is not empty NO Exception Handling IS REQUIRED
					
					while(havenotparkedthissecond) {//and there is no car parked this second (1 parking per second)

						Spot enter=incomingQueue.peek();//get Spot variable first without messing with the order arrangement [avoid the situation that the car cannot be parked]
						Car enterC=enter.getCar();//transfer it to Car variable

						if (lot.attemptParking(enterC,clock)) {//check validity


							lot.park(incomingQueue.dequeue().getCar(),clock);//park the car at certain timestamp
							

							havenotparkedthissecond=false;//switch the boolean

							break;	//end of the loop						
						}
						else{
						havenotparkedthissecond=false;//no car eligible (basically, the occupancy is full), giving up to park

						break; //end of the loop
					}
				}
			}

			//OUTGOING QUEUE
			if (!outgoingQueue.isEmpty()) { //when the outgoingQueue is not empty NO Exception Handling IS REQUIRED
				while(havenotleftthissecond){//and there is no car left this second (1 parking per second)
					
					Car leave=outgoingQueue.dequeue().getCar();//cars getting out
					
					havenotleftthissecond=false;//switch the boolean

					break;//end of the loop
				}
			}
			clock++;//increment of time variable
		}

	}

	public int getIncomingQueueSize() {

		return incomingQueue.size();//returns the size of the incomingQueue size for analyzing afterwards

	}


}