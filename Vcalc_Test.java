import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenRewriteStream;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeAdaptor;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.TreeAdaptor;
import org.antlr.stringtemplate.StringTemplateGroup;
import org.junit.Before;

import ast.vcalc.VcalcAST;
import ast.vcalc.VcalcErrorNode;
import symbol2.vcalc.SymbolTable;
import errors.vcalc.InvalidAssignmentException;
import errors.vcalc.ParserException;

public class Vcalc_Test {

    /** An adaptor that tells ANTLR to build VcalcAST nodes */
    public static TreeAdaptor VcalcAdaptor = new CommonTreeAdaptor() {
        public Object create(Token token) {
            return new VcalcAST(token);
        }
        public Object dupNode(Object t) {
            if ( t==null ) {
                return null;
            }
            return create(((VcalcAST)t).token);
        }

        public Object errorNode(TokenStream input,
                                Token start,
                                Token stop,
                                RecognitionException e)
        {
            return new VcalcErrorNode(input,start,stop,e);
        }
    };
    
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
        final TokenRewriteStream tokens = new TokenRewriteStream(lexer);
        //TokenStream tokenStream = new CommonTokenStream(lexer);
        VcalcParser parser = new VcalcParser(tokens);
        parser.setTreeAdaptor(VcalcAdaptor);
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
        
        CommonTreeNodeStream nodes = new CommonTreeNodeStream(ast);
        nodes.setTokenStream(tokens);
        nodes.setTreeAdaptor(VcalcAdaptor); 
        SymbolTable symTable = new SymbolTable();
        Defined defined = new Defined(nodes, symTable);
        
        // Pass over to verify no variable misuse && Define Symbols
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
        	
            // RESOLVE SYMBOLS, COMPUTE EXPRESSION TYPES
            nodes.reset();
            Types types = new Types(nodes, symTable);
            types.program(); // trigger resolve/type computation actions
            
            //System.out.println(symTable.toString());
            
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
