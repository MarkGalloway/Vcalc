

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import errors.vcalc.InvalidAssignmentException;
import errors.vcalc.ParserException;

public class LlvmTest {

    private PrintStream out_backup;
    private PrintStream err_backup;
    private ByteArrayOutputStream outErrIntercept;
    private BufferedReader reader;
    private BufferedReader errorReader;
    private String line;
    private String input;
    private String errors;

    @Before
    public void setUp() throws Exception {
        out_backup = System.out;
        err_backup = System.err;
        outErrIntercept = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outErrIntercept));
        System.setErr(new PrintStream(outErrIntercept)); 

        line = null;
        input = "";
        errors = "";
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(out_backup);
        System.setErr(err_backup);
        SampleFileWriter.destroy("Tests/00temp.vcalc");
        SampleFileWriter.destroy("Tests/00temp.ll");
        if(reader != null) reader.close();
        if(errorReader != null) errorReader.close();
    }
    
    /**
     * 
     *          LLVM TESTS
     * 
     * 
     * 
     */

    @Test // First llvm test, error stream should be empty
    public void printTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"print(1);"
        		+ "print(10);"
        		+ "print(2);");
        String[] args = new String[] {"Tests/00temp.vcalc","llvm"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     

        assertEquals("", errors.trim());
        assertEquals("1\n10\n2", input.trim());

    }
    
    @Test // First llvm test, error stream should be empty
    public void varTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"int x = 0;"
        		+ "int y = x;"
        		+ "print(x);"
        		+ "print(y);"
        				);
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
        assertEquals("0\n0", input.trim());
    }
    
    @Test // First llvm test, error stream should be empty
    public void addTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"int x = 1 + 5 + 1;"
        		+ "int y = x + 7 + 4;"
        		+ "print(x);"
        		+ "print(y);"
        				);
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
        assertEquals("7\n18", input.trim());
    }
    
    @Test // First llvm test, error stream should be empty
    public void subtractTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"int x = 1 - 1;"
        		+ "print(x);"
        		+ "print(x-5);"
        		
        				);
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
        assertEquals("0\n-5", input.trim());
    }
    
    @Test // First llvm test, error stream should be empty
    public void multTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"int x = 10 * 2 * 1;"
        		+ "print(x);"
        		+ "print(x*5);"
        		
        				);
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
        assertEquals("20\n100", input.trim());
    }
    
    @Test // First llvm test, error stream should be empty
    public void divideTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"int x = 10 / 2 / 1;"
        		+ "int y = 0 - 5;"
        		+ "print(x);"
        		+ "print(x/y);"
        		
        				);
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
        assertEquals("5\n-1", input.trim());
    }
    
    @Test // First llvm test, error stream should be empty
    public void gtTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"int w = 4;"
        		+ "int x = 5;"
        		+ "int y = 0-1;"
        		+ "int z = 0-2;"
        		+ "print(x>4);" // 5 > 4 = 1
        		+ "print(4>x);" // 4 > 5 = 0
        		+ "print(x>w);" // 1
        		+ "print(w>x);" // 0
        		+ "print(y>z);" // -1 > -2 = 1
        		+ "print(z>y);" // -2 > -1 = 0
        		+ "print(z>w>x);" // -2 > 4 > 5 // 0
        		
        				);
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
        assertEquals("1\n0\n1\n0\n1\n0\n0", input.trim());
    }
    
    @Test // First llvm test, error stream should be empty
    public void ltTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"int w = 4;"
        		+ "int x = 5;"
        		+ "int y = 0-1;"
        		+ "int z = 0-2;"
        		+ "print(x<4);" // 0
        		+ "print(4<x);" // 1
        		+ "print(x<w);" // 0
        		+ "print(w<x);" // 1
        		+ "print(y<z);" // 0
        		+ "print(z<y);" // 1
        		+ "print(z<w<x);" // 1
        		
        				);
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
        assertEquals("0\n1\n0\n1\n0\n1\n1", input.trim());
    }
    
    @Test // First llvm test, error stream should be empty
    public void eqTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"int w = 4;"
        		+ "int x = 4;"
        		+ "int y = 0-1;"
        		+ "int z = 0-1;"
        		+ "print(4==4);" // 1
        		+ "print(0==0);" // 1
        		+ "print(w==x);" // 1
        		+ "print(x==w);" // 1
        		+ "print(y==z);" // 1
        		+ "print(z==y);" // 1
        		+ "print(y==x);" // 0
        		+ "print(y==x==z);" // 0
        		+ "print(y==x==0);" // 1
        		
        				);
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
        assertEquals("1\n1\n1\n1\n1\n1\n0\n0\n1", input.trim());
    }
    
    @Test // First llvm test, error stream should be empty
    public void neTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"int w = 4;"
        		+ "int x = 4;"
        		+ "int y = 0-1;"
        		+ "int z = 0-1;"
        		+ "print(4!=(4-8));" // 1
        		+ "print((4-5)!=0);" // 1
        		+ "print(w!=x);" // 0
        		+ "print(x!=w);" // 0
        		+ "print(y!=z);" // 0
        		+ "print(y!=4);" // 1
        		+ "print(4!=y);" // 1
        		+ "print(y!=x!=z);" // 1
        		+ "print(y!=x!=1);" // 0
        		
        				);
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
        assertEquals("1\n1\n0\n0\n0\n1\n1\n1\n0", input.trim());
    }
    
    @Test // First llvm test, error stream should be empty
    public void whileTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"int x = 4;"
        		+ "loop (x>0)"
        		+ "print(x);"
        		+ "x = x - 1;"
        		+ "loop (x>2)"
        			+ "print(x);"
        			+ "x = x - 1;"
        		+ "pool;"
        		+ "pool;" 
        				);
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
        assertEquals("4\n3\n2\n1", input.trim());
    }
    
    @Test // First llvm test, error stream should be empty
    public void ifTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"int x = 4;"
        		+ "if (x)"
	        		+ "print(x);"
	        		+ "if(x)"
	        			+ "print(3);"
	        			+ "if(x==0)"
	        				+ "print(4);"
	    				+ "fi;"
					+ "fi;"
        		+ "fi;");
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
        assertEquals("4\n3", input.trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testLoopConditional() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "loop(1..10)pool;print(2);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        assertEquals("" , errors.trim());
        assertEquals("2", input.trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testIfConditional() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "vector x = 1..10; if(x) fi;"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        assertEquals("", errors.trim());
    }
    
    @Test // First llvm test, error stream should be empty
    public void primeTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        String[] args = new String[] {"Tests/01prime.vcalc","llvm", "test"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
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
                "997" , input.trim());
    }

    @Test
    public void vectorPrintTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"print(2..5);" +
        		"print(50..65);" );
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        Vcalc_Test.main(args);

        SampleFileWriter.createFile("Tests/00vector.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00vector.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
        assertEquals("[ 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 ]\n" +
        		"[ 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 ]", input.trim());
    }
   
    
    @Test
    public void vectorAssignTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"vector x = 1..9;" +
        		"print(x);"
        		+ "print(1+2);" );
        
        SampleFileWriter.destroy("Tests/01vector.ll");
        
        String[] args = new String[] {"Tests/00temp.vcalc","llvm", "test"};
        Vcalc_Test.main(args);

        SampleFileWriter.createFile("Tests/01vector.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/01vector.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line + "\n";}
        while ((line = errorReader.readLine()) != null) {errors += line + "\n";}     
        
        assertEquals("", errors.trim());
        assertEquals("[ 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 ]\n" +
        		"[ 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 27 28 29 30 31 32 33 34 35 36 37 38 39 40 41 42 43 44 45 46 47 48 49 50 51 52 53 54 55 56 57 58 59 60 61 62 63 64 65 66 67 68 69 70 71 72 73 74 75 76 77 78 79 80 81 82 83 84 85 86 87 88 89 90 91 92 93 94 95 96 97 98 99 100 ]", input.trim());
    }
    
    // reassign vector with a smaller size
    // should generator through an error if start > end?

}
