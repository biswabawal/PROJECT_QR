import com.oracle.VerSig;
import java.io.*;
class Verify
{
  public static void main(String args[]) throws IOException
  {  
    try
    {
      if(args.length!=3)
	throw new IOException("Too few arguments to complete the process");
      VerSig.verify(args[0],args[1],args[2]);
    }
    catch(IOException e)
    {
      System.out.println(e.toString());
    }
  }
}