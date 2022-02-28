package simulator.factories;
import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy> {

    public RoundRobinStrategyBuilder() {
        super("round_robin_lss");
    }

    @Override
    protected RoundRobinStrategy createTheInstance(JSONObject data) {
        RoundRobinStrategy r= new RoundRobinStrategy(data.getInt("timeslot"));
        return r;
    }


}
