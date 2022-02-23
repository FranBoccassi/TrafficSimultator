package simulator.model;

public class CityRoad extends Road {

    public CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }
    @Override
    protected void reduceTotalContamination() {

        if(weather==Weather.WINDY || weather== Weather.STORM)
            totalContamination-=10;
        else
            totalContamination-=2;
        if(totalContamination<0) 
            totalContamination=0;
    }

    @Override
    protected void updateSpeedLimit() {
        speedLimit=maxSpeed;
    }

    @Override
    protected int calculateVehicleSpeed(Vehicle v) {
        int x;
        x=(int)Math.ceil(((11.0-v.getContClass())/11.0)*speedLimit);
        return x;
    }

}