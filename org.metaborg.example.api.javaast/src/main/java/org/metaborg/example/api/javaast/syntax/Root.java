package org.metaborg.example.api.javaast.syntax;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

public class Root {

	protected final Expression exp; 
	
	public Root(Expression exp) {
		this.exp = exp;
	}

	@Override
	public String toString() {
		
		return exp.toString();
	}
	
	public IStrategoTerm toTerm(ITermFactory factory) {
		return factory.makeAppl(factory.makeConstructor("Root", 1), exp.toTerm(factory));
	}
}
