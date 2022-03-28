package simulator.model;

public class InterCityRoad extends Road{
	
	public InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	public void reduceTotalContamination() {
		int x=0;
		if(weather==Weather.SUNNY) x=2;
		else if(weather==Weather.CLOUDY) x=3;
		else if(weather==Weather.RAINY) x=10;
		else if(weather==Weather.WINDY) x=15;
		else if(weather==Weather.STORM) x=20;
		int y=(int)(((100.0-x)/100.0)*totalCont);
		totalCont=y;
	}
	public void updateSpeedLimit() {
		if(totalCont>alarm&&maxSpeed==limitSpeed) {
			maxSpeed=(int)(maxSpeed*0.5);
		}
		else if(totalCont<=alarm)
			maxSpeed=limitSpeed;
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		if(weather==Weather.STORM) {
			return (int)Math.ceil(v.getSpeed()*0.8);
		}
		else {
			return maxSpeed;
		}
	}

}