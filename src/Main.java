import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

         System.out.println("---------------------buffImage---------------------");
         File nameFile;
         BufferedImage buffImage;BufferedImage ImageYCbCr;
         nameFile = new File("C:\\Users\\zarin\\IdeaProjects\\BMPImage\\src\\kodim08.bmp");
       // nameFile = new File("C:\\Users\\zarin\\IdeaProjects\\BMPImage\\src\\3.bmp");

        buffImage = ImageIO.read(nameFile);
         CheckImage imageCheck = new CheckImage();
         imageCheck.ReadHeader(nameFile);

        imageCheck.NewRedImage(buffImage);
        imageCheck.NewGreenImage(buffImage);
        imageCheck.NewBlueImage(buffImage);

//Мат ожидание
        System.out.println("\n\n---------------------buffImage:MathO---------------------");
        double redEx = imageCheck.MathO(0,buffImage);
        double greenEx = imageCheck.MathO(1, buffImage);
        double blueEx = imageCheck.MathO(2, buffImage);
        System.out.println("expectation red:" + redEx);
        System.out.println("expectation green:" + greenEx);
        System.out.println("expectation blue:" + blueEx);


//Среднеквадратичное отелонение
        System.out.println("\n\n---------------------buffImage:Sigma---------------------");

        double redSigma = imageCheck.Sigma(0,redEx, buffImage);
        double greenSigma = imageCheck.Sigma(1,greenEx, buffImage);
        double blueSigma = imageCheck.Sigma(2,blueEx, buffImage);

        System.out.println("redSigma:" + redSigma);

        System.out.println("greenSigma:" + greenSigma);

        System.out.println("blueSigma:" + blueSigma);

        System.out.println("\n\n---------------------buffImage:test---------------------");

        //Корреляция
        double RredGreen = imageCheck.test(0,redEx,1,greenEx,redSigma,greenSigma,buffImage );
        double RredBlue = imageCheck.test(0,redEx,2,blueEx,redSigma,blueSigma, buffImage);
        double RGreenBlue = imageCheck.test(1,greenEx,2,blueEx,greenSigma,blueSigma, buffImage);

        System.out.println("r:(red and green) " + RredGreen);
        System.out.println("r:(red and blue) " + RredBlue);
        System.out.println("r:(blue and green) " + RGreenBlue);

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("\n\n---------------------ImageYCbCr---------------------");
        ImageYCbCr = imageCheck.Color_Spatium_ConversionisY(buffImage);

        System.out.println("\n\n---------------------ImageYCbCr:MathO---------------------");
        //Мат ожидание

        double YEx = imageCheck.MathO(0,ImageYCbCr);
        double CbEx = imageCheck.MathO(1, ImageYCbCr);
        double CrEx = imageCheck.MathO(2, ImageYCbCr);
        System.out.println("expectation red:" + YEx);
        System.out.println("expectation green:" + CbEx);
        System.out.println("expectation blue:" + CrEx);

        //Среднеквадратичное отелонение
        System.out.println("\n\n---------------------ImageYCbCr:Sigma---------------------");
        double YSigma = imageCheck.Sigma(0,YEx, ImageYCbCr);
        double CbSigma = imageCheck.Sigma(1,CbEx, ImageYCbCr);
        double CrSigma = imageCheck.Sigma(2,CrEx, ImageYCbCr);

        System.out.println("YSigma:" + YSigma);

        System.out.println("CbSigma:" + CbSigma);

        System.out.println("CrSigma:" + CrSigma);

        System.out.println("\n\n---------------------ImageYCbCr:test---------------------");

        //Корреляция
        double RYCb = imageCheck.test(0,YEx,1,CbEx,YSigma,CbSigma,ImageYCbCr );

        double RYCr = imageCheck.test(0,YEx,2,CrEx,YSigma,CrSigma, ImageYCbCr);

        double RCbCr = imageCheck.test(1,CbEx,2,CrEx,CbSigma,CrSigma, ImageYCbCr);

        System.out.println("r:(Y and Cb) " + RYCb);
        System.out.println("r:(Y and Cr) " + RYCr);
        System.out.println("r:(Cb and Cr) " + RCbCr);

        System.out.println("\n\n---------------------buffImage-ImageYCbCr:PSNR---------------------");

        BufferedImage otherImage = imageCheck.Other_Color_Spatium_Conversionis(ImageYCbCr);
        File output = new File("OTHERCPCImg.bmp");
        ImageIO.write(otherImage, "bmp", output);

        imageCheck.PSNR(buffImage,otherImage);

        imageCheck.Determinatio(ImageYCbCr);//Determinatio
        BufferedImage tmpImg = new BufferedImage(ImageYCbCr.getWidth(),ImageYCbCr.getHeight(),ImageYCbCr.getType());
        BufferedImage tmpImgResult = imageCheck.Vicissim_Determinatio(tmpImg);

        File output2 = new File(" VicissimDeterminatioCbCr.bmp");
        ImageIO.write(tmpImgResult, "bmp", output2);

        //imageCheck.Vicissim_Determinatio(tmpImg);

        BufferedImage ConvertImage = imageCheck.Other_Color_Spatium_Conversionis(tmpImgResult);
        File output3 = new File("OTHERVicissim_DetermImage.bmp");
        ImageIO.write(ConvertImage, "bmp", output3);

        System.out.println("\n\n---------------------CPCImgYCbCr- VicissimDeterminatioCbCr:PSNR---------------------");
        imageCheck.PSNR(ImageYCbCr,tmpImgResult);


        System.out.println("\n\n---------------------buffImage- OTHERVicissim_DetermImage:PSNR---------------------");
        imageCheck.PSNR(buffImage,ConvertImage);


        imageCheck.Determinatio4(ImageYCbCr);
        BufferedImage tmpImg2 = new BufferedImage(ImageYCbCr.getWidth(),ImageYCbCr.getHeight(),ImageYCbCr.getType());
        BufferedImage tmpImgResult2 = imageCheck.Vicissim_Determinatio4(tmpImg2);

        File output4 = new File(" VicissimDeterminatioCbCrX4.bmp");
        ImageIO.write(tmpImgResult2, "bmp", output4);

        BufferedImage ConvertImage2 = imageCheck.Other_Color_Spatium_Conversionis(tmpImgResult2);
        File output5 = new File("OTHERVicissim_DetermImageX4.bmp");
        ImageIO.write(ConvertImage2, "bmp", output5);

    }

}
