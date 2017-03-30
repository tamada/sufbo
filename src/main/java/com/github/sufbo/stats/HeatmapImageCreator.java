package com.github.sufbo.stats;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.github.sufbo.stats.entities.Similarity;

public class HeatmapImageCreator implements AutoCloseable{
    private BufferedOutputStream out;
    private Dimension dimension;

    public HeatmapImageCreator(int width, int height) throws IOException{
        this(width, height, "heatmap.ppm");
    }

    public HeatmapImageCreator(int width, int height, String fileName) throws IOException{
        this.dimension = new Dimension(width, height);
        this.out = new BufferedOutputStream(new FileOutputStream(fileName));
        writeHeader();
    }

    public void update(int ignoreIndex, List<Similarity> similarities) throws IOException{
        int offset = 0;
        byte[] data = new byte[(similarities.size() + 1) * 3];
        for(int i = 0; i < similarities.size(); i++){
            if(i == ignoreIndex) offset = 1;
            int rgb = getRGB(similarities.get(i));
            data[(i + offset) * 3    ] = (byte)((rgb >>> 16) & 0xff);
            data[(i + offset) * 3 + 1] = (byte)((rgb >>>  8) & 0xff);
            data[(i + offset) * 3 + 2] = (byte)((rgb >>>  0) & 0xff);
        }
        out.write(data);
    }

    private int getRGB(Similarity similarity){
        float[] hsb = Color.RGBtoHSB(0, 0, 255, new float[3]);
        hsb[0] = hsb[0] * (float)(1 - similarity.value());
        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
    }

    private void writeHeader() throws IOException{
        out.write(new byte[] { 'P', '6', '\n' });
        String size = String.format("%d %d\n255\n", dimension.width, dimension.height);
        out.write(size.getBytes());
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
