package chessGame;

public class Chess {

	public enum Pieces {
		wPawn(1), bPawn(-1), wKnight(2), bKnight(-2), wBishop(3), bBishop(-3), wRook(4), bRook(-4), wQueen(5),
		bQueen(-5), wKing(6), bKing(-6);

		int val;

		private Pieces(int val) {
			this.val = val;
		}

		public int getValue() {
			return val;
		}
	}

	public enum Status {
		EHitKingSchach(-1), EOutOfFields(-2), EUndefined(-3), ENoPieceMoved(-4), EOutOfFieldsOrBlocked(-5),
		EOutOfFieldOrBlockedByOwnPiece(-6), EOutOfFieldOrBlockedByPartnerPiece(-7), EWrongPlayer(-8),
		ESelectAMagicFigure(-9), EKindOfMagicRequired(-10), EKindOfMagicForbidden(-11), EbChess(-12), EwChess(-13),
		NormalMove(1), HitPiece(2), KindfOfMagic(3), Rochade(4), HitPieceEnPassant(5), NoChess(6);

		int val;

		private Status(int val) {
			this.val = val;
		}

		public int getValue() {
			return val;
		}
	}

	private Pieces[][] game = new Pieces[8][8];
	private int[] enWPassant = new int[2];
	private int[] enBPassant = new int[2];
	private int magic; // x coor of magic pawn, just one requiered!!!
	private boolean isWhitePartner = true;
	private Timer wTime;
	private Timer bTime;
	private boolean blocked = false;
	private int[] wKingPos = new int[2];
	private int[] bKingPos = new int[2];
	boolean wKingNotTouched = true;
	boolean bKingNotTouched = true;
	private boolean[] wRookNotTouched = {true, true};
	private boolean[] bRookNotTouched = {true, true};

	public Chess(Pieces[][] game, boolean isWhite, int wTimeLeft, int bTimeLeft) {
		game = this.game;
		this.isWhitePartner = isWhite;
		this.wTime = new Timer(wTimeLeft);
		this.bTime = new Timer(bTimeLeft);
	}

	public Chess(int wTimeLeft, int bTimeLeft) {
		this.wTime = new Timer(wTimeLeft);
		this.bTime = new Timer(bTimeLeft);
	}

	public Chess() {

		this.wTime = new Timer();
		this.bTime = new Timer();

		groundStructure();
		// System.out.println(move(4, 4, 3, 5)); //TODO: DEBUG

	}

	public Status move(int srcX, int srcY, int dstX, int dstY) {
		if (srcX<0 || srcX>7 || srcY<0 || srcY>7 || dstX<0 || dstX>7 || dstY<0 || dstY>7) {
			return Status.EOutOfFields;
		}
		if ((srcX == dstX && srcY == dstY) || game[srcX][srcY] == null) {
			return Status.ENoPieceMoved;
		}
		if (!blocked) {
			if (isWhitePartner) {
				if (isWhite(game[srcX][srcY])) {

					Status mv = chkMove(srcX, srcY, dstX, dstY);
					if (mv.getValue() < 0) { // Error
						return mv;
					}
					if (chkWChess() == Status.EwChess) {
						return Status.EwChess;
					}
					switch (mv) {
					case NormalMove:
						mkMove(srcX, srcY, dstX, dstY);
						isWhitePartner = !isWhitePartner; // TODO: Work Swap?
						wTime.stop();
						bTime.start();
						return mv;
					case KindfOfMagic:
						mkMove(srcX, srcY, dstX, dstY);
						return mv;
					case HitPiece:
						game[dstX][dstY] = null; // adios
						mkMove(srcX, srcY, dstX, dstY);
						isWhitePartner = !isWhitePartner;
						wTime.stop();
						bTime.start();
						return mv;
					case HitPieceEnPassant:
						game[dstX][dstY - 1] = null; // en passant
						mkMove(srcX, srcY, dstX, dstY);
						isWhitePartner = !isWhitePartner;
						wTime.stop();
						bTime.start();
						return mv;
					case Rochade:
						if (srcX < dstX) { // East
							System.out.println("do East Rochade"); // TODO implement rochade
						} else {
							System.out.println("do West Rochade");
						}
						isWhitePartner = !isWhitePartner;
						wTime.stop();
						bTime.start();
						return mv;
					default:
						return Status.EUndefined;
					}

				} else {
					return Status.EWrongPlayer;
				}
			} else { // black partner
				if (isBlack(game[srcX][srcY])) {

					Status mv = chkMove(srcX, srcY, dstX, dstY);
					if (mv.getValue() < 0) { // Error
						return mv;
					}
					if (chkBChess() == Status.EbChess) {
						return Status.EbChess;
					}
					switch (mv) {
					case NormalMove:
						mkMove(srcX, srcY, dstX, dstY);
						isWhitePartner = !isWhitePartner; // TODO: Work Swap?
						bTime.stop();
						wTime.start();						
						return mv;
					case KindfOfMagic:
						mkMove(srcX, srcY, dstX, dstY);
						return mv;
					case HitPiece:
						game[dstX][dstY] = null; // adios
						mkMove(srcX, srcY, dstX, dstY);
						isWhitePartner = !isWhitePartner;
						bTime.stop();
						wTime.start();
						return mv;
					case HitPieceEnPassant:
						game[dstX][dstY + 1] = null; // en passant
						mkMove(srcX, srcY, dstX, dstY);
						isWhitePartner = !isWhitePartner;
						bTime.stop();
						wTime.start();
						return mv;
					case Rochade:
						if (srcX < dstX) { // East
							System.out.println("do East Rochade"); // TODO implement rochade
						} else {
							System.out.println("do West Rochade");
						}
						isWhitePartner = !isWhitePartner;
						bTime.stop();
						wTime.start();
						return mv;
					default:
						return Status.EUndefined;
					}
				} else {
					return Status.EWrongPlayer;
				}
			}
		} else {
			return Status.EKindOfMagicRequired;
		}
	}

	public void printGame() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				System.out.print(game[x][y] + "|");
			}
			System.out.println();
		}

	}

	public Status doMagic(Pieces PieceOfWish) {
		if (!blocked) {
			return Status.EKindOfMagicForbidden;
		} else {
			if (isWhitePartner && PieceOfWish.getValue() > 0 && PieceOfWish != Pieces.wKing) {
				game[this.magic][7] = PieceOfWish;
				this.blocked = false;
				isWhitePartner = !isWhitePartner; // switch
				return Status.NormalMove;
			} else if (!isWhitePartner && PieceOfWish.getValue() < 0 && PieceOfWish != Pieces.bKing) {
				game[this.magic][0] = PieceOfWish;
				this.blocked = false;
				isWhitePartner = !isWhitePartner; // switch
				return Status.NormalMove;
			} else {
				return Status.EKindOfMagicForbidden;
			}
		}
	}

	private void groundStructure() {
		for (int x = 0; x < 8; x++) { // Bauern
			game[x][1] = Pieces.wPawn;
			game[x][6] = Pieces.bPawn;
		}

		game[0][0] = Pieces.wRook;
		game[7][0] = Pieces.wRook;
		game[1][0] = Pieces.wKnight;
		game[6][0] = Pieces.wKnight;
		game[2][0] = Pieces.wBishop;
		game[5][0] = Pieces.wBishop;
		game[3][0] = Pieces.wQueen;
		game[4][0] = Pieces.wKing;
		wKingPos[0] = 4;
		wKingPos[1] = 0;

		game[0][7] = Pieces.bRook;
		game[7][7] = Pieces.bRook;
		game[1][7] = Pieces.bKnight;
		game[6][7] = Pieces.bKnight;
		game[2][7] = Pieces.bBishop;
		game[5][7] = Pieces.bBishop;
		game[3][7] = Pieces.bQueen;
		game[4][7] = Pieces.bKing;
		bKingPos[0] = 4;
		bKingPos[1] = 7;

		// TODO: DEBUG
		System.out.println("DEBUG MODE!!!");
		bKingPos[0] = 4;
		bKingPos[1] = 3;
		game[4][3] = Pieces.bKing; // TODO Debeug
		game[3][2] = Pieces.wPawn;

		System.out.println(chkBChess());
		// game[4][4] = Pieces.wPawn; // TODO Debeug
	}

	private void mkMove(int srcX, int srcY, int dstX, int dstY) {
		game[dstX][dstY] = game[srcX][srcY];
		game[srcX][srcY] = null;
	}

	private Status chkMove(int srcX, int srcY, int dstX, int dstY) {

		switch (game[srcX][srcY]) {
		case wRook:
			return chkWRook(srcX, srcY, dstX, dstY);
		case bRook:
			return chkBRook(srcX, srcY, dstX, dstY);
		case wBishop:
			return chkWBishop(srcX, srcY, dstX, dstY);
		case bBishop:
			return chkBBishop(srcX, srcY, dstX, dstY);
		case wKnight:
			return chkWKnight(srcX, srcY, dstX, dstY);
		case bKnight:
			return chkBKnight(srcX, srcY, dstX, dstY);
		case wQueen:
			return chkWQueen(srcX, srcY, dstX, dstY);
		case bQueen:
			return chkBQueen(srcX, srcY, dstX, dstY);
		case wPawn:
			return chkWPawn(srcX, srcY, dstX, dstY);
		case bPawn:
			return chkBPawn(srcX, srcY, dstX, dstY);
		default:
			return Status.EUndefined;

		}

	}
	
	private Status chkWKing(int srcX, int srcY, int dstX, int dstY) {
		if (Math.abs(srcX - dstX) <= 1 && Math.abs(srcY - dstY) <= 1) { //Range safe
			System.out.println("not implemented");
		}
		return Status.EUndefined;
	}
	
	private Status chkWChess() {
		return chkWChessAtField(wKingPos[0], wKingPos[1]);
	}
	
	private Status chkBChess() {
		return chkBChessAtField(bKingPos[0], bKingPos[1]);
	}
	
	private Status chkBChessAtField(int dstX, int dstY) {
		int x = dstX;
		int y = dstY;

		Pieces figur = null; // necessary, otherwise error: var may not be initialized

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// x-dir

		while (++x < 8 && (figur = game[x][y]) == null)
			continue;
		if (figur == Pieces.wQueen || figur == Pieces.wRook
				|| (figur == Pieces.wKing && Math.abs(x - dstX) == 1)) {
			return Status.EbChess;
		}

		x = dstX;
		while (--x >= 0 && (figur = game[x][y]) == null)
			continue;
		if (figur == Pieces.wQueen || figur == Pieces.wRook
				|| (figur == Pieces.wKing && Math.abs(x - dstX) == 1)) {
			return Status.EbChess;
		}

		// y-dir

		x = dstY;
		while (++y < 8 && (figur = game[x][y]) == null)
			continue;
		if (figur == Pieces.wQueen || figur == Pieces.wRook
				|| (figur == Pieces.wKing && Math.abs(y - dstY) == 1)) {
			return Status.EbChess;
		}
		y = dstY;
		while (--y >= 0 && (figur = game[x][y]) == null)
			continue;
		if (figur == Pieces.wQueen || figur == Pieces.wRook
				|| (figur == Pieces.wKing && Math.abs(y - dstY) == 1)) {
			return Status.EbChess;
		}

		y = dstY;
		// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

		while (++y < 8 && ++x < 8 && (figur = game[x][y]) == null)
			continue; // NE
		if (figur == Pieces.wBishop || figur == Pieces.wQueen
				|| ((figur == Pieces.wKing) && Math.abs(y - dstY) == 1 && Math.abs(x - dstX) == 1)) {
			return Status.EbChess;
		}
		x = dstX;
		y = dstY;

		while (++y < 8 && --x >= 0 && (figur = game[x][y]) == null)
			continue; // NW
		if (figur == Pieces.wBishop || figur == Pieces.wQueen
				|| ((figur == Pieces.wKing) && Math.abs(y - dstY) == 1 && Math.abs(x - dstX) == 1)) {
			return Status.EbChess;
		}
		x = dstX;
		y = dstY;

		while (--y >= 0 && ++x < 8 && (figur = game[x][y]) == null)
			continue; // SE
		if (figur == Pieces.wBishop || figur == Pieces.wQueen || ((figur == Pieces.wKing || figur == Pieces.bPawn)
				&& Math.abs(y - dstY) == 1 && Math.abs(x - dstX) == 1)) {
			return Status.EbChess;
		}
		x = dstX;
		y = dstY;

		while (--y >= 0 && --x >= 0 && (figur = game[x][y]) == null)
			continue; // SW
		if (figur == Pieces.wBishop || figur == Pieces.wQueen || ((figur == Pieces.wKing || figur == Pieces.wPawn)
				&& Math.abs(y - dstY) == 1 && Math.abs(x - dstX) == 1)) {
			return Status.EbChess;
		}
		x = dstX;
		y = dstY;

		if (x + 2 < 8 && y + 1 < 8 && game[x + 2][y + 1] == Pieces.wKnight
				|| x + 2 < 8 && y - 1 >= 0 && game[x + 2][y - 1] == Pieces.wKnight
				|| x - 2 >= 0 && y + 1 < 8 && game[x - 2][y + 1] == Pieces.wKnight
				|| x - 2 >= 0 && y - 1 >= 0 && game[x - 2][y - 1] == Pieces.wKnight
				|| x + 1 < 8 && y + 2 < 8 && game[x + 1][y + 2] == Pieces.wKnight
				|| x + 1 < 8 && y - 2 >= 0 && game[x + 1][y - 2] == Pieces.wKnight
				|| x - 1 >= 0 && y + 2 < 8 && game[x - 1][y + 2] == Pieces.wKnight
				|| x - 1 >= 0 && y - 2 >= 0 && game[x - 1][y - 2] == Pieces.wKnight)
			return Status.EbChess;

		return Status.NoChess;
	}

	private Status chkWChessAtField(int dstX, int dstY) {
		int x = dstX;
		int y = dstY;

		Pieces figur = null; // necessary, otherwise error: var may not be initialized

		// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++

		// x-dir

		while (++x < 8 && (figur = game[x][y]) == null)
			continue;
		if (figur == Pieces.bQueen || figur == Pieces.bRook
				|| (figur == Pieces.bKing && Math.abs(x - dstX) == 1)) {
			return Status.EwChess;
		}

		x = dstX;
		while (--x >= 0 && (figur = game[x][y]) == null)
			continue;
		if (figur == Pieces.bQueen || figur == Pieces.bRook
				|| (figur == Pieces.bKing && Math.abs(x - dstX) == 1)) {
			return Status.EwChess;
		}

		// y-dir

		x = dstX;

		while (++y < 8 && (figur = game[x][y]) == null)
			continue;
		if (figur == Pieces.bQueen || figur == Pieces.bRook
				|| (figur == Pieces.bKing && Math.abs(y - dstY) == 1)) {
			return Status.EwChess;
		}
		y = dstY;
		while (--y >= 0 && (figur = game[x][y]) == null)
			continue;
		if (figur == Pieces.bQueen || figur == Pieces.bRook
				|| (figur == Pieces.bKing && Math.abs(y - dstY) == 1)) {
			return Status.EwChess;
		}

		y = dstY;
		// xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

		while (++y < 8 && ++x < 8 && (figur = game[x][y]) == null)
			continue; // NE
		if (figur == Pieces.bBishop || figur == Pieces.bQueen || ((figur == Pieces.bKing || figur == Pieces.bPawn)
				&& Math.abs(y - dstY) == 1 && Math.abs(x - dstX) == 1)) {
			return Status.EwChess;
		}
		x = dstX;
		y = dstY;

		while (++y < 8 && --x >= 0 && (figur = game[x][y]) == null)
			continue; // NW
		if (figur == Pieces.bBishop || figur == Pieces.bQueen || ((figur == Pieces.bKing || figur == Pieces.bPawn)
				&& Math.abs(y - dstY) == 1 && Math.abs(x - dstX) == 1)) {
			return Status.EwChess;
		}
		x = dstX;
		y = dstY;

		while (--y >= 0 && ++x < 8 && (figur = game[x][y]) == null)
			continue; // SE
		if (figur == Pieces.bBishop || figur == Pieces.bQueen
				|| (figur == Pieces.bKing && Math.abs(y - dstY) == 1 && Math.abs(x - dstX) == 1)) {
			return Status.EwChess;
		}
		x = dstX;
		y = dstY;

		while (--y >= 0 && --x >= 0 && (figur = game[x][y]) == null)
			continue; // SW
		if (figur == Pieces.bBishop || figur == Pieces.bQueen
				|| (figur == Pieces.bKing && Math.abs(y - dstY) == 1 && Math.abs(x - dstX) == 1)) {
			return Status.EwChess;
		}
		x = dstX;
		y = dstY;

		if (x + 2 < 8 && y + 1 < 8 && game[x + 2][y + 1] == Pieces.bKnight
				|| x + 2 < 8 && y - 1 >= 0 && game[x + 2][y - 1] == Pieces.bKnight
				|| x - 2 >= 0 && y + 1 < 8 && game[x - 2][y + 1] == Pieces.bKnight
				|| x - 2 >= 0 && y - 1 >= 0 && game[x - 2][y - 1] == Pieces.bKnight
				|| x + 1 < 8 && y + 2 < 8 && game[x + 1][y + 2] == Pieces.bKnight
				|| x + 1 < 8 && y - 2 >= 0 && game[x + 1][y - 2] == Pieces.bKnight
				|| x - 1 >= 0 && y + 2 < 8 && game[x - 1][y + 2] == Pieces.bKnight
				|| x - 1 >= 0 && y - 2 >= 0 && game[x - 1][y - 2] == Pieces.bKnight)
			return Status.EwChess;

		return Status.NoChess;
	}

	private Status chkWPawn(int srcX, int srcY, int dstX, int dstY) {
		Pieces p = game[dstX][dstY];
		if (srcY + 1 == dstY) { // Ein Zug nach vorne
			if (srcX == dstX) { // kein schlagen
				if (p == null) { // keine Figur vor der Nase
					if (dstY != 7) { // keine Magische Linie
						return Status.NormalMove;
					} else { // Umwandlung
						this.blocked = true; // block game, after conversion has finished
						this.magic = dstX;
						return Status.KindfOfMagic;
					}

				} else { // Figur vor der Nase
					return Status.EOutOfFieldsOrBlocked;
				}
			} else if (srcX + 1 == dstX || srcX - 1 == dstX) { // schlag nach rechts ODER links

				if (p == null) { // en passant?
					if (srcY == 4 && enBPassant[1] == dstY - 1 && enBPassant[0] == dstX) {
						return Status.HitPieceEnPassant; // TODO Don't forget clean up
					}

				} else if (isBlack(p)) {
					if (p != Pieces.bKing) {
						return Status.HitPiece;
					} else {
						return Status.EHitKingSchach;
					}
				} else {
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		} else if (srcY == 1 && dstY == 3 && srcX == dstX) { // Doppelschritt
			if (p == null) {
				return Status.NormalMove;
			} else {
				return Status.EOutOfFieldsOrBlocked;
			}
		}
		return Status.EOutOfFieldOrBlockedByOwnPiece;
	}

	private Status chkBPawn(int srcX, int srcY, int dstX, int dstY) {
		Pieces p = game[dstX][dstY];
		if (srcY - 1 == dstY) { // Ein Zug nach vorne
			if (srcX == dstX) { // kein schlagen
				if (p == null) { // keine Figur vor der Nase
					if (dstY != 0) { // keine Magische Linie
						return Status.NormalMove;
					} else { // Umwandlung
						this.blocked = true;
						this.magic = dstX;
						return Status.KindfOfMagic;
					}

				} else { // Figur vor der Nase
					return Status.EOutOfFieldsOrBlocked;
				}
			} else if (srcX + 1 == dstX || srcX - 1 == dstX) { // schlag nach rechts ODER links

				if (p == null) { // en passant?
					if (srcY == 3 && enWPassant[1] == dstY + 1 && enBPassant[0] == dstX) {
						return Status.HitPieceEnPassant; // TODO Don't forget clean up
					}

				} else if (isWhite(p)) {
					if (p != Pieces.wKing) {
						return Status.HitPiece;
					} else {
						return Status.EHitKingSchach;
					}
				} else {
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		} else if (srcY == 6 && dstY == 4 && srcX == dstX) { // Doppelschritt
			if (p == null) {
				return Status.NormalMove;
			} else {
				return Status.EOutOfFieldsOrBlocked;
			}
		}
		return Status.EOutOfFieldOrBlockedByOwnPiece;
	}

	private boolean isBlack(Pieces piece) {
		if (piece.getValue() < 0) {
			return true;
		}
		return false;
	}

	private boolean isWhite(Pieces piece) {
		if (piece.getValue() > 0) {
			return true;
		}
		return false;
	}
//TODO Implement Rochade

	private Status chkWRook(int srcX, int srcY, int dstX, int dstY) {
		if (srcX != dstX && srcY != dstY) {
			return Status.EOutOfFields;
		}

		Pieces p;

		int x = srcX;
		int y = srcY;

		if (srcY == dstY) { // zug nach rechts oder links
			if (srcX < dstX) { // zug nach rechts
				while (++x < 8) { // gehe nach rechts
					p = game[x][srcY];
					if (p == null) { // wenn leeres Feld, dann
						if (x == dstX) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld rechts weiter
						}
					}
					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (x == dstX) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.bKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die schwarze Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die weise Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			} else { // zug nach links

				x = srcX;

				while (--x >= 0) { // gehe nach links
					p = game[x][srcY];
					if (p == null) { // wenn leeres Feld, dann
						if (x == dstX) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld links weiter
						}
					}
					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (x == dstX) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.bKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die schwarze Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die weise Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		} else { // zug nach oben oder unten
			if (srcY < dstY) { // zug nach oben
				while (++y < 8) { // gehe nach oben
					p = game[srcX][y];
					if (p == null) { // wenn leeres Feld, dann
						if (y == dstY) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld oben weiter
						}
					}
					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (y == dstY) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.bKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die schwarze Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die weise Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}

			}

			// y = scrY;
			else { // zug nach unten

				while (--y >= 0) { // gehe nach unten
					p = game[srcX][y];
					if (p == null) { // wenn leeres Feld, dann
						if (y == dstY) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld unten weiter
						}
					}
					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (y == dstY) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.bKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die schwarze Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die weise Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}

		}

		return Status.EOutOfFieldsOrBlocked;

	}

	private Status chkBRook(int srcX, int srcY, int dstX, int dstY) {
		if (srcX != dstX && srcY != dstY) {
			return Status.EOutOfFields;
		}

		Pieces p;

		int x = srcX;
		int y = srcY;

		if (srcY == dstY) { // zug nach rechts oder links
			if (srcX < dstX) { // zug nach rechts
				while (++x < 8) { // gehe nach rechts
					p = game[x][srcY];
					if (p == null) { // wenn leeres Feld, dann
						if (x == dstX) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld rechts weiter
						}
					}
					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (x == dstX) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.wKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die schwarzene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
			// x = scrX;
			else { // zug nach links

				while (--x >= 0) { // gehe nach links
					p = game[x][srcY];
					if (p == null) { // wenn leeres Feld, dann
						if (x == dstX) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld links weiter
						}
					}
					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (x == dstX) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.wKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die weise Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die schwarze Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		} else { // zug nach oben oder unten
			if (srcY < dstY) { // zug nach oben
				while (++y < 8) { // gehe nach oben
					p = game[srcX][y];
					if (p == null) { // wenn leeres Feld, dann
						if (y == dstY) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld oben weiter
						}
					}
					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (y == dstY) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.wKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die schwarze Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}

				// y = scrY;
			} else { // zug nach unten
				while (--y >= 0) { // gehe nach unten
					p = game[srcX][y];
					if (p == null) { // wenn leeres Feld, dann
						if (y == dstY) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld unten weiter
						}
					}
					if (isWhite(p)) { // wenn weise Figur auf Feld, dann
						if (y == dstY) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.wKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die schwarze Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		}

		return Status.EOutOfFieldsOrBlocked;

	}

	private Status chkBBishop(int srcX, int srcY, int dstX, int dstY) {
		if (srcX == dstX || srcY == dstY) { // Fuer die, welche gerne eine Frau haetten...
			return Status.EOutOfFields;
		}

		Pieces p;
		int x = srcX;
		int y = srcY;

		if (srcX < dstX) { // E
			if (srcY < dstY) { // N
				while (++x < 8 && ++y < 8) { // gehe nach EN
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}

					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.wKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			} else { // S
				while (++x < 8 && --y >= 0) { // gehe nach ES
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}

					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.wKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		}

		else { // W
			if (srcY < dstY) { // WN
				while (--x >= 0 && ++y < 8) { // gehe nach WN TODO Check rumgepfuscht
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}
					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.wKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			} else { // S
				while (--x >= 0 && --y >= 0) { // gehe nach WS
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}
					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.wKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		}
		return Status.EOutOfFieldsOrBlocked;

	}

	private Status chkWBishop(int srcX, int srcY, int dstX, int dstY) {
		if (srcX == dstX || srcY == dstY) { // Fuer die, welche gerne eine Frau haetten...
			return Status.EOutOfFields;
		}

		Pieces p;
		int x = srcX;
		int y = srcY;

		if (srcX < dstX) { // E
			if (srcY < dstY) { // N
				while (++x < 8 && ++y < 8) { // gehe nach EN
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}

					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.bKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // schwarze Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			} else { // S
				while (++x < 8 && --y >= 0) { // gehe nach ES
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}

					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.bKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // schwarze Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		}

		else { // W
			if (srcY < dstY) { // N
				while (--x >= 0 && ++y < 8) { // gehe nach WN
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}
					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.bKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // schwarze Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			} else { // W
				while (--x >= 0 && --y >= 0) { // gehe nach WS
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}
					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.bKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // schwarze Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		}
		return Status.EOutOfFieldsOrBlocked;

	}

	private Status chkBKnight(int srcX, int srcY, int dstX, int dstY) {
		int x = Math.abs(srcX - dstX);
		int y = Math.abs(srcY - dstY);

		if ((x == 2 && y == 1) || (x == 1 && y == 2)) {
			Pieces p = game[dstX][dstY];
			if (p == null) {
				return Status.NormalMove;
			}
			if (isWhite(p)) {
				if (p != Pieces.wKing) {
					return Status.HitPiece;
				} else {
					return Status.EHitKingSchach;
				}
			}

			if (isBlack(p)) {
				return Status.EOutOfFieldOrBlockedByOwnPiece;
			}
		}

		return Status.EOutOfFields;
	}

	private Status chkWKnight(int srcX, int srcY, int dstX, int dstY) {
		int x = Math.abs(srcX - dstX);
		int y = Math.abs(srcY - dstY);

		if ((x == 2 && y == 1) || (x == 1 && y == 2)) {
			Pieces p = game[dstX][dstY];
			if (p == null) {
				return Status.NormalMove;
			}
			if (isBlack(p)) {
				if (p != Pieces.bKing) {
					return Status.HitPiece;
				} else {
					return Status.EHitKingSchach;
				}
			}

			if (isWhite(p)) {
				return Status.EOutOfFieldOrBlockedByOwnPiece;
			}
		}

		return Status.EOutOfFields;
	}

	private Status chkBQueen(int srcX, int srcY, int dstX, int dstY) {
		Pieces p;

		int x = srcX;
		int y = srcY;

		// Rook features

		if (srcY == dstY) { // zug nach rechts oder links
			if (srcX < dstX) { // zug nach rechts
				while (++x < 8) { // gehe nach rechts
					p = game[x][srcY];
					if (p == null) { // wenn leeres Feld, dann
						if (x == dstX) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld rechts weiter
						}
					}
					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (x == dstX) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.wKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die schwarzene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
			// x = scrX;
			else { // zug nach links

				while (--x >= 0) { // gehe nach links
					p = game[x][srcY];
					if (p == null) { // wenn leeres Feld, dann
						if (x == dstX) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld links weiter
						}
					}
					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (x == dstX) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.wKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die weise Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die schwarze Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		} else if (srcX == dstX) { // zug nach oben oder unten
			if (srcY < dstY) { // zug nach oben
				while (++y < 8) { // gehe nach oben
					p = game[srcX][y];
					if (p == null) { // wenn leeres Feld, dann
						if (y == dstY) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld oben weiter
						}
					}
					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (y == dstY) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.wKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die schwarze Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}

				// y = scrY;
			} else { // zug nach unten
				while (--y >= 0) { // gehe nach unten
					p = game[srcX][y];
					if (p == null) { // wenn leeres Feld, dann
						if (y == dstY) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld unten weiter
						}
					}
					if (isWhite(p)) { // wenn weise Figur auf Feld, dann
						if (y == dstY) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.wKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die schwarze Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		}
		// Bishop features
		if (srcX < dstX) { // E
			if (srcY < dstY) { // N
				while (++x < 8 && ++y < 8) { // gehe nach EN
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}

					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.wKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			} else { // S
				while (++x < 8 && --y >= 0) { // gehe nach ES
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}

					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.wKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		}

		else { // W
			if (srcY < dstY) { // N
				while (--x >= 0 && ++y < 8) { // gehe nach WN
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}
					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.wKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			} else { // S
				while (--x >= 0 && --y >= 0) { // gehe nach WS
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}
					if (isWhite(p)) { // wenn weisse Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.wKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		}
		return Status.EOutOfFieldsOrBlocked;
	}

	private Status chkWQueen(int srcX, int srcY, int dstX, int dstY) {
		Pieces p;

		int x = srcX;
		int y = srcY;

		// Rook features

		if (srcY == dstY) { // zug nach rechts oder links
			if (srcX < dstX) { // zug nach rechts
				while (++x < 8) { // gehe nach rechts
					p = game[x][srcY];
					if (p == null) { // wenn leeres Feld, dann
						if (x == dstX) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld rechts weiter
						}
					}
					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (x == dstX) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.bKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die schwarzene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
			// x = scrX;
			else { // zug nach links

				while (--x >= 0) { // gehe nach links
					p = game[x][srcY];
					if (p == null) { // wenn leeres Feld, dann
						if (x == dstX) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld links weiter
						}
					}
					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (x == dstX) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.bKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die weise Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die schwarze Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		} else if (srcX == dstX) { // zug nach oben oder unten
			if (srcY < dstY) { // zug nach oben
				while (++y < 8) { // gehe nach oben
					p = game[srcX][y];
					if (p == null) { // wenn leeres Feld, dann
						if (y == dstY) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld oben weiter
						}
					}
					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (y == dstY) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.bKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die schwarze Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}

				// y = scrY;
			} else { // zug nach unten
				while (--y >= 0) { // gehe nach unten
					p = game[srcX][y];
					if (p == null) { // wenn leeres Feld, dann
						if (y == dstY) { // pruefe ob ziel -> bestaetigt
							return Status.NormalMove;
						} else { // ansonsten:
							continue; // suche mit naechstem Feld unten weiter
						}
					}
					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (y == dstY) { // pruefe ob ziel, wenn Ziel:
							if (p != Pieces.bKing) { // pruefe ob Gegnerischer Koenig, wenn nicht
								return Status.HitPiece; // dann wird Figur geschlagen
							} else {
								return Status.EHitKingSchach; // ansonsten: unerlaubter Zug: Finger weg von Koenig
							}
						}
						// break; // die weisse Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; // die schwarze Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		}
		// Bishop features
		if (srcX < dstX) { // E
			if (srcY < dstY) { // N
				while (++x < 8 && ++y < 8) { // gehe nach EN
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}

					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.bKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // schwarze Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			} else { // S
				while (++x < 8 && --y >= 0) { // gehe nach ES
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}

					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.bKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // schwarze Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		}

		else { // W
			if (srcY < dstY) { // N
				while (--x >= 0 && ++y < 8) { // gehe nach WN TODO: Fix var and comments
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}
					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.bKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // schwarze Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			} else { // S
				while (--x >= 0 && --y >= 0) { // gehe nach WS
					p = game[x][y];
					if (p == null) { // leeres Feld?
						if (x == dstX && y == dstY) { // Ziel?
							return Status.NormalMove;
						} else {
							continue; // naechstes Feld
						}
					}
					if (isBlack(p)) { // wenn schwarze Figur auf Feld, dann
						if (x == dstX && y == dstY) { // Ziel?
							if (p != Pieces.bKing) { // Koenig?
								return Status.HitPiece;
							} else {
								return Status.EHitKingSchach; // NO DeathForKing == Schach
							}
						}
						// break; // schwarze Figur sperrt
						return Status.EOutOfFieldOrBlockedByPartnerPiece;
					}
					// break; //eigene Figur sperrt
					return Status.EOutOfFieldOrBlockedByOwnPiece;
				}
			}
		}
		return Status.EOutOfFieldsOrBlocked;
	}
}
