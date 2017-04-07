package com.github.sufbo.stats;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

public class HeatmapScaler {
    private Dimension dimension;
    private BufferedImage image;

    public HeatmapScaler(int width, int height){
        this.dimension = new Dimension(width, height);
    }

    public RenderedImage createImage(){
        if(image == null)
            image = createImageImpl();
        return image;
    }

    public BufferedImage createImageImpl(){
        BufferedImage image = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_RGB);
        IntStream.range(0, dimension.width)
        .forEach(index -> fill(image, index, createColor(index)));
        return image;
    }

    private int createColor(int index){
        float[] hsb = Color.RGBtoHSB(0, 0, 255, new float[3]);
        hsb[0] = hsb[0] * (float)(1 - (index / 255d));
        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);        
    }

    private void fill(BufferedImage image, int x, int color){
        IntStream.range(0, dimension.height)
        .forEach(y -> image.setRGB(x, y, color));
    }

    public static void main(String[] args) throws IOException{
        HeatmapScaler scaler = new HeatmapScaler(255, 10);
        RenderedImage image = scaler.createImage();
        ImageIO.write(image, "png", new File("scale.png"));
    }
}
