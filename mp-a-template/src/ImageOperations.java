import java.awt.*;
import java.awt.image.BufferedImage;

class ImageOperations {

    /**
     * removes the red channel from the colors of the given image.
     * @param img the BufferedImage the method removes the red channel from.
     * @return a new BufferedImage with the red channel removed from the colors.
     */
    static BufferedImage zeroRed(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                Color c = new Color(rgb);
                int newRGB = (c.getAlpha() << 24) | (0 << 16) | (c.getGreen() << 8) | c.getBlue();
                newImg.setRGB(x, y, newRGB);
            }
        }
        return newImg;
    }

    /**
     * converts the given image to grayscale.
     * @param img the BufferedImage being converted to grayscale.
     * @return a new BufferedImage converted to grayscale.
     */
    static BufferedImage grayscale(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage newImg = new BufferedImage(width, height, img.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                Color c = new Color(rgb);
                int gray = (int) (.299 * c.getRed() + .587 * c.getGreen() + .114 * c.getBlue());
                int newRGB = (gray << 16) | (gray << 8) | gray;
                newImg.setRGB(x, y, newRGB);
            }
        }
        return newImg;
    }

    /**
     * inverts the given image's pixel data.
     * @param img the BufferedImage's pixel data being inverted
     * @return a new BufferedImage but the pixel data is inverted.
     */
    static BufferedImage invert(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage newImg = new BufferedImage(width, height, img.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                Color c = new Color(rgb);

                int r = 255 - c.getRed();
                int g = 255 - c.getGreen();
                int b = 255 - c.getBlue();

                int newRGB = (r << 16) | (g << 8) | b;
                newImg.setRGB(x, y, newRGB);
            }
        }
        return newImg;
    }

    /**
     * mirrors the given BufferedImage either vertically or horizontally and returns a new BufferedImage.
     *
     * @param img the BufferedImage being mirrored either vertically or horizontally
     * @param dir the direction the image will be mirrored
     *            either MirrorMenuItem.MirrorDirection.HORIZONTAL or MirrorMenuItem.MirrorDirection.VERTICAL
     * @return a new BufferedImage of the original BufferedImage mirrored either vertically or horizontally.
     */
    static BufferedImage mirror(BufferedImage img, MirrorMenuItem.MirrorDirection dir) {
        int width = img.getWidth();
        int height = img.getHeight();

        if (dir == MirrorMenuItem.MirrorDirection.HORIZONTAL) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width / 2; x++) {
                    int pixel = img.getRGB(x, y);
                    img.setRGB(width - 1 - x, y, pixel);
                }
            }
        } else if(dir == MirrorMenuItem.MirrorDirection.VERTICAL) {
            for (int y = 0; y < height / 2; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = img.getRGB(x, y);
                    img.setRGB(x, height - 1 - y, pixel);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
        return img;
    }

    /**
     * creates a new BufferedImage repeated either side-by-side or top-to-bottom
     * depending on the given argument, n times.
     * @param img the BufferedImage being repeated
     * @param n the amount of times the BufferedImage is repeated
     * @param dir the direction the BufferedImage is repeated
     *            either RepeatMenuItem.RepeatDirection.HORIZONTAL for horizontal repetition
     *            or RepeatMenuItem.RepeatDirection.HORIZONTAL for vertical repetition
     * @return a new BufferedImage with the original BufferedImage repeated either clockwise or counterclockwise n amount of times.
     */
    static BufferedImage repeat(BufferedImage img, int n, RepeatMenuItem.RepeatDirection dir) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage newImg;

        if (dir == RepeatMenuItem.RepeatDirection.HORIZONTAL) {
            newImg = new BufferedImage(width * n, height, img.getType());

            for (int i = 0; i < n; i++) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int pixel = img.getRGB(x, y);
                        newImg.setRGB(i * width + x, y, pixel);
                    }
                }
            }
        } else if (dir == RepeatMenuItem.RepeatDirection.VERTICAL) {
            newImg = new BufferedImage(width, height * n, img.getType());

            for (int i = 0; i < n; i++) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        int pixel = img.getRGB(x, y);
                        newImg.setRGB(x, i * height + y, pixel);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
        return newImg;
    }

    /**
     * rotates the given BufferedImage 90 degrees clockwise or counterclockwise.
     * @param img the BufferedImage being rotated
     * @param dir the direction the BufferedImage is being rotated
     *            either RotateMenuItem.RotateDirection.CLOCKWISE to rotate the BufferedImage clockwise
     *            or RotateMenuItem.RotateDirection.COUNTERCLOCKWISE to rotate the BufferedImage counterclockwise
     * @return a new BufferedImage of the original BufferedImage rotate either clockwise or counterclockwise
     */
    static BufferedImage rotate(BufferedImage img, RotateMenuItem.RotateDirection dir) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage newImg = new BufferedImage(width, height, img.getType());

        if (dir == RotateMenuItem.RotateDirection.CLOCKWISE) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = img.getRGB(x, y);
                    newImg.setRGB(height - 1- y, x, pixel);
                }
            }
        } else if (dir == RotateMenuItem.RotateDirection.COUNTER_CLOCKWISE) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int pixel = img.getRGB(x, y);
                    newImg.setRGB(y, width - 1 - x, pixel);
                }
            }
        }
        return newImg;
    }

    /**
     * Zooms in on the image. The zoom factor increases in multiplicatives of 10% and
     * decreases in multiplicatives of 10%.
     *
     * @param img        the original image to zoom in on. The image cannot be already zoomed in
     *                   or out because then the image will be distorted.
     * @param zoomFactor The factor to zoom in by.
     * @return the zoomed in image.
     */
    static BufferedImage zoom(BufferedImage img, double zoomFactor) {
        int newImageWidth = (int) (img.getWidth() * zoomFactor);
        int newImageHeight = (int) (img.getHeight() * zoomFactor);
        BufferedImage newImg = new BufferedImage(newImageWidth, newImageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = newImg.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(img, 0, 0, newImageWidth, newImageHeight, null);
        g2d.dispose();
        return newImg;
    }
}
