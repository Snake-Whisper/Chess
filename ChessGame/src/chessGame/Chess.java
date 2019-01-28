package chessGame;

public class Chess {

	enum Pieces {
		wPawn (1), bPawn (-1), wKnight(2), bKnight(-2), wBishop(3), bBishop(-3), wRook(4), bRook(-4), wQueen(5), bQueen(-5), wKing(6), bKing(-6);
		
		int val;
		
		private Pieces(int val) {
		    this.val = val;
		}
		
		public int getValue() {
	        return val;
	    }
	}

	enum Status {
		EHitKingSchach, EOutOfFields, EUndefined, ENotMoved, NormalMove, HitPiece, EOutOfFieldsOrBlocked,
		EOutOfFieldOrBlockedByOwnPiece, EOutOfFieldOrBlockedByPartnerPiece, KindfOfMagic, Rochade;
	}

	Pieces[][] game = new Pieces[8][8];

	Chess() {

		groundStructure();
		// System.out.println(test());
		System.out.println(move(3, 0, 2, 1));
		// System.out.println();

	}

	void groundStructure() {
		for (int x = 0; x < 8; x++) { // Bauern
			//game[x][1] = Pieces.wPawn;
			//game[x][6] = Pieces.bPawn;
		}

		game[0][0] = Pieces.wRook;
		game[7][0] = Pieces.wRook;
		game[1][0] = Pieces.wKnight;
		game[6][0] = Pieces.wKnight;
		game[2][0] = Pieces.wBishop;
		game[5][0] = Pieces.wBishop;
		game[3][0] = Pieces.wQueen;
		game[4][0] = Pieces.wKing;
		//game[0][5] = Pieces.wKing; // TODO Debeug

		game[0][7] = Pieces.bRook;
		game[7][7] = Pieces.bRook;
		game[1][7] = Pieces.bKnight;
		game[6][7] = Pieces.bKnight;
		game[2][7] = Pieces.bBishop;
		game[5][7] = Pieces.bBishop;
		game[3][7] = Pieces.bQueen;
		game[4][7] = Pieces.bKing;
		//game[3][4] = Pieces.bKing; // TODO Debeug
	}

	void printGame() {
		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				System.out.print(game[x][y] + "|");
			}
			System.out.println();
		}

	}

	Status move(int srcX, int srcY, int dstX, int dstY) {
		if (srcX == dstX && srcY == dstY) {
			return Status.ENotMoved;
		}
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
		default:
			return Status.EUndefined;

		}

	}

	boolean isBlack(Pieces piece) {
		if (piece == null) {
			return false;
		}
		switch (piece) {
		case bRook:
		case bPawn:
		case bQueen:
		case bKing:
		case bKnight:
		case bBishop:
			return true;
		default:
			return false;
		}
	}

	boolean isWhite(Pieces piece) {
		if (piece == null) {
			return false;
		}
		switch (piece) {
		case wRook:
		case wPawn:
		case wQueen:
		case wKing:
		case wKnight:
		case wBishop:
			return true;
		default:
			return false;
		}
	}
//TODO Implement Rochade

	Status chkWRook(int srcX, int srcY, int dstX, int dstY) {
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

	Status chkBRook(int srcX, int srcY, int dstX, int dstY) {
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

	Status chkBBishop(int srcX, int srcY, int dstX, int dstY) {
		if (srcX == dstX || srcY == dstY) { // Fuer die, welche gerne eine Frau haetten...
			return Status.EOutOfFields;
		}

		Pieces p;
		int x = srcX;
		int y = srcY;

		if (srcX < dstX) { // E
			if (srcY < dstY) { // N
				while (++x < 8 && ++y < 8) { // gehe nach NE
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
				while (++x < 8 && --y >= 0) { // gehe nach NW
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
			} else { // W
				while (--x >= 0 && --y >= 0) { // gehe nach SW
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

	Status chkWBishop(int srcX, int srcY, int dstX, int dstY) {
		if (srcX == dstX || srcY == dstY) { // Fuer die, welche gerne eine Frau haetten...
			return Status.EOutOfFields;
		}

		Pieces p;
		int x = srcX;
		int y = srcY;

		if (srcX < dstX) { // N
			if (srcY < dstY) { // E
				while (++x < 8 && ++y < 8) { // gehe nach NE
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
				while (++x < 8 && --y >= 0) { // gehe nach NW
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

		else { // S
			if (srcY < dstY) { // E
				while (--x >= 0 && ++x < 8) { // gehe nach SE
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
				while (--x >= 0 && --y >= 0) { // gehe nach SW
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

	Status chkBKnight(int srcX, int srcY, int dstX, int dstY) {
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

	Status chkWKnight(int srcX, int srcY, int dstX, int dstY) {
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

	Status chkBQueen(int srcX, int srcY, int dstX, int dstY) {
		Pieces p;

		int x = srcX;
		int y = srcY;
		
		//Rook features

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
	//Bishop features
		if (srcX < dstX) { // N
			if (srcY < dstY) { // E
				while (++x < 8 && ++y < 8) { // gehe nach NE
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
			} else { // W
				while (++x < 8 && --y >= 0) { // gehe nach NW
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

		else { // S
			if (srcY < dstY) { // E
				while (--x >= 0 && ++x < 8) { // gehe nach SE
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
			} else { // W
				while (--x >= 0 && --y >= 0) { // gehe nach SW
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
	
	Status chkWQueen(int srcX, int srcY, int dstX, int dstY) {
		Pieces p;

		int x = srcX;
		int y = srcY;
		
		//Rook features

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
	//Bishop features
		if (srcX < dstX) { // N
			if (srcY < dstY) { // E
				while (++x < 8 && ++y < 8) { // gehe nach NE
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
				while (++x < 8 && --y >= 0) { // gehe nach NW
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

		else { // S
			if (srcY < dstY) { // E
				while (--x >= 0 && ++x < 8) { // gehe nach SE TODO: Fix var and comments
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
				while (--x >= 0 && --y >= 0) { // gehe nach SW
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
