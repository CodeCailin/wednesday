package com.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class holds the whole logic of the application$
 * The class is a Singleton class, in case, we wan to manage multiplayres games, change it to protype instance
 * Init the game
 * Manage the players
 * Run the algorithm to check if there is a 5 connect disc in the board game
 * @author Claire
 *
 */
public class GameLogic {
	
	private static GameLogic instance;
	
	private static List<String> colorPiecesConstant;
	    
    private GameLogic(){
    	colorPiecesConstant = Arrays.asList("X","O");
    }
    
    public static GameLogic getInstance(){
        if(instance == null){
            instance = new GameLogic();
        }
        return instance;
    }
    
    private GameState gameState;
    
    private int pointerTurn = 1;
    
    /**
     * Clear or reinitialize the state of the game
     * In case the two players for example has been disconnected
     */
    public void cleatGameData() {
    	gameState.getUserColorPieces().clear();
    	gameState.getUserNames().clear();
    	gameState = null;
    	pointerTurn = 1;
    }
    
    /**
     * This method init the game
     * Client send request the game and this method receive it for processing
     * @param userId
     * @param userColorPiece
     */
    public void initState(String userId, String userColorPiece) {
    	if(gameState == null) { //Run this if bloc for the first player which initiates the game
	    	gameState = new GameState();
	    	gameState.setStateGame(new String [6][9]);
	    	gameState.setTurn(userId);
	    	gameState.setStatus(GameStatus.WAITING_PLAYER);
	    	gameState.setErrorMessage("");
	    	
	    	List<String> users = new ArrayList<>();
	    	List<String> pieces = new ArrayList<>();

	    	users.add(userId);
	    	pieces.add(userColorPiece);
	    	
	    	gameState.setUserNames(users);
	    	gameState.setUserColorPieces(pieces);
	    	
	    	//Init board game
	    	for(int i = 0; i < 6; i++) {
	    		for(int j = 0; j < 9; j++) {
	    			gameState.getStateGame()[i][j] = "*";
	    		}
	    	}
	    	
    	} else if(!gameState.getUserNames().contains(userId)  //In case the second player ask to init the game, run this if bloc
    					&& gameState.getUserNames().size() == 1) {
	    	gameState.getUserNames().add(userId);
	    	gameState.setErrorMessage("");
	    	for(String s : colorPiecesConstant) {
	    		if(!gameState.getUserColorPieces().contains(s)) {
	    			gameState.getUserColorPieces().add(s);
	    		}
	    	}
    	} else if(gameState.getUserNames().size() == 2) { //In case there are already two players , throw an error message
    		gameState.setErrorMessage("The game has already two players in progres, namely : " + gameState.getUserNames());
    	} else { //In case the input userName is already exist
    		gameState.setErrorMessage("This user is already registered");
    	}
    }
    
    /**
     * This Method process the algorithm and manage the turn of the player
     * @param userId
     * @param column
     * @return
     */
    public GameState game(String userId, int column) {
    	
    	if(gameState != null ) {
    		if(gameState.getStatus() == GameStatus.ABORT_GAME) {
    			return gameState;
    		}
    		
        	// Get colorPiece of user
        	String colorPiece = gameState.getColorPieceFromUserId(userId);
        	
        	// Update Board according to the column's value
        	String [][] board = gameState.getStateGame();
        	int j = column - 1;
        	for(int i = board.length - 1; i >= 0; i--) {
        		if(board[i][j].equals("*")) {
        			board[i][j] = colorPiece;
        			break;
        		}
        	}
        	
        	//Evaluation the game
        	gameState = evaluateGame(gameState);
        	
        	// Change turn
        	pointerTurn = pointerTurn == 1 ? 2 : 1;
        	gameState.setTurn(gameState.getUserNames().get(pointerTurn - 1));
    	}
    	return gameState;

    }
    
    /**
     * Method to check if there will be a winner
     * Also check if the game is draw
     * @param gameState
     * @return
     */
    public GameState evaluateGame(GameState gameState) {
    	String [][] board = gameState.getStateGame();
    	boolean isGameOver = false;
    	
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				boolean retval = checkConnectHorizontal(board, i, j)
										| checkConnectVertical(board, i,j)
										| checkConnectDiagonal(board, i, j);
				if(retval) {
					isGameOver = true;
					break;
				}
			}
		}
		
		//Is GAME OVER
		if(isGameOver) {
			gameState.setStatus(GameStatus.GAME_OVER);
			gameState.setWinner(gameState.getTurn());
		} else if(isGameDraw(gameState)){
			gameState.setStatus(GameStatus.GAME_DRAW);
		}
		return gameState;
	}
    
    public boolean isGameDraw(GameState gameState) {
    	String [][] board = gameState.getStateGame();
    	boolean isGameDraw = true;
    	
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length; j++) {
				if(board[i][j].equals("*")) {
					isGameDraw  = false;
					break;
				}
			}
		}
		return isGameDraw;
    }
	
	public boolean checkConnectHorizontal(String [][] board, int i, int j) {
		int x = i, y = j - 4;
		String disc = board[i][j];
		int connectFive = 1;
		for(; y < j && y >= 0; y++) {
			if(!board[x][y].isEmpty() 
					&& !board[x][y].equals("*")
					&& board[x][y].equals(disc)) {
				connectFive++;
			}
		}
		if(connectFive == 5) return true;
		connectFive = 1;
		y = j + 4;
		for(; y > j && y < board[i].length; y--) {
			if(!board[x][y].isEmpty() 
					&& !board[x][y].equals("*")
					&& board[x][y].equals(disc)) {
				connectFive++;
			}
		}
		if(connectFive == 5) return true;
		return false;
	}
	
	public boolean checkConnectVertical(String [][] board, int i, int j) {
		int x = i - 4 , y = j;
		String disc = board[i][j];
		int connectFive = 1;
		for(; x < i && x > 0; x++) {
			if(!board[x][y].isEmpty() 
					&& !board[x][y].equals("*")
					&& board[x][y].equals(disc)) {
				connectFive++;
			}
		}
		if(connectFive == 5) return true;
		connectFive = 1;
		x = i + 4;
		for(; x > i && x < board.length; x--) {
			if(!board[x][y].isEmpty() 
					&& !board[x][y].equals("*")
					&& board[x][y].equals(disc)) {
				connectFive++;
			}
		}
		if(connectFive == 5) return true;
		return false;
	}
	public boolean checkConnectDiagonal(String [][] board, int i, int j) {
		//Bottom-Left Diagonal
		int x = i + 4 , y = j - 4;
		String disc = board[i][j];
		int connectFive = 1;
		for(; x > i && x < board.length && y >= 0 && y < j; x--, y++) {
			if(!board[x][y].isEmpty() 
					&& !board[x][y].equals("*")
					&& board[x][y].equals(disc)) {
				connectFive++;
			}
		}
		if(connectFive == 5) return true;
		connectFive = 1;
		//Bottom-Right Diagonal
		x = i + 4 ; y = j + 4;
		for(; x > i && x < board.length && y > j && y < board[i].length; x--, y--) {
			if(!board[x][y].isEmpty() 
					&& !board[x][y].equals("*")
					&& board[x][y].equals(disc)) {
				connectFive++;
			}
		}
		if(connectFive == 5) return true;
		connectFive = 1;
		//Top-Left Diagonal
		x = i - 4 ; y = j - 4;
		for(; x < i && x >= 0 && y > j && y >= 0 && y < j; x++, y++) {
			if(!board[x][y].isEmpty() 
					&& !board[x][y].equals("*")
					&& board[x][y].equals(disc)) {
				connectFive++;
			}
		}
		if(connectFive == 5) return true;
		connectFive = 1;
		//Top-Right Diagonal
		x = i - 4 ; y = j + 4;
		for(; x < i && x >= 0 && y > j && y > j && y < board[i].length; x++, y--) {
			if(!board[x][y].isEmpty() 
					&& !board[x][y].equals("*")
					&& board[x][y].equals(disc)) {
				connectFive++;
			}
		}
		if(connectFive == 5) return true;
		connectFive = 1;
		return false;
	}
    
    

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
    
}
