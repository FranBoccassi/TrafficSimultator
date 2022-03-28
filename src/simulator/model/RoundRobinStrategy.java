package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy{
	private int timeSlot;
	public RoundRobinStrategy(int k){
		timeSlot=k;
	}
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,int currTime) {
		if(timeSlot<0)
			timeSlot=1;
		if(roads.size()==0)
			return -1;
		if(currGreen==-1)
				return 0;
		if(currTime-lastSwitchingTime<timeSlot)
				return currGreen;
		if(roads.size()==currGreen+1)
			return 0;
		
		int i=currGreen+1;
		return i;
	}
}