package com.megan.faces.i18n;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class TextBundleTest {

	private static final String KEY_TO_CHECK = "product.name";

	@Test
	public void bundle_de() {
		final Locale locale = new Locale("de");
		final TextBundle bundle = new TextBundle(locale);

		Assert.assertTrue(bundle.containsKey(TextBundleTest.KEY_TO_CHECK));
	}

	@Test
	public void bundle_en() {
		final Locale locale = new Locale("en");
		final TextBundle bundle = new TextBundle(locale);

		Assert.assertTrue(bundle.containsKey(TextBundleTest.KEY_TO_CHECK));
	}
}
