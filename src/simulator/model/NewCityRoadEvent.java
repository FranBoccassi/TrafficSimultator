package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent {

    public NewCityRoadEvent(int time, String id, String src, String dest, int maxspeed, int co2limit, int length, Weather weather) {
        super(time, id, src, dest, maxspeed, co2limit, length, weather);
    }

    @Override
    Road CreateRoad(RoadMap map) {
        CityRoad cityroad=new CityRoad(id, map.getJunction(src), map.getJunction(dest), length, co2limit, maxspeed, weather);
        return cityroad;
    }
    
}
