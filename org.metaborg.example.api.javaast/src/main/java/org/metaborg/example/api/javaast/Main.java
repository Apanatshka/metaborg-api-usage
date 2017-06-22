package org.metaborg.example.api.javaast;


import java.io.IOException;

import org.metaborg.core.MetaborgException;
import org.metaborg.example.api.javaast.frontend.Frontend;
import org.metaborg.example.api.javaast.syntax.Add;
import org.metaborg.example.api.javaast.syntax.IntLiteral;
import org.metaborg.example.api.javaast.syntax.Mul;
import org.metaborg.example.api.javaast.syntax.Root;
import org.metaborg.spoofax.core.Spoofax;
import org.metaborg.util.log.ILogger;
import org.metaborg.util.log.LoggerUtils;

public class Main {

	final static Root PROGRAM = new Root(new Add(new IntLiteral(2), new Mul(new IntLiteral(4), new IntLiteral(10))));

	public static void main(String[] args) {
		
		String languageResource = args[0];
		
		try(Spoofax spoofax = new Spoofax()) {
			Frontend parser = new Frontend(spoofax, languageResource);

			ILogger logger = LoggerUtils.logger(Main.class);
			
			logger.info("start analysing + transforming");
			String result = parser.evaluate(PROGRAM);
			logger.info(result);
			
		} catch (MetaborgException | IOException e) {
			e.printStackTrace();
		}
	}

}
