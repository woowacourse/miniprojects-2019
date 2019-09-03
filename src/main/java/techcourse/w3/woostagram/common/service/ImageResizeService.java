package techcourse.w3.woostagram.common.service;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import techcourse.w3.woostagram.common.exception.InvalidImageSize;
import techcourse.w3.woostagram.common.support.ImageResizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class ImageResizeService {
    private static final double MAX_WIDTH = 1000;
    private static final double MAX_HEIGHT = 1000;

    private final ImageResizer imageResizer;

    public ImageResizeService(ImageResizer imageResizer) {
        this.imageResizer = imageResizer;
    }

    public void resizeImage(String fileExtension, File fileData, File resized) throws IOException {
        BufferedImage originalImage = getImage(fileData);
        int type = getImageType(originalImage);
        List<Pair<Double, Double>> sizeCandidates = getCandidates(originalImage);

        Pair<Double, Double> selectedSize = selectSize(sizeCandidates);

        BufferedImage resizeImage = imageResizer.resize(originalImage, type, selectedSize.getLeft().intValue(), selectedSize.getRight().intValue());
        ImageIO.write(resizeImage, fileExtension, resized);
    }

    private int getImageType(BufferedImage originalImage) {
        return originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
    }

    private BufferedImage getImage(File fileData) throws IOException {
        FileInputStream original = new FileInputStream(fileData);
        return ImageIO.read(original);
    }

    private List<Pair<Double, Double>> getCandidates(BufferedImage originalImage) {
        double width = originalImage.getWidth();
        double height = originalImage.getHeight();

        return Arrays.asList(
                Pair.of(width, height),
                Pair.of(MAX_WIDTH, (height) / (width) * MAX_WIDTH),
                Pair.of((width) / (height) * MAX_HEIGHT, MAX_HEIGHT)
        );
    }

    private Pair<Double, Double> selectSize(List<Pair<Double, Double>> sizeCandidates) {
        for (Pair<Double, Double> size : sizeCandidates) {
            Double width = size.getKey();
            Double height = size.getValue();

            if (validateSize(width, height)) {
                return Pair.of(width, height);
            }
        }

        throw new InvalidImageSize();
    }

    private boolean validateSize(double width, double height) {
        return width <= MAX_WIDTH && height <= MAX_HEIGHT;
    }
}
