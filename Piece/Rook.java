package Piece;


import java.awt.Color;

public class Rook extends ChessPiece{
    public ChessPieceSprite myInstance = ChessPieceSprite.getInstance();

    public Rook(int team, Color color, ChessPieceSprite.ChessPieceSpriteType type) {
        super.team = team;
        super.color = color;
        super.image = myInstance.getChessPiece(type);
        super.name = "rook";
    }

}
