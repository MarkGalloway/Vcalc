

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.runtime.RecognitionException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import errors.vcalc.InvalidAssignmentException;
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
        SampleFileWriter.destroy("Tests/00dummyINTERPRETERtest.vcalc");
    }
    
    /**
     * 
     *          INTERPRETER TESTS
     * 
     * 
     */

    @Test // Just a dummy test to verify testing output actually works
    public void dummyTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
        SampleFileWriter.createFile("Tests/00dummyINTERPRETERtest.vcalc", "print(256);");
        String[] args = new String[] {"Tests/00dummyINTERPRETERtest.vcalc","int"};
        
        Vcalc_Test.main(args);
        assertEquals("256\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple addition, brackets, and assignment
    public void additionTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/18INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/18INTERPRETERtest.vcalc", 
                  "int x = 1 + 1;"
                + "int y = 1 + 1 + (1 + 1) + 1 + 1 + (((1))) + 1 + (1 + 1 + 1) + 0;"
                + "int z = x + y;"
                + "print(z);");
        String[] args = new String[] {"Tests/18INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("13\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple assignment
    public void assignmentTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/19INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/19INTERPRETERtest.vcalc", 
        		"int i = 1;"
        		+ "i = 2;"
                  + "print(i);");
        String[] args = new String[] {"Tests/19INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("2\n", outErrIntercept.toString());
    }
    
    @Test(expected=InvalidAssignmentException.class)
    public void assignmentVectorToInt() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/20INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/20INTERPRETERtest.vcalc","vector i = 1;");
        String[] args = new String[] {"Tests/20INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("2\n", outErrIntercept.toString());
    }
    
    @Test(expected=InvalidAssignmentException.class)
    public void assignmentIntToVector() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/21INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/21INTERPRETERtest.vcalc","int i = 1..10;");
        String[] args = new String[] {"Tests/21INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("2\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple subtraction, brackets and, assignment
    public void subtractionTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/22INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/22INTERPRETERtest.vcalc", 
                  "int x = 1 - 1;"
                + "int y = 1 - 1 - (1 - 1) - 1 - 1 - (((1))) - 1 - (1 - 1 - 1) - 0;"
                + "int z = x + y;"
                + "print(z);"
                + "x = 2 - 6;"
                + "y = 4 - 7;"
                + "z = y - x;"
                + "print(z);");
        String[] args = new String[] {"Tests/22INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("-3\n" + "1\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple multiplication, brackets, and assignment
    public void multiplicationTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/23INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/23INTERPRETERtest.vcalc", 
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
        String[] args = new String[] {"Tests/23INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("1\n" + "0\n" + "0\n" + "12\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple multiplication, brackets, and assignment
    public void divisionTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/24INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/24INTERPRETERtest.vcalc", 
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
        String[] args = new String[] {"Tests/24INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("1\n" + "1\n" + "0\n" + "1\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple comparison
    public void comparisonTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/25INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/25INTERPRETERtest.vcalc", 
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
        String[] args = new String[] {"Tests/25INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("1\n" + "1\n" + "0\n" 
                   + "0\n" + "1\n" + "0\n" 
                   + "0\n" + "0\n" + "1\n"
                   + "0\n" + "1\n" + "1\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple nested ifs
    public void ifTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/26INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/26INTERPRETERtest.vcalc", 
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
        String[] args = new String[] {"Tests/26INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("2\n" + "1\n", outErrIntercept.toString());
    }
    
    @Test // Expression Test: simple nested loops
    public void loopTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/27INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/27INTERPRETERtest.vcalc", 
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
        String[] args = new String[] {"Tests/27INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("2\n" + "2\n" + "1\n" + "2\n", outErrIntercept.toString());
    }
    
    @Test // Vector Print Test
    public void vectorPrintTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/28INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/28INTERPRETERtest.vcalc", 
        		"vector v = 1..10;"
        		 + "print(v);");
        String[] args = new String[] {"Tests/28INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 2 3 4 5 6 7 8 9 10 ]", outErrIntercept.toString().trim());
    }
    
    @Test
    public void generatorTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/29INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/29INTERPRETERtest.vcalc", 
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
        String[] args = new String[] {"Tests/29INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 0 0 0 0 0 0 0 0 0 0 ]\n" + 
                     "[ 5 5 5 5 5 5 5 5 5 5 ]\n" + 
                     "[ 1 2 3 4 5 6 7 8 9 10 ]" , outErrIntercept.toString().trim());
    }
    
    // Test that we retrieve local scope variables correctly
    @Test
    public void nestedScopeTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/30INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/30INTERPRETERtest.vcalc", 
                "int i = 666;" +  
                "vector v = [i in [i in 1..10 | 2 * i] | i * i];" +
                "print(v);"
                );
        String[] args = new String[] {"Tests/30INTERPRETERtest.vcalc","int", "test"};
        Vcalc_Test.main(args);
        assertEquals("[ 4 16 36 64 100 144 196 256 324 400 ]" , outErrIntercept.toString().trim());
    }
    
    // Test that we can access global variables if there are no corresponding local variables
    @Test
    public void scopeTestAccessEnclosingScope() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/31INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/31INTERPRETERtest.vcalc", 
                "int i = 5;" +  
                "vector v = [k in [j in 1..10 | j*i] | k*i];" +
                "print(v);"
                );
        String[] args = new String[] {"Tests/31INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 25 50 75 100 125 150 175 200 225 250 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void generalScopeTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/32INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/32INTERPRETERtest.vcalc", 
        "int i = 0;" +
        "int j = 3;" +
        "vector v = [i in 1..3 | j * i * i];" +
        "print(i);" +
        "print(v);"
                );
        String[] args = new String[] {"Tests/32INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("0\n[ 3 12 27 ]" , outErrIntercept.toString().trim());
    }
    
    
    // Should have a runtime exception, variable i is not defined in j
    @Test(expected=RuntimeException.class)
    public void scopeTestAccessUndefinedEnclosingScope() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/33INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/33INTERPRETERtest.vcalc", 
                "vector v = [i in [j in 1..2 | 1 * i] | i];" +
                "print(v);"
                );
        String[] args = new String[] {"Tests/33INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
    }
    
    
    @Test
    public void filterTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/34INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/34INTERPRETERtest.vcalc", 
        "vector v = [i in 95..100 | i];" +
        "v = filter(i in v | i > 95);" + 
         "print(v);"
                );
        String[] args = new String[] {"Tests/34INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 96 97 98 99 100 ]"
                , outErrIntercept.toString().trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testIndexIntType() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/35INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/35INTERPRETERtest.vcalc", 
        "int i = 2;\n" +
        "print(i[2]);"
        );
        String[] args = new String[] {"Tests/35INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
    }
    
    @Test
    public void testIndex() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/36INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/36INTERPRETERtest.vcalc", 
        "int i = 2;\n" +
        "vector j = [i in 1..2 | 2 - i];" +
        "print(j);" +
        "print([i in 1..2 | 2 - i][0]);" +
        "print([i in 1..2 | 2 - i][1]);"
        );
        String[] args = new String[] {"Tests/36INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 0 ]\n1\n0" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testIndexFilter() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/37INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/37INTERPRETERtest.vcalc", 
        "print(filter(i in 1..10 | i > 5)[4]);"
        );
        String[] args = new String[] {"Tests/37INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("10" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testIndexOutOfBounds() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/38INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/38INTERPRETERtest.vcalc", 
        "int v = [i in 1..5 | i ][3-6];" +
        "print(v);" +
        "print(filter(i in 1..10 | i > 5)[5-2]);"
        + "print(filter(i in 1..10 | i > 5)[11]);"

        );
        String[] args = new String[] {"Tests/38INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("0\n9\n0" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testIndexingWithVectors() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/39INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/39INTERPRETERtest.vcalc", 
        	"vector v = 1..3;" +
        	"vector j = [i in 1..2 | 2 - i];"+
        	"print(v);" +
        	"print(j);" +
        	"print(v[j]);"
        );
        String[] args = new String[] {"Tests/39INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 2 3 ]\n[ 1 0 ]\n[ 2 1 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testAddVectors() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/40INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/40INTERPRETERtest.vcalc", 
        	"vector v = 1..5;" +
        	"vector j = [i in 1..2 | 2 - i];"+
        	"print(v);" +
        	"print(j);" +
        	"print(v + j);"
        );
        String[] args = new String[] {"Tests/40INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 2 3 4 5 ]\n[ 1 0 ]\n[ 2 2 3 4 5 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testAddVectorToInt() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/41INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/41INTERPRETERtest.vcalc", 
        	"vector v = 1..5;" +
        	"int j = 5;"+
        	"print(v);" +
        	"print(j);" +
        	"print(v + j);"
        );
        String[] args = new String[] {"Tests/41INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 2 3 4 5 ]\n5\n[ 6 7 8 9 10 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testAddIntToVector() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/42INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/42INTERPRETERtest.vcalc", 
        	"vector v = 1..5;" +
        	"int j = 5;"+
        	"print(v);" +
        	"print(j);" +
        	"print(j + v);"
        );
        String[] args = new String[] {"Tests/42INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 2 3 4 5 ]\n5\n[ 6 7 8 9 10 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testSubtractVectors() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/43INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/43INTERPRETERtest.vcalc", 
        	"vector v = 1..10;" +
        	"vector j = [i in 1..5 | 6];"+
        	"print(v);" +
        	"print(j);" +
        	"print(v-j);" +
        	"print([i in 1..2 | 0-1] - [i in 1..5 | i]);"
        	+ "print(j);"
        );
        String[] args = new String[] {"Tests/43INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 2 3 4 5 6 7 8 9 10 ]\n[ 6 6 6 6 6 ]\n[ -5 -4 -3 -2 -1 6 7 8 9 10 ]\n[ -2 -3 -3 -4 -5 ]\n[ 6 6 6 6 6 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testSubtractIntVector() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/44INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/44INTERPRETERtest.vcalc", 
        	"int v = 1;" +
        	"vector j = [i in 1..5 | 6];"+
        	"print(j-v);" +
        	"print(v-j);"
        );
        String[] args = new String[] {"Tests/44INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 5 5 5 5 5 ]\n[ -5 -5 -5 -5 -5 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testMultVector() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/45INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/45INTERPRETERtest.vcalc", 
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
        String[] args = new String[] {"Tests/45INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 2 4 6 ]\n[ 2 4 6 ]\n[ 1 2 3 ]\n[ 1 4 9 0 0 ]\n[ 1 4 9 0 0 ]\n[ 1 2 3 4 5 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testDivideVector() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/46INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/46INTERPRETERtest.vcalc", 
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
        String[] args = new String[] {"Tests/46INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 0 1 1 ]\n[ 2 1 0 ]\n[ 1 2 3 ]\n[ 1 1 1 0 0 ]\n[ 1 1 1 4 5 ]\n[ 1 2 3 4 5 ]" , outErrIntercept.toString().trim());
    }

    @Test
    public void equalityVectorTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/47INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/47INTERPRETERtest.vcalc", 
            "vector v = 1..3;" + 
            "vector s = 1..3;" +
            "vector x = 1..2;" +
            "vector z = 2..4;" +
            "print(v==s);" +
            "print(s==v);" +
            "print(v==z);" +
            "print(z==v);" +
            "print(x==v);" +
            "print(v==x);" + 
            "print([i in 1..5 | 1] == 1..5 == 2..6);" //left associativity test
        );
        String[] args = new String[] {"Tests/47INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 1 1 1 ]\n" + 
                     "[ 1 1 1 ]\n" + 
                     "[ 0 0 0 ]\n" +
                     "[ 0 0 0 ]\n" + 
                     "[ 1 1 0 ]\n" + 
                     "[ 1 1 0 ]\n" + 
                     "[ 0 0 0 0 0 ]", outErrIntercept.toString().trim());
    }
    
    @Test
    public void inequalityVectorTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/48INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/48INTERPRETERtest.vcalc", 
            "vector v = 1..3;" + 
            "vector s = 1..3;" +
            "vector x = 1..2;" +
            "vector z = 2..4;" +
            "print(v!=s);" +
            "print(s!=v);" +
            "print(v!=z);" + 
            "print(z!=v);" + 
            "print(x!=v);" +
            "print(v!=x);" + 
            "print(1..3 != 1..3 != 5..7);" //left associativity test
        );
        String[] args = new String[] {"Tests/48INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 0 0 0 ]\n" + 
                     "[ 0 0 0 ]\n" +
                     "[ 1 1 1 ]\n" +
                     "[ 1 1 1 ]\n" +
                     "[ 0 0 1 ]\n" +
                     "[ 0 0 1 ]\n" +
                     "[ 1 1 1 ]" +
                "", outErrIntercept.toString().trim());
    }
    
    @Test
    public void lessThanVectorTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/49INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/49INTERPRETERtest.vcalc", 
            "vector v = 1..3;" + 
            "vector s = 1..3;" +
            "vector x = 1..2;" +
            "vector z = 2..4;" +
            "print(v<s);" +
            "print(s<v);" +
            "print(z<v);" +
            "print(v<z);" + 
            "print(x<v);" + 
            "print(v<x);" +
            "print(1..3 < 1..3 < [i in 1..3 | 1]);" //left associativity test
            
                );
	        String[] args = new String[] {"Tests/49INTERPRETERtest.vcalc","int", "test"};
	        
	        Vcalc_Test.main(args);
	        assertEquals("[ 0 0 0 ]\n" + 
	                     "[ 0 0 0 ]\n" + 
	                     "[ 0 0 0 ]\n" +
	                     "[ 1 1 1 ]\n" + 
	                     "[ 0 0 0 ]\n" + 
	                     "[ 0 0 0 ]\n" + 
	                     "[ 1 1 1 ]" + 
	        "", outErrIntercept.toString().trim());
	    }
    
    @Test
    public void greaterThanVectorTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/50INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/50INTERPRETERtest.vcalc", 
            "vector v = 1..3;" + 
            "vector s = 1..3;" +
            "vector x = 1..2;" +
            "vector z = 2..4;" +
            "print(v>s);" +
            "print(s>v);" +
            "print(z>v);" +
            "print(v>z);" + 
            "print(x>v);" + 
            "print(v>x);" +
            "print(1..3 > 1..3 > [i in 1..3 | 1]);" //left associativity test
            
                );
            String[] args = new String[] {"Tests/50INTERPRETERtest.vcalc","int", "test"};
            
            Vcalc_Test.main(args);
            assertEquals("[ 0 0 0 ]\n" + 
                         "[ 0 0 0 ]\n" +
                         "[ 1 1 1 ]\n" +
                         "[ 0 0 0 ]\n" + 
                         "[ 0 0 0 ]\n" + 
                         "[ 0 0 0 ]\n" + 
                         "[ 0 0 0 ]" + 
            "", outErrIntercept.toString().trim());
        }
    
    

    @Test(expected=RuntimeException.class)
    public void testTypeCheckGeneratorBothVectors() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/51INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/51INTERPRETERtest.vcalc", 
        	"vector j = [i in 1..3 | i];"+ 
        	"vector p = [i in 1..5 | i];" +
        	"vector k = [i in j..p | i];");
        String[] args = new String[] {"Tests/51INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
    }
    
    @Test(expected=RuntimeException.class)
    public void testTypeCheckGeneratorLeftVector() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/52INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/52INTERPRETERtest.vcalc", 
        	"vector j = [i in 1..3 | i];"+ 
        	"int p = 1;" +
        	"vector k = [i in j..p | i];"
        );
        String[] args = new String[] {"Tests/52INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
    }
    
    @Test(expected=RuntimeException.class)
    public void testTypeCheckGeneratorRightVector() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/53INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/53INTERPRETERtest.vcalc", 
        	"int j = 1;"+ 
        	"vector p = [i in 1..5 | i];" +
        	"vector k = [i in j..p | i];"
        );
        String[] args = new String[] {"Tests/53INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
    }
    
    @Test(expected=RuntimeException.class)
    public void testFilterDomainVector() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/54INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/54INTERPRETERtest.vcalc", 
        "vector v = filter(i in (5+5) | i > 5);"
        );
        String[] args = new String[] {"Tests/54INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("" , outErrIntercept.toString().trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testGeneratorDomainVector() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/55INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/55INTERPRETERtest.vcalc", 
        "vector v = filter(i in (5+5) | i > 5);"
        );
        String[] args = new String[] {"Tests/55INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("" , outErrIntercept.toString().trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testFilterExpression() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/56INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/56INTERPRETERtest.vcalc", 
        "vector v = filter(i in 1..10 | (1..10));"
        );
        String[] args = new String[] {"Tests/56INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("" , outErrIntercept.toString().trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testGeneratorExpression() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/57INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/57INTERPRETERtest.vcalc", 
        "vector v = filter(i in 1..10 | 1..10);"
        );
        String[] args = new String[] {"Tests/57INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("" , outErrIntercept.toString().trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testLoopConditional() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/58INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/58INTERPRETERtest.vcalc", 
        "loop(1..10) pool;"
        );
        String[] args = new String[] {"Tests/58INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("" , outErrIntercept.toString().trim());
    }
    
    @Test(expected=RuntimeException.class)
    public void testIfConditional() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/59INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/59INTERPRETERtest.vcalc", 
        "if(1..10) fi;"
        );
        String[] args = new String[] {"Tests/59INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testForumExert() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/60INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/60INTERPRETERtest.vcalc", 
        "vector v1 = 1..10;" +
        "vector v2 = 2..8;" +
        "vector v3 = v1 * 2;" +
        "vector v4 = [i in (v1 * v2) + (v3 / 2) | i * 3 ];" +
        "vector v5 = filter(i in v4 | (i > 50) * (i < 100) );" +
        "print(v5);"
        );
        String[] args = new String[] {"Tests/60INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 72 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testSpecAssociativityExamples() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/610INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/61INTERPRETERtest.vcalc", 
        "vector v = 1..10;"
        + "print(v + 5);"
        + "print(5 + [i in 1..3 | 0] + 1..5);"
        + "print((5 + [i in 1..3 | 0]) + 1..5);"
        );
        String[] args = new String[] {"Tests/61INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 6 7 8 9 10 11 12 13 14 15 ]\n[ 6 7 8 4 5 ]\n[ 6 7 8 4 5 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void testSpecScopeExamples() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/62INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/62INTERPRETERtest.vcalc", 
        "int i = 0;"
        + "int j = 3;"
        + "vector v = [i in 1..3 | j * i * i];"
        + "print(i);"
        + "print(v);"
        );
        String[] args = new String[] {"Tests/62INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("0\n[ 3 12 27 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void rangeOperatorForumsTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/63INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/63INTERPRETERtest.vcalc", 
        "vector v = ([i in 1..3 | i * 2][1])..([j in 2..5 | j * 3][0]);"
        + "print(v);"
        );
        String[] args = new String[] {"Tests/63INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 4 5 6 ]" , outErrIntercept.toString().trim());
    }
    
    @Test
    public void primeTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {

      String[] args = new String[] {"Tests/INTERPRETERprimes.vcalc","int"};
      
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
    public void doubleDeclarationTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/64INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/64INTERPRETERtest.vcalc", 
        "vector v = [i in 1..10 | i];" +
        "vector v = [i in 1..10 | i];"
                );
        String[] args = new String[] {"Tests/64INTERPRETERtest.vcalc","int", "test"};
        
        Vcalc_Test.main(args);
        assertEquals("[ 6 7 8 9 10 ]"
                , outErrIntercept.toString().trim());
    }
    
    // test that we don't get output if we use an undeclared variable
    @Test
    public void undefinedVariableTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/65INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/65INTERPRETERtest.vcalc", 
        		"print(1);" +
        		"print(c);"
                );
        String[] args = new String[] {"Tests/65INTERPRETERtest.vcalc","int", "test"};
        
        try {
        	Vcalc_Test.main(args);
        }
        catch(RuntimeException e) {
        	assertEquals("", outErrIntercept.toString().trim());
        }
        
    }
    
    // test that we are warned if are declarations are after statements
    @Test (expected=ParserException.class)
    public void improperDeclarationPosition() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/66INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/66INTERPRETERtest.vcalc", 
        		"vector v = [i in 1..10 | i];\n" +
        		"print(1);\n" +
        		"int a = 2;\n" +
        		"print(1);\n"
                );
        String[] args = new String[] {"Tests/66INTERPRETERtest.vcalc","int", "test"};
        Vcalc_Test.main(args);
        String expected = "Tests/00temp.vcalc line 3:0 - declaration of variables must be at the top of the input file";
        assertEquals(expected, outErrIntercept.toString().trim());

        
    }
    
    // test that we are warned if we forget a semicolon
    @Test(expected=ParserException.class)
    public void missingSemicolon() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/67INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/67INTERPRETERtest.vcalc", 
        		"vector v = [i in 1..10 | i];\n" +
        		"print(1)\n" +
        		"print(2);"
        				);
        String[] args = new String[] {"Tests/67INTERPRETERtest.vcalc","int", "test"};
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
    public void astPrintTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/68INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/68INTERPRETERtest.vcalc", "print(128); print(256); print(512);");
        String[] args = new String[] {"Tests/68INTERPRETERtest.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        
        assertEquals("(PROGRAM (PRINT 128) (PRINT 256) (PRINT 512))", outErrIntercept.toString().trim());
    }
    
    @Test // AST Test: simple print statements
    public void astComparisonTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/69INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/69INTERPRETERtest.vcalc", 
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
        String[] args = new String[] {"Tests/69INTERPRETERtest.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        
        assertEquals("(PROGRAM (VAR int x 5) (VAR int y 5) (VAR int z 0) (PRINT (== x x)) " 
                   + "(PRINT (== y x)) (PRINT (== y z)) (PRINT (!= x x)) (PRINT (!= z x)) " 
                   + "(PRINT (!= y x)) (PRINT (< x x)) (PRINT (< x y)) (PRINT (< z y)) "
                   + "(PRINT (> x y)) (PRINT (< (> (< 1 2) 2) 1)) (PRINT (== (!= (== 1 2) 0) 0)))", 
                   outErrIntercept.toString().trim());
    }
    
    @Test // AST Test: simple nested if
    public void astIfTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/70INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/70INTERPRETERtest.vcalc", 
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
        String[] args = new String[] {"Tests/70INTERPRETERtest.vcalc","astDebug"};
        
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
    public void astLoopTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/71INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/71INTERPRETERtest.vcalc", 
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
        String[] args = new String[] {"Tests/71INTERPRETERtest.vcalc","astDebug"};
        
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
    
    
    @Test // Test simple vector statements
    public void astRangeTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/72INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/72INTERPRETERtest.vcalc", 
                "vector v = 1..10;" +
                "vector w = 100..200;" +
                "vector z = v + w;"
                );
        String[] args = new String[] {"Tests/72INTERPRETERtest.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        
        assertEquals("(PROGRAM (VAR vector v (.. 1 10)) (VAR vector w (.. 100 200)) (VAR vector z (+ v w)))", 
                outErrIntercept.toString().trim());
    }
    
    @Test // Test simple vector statements
    public void astGeneratorTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/73INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/73INTERPRETERtest.vcalc", 
                "vector z = 1..10;" +
                "z = [i in 1..100 | i * i];" + // generator assignment
                "z = [i in [j in 1..10 | 2 * j] | i * i];" // Nested Generator
                );
        String[] args = new String[] {"Tests/73INTERPRETERtest.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        
        assertEquals("(PROGRAM (VAR vector z (.. 1 10)) " +
                    "(= z (GENERATOR i (.. 1 100) (* i i))) " + 
                    "(= z (GENERATOR i (GENERATOR j (.. 1 10) (* 2 j)) (* i i)))" +
                    ")", outErrIntercept.toString().trim());
    }
    
    @Test // Test simple vector statements
    public void astFilterTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/74INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/74INTERPRETERtest.vcalc", 
                "vector v = 1..10;" +
                "v = filter(i in 1..10 | i > 5);" + //filter
                "v = filter(i in filter(j in 1..20 | j < 11) | i > 5);" //nested filter
                );
        String[] args = new String[] {"Tests/74INTERPRETERtest.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        
        assertEquals("(PROGRAM (VAR vector v (.. 1 10)) " +
                    "(= v (FILTER i (.. 1 10) (> i 5))) " +
                    "(= v (FILTER i (FILTER j (.. 1 20) (< j 11)) (> i 5)))" +
                    ")", outErrIntercept.toString().trim());
    }
    
    @Test // Test simple vector statements
    public void astVectorIndexTest() throws IOException, RecognitionException, ParserException, InvalidAssignmentException {
    	SampleFileWriter.destroy("Tests/75INTERPRETERtest.vcalc");
        SampleFileWriter.createFile("Tests/75INTERPRETERtest.vcalc", 
                "vector v = 1..10;" +
                "print(v[5]);" +
                "print([j in 1..3 | j * 2][1]);" + 
                "print(filter(i in 1..10 | i > 5)[2]);"
                );
        String[] args = new String[] {"Tests/75INTERPRETERtest.vcalc","astDebug"};
        
        Vcalc_Test.main(args);
        
        assertEquals("(PROGRAM (VAR vector v (.. 1 10)) " +
                     "(PRINT (INDEX v 5)) " +
                     "(PRINT (INDEX (GENERATOR j (.. 1 3) (* j 2)) 1)) " +
                     "(PRINT (INDEX (FILTER i (.. 1 10) (> i 5)) 2))" +
                    ")", outErrIntercept.toString().trim());
    }
    
}
