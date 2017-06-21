package org.metaborg.example.api.javaast.syntax;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

public class VarRef extends Expression {

	protected final String id; 

	public VarRef(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}

	public IStrategoTerm toTerm(ITermFactory factory) {
		return factory.makeAppl(factory.makeConstructor("VarRef", 1), factory.makeString(id));
	}
}
