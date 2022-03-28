package simulator.model;

public class CityRoad extends Road {

	public CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	void reduceTotalContamination() {
        int x=0;
        if(weather==Weather.WINDY) x=10;
        else if(weather==Weather.STORM)x=10;
        else  x=2;
        x=totalCont-x;
        if(x<0) x=0;
        else totalCont=x;
	}
	@Override
	void updateSpeedLimit() {}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		return (int)Math.ceil(((11-v.getContClass())*limitSpeed)/11);
	}
}