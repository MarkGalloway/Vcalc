

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
        reader.close();
        errorReader.close();
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
}
