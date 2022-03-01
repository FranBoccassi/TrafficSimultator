package simulator.factories;


import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewVehicleEvent;



public class NewVehicleEventBuilder extends Builder<NewVehicleEvent> {	
	public NewVehicleEventBuilder() {
		super("new_vehicle");
	}

	@Override
	protected NewVehicleEvent createTheInstance(JSONObject data) {
		List<String> list= new ArrayList<String>();
		JSONArray iti = data.getJSONArray("itinerary");
		if(iti!=null) {
			for(int i=0; i< iti.length();i++) {
				list.add(iti.getString(i));
			}
		}
		NewVehicleEvent e= new NewVehicleEvent(data.getInt("time"),data.getString("id"), data.getInt("maxspeed"), data.getInt("class"),list);
		return e;
	}

}