package Piece;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to maintain ChessPiece Images.
 * <p>
 * this class load Sprite atlas and generate BufferedImage for each chess piece
 * 
 * @see ChessPieceSpriteType
 * @see BufferedImage
 * @author LeNerd
 * @since 2018-05-23
 */
public class ChessPieceSprite {
	/**
	 * This represents possible chess pieces.
	 * 
	 * @author LeNerd
	 *
	 */
	public enum ChessPieceSpriteType {
		BLACK_KING(5, 0), BLACK_QUEEN(4, 0), BLACK_ROOK(3, 0), BLACK_KNIGHT(1, 0), BLACK_BISHOP(2, 0), BLACK_PAWN(0, 0),

		WHITE_KING(5, 1), WHITE_QUEEN(4, 1), WHITE_ROOK(3, 1), WHITE_KNIGHT(1, 1), WHITE_BISHOP(2, 1), WHITE_PAWN(0, 1),

		RED_KING(5, 5), RED_QUEEN(4, 5), RED_ROOK(3, 5), RED_KNIGHT(1, 5), RED_BISHOP(2, 5), RED_PAWN(0, 5),

		GREEN_KING(5, 8), GREEN_QUEEN(4, 8), GREEN_ROOK(3, 8), GREEN_KNIGHT(1, 8), GREEN_BISHOP(2, 8), GREEN_PAWN(0, 8);

		int x;
		int y;

		private ChessPieceSpriteType(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public String getKey() {
			return x + "," + y;
		}
	}

	private static ChessPieceSprite instance = new ChessPieceSprite();
	BufferedImage imgAtlas;
	Map<String, BufferedImage> sprites;

	private ChessPieceSprite() {
		try {
			imgAtlas = ImageIO.read(new File("src/Image/sprite2.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sprites = new HashMap<>();
	}

	/**
	 * returns current instance of Piece.ChessPieceSprite
	 * 
	 * @return instance of Piece.ChessPieceSprite
	 */
	public static ChessPieceSprite getInstance() {
		return instance;
	}

	/**
	 * returns BufferedImage for given ChessPieceSpriteType
	 * <p>
	 * this function generates BufferedImage for first time for each
	 * ChessPieceSpriteType. any subsequent call is cached.
	 * 
	 * @param type
	 *            type of chess piece
	 * 
	 * @return BufferedImage for given ChessPieceSpriteType.
	 */
	public BufferedImage getChessPiece(ChessPieceSpriteType type) {
		if (sprites.get(type.getKey()) == null) {
			sprites.put(type.getKey(), getSpriteFromAtlas(type));
		}

		return sprites.get(type.getKey());
	}

	private BufferedImage getSpriteFromAtlas(ChessPieceSpriteType type) {
		int w = imgAtlas.getWidth() / 6;
		int h = imgAtlas.getHeight() / 12;
		BufferedImage img = new BufferedImage(w, h, imgAtlas.getType());

		Graphics g = img.getGraphics();

		g.drawImage(imgAtlas, 0, 0, w, h, type.x * w, type.y * h, type.x * w + w, type.y * h + h, null);

		// g.dispose();

		return img;
	}
}
