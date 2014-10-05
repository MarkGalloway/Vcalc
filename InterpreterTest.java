

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import errors.vcalc.ParserException;

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
    
    /**
     * 
     *          INTERPRETER TESTS
     * 
     * 
     */

    @Test // Just a dummy test to verify testing output actually works
    public void dummyTest() throws IOException, RecognitionException, ParserException {
        SampleFileWriter.createFile("Tests/00dummytest.vcalc", "print(256);");
        String[] args = new String[] {"Tests/00dummytest.vcalc","int"};
        
        Vcalc_Test.main(args);
        assertEquals("256\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple addition, brackets, and assignment
    public void additionTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                  "int x = 1 + 1;"
                + "int y = 1 + 1 + (1 + 1) + 1 + 1 + (((1))) + 1 + (1 + 1 + 1) + 0;"
                + "int z = x + y;"
                + "print(z);");
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("13\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple assignment
    public void assignmentTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"int i = 1;"
        		+ "i = 2;"
                  + "print(i);");
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("2\n", outErrIntercept.toString());
    }
    
    @Test(expected=RuntimeException.class)
    public void assignmentVectorToInt() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc","vector i = 1;");
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("2\n", outErrIntercept.toString());
    }
    
    @Test(expected=RuntimeException.class)
    public void assignmentIntToVector() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc","int i = 1..10;");
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("2\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple subtraction, brackets and, assignment
    public void subtractionTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                  "int x = 1 - 1;"
                + "int y = 1 - 1 - (1 - 1) - 1 - 1 - (((1))) - 1 - (1 - 1 - 1) - 0;"
                + "int z = x + y;"
                + "print(z);"
                + "x = 2 - 6;"
                + "y = 4 - 7;"
                + "z = y - x;"
                + "print(z);");
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("-3\n" + "1\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple multiplication, brackets, and assignment
    public void multiplicationTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                  "int x = 1 * 1 * 1;"
                + "int y = 10 * 0;"
                + "int z = 6 * 2 - 6 * 2;" // Precedence
                + "print(x);"
                + "print(y);"
                + "print(z);"
                + "x = 2 - 6;"
                + "y = 4 - 7;"
                + "z = x * y;"
                + "print(z);");
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("1\n" + "0\n" + "0\n" + "12\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple multiplication, brackets, and assignment
    public void divisionTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                  "int x = 1 / 1 / 1;"
                + "int y = 100 / 100;"
                + "int z = 9 / 3 - 9 / 3;" // Precedence
                + "print(x);"
                + "print(y);"
                + "print(z);"
                + "x = 2 - 6;"
                + "y = 4 - 7;"
                + "z = x / y;"
                + "print(z);");
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("1\n" + "1\n" + "0\n" + "1\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple comparison
    public void comparisonTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                  "int x = 5;"
                + "int y = 5;"
                + "int z = 0;"
                + "print(x == x);"
                + "print(y == x);"
                + "print(y == z);"
                + "print(x != x);"
                + "print(z != x);"
                + "print(y != x);"
                + "print(x < x);"
                + "print(x < y);"
                + "print(z < y);"
                + "print(x > y);"
                + "print( 1 < 2 > 2 < 1);" // Precedence
                + "print( 1 == 2 != 0 == 0);"); // Precedence 
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("1\n" + "1\n" + "0\n" 
                   + "0\n" + "1\n" + "0\n" 
                   + "0\n" + "0\n" + "1\n"
                   + "0\n" + "1\n" + "1\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple nested ifs
    public void ifTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                  "int x = 2;"
                + "int y = 0;"
                + "int z = 100;"
                + "if ( x != 0)"
                    + "print (x);"
                    + "x = x - 1;"
                    + "if( y == 0)"
                        + "y = y + 1;"
                        + "if(z < 0)"
                            + "print(z);"
                        + "fi;"
                    + "fi;"
                    + "print(y);"
                + "fi;");
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("2\n" + "1\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple nested loops
    public void loopTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                  "int x = 2;"
                + "int y = 0;"
                + "int z = 100;"
                + "loop ( x != 0)"
                    + "print (x);"
                    + "x = x - 1;"
                    + "loop( y < 2)"
                        + "y = y + 1;"
                        + "loop(z > 0)"
                            + "z = z - 5;"
                        + "pool;"
                    + "pool;"
                    + "print(y);"
                + "pool;");
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("2\n" + "2\n" + "1\n" + "2\n", outErrIntercept.toString());
    }
    
    @Test // Vector Print Test
    public void vectorPrintTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"vector v = 1..10;"
        		 + "print(v);");
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 2 3 4 5 6 7 8 9 10 ]", outErrIntercept.toString().trim());
    }
    
    @Test
    public void generatorTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                "int i = 666;" +  //global var should not be seen
                "int j = 1;" +
                "int k = 10;" +
                "vector v = [i in 1..10 | 0];" + 
                "vector s = [i in 1..10 | 2 + 3];" +
                "vector z = [i in j..k | i];" +
                "print(v);" +
                "print(s);" +
                "print(z);"
                );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 0 0 0 0 0 0 0 0 0 0 ]\n" + 
                     "[ 5 5 5 5 5 5 5 5 5 5 ]\n" + 
                     "[ 1 2 3 4 5 6 7 8 9 10 ]" , outErrIntercept.toString().trim());
    }
    
    // Test that we retrieve local scope variables correctly
    @Test
    public void nestedScopeTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                "int i = 666;" +  
                "vector v = [i in [i in 1..10 | 2 * i] | i * i];" +
                "print(v);"
                );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        Vcalc_Test.main(args);
        assertEquals("[ 4 16 36 64 100 144 196 256 324 400 ]" , outErrIntercept.toString().trim());
    }
    
    // Test that we can access global variables if there are no corresponding local variables
    @Test
    public void scopeTestAccessEnclosingScope() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                "int i = 5;" +  
                "vector v = [k in [j in 1..10 | j*i] | k*i];" +
                "print(v);"
                );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 25 50 75 100 125 150 175 200 225 250 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void generalScopeTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "int i = 0;" +
        "int j = 3;" +
        "vector v = [i in 1..3 | j * i * i];" +
        "print(i);" +
        "print(v);"
                );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("0\n[ 3 12 27 ]" , outErrIntercept.toString().trim());
    }
    
    
    // Should have a runtime exception, variable i is not defined in j
    @Test(expected=RuntimeException.class)
    public void scopeTestAccessUndefinedEnclosingScope() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                "vector v = [i in [j in 1..2 | 1 * i] | i];" +
                "print(v);"
                );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
    }
    
    
    @Test
    public void filterTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "vector v = [i in 95..100 | i];" +
        "v = filter(i in v | i > 95);" + 
         "print(v);"
                );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 96 97 98 99 100 ]"
                , outErrIntercept.toString().trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testIndexIntType() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "int i = 2;\n" +
        "print(i[2]);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
    }
    
    @Test
    public void testIndex() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "int i = 2;\n" +
        "vector j = [i in 1..2 | 2 - i];" +
        "print(j);" +
        "print([i in 1..2 | 2 - i][0]);" +
        "print([i in 1..2 | 2 - i][1]);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 0 ]\n1\n0" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testIndexFilter() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "print(filter(i in 1..10 | i > 5)[4]);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("10" , outErrIntercept.toString().trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testIndexOutOfBounds() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "print(filter(i in 1..10 | i > 5)[5]);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("10" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testIndexingWithVectors() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        	"vector v = 1..3;" +
        	"vector j = [i in 1..2 | 2 - i];"+
        	"print(v);" +
        	"print(j);" +
        	"print(v[j]);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 2 3 ]\n[ 1 0 ]\n[ 2 1 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testAddVectors() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        	"vector v = 1..5;" +
        	"vector j = [i in 1..2 | 2 - i];"+
        	"print(v);" +
        	"print(j);" +
        	"print(v + j);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 2 3 4 5 ]\n[ 1 0 ]\n[ 2 2 3 4 5 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testAddVectorToInt() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        	"vector v = 1..5;" +
        	"int j = 5;"+
        	"print(v);" +
        	"print(j);" +
        	"print(v + j);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 2 3 4 5 ]\n5\n[ 6 7 8 9 10 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testAddIntToVector() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        	"vector v = 1..5;" +
        	"int j = 5;"+
        	"print(v);" +
        	"print(j);" +
        	"print(j + v);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 2 3 4 5 ]\n5\n[ 6 7 8 9 10 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testSubtractVectors() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        	"vector v = 1..10;" +
        	"vector j = [i in 1..5 | 6];"+
        	"print(v);" +
        	"print(j);" +
        	"print(v-j);" +
        	"print([i in 1..2 | 0-1] - [i in 1..5 | i]);"
        	+ "print(j);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 2 3 4 5 6 7 8 9 10 ]\n[ 6 6 6 6 6 ]\n[ -5 -4 -3 -2 -1 6 7 8 9 10 ]\n[ -2 -3 -3 -4 -5 ]\n[ 6 6 6 6 6 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testSubtractIntVector() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        	"int v = 1;" +
        	"vector j = [i in 1..5 | 6];"+
        	"print(j-v);" +
        	"print(v-j);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 5 5 5 5 5 ]\n[ -5 -5 -5 -5 -5 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testMultVector() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        	"int v = 2;" +
        	"vector j = [i in 1..3 | i];"+ 
        	"vector p = [i in 1..5 | i];" +
        	"print(j*v);" +
        	"print(v*j);"
        	+ "print(j);"
        	+ "print(j*p);"
        	+ "print(p*j);"
        	+ "print(p);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 2 4 6 ]\n[ 2 4 6 ]\n[ 1 2 3 ]\n[ 1 4 9 0 0 ]\n[ 1 4 9 0 0 ]\n[ 1 2 3 4 5 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testDivideVector() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        	"int v = 2;" +
        	"vector j = [i in 1..3 | i];"+ 
        	"vector p = [i in 1..5 | i];" +
        	"print(j/v);" + // 0 1 1
        	"print(v/j);" // 2 1 0
        	+ "print(j);"
        	+ "print(j/p);" //1 1 1 0 0
        	+ "print(p/j);" //1 1 1 4 5
        	+ "print(p);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 0 1 1 ]\n[ 2 1 0 ]\n[ 1 2 3 ]\n[ 1 1 1 0 0 ]\n[ 1 1 1 4 5 ]\n[ 1 2 3 4 5 ]" , outErrIntercept.toString().trim());
    }

    @Test
    public void equalityVectorTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
            "vector v = 1..3;" + 
            "vector s = 1..3;" +
            "vector x = 1..2;" +
            "vector z = 2..4;" +
            "print(v==s);" +
            "print(v==z);" +
            "print(x==v);" +
            "print(v==x);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("1\n0\n0\n0" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void inequalityVectorTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
            "vector v = 1..3;" + 
            "vector s = 1..3;" +
            "vector x = 1..2;" +
            "vector z = 2..4;" +
            "print(v!=s);" +
            "print(v!=z);" + 
            "print(x != v);" +
            "print(v != x);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("0\n1\n1\n1" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void lessThanVectorTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
            "vector v = 1..3;" + 
            "vector s = 1..3;" +
            "vector x = 1..2;" +
            "vector z = 2..4;" +
            "print(v<s);" +
            "print(s<v);" +
            "print(z<v);" +
            "print(v<z);" + 
            "print(x < v);" + 
            "print(v < x);"         );
	        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
	        
	        Vcalc_Test.main(args);
	        assertEquals("0\n0\n0\n1\n1\n0" , outErrIntercept.toString().trim());
	    }
    
    

    @Test(expected=RuntimeException.class)
    public void testTypeCheckGeneratorBothVectors() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        	"vector j = [i in 1..3 | i];"+ 
        	"vector p = [i in 1..5 | i];" +
        	"vector k = [i in j..p | i];");
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
    }
    
    @Test(expected=RuntimeException.class)
    public void testTypeCheckGeneratorLeftVector() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        	"vector j = [i in 1..3 | i];"+ 
        	"int p = 1;" +
        	"vector k = [i in j..p | i];"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
    }
    
    @Test(expected=RuntimeException.class)
    public void testTypeCheckGeneratorRightVector() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        	"vector j = 1;"+ 
        	"int p = [i in 1..5 | i];" +
        	"vector k = [i in j..p | i];"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
    }
    
    @Test(expected=RuntimeException.class)
    public void testFilterDomainVector() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "int v = filter(i in (5+5) | i > 5);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("" , outErrIntercept.toString().trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testGeneratorDomainVector() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "int v = filter(i in (5+5) | i > 5);"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("" , outErrIntercept.toString().trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testFilterExpression() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "int v = filter(i in 1..10 | (1..10));"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("" , outErrIntercept.toString().trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testGeneratorExpression() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "int v = filter(i in 1..10 | (1..10));"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("" , outErrIntercept.toString().trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testLoopConditional() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "loop(1..10) pool;"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("" , outErrIntercept.toString().trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testIfConditional() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "if(1..10) fi;"
        );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void primeTest() throws RecognitionException, IOException, ParserException {

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


    /**
     * 
     *         END INTERPRETER TESTS
     * 
     * 
     */

    
    /**
     * 
     *         DEFINED TESTS
     * 
     * 
     */
    
    @Test(expected=RuntimeException.class)
    public void doubleDeclarationTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        "vector v = [i in 1..10 | i];" +
        "vector v = [i in 1..10 | i];"
                );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 6 7 8 9 10 ]"
                , outErrIntercept.toString().trim());
    }
    
    // test that we don't get output if we use an undeclared variable
    @Test
    public void undefinedVariableTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"print(1);" +
        		"print(c);"
                );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        
        try {
        	Vcalc_Test.main(args);
        }
        catch(RuntimeException e) {
        	assertEquals("", outErrIntercept.toString().trim());
        }
        
    }
    
    // test that we are warned if are declarations are after statements
    @Test (expected=ParserException.class)
    public void improperDeclarationPosition() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"vector v = [i in 1..10 | i];\n" +
        		"print(1);\n" +
        		"int a = 2;\n" +
        		"print(1);\n"
                );
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        Vcalc_Test.main(args);
        String expected = "Tests/00temp.vcalc line 3:0 - declaration of variables must be at the top of the input file";
        assertEquals(expected, outErrIntercept.toString().trim());

        
    }
    
    // test that we are warned if we forget a semicolon
    @Test(expected=ParserException.class)
    public void missingSemicolon() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
        		"vector v = [i in 1..10 | i];\n" +
        		"print(1)\n" +
        		"print(2);"
        				);
        String[] args = new String[] {"Tests/00temp.vcalc","int", "test"};
        Vcalc_Test.main(args);
        String expected = "Tests/00temp.vcalc line 3:0 - expected semicolon before 'print'";
        assertEquals(expected, outErrIntercept.toString().trim());

        
    }
    
    
    /**
     * 
     *         END DEFINED TESTS
     * 
     * 
     */
    
    /**
     * 
     *         Lexer/Parser TESTS
     * 
     * 
     */
    @Test // Test simple print statements
    public void astPrintTest() throws IOException, RecognitionException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", "print(128); print(256); print(512);");
        String[] args = new String[] {"Tests/00temp.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        
        assertEquals("(PROGRAM (PRINT 128) (PRINT 256) (PRINT 512))", outErrIntercept.toString().trim());
    }
    
    @Test // AST Test: simple print statements
    public void astComparisonTest() throws IOException, RecognitionException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                "int x = 5;"
              + "int y = 5;"
              + "int z = 0;"
              + "print(x == x);"
              + "print(y == x);"
              + "print(y == z);"
              + "print(x != x);"
              + "print(z != x);"
              + "print(y != x);"
              + "print(x < x);"
              + "print(x < y);"
              + "print(z < y);"
              + "print(x > y);"
              + "print( 1 < 2 > 2 < 1);" // Precedence
              + "print( 1 == 2 != 0 == 0);"); // Precedence 
        String[] args = new String[] {"Tests/00temp.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        
        assertEquals("(PROGRAM (VAR int x 5) (VAR int y 5) (VAR int z 0) (PRINT (== x x)) " 
                   + "(PRINT (== y x)) (PRINT (== y z)) (PRINT (!= x x)) (PRINT (!= z x)) " 
                   + "(PRINT (!= y x)) (PRINT (< x x)) (PRINT (< x y)) (PRINT (< z y)) "
                   + "(PRINT (> x y)) (PRINT (< (> (< 1 2) 2) 1)) (PRINT (== (!= (== 1 2) 0) 0)))", 
                   outErrIntercept.toString().trim());
    }
    
    @Test // AST Test: simple nested if
    public void astIfTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                  "int x = 2;"
                + "int y = 0;"
                + "int z = 100;"
                + "if ( x != 0)"
                    + "print (x);"
                    + "x = x - 1;"
                    + "if( y == 0)"
                        + "y = y + 1;"
                        + "if(z < 0)"
                            + "print(z);"
                        + "fi;"
                    + "fi;"
                    + "print(y);"
                + "fi;");
        String[] args = new String[] {"Tests/00temp.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        assertEquals("(PROGRAM (VAR int x 2) (VAR int y 0) (VAR int z 100) "+
                     "(IF (!= x 0) " +
                         "(SLIST (PRINT x) (= x (- x 1)) "+
                         "(IF (== y 0) " + 
                             "(SLIST (= y (+ y 1)) "+
                             "(IF (< z 0) "+
                                 "(SLIST (PRINT z))))) "+
                     "(PRINT y))))\n", outErrIntercept.toString());
    }
    
    @Test // AST Test: simple nested loops
    public void astLoopTest() throws RecognitionException, IOException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                  "int x = 2;"
                + "int y = 0;"
                + "int z = 100;"
                + "loop ( x != 0)"
                    + "print (x);"
                    + "x = x - 1;"
                    + "loop( y < 2)"
                        + "y = y + 1;"
                        + "loop(z > 0)"
                            + "z = z - 5;"
                        + "pool;"
                    + "pool;"
                    + "print(y);"
                + "pool;");
        String[] args = new String[] {"Tests/00temp.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        assertEquals("(PROGRAM (VAR int x 2) (VAR int y 0) (VAR int z 100) " 
                    + "(LOOP (!= x 0) (SLIST (PRINT x) (= x (- x 1)) " + 
                          "(LOOP (< y 2) " +
                               "(SLIST (= y (+ y 1)) " +
                               "(LOOP (> z 0) " +
                                   "(SLIST (= z (- z 5)))))) "
                        + "(PRINT y)))"
                    + ")" 
                , outErrIntercept.toString().trim());
    }
    
    
    @Test
    public void astPrimeTest() throws RecognitionException, IOException, ParserException {

      String[] args = new String[] {"Tests/01prime.vcalc","astDebug"};
      
      Vcalc_Test.main(args);
      System.out.println();
      assertEquals("(PROGRAM (VAR int i 1) (VAR int p 1) (VAR int isPrime 1) " + 
                       "(LOOP (< p 1000) (SLIST (= i 1) (= isPrime 1) (= p (+ p 1)) " + 
                           "(LOOP (< i (/ p 2)) " + 
                               "(SLIST (= i (+ i 1)) " + 
                                   "(IF (== (* (/ p i) i) p) " + 
                                       "(SLIST (= isPrime 0) (= i p))))) "+ 
                           "(IF isPrime (SLIST (PRINT p)))"
                          + ")"+ 
                        ")" + 
                    ")", outErrIntercept.toString().trim());
    }
    
    @Test // Test simple vector statements
    public void astRangeTest() throws IOException, RecognitionException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                "vector v = 1..10;" +
                "vector w = 100..200;" +
                "vector z = v + w;"
                );
        String[] args = new String[] {"Tests/00temp.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        
        assertEquals("(PROGRAM (VAR vector v (.. 1 10)) (VAR vector w (.. 100 200)) (VAR vector z (+ v w)))", 
                outErrIntercept.toString().trim());
    }
    
    @Test // Test simple vector statements
    public void astGeneratorTest() throws IOException, RecognitionException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                "vector z = 1..10;" +
                "z = [i in 1..100 | i * i];" + // generator assignment
                "z = [i in [j in 1..10 | 2 * j] | i * i];" // Nested Generator
                );
        String[] args = new String[] {"Tests/00temp.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        
        assertEquals("(PROGRAM (VAR vector z (.. 1 10)) " +
                    "(= z (GENERATOR i (.. 1 100) (* i i))) " + 
                    "(= z (GENERATOR i (GENERATOR j (.. 1 10) (* 2 j)) (* i i)))" +
                    ")", outErrIntercept.toString().trim());
    }
    
    @Test // Test simple vector statements
    public void astFilterTest() throws IOException, RecognitionException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                "vector v = 1..10;" +
                "v = filter(i in 1..10 | i > 5);" + //filter
                "v = filter(i in filter(j in 1..20 | j < 11) | i > 5);" //nested filter
                );
        String[] args = new String[] {"Tests/00temp.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        
        assertEquals("(PROGRAM (VAR vector v (.. 1 10)) " +
                    "(= v (FILTER i (.. 1 10) (> i 5))) " +
                    "(= v (FILTER i (FILTER j (.. 1 20) (< j 11)) (> i 5)))" +
                    ")", outErrIntercept.toString().trim());
    }
    
    @Test // Test simple vector statements
    public void astVectorIndexTest() throws IOException, RecognitionException, ParserException {
        SampleFileWriter.createFile("Tests/00temp.vcalc", 
                "vector v = 1..10;" +
                "print(v[5]);" +
                "print([j in 1..3 | j * 2][1]);" + 
                "print(filter(i in 1..10 | i > 5)[2]);"
                );
        String[] args = new String[] {"Tests/00temp.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        
        assertEquals("(PROGRAM (VAR vector v (.. 1 10)) " +
                     "(PRINT (INDEX v 5)) " +
                     "(PRINT (INDEX (GENERATOR j (.. 1 3) (* j 2)) 1)) " +
                     "(PRINT (INDEX (FILTER i (.. 1 10) (> i 5)) 2))" +
                    ")", outErrIntercept.toString().trim());
    }
    
}
