package org.metaborg.example.api.javaast.syntax;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

public abstract class Expression {
	public abstract IStrategoTerm toTerm(ITermFactory factory);
}
