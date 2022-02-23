package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

    private List<Road> roadList;
    private Map<Junction, Road> roadMap;
    private List<List<Vehicle>> queueList;
    private int green;
    private int lastGreen;
    private LightSwitchingStrategy lStrategy;
    private DequeuingStrategy dStrategy;
    private int xCoor;
    private int yCoor;


    Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
        super(id);
        roadList = new ArrayList<Road>();
        roadMap = new HashMap<Junction, Road>();
        queueList = new ArrayList<List<Vehicle>>();
        green = -1;
        lastGreen = 0;
        
        if(lsStrategy == null || dqStrategy == null)
            throw new IllegalArgumentException("Strategies can't be null");
        else
            lStrategy = lsStrategy;
            dStrategy = dqStrategy;
        
        if(xCoor < 0 || yCoor < 0)
             throw new IllegalArgumentException("Coordenatis can't be negative");
        else
            this.xCoor = xCoor;
            this.yCoor = yCoor;

    }
    
    public void addIncommingRoad(Road r){
        if(r.getDestination() != this)
            throw new IllegalArgumentException("This road isn't a incomming road");
        else 
            roadList.add(r);
            queueList.add(new LinkedList<Vehicle>());
            //TODO preguntar mapa carreteras
    }

    public void addOutGoingRoad(Road r){
        if(r.getOrigin() != this || roadMap.get(r.getDestination()) != null)
            throw new IllegalArgumentException("This road can't go to this junction");
        else 
            roadMap.put(r.getDestination(), r);
    }

    public void enter(Vehicle v){

       int pos = roadList.indexOf(v.getRoad());
        if(pos == -1)
            throw new IllegalArgumentException("This road doesn't exists in this junction");
        else
            queueList.get(pos).add(v);
    }

    public Road roadTo(Junction j){
        return roadMap.get(j);
    }

    @Override
    void advance(int time) {

        if(green != -1){
            if(queueList.get(green).size()>0){
                List<Vehicle> listV = dStrategy.dequeue(queueList.get(green));
                while(listV.size() > 0){
                    listV.get(0).moveToNextRoad();
                    for(int i=0; i< queueList.get(green).size();i++) {
                        if(queueList.get(green).get(i).getId() == listV.get(0).getId()) {
                            queueList.get(green).remove(i);
                        }
                    }
                    listV.remove(0);
                }
            }
        }
        if(green != lStrategy.chooseNextGreen(roadList, queueList, green, lastGreen, time)) {
            int x = lStrategy.chooseNextGreen(roadList, queueList, green, lastGreen, time);
            green = x;
            lastGreen = time; 
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
			jo.put("green", roadList.get(green).getId());
		}
		if(roadList.size()>0) {
		for(int i=0;i<roadList.size();i++) {
			JSONObject jo2= new JSONObject();
			JSONArray listV= new JSONArray();
			jo2.put("road", roadList.get(i).getId());
			for(Vehicle j: queueList.get(i)) {
				listV.put(j.getId());
			}
			jo2.put("vehicles", listV);
		    listT.put(jo2);
		}
		}
		jo.put("queues", listT);
		return jo;
}
    
    
}
