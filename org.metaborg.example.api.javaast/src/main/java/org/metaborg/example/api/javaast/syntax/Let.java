package org.metaborg.example.api.javaast.syntax;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

public class Let extends Expression {

	protected final String id;
	protected final Expression init;
	protected final Expression body;

	public Let(String id, Expression init, Expression body) {
		this.id = id;
		this.init = init;
		this.body = body;
	}

	@Override
	public String toString() {
		return "let " + id + " = " + init + " in " + body + " end";
	}

	public IStrategoTerm toTerm(ITermFactory factory) {
		return factory.makeAppl(factory.makeConstructor("Let", 2),
		        factory.makeAppl(factory.makeConstructor("LetDecl", 2), factory.makeString(id), init.toTerm(factory)),
		        body.toTerm(factory));
	}
}
