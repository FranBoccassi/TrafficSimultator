package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{
	private int timeSlot;
	public MostCrowdedStrategy(int k){
		timeSlot=k;
	}
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {
		int x=qs.get(0).size();
		int y=0;
		if(timeSlot<0) 
			timeSlot=1;
		if(roads.size()==0)
			return -1;
		if(currGreen==-1) {
			for(int i=1;i<qs.size();i++) {
				if(qs.get(i).size()>x) {
					x=qs.get(i).size();
					y=i;
				}
			}
			return y;
			}
		if(currTime-lastSwitchingTime <timeSlot)
			return currGreen;
		
	for(int i=currGreen+1;i<qs.size();i++) {
		if(qs.get(i).size()>=x) {
			x=qs.get(i).size();
			y=i;
		}
	}
	return y;
	}
}