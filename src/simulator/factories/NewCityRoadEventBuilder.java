package simulator.factories;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewCityRoadEvent;
import simulator.model.Weather;

public class NewCityRoadEventBuilder extends  NewRoadEventBuilder{

    public NewCityRoadEventBuilder() {
        super("new_city_road");

    }
	@Override
	NewCityRoadEvent crearI(JSONObject data, Weather w) {
		 NewCityRoadEvent e= new NewCityRoadEvent(data.getInt("time"),data.getString("id"), data.getString("src"),data.getString("dest"),
		            data.getInt("length"), data.getInt("co2limit"), data.getInt("maxspeed"), w);
		    return e;
	}
}