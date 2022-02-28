package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
    
    private int timeSlot;

    public MostCrowdedStrategy(int ticks){
        timeSlot = ticks;
    }

    @Override
    public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime){
        int tam = qs.get(0).size();
        int pos = 0;

        if(roads.isEmpty())
            return -1;

        if(currGreen == -1){
            for(int i = 1; i < qs.size(); i++){
                if(tam < qs.get(i).size()){
                    tam = qs.get(i).size();
                    pos = i;
                }
            }   
            return pos;
        }   
        
        
        if((currTime - lastSwitchingTime) < timeSlot)
            return currGreen;

        
        for(int i = 1; i < qs.size(); i++){
             if(tam < qs.get(i).size())
                tam = qs.get(i).size();
                 pos = i;
            }
        return pos;

    }


    
}

