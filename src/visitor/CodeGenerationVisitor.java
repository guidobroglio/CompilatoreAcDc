package visitor;
import java.util.ArrayList;

import ast.*;
import symbolTable.*;

public class CodeGenerationVisitor implements IVisitor
{
	private String code = "";
	private String log = "";
	
	public CodeGenerationVisitor()
	{
		SymbolTable.init();
		Registri.init();
	}
	
	public String getCode()
	{
		return code;
	}
	
	public String getLog()
	{
		return log;
	}
	
	public void visit(NodeProgram node)
	{
		ArrayList<NodeDecSt> decSts = node.getDecSts();
		
		for(NodeDecSt decSt : decSts)
		{
			decSt.accept(this);
		}
	}
	
	public void visit(NodeId node)
	{
		return;
	}

	public void visit(NodeDecl node)
	{
		NodeId nodeId = node.getId();
		NodeExpr nodeExpr = node.getInit();
		
		nodeId.getDefinition().setRegistro(Registri.newRegister());
		
		if(nodeExpr != null)
		{
			nodeExpr.accept(this);
			nodeId.accept(this);
			String codiceInit=code;
			String codiceId=code;
			code=codiceInit + "s" + codiceId + " 0 k ";
		}
	}
	
	public void visit(NodeBinOp node)
	{
		NodeExpr left = node.getLeft();
		left.accept(this);
		NodeExpr right = node.getRight();
		right.accept(this);
		LangOper opertor = node.getOp();
				
		switch(opertor)
		{
			case PLUS:
				code += "+ ";
				break;
				
			case MINUS:
				code += "- ";
				break;
				
			case TIMES:
				code += "* ";
				break;
				
			case DIVIDE:
				code += "/ ";
				break;
		}
	}
	
	public void visit(NodePrint node)
	{
		NodeId id = node.getId();
		id.accept(this);
		code = "l" + code + " p P";
	}
	
	public void visit(NodeDeref node)
	{
		NodeId id = node.getId();
		id.accept(this);
		code = "l" + code;
	}
	
	public void visit(NodeAssign node)
	{
		NodeId id = node.getId();
		id.accept(this);
		NodeExpr expr = node.getExpr();
		expr.accept(this);
		code = "l" + code + " p P";
	}
	
	public void visit(NodeConvert node)
	{
		NodeExpr expr = node.getExpr();
		expr.accept(this);
		code = code + " 5 k ";
	}
	
	public void visit(NodeCost node)
	{
		code+=node.getValue()+" ";
	}
}
