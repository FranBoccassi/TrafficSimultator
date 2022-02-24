package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
    private List<Junction> junctionList;
    private List<Vehicle> vehicleList;
    private List<Road> roadList;
    private Map<String, Junction> junctionMap;
    private Map<String, Road> roadMap;
    private Map<String, Vehicle> vehicleMap;

    protected RoadMap() {
        junctionList = new ArrayList<Junction>();
        vehicleList = new ArrayList<Vehicle>();
        roadList = new ArrayList<Road>();
        junctionMap = new HashMap<String, Junction>();
        roadMap = new HashMap<String, Road>();
        vehicleMap = new HashMap<String, Vehicle>();
    }

    public void addJunction(Junction j) {
        if (junctionMap.get(j.getId()) != null)
            throw new IllegalArgumentException("This junction already exist");
        else
            junctionList.add(j);
            junctionMap.put(j.getId(), j);
    }

    public void addRoad(Road r) {
        if (roadMap.get(r._id) == null) {
            if (junctionMap.containsValue(r.getOrigin()) && junctionMap.containsValue(r.getDestination())) {
                roadList.add(r);
                roadMap.put(r._id, r);
                //TO DO
                /*int posIni=junctionList.indexOf(r.getOrigin());
                int posLast=junctionList.indexOf(r.getDestination());
                junctionList.get(posIni).addIncommingRoad(r);
                junctionList.get(posLast).addOutGoingRoad(r);*/
            } else 
                throw new IllegalArgumentException("One Junction isn't exist");
            
        }
        else
            throw new IllegalArgumentException("This road already exist");
        
    }

    public void addVehicle(Vehicle v){
        if(vehicleMap.get(v.getId())!=null)
            throw new IllegalArgumentException("This vehicle already exist");
        else
            for(int i=0;i<v.getItinerary().size()-1;i++){
                if(v.getItinerary().get(i).getRoadMap().get(v.getItinerary().get(i+1))==null)  // si en el mapa del cruce actual no 
                    throw new IllegalArgumentException("Roads aren't connected");              // existe una carretera que vaya al siguiente
                                                                                               // cruce, no esta conectada.
            }
            vehicleList.add(v);
			vehicleMap.put(v._id, v); 
    }

    public Junction getJunction(String id){
		if(junctionMap.get(id)==null)return null;
		else return junctionMap.get(id);
	}
	public Road getRoad(String id) {
		if(roadMap.get(id)==null)return null;
		else return roadMap.get(id);
		
		
	}
	public Vehicle getVehicle(String id) {
		if(vehicleMap.get(id)==null)return null;
		else return vehicleMap.get(id);
	}
	public List<Junction>getJunctions(){
		 return junctionList;
		
	}
	public List<Road>getRoads(){
		return roadList;
		
	}
	public List<Vehicle>getVehicles(){
		return vehicleList;
		
	}
	void reset() {
		junctionList.clear();
		roadList.clear();
		vehicleList.clear();
		junctionMap.clear();
		roadMap.clear();
		vehicleMap.clear();
	}
    public JSONObject report(){
		JSONArray listV= new JSONArray();
		JSONArray listJ= new JSONArray();
		JSONArray listR= new JSONArray();
		JSONObject jo= new JSONObject();
		for(int i=0; i< junctionList.size(); i++) {
			listJ.put(junctionList.get(i).report());
		}
		jo.put("junctions", listJ);
		for(int i=0; i< roadList.size(); i++) {
			listR.put(roadList.get(i).report());
		}
		jo.put("roads" , listR);
		for(int i=0; i< vehicleList.size(); i++) {
			listV.put(vehicleList.get(i).report());
		}
		jo.put("vehicles",listV );
		
		return jo;
	}

}