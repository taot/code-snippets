#include <iostream>
#include "expr.h"

using namespace std;

int main(int argc, char **argv)
{
	// single number
	cout << "--- single number ---" << endl;
	NumberExprAST n1 = NumberExprAST(1.0);
	n1.PrettyPrint(0);

	// binary expr
	cout << "--- binary expr ---" << endl;
	NumberExprAST n2 = NumberExprAST(2.0);
	BinaryExprAST binary1 = BinaryExprAST('+', &n1, &n2);
	binary1.PrettyPrint(0);

	// call expr
	cout << "--- call expr ---" << endl;
	vector<ExprAST*> v_args1;
	v_args1.push_back(&n1);
	v_args1.push_back(&n2);
	CallExprAST call1 = CallExprAST("fib", v_args1);
	call1.PrettyPrint(0);

	// prototype
	cout << "--- prototype ---" << endl;
	vector<string> v_args2;
	v_args2.push_back("num");
	v_args2.push_back("order");
	PrototypeAST prototype1 = PrototypeAST("fib", v_args2);
	prototype1.PrettyPrint(0);

	// function
	cout << "--- function ---" << endl;
	FunctionAST function1 = FunctionAST(&prototype1, &n1);
	function1.PrettyPrint(0);

	return 0;
}