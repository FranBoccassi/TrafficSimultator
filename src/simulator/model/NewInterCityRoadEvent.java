package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent {

    public NewInterCityRoadEvent(int time, String id, String src, String dest, int maxspeed, int co2limit, int length, Weather weather) {
        super(time, id, src, dest, maxspeed, co2limit, length, weather);
        //TODO Auto-generated constructor stub
    }

    @Override
    Road CreateRoad(RoadMap map) {
        InterCityRoad interCityRoad=new InterCityRoad(id, map.getJuntion(src), map.getJuntion(dest), length, co2limit, maxspeed, weather);
		return interCityRoad;
    }
    
}
