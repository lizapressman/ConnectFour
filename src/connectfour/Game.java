package connectfour;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;

public class Game {
	
	//keeps track of how many states we visited
	static int count = 0;
	
	//our result function
	public static Board result(Board b, int action) {
		return new Board(b,action);
	}
	
	//full MINIMAX
	public static int minimax(Board b) {
		if (b.terminal) {
			if(b.utility==0) {
			//b.printboard();
			//System.out.println(b.utility);
			}
			return b.utility;
		}
		if (b.player == 1) {
			int bestValue = -10;
			for (int i=0;i<b.w;i++) {
				if (!b.columnfull(i)) {
					Board bc = copyBoard(b);
					Board r = result(bc,i);
					count++;
					//r.printboard();
						int value = minimax(r);
						if (value>bestValue) {
							bestValue = value;
						}
					//System.out.println("UTILITY"+ value);
				}
			}
			return bestValue;
		}
		else {
			int bestValue = 10;
			for (int i=0;i<b.w;i++) {
				if (!b.columnfull(i)) {
					Board bc = copyBoard(b);
					Board r = result(bc,i);
					count++;
					//r.printboard();
						int value = minimax(r);
						if (value<bestValue) {
							bestValue = value;
						}
					//System.out.println("UTILITY"+ value);
				}
			}
			return bestValue;
		}
	}
	
	//Our heuristic function
	public static int h(Board b) {
		int total = 0;
		if(b.inARow(1)==b.inARow) {
			total= 1000;
		}
		else if(b.inARow(-1)==b.inARow) {
			total= -1000;
		}
		else if(b.inARow(1)==b.inARow-1 && b.player==1) {
			total= 999;
		}
		else if(b.inARow(-1)==b.inARow-1 && b.player==-1) {
			total= -999;
		}
		else if(b.xInARow(1,b.inARow-1)>=2) {
			total= 998;
		}
		else if(b.xInARow(-1,b.inARow-1)>=2) {
			total= -998;
		}
		else {
		for(int i=b.inARow;i>=0;i--) {
			total += (b.xInARow(1, i)*5*i);
			total += (b.xInARow(-1, i)*5*-i);
		} 
		total+= b.blocks(1)*-100;
		total+= b.blocks(-1)*100;
		}
		return total;
	}
	
	
	//H-MINIMAX (we never call this)
	public static int hminimax(Board b, int d) {
		if (d==0 || b.terminal) {
			return h(b);
		}
		if (b.player == 1) {
			int bestValue = -10;
			for (int i=0;i<b.w;i++) {
				if (!b.columnfull(i)) {
					Board bc = copyBoard(b);
					Board r = result(bc,i);
					count++;
					int value = hminimax(r,d-1);
					if (value>bestValue) {
						bestValue = value;
					}
				}
			}
			return bestValue;
		}
		else {
			int bestValue = 10;
			for (int i=0;i<b.w;i++) {
				if (!b.columnfull(i)) {
					Board bc = copyBoard(b);
					Board r = result(bc,i);
					count++;
					int value = hminimax(r,d-1);
					if (value<bestValue) {
						bestValue = value;
					}
				}
			}
			return bestValue;
		}
	}
	
	//MINIMAX with alpha beta pruning
	public static int alphabeta(Board bo, int a, int b) {
		if (bo.terminal) {
			return bo.utility;
		}
		if (bo.player == 1) {
			int bestValue = -10000;
			for (int i=0;i<bo.w;i++) {
				if (!bo.columnfull(i)) {
					Board bc = copyBoard(bo);
					Board r = result(bc,i);
					count++;
					int value = alphabeta(r,a,b);
					if (value>bestValue) {
						bestValue = value;
						}
					if(value>a) {
						a=value;
					}
					if(a>=b) {
						break;
					}
				}
			}
			return bestValue;
		}
		else {
			int bestValue = 10000;
			for (int i=0;i<bo.w;i++) {
				if (!bo.columnfull(i)) {
					Board bc = copyBoard(bo);
					Board r = result(bc,i);
					count++;
					int value = alphabeta(r,a,b);
					if (value<bestValue) {
						bestValue = value;
					}
					if(value<b) {
						b = value;
					}
					if(a >=b) {
						break;
					}
					}
			}
			return bestValue;
		}
	}
	
	//H-MINIMAX with alpha beta pruning and fixed depth cutoff
	public static int halphabeta(Board bo, int d, int a, int b) {
		if (d==0 || bo.terminal) {
			return h(bo);
		}
		if (bo.player == 1) {
			int bestValue = -10000;
			for (int i=0;i<bo.w;i++) {
				if (!bo.columnfull(i)) {
					Board bc = copyBoard(bo);
					Board r = result(bc,i);
					count++;
					int value = halphabeta(r,d-1,a,b);
					if (value>bestValue) {
						bestValue = value;
					}
					if(value>a) {
						a=value;
					}
					if(a>=b) {
						break;
					}
				}
			}
			return bestValue;
		}
		else {
			int bestValue = 10000;
			for (int i=0;i<bo.w;i++) {
				if (!bo.columnfull(i)) {
					Board bc = copyBoard(bo);
					Board r = result(bc,i);
					count++;
					int value = halphabeta(r,d-1,a,b);
					if (value<bestValue) {
						bestValue = value;
					}
					if(value<b) {
						b = value;
					}
					if(a>=b) {
						break;
					}
				}
			}
			return bestValue;
		}
	}
	
	//picks a random place to go
	public static int random(Board bo) {
		Random generator = new Random();
		int i = generator.nextInt(bo.w);
		while (bo.columnfull(i)) {
			i = generator.nextInt(bo.w);
		}
		return i;
	}
		
	
	//finds the best move for the computer to make
	public static int bestMove(Board b, int n, int de) {
		Board c = copyBoard(b);
		int bestValue;
		if (b.player == -1) {
			bestValue = 1000000;
		}
		else {
			bestValue = -1000000;
		}
		int bestAction = 0;
		for (int i=0;i<c.w;i++) {
			Board d = copyBoard(c);
			if (!d.columnfull(i)) {
				Board e = new Board(d,i);
				int value;
				if (n == 1) {
					return random(b);
				}
				else if (n == 2) {
					value = minimax(e);
					if(b.player==-1) {
						if (value<bestValue) {
							bestValue=value;
							bestAction = i;
						}
					}
					else {
						if (value>bestValue) {
							bestValue=value;
							bestAction = i;
						}
					}
				}
				else if (n == 3) {
					value = alphabeta(e,-10000, 10000);
					if(b.player==-1) {
					if (value<bestValue) {
						bestValue=value;
						bestAction = i;
					}
					}
					else {
						if (value>bestValue) {
							bestValue=value;
							bestAction = i;
						}
					}
				}
				else {
					value = halphabeta(e,de,-10000, 10000);
					//System.out.println(value);
					if(b.player==-1) {
						if (value<bestValue) {
							bestValue=value;
							bestAction = i;
						}
					}
					else {
						if (value>bestValue) {
							bestValue=value;
							bestAction = i;
						}
					}
				}
			}
		}
		return bestAction;
	}
	
	//function that actually makes the move/prints boards
	public static void play(Board b, int n, int de,int player) {
		Scanner sc = new Scanner(System.in);
		//b.player = player;
		while(!b.terminal) {
			if(player==1) {
				System.out.println("make a move by typing in a number 0 to " + (b.w-1));
			    int i = sc.nextInt();
			    if(i>b.w-1 || i<0) {
			    	System.out.println("you quit the game!");
			    	break;
			    }
			    if (b.columnfull(i)) {
			    	System.out.println("Column Full please pick again!");
			    }
			    else {
			    	b = new Board(b,i);
					b.printboard();
					System.out.println();
					if (tie(b)) {
						System.out.println("It's a tie!");
						break;
					}
					if(b.terminal) {
						System.out.println("Congrats you win!");
						break;
					}
					long startTime = System.nanoTime();
					int j = bestMove(b,n,de);
					long endTime = System.nanoTime();
					long duration = (endTime - startTime)/1000000000;
					b = new Board(b,j);
					System.out.println();
					System.out.println("Im thinking...");
					System.out.println("	visited " + count + " states");
					System.out.println("	best move: " + j);
					System.out.println("Elapsed time: " + duration + " secs");
					b.printboard();
					System.out.println();
					if (tie(b)) {
						System.out.println("It's a tie!");
						break;
					}
					if(b.terminal) {
						System.out.println("Sorry!Computer wins");
					}
			    }
			}
			else {
				long startTime = System.nanoTime();
				int j = bestMove(b,n,de);
				long endTime = System.nanoTime();
				long duration = (endTime - startTime)/1000000000;
				b = new Board(b,j);
				System.out.println();
				System.out.println("Im thinking...");
				System.out.println("	visited " + count + " states");
				System.out.println("	best move: " + j);
				System.out.println("Elapsed time: " + duration + " secs");
				System.out.println();
				b.printboard();
				System.out.println();
				if (tie(b)) {
					System.out.println("It's a tie!");
					break;
				}
				if(b.terminal) {
					System.out.println("Sorry!Computer wins");
					break;
				}
				System.out.println("make a move by typing in a number 0 to " + (b.w-1));
			    int i = sc.nextInt();
			    if(i>b.w-1 || i<0) {
			    	System.out.println("you quit the game!");
			    	break;
			    }
			    if (b.columnfull(i)) {
			    	System.out.println("Column Full please pick again!");
			    }
			    else {
			    	b = new Board(b,i);
					b.printboard();
					System.out.println();
					if (tie(b)) {
						System.out.println("It's a tie!");
						break;
					}
					if(b.terminal) {
						System.out.println("Congrats you win!");
						
					}
				
			}
		}
		}
		sc.close();
	}
	
	public static boolean tie(Board bo) {
		boolean tie = false;
		boolean full = true;
		for(int i=0;i<bo.w;i++) {
			if(bo.bo[0][i]==0) {
				full = false;
			}
		}
		if(full && !bo.terminal) {
			tie = true;
		}
		return tie;
	}

	public static Board copyBoard(Board b) {
		Board c = new Board(b.h,b.w,b.inARow);
		c.player=b.player;
		c.utility = b.utility;
		c.terminal = b.terminal;
		for (int i=0;i<b.h;i++) {
			for(int j=0;j<b.w;j++) {
				c.bo[i][j] = b.bo[i][j];
				}
			}
		return c;
	}
	
	public static void start() {
		System.out.println("Connect-Four by Sydney Dlhopolsky and Liza Pressman");
		System.out.println("Choose your game");
		System.out.println("1. Tiny 3x3x3 Connect-Three");
		System.out.println("2. Wider 3x5x3 Connect-Three");
		System.out.println("3. Standard 6x7x4 Connect-Four");
		System.out.print("Your choice? ");
		Scanner sc = new Scanner(System.in);
		Board b;
		int s = sc.nextInt();
		if (s == 1) {
			b = new Board(3,3,3);
		}
		else if (s == 2) {
			b = new Board(3,5,3);
		}
		else {
			b = new Board(6,7,4);
		}
		System.out.println("Choose you opponent: ");
		System.out.println("1. An agent that plays randomly");
		System.out.println("2. An agent that uses MINIMAX");
		System.out.println("3. An agent that uses MINIMAX with alpha-beta pruning");
		System.out.println("4. An agent that uses H-MINIMAX with alpha-beta pruning and a fixed depth cutoff");
		System.out.print("Your choice? ");
		s = sc.nextInt();
		int gameVersion = 1;
		int depth=1;
		if (s == 1) {
			gameVersion = 1;
		}
		else if (s == 2) {
			gameVersion = 2;
		}
		else if (s == 3) {
			gameVersion = 3;
		}
		else if (s == 4) {
			System.out.print("Depth limit? ");
			gameVersion = 4;
			depth = sc.nextInt();	
		}
		System.out.println("Do you want to play red(1) or yellow(2): ");
		s=sc.nextInt();
		if(s==1) {
			play(b,gameVersion,depth,1);
		}
		else {
			play(b,gameVersion,depth,-1);
		}
		
		sc.close();
		
	}
	
	
	public static void main(String[] args) {
		start();
	}
	
}
