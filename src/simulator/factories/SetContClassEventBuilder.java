package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.SetWeatherEvent;

public class SetContClassEventBuilder extends Builder<Event> {
	public SetContClassEventBuilder() {
		super("set_cont_class");
		// TODO Auto-generated constructor stub
	}
	    @Override
	    protected NewSetContClassEvent createTheInstance(JSONObject data) {
	    	List<Pair<String,Integer>> list= new ArrayList<Pair<String,Integer>>();
	    	JSONArray jA=data.getJSONArray("info");
	    	for(int i=0; i<jA.length();i++) {
	    		JSONObject pair=jA.getJSONObject(i);
	    		list.add(new Pair<String,Integer>(pair.getString("vehicle"),(Integer) pair.get("class")));
	    	}
	    	NewSetContClassEvent e= new NewSetContClassEvent(data.getInt("time"),list);

	    return e;
	}
}
