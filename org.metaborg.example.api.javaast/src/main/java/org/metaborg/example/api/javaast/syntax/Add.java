package org.metaborg.example.api.javaast.syntax;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

public class Add extends Expression {

	protected final Expression left; 
	protected final Expression right; 

	public Add(Expression left, Expression right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public String toString() {
		return left + " + " + right;
	}

	public IStrategoTerm toIStrategoTerm(ITermFactory factory) {
		return factory.makeAppl(factory.makeConstructor("Add", 2), left.toIStrategoTerm(factory), right.toIStrategoTerm(factory));
	}
}
