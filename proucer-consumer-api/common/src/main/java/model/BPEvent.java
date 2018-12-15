	package model;

	import java.util.HashMap;
	import java.util.Map;

	public class BPEvent {
	    private String type;
	    private Map<String, Integer> data = new HashMap();
	    private int numOfInstances;

	    public BPEvent() {
	    }

	    public String getType() {
	        return this.type;
	    }

	    public void setType(String type) {
	        this.type = type;
	    }

	    public Map<String, Integer> getData() {
	        return this.data;
	    }

	    public void setData(Map<String, Integer> data) {
	        this.data = data;
	    }

	    public int getNumOfInstances() {
	        return this.numOfInstances;
	    }

	    public void incrementNumOfInstances() {
	        ++this.numOfInstances;
	    }

	    public void setNumOfInstances(int numOfInstances) {
	        this.numOfInstances = numOfInstances;
	    }
	}

