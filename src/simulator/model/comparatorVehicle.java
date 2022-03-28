package simulator.model;

import java.util.Comparator;

class comparatorVehicle implements Comparator<Vehicle> {
    comparatorVehicle() {
    }
	@Override
	public int compare(Vehicle o1, Vehicle o2) {
	  	if (o1.getLocation()== o2.getLocation()) return 0;
    	else if (o1.getLocation() < o2.getLocation()) return 1;
    	else return -1;
	}
}
   