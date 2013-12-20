package behaviours;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector2d;

import bioSimulation.Agent;

public class Movement extends Behaviour {

	int modifiedSpeed;
	Random rnd = new Random();
	Vector2d carbVelocity = new Vector2d(0,0);
	
	
	public Movement (int cruiseSpeed)
	{
		modifiedSpeed = cruiseSpeed/10;
		
	}
	@Override
	public void Update(Agent agent,ArrayList<Agent> population) {
		
		
		if(agent.getVelocity().length() > modifiedSpeed)
		{
			carbVelocity = agent.getVelocity();
			carbVelocity.scale(limitSpeed(agent.getVelocity()));
			agent.setVelocity(carbVelocity);
		}
	}

	

	@Override
	public void initialise(Agent agent) {

		agent.setMaxSpeed(modifiedSpeed);
		//agent.setVelocity(new Vector2d(rnd.nextInt(5),rnd.nextInt(5)));
		
		
		
	}
	
	public double limitSpeed(Vector2d vel){
        if(vel.length() > modifiedSpeed)
            return modifiedSpeed/vel.length();
        else
            return 1.0f;
    }

}
