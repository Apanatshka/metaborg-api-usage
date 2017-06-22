package org.metaborg.example.api.javaast.syntax;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

public class IntLiteral extends Expression {

	protected final int value; 

	public IntLiteral(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}

	public IStrategoTerm toIStrategoTerm(ITermFactory factory) {
		return factory.makeAppl(factory.makeConstructor("IntLiteral", 1), factory.makeString(Integer.toString(value)));
	}
}
