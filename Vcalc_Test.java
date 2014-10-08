import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.junit.Before;

import symbol2.vcalc.SymbolTable;
import errors.vcalc.InvalidAssignmentException;
import errors.vcalc.ParserException;

public class Vcalc_Test {

    public static void main(String[] args) throws RecognitionException, IOException, ParserException, InvalidAssignmentException {
        if (args.length != 2 && !(args.length == 3 && args[2].equals("test"))) {
            System.err.print("Insufficient arguments: ");
            System.err.println(Arrays.toString(args));
            System.exit(1);
        }

        ANTLRFileStream input = null;
        try {
            input = new ANTLRFileStream(args[0]);
        } catch (IOException e) {
            System.err.print("Invalid program filename: ");
            System.err.println(args[0]);
            System.exit(1);
        }

        PrintStream err_backup = System.err;
        ByteArrayOutputStream outErrIntercept = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outErrIntercept));

        VcalcLexer lexer = new VcalcLexer(input);
        TokenStream tokenStream = new CommonTokenStream(lexer);
        VcalcParser parser = new VcalcParser(tokenStream);
        VcalcParser.program_return entry = parser.program();
        
        
        String errors = outErrIntercept.toString().trim();
        System.setErr(err_backup);
        
        if (errors.length() > 0) {
        	System.err.println(args[0] + " has the following errors: ");
        	System.err.println(errors);
        	if ((args.length == 3 && args[2].equals("test")))
        	{
        		throw new ParserException("Invalid program syntax.");
        	}
        	return;
        }
        
        CommonTree ast = (CommonTree) entry.getTree();
        
        if(args[1].equals("astDebug")) {
            System.out.println(ast.toStringTree());
            return;
        }
        
        // Pass over to verify no variable misuse
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
        nodes.setTokenStream(tokenStream);
        SymbolTable symTable = new SymbolTable();
        Defined defined = new Defined(nodes, symTable);
        defined.program();
        //defined.downup(ast);
        //System.err.println(ast.toStringTree());

        if (args[1].equals("int")) {
            // Run it through the Interpreter
            nodes.reset();
            Interpreter interpreter = new Interpreter(nodes);
            interpreter.program().evaluate();
        }
        else {
        	//Pass it all to the String templater!
            String templateFile = args[1];

              // Load in string template from file
			FileReader groupFileR = new FileReader(templateFile);
			StringTemplateGroup stg = new StringTemplateGroup(groupFileR); 
			groupFileR.close();
			
			nodes.reset();
			Templater templater = new Templater(nodes);
			templater.setTemplateLib(stg);
			System.out.println(templater.program().getTemplate().toString());
        }
    }

}
