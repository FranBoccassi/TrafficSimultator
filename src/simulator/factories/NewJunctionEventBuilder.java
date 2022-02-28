package simulator.factories;
import org.json.JSONArray;
import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<NewJunctionEvent> {
    Factory<LightSwitchingStrategy> lssFactory;
    Factory<DequeuingStrategy> dqsFactory;

    public NewJunctionEventBuilder(Factory<LightSwitchingStrategy> lssFactory, Factory<DequeuingStrategy> dqsFactory) {
        super("new_junction");
        this.dqsFactory=dqsFactory;
        this.lssFactory=lssFactory;
    }

    @Override
    protected NewJunctionEvent createTheInstance(JSONObject data) {
        LightSwitchingStrategy ls= lssFactory.createInstance(data.getJSONObject("ls_strategy"));
        DequeuingStrategy dq= dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
        JSONArray coords = data.getJSONArray("coor");
        NewJunctionEvent e= new NewJunctionEvent(data.getInt("time"),data.getString("id"),ls, dq, coords.getInt(0),coords.getInt(1));
        return e;
    }

}
