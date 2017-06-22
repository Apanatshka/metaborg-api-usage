package org.metaborg.example.api.javaast.syntax;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

public class Mul extends Expression {

	protected final Expression left; 
	protected final Expression right; 

	public Mul(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return parenthesize(left) + " * " + parenthesize(right);
	}

	private String parenthesize(Expression exp) {
		if (exp instanceof Add)
			return "(" + exp + ")";
		else 
			return exp.toString();
	}

	public IStrategoTerm toIStrategoTerm(ITermFactory factory) {
		return factory.makeAppl(factory.makeConstructor("Mul", 2), left.toIStrategoTerm(factory), right.toIStrategoTerm(factory));
	}
}
