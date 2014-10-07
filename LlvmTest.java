

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
    public void dummyTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException, InterruptedException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", "print(1);");
        String[] args = new String[] {"Tests/00temp.vcalc","llvm"};
        Vcalc_Test.main(args);
        SampleFileWriter.createFile("Tests/00temp.ll", outErrIntercept.toString());

        Process p = Runtime.getRuntime().exec("lli Tests/00temp.ll");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        errorReader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        
        while ((line = reader.readLine()) != null) {input += line;}
        while ((line = errorReader.readLine()) != null) {errors += line;}     

        assertEquals("1", input);
        assertEquals("", errors);
    }
}