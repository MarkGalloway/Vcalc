

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InterpreterTest {

    private PrintStream out_backup;
    private PrintStream err_backup;
    private ByteArrayOutputStream outErrIntercept;

    @Before
    public void setUp() throws Exception {
        out_backup = System.out;
        err_backup = System.err;
        outErrIntercept = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outErrIntercept));
        System.setErr(new PrintStream(outErrIntercept));     
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(out_backup);
        System.setErr(err_backup);
        SampleFileWriter.destroy("Tests/00dummytest.vcalc");
        SampleFileWriter.destroy("Tests/00temp.vcalc");
    }

    @Test //Just a dummy test to verify testing output actually works
    public void dummyTest() throws IOException, RecognitionException {
        SampleFileWriter.createFile("Tests/00dummytest.vcalc", "print(256);");
        String[] args = new String[] {"Tests/00dummytest.vcalc","int"};
        
        Vcalc_Test.main(args);
        assertEquals("256", outErrIntercept.toString().trim());
    }
    
    @Test //Test simple addition and assignment
    public void additionTest() throws RecognitionException, IOException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                  "int x = 1 + 1;"
                + "int y = 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1 + 1;"
                + "int z = x + y;"
                + "print(z);");
        String[] args = new String[] {"Tests/00temp.vcalc","int"};
        
        Vcalc_Test.main(args);
        assertEquals("13", outErrIntercept.toString().trim());
    }
    
    @Test
    public void primeTest() throws RecognitionException, IOException {

      String[] args = new String[] {"Tests/01prime.vcalc","int"};
      
      Vcalc_Test.main(args);
      System.out.println();
      assertEquals("2\n" + 
              "3\n" + 
              "5\n" + 
              "7\n" + 
              "11\n" + 
              "13\n" + 
              "17\n" + 
              "19\n" + 
              "23\n" + 
              "29\n" + 
              "31\n" + 
              "37\n" + 
              "41\n" + 
              "43\n" + 
              "47\n" + 
              "53\n" + 
              "59\n" + 
              "61\n" + 
              "67\n" + 
              "71\n" + 
              "73\n" + 
              "79\n" + 
              "83\n" + 
              "89\n" + 
              "97\n" + 
              "101\n" + 
              "103\n" + 
              "107\n" + 
              "109\n" + 
              "113\n" + 
              "127\n" + 
              "131\n" + 
              "137\n" + 
              "139\n" + 
              "149\n" + 
              "151\n" + 
              "157\n" + 
              "163\n" + 
              "167\n" + 
              "173\n" + 
              "179\n" + 
              "181\n" + 
              "191\n" + 
              "193\n" + 
              "197\n" + 
              "199\n" + 
              "211\n" + 
              "223\n" + 
              "227\n" + 
              "229\n" + 
              "233\n" + 
              "239\n" + 
              "241\n" + 
              "251\n" + 
              "257\n" + 
              "263\n" + 
              "269\n" + 
              "271\n" + 
              "277\n" + 
              "281\n" + 
              "283\n" + 
              "293\n" + 
              "307\n" + 
              "311\n" + 
              "313\n" + 
              "317\n" + 
              "331\n" + 
              "337\n" + 
              "347\n" + 
              "349\n" + 
              "353\n" + 
              "359\n" + 
              "367\n" + 
              "373\n" + 
              "379\n" + 
              "383\n" + 
              "389\n" + 
              "397\n" + 
              "401\n" + 
              "409\n" + 
              "419\n" + 
              "421\n" + 
              "431\n" + 
              "433\n" + 
              "439\n" + 
              "443\n" + 
              "449\n" + 
              "457\n" + 
              "461\n" + 
              "463\n" + 
              "467\n" + 
              "479\n" + 
              "487\n" + 
              "491\n" + 
              "499\n" + 
              "503\n" + 
              "509\n" + 
              "521\n" + 
              "523\n" + 
              "541\n" + 
              "547\n" + 
              "557\n" + 
              "563\n" + 
              "569\n" + 
              "571\n" + 
              "577\n" + 
              "587\n" + 
              "593\n" + 
              "599\n" + 
              "601\n" + 
              "607\n" + 
              "613\n" + 
              "617\n" + 
              "619\n" + 
              "631\n" + 
              "641\n" + 
              "643\n" + 
              "647\n" + 
              "653\n" + 
              "659\n" + 
              "661\n" + 
              "673\n" + 
              "677\n" + 
              "683\n" + 
              "691\n" + 
              "701\n" + 
              "709\n" + 
              "719\n" + 
              "727\n" + 
              "733\n" + 
              "739\n" + 
              "743\n" + 
              "751\n" + 
              "757\n" + 
              "761\n" + 
              "769\n" + 
              "773\n" + 
              "787\n" + 
              "797\n" + 
              "809\n" + 
              "811\n" + 
              "821\n" + 
              "823\n" + 
              "827\n" + 
              "829\n" + 
              "839\n" + 
              "853\n" + 
              "857\n" + 
              "859\n" + 
              "863\n" + 
              "877\n" + 
              "881\n" + 
              "883\n" + 
              "887\n" + 
              "907\n" + 
              "911\n" + 
              "919\n" + 
              "929\n" + 
              "937\n" + 
              "941\n" + 
              "947\n" + 
              "953\n" + 
              "967\n" + 
              "971\n" + 
              "977\n" + 
              "983\n" + 
              "991\n" + 
              "997" , outErrIntercept.toString().trim());
    }

}
