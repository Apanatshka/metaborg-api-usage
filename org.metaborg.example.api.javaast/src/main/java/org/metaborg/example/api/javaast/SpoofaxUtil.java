package org.metaborg.example.api.javaast;


import java.io.IOException;

import org.apache.commons.vfs2.FileObject;
import org.metaborg.core.MetaborgException;
import org.metaborg.core.context.IContext;
import org.metaborg.core.language.ILanguageImpl;
import org.metaborg.core.project.IProject;
import org.metaborg.core.project.ISimpleProjectService;
import org.metaborg.core.project.SimpleProjectService;
import org.metaborg.spoofax.core.Spoofax;
import org.spoofax.interpreter.terms.IStrategoAppl;
import org.spoofax.interpreter.terms.IStrategoString;
import org.spoofax.interpreter.terms.IStrategoTerm;

public final class SpoofaxUtil {

	public static IContext getContext(Spoofax spoofax, ILanguageImpl implementation, FileObject file) throws MetaborgException, IOException  {
		IProject project = spoofax.projectService.get(file);
		if (project == null) {
			ISimpleProjectService projectService = spoofax.injector.getInstance(SimpleProjectService.class);
			project = projectService.create(file);
		}
		return spoofax.contextService.get(file, project, implementation);
	}
	
	public static IStrategoAppl castStrategoAppl(IStrategoTerm term) {
		if (term instanceof IStrategoAppl)
			return (IStrategoAppl) term;
		else
			throw new IllegalArgumentException(term.toString());

	}

	public static String convertStrategoString(IStrategoTerm term) {
		if (term instanceof IStrategoString)
			return ((IStrategoString) term).stringValue();
		else
			throw new IllegalArgumentException(term.toString());

	}

}
