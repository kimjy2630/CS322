package Project2;

import java.io.*;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

import Project2_1.*;

public class Main2 {
	public static void main(String[] args) {

		ANTLRInputStream input = null;
		try {
			input = new ANTLRInputStream(new FileInputStream("re.txt"));
		} catch(FileNotFoundException e) {
			System.err.println("No input: re.txt");
			System.exit(-1);
		} catch(IOException e) {
			System.err.println("I/O error occured!!");
			System.exit(-1);
		}

		try {
			RegularExprLexer lexer = new RegularExprLexer(input);
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			RegularExprParser parser = new RegularExprParser(tokens);
			ParseTree tree = parser.expr();
			// System.out.println(tree.toStringTree(parser));

			ParseTreeWalker walker = new ParseTreeWalker();
			RegularExprBaseListener listener = new RegularExprBaseListener();
			walker.walk(listener, tree);

			Main2_1.eNFAToDFA(listener.stack.peek()).minimalize().printToFile("m-dfa.txt");
		} catch(Exception e) {
			System.err.println("parsing error!!");
			System.exit(-1);
		}
	}

}
