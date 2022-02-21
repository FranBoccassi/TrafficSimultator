package simulator.model;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class Vehicle extends SimulatedObject {
    
  private List<Junction> itinerary;
  private int maxSpeed;
  private int actualSpeed;
  private VehicleStatus condition;
  private Road road;
  private int location;
  private int contaminationGrade;
  private int totalContamination;
  private int totalDistance;


    Vehicle(String id, int maxSpeed, int contaminationGrade, List<Junction> itinerary) {
        super(id);
        actualSpeed = 0;
        condition = VehicleStatus.PENDING;
        road = null;
        totalContamination = 0;
        totalDistance = 0;
        location = 0;
        if ( maxSpeed < 0)
            throw new IllegalArgumentException("max Speed must be at least 0");
        else
            this.maxSpeed = maxSpeed;
        if (contaminationGrade < 0 || contaminationGrade > 10)
            throw new IllegalArgumentException("The grade of contamination must be between 0 and 10");
        else
            this.contaminationGrade = contaminationGrade;
        if ( itinerary.size() < 2)
            throw new IllegalArgumentException("The itinerary must be at least 2");
        else
            this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
        
    }
    

   protected void setSpeed(int s){
        if (s < 0)
             throw new IllegalArgumentException("Vehicle Speed must be at least 0");
        else
            if(s < maxSpeed)
                actualSpeed = s;
            else
                actualSpeed = maxSpeed;
      
    }


    protected void setContaminationClass(int c){
        if (c < 0 || c > 10)
            throw new IllegalArgumentException("The grade of contamination must be between 0 and 10");
        else
            this.contaminationGrade = c;
    }

    protected void advance(int time){
        int prevLocation = location;
        if(condition == VehicleStatus.TRAVELING)
            if((location + actualSpeed) > road.length)
                location = road.length;
            else
                location += actualSpeed;

            int l = location - prevLocation;
            int c = l * contaminationGrade;
            totalContamination += c;
             road.addContamination(c)
            //change Road data.   

            if(location >= road.length)
               condition = VehicleStatus.WAITING;
                actualSpeed = 0;
                junction.joinQueue(this);

    }

    protected void moveToNextRoad(){}


    public void setStatus(VehicleStatus condition){
        this.condition = condition;
    }



    public JSONObject report() {
		JSONObject jo= new JSONObject();
		jo.put("id", _id);
		jo.put("speed", this.maxSpeed);
		jo.put("distance", this.location);
		jo.put("co2", this.totalContamination);
		jo.put("class",this.contaminationGrade);
		if(this.condition==VehicleStatus.ARRIVED) 
			jo.put("status", "ARRIVED");
		else if(this.condition==VehicleStatus.PENDING)
			jo.put("status", "PENDING");
		else if(this.condition==VehicleStatus.TRAVELING)
			jo.put("status", "TRAVELING");
		else if(this.condition==VehicleStatus.WAITING)
			jo.put("status", "WAITING");
		if(this.condition!=VehicleStatus.ARRIVED)
			jo.put("road", road.getId());
			jo.put("location", location);
			
		return jo;
	}

    //Getters
    public int getLocation(){
        return this.location;
    }
    public int getSpeed(){
        return this.actualSpeed;
    } 
    public int getMaxSpeed(){
        return this.maxSpeed;
    } 
    public int getContClass(){
        return this.contaminationGrade;
    } 
    public VehicleStatus getStatus(){
        return this.condition;
    } 
    public int getTotalCO2(){
        return this.totalContamination;
    }
    public List<Junction> getItinerary(){
        return this.itinerary;
    }
    public Road getRoad(){
        return this.road;
    }

}

