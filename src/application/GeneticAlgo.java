package application;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GeneticAlgo {
	
	// varibles and constants

	public static Scanner s,s2;

	static ArrayList<String> strings = new ArrayList<String>();//for reading data
	static ArrayList<String> groups = new ArrayList<String>();//names of groups
	static ArrayList<Integer> Fchosise = new ArrayList<Integer>();//first choice of group
	static ArrayList<Integer> Schosise = new ArrayList<Integer>();//second choice of group 
	static ArrayList<Integer> Tchosise = new ArrayList<Integer>();//third choice of group
	
	static ArrayList<String> strings2 = new ArrayList<String>();//for reading data
	static ArrayList<Integer> pronum = new ArrayList<Integer>();//index of project
	static ArrayList<String> instructorName = new ArrayList<String>();//instructor name
	static ArrayList<String> projectName = new ArrayList<String>();// project name
	static ArrayList<int[]> chromosoms = new ArrayList<int[]>();// chromosomes of project
	
	
	
	//the assumption of best solution : 708
	static int solution = 17;
	static int [] bestchromosom = new int[37]; // bestchromosem 
	static boolean opt = false; //for while loop 
	
	
	
	
//********************************************************************//	
	// function to read data 
	public static void Reading() throws FileNotFoundException {
  
		
		 s = new Scanner(new File("Data.txt"));
		 s2 = new Scanner(new File("projects.txt"));
		 
		 

		while( s.hasNext()) {
			strings.add(s.nextLine());	   
		}
		System.out.println("*************************");
		
		//reading data 
		for (int i=1; i < strings.size(); i++) {
			String ss=strings.get(i);
			System.out.println(strings.get(i));
			String[] sp= ss.split("\t");// split for tap 
			groups.add(sp[0]);
			Fchosise.add(Integer.parseInt(sp[1].toString()));
			Schosise.add(Integer.parseInt(sp[2].toString()));
			Tchosise.add(Integer.parseInt(sp[3].toString()));
		}
		
		
		for(int k=0; k < groups.size();k++) {
			System.out.println("Group "+k+" :"+groups.get(k));
			System.out.println("first choise "+k+" :"+Fchosise.get(k));
			System.out.println("Second choise "+k+" :"+Schosise.get(k));
			System.out.println("Third choise "+k+" :"+Tchosise.get(k));	
		}
		
		
		
	
		while( s2.hasNext()) {
			strings2.add(s2.nextLine());	   
		}
		System.out.println("*************************");
		

		//reading data 
		for (int i=0; i < strings2.size(); i++) {
			String ss=strings2.get(i);
			System.out.println(strings2.get(i));
			String[] sp= ss.split("\t");
			pronum.add(Integer.parseInt(sp[0].toString()));
			instructorName.add(sp[1]);
			projectName.add(sp[2]);
		}
		
		
		
		for(int k=0; k < pronum.size();k++) { 
		System.out.println("Project number "+pronum.get(k));
		System.out.println("Instructor Name"+instructorName.get(k));
		System.out.println("project Name "+ projectName.get(k));
		}
		
		
		//------------------
		
		
        int [] parentsIndexs;//initialization the indexes of two highest chromosemss 
        int [] fitnees;   // fitnees of chromosomes
    	int [][] parentforcross; //two parent chromosom
    	int [][] children; // children of parents
    	
    	//make set of chromosems
    	chromosoms = Population();
    	//calcluate the fitness of all chromosems 
		fitnees=AssignGenes(chromosoms);
		
		
		// this block of code to chek if there fitness of chromosem from population is bigger than optimal solution
		int f=0;
		while (f >= 0 && f < fitnees.length ) {
			if(fitnees[f] > solution) {
				bestchromosom = chromosoms.get(f);
				opt = true;
				System.out.println("Best solution is :"+bestchromosom);
			    break;
			}
			f++;
		}
		
		
		// this block of code to repeat the steps of genetic algorthim
		int z=0; // to count the repeation of steps;
			while ( opt == false ) {
				fitnees=AssignGenes(chromosoms);
				// select two highest chromosems to implement crosover 
				parentsIndexs = SelectParents(fitnees);
				//implement crossover between parents
				parentforcross = ImplementCrossover(chromosoms,parentsIndexs[0], parentsIndexs[1]);
				//implement mutuation
				chromosoms = ImplementMutation(chromosoms,parentforcross,fitnees);
				z++;
			}
			
			System.out.println("\t -------------------");
			for (int m=0 ; m< bestchromosom.length ; m++) 
				System.out.print(bestchromosom[m]+"|");
				System.out.println("\n The Repetition is : "+z+" --> Done");
			
		
	
	}//end Reading method
	
	
	
//********************************************************************//		
	public static int[] GRandomWithoutRed() {
//this function to generate projects numbers in the array with range (1-37)
//without reduction  of projects numbers		
		
		
		Random matRandom = new Random();
		int [] a = new int[37];
		
		
		for (int i=0 ; i<a.length ; i++) {
		a[i]=matRandom.nextInt(37)+1 ; 
		
		//check the reduction
		for(int j=0 ; j<i ; j++) {
			if(a[i]==a[j]) {
				i--;
				break;
			}
			
		}
		}
			
		
		return a;
		
		}//end method GRandomWithoutRed()
//********************************************************************//	
	
	
	
	
//********************************************************************//	
	public static ArrayList<int[]> Population() {
//create set of chromosoms randomly with projects numbers		
		
		
		int [][] chromosoms2 = new int[10][37];
		
		for(int i=0 ; i<10 ; i++) {

			chromosoms2[i]=GRandomWithoutRed();
			
		}
		
		 
		for(int i=0 ; i<10 ; i++) {
			System.out.println("Generation Chromoson Number :" + i);
			for(int j=0 ; j<37 ; j++) {
		    System.out.print(" "+chromosoms2[i][j]+"|");
			}
			System.out.println(" \n");

		}
		
		 ArrayList<int[]> tmp = new ArrayList<int[]>();
		 
		 for(int i1=0;i1<10;i1++) {
			 tmp.add(chromosoms2[i1]);
		 }
		
		
		return tmp;
	}//end Population method
//********************************************************************//		
	
	
	
//********************************************************************//			
	public static int[] AssignGenes(ArrayList<int[]> cromo) {
	//this function to calclute the fitness of chromosoms	
		int [] fitnees = new int[cromo.size()];
		//we make class gene to compare between selection of groups and fitness of chromosms 
		Gene [][] indgene = new Gene[cromo.size()][37];
		
	
		for (int in=0; in<cromo.size() ; in++) {
			for(int j=0 ;j<37 ;j++) {
				
				indgene[in][j] = new Gene(groups.get(j),Fchosise.get(j),Schosise.get(j),
						Tchosise.get(j),cromo.get(in)[j],projectName.get(cromo.get(in)[j]),
						instructorName.get(cromo.get(in)[j]),0);
				
				
				indgene[in][j].calFitness();		
		
		/*		
		System.out.println("Chromosom ("+in+") Gene"+j+"Groups"+indgene[in][j].getGroups()+
						"\n"+"First Choise :"+indgene[in][j].getFch()+"\n"+
						"Second Chise :" + indgene[in][j].getSch()+"\n"+
						"Third choise :" + indgene[in][j].getTch()+"\n"+
						"Project Name :" + indgene[in][j].getTopic());
		*/
		
		
		fitnees[in] += indgene[in][j].getFitness();
				
			}
			
			//fitnees[in] +=  ;
			System.out.println("Fitness of chromosoem "+in+": "+fitnees[in]);
		}
	
		
		return fitnees;
	}//end method Assign Genes 
//********************************************************************//		
	
	
	
//********************************************************************//			
	public static int[] SelectParents(int [] fitnees ) {
//this function to select two highest of fitness
		
		System.out.println(" Step pf selection parents ");
		
	
      int max1=0;
      int max2=0;
      int maxindex1=0;
      int maxindex2=0;
      
      for (int n=0; n<fitnees.length ; n++)
    	  System.out.println("fitness of chromosom "+n+" :" +fitnees[n]);
      
      
		for (int in=0; in<fitnees.length ; in++) {
			if( fitnees[in] > max1) {
			max2=max1;
			max1=fitnees[in];
			maxindex1=in;
			}else if (fitnees[in]>max2) {
				max2 = fitnees[in];
			}//end for loop 
		}
			
			for (int m=0; m<fitnees.length ; m++)
				if(fitnees[m] == max2)
					maxindex2=m;
			
			
	System.out.println("The best two chromosom is "
			+ " First chromosom "+maxindex1+"-->fitness is :"+fitnees[maxindex1]
			+ "\n Second chromosom "+maxindex2+"-->fitness is :"+fitnees[maxindex2]);		
	
	//return the indexes of highest two chromosoms		
	return new int[] {maxindex1,maxindex2};
		
	}// end class selectParents
//********************************************************************//		
	
	
	
//********************************************************************//
	public static int[][] ImplementCrossover(ArrayList<int[]> cromo,int a, int b){
		System.out.println(" Step of crosover of parents ");
		
		// a :the best chromosom
		int [][] parentforcross = new int[2][37];
		int [] tmp1 = new int[37];
		int [] tmp2 = new int[37];
		Random matrandom = new Random();
		
		//double crossoverProbability = 0.9 ;
	
		for(int w=0; w<37 ; w++) {
			parentforcross[0][w] = cromo.get(a)[w];
			parentforcross[1][w] = cromo.get(b)[w];
		}
		
	
	// crossover between first 19 genes(half of chromosom)		
	    for (int i1=0;i1<19;i1++) {
	    	tmp1[i1] = parentforcross[1][i1];
	    	tmp2[i1] = parentforcross[0][i1];
	    }
	    
	    
	  //fill others genes in parent 1 without repeation
	    for (int i3=19 ; i3<37 ; i3++) {
			tmp1[i3]=matrandom.nextInt(37)+1;
			
			for(int j2=0 ; j2<i3 ; j2++) {
				if(tmp1[i3]==tmp1[j2]) {
					i3--;
					break;
				}
				
			}
			}
	    
	   
	    //fill others genes in parent 2 without repeation
	    for (int i3=19 ; i3<37 ; i3++) {
			tmp2[i3]=matrandom.nextInt(37)+1;
			
			for(int j2=0 ; j2<i3 ; j2++) {
				if(tmp2[i3]==tmp2[j2]) {
					i3--;
					break;
				}
				
			}
			}
	    
	    /*
	      //fill others genes without repeation
	    for (int i3=19 ; i3<37 ; i3++) {
			tmp2[i3]=parentforcross[1][i3];
			
			for(int jjj=0 ; jjj<i3 ; jjj++) 
				if(tmp2[i3]==parentforcross[1][jjj]) {
					for (int ii=19 ; ii<37 ; ii++) {
						 tmp2[ii]=matrandom.nextInt(37)+1;
							for(int j2=0 ; j2<ii ; j2++) {
								if(tmp2[ii]==tmp2[j2]) {
									ii--;
									break;
								}
								
							}
							}
				}	
			}
	    
			*/
	    
	    for(int w2=0; w2<37 ; w2++) {
			parentforcross[0][w2] = tmp1[w2];
			parentforcross[1][w2] = tmp2[w2];
		}
			
			
			
	    System.out.println("The Two children is \n "+ " First child \n ");
	    for(int w=0; w<37 ; w++) 
		System.out.print(parentforcross[0][w]+"|");	
		
		System.out.println("\n The Second child ");
	    for(int z=0; z<37 ; z++) 
		System.out.print(parentforcross[1][z]+"|");
	    
	    
	
	    int s1 = 0;
	    int s2 = 0;
	    for(int zz=0; zz<37 ; zz++) {
	    	s1 +=parentforcross[0][zz];
	        s2 +=parentforcross[1][zz];
}
			System.out.println("\n c1 = "+s1);	    
		    System.out.println(" c2 = "+s2);
    
	    
	    return parentforcross;
			    
	}// end method crossover 
//********************************************************************//		


	
	
//********************************************************************//			
	public static ArrayList<int[]> ImplementMutation(ArrayList<int[]> cromo,int [][] parentforcross,int [] fitnees) {
	//this function to make mutation of chromosoms	
		int c1 = (int)(Math.random()*20)+10;// 10-30 
		int c2 = (int)(Math.random()*20)+10; // 10-30
		
		//probability of mutation for chromosom
	double ProMutation = 0.4 ;
		
		if(ProMutation > Math.random() ) {
			//swap between two indexes of same chromosom 
			int tmp = parentforcross[0][c1];
            parentforcross[0][c1]= parentforcross[0][c2];
            parentforcross[0][c2] = tmp; 
		}else if(ProMutation == Math.random()){
			int tmp = parentforcross[1][c1];
            parentforcross[1][c1]= parentforcross[1][c2];
            parentforcross[1][c2] = tmp; 
		}
		
		
		//calcluate fitness of children 
		int fit = FitnessForChildren(parentforcross);
		if( fit == 0) {
			bestchromosom = parentforcross[0];
			System.out.println("\n Best cromosom is :"+"\n"+bestchromosom);
			opt = true;
		}
			
		else if (fit == 1) {
			bestchromosom = parentforcross[1];
			System.out.println("\n Best cromosom is :"+"\n"+bestchromosom);
			opt = true;
		}
			
		else {
		cromo.add(parentforcross[0]);
		cromo.add(parentforcross[1]);
		}
		
		return cromo;
	}// end method mutation
//********************************************************************//
	
//********************************************************************//	
	/*
	public static int[] replace(int [] fitnees ) {
		
		
	      int min1=Integer.MAX_VALUE;
	      int min2=Integer.MAX_VALUE;
	      int minindex1=0;
	      int minindex2=0;
	      
			for (int in=0; in<10 ; in++) {
				if( fitnees[in] < min1) {
				min2=min1;
				min1=fitnees[in];
				minindex1=in;
				}else if (fitnees[in]< min2) {
					min2 = fitnees[in];
				}//end for loop 
			}
				
				for (int m=0; m<10 ; m++)
					if(fitnees[m] == min2)
						minindex2=m;
				
	fitnees[minindex1]=min1;
	fitnees[minindex2]=min2;
	
		
				
		return new int[] {minindex1,minindex2};
	
	}//end method replace
	*/
//********************************************************************//		
	
	
	
	
	

//********************************************************************//			
	public static int FitnessForChildren(int [][] children) {
//this function to  calculate fitness of children 
		
		int [] fitnees = new int[2];
		Gene [][] indgene=new Gene[2][37];
		
		
		for (int in=0; in<2 ; in++) {
			for(int j=0 ;j<37 ;j++) {
				
				indgene[in][j] = new Gene(groups.get(j),Fchosise.get(j),Schosise.get(j),
						Tchosise.get(j),children[in][j],projectName.get(children[in][j]),
						instructorName.get(children[in][j]),0);
				
				
				indgene[in][j].calFitness();		
		
		fitnees[in] += indgene[in][j].getFitness();
		
				
			}
		}
		
		//return the index of the child that has fitness grater than solution
		if(Math.max(fitnees[0], fitnees[1]) >= solution )
			if (fitnees[0] > fitnees[1])
			return 0;
			else
			return 1;
		else
		return Math.max(fitnees[0], fitnees[1]);
	}//end method fitnessforchildren;
//********************************************************************//		

	
	
	

}//end class
