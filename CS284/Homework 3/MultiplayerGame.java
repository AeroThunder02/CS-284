package hw3;

public class MultiplayerGame {
	
	private GameEntity[] index; 
	private GameEntity turnTaker; 
	
	//main methods
	public MultiplayerGame(int n) {
		//checks for any illegal arguments
		if (n < 0) {
			throw new IllegalArgumentException(
					"You cannot start a game with a negative number of players");	
		}
		if (n == 0) {
			throw new IllegalArgumentException(
					"You cannot start a game without any players");
		}
		//make a new list of length n to contain all our players
		index = new GameEntity[n];
		
		for(int i = 0; i < n; i++) {         
			index[i] = new GamePlayer(null, null, i );
		}
		
		for(int j = 0; j < n; j++) {
			if(j == 0) {
				index[j].prev = index[n-1];    //Start
				index[j].next = index[j+1];
			}
			else if(j == n-1) {
				index[j].next = index[0];      //End
				index[j].prev = index[j-1];
			}
			else {
				index[j].next = index[j+1];    //Middle
				index[j].prev = index[j-1];
			}
		}
	}
	
	//method to count pieces in the game
	public int size() {
		int count = 0;
		GameEntity current = index[0]; 
		while(current.next != index[0]) {       //Loop to iterate through and count pieces
			if(!current.isGamePlayer()) {       //If current entity isnt a player, add to count, otherwise, go on
				count++;
			}
			current = current.next;
		}
		return count;
	}
	
	//method to add more pieces (to specific players)
	public void addGamePiece(int playerId, String name, int strength) {
		GameEntity current = index[playerId]; //Making a reference variable to work with
		
		//exceptions
		if(! index[playerId].isGamePlayer()) {
			throw new IllegalArgumentException("addGamePiece: no such player");
		}
		
		
		for (int i = 0; i < index.length-1; i++) {
			if(i == playerId) {
				if(current == new GamePiece(current.prev, current.next, name, strength)) {
					throw new IllegalArgumentException("addGamePiece: duplicate entity");
				}
			}
		}
		
		GamePiece toAdd = new GamePiece(index[playerId], index[playerId].next, name, strength);	
		index[playerId].next.prev = toAdd;
		index[playerId].next = toAdd;
	}
	
	//method to remove pieces from specific players
	public void removeGamePiece(int playerId, String name) {    
		GameEntity current = index[playerId];  //Making a reference variable to work with
		if(! index[playerId].isGamePlayer()) {
			throw new IllegalArgumentException("removeGamePiece: no such player");
		}
		
		while(!current.isGamePlayer()) {
			if(!(((GamePiece)current).getName() == name)) {
				current = current.next;
			}
			else {
				throw new IllegalArgumentException("removegamePiece: entity does not exist");
			}
		}
		
		//loop that checks if the name is the same, if it is, then disconnect the next and previous links to remove it
		while(!current.isGamePlayer()) {
			if(current.getName() == name) {
				current.next.prev = current.prev;
				current.prev.next = current.next;
				current.next = null;
				current.prev = null;
			}
			current = current.next;
		}
	}
	
	//method that checks for game piece (all players)
	public boolean hasGamePiece (String name) {
		
		//loop that goes through ENTIRE list and checks the name of each. If we get a match, return true (CASE SENSITIVE)
		for(GameEntity current : index) {
			if(!current.isGamePlayer()){
				if(current.getName() == name) {
					return true;
				}
			}
			current = current.next;
		}
		return false;
	}
	
	public void removeAllGamePieces(int playerId) {
		GameEntity current = index[playerId];
		
		if(! index[playerId].isGamePlayer()) {
			throw new IllegalArgumentException("removeAllgamePieces: no such player");
		}
		
		//If the current object is not a game player, go next
		while(!current.isGamePlayer()) {
			current = current.next;
		}
		
		//This will basically set the next value to be a player, and the previous value to be the last piece of previous player
		//therefore, removing everything in between
		index[playerId].next = current;
		current.prev = index[playerId];
	}
	
	public void increaseStrength(int playerId, int n) {
		GameEntity current = index[playerId];
		
	
		if(! index[playerId].isGamePlayer()) {
			throw new IllegalArgumentException("increaseStrength: no such player");
		}
		
		//As long as its not a player, iteratively update each piece in the list
		while(!current.isGamePlayer()) {
			((GamePiece)current).updateStrength(n);
			current.next = current.next.next;
		}	
	}
	
	public void removePlayer(int playerId) {            //TODO this is broken, makes player0 the end
		GameEntity current = index[playerId].next;		//Forgot to fix, unsure how and i only have 30 minutes
		
		if(! index[playerId].isGamePlayer()) {
			throw new IllegalArgumentException("increaseStrength: no such player");
		}
		
		while(!current.isGamePlayer()) {
			current = current.next;
		}
		current.prev = index[playerId].prev;
		current.prev.next = current;
		index[playerId] = null;
	}
	
	public void swapPieces(int playerId1, int playerId2) {
		if((!index[playerId1].isGamePlayer()) || (!index[playerId2].isGamePlayer())){
			throw new IllegalArgumentException("swapPieces: no such player");
		}
		
		GameEntity p1Pieces = index[playerId1].next;
		GameEntity p2Pieces = index[playerId2].next;
		
		//Set player.next to point to the new set of pieces
		index[playerId1].next = p2Pieces;
		index[playerId2].next = p1Pieces;
		
		//get last pieces for each player
		while(!p1Pieces.next.isGamePlayer()) {
			p1Pieces = p1Pieces.next;
		}
		
		while(!p2Pieces.next.isGamePlayer()) {
			p2Pieces = p2Pieces.next;
		}
		
		//make the last piece point to the new next player
		GameEntity placeHolder = p2Pieces.next;
		p2Pieces.next = p1Pieces.next;
		p1Pieces.next = placeHolder;
		
		//make the next player.prev point to the proper last piece
		p1Pieces.next.prev = p1Pieces;
		p2Pieces.next.prev = p2Pieces;
		
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		GameEntity current = null;
		GameEntity start = null; //DLL is cyclic -- so let's keep track of where we start
		
		//Find the first un-removed player 
		for (int i = 0; i < index.length ; i++) {
			if (index[i] != null) { // or however you check that the player has been removed
				current = index[i];
				start = index[i];
				break;
			}
		}
		
		//Find that player's last piece
		while (!current.next.isGamePlayer()) {
			current = current.next;
			start = start.next;
		}
		
		sb.append("[");
		sb.append(current.toString());
		current = current.next;
		while (current != start) { //check that we haven't cycled back to the beginning
			sb.append("; ");
			sb.append(current.toString());
			current = current.next;
		}
		sb.append("]");
		return sb.toString();
	}
	
	public String toStringReverse() {
		StringBuilder sb = new StringBuilder();
		GameEntity current = null;
		GameEntity start = null; //DLL is cyclic -- so let's keep track of where we start
		
		//Find the last un-removed player 
		for (int i = index.length - 1; i >= 0 ; i--) {
			if (index[i] != null) { // or however you check that the player has been removed
				current = index[i];
				start = index[i];
				break;
			}
		}
		
		//Find that player's last piece
		while (!current.next.isGamePlayer()) {
			current = current.next;
			start = start.next;
		}
		
		sb.append("[");
		sb.append(current.toString());
		current = current.prev;
		while (current != start) { //check that we haven't cycled back to the beginning
			sb.append("; ");
			sb.append(current.toString());
			current = current.prev;
		}
		sb.append("]");
		return sb.toString();
		
	}
	
	
	public void initializeTurnTracker() {
		GameEntity current = null;
		for (int i = 0; i < index.length ; i++) {
			if (index[i] != null) { // or however you check that the player has been removed
				current = index[i];
				break;
			}
		}
		turnTaker = current;
	}
	
	public void nextPlayer() {
		turnTaker = turnTaker.next;
		while(!turnTaker.isGamePlayer()) {
			turnTaker = turnTaker.next;
		}
	}
	
	public void nextEntity() {
		turnTaker = turnTaker.next;
	}
	
	public void prevPlayer() {
		turnTaker = turnTaker.prev;
		while(!turnTaker.isGamePlayer()) {
			turnTaker = turnTaker.prev;
		}
		
	}
	
	public String currentEntityToString(){
		return turnTaker.toString();
	}
	
	public static void main(String[] args) {
		//EXAMPLE 1 
		MultiplayerGame g1 = new MultiplayerGame(3);
		g1.addGamePiece(2, "BluePiece", 5);
		g1.addGamePiece(1, "PurplePiece", 2);
		g1.addGamePiece(1, "OrangePiece", 17);
		System.out.println(g1.toString());
		
		
		//Expected output: 
		//[Player0; Player1; GamePiece: OrangePiece strength: 17; GamePiece: PurplePiece strength: 2; Player2; GamePiece: BluePiece strength: 5]
		
		System.out.println(g1.toStringReverse());
		//Expected output:
		//[GamePiece: BluePiece strength: 5; Player2; GamePiece: PurplePiece strength: 2; GamePiece: OrangePiece strength: 17; Player1; Player0]
		
		g1.removePlayer(2);
		System.out.println(g1.toStringReverse());
		//Expected output:
		//[GamePiece: PurplePiece strength: 2; GamePiece: OrangePiece strength: 17; Player1; Player0]

		//EXAMPLE 2
		MultiplayerGame g2 = new MultiplayerGame(3);
		g2.addGamePiece(2, "BluePiece", 5);
		g2.addGamePiece(1, "PurplePiece", 2);
		g2.addGamePiece(1, "OrangePiece", 17);
		g2.removePlayer(1);
		g2.increaseStrength(2,3);
		System.out.println(g2.toString());
		
		//Expected output:
		//[Player0; Player2; GamePiece: BluePiece strength: 8]
		
		System.out.println(g2.toStringReverse());
		//Expected output:
		//[GamePiece: BluePiece strength: 8; Player2; Player0]

		//EXAMPLE 3
		MultiplayerGame g3 = new MultiplayerGame(3);
		g3.addGamePiece(2, "BluePiece", 5);
		g3.addGamePiece(1, "PurplePiece", 2);
		g3.addGamePiece(1, "OrangePiece", 17);
		g3.removeGamePiece(1, "PurplePiece");
		System.out.println(g3.toString());
		
		//Expected output:
		//[Player0; Player1; GamePiece: OrangePiece strength: 17; Player2; GamePiece: BluePiece strength: 5]

		System.out.println(g3.toStringReverse());
		//Expected output
		//[GamePiece: BluePiece strength: 5; Player2; GamePiece: OrangePiece strength: 17; Player1; Player0]


	
	}

}
