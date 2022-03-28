package simulator.factories;
import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy> {

    public MostCrowdedStrategyBuilder() {
        super("most_crowded_lss");
    }

    @Override
    protected MostCrowdedStrategy createTheInstance(JSONObject data) {
    	MostCrowdedStrategy r;
    	if(data.length()!=0)
    		 r= new MostCrowdedStrategy(data.getInt("timeslot"));
    	else
    		 r= new MostCrowdedStrategy(1);	
        return r;
    }

}