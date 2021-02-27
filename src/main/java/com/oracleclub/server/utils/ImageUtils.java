package com.oracleclub.server.utils;

import com.oracleclub.server.exception.FileOperationException;
import lombok.extern.slf4j.Slf4j;
import net.sf.image4j.codec.ico.ICODecoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @author :RETURN
 * @date :2021/2/27 19:48
 */
@Slf4j
public class ImageUtils {

    public static final String EXTENSION_ICO = "ico";

    public static BufferedImage getImageFromFile(InputStream is, String extension) throws IOException {
        log.debug("当前文件类型是: [{}]", extension);

        if (EXTENSION_ICO.equals(extension)) {
            try {
                return ICODecoder.read(is).get(0);
            } catch (IOException e) {
                throw new FileOperationException("ico 文件已损坏", e);
            }
        } else {
            return ImageIO.read(is);
        }
    }

    public static ImageReader getImageReaderFromFile(InputStream is, String formatName) {
        try {
            Iterator<ImageReader> readerIterator = ImageIO.getImageReadersByFormatName(formatName);
            ImageReader reader = readerIterator.next();
            ImageInputStream stream = ImageIO.createImageInputStream(is);
            ImageIO.getImageReadersByFormatName(formatName);
            reader.setInput(stream, true);
            return reader;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
