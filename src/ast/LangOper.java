package ast;

/**
 * L'enumerazione LangOper definisce gli operatori aritmetici supportati dal linguaggio.
 * Gli operatori definiti sono PLUS (+), MINUS (-), TIMES (*) e DIVIDE (/).
 * 
 * @see ast.NodeBinOp
 * @see parser.Parser
 * @see scanner.Scanner
 * @see token.Token
 * @see exception.SyntacticException
 * @see exception.LexicalException
 * @see ast.LangType
 * @see ast.NodeProgram
 * @see ast.NodeDecl
 * @see ast.NodeStm
 * @see ast.NodeExpr
 * @see ast.NodeId
 * @see ast.NodeCost
 * @see ast.NodeDeref
 * @see ast.NodeAssign
 * @see ast.NodePrint
 * 
 * @author Guido Broglio 20043973
 */
public enum LangOper
{
    PLUS, 
    MINUS,
    TIMES,
    DIVIDE
}
