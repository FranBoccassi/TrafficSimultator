package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {

	private String id;
	private int maxspeed;
	private int contClass; 
	private List<String> itinerary;
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
			super(time);
			this.id=id;
			this.maxspeed=maxSpeed;
			this.contClass=contClass;
			this.itinerary=new ArrayList<String>(itinerary);
            
			}

	@Override
	void execute(RoadMap map) {
		List<Junction> j= new ArrayList();
		for(int i=0;i<itinerary.size();i++) {
			j.add(map.getJunction(itinerary.get(i)));
		}
		Vehicle v= new Vehicle(id, maxspeed, contClass, j);
		map.addVehicle(v);
		v.moveToNextRoad();
	}
}