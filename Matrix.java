/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this toplate file, choose Tools | Templates
 * and open the toplate in the editor.
 */
package eceproject3;

import java.util.*;
import javafx.scene.chart.XYChart;



interface Matrix
{
    // Assign the value x to element i,j
    void set(int i,int j, double x);
    // get the value of the element i,j
    double get(int i, int j);
    // Extract the diagonal of the matrix
    double[] getDiagonal();
    // get the size of the matrix-- number of rows 
    int getSize();
    // get the number of non-zero elements
    int getNnz();
    // Multiply a matrix by a vector
    Vector multiply(Vector B);
    // Print matrix using a specific format  
    void display();
    // return info about the matrix 
    void info();
}




//////////////////////////////////// ARRAY DENSE MATRIX IMPLEMENTATION

class DenseMatrix implements Matrix
{
    //////// TO COMPLETE--you can uncomment the instructions below
   
    private int size=0; // size of the matrix- number of rows/columns
    private int nnz=0;  // number of non-zero elements
    private double[][] data;
    
    
    //Constructors
    public DenseMatrix(int size){
        this.size=size;
        data=new double[size][size];
    }

    public DenseMatrix(Matrix A2) {
        this.size=A2.getSize();
        this.nnz=A2.getNnz();
        data=new double[size][size];
        for(int i=0;i<size;i++){
            
            for(int j=0;j<size;j++){
                data[i][j]=A2.get(i, j);
                
            }
        }
       
    }
    
    public DenseMatrix(int size,int nnz){
        this.size=size;
        this.nnz=nnz;
        Random rand=new Random();
        data=new double[size][size];
       for(int i=0;i<size;i++){ 
            for(int j=0;j<size;j++){
               // System.out.println(data[i][j]);
                data[i][j]=rand.nextDouble();
                
            }
        }
    }
   
    /////// return info about the matrix
     public void info(){
    	System.out.println("Dense Matrix n="+size+", nnz="+nnz+", Storage="+(8*size*size)+"b or "+(8*size*size)/(1024*1024)+"Mb");
    }

    //Override
    public void set(int i, int j, double x) {
        data[i][j]=x;
        nnz++;
    }

    //Override
    public double get(int i, int j) {
        return data[i][j];
    }

    //Override
    public double[] getDiagonal() {
        double[]diagonal=new double[size];
        for(int i=0;i<size;i++){
            diagonal[i]=data[i][i];  //find diagonals i.e where indexes are equal
               
        }
        return diagonal;
    }

    //Override
    public int getSize() {
     return size;
    }

    //Override
    public int getNnz() {
        return nnz;
    }

    //Override
    public Vector multiply(Vector B) {
       double[] product=new double[size]; //to hold products
       double[]sumarray=new double[size]; //to hold sums
        double sum=0;
        product=B.getArray();
        
       for(int i=0;i<size;i++){
           for(int j=0;j<size;j++){
               sumarray[i]=sumarray[i]+data[i][j];
              
           }
           
           product[i]=sumarray[i]*product[i];
           B.set(i, product[i]);
        
       }
        
           return B;
    }

    //Override
    public void display() {
       for(int i=0;i<size;i++){
           for(int j=0;j<size;j++){
               System.out.print(data[i][j]+"  ");
           }
           System.out.println("  ");
       } 
    }

    
}



//////////////////////////////////// Linked-List SPARSE MATRIX IMPLEMENTATION

class RowNode{
    public    int rowindex;
    public    ColNode col;
    public    RowNode next;
  
    RowNode(int i){rowindex=i; col=null; next=null;}
}

class ColNode{
    public    double entry;
    public    int colindex;;
    public    ColNode next;

    ColNode(int j,double x){
	colindex=j;
	entry=x;
        next=null;}
}



class SparseMatrixLinkedList implements Matrix
{ 
    private   RowNode top;
    private   int size=0;
    private   int nnz=0;
    private RowNode temp;
    private DenseMatrix A1;
 
  
    // constructors
    SparseMatrixLinkedList(Matrix A1){

        for(int i=0;i<A1.getSize();i++){
            for(int j=0;j<A1.getSize();j++){
            if(A1.get(i, j)!=0){
               set(i, j, A1.get(i, j));
            }
            }
        }
       
           
           
       }
     
    
    //constructor for App7
    public SparseMatrixLinkedList(int size,int nnz){
      
        this.size=size;
        this.nnz=nnz;
        Random rand=new Random();
        
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                for(int z=0;z<nnz;z++){
                double x =rand.nextDouble();
                  set(i, j,x);
                }
        }
        }
        
        
    }
    // Basic constructor- no element in the list yet
    SparseMatrixLinkedList(){top=null;} 

    

 
    // methods   

    public void display(){
	RowNode current = top; //start probe at the beginning
	System.out.println("i");
	while (current!=null) { // until the end of the list
	    System.out.print(current.rowindex+" ");
	    ColNode jcurrent = current.col;
             //System.out.println(temp.col.entry+"sdds");
	    while (jcurrent!=null) { // until the end of the list
		System.out.format("==>  (j=%d, a=%.4f)",jcurrent.colindex,jcurrent.entry); 	       
		jcurrent = jcurrent.next;
                
	    }
	    System.out.println();
	    current = current.next; // move to next Link
	}
	System.out.println();
    }

    // return info about the matrix
    public void info(){
	System.out.println("Sparse Matrix n="+size+", nnz="+nnz+", Storage="+(8*nnz)+"b or "+(8*nnz)/(1024*1024)+"Mb");
    }
    
   
    //Override
    public void set(int i, int j, double x) {
       
       if(x==0){
           return;
       }
       
     
       if(top==null){
           
           size =1;
           
           ColNode newCol=new ColNode(j, x);
           top=new RowNode(i); /// empty 0 row
           top.col=newCol;
           
            nnz++;
         
       }
       
       else{
           
       RowNode curRow =top;
     
       // for row
       while(curRow.next!=null && curRow.rowindex != i && curRow.next.rowindex!=i){
           curRow = curRow.next;
       }
       
       if(curRow.next == null && curRow.rowindex != i){
           curRow.next = new RowNode(i);
           size = i+1;
       }
           
        // for col
        
        ColNode cn = (curRow.rowindex != i)? curRow.next.col : curRow.col;

        while(cn!=null && cn.next!=null && cn.next.colindex<j){

            cn = cn.next; // find prev
        }

        if(cn == null){ // make a new col node when list is empty
            curRow.next.col= new ColNode(j, x);
            nnz++;
        }
        else if (cn.colindex == j){ // replace the 1st 
            cn.entry = x;
        }
        else if(cn.next != null && cn.next.colindex == j){ // replace rest
            cn.next.entry = x;
//            System.out.println("replace " + i + " " + x);
        }
        else { // ins ert
//            
            ColNode tempColNode=new ColNode(j, x);
            tempColNode.next=cn.next;
            cn.next=tempColNode;
            nnz++;
        }
            
        
      }
       
    }



    //Override
    public double get(int i, int j) {

        RowNode current=top;
        while(current!=null){
            
            ColNode cn = current.col;
            while(cn!=null){
            if(current.rowindex==i && cn.colindex==j){
               // System.err.println(cn.colindex);
                return cn.entry;
            }
            cn=cn.next;
            }
            current=current.next;
        }    
        return 0.0;
    }

    //Override
    public double[] getDiagonal() { //get diagonal of the matrix
        RowNode current=top;
        int i=0;
        double[]diagonal=new double[size];
        
        
       while(current!=null){
           ColNode TEMPCOL=current.col;
           while(TEMPCOL!=null){
           if(current.rowindex==TEMPCOL.colindex){
               diagonal[i]=TEMPCOL.entry;
               
                i++;
                
           }
           TEMPCOL=TEMPCOL.next;
                
           }
           current=current.next; //increment row
       }
       
       return diagonal;
    }

    //Override
    public int getSize() {
       return size;
    }

    //Override
    public int getNnz() { //get number of non zeros in matrix
       return nnz; 
    }

    //Override
    public Vector multiply(Vector B){
        int count=0;
        RowNode newRow=top;
        ColNode newColumn;
        double sum2=0;
        int i=0;
        VectorArray tempVectorArray= new VectorArray(B.getSize());
        while(newRow!=null){
            sum2=0;
            count=0;
            newColumn=newRow.col; 
            while(newColumn!=null){ //implement column and find sum of multiplications
                sum2+=newColumn.entry*B.get(count++);
                newColumn=newColumn.next; 
            }
            tempVectorArray.set(i++, sum2); //set ntemporary Vector Array so it contains sum of products
            newRow=newRow.next; //implement row
        }
        return tempVectorArray;
    }

    
}

