package com.megan.faces.i18n;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

/**
 * @see http://jdevelopment.nl/internationalization-jsf-utf8-encoded-properties-files/
 * @author markus
 */
public class TextBundle extends ResourceBundle {

	private static class UTF8Control extends Control {

		@Override
		public ResourceBundle newBundle(final String baseName, final Locale locale, final String format,
				final ClassLoader loader, final boolean reload) throws IllegalAccessException, InstantiationException,
				IOException {
			// The below code is copied from default Control#newBundle() implementation.
			// Only the PropertyResourceBundle line is changed to read the file as UTF-8.
			final String bundleName = this.toBundleName(baseName, locale);
			final String resourceName = this.toResourceName(bundleName, TextBundle.BUNDLE_EXTENSION);
			ResourceBundle bundle = null;
			InputStream stream = null;
			if (reload) {
				final URL url = loader.getResource(resourceName);
				if (url != null) {
					final URLConnection connection = url.openConnection();
					if (connection != null) {
						connection.setUseCaches(false);
						stream = connection.getInputStream();
					}
				}
			} else {
				stream = loader.getResourceAsStream(resourceName);
			}
			if (stream != null) {
				try {
					bundle = new PropertyResourceBundle(new InputStreamReader(stream, "UTF-8"));
				} finally {
					stream.close();
				}
			}
			return bundle;
		}
	}

	private static final String BUNDLE_EXTENSION = "properties";

	private static final String BUNDLE_NAME = "texts";

	private static final Control UTF8_CONTROL = new UTF8Control();

	public TextBundle() {
		this.setParent(ResourceBundle.getBundle(TextBundle.BUNDLE_NAME, FacesContext.getCurrentInstance().getViewRoot()
				.getLocale(), TextBundle.UTF8_CONTROL));
	}

	public TextBundle(final Locale locale) {
		this.setParent(ResourceBundle.getBundle(TextBundle.BUNDLE_NAME, locale, TextBundle.UTF8_CONTROL));
	}

	@Override
	public Enumeration<String> getKeys() {
		return this.parent.getKeys();
	}

	@Override
	protected Object handleGetObject(final String key) {
		return this.parent.getObject(key);
	}
}
