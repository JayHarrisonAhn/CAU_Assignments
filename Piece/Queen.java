package Piece;

import java.awt.Color;

public class Queen extends ChessPiece{
    public ChessPieceSprite myInstance = ChessPieceSprite.getInstance();

    public Queen(int team, Color color, ChessPieceSprite.ChessPieceSpriteType type) {
        super.team = team;
        super.color = color;
        super.image = myInstance.getChessPiece(type);
    }

}