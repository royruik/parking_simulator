/**
 * @author Mehrdad Sabetzadeh, University of Ottawa
 */
public class ParkingLot {

	// IMPORTANT: You are *discouraged* from defining any new instance variables in
	// ParkingLot. You are expected to provide a list-based implementation of
	// ParkingLot. Defining new instance variables can take you away from this
	// implementation goal and thus result in the loss of marks.
	/**
	 * List for storing occupancy information in the lot
	 */
	private List<Spot> occupancy;

	/**
	 * The maximum number of cars that the lot can accommodate
	 */
	private int capacity;

	/**
	 * Constructs a parking lot with a given (maximum) capacity
	 * 
	 * @param capacity is the (maximum) capacity of the lot
	 */
	public ParkingLot(int capacity) {

		if (capacity < 0) {
		
			// Hint: throw a suitable exception here.
			//Throw Exception when receiving a negative capacity variable (dont make sense)
			throw new IllegalStateException("The capacity should be positive to be legit.");
		}

		this.capacity = capacity;
		this.occupancy = new SinglyLinkedList<Spot>();
	}

	/**
	 * Parks a car (c) in the parking lot.
	 * 
	 * @param c         is the car to be parked
	 * @param timestamp is the (simulated) time when the car gets parked in the lot
	 */
	public void park(Car c, int timestamp) {
	
		if (c==null) {//Throw Exception when passing a null Car variable
			throw new NullPointerException("Can't add null reference to enable park(); The Car variable can not be null.");
		}
		Spot toPark=new Spot(c, timestamp);
		occupancy.add(toPark);
	
	}

	/**
	 * Removes the car (spot) parked at list index i in the parking lot
	 * 
	 * @param i is the index of the car to be removed
	 * @return the car (spot) that has been removed
	 */
	public Spot remove(int i) {
	

		try{
			Spot toRemove=occupancy.remove(i);
			return toRemove;
		}
		catch(IndexOutOfBoundsException e){ //Throw Exception when the index is out of bounds (cannot remove any value from the occupancy)
			throw new IllegalStateException("The position index should be legal. (There is no car at certain position.)");
		}

	
	}

	public boolean attemptParking(Car c, int timestamp) {
	
		if (c==null) { //Throw Exception when passing a null Car variable
			throw new NullPointerException("Can't add null reference to enable park(); The Car variable can not be null.");
		}

		if (occupancy.size()<capacity) {
			return true;
		}
		else{
			return false;
		}
	
	}

	/**
	 * @return the capacity of the parking lot
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Returns the spot instance at a given position (i, j)
	 * 
	 * @param i is the parking row index
	 * @param j is the index of the spot within row i
	 * @return the spot instance at a given position (i, j)
	 */
	public Spot getSpotAt(int i) {

	
		try{
			Spot toGet=occupancy.get(i);
			if (toGet==null) { //Throw Exception when getting a null Spot variable [is not what we wanted]
				throw new NullPointerException("Got a null spot variable at certain index.");
			}
			else{
				return toGet;
			}
		}
		catch(IndexOutOfBoundsException e){ //Throw Exception when the index is out of bounds (cannot get any value from the occupancy)
			throw new IllegalStateException("The position index should be legal. (There is no car at certain position.)");
		}



	}

	/**
	 * @return the total number of cars parked in the lot
	 */
	public int getOccupancy() {

		return occupancy.size();//Return the size of the occupancy variable (how many cars in the lot)

	}

	/**
	 * @return String representation of the parking lot
	 */
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("Total capacity: " + this.capacity + System.lineSeparator());
		buffer.append("Total occupancy: " + this.occupancy.size() + System.lineSeparator());
		buffer.append("Cars parked in the lot: " + this.occupancy + System.lineSeparator());

		return buffer.toString();
	}
}