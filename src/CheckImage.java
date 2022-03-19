import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.io.*;

import static java.lang.Math.pow;

public class CheckImage {
   // private BufferedImage ImageYCbCr;

    public CheckImage() throws IOException {
    }

    public void ReadHeader(File nameFile) throws IOException {
        FileInputStream fis=new FileInputStream(nameFile);
        BufferedInputStream bis=new BufferedInputStream(fis);
        byte[] tmp = new byte[54];// HEADER
        bis.read(tmp);


        ByteArrayInputStream byteStream1 = new ByteArrayInputStream(tmp);
        int b;
        System.out.print("HEADER: ");
        while((b=byteStream1.read())!=-1){

            System.out.print(b + " ");
        }
        System.out.println();

        try(FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\zarin\\IdeaProjects\\BMPImage\\test2.txt")) {
            fileOutputStream.write(tmp);
        } catch (FileNotFoundException e) {
            // exception handling
        } catch (IOException e) {
            // exception handling
        }
    }

    public void NewBlueImage(BufferedImage image) throws IOException {

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                // Получаем цвет текущего пикселя
                Color color = new Color(image.getRGB(x, y));

                // Получаем каналы этого цвета
                int blue = color.getBlue();

                Color newColor = new Color(0, 0, blue);

                // И устанавливаем этот цвет в текущий пиксель результирующего изображения
                result.setRGB(x, y, newColor.getRGB());
            }
        }
        // Созраняем результат в новый файл
        File output = new File("blueImg.bmp");
        ImageIO.write(result, "bmp", output);
    }

    public void NewGreenImage(BufferedImage image) throws IOException {
        // Создаем новое пустое изображение, такого же размера
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                Color color = new Color(image.getRGB(x, y));
                int green = color.getGreen();
                Color newColor = new Color(0, green, 0);

                result.setRGB(x, y, newColor.getRGB());
            }
        }

        File output = new File("greenImg.bmp");
        ImageIO.write(result, "bmp", output);
    }

    public void NewRedImage(BufferedImage image) throws IOException {

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {


                Color color = new Color(image.getRGB(x, y));
                int red = color.getRed();
                Color newColor = new Color(red, 0, 0);
                result.setRGB(x, y, newColor.getRGB());
            }
        }

        File output = new File("redImg.bmp");
        ImageIO.write(result, "bmp", output);
    }

    public double MathO(int valColor, BufferedImage image) throws IOException {


        int sum = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                Color color = new Color(image.getRGB(x, y));
                switch (valColor) {
                    case 0:// REd
                        sum += color.getRed();
                        break;
                    case 1://Green
                        sum += color.getGreen();
                        break;
                    case 2:
                        sum += color.getBlue();

                }

            }

        }
        return ((double) sum / (image.getWidth() * image.getHeight()));


    }

    public double Sigma(int valColor, double math, BufferedImage image) throws IOException {

        int sigma = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                Color color = new Color(image.getRGB(x, y));
                switch (valColor) {
                    case 0:// REd
                        sigma +=(pow( (color.getRed() - math) , 2));
                        break;
                    case 1://Green
                        sigma +=(pow( (color.getGreen() - math) , 2));
                        break;
                    case 2:
                        sigma += (pow( (color.getBlue() - math) , 2));

                }

            }

        }
        return Math.sqrt(sigma/(image.getWidth() * image.getHeight() - 1));

    }

    public double test(int valColor, double math, int valColor2, double math2, double sigma, double sigma2,BufferedImage image){

            double array[][] = new double[image.getWidth()][image.getHeight()];
            double array2[][] = new double[image.getWidth()][image.getHeight()];
            double result[][] = new double[image.getWidth()][image.getHeight()];
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {

                    Color color = new Color(image.getRGB(x, y));
                    switch (valColor) {
                        case 0:// REd
                            array[x][y] = color.getRed() - math;
                            break;
                        case 1://Green
                            array[x][y] = color.getGreen() - math;

                            break;
                        case 2:
                            array[x][y] = color.getBlue() - math;
                            break;
                    }

                    switch (valColor2) {
                        case 0:// REd
                            array2[x][y] = color.getRed() - math2;
                            break;
                        case 1://Green
                            array2[x][y] = color.getGreen() - math2;

                            break;
                        case 2:
                            array2[x][y] = color.getBlue() - math2;
                            break;
                    }

                }
            }

            double dobResult = 0;

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    result[x][y] = array[x][y] * array2[x][y];
                    dobResult +=result[x][y];
                }
            }

            dobResult = (double) dobResult/(image.getWidth() * image.getHeight());

            return (double) dobResult/(sigma*sigma2);
    }

    public BufferedImage Color_Spatium_ConversionisY(BufferedImage image) throws IOException {
        BufferedImage resultY = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        BufferedImage resultCb = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        BufferedImage resultCr = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        BufferedImage ImageYCbCr = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                // Получаем цвет текущего пикселя
                Color color = new Color(image.getRGB(x, y));

                 int Y =Sat((int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.144 * color.getBlue()));
                 int Cb =Sat ((int) (0.5643 * (color.getBlue() - Y) + 128));
                 int Cr =Sat ((int)( 0.7132 * (color.getRed() - Y) + 128));

                 Color newColorY = new Color(Y,Y,Y);
                 Color newColorCb = new Color(Cb,Cb,Cb);
                 Color newColorCr = new Color(Cr,Cr,Cr);
                 Color newColorYCbCr = new Color(Y,Cb,Cr);

                // И устанавливаем этот цвет в текущий пиксель результирующего изображения
                resultY.setRGB(x, y, newColorY.getRGB());
                resultCb.setRGB(x, y, newColorCb.getRGB());
                resultCr.setRGB(x, y, newColorCr.getRGB());
                ImageYCbCr.setRGB(x, y, newColorYCbCr.getRGB());
            }
        }
        // Созраняем результат в новый файл
        File output = new File("CPCImgY.bmp");
        ImageIO.write(resultY, "bmp", output);

        File output1 = new File("CPCImgCb.bmp");
        ImageIO.write(resultCb, "bmp", output1);

        File output2 = new File("CPCImgCr.bmp");
        ImageIO.write(resultCr, "bmp", output2);

        File output3 = new File("CPCImgYCbCr.bmp");
        ImageIO.write(ImageYCbCr, "bmp", output3);

        return ImageYCbCr;
    }
    private int Sat(int X){
        if(X < 0) return  0;
        else if(X>255) return 255;
        else return X;

    }

    public BufferedImage Other_Color_Spatium_Conversionis(BufferedImage image) throws IOException {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        BufferedImage ImageYCbCr = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                // Получаем цвет текущего пикселя
                Color color = new Color(image.getRGB(x, y));

                int green =Sat( (int)(color.getRed() - 0.714*(color.getBlue() - 128) - 0.334* (color.getGreen() - 128)));
                int red = Sat((int)(color.getRed() + 1.402* ( color.getBlue()- 128)));
                int blue =Sat( (int)(color.getRed() + 1.772* (color.getGreen() - 128)));

                Color newColorYCbCr = new Color(red,green,blue);

                // И устанавливаем этот цвет в текущий пиксель результирующего изображения

                ImageYCbCr.setRGB(x, y, newColorYCbCr.getRGB());
            }
        }
        // Созраняем результат в новый файл
//        File output = new File("OTHERCPCImg.bmp");
//        ImageIO.write(ImageYCbCr, "bmp", output);

        return ImageYCbCr;
    }

    public void PSNR(BufferedImage image1 , BufferedImage image2) throws IOException {
        double tmp =  ((image1.getHeight() * image1.getWidth() * (pow ((pow(2,24) - 1), 2))));
        double SUMRed = Sum( 0,  image1,   image2);
        double SUMGreen = Sum( 1,  image1,   image2);
        double SUMBlue = Sum( 2,  image1,   image2);

        double PSNRred = 10*Math.log10((double) tmp/SUMRed);
        System.out.println("PSNR R: " + PSNRred);

        double PSNRgreen= 10* Math.log10((double) tmp/SUMGreen);
        System.out.println("PSNR G: " + PSNRgreen);

        double PSNRblue=10* Math.log10((double) tmp/SUMBlue);
        System.out.println("PSNR B:" + PSNRblue);

    }
    public double Sum(int valColor, BufferedImage image,  BufferedImage image2) throws IOException {

        int sum = 0;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                Color color = new Color(image.getRGB(x, y));
                Color color2 = new Color(image2.getRGB(x, y));
                switch (valColor) {
                    case 0:// REd
                        sum +=(pow( (color.getRed() - color2.getRed()) , 2));
                        break;
                    case 1://Green
                        sum +=(pow( (color.getGreen() - color2.getGreen()) , 2));
                        break;
                    case 2:
                        sum += (pow( (color.getBlue() - color2.getBlue()) , 2));

                }

            }

        }
        return sum;
    }


    public void Determinatio(BufferedImage image) throws IOException {

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 1; x < image.getWidth(); x++) {
            for (int y = 1; y < image.getHeight(); y++) {

                Color color = new Color(image.getRGB(x, y));
                if((x%2 == 1 && y%2 == 1)) {
                    int pix1 = new Color(image.getRGB(x - 1, y - 1)).getGreen();
                    int pix2 = new Color(image.getRGB(x, y - 1)).getGreen();
                    int pix3 = new Color(image.getRGB(x - 1, y)).getGreen();
                    int pix4 = new Color(image.getRGB(x, y)).getGreen();
                    int tmpResCb = (pix1 + pix2 + pix3 + pix4) / 4;

                    int pixCr1 = new Color(image.getRGB(x - 1, y - 1)).getBlue();
                    int pixCr2 = new Color(image.getRGB(x, y - 1)).getBlue();
                    int pixCr3 = new Color(image.getRGB(x - 1, y)).getBlue();
                    int pixCr4 = new Color(image.getRGB(x, y)).getBlue();
                    int tmpResCr = (pixCr1 + pixCr2 + pixCr3 + pixCr4) / 4;

                    result.setRGB(x, y, new Color(color.getRed(), tmpResCb, tmpResCr).getRGB());

                }

            }

        }

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                Color color = new Color(image.getRGB(x, y));
                if((x%2 == 0 || y%2 == 0)){
                    result.setRGB(x, y, new Color(color.getRed(),0,0).getRGB());
                }
            }

        }
        File output = new File("DeterminatioCbCr.bmp");
        ImageIO.write(result, "bmp", output);

    }

    public BufferedImage Vicissim_Determinatio(BufferedImage image) throws IOException {// Обратная Детерминация
        File nameFile;

        nameFile = new File("C:\\Users\\zarin\\IdeaProjects\\BMPImage\\DeterminatioCbCr.bmp");
        image = ImageIO.read(nameFile);
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                Color color = new Color(image.getRGB(x, y));

                if(x%2 == 0 || y%2 == 0){
                    if(x%2 == 0 && y%2 == 0){

                        int pix1 = new Color(image.getRGB(x+1, y+1)).getGreen();
                        int pixCr1 = new Color(image.getRGB(x+1, y+1)).getBlue();
                        result.setRGB(x, y,  new Color(color.getRed(),pix1,pixCr1).getRGB());
                    }
                    else if(x%2 ==0){
                        int pix1 = new Color(image.getRGB(x+1, y)).getGreen();
                        int pixCr1 = new Color(image.getRGB(x+1, y)).getBlue();
                        result.setRGB(x, y,  new Color(color.getRed(),pix1,pixCr1).getRGB());
                    }
                    else if(y%2 == 0) {
                        int pix1 = new Color(image.getRGB(x, y+1)).getGreen();
                        int pixCr1 = new Color(image.getRGB(x, y+1)).getBlue();
                        result.setRGB(x, y,  new Color(color.getRed(),pix1,pixCr1).getRGB());
                    }

                }
                else{
                    result.setRGB(x, y, color.getRGB());
                }

            }

        }

//        File output = new File(" VicissimDeterminatioCbCr.bmp");
//        ImageIO.write(result, "bmp", output);

        return result;

    }


    public void Determinatio4(BufferedImage image) throws IOException {

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 3; x < image.getWidth(); x+=4) {
            for (int y = 3; y < image.getHeight(); y+=4) {

                Color color = new Color(image.getRGB(x, y));

                    int pix1 = new Color(image.getRGB(x - 1, y - 1)).getGreen();
                    int pix2 = new Color(image.getRGB(x, y - 1)).getGreen();
                    int pix3 = new Color(image.getRGB(x - 1, y)).getGreen();
                    int pix4 = new Color(image.getRGB(x, y)).getGreen();

                    int pix5 = new Color(image.getRGB(x - 2, y - 2)).getGreen();
                    int pix6 = new Color(image.getRGB(x, y - 2)).getGreen();
                    int pix7 = new Color(image.getRGB(x - 2, y)).getGreen();

                    int pix8 = new Color(image.getRGB(x - 1, y - 2)).getGreen();
                    int pix9 = new Color(image.getRGB(x - 2, y - 1)).getGreen();

                    int pix10 = new Color(image.getRGB(x - 3, y - 3)).getGreen();
                    int pix11= new Color(image.getRGB(x, y - 3)).getGreen();
                    int pix12= new Color(image.getRGB(x - 3, y)).getGreen();

                    int pix13 = new Color(image.getRGB(x - 1, y - 3)).getGreen();
                    int pix14 = new Color(image.getRGB(x - 3, y - 1)).getGreen();

                    int pix15 = new Color(image.getRGB(x - 3, y - 2)).getGreen();
                    int pix16 = new Color(image.getRGB(x - 2, y - 3)).getGreen();


                    int tmpResCb = (pix1 + pix2 + pix3 + pix4+pix5+pix6+pix7 + pix8 + pix9 + pix10 + pix11 + pix12 + pix13 + pix14 + pix15 + pix16) / 16;

                int pixCr1 = new Color(image.getRGB(x - 1, y - 1)).getBlue();
                int pixCr2 = new Color(image.getRGB(x, y - 1)).getBlue();
                int pixCr3 = new Color(image.getRGB(x - 1, y)).getBlue();
                int pixCr4 = new Color(image.getRGB(x, y)).getBlue();

                int pixCr5 = new Color(image.getRGB(x - 2, y - 2)).getBlue();
                int pixCr6 = new Color(image.getRGB(x, y - 2)).getBlue();
                int pixCr7 = new Color(image.getRGB(x - 2, y)).getBlue();

                int pixCr8 = new Color(image.getRGB(x - 1, y - 2)).getBlue();
                int pixCr9 = new Color(image.getRGB(x - 2, y - 1)).getBlue();

                int pixCr10 = new Color(image.getRGB(x - 3, y - 3)).getBlue();
                int pixCr11= new Color(image.getRGB(x, y - 3)).getBlue();
                int pixCr12= new Color(image.getRGB(x - 3, y)).getBlue();

                int pixCr13 = new Color(image.getRGB(x - 1, y - 3)).getBlue();
                int pixCr14 = new Color(image.getRGB(x - 3, y - 1)).getBlue();

                int pixCr15 = new Color(image.getRGB(x - 3, y - 2)).getBlue();
                int pixCr16 = new Color(image.getRGB(x - 2, y - 3)).getBlue();
                int tmpResCr = (pixCr1 + pixCr2 + pixCr3 + pixCr4 + pixCr5 + pixCr6 + pixCr7 + pixCr8 +pixCr9  + pixCr10 + pixCr11 + pixCr12 + pixCr13 + pixCr14 + pixCr15 + pixCr16  ) / 16;

                result.setRGB(x, y, new Color(color.getRed(), tmpResCb, tmpResCr).getRGB());

                result.setRGB(x-3, y-3, new Color(new Color(image.getRGB(x - 3, y - 3)).getRed(),0,0).getRGB());
                result.setRGB(x-2, y-2, new Color(new Color(image.getRGB(x - 2, y - 2)).getRed(),0,0).getRGB());
                result.setRGB(x-1, y-1, new Color(new Color(image.getRGB(x - 1, y - 1)).getRed(),0,0).getRGB());

                result.setRGB(x-3, y-2, new Color(new Color(image.getRGB(x - 3, y - 2)).getRed(),0,0).getRGB());
                result.setRGB(x-3, y-1, new Color(new Color(image.getRGB(x - 3, y - 1)).getRed(),0,0).getRGB());

                result.setRGB(x-2, y-3, new Color(new Color(image.getRGB(x - 2, y - 3)).getRed(),0,0).getRGB());
                result.setRGB(x-2, y-1, new Color(new Color(image.getRGB(x - 2, y - 1)).getRed(),0,0).getRGB());

                result.setRGB(x-1, y-3, new Color(new Color(image.getRGB(x - 1, y - 3)).getRed(),0,0).getRGB());
                result.setRGB(x-1, y-2, new Color(new Color(image.getRGB(x - 1, y - 2)).getRed(),0,0).getRGB());

                result.setRGB(x-1, y, new Color(new Color(image.getRGB(x - 1, y )).getRed(),0,0).getRGB());
                result.setRGB(x-2, y, new Color(new Color(image.getRGB(x - 2, y )).getRed(),0,0).getRGB());
                result.setRGB(x-3, y, new Color(new Color(image.getRGB(x - 3, y )).getRed(),0,0).getRGB());

                result.setRGB(x, y-1, new Color(new Color(image.getRGB(x , y - 1)).getRed(),0,0).getRGB());
                result.setRGB(x, y-2, new Color(new Color(image.getRGB(x , y - 2)).getRed(),0,0).getRGB());
                result.setRGB(x, y-3, new Color(new Color(image.getRGB(x , y - 3)).getRed(),0,0).getRGB());



            }

        }



        File output = new File("DeterminatioCbCrX4.bmp");
        ImageIO.write(result, "bmp", output);

    }


    public BufferedImage Vicissim_Determinatio4(BufferedImage image) throws IOException {// Обратная Детерминация
        File nameFile;

        nameFile = new File("C:\\Users\\zarin\\IdeaProjects\\BMPImage\\DeterminatioCbCrX4.bmp");
        image = ImageIO.read(nameFile);
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                Color color = new Color(image.getRGB(x, y));

                if(x%4 == 3 && y%4 == 3){
                        int pix1 = new Color(image.getRGB(x, y)).getGreen();
                        int pixCr1 = new Color(image.getRGB(x, y)).getBlue();
//
//                        int pix1 = new Color(image.getRGB(x-3, y-3)).getGreen();
//                        int pixCr1 = new Color(image.getRGB(x-3, y-3)).getBlue();
                        result.setRGB(x-3, y-3,  new Color(new Color(image.getRGB(x - 3, y - 3)).getRed(),pix1,pixCr1).getRGB());


//                        int pix2 = new Color(image.getRGB(x-2, y-3)).getGreen();
//                        int pixCr2 = new Color(image.getRGB(x-2, y-3)).getBlue();
                        result.setRGB(x-2, y-3,  new Color(new Color(image.getRGB(x - 2, y - 3)).getRed(),pix1,pixCr1).getRGB());

//                        int pix3 = new Color(image.getRGB(x-1, y-3)).getGreen();
//                        int pixCr3 = new Color(image.getRGB(x-1, y-3)).getBlue();
                        result.setRGB(x-1, y-3,  new Color(new Color(image.getRGB(x - 1, y - 3)).getRed(),pix1,pixCr1).getRGB());

//                        int pix4 = new Color(image.getRGB(x, y-3)).getGreen();
//                        int pixCr4 = new Color(image.getRGB(x, y-3)).getBlue();
                        result.setRGB(x, y-3,  new Color(new Color(image.getRGB(x , y - 3)).getRed(),pix1,pixCr1).getRGB());

//                        int pix5 = new Color(image.getRGB(x, y-2)).getGreen();
//                        int pixCr5 = new Color(image.getRGB(x, y-2)).getBlue();
                        result.setRGB(x, y-2,  new Color(new Color(image.getRGB(x, y - 2)).getRed(),pix1,pixCr1).getRGB());

//                        int pix6 = new Color(image.getRGB(x-1, y-2)).getGreen();
//                        int pixCr6 = new Color(image.getRGB(x-1, y-2)).getBlue();
                        result.setRGB(x-1, y-2,  new Color(new Color(image.getRGB(x - 1, y - 2)).getRed(),pix1,pixCr1).getRGB());

//                        int pix7 = new Color(image.getRGB(x-2, y-2)).getGreen();
//                        int pixCr7 = new Color(image.getRGB(x-2, y-2)).getBlue();
                        result.setRGB(x-2, y-2,  new Color(new Color(image.getRGB(x - 2, y - 2)).getRed(),pix1,pixCr1).getRGB());

//                        int pix8 = new Color(image.getRGB(x-3, y-2)).getGreen();
//                        int pixCr8 = new Color(image.getRGB(x-3, y-2)).getBlue();
                        result.setRGB(x-3, y-2,  new Color(new Color(image.getRGB(x - 3, y - 2)).getRed(),pix1,pixCr1).getRGB());

//                        int pix9 = new Color(image.getRGB(x, y-1)).getGreen();
//                        int pixCr9 = new Color(image.getRGB(x, y-1)).getBlue();
                        result.setRGB(x, y-1,  new Color(new Color(image.getRGB(x , y - 1)).getRed(),pix1,pixCr1).getRGB());

//                        int pix10 = new Color(image.getRGB(x-1, y-1)).getGreen();
//                        int pixCr10 = new Color(image.getRGB(x-1, y-1)).getBlue();
                        result.setRGB(x-1, y-1,  new Color(new Color(image.getRGB(x - 1, y - 1)).getRed(),pix1,pixCr1).getRGB());

//                        int pix11 = new Color(image.getRGB(x-2, y-1)).getGreen();
//                        int pixCr11 = new Color(image.getRGB(x-2, y-1)).getBlue();
                        result.setRGB(x-2, y-1,  new Color(new Color(image.getRGB(x - 2, y - 1)).getRed(),pix1,pixCr1).getRGB());

//                        int pix12 = new Color(image.getRGB(x-3, y-1)).getGreen();
//                        int pixCr12 = new Color(image.getRGB(x-3, y-1)).getBlue();
                        result.setRGB(x-3, y-1,  new Color(new Color(image.getRGB(x - 3, y - 1)).getRed(),pix1,pixCr1).getRGB());

//                        int pix13 = new Color(image.getRGB(x-2, y-1)).getGreen();
//                        int pixCr13 = new Color(image.getRGB(x-2, y-1)).getBlue();
                        result.setRGB(x-1, y,  new Color(new Color(image.getRGB(x - 1, y )).getRed(),pix1,pixCr1).getRGB());

//                        int pix14 = new Color(image.getRGB(x-3, y)).getGreen();
//                        int pixCr14 = new Color(image.getRGB(x-3, y)).getBlue();
                        result.setRGB(x-3, y,  new Color(new Color(image.getRGB(x - 3, y )).getRed(),pix1,pixCr1).getRGB());
//
//                        int pix15 = new Color(image.getRGB(x-2, y)).getGreen();
//                        int pixCr15 = new Color(image.getRGB(x-2, y)).getBlue();
                        result.setRGB(x-2, y,  new Color(new Color(image.getRGB(x - 2, y )).getRed(),pix1,pixCr1).getRGB());


                    result.setRGB(x, y, color.getRGB());

                }
                //else{
//                    result.setRGB(x, y, color.getRGB());
                //}

            }

        }

//        File output = new File(" VicissimDeterminatioCbCr.bmp");
//        ImageIO.write(result, "bmp", output);

        return result;

    }
}
