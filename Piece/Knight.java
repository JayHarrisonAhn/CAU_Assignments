package Piece;

import java.awt.Color;

public class Knight extends ChessPiece{
    public ChessPieceSprite myInstance = ChessPieceSprite.getInstance();

    public Knight(int team, Color color, ChessPieceSprite.ChessPieceSpriteType type) {
        super.team = team;
        super.color = color;
        super.image = myInstance.getChessPiece(type);
        super.name = "Knight";
    }

}
