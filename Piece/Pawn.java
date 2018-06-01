package Piece;

import java.awt.*;

public class Pawn extends ChessPiece {
	public ChessPieceSprite myInstance = ChessPieceSprite.getInstance();

	public Pawn(int team, Color color, ChessPieceSprite.ChessPieceSpriteType type) {
		super.team = team;
		super.color = color;
		super.image = myInstance.getChessPiece(type);
	}

}