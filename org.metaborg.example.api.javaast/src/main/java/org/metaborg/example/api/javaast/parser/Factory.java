package org.metaborg.example.api.javaast.parser;

import org.metaborg.example.api.javaast.SpoofaxUtil;
import org.metaborg.example.api.javaast.syntax.Add;
import org.metaborg.example.api.javaast.syntax.Expression;
import org.metaborg.example.api.javaast.syntax.IntLiteral;
import org.metaborg.example.api.javaast.syntax.Let;
import org.metaborg.example.api.javaast.syntax.Mul;
import org.metaborg.example.api.javaast.syntax.Root;
import org.metaborg.example.api.javaast.syntax.VarRef;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoTerm;

public class Factory {

	public Factory() {
		
	}

	public Root createRoot(IStrategoTerm term) {
		
		IStrategoAppl appl = SpoofaxUtil.castStrategoAppl(term);
		if ("Root".equals(appl.getConstructor().getName())) {
			Expression exp = createExpression(appl.getSubterm(0));
			return new Root(exp);
		} else throw new IllegalArgumentException(term.toString());
	}

	public Expression createExpression(IStrategoTerm term) {
		
		IStrategoAppl appl = SpoofaxUtil.castStrategoAppl(term);
		
		switch (appl.getConstructor().getName()) {

		case "IntLiteral": {

			String stringValue = SpoofaxUtil.convertStrategoString(appl.getSubterm(0));
			int value = Integer.parseInt(stringValue);
			return new IntLiteral(value);
		}
		case "Add": {

			Expression left = createExpression(appl.getSubterm(0));
			Expression right = createExpression(appl.getSubterm(1));
			return new Add(left, right);
		}
		case "Mul": {

			Expression left = createExpression(appl.getSubterm(0));
			Expression right = createExpression(appl.getSubterm(1));
			return new Mul(left, right);
		}
		case "Let": {

			String id = SpoofaxUtil.convertStrategoString(appl.getSubterm(0).getSubterm(0));
			Expression init = createExpression(appl.getSubterm(0).getSubterm(1));
			Expression body = createExpression(appl.getSubterm(1));
			return new Let(id, init, body);
		}
		case "VarRef": {

			String id = SpoofaxUtil.convertStrategoString(appl.getSubterm(0));
			return new VarRef(id);
		}
		default:
			throw new IllegalArgumentException(term.toString());
		}

	}
}
