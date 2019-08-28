package techcourse.w3.woostagram.common.support;

import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ImageResizeUtils {
    private static final double MAX_WIDTH = 1000;
    private static final double MAX_HEIGHT = 1000;

    public static void resizeImage(String fileExtension, File original, File resized) throws IOException {
        BufferedImage originalImage = ImageIO.read(original);
        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
        double width = originalImage.getWidth();
        double height = originalImage.getHeight();
        List<Pair<Double, Double>> sizeCandidates = Arrays.asList(
                Pair.of(width, height),
                Pair.of(MAX_WIDTH, (height) / (width) * MAX_WIDTH),
                Pair.of((width) / (height) * MAX_HEIGHT, MAX_HEIGHT)
        );

        for (Pair<Double, Double> size : sizeCandidates) {
            width = size.getKey();
            height = size.getValue();
            if (validateSize(width, height)) {
                BufferedImage resizeImage = resizeImageWithHint(originalImage, type, (int) width, (int) height);
                ImageIO.write(resizeImage, fileExtension, resized);
                return;
            }
        }
    }

    private static boolean validateSize(double width, double height) {
        return width <= MAX_WIDTH && height <= MAX_HEIGHT;
    }

    private static BufferedImage resizeImageWithHint(BufferedImage originalImage, int type, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        g.setComposite(AlphaComposite.Src);

        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return resizedImage;
    }
}
