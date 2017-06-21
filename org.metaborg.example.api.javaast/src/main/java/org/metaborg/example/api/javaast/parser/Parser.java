package org.metaborg.example.api.javaast.parser;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.VFS;
import org.metaborg.core.MetaborgException;
import org.metaborg.core.action.EndNamedGoal;
import org.metaborg.core.analysis.AnalysisException;
import org.metaborg.core.context.IContext;
import org.metaborg.core.language.ILanguageImpl;
import org.metaborg.core.transform.TransformException;
import org.metaborg.example.api.javaast.SpoofaxUtil;
import org.metaborg.example.api.javaast.syntax.Add;
import org.metaborg.example.api.javaast.syntax.IntLiteral;
import org.metaborg.example.api.javaast.syntax.Mul;
import org.metaborg.example.api.javaast.syntax.Root;
import org.metaborg.spoofax.core.Spoofax;
import org.metaborg.spoofax.core.unit.ISpoofaxAnalyzeUnit;
import org.metaborg.spoofax.core.unit.ISpoofaxInputUnit;
import org.metaborg.spoofax.core.unit.ISpoofaxParseUnit;
import org.metaborg.spoofax.core.unit.ISpoofaxTransformUnit;
import org.metaborg.spoofax.core.unit.ParseContrib;
import org.metaborg.util.concurrent.IClosableLock;
import org.spoofax.interpreter.terms.IStrategoInt;
import org.spoofax.interpreter.terms.IStrategoTerm;
import org.spoofax.interpreter.terms.ITermFactory;

public class Parser {

	final static Root PROGRAM = new Root(new Add(new IntLiteral(2), new Mul(new IntLiteral(4), new IntLiteral(10))));

	final private Spoofax spoofax;
	final private ILanguageImpl implementation;

	public Parser(Spoofax spoofax, String languageResource) throws MetaborgException {
		this.spoofax = spoofax;
		FileObject location = spoofax.resourceService.resolve(languageResource);
		this.implementation = spoofax.languageDiscoveryService.languageFromArchive(location);
	}

	public String parse() throws MetaborgException, IOException {
		FileObject file = VFS.getManager().toFileObject(File.createTempFile("temp", "example"));
		ITermFactory factory = spoofax.strategoRuntimeService.genericRuntime().getFactory();

		ISpoofaxInputUnit input = spoofax.unitService.emptyInputUnit(file, implementation, null);
		ISpoofaxParseUnit parsed = spoofax.unitService.parseUnit(input, new ParseContrib(PROGRAM.toTerm(factory)));

		IContext context = SpoofaxUtil.getContext(spoofax, implementation, file);
		ISpoofaxAnalyzeUnit analyzed;
		try (IClosableLock lock = context.write()) {
			analyzed = spoofax.analysisService.analyze(parsed, context).result();
			if (!analyzed.valid())
				throw new AnalysisException(context, "Could not analyse");
		}
		Collection<ISpoofaxTransformUnit<ISpoofaxAnalyzeUnit>> transformUnits;
		try(IClosableLock lock = context.read()) {
			transformUnits = spoofax.transformService.transform(analyzed, context, new EndNamedGoal("Evaluate"));
		}
		
		String results = "";
		for (ISpoofaxTransformUnit<ISpoofaxAnalyzeUnit> transform : transformUnits) {
			IStrategoTerm result = transform.ast();
			if (result instanceof IStrategoInt)
				results = results + ((IStrategoInt) result).intValue() + " ";
			else
				throw new TransformException("Result is not an integer");
		}
		
		return results;
	}
}
