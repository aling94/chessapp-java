package Chess.Pieces;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.IOException;

/**
 * Static ImageIcons representing pieces. Rather than load a new Image from IO each time, load them only once and
 * reuse them.
 */
public class PieceIcons
{
    public static ImageIcon PAWN_W;
    public static ImageIcon PAWN_B;
    public static ImageIcon ROOK_W;
    public static ImageIcon ROOK_B;
    public static ImageIcon KNIGHT_W;
    public static ImageIcon KNIGHT_B;
    public static ImageIcon BISHOP_W;
    public static ImageIcon BISHOP_B;
    public static ImageIcon EMPR_W;
    public static ImageIcon EMPR_B;
    public static ImageIcon PRNC_W;
    public static ImageIcon PRNC_B;
    public static ImageIcon QUEEN_W;
    public static ImageIcon QUEEN_B;
    public static ImageIcon KING_W;
    public static ImageIcon KING_B;

    /**
     * Static helper function for loading in images from IO.
     * @param path String
     * @return ImageIcon
     * @throws IOException
     */
    private static ImageIcon loadPieceIcon(String path) throws IOException
    {
        return new ImageIcon(ImageIO.read(Piece.class.getResource(path)));
    }

    static
    {
        try
        {
            PAWN_W   = loadPieceIcon("chesspieces/pw.png");
            PAWN_B   = loadPieceIcon("chesspieces/pb.png");
            ROOK_W   = loadPieceIcon("chesspieces/rw.png");
            ROOK_B   = loadPieceIcon("chesspieces/rb.png");
            KNIGHT_W = loadPieceIcon("chesspieces/nw.png");
            KNIGHT_B = loadPieceIcon("chesspieces/nb.png");
            BISHOP_W = loadPieceIcon("chesspieces/bw.png");
            BISHOP_B = loadPieceIcon("chesspieces/bb.png");
            EMPR_W   = loadPieceIcon("chesspieces/ew.png");
            EMPR_B   = loadPieceIcon("chesspieces/eb.png");
            PRNC_W   = loadPieceIcon("chesspieces/cw.png");
            PRNC_B   = loadPieceIcon("chesspieces/cb.png");
            QUEEN_W  = loadPieceIcon("chesspieces/qw.png");
            QUEEN_B  = loadPieceIcon("chesspieces/qb.png");
            KING_W   = loadPieceIcon("chesspieces/kw.png");
            KING_B   = loadPieceIcon("chesspieces/kb.png");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
