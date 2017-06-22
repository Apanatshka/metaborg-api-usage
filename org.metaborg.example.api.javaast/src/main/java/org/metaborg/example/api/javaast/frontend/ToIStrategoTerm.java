package org.metaborg.example.api.javaast.frontend;

import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

public interface ToIStrategoTerm {
	public abstract IStrategoTerm toIStrategoTerm(ITermFactory factory);
}
