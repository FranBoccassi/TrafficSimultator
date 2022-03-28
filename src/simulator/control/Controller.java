package simulator.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {


    private TrafficSimulator sim;
    private Factory<Event> _eventsFactory;
    
    public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
    	this.sim=sim;
    	this._eventsFactory= eventsFactory;
    }

    
        
   public void loadEvents(InputStream in) {
    if(in==null) {
    
    }
    else {
    JSONObject jsonInupt = new JSONObject(new JSONTokener(in));
    JSONArray events = jsonInupt.getJSONArray("events"); 
    for(int i = 0; i < events.length(); i++) {
        sim.addEvent(_eventsFactory.createInstance(events.getJSONObject(i)));
    }
    events=null;
    }
}
    public void run(int n, OutputStream out) {
        if (out == null)
        { out = new OutputStream() {
           public void write(int b) throws IOException {} 
           };
           } PrintStream p = new PrintStream(out);
           p.println("{");
           p.println(" \"states\": [");
           for(int i=0; i<n-1;i++) {
               sim.advance();
               p.print(sim.report());
               p.println(",");
           }
           sim.advance(); p.print(sim.report());
           p.println("\n]");  // no pone en lista_vehiculo los vehiculos
           p.println("}");

        }
    public void reset() {
    	sim.reset(); 
        }

      public void addEvent(Event e) {
    	  sim.addEvent(e);
      }

      public void addObserver(TrafficSimObserver o){
          sim.addObserver(o);
      }

      public void removeObserver(TrafficSimObserver o){
        sim.removeObserver(o);
     }

       }