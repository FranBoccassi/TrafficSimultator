package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
// mirar el onError
public class TrafficSimulator{
 
    private RoadMap mapRoad;
    private List<Event> listEvent;
    private int timeSimulation;

	public TrafficSimulator() {
		timeSimulation=0;
		mapRoad=new RoadMap();
		listEvent= new ArrayList<Event>();
	}
	public RoadMap getM() {
		return mapRoad;
	}
	public void addEvent(Event e) {
		listEvent.add(e);
	}
	
	public void advance() {
		timeSimulation++;
		for(int i=0; i<listEvent.size();i++) {
			if(listEvent.get(i).getTime()==timeSimulation)
				listEvent.get(i).execute(mapRoad);
		}
		for(int j=0; j<listEvent.size();j++) {
			if(listEvent.get(j).getTime()<=timeSimulation)
				listEvent.remove(j);
			
		}
		for(int x=0; x<mapRoad.getJunctions().size();x++) {
			mapRoad.getJunctions().get(x).advance(timeSimulation);
		}
		for(int y=0; y<mapRoad.getRoads().size();y++) {
			mapRoad.getRoads().get(y).advance(timeSimulation);
		}
	}
	
	public void reset() {
		timeSimulation=0;
		mapRoad.reset();
		listEvent.clear();
	}
	
	public JSONObject report() {
		JSONObject jo= new JSONObject();
		jo.put("time", timeSimulation);
		jo.put("state", mapRoad.report());
	return jo;
	}
}