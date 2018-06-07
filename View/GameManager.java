package View;

import Piece.ChessPiece;
import View.panels.*;
import javafx.geometry.Pos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public abstract class GameManager extends JFrame implements MouseListener {
    public GameManager() {
        setSize(numOfWidth()*100, (numOfWidth()+1)*100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add("North", display);

    }

    abstract int numOfWidth();
    abstract int numOfTeams();

    protected ChessBoard board;
    private ChessBoard tempBoard;
    protected ChessPiece onHand;
    protected int turn = 0;
    protected Position[] king = new Position[numOfTeams()];
    protected StatusDisplay display = new StatusDisplay();
    protected int[][] danger = new int[numOfWidth()][numOfWidth()];

    abstract int[][] dangerMapping();
    abstract boolean isValidMove(Position from, Position to);

    // MouseListener 援ы쁽
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) { //mouseClicked는 가끔 인식이 안될 때가 있어서 mouseReleased로 구현합니다.
        ChessBoardCell selected = (ChessBoardCell) e.getSource();

        if (onHand != null) {                   //손에 체스를 들고있을 상황
            if (selected.getBackground() == Color.BLUE) {   //선택한 칸이 파란색일 경우(움직일 수 있는 곳인 경우)
                selected.piece = onHand;
                onHand = null;
                if(selected.piece != null) {
                    if(selected.piece.getClass().getCanonicalName()=="Piece.King") {
                        king[turn] = positionofKing();
                    }
                }
                turnToNext();
                board.refresh();

                if(isCheckmate()) {
                    System.out.println("체크메이트");
                }

                if(isCheck(turn)) {
                    display.showCheck(board.cells[king[turn].x][king[turn].y].piece.color);
                }
                else {
                    display.showNothing();
                }
            }
            else if (selected.getBackground() == Color.YELLOW) {    //선택한 칸이 노란색일 경우(자기위치를 가리킨 경우) -> 실행취소시킨다.
                selected.piece = onHand;
                onHand = null;
                board.refresh();
            }
        } else if (selected.piece == null) {    //아무것도 없는 칸을 선택했을 경우에는 아무것도 안하고 지나간다.
        } else {
            if (turn == selected.piece.team) {  //현재 차례인 플레이어가 자신의 말을 잡았을 경우
                danger=dangerMapping();

//				System.out.println(selected.piece.getClass().getCanonicalName());
                validMoves(selected.position);

                onHand = selected.piece;        //선택된 칸의 말을 onHand에 올려버린다
                selected.piece = null;
                selected.setBackground(Color.YELLOW);


            }
        }
    }

    protected void validMoves(Position from) {// validMoveArr[0].x=num; 이동가능타일 갯수
        Position to = new Position();
        for (int i = 0; i < numOfWidth(); i++) {
            to.x = i;
            for (int j = 0; j < numOfWidth(); j++) {
                to.y = j;
                if (isValidMove(from, to)) {
//                    System.out.println("(" + to.x + " " + to.y + ")");
                    board.cells[to.x][to.y].setBackground(Color.BLUE);
                }
            }
        }
    }

    boolean isCheckmate() {

        Position from = new Position();
        Position to = new Position();
        ChessPiece temp;
        for (int i = 0; i < numOfWidth(); i++) {

            from.x = i;
            for (int j = 0; j < numOfWidth(); j++) {
                from.y = j;
                if(board.cells[i][j].piece != null) {
                    if(board.cells[i][j].piece.team == turn) {
                        for (int k = 0; k < numOfWidth(); k++) {
                            to.x = k;
                            for (int l = 0; l < numOfWidth(); l++) {
                                to.y = l;

                                if(isValidMove(from, to)) {
                                    temp = board.cells[to.x][to.y].piece;
                                    board.cells[to.x][to.y].piece = board.cells[from.x][from.y].piece;
                                    board.cells[from.x][from.y].piece = null;

                                    System.out.print(from.x);
                                    System.out.print(", ");
                                    System.out.println(from.y);
                                    if(!isCheck(turn)) {
                                        board.cells[from.x][from.y].piece = board.cells[to.x][to.y].piece;
                                        board.cells[to.x][to.y].piece = temp;
                                        return false;
                                    }
                                    board.cells[from.x][from.y].piece = board.cells[to.x][to.y].piece;
                                    board.cells[to.x][to.y].piece = temp;
                                    temp = null;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    boolean isCheck(int now) {//다음 차례의 사람이 내 왕을 죽일 수 있는가?
        for (int i = 0; i < numOfWidth(); i++) {
            for (int j = 0; j < numOfWidth(); j++) {//탐색한다
                if((board.cells[i][j].piece != null)) {//말이 있는 곳인데
                    if(board.cells[i][j].piece.team == (now+1)%numOfTeams()) {//다음 상대의 말이
                        if(isValidMove(board.cells[i][j].position, positionofKing(now))) {//내 왕의 위치로 올 수 있는가?
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    protected Position positionofKing() {// 왕의 위치찾기 turn ==0 black 임
        for (int i = 0; i < numOfWidth(); i++) {
            for (int j = 0; j < numOfWidth(); j++) {
                if (board.cells[i][j].piece != null) {
                    if ((board.cells[i][j].piece.getClass().getCanonicalName() == "Piece.King") && (board.cells[i][j].piece.team == turn)) {
                        return (board.cells[i][j].position);
                    }
                }
            }
        }
        return null;
    }
    protected Position positionofKing(int team) {// 왕의 위치찾기 turn ==0 black 임
        for (int i = 0; i < numOfWidth(); i++) {
            for (int j = 0; j < numOfWidth(); j++) {
                if (board.cells[i][j].piece != null) {
                    if ((board.cells[i][j].piece.getClass().getCanonicalName() == "Piece.King") && (board.cells[i][j].piece.team == team)) {
                        return (board.cells[i][j].position);
                    }
                }
            }
        }
        return null;
    }

    private void turnToNext() {
        turn = (turn + 1) % numOfTeams();
        display.updateTurn(turn);
    }
}
