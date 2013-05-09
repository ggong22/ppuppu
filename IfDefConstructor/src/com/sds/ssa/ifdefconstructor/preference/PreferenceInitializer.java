package com.sds.ssa.ifdefconstructor.preference;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

import com.sds.ssa.ifdefconstructor.IfDefConstructorPlugin;

public class PreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = IfDefConstructorPlugin.getDefault()
				.getPreferenceStore();
		store.setDefault("DB", true);
	}

}
