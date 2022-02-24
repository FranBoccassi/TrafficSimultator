package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

public class TrafficSimulator {
    
    private RoadMap roadMap;
    private List<Event> eventList;
    private int time;

    public TrafficSimulator() {
        time=0;
        roadMap=new RoadMap();
        eventList= new ArrayList<Event>();
    }

    public void addEvent(Event e) {
        eventList.add(e);
    }
    public void advance() {
        time++;
        int i=0;
        while(i<eventList.size()){
            if(eventList.get(i).getTime()==time){
                eventList.get(i).execute(roadMap);
                eventList.remove(i);}
            else
                i++;
        }
        for(int x=0; x<roadMap.getJunctions().size();x++) {
            roadMap.getJunctions().get(x).advance(time);
        }
        for(int y=0; y<roadMap.getRoads().size();y++) {
            roadMap.getRoads().get(y).advance(time);
        }
    }
    public void reset() {
        time=0;
        roadMap.reset();
        eventList.clear();
    }
    public JSONObject report() {
        JSONObject jo= new JSONObject();
        jo.put("time", time);
        jo.put("state", roadMap.report());
    return jo;
    }
}
