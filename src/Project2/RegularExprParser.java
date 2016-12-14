// Generated from RegularExpr.g4 by ANTLR 4.5.3
package Project2;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class RegularExprParser extends Parser {
	static {
		RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION);
	}

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
	public static final int T__0 = 1, T__1 = 2, T__2 = 3, T__3 = 4, Id = 5, E = 6, WS = 7;
	public static final int RULE_expr = 0, RULE_closure = 1, RULE_union = 2, RULE_union_term = 3, RULE_concat = 4,
			RULE_string = 5;
	public static final String[] ruleNames = { "expr", "closure", "union", "union_term", "concat", "string" };

	private static final String[] _LITERAL_NAMES = { null, "'*'", "'('", "')'", "'+'", null, "'()'" };
	private static final String[] _SYMBOLIC_NAMES = { null, null, null, null, null, "Id", "E", "WS" };
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() {
		return "RegularExpr.g4";
	}

	@Override
	public String[] getRuleNames() {
		return ruleNames;
	}

	@Override
	public String getSerializedATN() {
		return _serializedATN;
	}

	@Override
	public ATN getATN() {
		return _ATN;
	}

	public RegularExprParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	public static class ExprContext extends ParserRuleContext {
		public ClosureContext closure() {
			return getRuleContext(ClosureContext.class, 0);
		}

		public UnionContext union() {
			return getRuleContext(UnionContext.class, 0);
		}

		public Union_termContext union_term() {
			return getRuleContext(Union_termContext.class, 0);
		}

		public ConcatContext concat() {
			return getRuleContext(ConcatContext.class, 0);
		}

		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_expr;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).enterExpr(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_expr);
		try {
			setState(16);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 0, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(12);
				closure();
			}
				break;
			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(13);
				union();
			}
				break;
			case 3:
				enterOuterAlt(_localctx, 3); {
				setState(14);
				union_term();
			}
				break;
			case 4:
				enterOuterAlt(_localctx, 4); {
				setState(15);
				concat();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClosureContext extends ParserRuleContext {
		public ClosureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_closure;
		}

		public ClosureContext() {
		}

		public void copyFrom(ClosureContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class Closure_unionContext extends ClosureContext {
		public UnionContext union() {
			return getRuleContext(UnionContext.class, 0);
		}

		public Closure_unionContext(ClosureContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).enterClosure_union(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).exitClosure_union(this);
		}
	}

	public static class Closure_idContext extends ClosureContext {
		public TerminalNode Id() {
			return getToken(RegularExprParser.Id, 0);
		}

		public Closure_idContext(ClosureContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).enterClosure_id(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).exitClosure_id(this);
		}
	}

	public final ClosureContext closure() throws RecognitionException {
		ClosureContext _localctx = new ClosureContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_closure);
		try {
			setState(23);
			switch (_input.LA(1)) {
			case T__1:
				_localctx = new Closure_unionContext(_localctx);
				enterOuterAlt(_localctx, 1); {
				setState(18);
				union();
				setState(19);
				match(T__0);
			}
				break;
			case Id:
				_localctx = new Closure_idContext(_localctx);
				enterOuterAlt(_localctx, 2); {
				setState(21);
				match(Id);
				setState(22);
				match(T__0);
			}
				break;
			default:
				throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnionContext extends ParserRuleContext {
		public Union_termContext union_term() {
			return getRuleContext(Union_termContext.class, 0);
		}

		public UnionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_union;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).enterUnion(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).exitUnion(this);
		}
	}

	public final UnionContext union() throws RecognitionException {
		UnionContext _localctx = new UnionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_union);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(25);
				match(T__1);
				setState(26);
				union_term();
				setState(27);
				match(T__2);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Union_termContext extends ParserRuleContext {
		public Union_termContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_union_term;
		}

		public Union_termContext() {
		}

		public void copyFrom(Union_termContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class Union_baseContext extends Union_termContext {
		public ConcatContext concat() {
			return getRuleContext(ConcatContext.class, 0);
		}

		public Union_baseContext(Union_termContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).enterUnion_base(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).exitUnion_base(this);
		}
	}

	public static class Union_recContext extends Union_termContext {
		public ConcatContext concat() {
			return getRuleContext(ConcatContext.class, 0);
		}

		public Union_termContext union_term() {
			return getRuleContext(Union_termContext.class, 0);
		}

		public Union_recContext(Union_termContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).enterUnion_rec(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).exitUnion_rec(this);
		}
	}

	public final Union_termContext union_term() throws RecognitionException {
		Union_termContext _localctx = new Union_termContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_union_term);
		try {
			setState(34);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
			case 1:
				_localctx = new Union_recContext(_localctx);
				enterOuterAlt(_localctx, 1); {
				setState(29);
				concat();
				setState(30);
				match(T__3);
				setState(31);
				union_term();
			}
				break;
			case 2:
				_localctx = new Union_baseContext(_localctx);
				enterOuterAlt(_localctx, 2); {
				setState(33);
				concat();
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConcatContext extends ParserRuleContext {
		public ConcatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_concat;
		}

		public ConcatContext() {
		}

		public void copyFrom(ConcatContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class Concat_baseContext extends ConcatContext {
		public StringContext string() {
			return getRuleContext(StringContext.class, 0);
		}

		public ClosureContext closure() {
			return getRuleContext(ClosureContext.class, 0);
		}

		public UnionContext union() {
			return getRuleContext(UnionContext.class, 0);
		}

		public Concat_baseContext(ConcatContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).enterConcat_base(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).exitConcat_base(this);
		}
	}

	public static class Concat_recContext extends ConcatContext {
		public ConcatContext concat() {
			return getRuleContext(ConcatContext.class, 0);
		}

		public StringContext string() {
			return getRuleContext(StringContext.class, 0);
		}

		public ClosureContext closure() {
			return getRuleContext(ClosureContext.class, 0);
		}

		public UnionContext union() {
			return getRuleContext(UnionContext.class, 0);
		}

		public Concat_recContext(ConcatContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).enterConcat_rec(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).exitConcat_rec(this);
		}
	}

	public final ConcatContext concat() throws RecognitionException {
		ConcatContext _localctx = new ConcatContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_concat);
		try {
			setState(48);
			_errHandler.sync(this);
			switch (getInterpreter().adaptivePredict(_input, 5, _ctx)) {
			case 1:
				_localctx = new Concat_recContext(_localctx);
				enterOuterAlt(_localctx, 1); {
				setState(39);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 3, _ctx)) {
				case 1: {
					setState(36);
					string();
				}
					break;
				case 2: {
					setState(37);
					closure();
				}
					break;
				case 3: {
					setState(38);
					union();
				}
					break;
				}
				setState(41);
				concat();
			}
				break;
			case 2:
				_localctx = new Concat_baseContext(_localctx);
				enterOuterAlt(_localctx, 2); {
				setState(46);
				_errHandler.sync(this);
				switch (getInterpreter().adaptivePredict(_input, 4, _ctx)) {
				case 1: {
					setState(43);
					string();
				}
					break;
				case 2: {
					setState(44);
					closure();
				}
					break;
				case 3: {
					setState(45);
					union();
				}
					break;
				}
			}
				break;
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_string;
		}

		public StringContext() {
		}

		public void copyFrom(StringContext ctx) {
			super.copyFrom(ctx);
		}
	}

	public static class Str_mtContext extends StringContext {
		public TerminalNode E() {
			return getToken(RegularExprParser.E, 0);
		}

		public Str_mtContext(StringContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).enterStr_mt(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).exitStr_mt(this);
		}
	}

	public static class Str_nonmtContext extends StringContext {
		public List<TerminalNode> Id() {
			return getTokens(RegularExprParser.Id);
		}

		public TerminalNode Id(int i) {
			return getToken(RegularExprParser.Id, i);
		}

		public Str_nonmtContext(StringContext ctx) {
			copyFrom(ctx);
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).enterStr_nonmt(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof RegularExprListener)
				((RegularExprListener) listener).exitStr_nonmt(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_string);
		try {
			int _alt;
			setState(56);
			switch (_input.LA(1)) {
			case Id:
				_localctx = new Str_nonmtContext(_localctx);
				enterOuterAlt(_localctx, 1); {
				setState(51);
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1: {
						{
							setState(50);
							match(Id);
						}
					}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(53);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 6, _ctx);
				} while (_alt != 2 && _alt != org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER);
			}
				break;
			case E:
				_localctx = new Str_mtContext(_localctx);
				enterOuterAlt(_localctx, 2); {
				setState(55);
				match(E);
			}
				break;
			default:
				throw new NoViableAltException(this);
			}
		} catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		} finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN = "\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\t=\4\2\t\2\4\3\t"
			+ "\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\3\2\3\2\3\2\5\2\23\n\2\3\3\3\3"
			+ "\3\3\3\3\3\3\5\3\32\n\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\5\5%\n\5\3"
			+ "\6\3\6\3\6\5\6*\n\6\3\6\3\6\3\6\3\6\3\6\5\6\61\n\6\5\6\63\n\6\3\7\6\7"
			+ "\66\n\7\r\7\16\7\67\3\7\5\7;\n\7\3\7\2\2\b\2\4\6\b\n\f\2\2B\2\22\3\2\2"
			+ "\2\4\31\3\2\2\2\6\33\3\2\2\2\b$\3\2\2\2\n\62\3\2\2\2\f:\3\2\2\2\16\23"
			+ "\5\4\3\2\17\23\5\6\4\2\20\23\5\b\5\2\21\23\5\n\6\2\22\16\3\2\2\2\22\17"
			+ "\3\2\2\2\22\20\3\2\2\2\22\21\3\2\2\2\23\3\3\2\2\2\24\25\5\6\4\2\25\26"
			+ "\7\3\2\2\26\32\3\2\2\2\27\30\7\7\2\2\30\32\7\3\2\2\31\24\3\2\2\2\31\27"
			+ "\3\2\2\2\32\5\3\2\2\2\33\34\7\4\2\2\34\35\5\b\5\2\35\36\7\5\2\2\36\7\3"
			+ "\2\2\2\37 \5\n\6\2 !\7\6\2\2!\"\5\b\5\2\"%\3\2\2\2#%\5\n\6\2$\37\3\2\2"
			+ "\2$#\3\2\2\2%\t\3\2\2\2&*\5\f\7\2\'*\5\4\3\2(*\5\6\4\2)&\3\2\2\2)\'\3"
			+ "\2\2\2)(\3\2\2\2*+\3\2\2\2+,\5\n\6\2,\63\3\2\2\2-\61\5\f\7\2.\61\5\4\3"
			+ "\2/\61\5\6\4\2\60-\3\2\2\2\60.\3\2\2\2\60/\3\2\2\2\61\63\3\2\2\2\62)\3"
			+ "\2\2\2\62\60\3\2\2\2\63\13\3\2\2\2\64\66\7\7\2\2\65\64\3\2\2\2\66\67\3"
			+ "\2\2\2\67\65\3\2\2\2\678\3\2\2\28;\3\2\2\29;\7\b\2\2:\65\3\2\2\2:9\3\2"
			+ "\2\2;\r\3\2\2\2\n\22\31$)\60\62\67:";
	public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}