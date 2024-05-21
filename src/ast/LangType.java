package ast;

/**
 * L'enumerazione LangType definisce i tipi di dato supportati dal linguaggio.
 * I tipi inclusi sono INT (intero) e FLOAT (virgola mobile).
 * 
 * @see ast.NodeDecl
 * @see parser.Parser
 * @see scanner.Scanner
 * @see token.Token
 * @see exception.SyntacticException
 * @see exception.LexicalException
 * @see ast.NodeProgram
 * @see ast.NodeStm
 * @see ast.NodeExpr
 * @see ast.NodeId
 * @see ast.NodeCost
 * @see ast.NodeDeref
 * @see ast.NodeAssign
 * @see ast.NodePrint
 * @see ast.NodeBinOp
 * 
 * @author Guido Broglio 20043973
 */
public enum LangType 
{
    INT, // Il tipo di dato intero.
    FLOAT // Il tipo di dato virgola mobile.
}
