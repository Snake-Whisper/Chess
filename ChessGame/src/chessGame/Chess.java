package chessGame;

public class Chess {

	enum Pieces {
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

	enum Status {
		EHitKingSchach, EOutOfFields, EUndefined, ENoPieceMoved, NormalMove, HitPiece, EOutOfFieldsOrBlocked,
		EOutOfFieldOrBlockedByOwnPiece, EOutOfFieldOrBlockedByPartnerPiece, KindfOfMagic, Rochade, HitPieceEnPassant;
	}

	Pieces[][] game = new Pieces[8][8];
	int[] enWPassant = new int[2];
	int[] enBPassant = new int[2];
	

	Chess() {
		
		// TODO Debeug
		
		//enBPassant[0] = 3;
		//enBPassant[1] = 4;

		groundStructure();
		// System.out.println(test());
		System.out.println(move(4, 4, 3, 5));
		// System.out.println(Pieces.bKing.getValue());

	}

	void groundStructure() {
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
		// game[0][5] = Pieces.wKing; // TODO Debeug
		// game[3][4] = Pieces.bPawn; // TODO Debeug

		game[0][7] = Pieces.bRook;
		game[7][7] = Pieces.bRook;
		game[1][7] = Pieces.bKnight;
		game[6][7] = Pieces.bKnight;
		game[2][7] = Pieces.bBishop;
		game[5][7] = Pieces.bBishop;
		game[3][7] = Pieces.bQueen;
		game[4][7] = Pieces.bKing;
		// game[3][4] = Pieces.bKing; // TODO Debeug
		// game[4][4] = Pieces.wPawn; // TODO Debeug
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
		if ((srcX == dstX && srcY == dstY) || game[srcX][srcY] == null) {
			return Status.ENoPieceMoved;
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
		case wPawn:
			return chkWPawn(srcX, srcY, dstX, dstY);
		case bPawn:
			return chkBPawn(srcX, srcY, dstX, dstY);
		default:
			return Status.EUndefined;

		}

	}

	Status chkWPawn(int srcX, int srcY, int dstX, int dstY) {
		Pieces p = game[dstX][dstY];
		if (srcY + 1 == dstY) { // Ein Zug nach vorne
			if (srcX == dstX) { // kein schlagen
				if (p == null) { // keine Figur vor der Nase
					if (dstY != 7) { // keine Magische Linie
						return Status.NormalMove;
					} else { // Umwandlung
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
	
	Status chkBPawn(int srcX, int srcY, int dstX, int dstY) {
		Pieces p = game[dstX][dstY];
		if (srcY - 1 == dstY) { // Ein Zug nach vorne
			if (srcX == dstX) { // kein schlagen
				if (p == null) { // keine Figur vor der Nase
					if (dstY != 0) { // keine Magische Linie
						return Status.NormalMove;
					} else { // Umwandlung
						return Status.KindfOfMagic;
					}

				} else { // Figur vor der Nase
					return Status.EOutOfFieldsOrBlocked;
				}
			} else if (srcX + 1 == dstX || srcX - 1 == dstX) { // schlag nach rechts ODER links

				if (p == null) { // en passant?
					if (srcY == 3 && enBPassant[1] == dstY + 1 && enBPassant[0] == dstX) {
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

	boolean isBlack(Pieces piece) {
		if (piece.getValue() < 0) {
			return true;
		}
		return false;
	}

	boolean isWhite(Pieces piece) {
		if (piece.getValue() > 0) {
			return true;
		}
		return false;
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

	Status chkWBishop(int srcX, int srcY, int dstX, int dstY) {
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

	Status chkWQueen(int srcX, int srcY, int dstX, int dstY) {
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
