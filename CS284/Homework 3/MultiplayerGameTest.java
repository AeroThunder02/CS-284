package hw3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MultiplayerGameTest {

	@Test
	public void testMultGame1() {
		MultiplayerGame g1 = new MultiplayerGame(3);
		g1.addGamePiece(2, "BluePiece", 5);
		g1.addGamePiece(1, "PurplePiece", 2);
		g1.addGamePiece(1, "OrangePiece", 17);
		assertEquals(g1.toString(), "[Player0; Player1; GamePiece: OrangePiece strength: 17; GamePiece: PurplePiece strength: 2; Player2; GamePiece: BluePiece strength: 5]");
	}
	
	@Test
	public void testMultGame2() {
		MultiplayerGame g2 = new MultiplayerGame(3);
		g2.addGamePiece(2, "BluePiece", 5);
		g2.addGamePiece(1, "PurplePiece", 2);
		g2.addGamePiece(1, "OrangePiece", 17);
		g2.removePlayer(1);
		g2.increaseStrength(2,3);
		assertEquals(g2.toString(), "[Player0; Player2; GamePiece: BluePiece strength: 8]");
	}
	

}
