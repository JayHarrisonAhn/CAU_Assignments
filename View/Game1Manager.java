package View;

import Piece.ChessPiece;
import View.panels.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game1Manager extends GameManager {
    @Override
    int numOfWidth() {
        return 8;
    }
    @Override
    int numOfTeams() {
        return 2;
    }



	public Game1Manager() {
        super();

        //board 초기화
        super.board = new ChessBoard1();
        add("Center", board);
        for (int i = 0; i < numOfWidth(); i++) {
            for (int j = 0; j < numOfWidth(); j++) {
                ((ChessBoard1) super.board).cells[i][j].addMouseListener(this);
            }
        }

        //king 초기화
        for(int i=0;i<numOfTeams();i++) {
            king[i] = positionofKing(i);
        }
        setVisible(true);
	}


    @Override
    int[][] dangerMapping() {
		int[][] out = new int[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++)
				out[i][j] = 0;
		}
		Position t = new Position();
		if (turn % 2 == 0) {// black turn
			for (int i = 0; i < 8; i++) {
				t.x=i;
				for (int j = 0; j < 8; j++) {
					t.y=j;
					if (board.cells[i][j].piece != null) {
						if (board.cells[i][j].piece.team == 1 && board.cells[i][j].piece.getClass().getCanonicalName() != "Piece.King") {
							if (isValidMove(t, positionofKing())) {
								out[t.x][t.y] = 1;
//								System.out.printf("1 ");
							}
							else {
//                                System.out.printf("0 ");
                            }
						}
					}
				}
//				System.out.println();
			}
			return out;
		} else {// white turn
			for (int i = 0; i < 8; i++) {
				t.x=i;
				for (int j = 0; j < 8; j++) {
					t.y=j;
					if (board.cells[i][j].piece != null) {
						if (board.cells[i][j].piece.team == 0 && board.cells[i][j].piece.getClass().getCanonicalName() != "Piece.King") {
							if (isValidMove(t, positionofKing())) {
								out[t.x][t.y] = 1;
//								System.out.printf("1 ");
							}
							else {
//                                System.out.printf("0 ");
                            }
						}
					}
				}
//				System.out.println();
			}
			return out;
		}
	}

    @Override
    boolean isValidMove(Position from, Position to) {
        if (board.cells[from.x][from.y].piece.getClass().getCanonicalName() == "Piece.Pawn") {
            if (board.cells[from.x][from.y].piece.team == 1) {// black 0
                if ((from.x - to.x == -2) && (from.x == 1) && (to.y == from.y)) {
                    if (board.cells[to.x - 1][to.y].piece == null) {
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return false;
                        } else
                            return true;
                    } else
                        return false;

                } else if ((from.x - to.x == -1) && (to.y == from.y)) {
                    if (board.cells[to.x][to.y].piece != null) {// 잡을때
                        return false;
                    } else
                        return true;
                } else if (from.x - to.x == -1 && (from.y - to.y == -1 || from.y - to.y == 1)) {
                    if (board.cells[to.x][to.y].piece != null) {
                        return (board.cells[to.x][to.y].piece.team == 0);
                    } else
                        return false;
                } else
                    return false;
            } else {// white 1
                if ((from.x - to.x == 2) && (from.x == 6) && (to.y == from.y)) {
                    if (board.cells[to.x + 1][to.y].piece == null) {
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return false;
                        } else
                            return true;
                    }
                    else
                        return false;
                } else if ((from.x - to.x == 1) && (to.y == from.y)) {
                    if (board.cells[to.x][to.y].piece != null) {// 잡을때
                        return false;
                    } else
                        return true;
                } else if (from.x - to.x == 1 && (from.y - to.y == -1 || from.y - to.y == 1)) {
                    if (board.cells[to.x][to.y].piece != null) {// 잡을때
                        return (board.cells[to.x][to.y].piece.team == 1);
                    } else
                        return false;
                } else
                    return false;
            }
        } // pawn
        else if (board.cells[from.x][from.y].piece.getClass().getCanonicalName() == "Piece.Rook") {
            if (board.cells[from.x][from.y].piece.team == 0) {// black 0
                if (from.x == to.x) {
                    if (to.y < from.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[to.x][to.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    }
                } else if (from.y == to.y) {
                    if (to.x < from.x) {
                        for (int i = from.x - to.x - 1; i > 0; i--) {
                            if (board.cells[to.x + i][to.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.x - from.x - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    }
                } else
                    return false;
            } else {// white
                if (from.x == to.x) {
                    if (to.y < from.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[to.x][to.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    }
                } else if (from.y == to.y) {
                    if (to.x < from.x) {
                        for (int i = from.x - to.x - 1; i > 0; i--) {
                            if (board.cells[to.x + i][to.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.x - from.x - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    }
                } else
                    return false;
            }
        } // rook

        else if (board.cells[from.x][from.y].piece.getClass().getCanonicalName() == "Piece.Knight") {
            if (board.cells[from.x][from.y].piece.team == 0) {// black
                if ((from.x + 2 == to.x) && (from.y + 1 == to.y || from.y - 1 == to.y)) {
                    if (board.cells[to.x][to.y].piece != null) {// 잡을때
                        return (board.cells[to.x][to.y].piece.team == 1);
                    } else
                        return true;
                } else if ((from.x - 2 == to.x) && (from.y + 1 == to.y || from.y - 1 == to.y)) {
                    if (board.cells[to.x][to.y].piece != null) {// 잡을때
                        return (board.cells[to.x][to.y].piece.team == 1);
                    } else
                        return true;
                } else if ((from.y + 2 == to.y) && (from.x + 1 == to.x || from.x - 1 == to.x)) {
                    if (board.cells[to.x][to.y].piece != null) {// 잡을때
                        return (board.cells[to.x][to.y].piece.team == 1);
                    } else
                        return true;
                } else if ((from.y - 2 == to.y) && (from.x + 1 == to.x || from.x - 1 == to.x)) {
                    if (board.cells[to.x][to.y].piece != null) {// 잡을때
                        return (board.cells[to.x][to.y].piece.team == 1);
                    } else
                        return true;
                } else
                    return false;
            } else {// white
                if ((from.x + 2 == to.x) && (from.y + 1 == to.y || from.y - 1 == to.y)) {
                    if (board.cells[to.x][to.y].piece != null) {// 잡을때
                        return (board.cells[to.x][to.y].piece.team == 0);
                    } else
                        return true;
                } else if ((from.x - 2 == to.x) && (from.y + 1 == to.y || from.y - 1 == to.y)) {
                    if (board.cells[to.x][to.y].piece != null) {// 잡을때
                        return (board.cells[to.x][to.y].piece.team == 0);
                    } else
                        return true;
                } else if ((from.y + 2 == to.y) && (from.x + 1 == to.x || from.x - 1 == to.x)) {
                    if (board.cells[to.x][to.y].piece != null) {// 잡을때
                        return (board.cells[to.x][to.y].piece.team == 0);
                    } else
                        return true;
                } else if ((from.y - 2 == to.y) && (from.x + 1 == to.x || from.x - 1 == to.x)) {
                    if (board.cells[to.x][to.y].piece != null) {// 잡을때
                        return (board.cells[to.x][to.y].piece.team == 0);
                    } else
                        return true;
                } else
                    return false;
            }
        } else if (board.cells[from.x][from.y].piece.getClass().getCanonicalName() == "Piece.Bishop") {
            if (board.cells[from.x][from.y].piece.team == 0) {// black 0
                if (from.x + from.y == to.x + to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    }
                } else if (from.x - to.x == from.y - to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    }
                } else
                    return false;
            } else {// white
                if (from.x + from.y == to.x + to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    }
                } else if (from.x - to.x == from.y - to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    }
                } else
                    return false;
            }
        } else if (board.cells[from.x][from.y].piece.getClass().getCanonicalName() == "Piece.Queen") {
            if (board.cells[from.x][from.y].piece.team == 0) {// black 0
                if (from.x == to.x) {
                    if (to.y < from.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[to.x][to.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    }
                } else if (from.y == to.y) {
                    if (to.x < from.x) {
                        for (int i = from.x - to.x - 1; i > 0; i--) {
                            if (board.cells[to.x + i][to.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.x - from.x - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    }
                } else if (from.x + from.y == to.x + to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    }
                } else if (from.x - to.x == from.y - to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 1);
                        } else
                            return true;
                    }
                } else
                    return false;
            } else {// white 1
                if (from.x == to.x) {
                    if (to.y < from.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[to.x][to.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    }
                } else if (from.y == to.y) {
                    if (to.x < from.x) {
                        for (int i = from.x - to.x - 1; i > 0; i--) {
                            if (board.cells[to.x + i][to.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.x - from.x - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    }
                } else if (from.x + from.y == to.x + to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    }
                } else if (from.x - to.x == from.y - to.y) {
                    if (from.y > to.y) {
                        for (int i = from.y - to.y - 1; i > 0; i--) {
                            if (board.cells[from.x - i][from.y - i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    } else {
                        for (int i = to.y - from.y - 1; i > 0; i--) {
                            if (board.cells[from.x + i][from.y + i].piece != null)
                                return false;
                        }
                        if (board.cells[to.x][to.y].piece != null) {// 잡을때
                            return (board.cells[to.x][to.y].piece.team == 0);
                        } else
                            return true;
                    }
                } else
                    return false;
            }
        } else if (board.cells[from.x][from.y].piece.getClass().getCanonicalName() == "Piece.King") {
            if (board.cells[from.x][from.y].piece.team == 0) {// black 0
                if (to.x - from.x <= 1 && to.x - from.x >= -1 && to.y - from.y <= 1 && to.y - from.y >= -1) {
                    if (board.cells[to.x][to.y].piece != null) {// 잡을때
                        return (board.cells[to.x][to.y].piece.team == 1);
                    } else
                        return true;
                    /*
                     * dangerMap=dangerMapping(danger); for (int i = 0; i < 8; i++) { for (int j =
                     * 0; j < 8; j++) System.out.printf("%d", dangerMap[i][j]);
                     * System.out.println(); } if (board.cells[from.x][from.y].piece.team == 0) {//
                     * black 0 if (board.cells[to.x][to.y].piece != null) { if
                     * (board.cells[from.x][from.y].piece.team == 0) return false; } if
                     * (dangerMap[to.x][to.y] == 1) return false; else return true; } else {// white
                     * 1 if (board.cells[to.x][to.y].piece != null) { if
                     * (board.cells[from.x][from.y].piece.team == 1) return false; } if
                     * (dangerMap[to.x][to.y] == 1) return false; else return true; }
                     */
                } else
                    return false;
            }
            else {
                if (to.x - from.x <= 1 && to.x - from.x >= -1 && to.y - from.y <= 1 && to.y - from.y >= -1) {
                    if (board.cells[to.x][to.y].piece != null) {// 잡을때
                        return (board.cells[to.x][to.y].piece.team == 0);
                    } else
                        return true;
                    /*
                     * dangerMap=dangerMapping(danger); for (int i = 0; i < 8; i++) { for (int j =
                     * 0; j < 8; j++) System.out.printf("%d", dangerMap[i][j]);
                     * System.out.println(); } if (board.cells[from.x][from.y].piece.team == 0) {//
                     * black 0 if (board.cells[to.x][to.y].piece != null) { if
                     * (board.cells[from.x][from.y].piece.team == 0) return false; } if
                     * (dangerMap[to.x][to.y] == 1) return false; else return true; } else {// white
                     * 1 if (board.cells[to.x][to.y].piece != null) { if
                     * (board.cells[from.x][from.y].piece.team == 1) return false; } if
                     * (dangerMap[to.x][to.y] == 1) return false; else return true; }
                     */
                } else
                    return false;
            }
        } else
            return false;

    }

	private ChessBoard1 tempboard;

    boolean isStalemate() {//TODO : 스테일메이트 상황인지 판단해주는 메서드
        return false;//개발되기 전까지는 항상 false를 리턴하게끔 만들어주세요
    }
}