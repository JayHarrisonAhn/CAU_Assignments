package Piece;

import java.awt.*;

public class Bishop extends ChessPiece{
    public ChessPieceSprite myInstance = ChessPieceSprite.getInstance();

    public Bishop(int team, Color color, ChessPieceSprite.ChessPieceSpriteType type) {
        super.team = team;
        super.color = color;
        super.image = myInstance.getChessPiece(type);
    }

}
