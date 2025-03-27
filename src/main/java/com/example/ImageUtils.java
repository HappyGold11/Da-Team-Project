/*
 * Utility class for image operations, particularly cropping a rectangular image to a square.
 */
package com.example;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtils {
    /**
     * Crops the input image to a centered square using the shortest side of the image.
     * takes in an image The input image to be cropped.
     * returns a square BufferedImage cropped from the center of the original image.
     */
    public static BufferedImage cropSquare(Image image) {
        int width = image.getWidth(null); // Get image width
        int height = image.getHeight(null); // Get image height
        int squareSize = Math.min(width, height); // Determine the size of the square (shortest side)
        int x = (width - squareSize) / 2; // X-coordinate to crop from center
        int y = (height - squareSize) / 2; // Y-coordinate to crop from center

        // Convert Image to BufferedImage
        BufferedImage buffered = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = buffered.createGraphics();
        g2d.drawImage(image, 0, 0, null); // Draw original image onto buffered image
        g2d.dispose();

        // Crop to square
        return buffered.getSubimage(x, y, squareSize, squareSize);
    }
}