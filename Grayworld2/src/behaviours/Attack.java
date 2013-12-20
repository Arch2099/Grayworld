package behaviours;

import java.util.ArrayList;

import javax.vecmath.Vector2d;

import bioSimulation.Agent;

public class Attack extends Behaviour {

	
	
	
	private int damage;
	private int aggressionRange;
	private float aggressionFactor = 0.8f;
	private int determination;
	private Vector2d velModifier = new Vector2d(0,0);	
	private Vector2d attackVec = new Vector2d(0,0);
	private float maxSpeed;
	private float oldSpeed;
	private float sprint;
	private ArrayList<Integer> attackList = new ArrayList<Integer>();
	//private 
	
	public Attack(int damage,ArrayList<Integer> attackList,int aggresionRange,int determination)
	{
		this.damage = damage;
		this.aggressionRange = 50;//= aggresionRange/5;
		this.determination = determination;
		this.attackList = attackList;
	}
	
	
	
	
	
	@Override
	public void initialise(Agent agent) {
		// TODO Auto-generated method stub
		this.maxSpeed = agent.getMaxSpeed();
		sprint = maxSpeed*1.5f;
	}
	
	@Override
	public void Update(Agent agent, ArrayList<Agent> population) {
		
			attackVec.set(0, 0);

			Vector2d thisPos = new Vector2d(0, 0);

			for (Agent otherAgent : population) {
				if ((attackList.contains(otherAgent.getSpecie()) && otherAgent.isAlive())) {
					thisPos.set(agent.getPosition());
					thisPos.sub(otherAgent.getPosition());

					if ((thisPos.length() < agent.getInteractionRange())
							&& (thisPos.length() > 0.001)) {
						
						otherAgent.takeDamage(damage);
						break;
					}
					if ((thisPos.length() < aggressionRange)
							&& (thisPos.length() > 0.001)) {
						attackVec.add(otherAgent.getPosition());
						attackVec.sub(agent.getPosition());
						attackVec.scale(aggressionFactor);
						agent.setMaxSpeed(sprint);
						maxSpeed = sprint;
						break;
					}
				}
			}

			velModifier.add(attackVec);
			velModifier.add(agent.getVelocity());
			// System.out.println(velModifier.length());
			velModifier.scale(limitSpeed(velModifier));
			agent.setVelocity(velModifier);
		

	}

	public double limitSpeed(Vector2d vel){
        if(vel.length() > maxSpeed)
            return maxSpeed/vel.length();
        else
            return 1.0f;
    }

}
