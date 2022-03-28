package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject {
	protected Junction origin;
	protected Junction destination;
	protected int length;
	protected int maxSpeed;
	protected int limitSpeed;
	protected int alarm;
	protected Weather weather;
	protected int totalCont;
	protected List<Vehicle> listVehicles;
	protected int cont;
	
	public Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
			super(id);
			totalCont=0;
			listVehicles= new ArrayList<Vehicle>();
			if(maxSpeed<=0)
				 throw new IllegalArgumentException("Speed must be positive");
			else {
				this.maxSpeed=maxSpeed;
				limitSpeed=maxSpeed;
				if(contLimit<0)
					 throw new IllegalArgumentException("contLimit must be positive");
				else {
					alarm=contLimit;
					if(length<=0)
						throw new IllegalArgumentException("Length must be positive");
					else {
						this.length=length;
						if(weather==null) {
							throw new IllegalArgumentException("Incorrect Weather");
						}
						else
							this.weather=weather;
						if(destJunc==null||srcJunc==null)
								throw new IllegalArgumentException("Contamination must be positive");
							else {
								origin=srcJunc;
								destination=destJunc;
								origin.addOutGoingRoad(this);
							}
						}
					}
				}
			cont=listVehicles.size();
			}
	void enter(Vehicle v) {
		if(v.getSpeed()!=0||v.getLocation()!=0) {
			throw new IllegalArgumentException("Contamination must be positive");
		}
		else {
			listVehicles.add(v);
			cont++;
		}
	}
	void exit(Vehicle v) {	
		for(int i=0; i<listVehicles.size();i++) {
			if(listVehicles.get(i)==v) {
				listVehicles.remove(i);
				cont--;
		}
		}
	}
	
	void setWeather(Weather w) {
		if(w==null) {
			throw new IllegalArgumentException("Incorrect Weather");
		}
		else {
			weather=w;
		}
	}
	
	void addContamination(int c) {
		if(c<0) {
			throw new IllegalArgumentException("Contamination can´t be negative");
		}
		else {
			totalCont+=c;
		}
	}
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);
	@Override
	protected void advance(int time) {
		if(this.totalCont!=0) {
		reduceTotalContamination();
		}
		updateSpeedLimit();
		for(int i=0; i<listVehicles.size();i++) {
			if(maxSpeed<=calculateVehicleSpeed(listVehicles.get(i))) {
				listVehicles.get(i).setSpeed(maxSpeed);
				listVehicles.get(i).advance(time);
			}
			else {
			listVehicles.get(i).setSpeed(calculateVehicleSpeed(listVehicles.get(i)));
			listVehicles.get(i).advance(time);
			}
		}
		Collections.sort(listVehicles, new comparatorVehicle()); 
	}
	public JSONObject report() {
		JSONObject jo= new JSONObject();
		jo.put("id", _id);
		jo.put("speedlimit", maxSpeed);
		if(weather==Weather.CLOUDY)
			jo.put("weather","CLOUDY" );
		else if(weather==Weather.RAINY)
			jo.put("weather","RAINY" );
		else if(weather==Weather.STORM)
			jo.put("weather","STORM" );
		else if(weather==Weather.SUNNY)
			jo.put("weather","SUNNY" );
		else if(weather==Weather.WINDY)
			jo.put("weather","WINDY" );
		jo.put("co2", totalCont);
		JSONArray listV= new JSONArray();
		for(Vehicle i:listVehicles) {
			listV.put(i.getId());
		}
		jo.put("vehicles", listV);
		return jo;
	}
	
	//Getters
	public Weather getWeather() {
		return this.weather;
	}
	public Junction getDest() {
		return destination;
	}
	public Junction getSrc() {
		return origin;
	}
	public int getLength() {
		return length;
	}
	public int getMaxSpeed() {
		return this.limitSpeed;
	}
	public int getSpeedLimit() {
		return this.maxSpeed;
	}
	public int getTotalCO2() {
		return this.totalCont;
	}
	public int getContLimit() {
		return this.alarm;
	}
	public List<Vehicle> getVehicles(){
		return listVehicles;
	}
}