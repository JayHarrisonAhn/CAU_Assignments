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
        //留� �넃湲� :white 0 red 1 black 2 green 3
        //留� �넃湲� :white 0 red 1 black 2 green 3
    boolean isValidMove(Position from, Position to) {//TODO : from�뿉�꽌 to濡쒖쓽 �씠�룞�씠 媛��뒫�븳 寃껋씤吏� �뙋�떒�븯�뒗 硫붿꽌�뱶
        if(to.x<3&&to.y<3)
            return false;
        if(11<=to.x&&13>=to.x&&to.y>=0&&to.y<=2)
            return false;
        if(0<=to.x&&2>=to.x&&to.y>=11&&to.y<=13)
            return false;
        if(11<=to.x&&13>=to.x&&to.y>=11&&to.y<=13)
            return false;
        if (board.cells[from.x][from.y].piece.getClass().getCanonicalName() == "Piece.Pawn") {
            if (board.cells[from.x][from.y].piece.team == 2) {//black 2
                if ((from.x - to.x == -2) && (from.x == 1) && (to.y == from.y)) {
                    if (board.cells[to.x - 1][to.y].piece == null) {
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return false;
                        } else
                            return true;
                    } else
                        return false;
                } else if ((from.x - to.x == -1) && (to.y == from.y)) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return false;
                    } else
                        return true;
                }else if (from.x - to.x == -1 && (from.y - to.y == -1 || from.y - to.y == 1)) {
                    if(board.cells[to.x][to.y].piece != null) {
                        if(board.cells[from.x][from.y].piece.team%2!=board.cells[to.x][to.y].piece.team%2) {
                            return true;
                        }
                        else
                            return false;
                    }else
                        return false;
                }else
                    return false;
            }else if (board.cells[from.x][from.y].piece.team == 3) {//green 3
                if ((from.y - to.y == 2) && (from.y == 12) && (to.x == from.x)) {
                    if (board.cells[to.x][to.y + 1].piece == null) {
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return false;
                        } else
                            return true;
                    } else
                        return false;
                } else if ((from.y - to.y == 1) && (to.x == from.x)) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return false;
                    } else
                        return true;
                }else if (from.y - to.y == 1 && (from.x - to.x == -1 || from.x - to.x == 1)) {
                    if(board.cells[to.x][to.y].piece != null) {
                        if(board.cells[from.x][from.y].piece.team%2!=board.cells[to.x][to.y].piece.team%2) {
                            return true;
                        }
                        else
                            return false;
                    }else
                        return false;
                }else
                    return false;
            }else if (board.cells[from.x][from.y].piece.team == 0) {// white 0
                if ((from.x - to.x == 2) && (from.x == 12) && (to.y == from.y)) {
                    if (board.cells[to.x + 1][to.y].piece == null) {
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return false;
                        } else
                            return true;
                    } else
                        return false;
                } else if ((from.x - to.x == 1) && (to.y == from.y)) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return false;
                    } else
                        return true;
                }else if (from.x - to.x == 1 && (from.y - to.y == -1 || from.y - to.y == 1)) {
                    if(board.cells[to.x][to.y].piece != null) {
                        if(board.cells[from.x][from.y].piece.team%2!=board.cells[to.x][to.y].piece.team%2) {
                            return true;
                        }
                        else
                            return false;
                    }else
                        return false;
                }else
                    return false;
            }else if (board.cells[from.x][from.y].piece.team == 1) {//red 1
                if ((from.y - to.y == -2) && (from.y == 1) && (to.x == from.x)) {
                    if (board.cells[to.x][to.y-1].piece == null) {
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return false;
                        } else
                            return true;
                    } else
                        return false;
                } else if ((from.y - to.y == -1) && (to.x == from.x)) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return false;
                    } else
                        return true;
                }else if (from.y - to.y == -1 && (from.x - to.x == -1 || from.x - to.x == 1)) {
                    if(board.cells[to.x][to.y].piece != null) {
                        if(board.cells[from.x][from.y].piece.team%2!=board.cells[to.x][to.y].piece.team%2) {
                            return true;
                        }
                        else
                            return false;
                    }else
                        return false;
                }else
                    return false;
            }else
                return false;
        }

        else if (board.cells[from.x][from.y].piece.getClass().getCanonicalName() == "Piece.Rook") {
            if (board.cells[from.x][from.y].piece.team %2== 0) {// black white
                if (from.x == to.x) {
                    if (to.y < from.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[to.x][to.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team %2== 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team %2== 1);
                        } else
                            return true;
                    }
                } else if (from.y == to.y) {
                    if (to.x < from.x) {
                        for (int i = from.x - to.x - 1; i > 0; i--) {
                            if (board.cells[to.x + i][to.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team %2== 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.x - from.x - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team %2== 1);
                        } else
                            return true;
                    }
                } else
                    return false;
            }else {// green red team%2==1
                if (from.x == to.x) {
                    if (to.y < from.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[to.x][to.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 0);
                        } else
                            return true;
                    }
                } else if (from.y == to.y) {
                    if (to.x < from.x) {
                        for (int i = from.x - to.x - 1; i > 0; i--) {
                            if (board.cells[to.x + i][to.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.x - from.x - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 0);
                        } else
                            return true;
                    }
                } else
                    return false;
            }
        }

        else if (board.cells[from.x][from.y].piece.getClass().getCanonicalName() == "Piece.Knight") {
            if (board.cells[from.x][from.y].piece.team %2== 0) {// black white
                if ((from.x + 2 == to.x) && (from.y + 1 == to.y || from.y - 1 == to.y)) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return (board.cells[to.x][to.y].piece.team %2== 1);
                    } else
                        return true;
                } else if ((from.x - 2 == to.x) && (from.y + 1 == to.y || from.y - 1 == to.y)) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return (board.cells[to.x][to.y].piece.team %2== 1);
                    } else
                        return true;
                } else if ((from.y + 2 == to.y) && (from.x + 1 == to.x || from.x - 1 == to.x)) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return (board.cells[to.x][to.y].piece.team %2== 1);
                    } else
                        return true;
                } else if ((from.y - 2 == to.y) && (from.x + 1 == to.x || from.x - 1 == to.x)) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return (board.cells[to.x][to.y].piece.team %2== 1);
                    } else
                        return true;
                } else
                    return false;
            }else {// white
                if ((from.x + 2 == to.x) && (from.y + 1 == to.y || from.y - 1 == to.y)) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return (board.cells[to.x][to.y].piece.team %2== 0);
                    } else
                        return true;
                } else if ((from.x - 2 == to.x) && (from.y + 1 == to.y || from.y - 1 == to.y)) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return (board.cells[to.x][to.y].piece.team %2== 0);
                    } else
                        return true;
                } else if ((from.y + 2 == to.y) && (from.x + 1 == to.x || from.x - 1 == to.x)) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return (board.cells[to.x][to.y].piece.team %2== 0);
                    } else
                        return true;
                } else if ((from.y - 2 == to.y) && (from.x + 1 == to.x || from.x - 1 == to.x)) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return (board.cells[to.x][to.y].piece.team %2== 0);
                    } else
                        return true;
                } else
                    return false;
            }
        }

        else if (board.cells[from.x][from.y].piece.getClass().getCanonicalName() == "Piece.Bishop") {
            if (board.cells[from.x][from.y].piece.team %2== 0) {// black white
                if (from.x + from.y == to.x + to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 1);
                        } else
                            return true;
                    }
                } else if (from.x - to.x == from.y - to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 1);
                        } else
                            return true;
                    }
                } else
                    return false;
            }else {// green red
                if (from.x + from.y == to.x + to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 0);
                        } else
                            return true;
                    }
                } else if (from.x - to.x == from.y - to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 0);
                        } else
                            return true;
                    }
                } else
                    return false;
            }
        }

        else if (board.cells[from.x][from.y].piece.getClass().getCanonicalName() == "Piece.Queen") {
            if (board.cells[from.x][from.y].piece.team %2== 0) {// black white
                if (from.x == to.x) {
                    if (to.y < from.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[to.x][to.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team %2== 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team %2== 1);
                        } else
                            return true;
                    }
                } else if (from.y == to.y) {
                    if (to.x < from.x) {
                        for (int i = from.x - to.x - 1; i > 0; i--) {
                            if (board.cells[to.x + i][to.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team %2== 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.x - from.x - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team %2== 1);
                        } else
                            return true;
                    }
                }else if (from.x + from.y == to.x + to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 1);
                        } else
                            return true;
                    }
                } else if (from.x - to.x == from.y - to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 1);
                        } else
                            return true;
                    }
                } else
                    return false;
            }else {//green red
                if (from.x == to.x) {
                    if (to.y < from.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[to.x][to.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team %2== 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team %2== 0);
                        } else
                            return true;
                    }
                } else if (from.y == to.y) {
                    if (to.x < from.x) {
                        for (int i = from.x - to.x - 1; i > 0; i--) {
                            if (board.cells[to.x + i][to.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team %2== 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.x - from.x - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team %2== 0);
                        } else
                            return true;
                    }
                }else if (from.x + from.y == to.x + to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 0);
                        } else
                            return true;
                    }
                } else if (from.x - to.x == from.y - to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                            return (board.cells[to.x][to.y].piece.team%2 == 0);
                        } else
                            return true;
                    }
                } else
                    return false;
            }
        }

        else if (board.cells[from.x][from.y].piece.getClass().getCanonicalName() == "Piece.King") {
            if (board.cells[from.x][from.y].piece.team %2== 0) {// black white
                if (to.x - from.x <= 1 && to.x - from.x >= -1 && to.y - from.y <= 1 && to.y - from.y >= -1) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return (board.cells[to.x][to.y].piece.team %2== 1);
                    } else
                        return true;
                } else
                    return false;
            }else {
                if (to.x - from.x <= 1 && to.x - from.x >= -1 && to.y - from.y <= 1 && to.y - from.y >= -1) {
                    if (board.cells[to.x][to.y].piece != null) {// �옟�쓣�븣
                        return (board.cells[to.x][to.y].piece.team %2== 0);
                    } else
                        return true;
                } else
                    return false;
            }
        }

        else
            return false;
    }

    private ChessBoard1 tempboard;

    boolean isStalemate() {//TODO : 스테일메이트 상황인지 판단해주는 메서드
        return false;//개발되기 전까지는 항상 false를 리턴하게끔 만들어주세요
    }
}