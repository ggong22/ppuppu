package com.sds.ssa.ifdefconstructor.preference;

import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferencePageContainer;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.sds.ssa.ifdefconstructor.IfDefConstructorPlugin;

public class WorkbenchPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
	private StringFieldEditor dbInfoEditor;

	public WorkbenchPreferencePage() {
		super(GRID);
		setPreferenceStore(IfDefConstructorPlugin.getDefault().getPreferenceStore());
		setDescription("Set the DB invironment:");
	}
	

	@Override
	protected void createFieldEditors() {
		// TODO Auto-generated method stub
		dbInfoEditor = new StringFieldEditor("DB",
				"Insert DB Info", getFieldEditorParent());
		addField(dbInfoEditor);
	}

	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		if (event.getProperty().equals(FieldEditor.VALUE)) {
			if (event.getSource() == dbInfoEditor)
				checkState();
		}
	}


	@Override
	public void init(IWorkbench workbench) {
		// TODO Auto-generated method stub
		
	}
}
