import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import com.google.zxing.*;
import com.google.zxing.client.j2se.*;
import com.google.zxing.common.*;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mypackage.ImgtoBlackandWhite;
import com.oracle.GenSig;
public class QRCode 
{
	public static void main(String[] args) throws WriterException, IOException, NotFoundException
	{		
		String charset = "UTF-8", file = "QRCode.png", data = args[0];
		int c=is_image(args[0]);
		if(c!=0)
		{	  
		  
		  if(c==1)
		    data = ImgtoBlackandWhite.toBW(args[0]);
		  charset="ISO-8859-1";		  
		}		
		data=read_from_file(data, charset);		
		GenSig.Gen_Sig(args[0]);
		System.out.println("String size= "+data.length()+" bytes");
		Map<EncodeHintType, ErrorCorrectionLevel> hint_map1 = new HashMap<EncodeHintType, ErrorCorrectionLevel>();	
		Map<DecodeHintType, ErrorCorrectionLevel> hint_map2 = new HashMap<DecodeHintType, ErrorCorrectionLevel>();
		hint_map1.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);		
		hint_map2.put(DecodeHintType.TRY_HARDER, ErrorCorrectionLevel.L);
		createQRCode(data, file, hint_map1,500,500);
		System.out.println("QR Code image created successfully!");
		String s=readQRCode(file, hint_map2);	      		     
		write_to_file(s, args[1]);
	}
	public static int is_image(String s)
	{
		String ext=s.substring(s.indexOf('.')+1);
		if(ext.equals("png") || ext.equals("jpg") || ext.equals("bmp"))
		  return 1;
		else if(ext.equals("mid") || ext.equals("zip"))
		  return -1;
		return 0;
	}
	public static String read_from_file(String s, String charset) throws IOException
	{
		FileInputStream fp=new FileInputStream(s);
		int c;
		String data="";
		while((c=fp.read())!=-1)
		  data+=(char)c;
		data = new String(data.getBytes(), charset);
		return data;
	}
	public static void write_to_file(String s, String file) throws IOException
	{
		FileOutputStream fp=new FileOutputStream(file);
		for(int i=0;i<s.length();++i)
		  fp.write(s.charAt(i));
		fp.close();
	}
	public static void createQRCode(String data, String file, Map<EncodeHintType, ErrorCorrectionLevel> hint_map, int qrh, int qrw) throws WriterException, IOException
	{		
		BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, qrw, qrh, hint_map);
		MatrixToImageWriter.writeToFile(matrix, "png",new File(file));
	}	
	public static String readQRCode(String file, Map<DecodeHintType, ErrorCorrectionLevel> hint_map) throws FileNotFoundException, IOException, NotFoundException 
	{		
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(file)))));
		Result qr_result = new MultiFormatReader().decode(bitmap, hint_map);
		return qr_result.getText();		
	}
}
