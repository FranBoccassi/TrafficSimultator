package simulator.model;

public class InterCityRoad extends Road {

    public InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}
    @Override
    protected void reduceTotalContamination() {
        int x=0;
		if(weather==Weather.SUNNY) {
			x=2;
		}
		else if(weather==Weather.CLOUDY) {
			x=3;
		}
		else if(weather==Weather.RAINY) {
			x=10;
		}
		else if(weather==Weather.WINDY) {
			x=15;
		}
		else if(weather==Weather.STORM) {
			x=20;
		}
		totalContamination=(int)(((100.0-x)/100.0)*totalContamination);
        
    }

    @Override
    protected void updateSpeedLimit() {
        if(totalContamination>exccessContaminationAlarm)
        speedLimit=maxSpeed/2;
        else
        speedLimit=maxSpeed;
    }

    @Override
    protected int calculateVehicleSpeed(Vehicle v) {
        int s=0;
		if(weather==Weather.STORM) {
			s=(int)Math.ceil(v.getSpeed()*0.8);
			return s;
		}
		else {
			return maxSpeed;
		}
    }
    
    
}
