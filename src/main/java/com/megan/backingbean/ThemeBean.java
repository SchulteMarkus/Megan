package com.megan.backingbean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;

@ManagedBean(eager = true)
@NoneScoped
public class ThemeBean {

	public String getTheme() {
		return "blitzer";
	}
}
