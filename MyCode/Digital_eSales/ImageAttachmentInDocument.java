package Digital_eSales;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class ImageAttachmentInDocument {
	/**
	 * @param args
	 * @throws IOException
	 */
	//public static void main(String[] args) throws IOException
	public void CaptureScreenShot() throws IOException
	{

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Calendar cal = Calendar.getInstance();
		String date = dateFormat.format(cal.getTime());

		// Create a document file
		CustomXWPFDocument document = new CustomXWPFDocument();

		// Adding a file
		try {

			// Working addPicture Code below...
			XWPFParagraph paragraphX = document.createParagraph();
			paragraphX.setAlignment(ParagraphAlignment.CENTER);
			String blipId = paragraphX.getDocument().addPictureData(new FileInputStream(new File("C:\\Automated_Execution\\eSales\\Output\\ScreenShots\\ScreenShots.jpg")),Document.PICTURE_TYPE_JPEG);
			System.out.println("4"   +  blipId);
			System.out.println(document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG));
			document.createPicture(blipId,document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG),600, 300);
			System.out.println("5");
					
			// Working addPicture Code below...
			XWPFParagraph paragraphX1 = document.createParagraph();
			paragraphX1.setAlignment(ParagraphAlignment.CENTER);
			String blipId1 = paragraphX1.getDocument().addPictureData(new FileInputStream(new File("C:\\Automated_Execution\\eSales\\Output\\ScreenShots\\ScreenShots1.jpg")),Document.PICTURE_TYPE_JPEG);
			System.out.println("4" + blipId1);
			System.out.println(document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG));
			document.createPicture(blipId1,document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG),600, 300);
			System.out.println("5");
			
			// Working addPicture Code below...
			XWPFParagraph paragraphX2 = document.createParagraph();
			paragraphX2.setAlignment(ParagraphAlignment.CENTER);
			String blipId2 = paragraphX2.getDocument().addPictureData(new FileInputStream(new File("C:\\Automated_Execution\\eSales\\Output\\ScreenShots\\ScreenShots2.jpg")),Document.PICTURE_TYPE_JPEG);
			System.out.println("4" + blipId2);
			System.out.println(document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG));
			document.createPicture(blipId2,document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG),600, 300);
			System.out.println("5");	
			
			// Working addPicture Code below...
			XWPFParagraph paragraphX3 = document.createParagraph();
			paragraphX3.setAlignment(ParagraphAlignment.CENTER);
			String blipId3 = paragraphX3.getDocument().addPictureData(new FileInputStream(new File("C:\\Automated_Execution\\eSales\\Output\\ScreenShots\\ScreenShots3.jpg")),Document.PICTURE_TYPE_JPEG);
			System.out.println("4" + blipId3);
			System.out.println(document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG));
			document.createPicture(blipId3,document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG),600, 300);
			System.out.println("5");
		
		} catch (InvalidFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// insert doc details
		// Create a para -1
		XWPFParagraph paragraphOne = document.createParagraph();
		paragraphOne.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun paragraphOneRunOne = paragraphOne.createRun();
		paragraphOneRunOne.setBold(true);
		paragraphOneRunOne.setFontSize(20);
		paragraphOneRunOne.setFontFamily("Verdana");
		paragraphOneRunOne.setColor("000070");
		paragraphOneRunOne.setText("Status Report");

		// Create a para -2
		XWPFParagraph paragraphTwo = document.createParagraph();
		paragraphTwo.setAlignment(ParagraphAlignment.CENTER);
		XWPFRun paragraphTwoRunOne = paragraphTwo.createRun();
		paragraphTwoRunOne.setFontSize(12);
		paragraphTwoRunOne.setFontFamily("Verdana");
		paragraphTwoRunOne.setColor("000070");
		paragraphTwoRunOne.setText(date);
		paragraphTwoRunOne.addBreak();

		// Create a para -3
		XWPFParagraph paragraphThree = document.createParagraph();
		paragraphThree.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun paragraphThreeRunOne = paragraphThree.createRun();
		paragraphThreeRunOne.setFontSize(14);
		paragraphThreeRunOne.setFontFamily("Verdana");
		paragraphThreeRunOne.setColor("000070");
		paragraphThreeRunOne.setText("5.30 AM PST");
		paragraphThreeRunOne.addBreak();

		// Create a para -4
		XWPFParagraph paragraphFour = document.createParagraph();
		paragraphFour.setAlignment(ParagraphAlignment.LEFT);
		XWPFRun paragraphFourRunOne = paragraphFour.createRun();
		paragraphFourRunOne.setBold(true);
		paragraphFourRunOne.setUnderline(UnderlinePatterns.SINGLE);
		paragraphFourRunOne.setFontSize(10);
		paragraphFourRunOne.setFontFamily("Verdana");
		paragraphFourRunOne.setColor("000070");
		paragraphFourRunOne.setText("ABCD");

		// Insert doc details end
		XWPFParagraph paragraphFive = document.createParagraph();
		paragraphFive.setAlignment(ParagraphAlignment.RIGHT);
		XWPFRun paragraphFiveRunOne = paragraphFive.createRun();
		paragraphFiveRunOne.addBreak();
		paragraphFourRunOne.setBold(true);
		paragraphFourRunOne.setUnderline(UnderlinePatterns.SINGLE);
		paragraphFourRunOne.setFontSize(10);
		paragraphFourRunOne.setFontFamily("Verdana");
		paragraphFourRunOne.setColor("000070");
		paragraphFourRunOne.setText("ABCD00000000000");

		FileOutputStream outStream = null;
		try {
			double x = Math.random();
	//		String fileName = "C:\\Automated_Execution\\eSales\\Output\\ScreenShots\\" + String.valueOf(x) + "Sample.docx";
			String fileName = "C:\\Automated_Execution\\eSales\\Output\\ScreenShots\\Sample.docx";
			outStream = new FileOutputStream(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("First Catch");
			e.printStackTrace();
		}
		try {
			document.write(outStream);
			outStream.close();
		} catch (FileNotFoundException e) {
			System.out.println("Second Catch");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Third Catch");
			e.printStackTrace();
		}
	}
}
