/*************************************************************************
This class produce all the living elements(agents) of the simulation.
All the characteristics of the agents are determinate by their DNA which
contain the information in the following format :










**************************************************************************** */
package bioSimulation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.vecmath.Vector2d;

import behaviours.Attack;
import behaviours.Behaviour;
import behaviours.Diet;
import behaviours.Fear;
import behaviours.Movement;
import behaviours.Reproduction;
import behaviours.Senses;
import behaviours.Swarm;

public class LifeFactory {
	
	public static int MOVEMENT_GENE = 0;
	public static int DIET_GENE = 1;
	public static int SENSES_GENE = 2;
	public static int REPROD_GENE = 3;
	public static int FEAR_GENE = 4;
	public static int ATTACK_GENE = 5;
	
	public static int CHROMOSOME1 = 0;
	public static int CHROMOSOME2 = 1;
	public static int CHROMOSOME3 = 2;
	
	public static int CARNIVOROUS = 4;
	public static int ERBIVOROUS = 3;
	
	private ArrayList<String> geneList = new ArrayList<String>(); // token which hold the information to create a behaviour
	private ArrayList<Behaviour> behaviours = new ArrayList<Behaviour>(); 
	private int upkeep;  										// agent's passive energy used at rest. it increases with complexity/number of features
	private String chromoDelimiter = "/";							 //separates chromosomes inside the DNA
	private String geneDelimiter = "-";								// separates genes inside the chromosome
	private String intraGeneDelimiter = ",";
	private int redColor;
	private int greenColor;
	private int blueColor;
	private Color agentColor;
	private double mutationProbability = 0.01;
	
	public LifeFactory() {};
	
	public Agent createAgent(Vector2d position,String chromosome1, String chromosome2, String chromosome3)
	{
		Agent agent = null;
		
		String[] geneList1 = chromosome1.split(geneDelimiter); 
		String[] geneList2 = chromosome2.split(geneDelimiter);
		String[] geneList3 = chromosome3.split(geneDelimiter);			
		
		return agent;
	}
	
	public Agent createAgent(Vector2d position,String DNA)
	{
		behaviours = new ArrayList<Behaviour>();
		redColor = 0;
		greenColor = 0;
		blueColor = 0;
		String[] chromosomes = DNA.split(chromoDelimiter);   // separate the list of chromosome
		String[] geneList1 = chromosomes[CHROMOSOME1].split(geneDelimiter);  // separate the list of genes
		System.out.println(DNA);
		String[] geneList2 = chromosomes[CHROMOSOME2].split(geneDelimiter);
		String[] geneList3 = chromosomes[CHROMOSOME3].split(geneDelimiter);
		
		String[] exon = geneList1[CHROMOSOME1].split(intraGeneDelimiter); // part of the gene which contain the information
		
		if ( Integer.parseInt(exon[MOVEMENT_GENE]) > 50)
		{
			
			redColor += 25;
			Behaviour movement = new Movement(Integer.parseInt(exon[1]));
			//redColor +=Integer.parseInt(exon[1])/10;
			behaviours.add(movement);
		}
		
		String[] exon1 = geneList1[DIET_GENE].split(intraGeneDelimiter);
		
		if (Integer.parseInt(exon1[0]) <60)
		{
			Behaviour diet = new Diet(CARNIVOROUS);
			behaviours.add(diet);
		}
		if  (Integer.parseInt(exon1[0]) > 61)
		{
			Behaviour diet = new Diet(ERBIVOROUS);
			behaviours.add(diet);
		}
		
		String[] exon2 = geneList1[SENSES_GENE].split(intraGeneDelimiter);
		
		if ( Integer.parseInt(exon2[0]) > 50)
		{
			
			Behaviour senses = new Senses( Integer.parseInt(exon2[1]));
			behaviours.add(senses);
		}
		
		String[] exon3 = geneList1[REPROD_GENE].split(intraGeneDelimiter);
		
		if ( Integer.parseInt(exon3[0]) > 50)
		{
			Behaviour reproduction = new Reproduction(Integer.parseInt(exon3[1]));
			behaviours.add(reproduction);
		}
		
		String[] exon4 = geneList1[FEAR_GENE].split(intraGeneDelimiter);
		
		
		if ( Integer.parseInt(exon4[0]) > 50)
		{
			greenColor += 10;
			//redColor -= 25;
			blueColor += 75;
			ArrayList<Integer> fearSwitchList = new ArrayList<Integer>();
			String[] switchGeneList = geneList3[FEAR_GENE].split(intraGeneDelimiter);
			for (String geneSwitch : switchGeneList)
			{
				fearSwitchList.add(Integer.parseInt(geneSwitch));
			}
			
			Behaviour fear = new Fear(fearSwitchList,Integer.parseInt(exon3[1]),Integer.parseInt(exon3[2]));
			behaviours.add(fear);
		}
		
		String[] exon5 = geneList1[ATTACK_GENE].split(intraGeneDelimiter);
		
		if ( Integer.parseInt(exon5[0]) > 50)
		{
			//greenColor -= 10;
			redColor += 75;
			//blueColor -= 45;
			ArrayList<Integer> attackSwitchList = new ArrayList<Integer>();
			String[] switchGeneList = geneList3[ATTACK_GENE].split(intraGeneDelimiter);
			for (String geneSwitch : switchGeneList)
			{
				attackSwitchList.add(Integer.parseInt(geneSwitch));
			}
			
			Behaviour attack = new Attack(Integer.parseInt(exon5[0]),attackSwitchList,Integer.parseInt(exon4[1]),Integer.parseInt(exon3[2]));
			behaviours.add(attack);
		}
		
		
		
		System.out.println(redColor + " " + greenColor+ " " + blueColor);
		agentColor = new Color(redColor,greenColor,blueColor);
		upkeep = 0;
		
		//test
				
		Agent agent = new Agent(position,1,100,100,4,agentColor,behaviours,upkeep,DNA);
		
		// dummy genes
		
		return agent;		
	}
	
	public Agent createTestAgent()
	{
		Random rnd = new Random();
		Agent agent = new Agent(new Vector2d(rnd.nextInt(255),rnd.nextInt(255)),5);
		return agent;
	}
	
	
	public String HaploidCrossOver(String DNA1,String DNA2)
	{
		String childDNA= ""; 
		
		// deconstruct first parent DNA
		String[] chromosomes1 = DNA1.split(chromoDelimiter);
		String[] geneList1a = chromosomes1[0].split(geneDelimiter);
		String[] geneList2a = chromosomes1[1].split(geneDelimiter);
		String[] geneList3a = chromosomes1[2].split(geneDelimiter);
		
		// deconstruct second parent DNA
		String[] chromosomes2 = DNA2.split(chromoDelimiter);
		String[] geneList1b = chromosomes2[0].split(geneDelimiter);
		String[] geneList2b = chromosomes2[1].split(geneDelimiter);
		String[] geneList3b = chromosomes2[2].split(geneDelimiter);
		
		// strings for the recombined gene
		String[] geneList1c = new String[geneList1a.length];
		String[] geneList2c = new String[geneList2a.length];
		String[] geneList3c = new String[geneList3a.length];
		
		
		// each gene is taken from one of the 2 parents with 50% probability		
		// recombination of the first 2 chromosome
		for(int i =0; i <geneList1c.length; i++)
		{
			Random rnd = new Random();
			if(rnd.nextDouble() > 0.5)
			{
				geneList1c[i] = mutate(geneList1a[i]) + geneDelimiter;
			}
			else
			{
				geneList1c[i] = mutate(geneList1b[i]) + geneDelimiter;
			}	
			childDNA = childDNA+geneList1c[i];
		}
		childDNA = childDNA.substring(0, childDNA.length()-1); // eliminate the extra "-" delimiter
		childDNA += chromoDelimiter;  // add end of chromosome sign
		
		
		// recombination of the second 2 chromosome
		for(int i =0; i <geneList2c.length; i++)
		{
			Random rnd = new Random();
			if(rnd.nextDouble() > 0.5)
			{
				geneList2c[i] = geneList2a[i] + geneDelimiter;
			}
			else
			{
				geneList2c[i] = geneList2b[i] + geneDelimiter;
			}			
			childDNA = childDNA+geneList2c[i];
		}
		childDNA = childDNA.substring(0, childDNA.length()-1); // eliminate the extra "-" delimiter
		childDNA += chromoDelimiter; // add end of chromosome sign
		
		// recobination of the last(3rd) chromosome
		
		for(int i =0; i <geneList3c.length; i++)
		{
			Random rnd = new Random();
			if(rnd.nextDouble() > 0.5)
			{
				geneList3c[i] = geneList3a[i] + geneDelimiter;
			}
			else
			{
				geneList3c[i] = geneList3b[i] + geneDelimiter;
			}		
			childDNA = childDNA+geneList3c[i];
		}
		childDNA = childDNA.substring(0, childDNA.length()-1); // eliminate the extra "-" delimiter
		
		return childDNA;
	}
	
	public String mutate(String geneToMutate)
	{
		Random rnd = new Random();
		String geneM = "";
		String[] exonToMutate = geneToMutate.split(intraGeneDelimiter); // separate the strings in the genes(removing commas)
		for (String exonM : exonToMutate)
		{
			if(rnd.nextDouble() < mutationProbability)
			{				
				int exonMVal = Integer.parseInt(exonM);         // trasform the string in to an int
				String exonBinary = Integer.toBinaryString(exonMVal);    // trasform the value to binary
				String tempExon = "";
				for(int i=0; i<(8-exonBinary.length()); i++)			//**********************					
				{														//
					tempExon += "0";									//	fill the missing 0
				}														//*******************
				tempExon += exonBinary;
				int mutatingBit = rnd.nextInt(8);
				if(tempExon.charAt(mutatingBit) == '1')             // pick one of the 8 bit and switch value.
				{
					exonM = tempExon.substring(0,mutatingBit) +'0' + tempExon.substring(mutatingBit+1);
				}
				else
				{
					exonM = tempExon.substring(0,mutatingBit) +'1' + tempExon.substring(mutatingBit+1);
				}
			
				exonMVal = Integer.parseInt(exonM, 2);		// transform the binary value in int
				geneM += exonMVal + intraGeneDelimiter;       // rebuild the gene
				System.out.println("mutated!");
			}
			else
			{
				geneM += exonM + intraGeneDelimiter;
			}
			      
			
			
		}
		geneM = geneM.substring(0, geneM.length()-1);  // remove the last intragenedelimiter 
		
		return geneM;
	}
	
}
