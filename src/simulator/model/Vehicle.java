package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject {
	private List<Junction> itineray;
	private int numRoad;
	private int maxSpeed;
	private int actualSpeed;
	private VehicleStatus condition;
	private Road road;
	private int location;
	private int contGrade;
	private int totalCont;
	private int totalDistance;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
			super(id);
			actualSpeed=0;
			condition=VehicleStatus.PENDING;
			totalCont=0;
			numRoad=0;
			totalDistance=0;
			road=null;
			location=0;
			contGrade=0;
			if(maxSpeed >0)
				this.maxSpeed=maxSpeed;
			else
				throw new IllegalArgumentException("Speed must be positive");
			if(contClass<0||contClass>10)
				throw new IllegalArgumentException("Contamination must be between 0-10");
			else
				this.contGrade=contClass;
			if(itinerary==null || itinerary.size()<2)
				throw new IllegalArgumentException("List is empty");
			else
				itineray=Collections.unmodifiableList(new ArrayList<>(itinerary)); 
			}
			
	public void setSpeed(int s) {
		if(s<0)
			 throw new IllegalArgumentException("Speed must be positive");
		else {
			if(this.condition!=VehicleStatus.TRAVELING) {
				actualSpeed=0;
			}
		else if(s>maxSpeed)
			actualSpeed=maxSpeed;
		else
			actualSpeed=s;
		}
		
	}
	public void setContClass(int c) {
		if(c<0||c>10)
			 throw new IllegalArgumentException("Contamination must be between 0-10");
		else
			contGrade=c;
	}
	

	
	public void advance(int time) {
		int previaL;
		int cont;
		if(getStatus()==VehicleStatus.TRAVELING) {
			previaL=location;
			if(location+actualSpeed< road.getLength())
				location=location+actualSpeed;
			else
				location=road.getLength();
			
			previaL=location-previaL;
			totalDistance=totalDistance+previaL;
			cont=contGrade*previaL;
			totalCont=totalCont+cont;
			road.addContamination(cont); 
			if(location>=road.getLength()) {
				if(numRoad==itineray.size()-1) {
					itineray.get(numRoad).enter(this);
					condition=VehicleStatus.WAITING;
					actualSpeed=0;
				}
				else  if(itineray.size()-1>numRoad){
					if(!itineray.contains(road.getDest()))
						 throw new IllegalArgumentException("The itinerary doesn't contain the road");
					else {
						itineray.get(numRoad).enter(this);
						condition=VehicleStatus.WAITING;
						actualSpeed=0;
					}
				}
			}
		}
		else {
			actualSpeed=0;
		}
		
	}

	public void moveToNextRoad() {
	    if(this.condition!=VehicleStatus.PENDING && this.condition!=VehicleStatus.WAITING)
			throw new IllegalArgumentException("The condition must be PENDING or WAITING");
		else if(this.condition==VehicleStatus.PENDING) {
			numRoad++;
			road=itineray.get(0).getRoad(itineray.get(numRoad));
			location=0;
			actualSpeed=0;
			road.enter(this);
			actualSpeed=maxSpeed;
			condition=VehicleStatus.TRAVELING;
		}
		else {
			numRoad++;
			if(numRoad==itineray.size()) {
				condition=VehicleStatus.ARRIVED;
				actualSpeed=0;
				road.exit(this);
			}
			else if(numRoad<itineray.size()) {
			road.exit(this);
			road=itineray.get(numRoad-1).getRoad(itineray.get(numRoad));
			location=0;
			actualSpeed=0;
			road.enter(this);
			actualSpeed=maxSpeed;
			condition=VehicleStatus.TRAVELING;
		}
			
		}
	}
	@Override
	public JSONObject report() {
		JSONObject jo= new JSONObject();
		jo.put("id", _id);
		jo.put("speed", actualSpeed);
		jo.put("distance", totalDistance);
		jo.put("co2", totalCont);
		jo.put("class",contGrade);
		if(getStatus()==VehicleStatus.ARRIVED)
			jo.put("status", "ARRIVED");
		else if(getStatus()==VehicleStatus.PENDING)
			jo.put("status", "PENDING");
		else if(getStatus()==VehicleStatus.TRAVELING)
			jo.put("status", "TRAVELING");
		else if(getStatus()==VehicleStatus.WAITING)
			jo.put("status", "WAITING");
		if(getStatus()!=VehicleStatus.ARRIVED && road!=null) {
			jo.put("road", road.getId());
			jo.put("location", location);
		}
			
		return jo;
	}
	
	
	//Getters
	

	public List<Junction> getItinerary() {
		return itineray;
	}
	public int getLocation() {
		return location;
	}
	public int getSpeed() {
		return actualSpeed;
	}
	public int getContClass() {
		return contGrade;
	}
	public Road getRoad() {
		return road;
	}
	public int getMaxSpeed() {
		return maxSpeed;
	}
	public int getTotalCO2() {
		return totalCont;
	}
	public int getTotalDistance() {
		return totalDistance;
	}
	public VehicleStatus getStatus() {
		return condition;
	}

}