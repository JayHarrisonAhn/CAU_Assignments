package Piece;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ChessPiece {
    public int team;
    public Color color;
    public BufferedImage image;

    public ChessPieceSprite myInstance = ChessPieceSprite.getInstance();

    public ChessPiece(int team, ChessPieceSprite.ChessPieceSpriteType type) {
        switch(team) {
            case 0:
                color = Color.WHITE;
                break;
            case 1:
                color = Color.RED;
                break;
            case 2:
                color = Color.BLACK;
                break;
            case 3:
                color = Color.GREEN;
                break;

        }
        this.team = team;
        this.image = myInstance.getChessPiece(type);
    }
}
