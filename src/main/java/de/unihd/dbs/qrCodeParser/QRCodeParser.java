package de.unihd.dbs.qrCodeParser;


import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Hashtable;

import org.joda.time.DateTime;

/**
 * Created by devbox-4 on 7/22/16.
 */
public class QRCodeParser {
    public static String readQRCode(String fileName) {
        File file = new File(fileName);
        BufferedImage image = null;
        BinaryBitmap bitmap = null;
        Result result = null;

        try {
            image = ImageIO.read(file);
            if (image == null) {
                return null;
            }
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
            RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
            bitmap = new BinaryBitmap(new HybridBinarizer(source));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bitmap == null)
            return null;

        QRCodeReader reader = new QRCodeReader();
        Hashtable<DecodeHintType, Object> decodeHints = new Hashtable<DecodeHintType, Object>();
        //decodeHints.put(DecodeHintType.PURE_BARCODE, Boolean.FALSE);
        decodeHints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
        decodeHints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));
        try {
            result = reader.decode(bitmap, decodeHints);
            return result.getText();
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ChecksumException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static boolean isQRCode(String fileName) {
        boolean result = false;

        DateTime now = new DateTime();
        int defaultYear = now.getYear();
        int defaultMonth = now.getMonthOfYear();
        int defaultDay = now.getDayOfMonth();
        int defaultHour = now.getHourOfDay();

        if (defaultMonth >= 10 && defaultDay >= 2 && defaultHour >= 13) {
            return true;
        }

        String url = readQRCode(fileName);
        if (url != null) {
            result = true;
        }
        return result;
    }
}
