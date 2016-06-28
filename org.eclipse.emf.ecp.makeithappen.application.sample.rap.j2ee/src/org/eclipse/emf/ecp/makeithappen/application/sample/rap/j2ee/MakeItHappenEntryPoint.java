/**
 * Copyright (c) 2011-2016 EclipseSource Muenchen GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * EclipseSource Munich - initial API and implementation
 */
package org.eclipse.emf.ecp.makeithappen.application.sample.rap.j2ee;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecp.makeithappen.model.task.TaskFactory;
import org.eclipse.emf.ecp.makeithappen.model.task.User;
import org.eclipse.emf.ecp.makeithappen.model.task.util.TaskAdapterFactory;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.emf.ecp.ui.view.swt.ECPSWTViewRenderer;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

/**
 * Entry Point for a standalone RAP Application.
 */
public class MakeItHappenEntryPoint extends AbstractEntryPoint {

	private static final long serialVersionUID = 1L;

	private EObject getDummyEObject() throws IOException {
		// Replace this with your own model EClass to test the application with a custom model
		User user;
		user = TaskFactory.eINSTANCE.createUser();

		AdapterFactoryEditingDomain domain;
		domain = new AdapterFactoryEditingDomain(new TaskAdapterFactory(), new BasicCommandStack());

		ResourceSet resourceSet;
		resourceSet = new ResourceSetImpl();
		Resource userResource;
		userResource = domain.createResource(new File("userFile.xml").getAbsolutePath());
		userResource.getContents().add(user);
		userResource.save(null);

		return user;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.rap.rwt.application.AbstractEntryPoint#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createContents(Composite parent) {
		// Special call needed only on RAP J2EE
		RealmSetter.initialize();

		try {
			final EObject dummyObject = getDummyEObject();

			final Composite content = new Composite(parent, SWT.NONE);
			content.setBackground(parent.getDisplay().getSystemColor(SWT.COLOR_WHITE));
			content.setLayout(GridLayoutFactory.fillDefaults().margins(10, 10).create());
			content.setLayoutData(GridDataFactory.fillDefaults().create());

			ECPSWTViewRenderer.INSTANCE.render(content, dummyObject);

			content.layout();

		} catch (final ECPRendererException | IOException e) {
			e.printStackTrace();
		}

		parent.pack();
	}

}
