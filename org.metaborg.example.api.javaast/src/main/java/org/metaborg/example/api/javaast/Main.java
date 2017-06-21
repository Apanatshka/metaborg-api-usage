package org.metaborg.example.api.javaast;


import java.io.IOException;

import org.metaborg.core.MetaborgException;
import org.metaborg.example.api.javaast.parser.Parser;
import org.metaborg.spoofax.core.Spoofax;
import org.metaborg.util.log.ILogger;
import org.metaborg.util.log.LoggerUtils;

public class Main {

	public static void main(String[] args) {
		
		String languageResource = args[0];
		
		try(Spoofax spoofax = new Spoofax()) {
			Parser parser = new Parser(spoofax, languageResource);

			ILogger logger = LoggerUtils.logger(Main.class);
			
			logger.info("start analysing + transforming");
			String result = parser.parse();
			logger.info(result);
			
		} catch (MetaborgException | IOException e) {
			e.printStackTrace();
		}
	}

}
