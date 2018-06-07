package View;

import Piece.ChessPiece;
import View.panels.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game2Manager extends GameManager {
    @Override
    int numOfWidth() {
        return 14;
    }
    @Override
    int numOfTeams() {
        return 4;
    }
    @Override
    int turnToNext() {
        return (turn + 1) % 4;
    }
    @Override
    int turnToPrev() {
        return (turn - 1) % 4;
    }

    public Game2Manager() {
        super();

        //board 초기화
        super.board = new ChessBoard2();
        add("Center", board);
        for (int i = 0; i < numOfWidth(); i++) {
            for (int j = 0; j < numOfWidth(); j++) {
                ((ChessBoard2) super.board).cells[i][j].addMouseListener(this);
            }
        }

        //king 초기화
        for(int i=0;i<numOfTeams();i++) {
            king[i] = positionofKing(i);
        }
        setVisible(true);
    }

    @Override
    boolean isValidMove(Position from, Position to) {//TODO : 맞춰야합니다.
        return true;
    }

    private ChessBoard1 tempboard;

    boolean isStalemate() {//TODO : 스테일메이트 상황인지 판단해주는 메서드
        return false;//개발되기 전까지는 항상 false를 리턴하게끔 만들어주세요
    }
}