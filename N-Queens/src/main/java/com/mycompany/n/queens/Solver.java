package com.mycompany.n.queens;

import java.util.concurrent.Semaphore;

/**
 *
 * @author Eng Ossama Samir
 */
public class Solver implements Runnable {
    // all ttributaes
    private int N ;
    private int[][] Board ;
    private int[] nStart;// start of each thrad
    public static int[][] t1Board=null;
    public static int[][] t2Board=null;
    public static int[][] t3Board=null;
    public static int[][] t4Board=null;
    public static boolean t1Solve = true;
    public static boolean t2Solve = false;
    public static boolean t3Solve = true;
    public static boolean t4Solve = true;
    Semaphore lock1 = new Semaphore(1);
    
    //Constractor 
    public Solver(int N){
        if(N<=3){
            System.out.println("No Solution whit this N");
            return;
        }else{
            this.nStart = divideN(N);
            this.N = N ;
            this.Board = new int[N][N];
            setBoard(Board);
            this.t1Board = new int[N][N];
            this.t2Board = new int[N][N];
            this.t3Board = new int[N][N];
            this.t4Board = new int[N][N];
            setBoard(t1Board);
            setBoard(t2Board);
            setBoard(t3Board);
            setBoard(t4Board);
            run();
        }
    }
    // function to check if this cell is safe to let the queen
    private boolean isSafe(int[][] board , int column , int row){
        // check column from current cell to above
        for(int i=0 ; i<board.length ; i++){
            if(board[i][column]==1)
                return false;
        }
        // check upper right diagonal from current cell to above 
        for(int i=column+1,j=row-1 ; i<board.length&&j>=0 ; i++,j--)
            if(board[j][i]==1)
                return false;
        // check upper left diagonal from current cell to above 
        for(int i=column-1,j=row-1 ; i>=0&&j>=0 ; i--,j--)
            if(board[j][i]==1)
                return false;
        return true;
    }
    
    // function to ptint Board 
    public void printBoard(int[][] board){
        for(int i=0 ; i<board.length ; i++){
            for(int j=0 ; j<board.length ; j++)
                if(board[i][j]==1)
                    System.out.print("Q\t");
                else
                    System.out.print(board[i][j]+"\t");
            System.out.println("\n");
        }
    }
    
    // recursive function to sovle any group of rows from NQueens board
    private boolean solve(int[][] board , int row){
        // check if we reach to end of board then we founded solution 
        if(row==board.length)
            return true;
        // for loop all rows to set ecah safe cell to 1
        for(int i=0 ; i<board.length ; i++){
            // check if cell is safe 
            if(isSafe(board,i,row)){
                // set cell to 1 
                board[row][i]=1;
                // path col+1 to function 
                if(solve(board,row+1))
                    return true;                
                // if above "if false" then we backtrak and rest cell to 0
                board[row][i]=0;                 
            }
        }
        return false;
    }
    
    // function to solve n queens from any start state you give he it
    private boolean solveNQueens(int[][] board , int col){
        setBoard(board);
        // set start state 
        board[0][col]=1;
        
        // use backtraking with rest of board after set start state 
        boolean res = solve(board,1);   
        if(!res)
            return res;
        return res;
    }
    
    // function to set all cells in board equal 0
    private void setBoard(int[][] board){
        for(int i=0 ; i<board.length ; i++){
            for(int j=0 ; j<board.length ; j++){
                board[i][j]=0;
            }
        }
    }
    
    // function solve for each thread 
    public void solveForEachThread(int column,int[][] Board,boolean isSolve){
         try {
            lock1.acquire(); 
            System.out.println("Thread ID : "+Thread.currentThread().getId());
            if(!solveNQueens(Board,column))
                isSolve = false;
            printBoard(Board);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock1.release();
        }
    }
    
    public static int[][] getBoard(int n){
        if(n == 1)
            return t1Board;
        else if(n == 2)
            return t2Board;
        else if(n == 3)
            return t3Board;
        else if(n == 4)
            return t4Board;
        return t4Board;
    }

    // function to divide n to n1 for t1 and n2 for t2 and so on
    private int[] divideN(int number){
        int n = number;
        int n1=0;
        int n2=0;
        int n3=0;
        int n4=0;
        while(n>0){
            if(n>0){
                n1++;
                n--;
            }
            if(n>0){
                n2++;
                n--;
            }
            if(n>0){
                n3++;
                n--;
            }
            if(n>0){
                n4++;
                n--;
            }
        }
        int[] arrayOfn= {n1,n1+n2,n1+n2+n3,n1+n2+n3+n4};
        return arrayOfn;
    }
    
    @Override
    public void run() {
        Thread thread1 = new Thread(()->{
            int rows = this.nStart[0];
            for(int i=0 ; i<rows ;i++)
                solveForEachThread(i,t1Board,t1Solve);
        });
        Thread thread2 = new Thread(()->{
            int rows = this.nStart[1];
            for(int i=this.nStart[0]; i<rows ;i++)
                solveForEachThread(i,t2Board,t2Solve);
        });
        Thread thread3 = new Thread(()->{
            int rows = this.nStart[2];
            for(int i=this.nStart[1]; i<rows ;i++)
                solveForEachThread(i,t3Board,t3Solve);
        });
        Thread thread4 = new Thread(()->{
            int rows = this.nStart[3];
            for(int i=this.nStart[2]; i<rows ;i++)
            solveForEachThread(i,t4Board,t2Solve);
        });
        
        thread2.start();
        thread1.start();
        thread3.start();
        thread4.start();
        
    }
        // function to check if board have a solution or not
    public boolean isSolution(int[][] Board){
        int numberOfOnes=0;
        for(int i=0 ; i<N ;i++){
            for(int j=0 ; j<N ; j++){
                if(Board[i][j]==1)
                    numberOfOnes++;
            }
        }
        if(numberOfOnes==N){
            return true;
        }
        return false;
    }
}

    
    