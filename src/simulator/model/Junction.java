package simulator.model;

import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {
	private List<Road> listRoad;
	private Map<Junction,Road> mapRoad;
	private List<List<Vehicle >> listQueue;
	private int green;
	private int lastGreen;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int CoorX;
	private int CoorY;
	
	public Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
        super(id);
        listRoad= new ArrayList<Road>();
         mapRoad= new HashMap<Junction,Road>();
         listQueue= new ArrayList<List<Vehicle >>();
         green=-1;
         lastGreen=0;
            if(lsStrategy==null || dqStrategy==null)
                throw new IllegalArgumentException("Strategies can't be null");
            else {
                this.lsStrategy=lsStrategy;
                this.dqStrategy=dqStrategy;
                if(xCoor<0 || yCoor<0)
                    throw new IllegalArgumentException("Coordenates can't be negative");
                else {
                	CoorX=xCoor;
                	CoorY=yCoor;
                }
            }
            }
	public void addIncommingRoad(Road r) {
		if(r.getDest()==this) {
			listRoad.add(r);
			listQueue.add(new LinkedList<Vehicle>());
			}
		else {
			throw new IllegalArgumentException("This road isn't a incomming road");
		}
		
	}
	public void addOutGoingRoad(Road r){
		if(mapRoad.get(r.getDest())==null||r.getSrc()==this)
			 mapRoad.put(r.getDest(), r);
		else 
			throw new IllegalArgumentException("This road can't go to this junction.");
	}
	
	void enter(Vehicle v) {
		for(int i=0; i<listRoad.size();i++) {
			if(listRoad.get(i)==v.getRoad()) {
				listQueue.get(i).add(v);
			}
		}
		
	}
	public Road roadTo(Junction j) {
		return mapRoad.get(j);
	}
	@Override
	void advance(int time) {
			if(green!=-1) {
				if(listQueue.get(green).size()>0) {
				List <Vehicle> listV=dqStrategy.dequeue(listQueue.get(green));
				while(listV.size()>0) {
					listV.get(0).moveToNextRoad();
					for(int i=0; i<listQueue.get(green).size();i++) {
						if(listQueue.get(green).get(i).getId()==listV.get(0).getId()) {
							listQueue.get(green).remove(i);
						}
					}
					listV.remove(0);
				}
			}
			}
		if(green!=lsStrategy.chooseNextGreen(listRoad, listQueue, green, lastGreen, time)) {
			int x=lsStrategy.chooseNextGreen(listRoad, listQueue, green, lastGreen, time);
			green= x;
			lastGreen=time; 
		}
	}
	@Override
	public JSONObject report() {
		JSONObject jo= new JSONObject();
		JSONArray listT= new JSONArray();
		jo.put("id", _id);
		if(green==-1) {
			jo.put("green", "none");
		}
		else {
			jo.put("green", listRoad.get(green).getId());
		}
		if(listRoad.size()>0) {
		for(int i=0;i<listRoad.size();i++) {
			JSONObject jo2= new JSONObject();
			JSONArray listV= new JSONArray();
			jo2.put("road", listRoad.get(i).getId());
			for(Vehicle j: listQueue.get(i)) {
				listV.put(j.getId());
			}
			jo2.put("vehicles", listV);
		    listT.put(jo2);
		}
		}
		jo.put("queues", listT);
		return jo;
}
	//Getters
	public int getGreenLightIndex() {
		return green;
	}
	public int getX() {
		return CoorX;
	}
	public int getY() {
		return CoorY;
	}
	public List<Road>  getInRoads() {
		return listRoad;
	}

	protected Road getRoad(Junction j) {
		Road r= mapRoad.get(j);
		 return r;
	}
	public String getGreen() {
		if(green==-1) return "NONE";
		else return listRoad.get(green).getId();
	}
	
	public String getQueues() {
		String x= " ";
		if(listRoad.size()>0) {
			for(int i=0;i<listRoad.size();i++) {
				x= x +listRoad.get(i).getId()+"[]";
				for(Vehicle j: listQueue.get(i)) {
					x=x+j.getId()+", ";
				}
			}
		}
			return x;
	}
	
}