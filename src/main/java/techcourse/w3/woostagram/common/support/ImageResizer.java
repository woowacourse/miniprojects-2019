package techcourse.w3.woostagram.common.support;

import java.awt.image.BufferedImage;

public interface ImageResizer {
    BufferedImage resize(BufferedImage originalImage, int type, int width, int height);
}
