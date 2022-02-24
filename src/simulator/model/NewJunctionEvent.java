package simulator.model;

public class NewJunctionEvent extends Event {

    private int time;
	private  String id;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor; 
	private int yCoor; 

    public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
        super(time);
        this.id=id;
        this.lsStrategy=lsStrategy;
        this.dqStrategy=dqStrategy;
        this.xCoor=xCoor;
        this.yCoor=yCoor;
    }

    @Override
    void execute(RoadMap map) {
       Junction junction= new Junction(id, lsStrategy, dqStrategy, xCoor, yCoor);
       map.addJunction(junction);
        
    }
    
}
