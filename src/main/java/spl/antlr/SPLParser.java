// Generated from SPL.g4 by ANTLR 4.13.2

    package spl.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class SPLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		VOID=1, NUM=2, RETURN=3, PRINT=4, NOP=5, COMMENT=6, IF=7, THEN=8, ELSE=9, 
		DO=10, WHILE=11, UNTIL=12, MOD=13, ADD=14, SUB=15, MUL=16, DIV=17, NEG=18, 
		NOT=19, AND=20, OR=21, EQ=22, LARGER=23, LESSER=24, USER_ID=25, NUM_LIT=26, 
		STRING=27, LEFT_PAREN=28, RIGHT_PAREN=29, LEFT_BRACE=30, RIGHT_BRACE=31, 
		COLON=32, SEMICOLON=33, ASSIGN_OP=34, WS=35;
	public static final int
		RULE_spl_prog = 0, RULE_p = 1, RULE_v_decl = 2, RULE_f_decl = 3, RULE_f_type = 4, 
		RULE_algo = 5, RULE_instr = 6, RULE_outp = 7, RULE_call = 8, RULE_input = 9, 
		RULE_assign = 10, RULE_term = 11, RULE_branch = 12, RULE_bool = 13, RULE_loop = 14, 
		RULE_cond = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"spl_prog", "p", "v_decl", "f_decl", "f_type", "algo", "instr", "outp", 
			"call", "input", "assign", "term", "branch", "bool", "loop", "cond"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'void'", "'num'", "'return'", "'print'", "'nop'", "'comment'", 
			"'if'", "'then'", "'else'", "'do'", "'while'", "'until'", "'mod'", "'add'", 
			"'sub'", "'mul'", "'div'", "'neg'", "'not'", "'and'", "'or'", "'eq'", 
			"'larger'", "'lesser'", null, null, null, "'('", "')'", "'{'", "'}'", 
			"':'", "';'", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "VOID", "NUM", "RETURN", "PRINT", "NOP", "COMMENT", "IF", "THEN", 
			"ELSE", "DO", "WHILE", "UNTIL", "MOD", "ADD", "SUB", "MUL", "DIV", "NEG", 
			"NOT", "AND", "OR", "EQ", "LARGER", "LESSER", "USER_ID", "NUM_LIT", "STRING", 
			"LEFT_PAREN", "RIGHT_PAREN", "LEFT_BRACE", "RIGHT_BRACE", "COLON", "SEMICOLON", 
			"ASSIGN_OP", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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
	public String getGrammarFileName() { return "SPL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SPLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Spl_progContext extends ParserRuleContext {
		public PContext p() {
			return getRuleContext(PContext.class,0);
		}
		public TerminalNode EOF() { return getToken(SPLParser.EOF, 0); }
		public Spl_progContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_spl_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterSpl_prog(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitSpl_prog(this);
		}
	}

	public final Spl_progContext spl_prog() throws RecognitionException {
		Spl_progContext _localctx = new Spl_progContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_spl_prog);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			p();
			setState(33);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PContext extends ParserRuleContext {
		public V_declContext v_decl() {
			return getRuleContext(V_declContext.class,0);
		}
		public List<TerminalNode> COLON() { return getTokens(SPLParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(SPLParser.COLON, i);
		}
		public F_declContext f_decl() {
			return getRuleContext(F_declContext.class,0);
		}
		public AlgoContext algo() {
			return getRuleContext(AlgoContext.class,0);
		}
		public PContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_p; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterP(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitP(this);
		}
	}

	public final PContext p() throws RecognitionException {
		PContext _localctx = new PContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_p);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			v_decl();
			setState(36);
			match(COLON);
			setState(37);
			f_decl();
			setState(38);
			match(COLON);
			setState(39);
			algo();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class V_declContext extends ParserRuleContext {
		public TerminalNode USER_ID() { return getToken(SPLParser.USER_ID, 0); }
		public V_declContext v_decl() {
			return getRuleContext(V_declContext.class,0);
		}
		public V_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_v_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterV_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitV_decl(this);
		}
	}

	public final V_declContext v_decl() throws RecognitionException {
		V_declContext _localctx = new V_declContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_v_decl);
		try {
			setState(44);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case USER_ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(41);
				match(USER_ID);
				setState(42);
				v_decl();
				}
				break;
			case RIGHT_PAREN:
			case COLON:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class F_declContext extends ParserRuleContext {
		public F_typeContext f_type() {
			return getRuleContext(F_typeContext.class,0);
		}
		public F_declContext f_decl() {
			return getRuleContext(F_declContext.class,0);
		}
		public F_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_f_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterF_decl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitF_decl(this);
		}
	}

	public final F_declContext f_decl() throws RecognitionException {
		F_declContext _localctx = new F_declContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_f_decl);
		try {
			setState(50);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID:
			case NUM:
				enterOuterAlt(_localctx, 1);
				{
				setState(46);
				f_type();
				setState(47);
				f_decl();
				}
				break;
			case COLON:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class F_typeContext extends ParserRuleContext {
		public TerminalNode VOID() { return getToken(SPLParser.VOID, 0); }
		public TerminalNode USER_ID() { return getToken(SPLParser.USER_ID, 0); }
		public List<TerminalNode> LEFT_PAREN() { return getTokens(SPLParser.LEFT_PAREN); }
		public TerminalNode LEFT_PAREN(int i) {
			return getToken(SPLParser.LEFT_PAREN, i);
		}
		public V_declContext v_decl() {
			return getRuleContext(V_declContext.class,0);
		}
		public List<TerminalNode> RIGHT_PAREN() { return getTokens(SPLParser.RIGHT_PAREN); }
		public TerminalNode RIGHT_PAREN(int i) {
			return getToken(SPLParser.RIGHT_PAREN, i);
		}
		public TerminalNode LEFT_BRACE() { return getToken(SPLParser.LEFT_BRACE, 0); }
		public PContext p() {
			return getRuleContext(PContext.class,0);
		}
		public TerminalNode RETURN() { return getToken(SPLParser.RETURN, 0); }
		public TerminalNode RIGHT_BRACE() { return getToken(SPLParser.RIGHT_BRACE, 0); }
		public TerminalNode NUM() { return getToken(SPLParser.NUM, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public F_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_f_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterF_type(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitF_type(this);
		}
	}

	public final F_typeContext f_type() throws RecognitionException {
		F_typeContext _localctx = new F_typeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_f_type);
		try {
			setState(75);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID:
				enterOuterAlt(_localctx, 1);
				{
				setState(52);
				match(VOID);
				setState(53);
				match(USER_ID);
				setState(54);
				match(LEFT_PAREN);
				setState(55);
				v_decl();
				setState(56);
				match(RIGHT_PAREN);
				setState(57);
				match(LEFT_BRACE);
				setState(58);
				p();
				setState(59);
				match(RETURN);
				setState(60);
				match(RIGHT_BRACE);
				}
				break;
			case NUM:
				enterOuterAlt(_localctx, 2);
				{
				setState(62);
				match(NUM);
				setState(63);
				match(USER_ID);
				setState(64);
				match(LEFT_PAREN);
				setState(65);
				v_decl();
				setState(66);
				match(RIGHT_PAREN);
				setState(67);
				match(LEFT_BRACE);
				setState(68);
				p();
				setState(69);
				match(RETURN);
				setState(70);
				match(LEFT_PAREN);
				setState(71);
				term();
				setState(72);
				match(RIGHT_PAREN);
				setState(73);
				match(RIGHT_BRACE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AlgoContext extends ParserRuleContext {
		public List<InstrContext> instr() {
			return getRuleContexts(InstrContext.class);
		}
		public InstrContext instr(int i) {
			return getRuleContext(InstrContext.class,i);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(SPLParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(SPLParser.SEMICOLON, i);
		}
		public AlgoContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_algo; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterAlgo(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitAlgo(this);
		}
	}

	public final AlgoContext algo() throws RecognitionException {
		AlgoContext _localctx = new AlgoContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_algo);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 33561840L) != 0)) {
				{
				{
				setState(77);
				instr();
				setState(78);
				match(SEMICOLON);
				}
				}
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InstrContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(SPLParser.PRINT, 0); }
		public OutpContext outp() {
			return getRuleContext(OutpContext.class,0);
		}
		public TerminalNode NOP() { return getToken(SPLParser.NOP, 0); }
		public TerminalNode COMMENT() { return getToken(SPLParser.COMMENT, 0); }
		public TerminalNode STRING() { return getToken(SPLParser.STRING, 0); }
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public CallContext call() {
			return getRuleContext(CallContext.class,0);
		}
		public BranchContext branch() {
			return getRuleContext(BranchContext.class,0);
		}
		public LoopContext loop() {
			return getRuleContext(LoopContext.class,0);
		}
		public InstrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterInstr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitInstr(this);
		}
	}

	public final InstrContext instr() throws RecognitionException {
		InstrContext _localctx = new InstrContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_instr);
		try {
			setState(94);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(85);
				match(PRINT);
				setState(86);
				outp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(87);
				match(NOP);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(88);
				match(COMMENT);
				setState(89);
				match(STRING);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(90);
				assign();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(91);
				call();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(92);
				branch();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(93);
				loop();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class OutpContext extends ParserRuleContext {
		public TerminalNode LEFT_PAREN() { return getToken(SPLParser.LEFT_PAREN, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(SPLParser.RIGHT_PAREN, 0); }
		public TerminalNode STRING() { return getToken(SPLParser.STRING, 0); }
		public OutpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_outp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterOutp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitOutp(this);
		}
	}

	public final OutpContext outp() throws RecognitionException {
		OutpContext _localctx = new OutpContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_outp);
		try {
			setState(101);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LEFT_PAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(96);
				match(LEFT_PAREN);
				setState(97);
				term();
				setState(98);
				match(RIGHT_PAREN);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(100);
				match(STRING);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CallContext extends ParserRuleContext {
		public TerminalNode USER_ID() { return getToken(SPLParser.USER_ID, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(SPLParser.LEFT_PAREN, 0); }
		public InputContext input() {
			return getRuleContext(InputContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(SPLParser.RIGHT_PAREN, 0); }
		public CallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_call; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitCall(this);
		}
	}

	public final CallContext call() throws RecognitionException {
		CallContext _localctx = new CallContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_call);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(103);
			match(USER_ID);
			setState(104);
			match(LEFT_PAREN);
			setState(105);
			input();
			setState(106);
			match(RIGHT_PAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InputContext extends ParserRuleContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public InputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_input; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterInput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitInput(this);
		}
	}

	public final InputContext input() throws RecognitionException {
		InputContext _localctx = new InputContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_input);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 101179392L) != 0)) {
				{
				{
				setState(108);
				term();
				}
				}
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignContext extends ParserRuleContext {
		public TerminalNode USER_ID() { return getToken(SPLParser.USER_ID, 0); }
		public TerminalNode ASSIGN_OP() { return getToken(SPLParser.ASSIGN_OP, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public AssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitAssign(this);
		}
	}

	public final AssignContext assign() throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(USER_ID);
			setState(115);
			match(ASSIGN_OP);
			setState(116);
			term();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ParserRuleContext {
		public TerminalNode USER_ID() { return getToken(SPLParser.USER_ID, 0); }
		public TerminalNode NUM_LIT() { return getToken(SPLParser.NUM_LIT, 0); }
		public CallContext call() {
			return getRuleContext(CallContext.class,0);
		}
		public TerminalNode MOD() { return getToken(SPLParser.MOD, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(SPLParser.LEFT_PAREN, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(SPLParser.RIGHT_PAREN, 0); }
		public TerminalNode ADD() { return getToken(SPLParser.ADD, 0); }
		public TerminalNode SUB() { return getToken(SPLParser.SUB, 0); }
		public TerminalNode MUL() { return getToken(SPLParser.MUL, 0); }
		public TerminalNode DIV() { return getToken(SPLParser.DIV, 0); }
		public TerminalNode NEG() { return getToken(SPLParser.NEG, 0); }
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitTerm(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_term);
		try {
			setState(156);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(118);
				match(USER_ID);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(119);
				match(NUM_LIT);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(120);
				call();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(121);
				match(MOD);
				setState(122);
				match(LEFT_PAREN);
				setState(123);
				term();
				setState(124);
				term();
				setState(125);
				match(RIGHT_PAREN);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(127);
				match(ADD);
				setState(128);
				match(LEFT_PAREN);
				setState(129);
				term();
				setState(130);
				term();
				setState(131);
				match(RIGHT_PAREN);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(133);
				match(SUB);
				setState(134);
				match(LEFT_PAREN);
				setState(135);
				term();
				setState(136);
				term();
				setState(137);
				match(RIGHT_PAREN);
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(139);
				match(MUL);
				setState(140);
				match(LEFT_PAREN);
				setState(141);
				term();
				setState(142);
				term();
				setState(143);
				match(RIGHT_PAREN);
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(145);
				match(DIV);
				setState(146);
				match(LEFT_PAREN);
				setState(147);
				term();
				setState(148);
				term();
				setState(149);
				match(RIGHT_PAREN);
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(151);
				match(NEG);
				setState(152);
				match(LEFT_PAREN);
				setState(153);
				term();
				setState(154);
				match(RIGHT_PAREN);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BranchContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(SPLParser.IF, 0); }
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public TerminalNode THEN() { return getToken(SPLParser.THEN, 0); }
		public List<TerminalNode> LEFT_BRACE() { return getTokens(SPLParser.LEFT_BRACE); }
		public TerminalNode LEFT_BRACE(int i) {
			return getToken(SPLParser.LEFT_BRACE, i);
		}
		public List<AlgoContext> algo() {
			return getRuleContexts(AlgoContext.class);
		}
		public AlgoContext algo(int i) {
			return getRuleContext(AlgoContext.class,i);
		}
		public List<TerminalNode> RIGHT_BRACE() { return getTokens(SPLParser.RIGHT_BRACE); }
		public TerminalNode RIGHT_BRACE(int i) {
			return getToken(SPLParser.RIGHT_BRACE, i);
		}
		public TerminalNode ELSE() { return getToken(SPLParser.ELSE, 0); }
		public BranchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_branch; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterBranch(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitBranch(this);
		}
	}

	public final BranchContext branch() throws RecognitionException {
		BranchContext _localctx = new BranchContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_branch);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			match(IF);
			setState(159);
			bool();
			setState(160);
			match(THEN);
			setState(161);
			match(LEFT_BRACE);
			setState(162);
			algo();
			setState(163);
			match(RIGHT_BRACE);
			setState(164);
			match(ELSE);
			setState(165);
			match(LEFT_BRACE);
			setState(166);
			algo();
			setState(167);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BoolContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(SPLParser.NOT, 0); }
		public TerminalNode LEFT_PAREN() { return getToken(SPLParser.LEFT_PAREN, 0); }
		public List<BoolContext> bool() {
			return getRuleContexts(BoolContext.class);
		}
		public BoolContext bool(int i) {
			return getRuleContext(BoolContext.class,i);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(SPLParser.RIGHT_PAREN, 0); }
		public TerminalNode AND() { return getToken(SPLParser.AND, 0); }
		public TerminalNode OR() { return getToken(SPLParser.OR, 0); }
		public TerminalNode EQ() { return getToken(SPLParser.EQ, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public TerminalNode LARGER() { return getToken(SPLParser.LARGER, 0); }
		public TerminalNode LESSER() { return getToken(SPLParser.LESSER, 0); }
		public BoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitBool(this);
		}
	}

	public final BoolContext bool() throws RecognitionException {
		BoolContext _localctx = new BoolContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_bool);
		try {
			setState(204);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOT:
				enterOuterAlt(_localctx, 1);
				{
				setState(169);
				match(NOT);
				setState(170);
				match(LEFT_PAREN);
				setState(171);
				bool();
				setState(172);
				match(RIGHT_PAREN);
				}
				break;
			case AND:
				enterOuterAlt(_localctx, 2);
				{
				setState(174);
				match(AND);
				setState(175);
				match(LEFT_PAREN);
				setState(176);
				bool();
				setState(177);
				bool();
				setState(178);
				match(RIGHT_PAREN);
				}
				break;
			case OR:
				enterOuterAlt(_localctx, 3);
				{
				setState(180);
				match(OR);
				setState(181);
				match(LEFT_PAREN);
				setState(182);
				bool();
				setState(183);
				bool();
				setState(184);
				match(RIGHT_PAREN);
				}
				break;
			case EQ:
				enterOuterAlt(_localctx, 4);
				{
				setState(186);
				match(EQ);
				setState(187);
				match(LEFT_PAREN);
				setState(188);
				term();
				setState(189);
				term();
				setState(190);
				match(RIGHT_PAREN);
				}
				break;
			case LARGER:
				enterOuterAlt(_localctx, 5);
				{
				setState(192);
				match(LARGER);
				setState(193);
				match(LEFT_PAREN);
				setState(194);
				term();
				setState(195);
				term();
				setState(196);
				match(RIGHT_PAREN);
				}
				break;
			case LESSER:
				enterOuterAlt(_localctx, 6);
				{
				setState(198);
				match(LESSER);
				setState(199);
				match(LEFT_PAREN);
				setState(200);
				term();
				setState(201);
				term();
				setState(202);
				match(RIGHT_PAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LoopContext extends ParserRuleContext {
		public CondContext cond() {
			return getRuleContext(CondContext.class,0);
		}
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public TerminalNode DO() { return getToken(SPLParser.DO, 0); }
		public TerminalNode LEFT_BRACE() { return getToken(SPLParser.LEFT_BRACE, 0); }
		public AlgoContext algo() {
			return getRuleContext(AlgoContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(SPLParser.RIGHT_BRACE, 0); }
		public LoopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loop; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterLoop(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitLoop(this);
		}
	}

	public final LoopContext loop() throws RecognitionException {
		LoopContext _localctx = new LoopContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_loop);
		try {
			setState(220);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WHILE:
			case UNTIL:
				enterOuterAlt(_localctx, 1);
				{
				setState(206);
				cond();
				setState(207);
				bool();
				setState(208);
				match(DO);
				setState(209);
				match(LEFT_BRACE);
				setState(210);
				algo();
				setState(211);
				match(RIGHT_BRACE);
				}
				break;
			case DO:
				enterOuterAlt(_localctx, 2);
				{
				setState(213);
				match(DO);
				setState(214);
				match(LEFT_BRACE);
				setState(215);
				algo();
				setState(216);
				match(RIGHT_BRACE);
				setState(217);
				cond();
				setState(218);
				bool();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CondContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(SPLParser.WHILE, 0); }
		public TerminalNode UNTIL() { return getToken(SPLParser.UNTIL, 0); }
		public CondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cond; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).enterCond(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SPLListener ) ((SPLListener)listener).exitCond(this);
		}
	}

	public final CondContext cond() throws RecognitionException {
		CondContext _localctx = new CondContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_cond);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			_la = _input.LA(1);
			if ( !(_la==WHILE || _la==UNTIL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001#\u00e1\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0003\u0002-\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003"+
		"\u0003\u00033\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0003\u0004L\b\u0004\u0001\u0005\u0001\u0005\u0001\u0005"+
		"\u0005\u0005Q\b\u0005\n\u0005\f\u0005T\t\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0003\u0006_\b\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0003\u0007f\b\u0007\u0001\b\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\t\u0005\tn\b\t\n\t\f\tq\t\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u009d\b\u000b\u0001\f"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0003\r\u00cd\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0003\u000e\u00dd\b\u000e\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0000\u0000\u0010\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e\u0000\u0001"+
		"\u0001\u0000\u000b\f\u00ea\u0000 \u0001\u0000\u0000\u0000\u0002#\u0001"+
		"\u0000\u0000\u0000\u0004,\u0001\u0000\u0000\u0000\u00062\u0001\u0000\u0000"+
		"\u0000\bK\u0001\u0000\u0000\u0000\nR\u0001\u0000\u0000\u0000\f^\u0001"+
		"\u0000\u0000\u0000\u000ee\u0001\u0000\u0000\u0000\u0010g\u0001\u0000\u0000"+
		"\u0000\u0012o\u0001\u0000\u0000\u0000\u0014r\u0001\u0000\u0000\u0000\u0016"+
		"\u009c\u0001\u0000\u0000\u0000\u0018\u009e\u0001\u0000\u0000\u0000\u001a"+
		"\u00cc\u0001\u0000\u0000\u0000\u001c\u00dc\u0001\u0000\u0000\u0000\u001e"+
		"\u00de\u0001\u0000\u0000\u0000 !\u0003\u0002\u0001\u0000!\"\u0005\u0000"+
		"\u0000\u0001\"\u0001\u0001\u0000\u0000\u0000#$\u0003\u0004\u0002\u0000"+
		"$%\u0005 \u0000\u0000%&\u0003\u0006\u0003\u0000&\'\u0005 \u0000\u0000"+
		"\'(\u0003\n\u0005\u0000(\u0003\u0001\u0000\u0000\u0000)*\u0005\u0019\u0000"+
		"\u0000*-\u0003\u0004\u0002\u0000+-\u0001\u0000\u0000\u0000,)\u0001\u0000"+
		"\u0000\u0000,+\u0001\u0000\u0000\u0000-\u0005\u0001\u0000\u0000\u0000"+
		"./\u0003\b\u0004\u0000/0\u0003\u0006\u0003\u000003\u0001\u0000\u0000\u0000"+
		"13\u0001\u0000\u0000\u00002.\u0001\u0000\u0000\u000021\u0001\u0000\u0000"+
		"\u00003\u0007\u0001\u0000\u0000\u000045\u0005\u0001\u0000\u000056\u0005"+
		"\u0019\u0000\u000067\u0005\u001c\u0000\u000078\u0003\u0004\u0002\u0000"+
		"89\u0005\u001d\u0000\u00009:\u0005\u001e\u0000\u0000:;\u0003\u0002\u0001"+
		"\u0000;<\u0005\u0003\u0000\u0000<=\u0005\u001f\u0000\u0000=L\u0001\u0000"+
		"\u0000\u0000>?\u0005\u0002\u0000\u0000?@\u0005\u0019\u0000\u0000@A\u0005"+
		"\u001c\u0000\u0000AB\u0003\u0004\u0002\u0000BC\u0005\u001d\u0000\u0000"+
		"CD\u0005\u001e\u0000\u0000DE\u0003\u0002\u0001\u0000EF\u0005\u0003\u0000"+
		"\u0000FG\u0005\u001c\u0000\u0000GH\u0003\u0016\u000b\u0000HI\u0005\u001d"+
		"\u0000\u0000IJ\u0005\u001f\u0000\u0000JL\u0001\u0000\u0000\u0000K4\u0001"+
		"\u0000\u0000\u0000K>\u0001\u0000\u0000\u0000L\t\u0001\u0000\u0000\u0000"+
		"MN\u0003\f\u0006\u0000NO\u0005!\u0000\u0000OQ\u0001\u0000\u0000\u0000"+
		"PM\u0001\u0000\u0000\u0000QT\u0001\u0000\u0000\u0000RP\u0001\u0000\u0000"+
		"\u0000RS\u0001\u0000\u0000\u0000S\u000b\u0001\u0000\u0000\u0000TR\u0001"+
		"\u0000\u0000\u0000UV\u0005\u0004\u0000\u0000V_\u0003\u000e\u0007\u0000"+
		"W_\u0005\u0005\u0000\u0000XY\u0005\u0006\u0000\u0000Y_\u0005\u001b\u0000"+
		"\u0000Z_\u0003\u0014\n\u0000[_\u0003\u0010\b\u0000\\_\u0003\u0018\f\u0000"+
		"]_\u0003\u001c\u000e\u0000^U\u0001\u0000\u0000\u0000^W\u0001\u0000\u0000"+
		"\u0000^X\u0001\u0000\u0000\u0000^Z\u0001\u0000\u0000\u0000^[\u0001\u0000"+
		"\u0000\u0000^\\\u0001\u0000\u0000\u0000^]\u0001\u0000\u0000\u0000_\r\u0001"+
		"\u0000\u0000\u0000`a\u0005\u001c\u0000\u0000ab\u0003\u0016\u000b\u0000"+
		"bc\u0005\u001d\u0000\u0000cf\u0001\u0000\u0000\u0000df\u0005\u001b\u0000"+
		"\u0000e`\u0001\u0000\u0000\u0000ed\u0001\u0000\u0000\u0000f\u000f\u0001"+
		"\u0000\u0000\u0000gh\u0005\u0019\u0000\u0000hi\u0005\u001c\u0000\u0000"+
		"ij\u0003\u0012\t\u0000jk\u0005\u001d\u0000\u0000k\u0011\u0001\u0000\u0000"+
		"\u0000ln\u0003\u0016\u000b\u0000ml\u0001\u0000\u0000\u0000nq\u0001\u0000"+
		"\u0000\u0000om\u0001\u0000\u0000\u0000op\u0001\u0000\u0000\u0000p\u0013"+
		"\u0001\u0000\u0000\u0000qo\u0001\u0000\u0000\u0000rs\u0005\u0019\u0000"+
		"\u0000st\u0005\"\u0000\u0000tu\u0003\u0016\u000b\u0000u\u0015\u0001\u0000"+
		"\u0000\u0000v\u009d\u0005\u0019\u0000\u0000w\u009d\u0005\u001a\u0000\u0000"+
		"x\u009d\u0003\u0010\b\u0000yz\u0005\r\u0000\u0000z{\u0005\u001c\u0000"+
		"\u0000{|\u0003\u0016\u000b\u0000|}\u0003\u0016\u000b\u0000}~\u0005\u001d"+
		"\u0000\u0000~\u009d\u0001\u0000\u0000\u0000\u007f\u0080\u0005\u000e\u0000"+
		"\u0000\u0080\u0081\u0005\u001c\u0000\u0000\u0081\u0082\u0003\u0016\u000b"+
		"\u0000\u0082\u0083\u0003\u0016\u000b\u0000\u0083\u0084\u0005\u001d\u0000"+
		"\u0000\u0084\u009d\u0001\u0000\u0000\u0000\u0085\u0086\u0005\u000f\u0000"+
		"\u0000\u0086\u0087\u0005\u001c\u0000\u0000\u0087\u0088\u0003\u0016\u000b"+
		"\u0000\u0088\u0089\u0003\u0016\u000b\u0000\u0089\u008a\u0005\u001d\u0000"+
		"\u0000\u008a\u009d\u0001\u0000\u0000\u0000\u008b\u008c\u0005\u0010\u0000"+
		"\u0000\u008c\u008d\u0005\u001c\u0000\u0000\u008d\u008e\u0003\u0016\u000b"+
		"\u0000\u008e\u008f\u0003\u0016\u000b\u0000\u008f\u0090\u0005\u001d\u0000"+
		"\u0000\u0090\u009d\u0001\u0000\u0000\u0000\u0091\u0092\u0005\u0011\u0000"+
		"\u0000\u0092\u0093\u0005\u001c\u0000\u0000\u0093\u0094\u0003\u0016\u000b"+
		"\u0000\u0094\u0095\u0003\u0016\u000b\u0000\u0095\u0096\u0005\u001d\u0000"+
		"\u0000\u0096\u009d\u0001\u0000\u0000\u0000\u0097\u0098\u0005\u0012\u0000"+
		"\u0000\u0098\u0099\u0005\u001c\u0000\u0000\u0099\u009a\u0003\u0016\u000b"+
		"\u0000\u009a\u009b\u0005\u001d\u0000\u0000\u009b\u009d\u0001\u0000\u0000"+
		"\u0000\u009cv\u0001\u0000\u0000\u0000\u009cw\u0001\u0000\u0000\u0000\u009c"+
		"x\u0001\u0000\u0000\u0000\u009cy\u0001\u0000\u0000\u0000\u009c\u007f\u0001"+
		"\u0000\u0000\u0000\u009c\u0085\u0001\u0000\u0000\u0000\u009c\u008b\u0001"+
		"\u0000\u0000\u0000\u009c\u0091\u0001\u0000\u0000\u0000\u009c\u0097\u0001"+
		"\u0000\u0000\u0000\u009d\u0017\u0001\u0000\u0000\u0000\u009e\u009f\u0005"+
		"\u0007\u0000\u0000\u009f\u00a0\u0003\u001a\r\u0000\u00a0\u00a1\u0005\b"+
		"\u0000\u0000\u00a1\u00a2\u0005\u001e\u0000\u0000\u00a2\u00a3\u0003\n\u0005"+
		"\u0000\u00a3\u00a4\u0005\u001f\u0000\u0000\u00a4\u00a5\u0005\t\u0000\u0000"+
		"\u00a5\u00a6\u0005\u001e\u0000\u0000\u00a6\u00a7\u0003\n\u0005\u0000\u00a7"+
		"\u00a8\u0005\u001f\u0000\u0000\u00a8\u0019\u0001\u0000\u0000\u0000\u00a9"+
		"\u00aa\u0005\u0013\u0000\u0000\u00aa\u00ab\u0005\u001c\u0000\u0000\u00ab"+
		"\u00ac\u0003\u001a\r\u0000\u00ac\u00ad\u0005\u001d\u0000\u0000\u00ad\u00cd"+
		"\u0001\u0000\u0000\u0000\u00ae\u00af\u0005\u0014\u0000\u0000\u00af\u00b0"+
		"\u0005\u001c\u0000\u0000\u00b0\u00b1\u0003\u001a\r\u0000\u00b1\u00b2\u0003"+
		"\u001a\r\u0000\u00b2\u00b3\u0005\u001d\u0000\u0000\u00b3\u00cd\u0001\u0000"+
		"\u0000\u0000\u00b4\u00b5\u0005\u0015\u0000\u0000\u00b5\u00b6\u0005\u001c"+
		"\u0000\u0000\u00b6\u00b7\u0003\u001a\r\u0000\u00b7\u00b8\u0003\u001a\r"+
		"\u0000\u00b8\u00b9\u0005\u001d\u0000\u0000\u00b9\u00cd\u0001\u0000\u0000"+
		"\u0000\u00ba\u00bb\u0005\u0016\u0000\u0000\u00bb\u00bc\u0005\u001c\u0000"+
		"\u0000\u00bc\u00bd\u0003\u0016\u000b\u0000\u00bd\u00be\u0003\u0016\u000b"+
		"\u0000\u00be\u00bf\u0005\u001d\u0000\u0000\u00bf\u00cd\u0001\u0000\u0000"+
		"\u0000\u00c0\u00c1\u0005\u0017\u0000\u0000\u00c1\u00c2\u0005\u001c\u0000"+
		"\u0000\u00c2\u00c3\u0003\u0016\u000b\u0000\u00c3\u00c4\u0003\u0016\u000b"+
		"\u0000\u00c4\u00c5\u0005\u001d\u0000\u0000\u00c5\u00cd\u0001\u0000\u0000"+
		"\u0000\u00c6\u00c7\u0005\u0018\u0000\u0000\u00c7\u00c8\u0005\u001c\u0000"+
		"\u0000\u00c8\u00c9\u0003\u0016\u000b\u0000\u00c9\u00ca\u0003\u0016\u000b"+
		"\u0000\u00ca\u00cb\u0005\u001d\u0000\u0000\u00cb\u00cd\u0001\u0000\u0000"+
		"\u0000\u00cc\u00a9\u0001\u0000\u0000\u0000\u00cc\u00ae\u0001\u0000\u0000"+
		"\u0000\u00cc\u00b4\u0001\u0000\u0000\u0000\u00cc\u00ba\u0001\u0000\u0000"+
		"\u0000\u00cc\u00c0\u0001\u0000\u0000\u0000\u00cc\u00c6\u0001\u0000\u0000"+
		"\u0000\u00cd\u001b\u0001\u0000\u0000\u0000\u00ce\u00cf\u0003\u001e\u000f"+
		"\u0000\u00cf\u00d0\u0003\u001a\r\u0000\u00d0\u00d1\u0005\n\u0000\u0000"+
		"\u00d1\u00d2\u0005\u001e\u0000\u0000\u00d2\u00d3\u0003\n\u0005\u0000\u00d3"+
		"\u00d4\u0005\u001f\u0000\u0000\u00d4\u00dd\u0001\u0000\u0000\u0000\u00d5"+
		"\u00d6\u0005\n\u0000\u0000\u00d6\u00d7\u0005\u001e\u0000\u0000\u00d7\u00d8"+
		"\u0003\n\u0005\u0000\u00d8\u00d9\u0005\u001f\u0000\u0000\u00d9\u00da\u0003"+
		"\u001e\u000f\u0000\u00da\u00db\u0003\u001a\r\u0000\u00db\u00dd\u0001\u0000"+
		"\u0000\u0000\u00dc\u00ce\u0001\u0000\u0000\u0000\u00dc\u00d5\u0001\u0000"+
		"\u0000\u0000\u00dd\u001d\u0001\u0000\u0000\u0000\u00de\u00df\u0007\u0000"+
		"\u0000\u0000\u00df\u001f\u0001\u0000\u0000\u0000\n,2KR^eo\u009c\u00cc"+
		"\u00dc";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}