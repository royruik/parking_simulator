public class CapacityOptimizer {
	private static final int NUM_RUNS = 10;

	private static final double THRESHOLD = 5.0d;

// This method determines the optimal number of spots for a parking lot
// based on the given hourly rate for parking.
// It runs simulations with increasing lot sizes until the average queue length is 5 or less,
// and returns the optimal lot size.
	public static int getOptimalNumberOfSpots(int hourlyRate) {
		
		// Initialize variables
		boolean havenotfoundoptimizedsize=true;
		ParkingLot lot;
		Simulator eachtime;
		int nsize = 1;

		int sIMULATION_DURATION = 24 * 3600;
		
		// Run simulations to find optimal lot size
		while(havenotfoundoptimizedsize){
			int averagequeuesizes=0;
			System.out.println("==== Setting lot capacity to: "+ nsize+"====");
			
			// Run 10 simulations for each lot size
			for (int i = 0; i< 10 ; i++ ) {
				long simStart = System.currentTimeMillis();
				lot = new ParkingLot(nsize);
				eachtime = new Simulator(lot,hourlyRate,sIMULATION_DURATION);
				eachtime.simulate();
				long simEnd = System.currentTimeMillis();
				
				// Print simulation results
				System.out.println("Simulation run "+ (i+1)+" ("+((simEnd - simStart))+"ms); Queue length at the end of simulation run: "+eachtime.getIncomingQueueSize());
				averagequeuesizes+=eachtime.getIncomingQueueSize();
			}
			averagequeuesizes=averagequeuesizes/10;
			
			// Check if average queue length is 5 or less
			if (averagequeuesizes<=5){
				havenotfoundoptimizedsize=false;
				break;
			}
			else{
				nsize++;
			}
			System.out.println("");
		}
		
		// Return optimal lot size
		return nsize;
	}


	public static void main(String args[]) {
		
		StudentInfo.display();

		long mainStart = System.currentTimeMillis();

		if (args.length < 1) {
			System.out.println("Usage: java CapacityOptimizer <hourly rate of arrival>");
			System.out.println("Example: java CapacityOptimizer 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		int hourlyRate = Integer.parseInt(args[0]);

		int lotSize = getOptimalNumberOfSpots(hourlyRate);

		System.out.println();
		System.out.println("SIMULATION IS COMPLETE!");
		System.out.println("The smallest number of parking spots required: " + lotSize);

		long mainEnd = System.currentTimeMillis();

		System.out.println("Total execution time: " + ((mainEnd - mainStart) / 1000f) + " seconds");

	}
}