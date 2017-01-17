import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.aspose.slides.IHyperlinkContainer;
import com.aspose.slides.IHyperlinkQueries;
import com.aspose.slides.IParagraph;
import com.aspose.slides.IPortion;
import com.aspose.slides.ITextFrame;
import com.aspose.slides.Presentation;
import com.aspose.slides.SlideUtil;
import com.aspose.slides.Collections.Generic.IGenericList;

public class URLExtractor {

	private static List<String> extractUrls(String text) {
		List<String> containedUrls = new ArrayList<>();
		String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";

		// String urlRegex =
		// "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
		Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
		Matcher urlMatcher = pattern.matcher(text);
		;
		while (urlMatcher.find()) {
			containedUrls.add(text.substring(urlMatcher.start(0), urlMatcher.end(0)));
		}

		return containedUrls;
	}

	public static void getFileAspose(File input, File output) throws IOException {
		StringBuilder sb = new StringBuilder();

		Presentation pres = new Presentation(input.getAbsolutePath());

		// Get an Array of ITextFrame objects from all slides in the PPTX
		ITextFrame[] textFramesPPTX = SlideUtil.getAllTextFrames(pres, true);

		// Loop through the Array of TextFrames
		for (int i = 0; i < textFramesPPTX.length; i++)

			// Loop through paragraphs in current ITextFrame
			for (IParagraph para : textFramesPPTX[i].getParagraphs())

				// Loop through portions in the current IParagraph
				for (IPortion port : para.getPortions()) {
					// Display text in the current portion
					// System.out.println(port.getText());
					sb.append(port.getText() + "\n");
					// Display font height of the text
					// System.out.println(port.getPortionFormat().getFontHeight());
					IHyperlinkQueries query = port.getSlide().getHyperlinkQueries();
					IGenericList<IHyperlinkContainer> container = query.getAnyHyperlinks();
					for (IHyperlinkContainer iHyperlinkContainer : container) {
						// System.out.println(iHyperlinkContainer.getHyperlinkClick().getExternalUrl());
						sb.append(iHyperlinkContainer.getHyperlinkClick().getExternalUrl() + "\n");
					}
					// Display font name of the text

				}

		List<String> list = extractUrls(sb.toString());
		Set<String> hs = new HashSet<>();
		hs.addAll(list);
		list.clear();
		list.addAll(hs);

		for (String string : list) {
			System.out.println("writting string" + string);
		}
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(output.getAbsolutePath());

			for (String string : list) {
				writer.println(string);
			}

		} catch (IOException e) {
		} finally {
			if (writer != null)
				writer.close();
		}

	}
}
