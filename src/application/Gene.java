package application;

public class Gene {
	
	//Properities of Genes and their selection order 

	private String groups;
	private int Fch;
	private int Sch;
	private int Tch;
	private int ProIndex;
	private String topic;
	private String SuperVisor;
	private int Fitness;
	
	
	
	
	//constructor
	public Gene(String groups, int fch, int sch, int tch, int proIndex, String topic, String superVisor, int fitness) {
		super();
		this.groups = groups;
		Fch = fch;
		Sch = sch;
		Tch = tch;
		ProIndex = proIndex;
		this.topic = topic;
		SuperVisor = superVisor;
		Fitness = fitness;
	}

     


// getters and setters
	public String getGroups() {
		return groups;
	}


	public void setGroups(String groups) {
		this.groups = groups;
	}

	public int getFch() {
		return Fch;
	}

	public void setFch(int fch) {
		Fch = fch;
	}

	public int getSch() {
		return Sch;
	}

	public void setSch(int sch) {
		Sch = sch;
	}

	public int getTch() {
		return Tch;
	}

	public void setTch(int tch) {
		Tch = tch;
	}

	public int getProIndex() {
		return ProIndex;
	}


	public void setProIndex(int proIndex) {
		ProIndex = proIndex;
	}


	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}


	public String getSuperVisor() {
		return SuperVisor;
	}


	public void setSuperVisor(String superVisor) {
		SuperVisor = superVisor;
	}


	public int getFitness() {
		return Fitness;
	}

	public void setFitness(int fitness) {
		Fitness = fitness;
	}



	

	//calcluate the fitnness of gene based on firstchoise ,second choise and third choise 
	public void calFitness() {
		
		if(getFch() == getProIndex())
			setFitness(3);
		else if(getSch() == getProIndex())
			setFitness(2);	
		else if(getTch() == getProIndex())
			setFitness(1);
		else
			setFitness(0);
		
	}//end method 
	
	
	
	
	
	
	
}//end class

