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
		COLON=32, SEMICOLON=33, ASSIGN=34, DOLLAR=35, WS=36;
	public static final int
		RULE_spl_prog = 0, RULE_p = 1, RULE_v_decl = 2, RULE_f_decl = 3, RULE_f_type = 4, 
		RULE_algo = 5, RULE_instr = 6, RULE_outp = 7, RULE_term = 8, RULE_branch = 9, 
		RULE_loop = 10, RULE_bool = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"spl_prog", "p", "v_decl", "f_decl", "f_type", "algo", "instr", "outp", 
			"term", "branch", "loop", "bool"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'void'", "'num'", "'return'", "'print'", "'nop'", "'comment'", 
			"'if'", "'then'", "'else'", "'do'", "'while'", "'until'", "'mod'", "'add'", 
			"'sub'", "'mul'", "'div'", "'neg'", "'not'", "'and'", "'or'", "'eq'", 
			"'larger'", "'lesser'", null, null, null, "'('", "')'", "'{'", "'}'", 
			"':'", "';'", "'='", "'$'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "VOID", "NUM", "RETURN", "PRINT", "NOP", "COMMENT", "IF", "THEN", 
			"ELSE", "DO", "WHILE", "UNTIL", "MOD", "ADD", "SUB", "MUL", "DIV", "NEG", 
			"NOT", "AND", "OR", "EQ", "LARGER", "LESSER", "USER_ID", "NUM_LIT", "STRING", 
			"LEFT_PAREN", "RIGHT_PAREN", "LEFT_BRACE", "RIGHT_BRACE", "COLON", "SEMICOLON", 
			"ASSIGN", "DOLLAR", "WS"
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
			setState(24);
			p();
			setState(25);
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
			setState(27);
			v_decl();
			setState(28);
			match(COLON);
			setState(29);
			f_decl();
			setState(30);
			match(COLON);
			setState(31);
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
			setState(36);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case USER_ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(33);
				match(USER_ID);
				setState(34);
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
			setState(42);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID:
			case NUM:
				enterOuterAlt(_localctx, 1);
				{
				setState(38);
				f_type();
				setState(39);
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
		public TerminalNode LEFT_PAREN() { return getToken(SPLParser.LEFT_PAREN, 0); }
		public V_declContext v_decl() {
			return getRuleContext(V_declContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(SPLParser.RIGHT_PAREN, 0); }
		public TerminalNode LEFT_BRACE() { return getToken(SPLParser.LEFT_BRACE, 0); }
		public PContext p() {
			return getRuleContext(PContext.class,0);
		}
		public TerminalNode RETURN() { return getToken(SPLParser.RETURN, 0); }
		public TerminalNode RIGHT_BRACE() { return getToken(SPLParser.RIGHT_BRACE, 0); }
		public TerminalNode NUM() { return getToken(SPLParser.NUM, 0); }
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
			setState(64);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VOID:
				enterOuterAlt(_localctx, 1);
				{
				setState(44);
				match(VOID);
				setState(45);
				match(USER_ID);
				setState(46);
				match(LEFT_PAREN);
				setState(47);
				v_decl();
				setState(48);
				match(RIGHT_PAREN);
				setState(49);
				match(LEFT_BRACE);
				setState(50);
				p();
				setState(51);
				match(RETURN);
				setState(52);
				match(RIGHT_BRACE);
				}
				break;
			case NUM:
				enterOuterAlt(_localctx, 2);
				{
				setState(54);
				match(NUM);
				setState(55);
				match(USER_ID);
				setState(56);
				match(LEFT_PAREN);
				setState(57);
				v_decl();
				setState(58);
				match(RIGHT_PAREN);
				setState(59);
				match(LEFT_BRACE);
				setState(60);
				p();
				setState(61);
				match(RETURN);
				setState(62);
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
		try {
			int _alt;
			setState(73);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(69);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(66);
						instr();
						}
						} 
					}
					setState(71);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
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
	public static class InstrContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(SPLParser.PRINT, 0); }
		public OutpContext outp() {
			return getRuleContext(OutpContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(SPLParser.SEMICOLON, 0); }
		public TerminalNode NOP() { return getToken(SPLParser.NOP, 0); }
		public TerminalNode COMMENT() { return getToken(SPLParser.COMMENT, 0); }
		public TerminalNode USER_ID() { return getToken(SPLParser.USER_ID, 0); }
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
			setState(87);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PRINT:
				enterOuterAlt(_localctx, 1);
				{
				setState(75);
				match(PRINT);
				setState(76);
				outp();
				setState(77);
				match(SEMICOLON);
				}
				break;
			case NOP:
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				match(NOP);
				setState(80);
				match(SEMICOLON);
				}
				break;
			case COMMENT:
				enterOuterAlt(_localctx, 3);
				{
				setState(81);
				match(COMMENT);
				setState(82);
				match(SEMICOLON);
				}
				break;
			case USER_ID:
				enterOuterAlt(_localctx, 4);
				{
				setState(83);
				match(USER_ID);
				setState(84);
				match(SEMICOLON);
				}
				break;
			case IF:
				enterOuterAlt(_localctx, 5);
				{
				setState(85);
				branch();
				}
				break;
			case DO:
			case WHILE:
			case UNTIL:
				enterOuterAlt(_localctx, 6);
				{
				setState(86);
				loop();
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
	public static class OutpContext extends ParserRuleContext {
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
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
			setState(91);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case MOD:
			case ADD:
			case SUB:
			case MUL:
			case DIV:
			case NEG:
			case USER_ID:
			case NUM_LIT:
				enterOuterAlt(_localctx, 1);
				{
				setState(89);
				term();
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 2);
				{
				setState(90);
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
	public static class TermContext extends ParserRuleContext {
		public TerminalNode USER_ID() { return getToken(SPLParser.USER_ID, 0); }
		public TerminalNode NUM_LIT() { return getToken(SPLParser.NUM_LIT, 0); }
		public TerminalNode MOD() { return getToken(SPLParser.MOD, 0); }
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
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
		enterRule(_localctx, 16, RULE_term);
		try {
			setState(107);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case USER_ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(93);
				match(USER_ID);
				}
				break;
			case NUM_LIT:
				enterOuterAlt(_localctx, 2);
				{
				setState(94);
				match(NUM_LIT);
				}
				break;
			case MOD:
				enterOuterAlt(_localctx, 3);
				{
				setState(95);
				match(MOD);
				setState(96);
				term();
				}
				break;
			case ADD:
				enterOuterAlt(_localctx, 4);
				{
				setState(97);
				match(ADD);
				setState(98);
				term();
				}
				break;
			case SUB:
				enterOuterAlt(_localctx, 5);
				{
				setState(99);
				match(SUB);
				setState(100);
				term();
				}
				break;
			case MUL:
				enterOuterAlt(_localctx, 6);
				{
				setState(101);
				match(MUL);
				setState(102);
				term();
				}
				break;
			case DIV:
				enterOuterAlt(_localctx, 7);
				{
				setState(103);
				match(DIV);
				setState(104);
				term();
				}
				break;
			case NEG:
				enterOuterAlt(_localctx, 8);
				{
				setState(105);
				match(NEG);
				setState(106);
				term();
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
	public static class BranchContext extends ParserRuleContext {
		public TerminalNode IF() { return getToken(SPLParser.IF, 0); }
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public TerminalNode THEN() { return getToken(SPLParser.THEN, 0); }
		public List<AlgoContext> algo() {
			return getRuleContexts(AlgoContext.class);
		}
		public AlgoContext algo(int i) {
			return getRuleContext(AlgoContext.class,i);
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
		enterRule(_localctx, 18, RULE_branch);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(IF);
			setState(110);
			bool();
			setState(111);
			match(THEN);
			setState(112);
			algo();
			setState(115);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(113);
				match(ELSE);
				setState(114);
				algo();
				}
				break;
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
	public static class LoopContext extends ParserRuleContext {
		public TerminalNode WHILE() { return getToken(SPLParser.WHILE, 0); }
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public TerminalNode DO() { return getToken(SPLParser.DO, 0); }
		public AlgoContext algo() {
			return getRuleContext(AlgoContext.class,0);
		}
		public TerminalNode UNTIL() { return getToken(SPLParser.UNTIL, 0); }
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
		enterRule(_localctx, 20, RULE_loop);
		try {
			setState(132);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case WHILE:
				enterOuterAlt(_localctx, 1);
				{
				setState(117);
				match(WHILE);
				setState(118);
				bool();
				setState(119);
				match(DO);
				setState(120);
				algo();
				}
				break;
			case UNTIL:
				enterOuterAlt(_localctx, 2);
				{
				setState(122);
				match(UNTIL);
				setState(123);
				bool();
				setState(124);
				match(DO);
				setState(125);
				algo();
				}
				break;
			case DO:
				enterOuterAlt(_localctx, 3);
				{
				setState(127);
				match(DO);
				setState(128);
				algo();
				setState(129);
				match(WHILE);
				setState(130);
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
	public static class BoolContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(SPLParser.NOT, 0); }
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
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
		enterRule(_localctx, 22, RULE_bool);
		try {
			setState(152);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOT:
				enterOuterAlt(_localctx, 1);
				{
				setState(134);
				match(NOT);
				setState(135);
				bool();
				}
				break;
			case AND:
				enterOuterAlt(_localctx, 2);
				{
				setState(136);
				match(AND);
				setState(137);
				bool();
				}
				break;
			case OR:
				enterOuterAlt(_localctx, 3);
				{
				setState(138);
				match(OR);
				setState(139);
				bool();
				}
				break;
			case EQ:
				enterOuterAlt(_localctx, 4);
				{
				setState(140);
				match(EQ);
				setState(141);
				term();
				setState(142);
				term();
				}
				break;
			case LARGER:
				enterOuterAlt(_localctx, 5);
				{
				setState(144);
				match(LARGER);
				setState(145);
				term();
				setState(146);
				term();
				}
				break;
			case LESSER:
				enterOuterAlt(_localctx, 6);
				{
				setState(148);
				match(LESSER);
				setState(149);
				term();
				setState(150);
				term();
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

	public static final String _serializedATN =
		"\u0004\u0001$\u009b\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0001"+
		"\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0003"+
		"\u0002%\b\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0003"+
		"\u0003+\b\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u0004A\b\u0004\u0001"+
		"\u0005\u0005\u0005D\b\u0005\n\u0005\f\u0005G\t\u0005\u0001\u0005\u0003"+
		"\u0005J\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0003\u0006X\b\u0006\u0001\u0007\u0001\u0007\u0003"+
		"\u0007\\\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\bl\b"+
		"\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\tt\b\t\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\n\u0085\b\n\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b"+
		"\u0099\b\u000b\u0001\u000b\u0000\u0000\f\u0000\u0002\u0004\u0006\b\n\f"+
		"\u000e\u0010\u0012\u0014\u0016\u0000\u0000\u00a8\u0000\u0018\u0001\u0000"+
		"\u0000\u0000\u0002\u001b\u0001\u0000\u0000\u0000\u0004$\u0001\u0000\u0000"+
		"\u0000\u0006*\u0001\u0000\u0000\u0000\b@\u0001\u0000\u0000\u0000\nI\u0001"+
		"\u0000\u0000\u0000\fW\u0001\u0000\u0000\u0000\u000e[\u0001\u0000\u0000"+
		"\u0000\u0010k\u0001\u0000\u0000\u0000\u0012m\u0001\u0000\u0000\u0000\u0014"+
		"\u0084\u0001\u0000\u0000\u0000\u0016\u0098\u0001\u0000\u0000\u0000\u0018"+
		"\u0019\u0003\u0002\u0001\u0000\u0019\u001a\u0005\u0000\u0000\u0001\u001a"+
		"\u0001\u0001\u0000\u0000\u0000\u001b\u001c\u0003\u0004\u0002\u0000\u001c"+
		"\u001d\u0005 \u0000\u0000\u001d\u001e\u0003\u0006\u0003\u0000\u001e\u001f"+
		"\u0005 \u0000\u0000\u001f \u0003\n\u0005\u0000 \u0003\u0001\u0000\u0000"+
		"\u0000!\"\u0005\u0019\u0000\u0000\"%\u0003\u0004\u0002\u0000#%\u0001\u0000"+
		"\u0000\u0000$!\u0001\u0000\u0000\u0000$#\u0001\u0000\u0000\u0000%\u0005"+
		"\u0001\u0000\u0000\u0000&\'\u0003\b\u0004\u0000\'(\u0003\u0006\u0003\u0000"+
		"(+\u0001\u0000\u0000\u0000)+\u0001\u0000\u0000\u0000*&\u0001\u0000\u0000"+
		"\u0000*)\u0001\u0000\u0000\u0000+\u0007\u0001\u0000\u0000\u0000,-\u0005"+
		"\u0001\u0000\u0000-.\u0005\u0019\u0000\u0000./\u0005\u001c\u0000\u0000"+
		"/0\u0003\u0004\u0002\u000001\u0005\u001d\u0000\u000012\u0005\u001e\u0000"+
		"\u000023\u0003\u0002\u0001\u000034\u0005\u0003\u0000\u000045\u0005\u001f"+
		"\u0000\u00005A\u0001\u0000\u0000\u000067\u0005\u0002\u0000\u000078\u0005"+
		"\u0019\u0000\u000089\u0005\u001c\u0000\u00009:\u0003\u0004\u0002\u0000"+
		":;\u0005\u001d\u0000\u0000;<\u0005\u001e\u0000\u0000<=\u0003\u0002\u0001"+
		"\u0000=>\u0005\u0003\u0000\u0000>?\u0005\u001f\u0000\u0000?A\u0001\u0000"+
		"\u0000\u0000@,\u0001\u0000\u0000\u0000@6\u0001\u0000\u0000\u0000A\t\u0001"+
		"\u0000\u0000\u0000BD\u0003\f\u0006\u0000CB\u0001\u0000\u0000\u0000DG\u0001"+
		"\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000EF\u0001\u0000\u0000\u0000"+
		"FJ\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000HJ\u0001\u0000\u0000"+
		"\u0000IE\u0001\u0000\u0000\u0000IH\u0001\u0000\u0000\u0000J\u000b\u0001"+
		"\u0000\u0000\u0000KL\u0005\u0004\u0000\u0000LM\u0003\u000e\u0007\u0000"+
		"MN\u0005!\u0000\u0000NX\u0001\u0000\u0000\u0000OP\u0005\u0005\u0000\u0000"+
		"PX\u0005!\u0000\u0000QR\u0005\u0006\u0000\u0000RX\u0005!\u0000\u0000S"+
		"T\u0005\u0019\u0000\u0000TX\u0005!\u0000\u0000UX\u0003\u0012\t\u0000V"+
		"X\u0003\u0014\n\u0000WK\u0001\u0000\u0000\u0000WO\u0001\u0000\u0000\u0000"+
		"WQ\u0001\u0000\u0000\u0000WS\u0001\u0000\u0000\u0000WU\u0001\u0000\u0000"+
		"\u0000WV\u0001\u0000\u0000\u0000X\r\u0001\u0000\u0000\u0000Y\\\u0003\u0010"+
		"\b\u0000Z\\\u0005\u001b\u0000\u0000[Y\u0001\u0000\u0000\u0000[Z\u0001"+
		"\u0000\u0000\u0000\\\u000f\u0001\u0000\u0000\u0000]l\u0005\u0019\u0000"+
		"\u0000^l\u0005\u001a\u0000\u0000_`\u0005\r\u0000\u0000`l\u0003\u0010\b"+
		"\u0000ab\u0005\u000e\u0000\u0000bl\u0003\u0010\b\u0000cd\u0005\u000f\u0000"+
		"\u0000dl\u0003\u0010\b\u0000ef\u0005\u0010\u0000\u0000fl\u0003\u0010\b"+
		"\u0000gh\u0005\u0011\u0000\u0000hl\u0003\u0010\b\u0000ij\u0005\u0012\u0000"+
		"\u0000jl\u0003\u0010\b\u0000k]\u0001\u0000\u0000\u0000k^\u0001\u0000\u0000"+
		"\u0000k_\u0001\u0000\u0000\u0000ka\u0001\u0000\u0000\u0000kc\u0001\u0000"+
		"\u0000\u0000ke\u0001\u0000\u0000\u0000kg\u0001\u0000\u0000\u0000ki\u0001"+
		"\u0000\u0000\u0000l\u0011\u0001\u0000\u0000\u0000mn\u0005\u0007\u0000"+
		"\u0000no\u0003\u0016\u000b\u0000op\u0005\b\u0000\u0000ps\u0003\n\u0005"+
		"\u0000qr\u0005\t\u0000\u0000rt\u0003\n\u0005\u0000sq\u0001\u0000\u0000"+
		"\u0000st\u0001\u0000\u0000\u0000t\u0013\u0001\u0000\u0000\u0000uv\u0005"+
		"\u000b\u0000\u0000vw\u0003\u0016\u000b\u0000wx\u0005\n\u0000\u0000xy\u0003"+
		"\n\u0005\u0000y\u0085\u0001\u0000\u0000\u0000z{\u0005\f\u0000\u0000{|"+
		"\u0003\u0016\u000b\u0000|}\u0005\n\u0000\u0000}~\u0003\n\u0005\u0000~"+
		"\u0085\u0001\u0000\u0000\u0000\u007f\u0080\u0005\n\u0000\u0000\u0080\u0081"+
		"\u0003\n\u0005\u0000\u0081\u0082\u0005\u000b\u0000\u0000\u0082\u0083\u0003"+
		"\u0016\u000b\u0000\u0083\u0085\u0001\u0000\u0000\u0000\u0084u\u0001\u0000"+
		"\u0000\u0000\u0084z\u0001\u0000\u0000\u0000\u0084\u007f\u0001\u0000\u0000"+
		"\u0000\u0085\u0015\u0001\u0000\u0000\u0000\u0086\u0087\u0005\u0013\u0000"+
		"\u0000\u0087\u0099\u0003\u0016\u000b\u0000\u0088\u0089\u0005\u0014\u0000"+
		"\u0000\u0089\u0099\u0003\u0016\u000b\u0000\u008a\u008b\u0005\u0015\u0000"+
		"\u0000\u008b\u0099\u0003\u0016\u000b\u0000\u008c\u008d\u0005\u0016\u0000"+
		"\u0000\u008d\u008e\u0003\u0010\b\u0000\u008e\u008f\u0003\u0010\b\u0000"+
		"\u008f\u0099\u0001\u0000\u0000\u0000\u0090\u0091\u0005\u0017\u0000\u0000"+
		"\u0091\u0092\u0003\u0010\b\u0000\u0092\u0093\u0003\u0010\b\u0000\u0093"+
		"\u0099\u0001\u0000\u0000\u0000\u0094\u0095\u0005\u0018\u0000\u0000\u0095"+
		"\u0096\u0003\u0010\b\u0000\u0096\u0097\u0003\u0010\b\u0000\u0097\u0099"+
		"\u0001\u0000\u0000\u0000\u0098\u0086\u0001\u0000\u0000\u0000\u0098\u0088"+
		"\u0001\u0000\u0000\u0000\u0098\u008a\u0001\u0000\u0000\u0000\u0098\u008c"+
		"\u0001\u0000\u0000\u0000\u0098\u0090\u0001\u0000\u0000\u0000\u0098\u0094"+
		"\u0001\u0000\u0000\u0000\u0099\u0017\u0001\u0000\u0000\u0000\u000b$*@"+
		"EIW[ks\u0084\u0098";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}