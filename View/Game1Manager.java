package View;

import Piece.ChessPiece;
import View.panels.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Game1Manager extends JFrame implements MouseListener {

	private ChessBoard1 board = new ChessBoard1();
	private StatusDisplay display = new StatusDisplay();
	private int turn = 0;
    Position[] king = new Position[2];
	ChessPiece onHand;

	public Game1Manager() {
		setSize(800, 900);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		add("North", display);
		add("Center", board);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board.cells[i][j].addMouseListener(this);
			}
		}
		setVisible(true);

		for(int i=0;i<2;i++) {  //king 초기화
		    king[i] = PositionofKing(i);
        }
	}

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
		        if(selected.piece != null) {
                    if(selected.piece.getClass().getCanonicalName()=="Piece.King") {
                        king[turn] = selected.position;
                    }
                }


                selected.piece = onHand;
                onHand = null;
                board.refresh();
                turnToNext();

                if(isCheck()) {
                    display.showCheck();
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

	private void turnToNext() {
        turn = (turn + 1) % 2;
        display.updateTurn(turn);
    }

	int[][] danger = new int[8][8];// dangerous cell for king
	// BLACK 0, WHITE 1

	void validMoves(Position from) {// validMoveArr[0].x=num; 이동가능타일 갯수
		Position to = new Position();
		for (int i = 0; i < 8; i++) {
			to.x = i;
			for (int j = 0; j < 8; j++) {
				to.y = j;
				if (isValidMove(from, to)) {
					System.out.println("(" + to.x + " " + to.y + ")");
					board.cells[to.x][to.y].setBackground(Color.BLUE);
				}
			}
		}
	}
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
							if (isValidMove(t, PositionofKing())) {
								out[t.x][t.y] = 1;
								System.out.printf("1 ");
							}
							else
								System.out.printf("0 ");
						}
					}
				}
				System.out.println();
			}
			return out;
		} else {// white turn
			for (int i = 0; i < 8; i++) {
				t.x=i;
				for (int j = 0; j < 8; j++) {
					t.y=j;
					if (board.cells[i][j].piece != null) {
						if (board.cells[i][j].piece.team == 0 && board.cells[i][j].piece.getClass().getCanonicalName() != "Piece.King") {
							if (isValidMove(t, PositionofKing())) {
								out[t.x][t.y] = 1;
								System.out.printf("1 ");
							}
							else
								System.out.printf("0 ");
						}
					}
				}
				System.out.println();
			}
			return out;
		}
	}

	private Position PositionofKing() {// 왕의 위치찾기 turn ==0 black 임
		if (turn % 2 == 0) { // turn BLACK
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (board.cells[i][j].piece != null) {
						if ((board.cells[i][j].piece.getClass().getCanonicalName() == "Piece.King") && (board.cells[i][j].piece.team == 0)) {
							return (board.cells[i][j].position);
						}
					}
				}
			}
			return null;
		} else { // turn WHITE
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (board.cells[i][j].piece != null) {
						if ((board.cells[i][j].piece.getClass().getCanonicalName() == "Piece.King") && (board.cells[i][j].piece.team == 1)) {
							return (board.cells[i][j].position);
						}
					}
				}
			}
			return null;
		}
	}
    private Position PositionofKing(int team) {// 왕의 위치찾기 turn ==0 black 임
        if (team % 2 == 0) { // turn BLACK
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board.cells[i][j].piece != null) {
                        if ((board.cells[i][j].piece.getClass().getCanonicalName() == "Piece.King") && (board.cells[i][j].piece.team == 0)) {
                            return (board.cells[i][j].position);
                        }
                    }
                }
            }
            return null;
        } else { // turn WHITE
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board.cells[i][j].piece != null) {
                        if ((board.cells[i][j].piece.getClass().getCanonicalName() == "Piece.King") && (board.cells[i][j].piece.team == 1)) {
                            return (board.cells[i][j].position);
                        }
                    }
                }
            }
            return null;
        }
    }

    boolean isValidMove(Position from, Position to) {
        if (board.cells[from.x][from.y].piece.getClass().getCanonicalName() == "Piece.Pawn") {
            if (board.cells[from.x][from.y].piece.team == 0) {// black 0
                if ((from.x - to.x == -2) && (from.x == 1) && (to.y == from.y)) {
                    if (board.cells[to.x + 1][to.y].piece == null) {
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
                        return (board.cells[to.x][to.y].piece.team == 1);
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
                        return (board.cells[to.x][to.y].piece.team == 0);
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

	/*void attackable() {
       for(int i=0;i<8;i++) {
          for(int j=0;j<8;j++) {

             if(turn%2==0) {//black turn
                if (board.cells[i][j].piece.team == 1) {//white piece

                   if (board.cells[i][j].piece.getClass().getCanonicalName() == "Piece.Pawn") {
                      if(i!=7) {
                         if(j==7)
                            danger[i-1][j-1]=1;
                         else if(j==0)
                            danger[i-1][j+1]=1;
                         else {
                            danger[i-1][j+1]=1;
                            danger[i-1][j-1]=1;
                         }
                      }
                   }

                   else if (board.cells[i][j].piece.getClass().getCanonicalName() == "Piece.Rook") {
                      for(int k=1;k+i<8;k++) {
                         if(board.cells[i+k][j].piece==null)
                            danger[i+k][j]=1;
                      }
                      for(int k=1;i-k>0;k++) {
                         if(board.cells[i-k][j].piece==null)
                            danger[i-k][j]=1;
                      }
                      for(int k=1;k+j<8;k++) {
                         if(board.cells[i][j+k].piece==null)
                            danger[i][j+k]=1;
                      }
                      for(int k=1;j-k>0;k++) {
                         if(board.cells[i][j-k].piece==null)
                            danger[i][j-k]=1;
                      }
                   }

                   else if (board.cells[i][j].piece.getClass().getCanonicalName() == "Piece.Bishop") {
                      for(int k=1;k+i<8&&j+k<8;k++) {
                         if(board.cells[i+k][j+k].piece==null)
                            danger[i+k][j+k]=1;
                      }
                      for(int k=1;i-k<0&&j-k<0;k++) {
                         if(board.cells[i-k][j-k].piece==null)
                            danger[i-k][j-k]=1;
                      }
                      for(int k=1;i-k<0&&j+k<8;k++) {
                         if(board.cells[i-k][j+k].piece==null)
                            danger[i-k][j+k]=1;
                      }
                      for(int k=1;i+k<8&&j-k<0;k++) {
                         if(board.cells[i+k][j-k].piece==null)
                            danger[i+k][j-k]=1;
                      }
                   }

                   else if (board.cells[i][j].piece.getClass().getCanonicalName() == "Piece.Knight") {
                      danger[i+2][j-1]=1;
                      danger[i-2][j-1]=1;
                   }

                   else {

                   }
                }


             }
             else {//white turn

             }
          }
       }/////////////////////////////////////////////////////////

       for(int i=0;i<8;i++) {
          for(int j=0;j<8;j++) {
             if(turn%2==0) {//black turn
                if (board.cells[i][j].piece.team == 1) {//white piece
                   danger[i][j]=0;
                }
             }
             else
                if(board.cells[i][j].piece.team == 0)
                   danger[i][j]=0;
          }
       }
    }
    */
	boolean isCheck() {
	    Position king = PositionofKing();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board.cells[i][j].piece != null) {
                    if(isValidMove(board.cells[i][j].position, king)) {
                        return true;
                    }
                }
            }
        }

		return false;
	}

	ChessBoard tempboard;

    boolean isCheckmate() {// TODO : 泥댄겕硫붿씠�듃 �긽�솴�씤吏� �뙋�떒�빐二쇰뒗 硫붿꽌�뱶
//        Position king = PositionofKing();
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (isValidMove(king, board.cells[i][j].position)) {
//                    if (!isCheck(board.cells[i][j].position)) {
//                        return false;
//                    }
//                }
//            }
//        } // 기본룰적용시의 체크메이트
//        tempboard = board;
//        for (int a = 0; a < 8; a++) {
//            for (int b = 0; b < 8; b++) {
//                for (int c = 0; c < 8; c++) {
//                    for (int d = 0; d < 8; d++) {
//                        if (tempboard.cells[a][b].piece.team == turn % 2) {
//                            if (isValidMove(tempboard.cells[a][b].position, tempboard.cells[c][d].position)) {
//                                tempboard.cells[c][d] = tempboard.cells[a][b];
//                                tempboard.cells[a][b] = null;
//                                for (int i = 0; i < 8; i++) {
//                                    for (int j = 0; j < 8; j++) {
//                                        if (isValidMove(king, board.cells[i][j].position)) {
//                                            if (!isCheck(board.cells[i][j].position)) {
//                                                return false;
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } //
//        return true;
        return false;
    }

    boolean isStalemate() {//TODO : 스테일메이트 상황인지 판단해주는 메서드
        return false;//개발되기 전까지는 항상 false를 리턴하게끔 만들어주세요
    }
}