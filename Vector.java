/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eceproject3;

import java.util.*;


interface Vector
{
    // randomly fill all entries with number 0<=x<1
    void fill();
    // Assign the value x to element i
    void set(int i,double x);
    // Get the value of the element i
    double get(int i);
    // Get the size of the vector (number of rows) 
    int getSize();
    // Print vector using a specific format  
    void display();
    // Calculate the Loo norm of a vector // norm=max(|x_i|) (x_i component i of x)
    double normLoo();
    // Calculate the L2 norm of a vector (Euclidean norm=sqrt(sum(x_i^2)))
    double normL2();
    //return array
    double[] getArray();
    
    //double get2(int i);
}




//////////////////////// USING ARRAY IMPLEMENTATION

class VectorArray implements Vector
{   
    int size;
    double[]vectors;
    
    public VectorArray(int size){ //constructor
    this.size=size;
    vectors=new double[size];
 }

    public void fill() {
       Random rand=new Random(2);
       
       for(int i=0;i<vectors.length;i++){
           vectors[i]=rand.nextDouble();
       }
    }
    
    public double[] getArray(){
        return vectors;
    }
   
    public void set(int i, double x) {
        vectors[i]=x;
    }

    //Override
    public double get(int i) {
        return vectors[i];
    }

    //Override
    public int getSize() { //get size of array
     return size;   
    }

    //Override
    public void display() { //display array
        for(int i=0;i<vectors.length;i++){
           if(vectors[i]!=0)
        System.out.println(vectors[i]);
        }
    }

    //Override
    public double normLoo() {
        double max=vectors[0];
        for(int i=0;i<vectors.length;i++){
            if(vectors[i]>max)
                max=vectors[i];
        }
        return max;
    }

    //Override
    public double normL2() {
        double sum=0;
        for(int i=0;i<vectors.length;i++){
            sum=sum+Math.pow(vectors[i],2);
        }
        double norm=Math.sqrt(sum);
        return norm;
    }

    
    
}



//////////////////////// USING Linked-List IMPLEMENTATION


class VecNode{
    public    int index;
    public    double entry;
    public    VecNode next;
  
    VecNode(int i,double x){index=i;entry=x;next=null;}
}


class VectorLL implements Vector
{ 

   int size;
   VecNode current;  
   VecNode first;
   VecNode last;
   
    public VectorLL(int size){ //constructor
    this.size=size;
    current=null;
    }

    public void fill() { //randomly fill the linked list
       Random rand=new Random(2);
       
       for(int i=0;i<size;i++){
           if(current==null){
               current=new VecNode(i, rand.nextDouble());
               first=current;
           }
           else{
          VecNode last=new VecNode(i, rand.nextDouble());
          current.next=last;
          current=last;
           }
       }
    }

   
   //Override
    public void set(int i, double x) {  //set item at i to x
        
        VecNode setNode=new VecNode(i, x);
        if(first==null){
           // System.out.println(current);
            first=setNode;
            last=first;
            
        }
        else{
           last.next=setNode;
           last=setNode;
        }
        
        current=first;
       
        int j=0;
        while(current!=null){
            if(current.index==i){
                current.entry=x;
            }
            
          current=current.next;
         return;
        }
        
    }
    
    
   
   
    public double get(int i){
        VecNode temp = first;
		for (int j = 0; j < i; j++) {
			temp = temp.next;  
		}
		return temp.entry;
	
    }

    //Override
    public int getSize() { //get size of linked list
     return size;   
    }

    //Override
    public void display() { //display linked list
        VecNode display=first;
        while(display!=null){
            System.out.println(display.entry);
            display=display.next;
        }
    }

    //Override
    public double normLoo() {
        double max=first.entry;
        while(current!=null){
           if(current.entry>max){
              max=current.entry;
           }
           
           else
               current=current.next;
           
    }
          return max;
    }

    //Override
    public double normL2() {
        double sum=0;
        for(int i=0;i<size;i++){
            
            sum=sum+Math.pow(first.entry,2);
            first=first.next;
        }
        double norm=Math.sqrt(sum);
        return norm;
    }

    //Override
    public double[] getArray() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}




