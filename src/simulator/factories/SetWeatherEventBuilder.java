package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<SetWeatherEvent> {
	public SetWeatherEventBuilder() {
		super("set_weather");
		// TODO Auto-generated constructor stub
	}
	    @Override
	    protected SetWeatherEvent createTheInstance(JSONObject data) {
	    	List<Pair<String,Weather>> list= new ArrayList<Pair<String,Weather>>();
	    	JSONArray jA=data.getJSONArray("info");
	    	for(int i=0; i<jA.length();i++) {
	    		JSONObject pair=jA.getJSONObject(i);
	    		Weather w= Weather.valueOf(pair.getString("weather").toUpperCase());
	    		list.add(new Pair<String,Weather>(pair.getString("road"), w));
	    	}
	    	SetWeatherEvent e= new SetWeatherEvent(data.getInt("time"),list);

	    return e;
	}
}