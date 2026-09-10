// Generated from SPL.g4 by ANTLR 4.13.2

    package spl.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SPLParser}.
 */
public interface SPLListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SPLParser#spl_prog}.
	 * @param ctx the parse tree
	 */
	void enterSpl_prog(SPLParser.Spl_progContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPLParser#spl_prog}.
	 * @param ctx the parse tree
	 */
	void exitSpl_prog(SPLParser.Spl_progContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPLParser#p}.
	 * @param ctx the parse tree
	 */
	void enterP(SPLParser.PContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPLParser#p}.
	 * @param ctx the parse tree
	 */
	void exitP(SPLParser.PContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPLParser#v_decl}.
	 * @param ctx the parse tree
	 */
	void enterV_decl(SPLParser.V_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPLParser#v_decl}.
	 * @param ctx the parse tree
	 */
	void exitV_decl(SPLParser.V_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPLParser#f_decl}.
	 * @param ctx the parse tree
	 */
	void enterF_decl(SPLParser.F_declContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPLParser#f_decl}.
	 * @param ctx the parse tree
	 */
	void exitF_decl(SPLParser.F_declContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPLParser#f_type}.
	 * @param ctx the parse tree
	 */
	void enterF_type(SPLParser.F_typeContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPLParser#f_type}.
	 * @param ctx the parse tree
	 */
	void exitF_type(SPLParser.F_typeContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPLParser#algo}.
	 * @param ctx the parse tree
	 */
	void enterAlgo(SPLParser.AlgoContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPLParser#algo}.
	 * @param ctx the parse tree
	 */
	void exitAlgo(SPLParser.AlgoContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPLParser#instr}.
	 * @param ctx the parse tree
	 */
	void enterInstr(SPLParser.InstrContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPLParser#instr}.
	 * @param ctx the parse tree
	 */
	void exitInstr(SPLParser.InstrContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPLParser#outp}.
	 * @param ctx the parse tree
	 */
	void enterOutp(SPLParser.OutpContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPLParser#outp}.
	 * @param ctx the parse tree
	 */
	void exitOutp(SPLParser.OutpContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPLParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(SPLParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPLParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(SPLParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPLParser#branch}.
	 * @param ctx the parse tree
	 */
	void enterBranch(SPLParser.BranchContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPLParser#branch}.
	 * @param ctx the parse tree
	 */
	void exitBranch(SPLParser.BranchContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPLParser#loop}.
	 * @param ctx the parse tree
	 */
	void enterLoop(SPLParser.LoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPLParser#loop}.
	 * @param ctx the parse tree
	 */
	void exitLoop(SPLParser.LoopContext ctx);
	/**
	 * Enter a parse tree produced by {@link SPLParser#bool}.
	 * @param ctx the parse tree
	 */
	void enterBool(SPLParser.BoolContext ctx);
	/**
	 * Exit a parse tree produced by {@link SPLParser#bool}.
	 * @param ctx the parse tree
	 */
	void exitBool(SPLParser.BoolContext ctx);
}