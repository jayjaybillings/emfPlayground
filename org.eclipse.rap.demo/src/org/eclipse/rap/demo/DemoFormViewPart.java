/*******************************************************************************
 * Copyright (c) 2012 EclipseSource and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    EclipseSource - initial API and implementation
 ******************************************************************************/
package org.eclipse.rap.demo;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecp.makeithappen.model.task.TaskPackage;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTView;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emfforms.spi.editor.GenericEditor;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.ui.forms.widgets.*;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;


public class DemoFormViewPart extends ViewPart {
	private ECPSWTView render;

	private EObject getDummyEObject() {
		// Replace this with your own model EClass to test the application with a custom model
		final EClass eClass = TaskPackage.eINSTANCE.getUser();
		return EcoreUtil.create(eClass);
	}

  @Override
  public void createPartControl( Composite parent ) {
		final EObject dummyObject = getDummyEObject();

		try {
			parent.getShell().setMaximized(true);
			parent.setLayout(GridLayoutFactory.fillDefaults().equalWidth(true).numColumns(1).create());
			parent.setLayoutData(GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, true)
				.create());
			parent.getParent().setLayout(GridLayoutFactory.fillDefaults().equalWidth(true).numColumns(1).create());
			parent.getParent().setLayoutData(
				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BEGINNING).grab(true, true).create());

			final Composite content = new Composite(parent, SWT.NONE);
			content.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
			content.setLayout(GridLayoutFactory.fillDefaults().margins(10, 10).create());
			content.setLayoutData(GridDataFactory.fillDefaults().create());

			render = ECPSWTViewRenderer.INSTANCE.render(content, dummyObject);
			
			// Declare an editor just to make the framework resolve the dependencies
			GenericEditor editor;

			content.layout();

		} catch (final ECPRendererException e) {
			e.printStackTrace();
		}
		parent.layout();
  }

  @Override
  public void setFocus() {
  }
  
  @Override
  public void dispose() {
		if (render != null) {
			render.dispose();
		}
  }
}
