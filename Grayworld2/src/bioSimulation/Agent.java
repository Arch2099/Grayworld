package bioSimulation;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector2d;


import behaviours.*;

public class Agent {
	
	private final int PROTISTA = 0;
	private final int PLANTAE = 1;
	private final int ANIMALIA = 2;
	private final int interactionRange = 10;
	private int senseRange;
	private ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>();
	private Vector2d position = new Vector2d(0,0);
	private Vector2d velocity = new Vector2d(0,0);
	private float maxSpeed;
	private Boolean mated = false;
	private int agentType;
	private int kingdom;  // 0= animalia 1=plantae 2=protista
	
	public int getKingdom() {
		return kingdom;
	}

	private boolean isAlive;
	private boolean devoured = false;
	private int health;
	private float energy;
	private float upkeep;
	private int size;
	private Color color;
	private String DNA;
	private String partnerDNA;
	private int specie;
	
	private int specieR;
	private int specieG;
	private int specieB;
	
	
	
	public Agent(Vector2d initialPosition,int kingdom, int initialHealth, int initialEnergy, int initialSize, 
			Color agentColor, ArrayList<Behaviour> behavioursList,int upkeep,String DNA)
	{
		isAlive = true;
		behaviours = behavioursList;
		position.set(initialPosition);
		health = initialHealth;
		energy = initialEnergy;
		size = initialSize;
		color = agentColor;	
		
		this.kingdom = kingdom;
		this.upkeep = upkeep;
		this.DNA = DNA;
		partnerDNA = "";
	}
	
	/// constructor for tests
	public Agent(Vector2d initialPosition)
	{
		velocity = new Vector2d(0,0);
		position = initialPosition;
		health = 100;
		energy = 100;
		size = 10;
		color = Color.RED;		
	} //test end
	
	
	/// constructor for tests
	public Agent(Vector2d initialPosition, int sense)
	{
		senseRange = sense;
		isAlive = true;
		Random rnd = new Random();
		velocity = new Vector2d(rnd.nextInt(30)-15,rnd.nextInt(30)-15);
		Swarm swarm = new Swarm();
		//Behaviour fear = new Fear();
		//behaviours.add(fear);
		behaviours.add(swarm);
		position = initialPosition;
		health = 100;
		energy = 100;
		size = 4;
		color = Color.RED;			
	} /// test end
	
	public void initilise()
	{
		Random rnd = new Random();
		
		determineKingdom();
		determineSpecie();
		for(Behaviour behaviour : behaviours )
				{
					behaviour.initialise(this);
				}
		
		kingdomModifier();
		
	}
	
	

	private void determineKingdom() {
		if (kingdom < 43) {
			kingdom = PROTISTA ; // is a fungae, bacteria or protozoa
		} else if (kingdom >= 43 && kingdom < 128) {
			kingdom = PLANTAE; // is a plant
		} else
			kingdom = ANIMALIA; //is an animal 
	}

	private void kingdomModifier() {

		switch (kingdom) {
		case ANIMALIA: {
			
			break;
		}
		case PLANTAE: {
			
			break;
		}
		case PROTISTA: {
			
			break;
		}
		default: {
			System.out.println("something gone wrong ,kingdom: " + kingdom);
			break;
		}
		}
		
	}

	private void determineSpecie() {
		
		// species are determinate by their main shade
		// the first 3 most significant bits of the color red and green
		// are added to the first 2 most significant bits
		// generating a combination of 256 possible species
		specieR =color.getRed();
		specieG =color.getGreen();
		specieB =color.getBlue();
		
		String redString = Integer.toBinaryString(specieR);
		String tempString = "";
		for(int i=0; i<(8-redString.length()); i++)
		{
			tempString += "0";
		}
		redString = tempString + redString;
		redString = redString.substring(0, 3);
		
		String greenString = Integer.toBinaryString(specieG);
		
		tempString = "";
		for(int i=0; i<(8-greenString.length()); i++)
		{
			tempString += "0";
		}
		greenString =tempString + greenString;
		greenString = greenString.substring(0, 3);
		
		String blueString = Integer.toBinaryString(specieB);
		tempString = "";
		for(int i=0; i<(8-blueString.length()); i++)
		{
			tempString += "0";
		}
		blueString =tempString + blueString;
		blueString = blueString.substring(0, 2);
		
		specie= Integer.parseInt((redString + greenString + blueString), 2);
		System.out.println(specie);
		
	}

	public void Update(ArrayList<Agent> population)
	{
		
		//velocity.set(new Vector2d(rnd.nextInt(10)-5,rnd.nextInt(10)-5));
		//velocity =new Vector2d(7,3);
		
		
		
        
		//System.out.println("update");

		if (isAlive) {

			for (Behaviour behaviour : behaviours) {
				behaviour.Update(this, population);
			}
			position.add(velocity); // System.out.println(velocity.length());


			// energy consumed by agent
			//energy -= upkeep / 30;
			//energy -= (World.consumedEnergy(size, velocity.length())) / 30; // System.out.println(energy);

			if (energy <= 0) {
				health--;
			}
			if (health < 0) {
				isAlive = false;
			}

			if (position.x < 0)
				position.x = Gui.WIDTH;
			
			if (position.x > Gui.WIDTH)
				position.x = 0;
			
			if (position.y < 0)
				position.y = Gui.HEIGHT;
			
			if (position.y > Gui.HEIGHT)
				position.y = 0;
		}
		
	}
	
	public void paint(Graphics g)
	{		
		g.setColor(color);
		((Graphics2D) g).setStroke(new BasicStroke(2));
		g.drawOval((int)position.x-(size/2),(int)position.y-(size/2),size,size);
        g.drawLine((int)position.x,(int)position.y,(int)(position.x-velocity.x),(int)(position.y-velocity.y));
	}

	public Vector2d getPosition() {
		return position;
	}

	public void setPosition(Vector2d position) {
		this.position = position;
	}

	public Vector2d getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2d velocity) {
		this.velocity = velocity;
	}
	public void modifyVelocity(Vector2d modifier) {
		this.velocity.add(modifier);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSenseRange() {
		return senseRange;
	}

	public void setSenseRange(int senseRange) {
		this.senseRange = senseRange;
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public String getDNA() {
		return DNA;
	}

	public String getPartnerDNA() {
		return partnerDNA;
	}

	public void setPartnerDNA(String partnerDNA) {
		this.partnerDNA = partnerDNA;
	}

	public float getEnergy() {
		return energy;
	}

	public void addEnergy(float energy) {
		this.energy += energy;
	}

	public Boolean hasMated() {
		return mated;
	}

	public void setMated(Boolean mated) {
		this.mated = mated;
	}

	public int getInteractionRange() {
		return interactionRange;
	}

	public int getSpecie() {
		return specie;
	}

	public void setSpecie(int specie) {
		this.specie = specie;
	}

	public int getHealth() {
		return health;
	}

	public void takeDamage(int damage) {
		this.health -= damage;
	}

	public boolean isDevoured() {
		return devoured;
	}

	public void setDevoured(boolean devoured) {
		this.devoured = devoured;
	}

	public int getAgentType() {
		return agentType;
	}

	public void setAgentType(int agentType) {
		this.agentType = agentType;
	}

	
}
