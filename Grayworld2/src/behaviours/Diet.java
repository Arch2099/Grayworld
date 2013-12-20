package behaviours;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Vector2d;

import bioSimulation.Agent;
import bioSimulation.World;

public class Diet extends Behaviour {

	private int dietType;
	private Vector2d eatingVec = new Vector2d(0,0);
	private int attractionRadius = 50;
	private float attractionFactor = 0.3f;
	 private Vector2d velModifier = new Vector2d(0,0);
	 private float maxSpeed;
	
	 public Diet(int dietType)
	 {
		 this.dietType = dietType;
	 }
	 
	@Override
	public void initialise(Agent agent) {
		// TODO Auto-generated method stub
		this.maxSpeed = agent.getMaxSpeed();
		agent.setAgentType(dietType);
	}
	@Override
	public void Update(Agent agent,ArrayList<Agent> population) {

		if (agent.getEnergy() >= 200) {
			eatingVec.set(0, 0);			
			Vector2d thisPos = new Vector2d(0, 0);
			//foreach start
			for (Agent otherAgent : population) {
				//solve this diet problem
				if (dietType ==otherAgent.getKingdom()) {
					thisPos.set(agent.getPosition());
					thisPos.sub(otherAgent.getPosition());
									
					if ((thisPos.length() < agent.getInteractionRange())
							&& (thisPos.length() > 0.001)) {
						otherAgent.setDevoured(true); //gnam
						agent.addEnergy(otherAgent.getSize()*50);
						break;
					}
					
					if ((thisPos.length() < attractionRadius)
							&& (thisPos.length() > 0.001)) {
						eatingVec.add(otherAgent.getPosition());
						eatingVec.sub(agent.getPosition());
						eatingVec.scale(attractionFactor);
						
						break;
					}
				}

			}
			
			velModifier.add(eatingVec);
			velModifier.add(agent.getVelocity());
			// System.out.println(velModifier.length());
			velModifier.scale(limitSpeed(velModifier));
			agent.setVelocity(velModifier);
			
						
		}
		
		if (agent.getKingdom() == 2)	// to be completed
			if (World.resourceGather(agent.getPosition())) {
				agent.addEnergy(+3);
			}
		

	}
	
	public double limitSpeed(Vector2d vel){
        if(vel.length() > maxSpeed)
            return maxSpeed/vel.length();
        else
            return 1.0f;
    }

		
		
	}

	


