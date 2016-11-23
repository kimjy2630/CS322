// Generated from RegularExpr.g4 by ANTLR 4.5.3
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link RegularExprParser}.
 */
public interface RegularExprListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link RegularExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(RegularExprParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegularExprParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(RegularExprParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code closure_union}
	 * labeled alternative in {@link RegularExprParser#closure}.
	 * @param ctx the parse tree
	 */
	void enterClosure_union(RegularExprParser.Closure_unionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code closure_union}
	 * labeled alternative in {@link RegularExprParser#closure}.
	 * @param ctx the parse tree
	 */
	void exitClosure_union(RegularExprParser.Closure_unionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code closure_id}
	 * labeled alternative in {@link RegularExprParser#closure}.
	 * @param ctx the parse tree
	 */
	void enterClosure_id(RegularExprParser.Closure_idContext ctx);
	/**
	 * Exit a parse tree produced by the {@code closure_id}
	 * labeled alternative in {@link RegularExprParser#closure}.
	 * @param ctx the parse tree
	 */
	void exitClosure_id(RegularExprParser.Closure_idContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegularExprParser#union}.
	 * @param ctx the parse tree
	 */
	void enterUnion(RegularExprParser.UnionContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegularExprParser#union}.
	 * @param ctx the parse tree
	 */
	void exitUnion(RegularExprParser.UnionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code union_rec}
	 * labeled alternative in {@link RegularExprParser#union_term}.
	 * @param ctx the parse tree
	 */
	void enterUnion_rec(RegularExprParser.Union_recContext ctx);
	/**
	 * Exit a parse tree produced by the {@code union_rec}
	 * labeled alternative in {@link RegularExprParser#union_term}.
	 * @param ctx the parse tree
	 */
	void exitUnion_rec(RegularExprParser.Union_recContext ctx);
	/**
	 * Enter a parse tree produced by the {@code union_base}
	 * labeled alternative in {@link RegularExprParser#union_term}.
	 * @param ctx the parse tree
	 */
	void enterUnion_base(RegularExprParser.Union_baseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code union_base}
	 * labeled alternative in {@link RegularExprParser#union_term}.
	 * @param ctx the parse tree
	 */
	void exitUnion_base(RegularExprParser.Union_baseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code concat_rec}
	 * labeled alternative in {@link RegularExprParser#concat}.
	 * @param ctx the parse tree
	 */
	void enterConcat_rec(RegularExprParser.Concat_recContext ctx);
	/**
	 * Exit a parse tree produced by the {@code concat_rec}
	 * labeled alternative in {@link RegularExprParser#concat}.
	 * @param ctx the parse tree
	 */
	void exitConcat_rec(RegularExprParser.Concat_recContext ctx);
	/**
	 * Enter a parse tree produced by the {@code concat_base}
	 * labeled alternative in {@link RegularExprParser#concat}.
	 * @param ctx the parse tree
	 */
	void enterConcat_base(RegularExprParser.Concat_baseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code concat_base}
	 * labeled alternative in {@link RegularExprParser#concat}.
	 * @param ctx the parse tree
	 */
	void exitConcat_base(RegularExprParser.Concat_baseContext ctx);
	/**
	 * Enter a parse tree produced by {@link RegularExprParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(RegularExprParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link RegularExprParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(RegularExprParser.StringContext ctx);
}