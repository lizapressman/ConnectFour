package connectfour;

public class Board {
	int[][] bo;
	int h;
	int w;
	boolean terminal;
	int utility;
	int player;
	int inARow;
	
	//Board constructor that takes in a board of m x n x k
	public Board(int x, int y, int z) {
		h = x;
		w = y;
		bo = new int[x][y];
		terminal = false;
		//utility = null;
		player = 1;
		inARow = z;
		for (int i=0;i<x;i++) {
			for (int j=0;j<y;j++) {
				bo[i][j] = 0;
			}
		}
	}
	
	//Board constructor that takes in a board and an action and performs the action on the board
	public Board(Board b, int action) {
		h = b.h;
		w = b.w;
		bo = b.bo;
		terminal = b.terminal;
		//utility = b.utility;
		player = b.player;
		inARow = b.inARow;
		int count0=0;
		for (int i=h-1; i>=0;i--) {
			if (bo[i][action]==0) {
				bo[i][action]=player;
				player = player *-1;
				break;
			}
		}
		for (int i=h-1;i>=0;i--) {
			for (int j=0;j<w;j++) {
				if(bo[i][j]==0) {
					count0++;
					//System.out.println("COunt" + count0);
				}
			}
		}
		for (int i=h-1;i>=0;i--) {
			for (int j=0;j<w;j++) {

				if (bo[i][j]!=0) {
					int n = bo[i][j];
					int count = 1;
					for(int k=1;k<inARow;k++) { //checking the up direction
						if(i-k>=0 && bo[i-k][j]==n) {
							count++;
						}
						else {
							break;
							}
						}
					if (count == inARow) {
						terminal = true;
						//System.out.println("Count"+ count0);
						utility=n*10 + n*-1*count0 +n*b.blocks(n);
					}
					count = 1;
					for(int k=1;k<inARow;k++) { //checking the right direction
						if(j+k<w && bo[i][j+k]==n) {
							count++;
						}
						else {
							break;
							}
						}
					if (count == inARow) {
						terminal = true;
						utility=n*10 + n*-1*count0+n*b.blocks(n);
					}
					count = 1;
					for(int k=1;k<inARow;k++) { //checking the diagonal / direction
						if(j+k<w && i-k>=0 && bo[i-k][j+k]==n) {
							count++;
						}
						else {
							break;
							}
						}
					if (count == inARow) {
						terminal = true;
						utility=n*10 + n*-1*count0+n*b.blocks(n);
					}
					count = 1;
					for(int k=1;k<inARow;k++) { //checking the diagonal \ direction
						if(j-k>=0 && i-k>=0 && bo[i-k][j-k]==n) {
							count++;
						}
						else {
							break;
							}
						}
					if (count == inARow) {
						terminal = true;
						utility=n*10 + n*-1*count0+n*b.blocks(n); 
					}
				}
			}
		}
		boolean full = true;
		for(int i=0;i<w;i++) {
			if(bo[0][i]==0) {
				full = false;
			}
		}
		if(full && !terminal) {
			terminal = true;
			utility= 0;
		}
	}
	
	//checks how many of n player tokens are in a row of number k
	public int inARow(int n){
		int max=0;
		for (int i=h-1;i>=0;i--) {
			for (int j=0;j<w;j++) {
				if (bo[i][j]==n) {
					//int n = bo[i][j];
					int count = 1;
					for(int k=1;k<inARow;k++) { //checking the up direction
						if(i-k>=0 && bo[i-k][j]==n) {
							count++;
						}
						else {
							break;
							}
						}
					if(count>max) {
						max = count;
					}
					count = 1;
					for(int k=1;k<inARow;k++) { //checking the right direction
						if(j+k<w && bo[i][j+k]==n) {
							count++;
						}
						else {
							break;
							}
						}

					if(count>max) {
						max = count;
					}
					count = 1;
					for(int k=1;k<inARow;k++) { //checking the diagonal / direction
						if(j+k<w && i-k>=0 && bo[i-k][j+k]==n) {
							count++;
						}
						else {
							break;
							}
						}
					if(count>max) {
						max = count;
					}
					count = 1;
					for(int k=1;k<inARow;k++) { //checking the diagonal \ direction
						if(j-k>=0 && i-k>=0 && bo[i-k][j-k]==n) {
							count++;
						}
						else {
							break;
							}
						}
					if(count>max) {
						max = count;
					}
				}
			}
		}
		return max;
	}
	
	//checks how many of n player tokens are in a row of number x
	public int xInARow(int n,int x){
		int max=0;
		for (int i=h-1;i>=0;i--) {
			for (int j=0;j<w;j++) {
				if (bo[i][j]==n) {
					//int n = bo[i][j];
					int count = 1;
					for(int k=1;k<inARow;k++) { //checking the up direction
						if(i-k>=0 && bo[i-k][j]==n) {
							count++;
						}
						else {
							break;
							}
						}
					if(count==x) {
						max++;
					}
					count = 1;
					for(int k=1;k<inARow;k++) { //checking the right direction
						if(j+k<w && bo[i][j+k]==n) {
							count++;
						}
						else {
							break;
							}
						}

					if(count==x) {
						max++;
					}
					count = 1;
					for(int k=1;k<inARow;k++) { //checking the diagonal / direction
						if(j+k<w && i-k>=0 && bo[i-k][j+k]==n) {
							count++;
						}
						else {
							break;
							}
						}
					if(count==x) {
						max++;
					}
					count = 1;
					for(int k=1;k<inARow;k++) { //checking the diagonal \ direction
						if(j-k>=0 && i-k>=0 && bo[i-k][j-k]==n) {
							count++;
						}
						else {
							break;
							}
						}
					if(count==x) {
						max++;
					}
				}
			}
		}
		return max;
	}
	
	//returns the position to block a player
	public int blocks(int player) {
		int max=0;
		for (int i=h-1;i>=0;i--) {
			for (int j=0;j<w;j++) {
				if (bo[i][j]==player) {
					//int n = bo[i][j];
					int count = 1;
					for(int k=1;k<inARow;k++) { //checking the up direction
						if(i-k>=0 && bo[i-k][j]==player) {
							count++;
							if(count==inARow-1 && 
									((i-k-1>=0 &&bo[i-k-1][j]==player*-1))) {
								max++;
							}
						}
						else {
							break;
							}
						}
					
					count = 1;
					for(int k=1;k<inARow;k++) { //checking the right direction
						if(j+k<w && bo[i][j+k]==player) {
							count++;
							if(count==inARow-1 && 
									(j+k+1<w && bo[i][j+k+1]==player*-1)
									 ||(j+k-inARow>=0 && bo[i][j+k-inARow]==player*-1)) {
								max++;
							}
						}
						else {
							break;
							}
						}

					count = 1;
					for(int k=1;k<inARow;k++) { //checking the diagonal / direction
						if(j+k<w && i-k>=0 && bo[i-k][j+k]==player) {
							count++;
							if(count==inARow-1 && 
									((i-k-1>=0 &&j+k+1<w && bo[i-k-1][j+k+1]==player*-1))) {
								max++;
							}
						}
						else {
							break;
							}
						}
					count = 1;
					for(int k=1;k< inARow;k++) { //checking the diagonal \ direction
						if(j-k>=0 && i-k>=0 && bo[i-k][j-k]==player) {
							count++;
							if(count== inARow-1 && 
									((j-k-1>=0 &&i-k-1>=0&&bo[i-k-1][j-k-1]==player*-1))) {
								max++;
							}
						}
						else {
							break;
							}
						}
				}
			}
		}
		return max;
	}
	
	//prints our board
	public void printboard() {
		System.out.print("  ");
		for(int i=0;i<w;i++) {
			System.out.print(i+ " ");
		}
		System.out.println("");
		for (int i=0;i<h;i++) {
			System.out.print(i+" ");
			for (int j=0;j<w;j++) {
				String s = " ";
				if(bo[i][j]== 1) {
					 s = "X";
				}
				else if (bo[i][j]==-1) {
					 s = "O";
				}
				System.out.print(s);
				System.out.print(" ");
			}
			System.out.println(" "+i);
		}
		System.out.print("  ");
		for(int i=0;i<w;i++) {
			System.out.print(i+ " ");
		}
	}
	
	//clears the board
	public void clear() {
		for (int i=0;i<h;i++) {
			for (int j=0;j<w;j++) {
				bo[i][j] = 0;
			}
	}
	}
	
	//checks if a given column c is full
	public boolean columnfull(int c) {
		for(int i=0;i<h;i++) {
			if (bo[i][c]==0) {
				return false;
			}
		}
		return true;
	}
	
	//counts the number of blanks on the board
	public int countZeros() {
		int count = 0;
		for (int i=h-1;i>=0;i--) {
			for (int j=0;j<w;j++) {
				if(bo[i][j]==0) {
					count++;
				}
			}
		}
		return count;
		
	}
	
	public static void main(String[] args) {
		
	}
}
