#include <vector>

class ExprAST {
public:
	virtual ~ExprAST() {}
	virtual void PrettyPrint(int d) = 0;
//	virtual Value *Codegen() = 0;
};

class NumberExprAST : public ExprAST {
	double Val;
public:
	NumberExprAST(double val) : Val(val) {}
	virtual void PrettyPrint(int d);
//	virtual Value *Codegen();
};

class VariableExprAST : public ExprAST {
	std::string Name;
public:
	VariableExprAST(const std::string &name) : Name(name) {}
	virtual void PrettyPrint(int d);
//	virtual Value *Codegen();
};

class BinaryExprAST : public ExprAST {
	char Op;
	ExprAST *LHS, *RHS;
public:
	BinaryExprAST(char op, ExprAST *lhs, ExprAST *rhs) : Op(op), LHS(lhs), RHS(rhs) {}
	virtual void PrettyPrint(int d);
//	virtual Value *Codegen();
};

class CallExprAST : public ExprAST {
	std::string Callee;
	std::vector<ExprAST*> Args;
public:
	CallExprAST(const std::string &callee, std::vector<ExprAST*> &args): Callee(callee), Args(args) {}
	virtual void PrettyPrint(int d);
//	virtual Value *Codegen();
};

class PrototypeAST {
	std::string Name;
	std::vector<std::string> Args;
public:
	PrototypeAST(const std::string &name, const std::vector<std::string> &args): Name(name), Args(args) {}
//	Function *Codegen();
	void PrettyPrint(int d);
};

class FunctionAST {
	PrototypeAST *Proto;
	ExprAST *Body;
public:
	FunctionAST(PrototypeAST *proto, ExprAST *body): Proto(proto), Body(body) {}
//	Function *Codegen();
	void PrettyPrint(int d);
};
