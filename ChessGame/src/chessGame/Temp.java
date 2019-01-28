package chessGame;

import chessGame.Chess.Pieces;
import chessGame.Chess.Status;

public class Temp {
	
	Status chkBRook(int srcX, int srcY, int dstX, int dstY) {
		if (srcX != dstX && srcY != dstY) {
			return Status.EOutOfFields;
		}

		Pieces p;

		int x = srcX;
		int y = srcY;

		if (srcY == dstY) { // zug nach rechts oder links
			if (srcX < dstX) { //zug nach rechts
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
						break; // die weisse Figur sperrt
					}
					break; // die schwarzene Figur sperrt
				}
			}
			// x = scrX;
			else { //zug nach links

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
						break; // die weise Figur sperrt
					}
					break; // die schwarze Figur sperrt
				}
			}
		} else { // zug nach oben oder unten
			if (srcY < dstY) { //zug nach oben
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
						break; // die weisse Figur sperrt
					}
					break; // die schwarze Figur sperrt
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
						break; // die weisse Figur sperrt
					}
					break; // die schwarze Figur sperrt
				}
			}
		}

		return Status.EOutOfFieldsOrBlocked;

	}

}
