package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	private List<Junction> listJunction;
	private List<Vehicle> listVehicle;
	private List<Road> listRoad;
	private Map<String,Junction> mapJunction;
	private Map<String,Road> mapRoad;
	private Map<String,Vehicle> mapVehicle;
	
	protected RoadMap() {
		listJunction=new ArrayList<Junction>();
		listVehicle= new ArrayList<Vehicle>();
		listRoad= new ArrayList<Road>();
		mapRoad=  new HashMap<String,Road>();
		mapJunction=  new HashMap<String,Junction>();
		mapVehicle=  new HashMap<String,Vehicle>();
	}
	void addJunction(Junction j){
		listJunction.add(j);
		mapJunction.put(j._id, j);
	}
	void addRoad(Road r) {
		if(mapRoad.get(r._id)==null) {
			if(mapJunction.containsValue(r.getSrc()) && mapJunction.containsValue(r.getDest())) {
				listRoad.add(r);
				mapRoad.put(r._id, r);
			}
			else
				throw new IllegalArgumentException("One Junction isn't exist");
		}
		else
			throw new IllegalArgumentException("The road already exist");
		//buscar posicion del destino
		if(this.listJunction.indexOf(r.getDest()) >= 0 )
			listJunction.get(this.listJunction.indexOf(r.getDest())).addIncommingRoad(r);
		//buscar posicion del origen
		if(this.listJunction.indexOf(r.getSrc()) >= 0 )
			listJunction.get(this.listJunction.indexOf(r.getSrc())).addOutGoingRoad(r);
	}
	
	void addVehicle(Vehicle v) {
		if(mapVehicle.get(v._id)==null) {
				listVehicle.add(v);
				mapVehicle.put(v._id, v);
		}
		else
			throw new IllegalArgumentException("The vehicle already exist");
	}
	void reset() {
		listJunction.clear();
		listRoad.clear();
		listVehicle.clear();
		mapJunction.clear();
		mapRoad.clear();
		mapVehicle.clear();
	}
	public JSONObject report(){
		JSONArray listV= new JSONArray();
		JSONArray listJ= new JSONArray();
		JSONArray listR= new JSONArray();
		JSONObject jo= new JSONObject();
		for(int i=0; i< listJunction.size(); i++) {
			listJ.put(listJunction.get(i).report());
		}
		jo.put("junctions", listJ);
		for(int i=0; i< listRoad.size(); i++) {
			listR.put(listRoad.get(i).report());
		}
		jo.put("roads" , listR);
		for(int i=0; i< listVehicle.size(); i++) {
			listV.put(listVehicle.get(i).report());
		}
		jo.put("vehicles",listV );
		
		return jo;
	}
	
	//Getters
	public Junction getJuntion(String id){
		if(mapJunction.get(id)==null)return null;
		else return mapJunction.get(id);
	}
	public Road getRoad(String id) {
		if(mapRoad.get(id)==null)return null;
		else return mapRoad.get(id);
		
		
	}
	public Vehicle getVehicle(String id) {
		if(mapVehicle.get(id)==null)return null;
		else return mapVehicle.get(id);
	}
	public List<Junction>getJunctions(){
		 return listJunction;
		
	}
	public List<Road>getRoads(){
		return listRoad;
		
	}
	public List<Vehicle>getVehilces(){
		return listVehicle;
		
	}
}