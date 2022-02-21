package simulator.model;
import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONArray;


public abstract class Road extends SimulatedObject {

    protected Junction origin;
    protected Junction destination;
    protected int length;
    protected int maxSpeed;
    protected int speedLimit;
    protected int exccessContaminationAlarm;
    protected Weather weather;
    protected int totalContamination;
    protected List<Vehicle> lVehicles;

    Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
        int contLimit, int length, Weather weather) {
        super(id);
        totalContamination = 0;
        lVehicles = new ArrayList<Vehicle>();
        if (srcJunc != null)
            srcJunc.addOutboundRoad(this);
            origin = srcJunc;
        else
            throw new IllegalArgumentException("Origin can't be null");
        
        if (destJunc != null)
            destJunc.addInboundRoad(this);
            destination = destJunc;
        else
            throw new IllegalArgumentException("Origin can't be null");

        if (maxSpeed > 0)
            this.maxSpeed = maxSpeed;
            speedLimit = maxSpeed;
        else
            throw new IllegalArgumentException("Max Speed must be positive");
        
        if (contLimit >= 0)
            exccessContaminationAlarm = contLimit;
        else
            throw new IllegalArgumentException("Cont Limit must be at least 0");

        if (length > 0)
            this.length = length;
        else
            throw new IllegalArgumentException("Road length must be positive");

        if (weather != null)
            this.weather = weather;
        else
            throw new IllegalArgumentException("Weather can't be null");

    }

    protected void enter(Vehicle v){
        if (v.getLocation() == 0 && v.getSpeed() == 0)
            lVehicles.add(v);
        else
            throw new IllegalArgumentException("Vehicle speed and location must be 0");
    }

    protected void exit(Vehicle v){
        if(lVehicles.remove(v) == false)
            throw new IllegalArgumentException("This vehicle is not on the Road");
        
    }

    protected void setWeather(Weather w){
        if (w != null)
            weather = w;
        else
            throw new IllegalArgumentException("Weather can't be null");
    }

    protected void addContamination(int c){
        if (c >= 0)
            totalContamination += c;
        else
            throw new IllegalArgumentException("Contamination can't be negative");

    }

    protected abstract void reduceTotalContamination();

    protected abstract void updateSpeedLimit();

    protected abstract int calculateVehicleSpeed(Vehicle v);

   
    @Override
    protected void advance(int time){
        if(totalContamination != 0)
            reduceTotalContamination();
        
        updateSpeedLimit();
        for(int i= 0; i < lVehicles.size(); i++){
            if(calculateVehicleSpeed(lVehicles.get(i)) > maxSpeed)
                 lVehicles.get(i).setSpeed(maxSpeed);
            else
                lVehicles.get(i).setSpeed(calculateVehicleSpeed(lVehicles.get(i)));

            lVehicles.get(i).advance(time);
        }
        Collections.sort(lVehicles, .); // TO DO ordenar la lista
    }

    public JSONObject report() {
        JSONObject jo= new JSONObject();
        jo.put("id", _id);
        jo.put("speedlimit", maxSpeed);
        if(weather==Weather.CLOUDY) {
            jo.put("weather","CLOUDY" );
        }
        else if(weather==Weather.RAINY) {
            jo.put("weather","RAINY" );
        }
        else if(weather==Weather.STORM) {
            jo.put("weather","STORM" );
        }
        else if(weather==Weather.SUNNY) {
            jo.put("weather","SUNNY" );
        }
        else if(weather==Weather.WINDY) {
            jo.put("weather","WINDY" );
        }
        jo.put("co2", totalContamination);
        JSONArray listV= new JSONArray();
        for(Vehicle i:lVehicles) {
            listV.put(i.getId());
        }
        jo.put("vehicles", listV);
        return jo;
    }

}



