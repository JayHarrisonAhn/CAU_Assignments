package Piece;

import java.awt.Color;

public class King extends ChessPiece{
    public ChessPieceSprite myInstance = ChessPieceSprite.getInstance();

    public King(int team, Color color, ChessPieceSprite.ChessPieceSpriteType type) {
        super.team = team;
        super.color = color;
        super.image = myInstance.getChessPiece(type);
    }

}
