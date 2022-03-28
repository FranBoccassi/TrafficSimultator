package simulator.factories;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.Weather;

public abstract class NewRoadEventBuilder extends Builder<Event> {

    public NewRoadEventBuilder(String s) {
        super(s);
    }
    abstract Event crearI(JSONObject data,Weather w);
    @Override
    protected Event createTheInstance(JSONObject data) {
    	Weather w= Weather.valueOf(data.getString("weather").toUpperCase());
    	return crearI(data, w);
}

}