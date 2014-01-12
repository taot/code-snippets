#include <iostream>
#include <vector>
#include "expr.h"

using namespace std;

string GetIndent(int d) {
	string s = "";
	for (int i = 0; i < d; i++) {
		s += "  ";
	}
	return s;
}

void NumberExprAST::PrettyPrint(int d) {
	cout << GetIndent(d) << "NumberExpr: " << this->Val << endl;
}

void VariableExprAST::PrettyPrint(int d) {
	cout << GetIndent(d) << "VariableExpr: " << this->Name << endl;
}

void BinaryExprAST::PrettyPrint(int d) {
	cout << GetIndent(d) << "BinaryExpr:" << endl;
	cout << GetIndent(d + 1) << this->Op << endl;
	this->LHS->PrettyPrint(d + 1);
	this->RHS->PrettyPrint(d + 1);
}

void CallExprAST::PrettyPrint(int d) {
	cout << GetIndent(d) << "CallExpr:" << endl;
	cout << GetIndent(d + 1) << this->Callee << endl;
	for (vector<ExprAST*>::const_iterator it = this->Args.begin(); it < this->Args.end(); it++) {
		(*it)->PrettyPrint(d + 1);
	}
}

void PrototypeAST::PrettyPrint(int d) {
	cout << GetIndent(d) << "Prototype:" << endl;
	cout << GetIndent(d + 1) << "name: " + this->Name << endl;
	cout << GetIndent(d + 1) << "args: ";
	for (vector<string>::const_iterator it = this->Args.begin(); it < this->Args.end(); it++) {
		if (it != this->Args.begin()) {
			cout << ", ";
		}
		cout << *it;
	}
	cout << endl;
}

void FunctionAST::PrettyPrint(int d) {
	cout << GetIndent(d) << "Function:" << endl;
	this->Proto->PrettyPrint(d + 1);
	this->Body->PrettyPrint(d + 1);
}