/*******************************************************************************
 * Copyright (c) 2000-2011 Accenture Services Pvt. Ltd., All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * Contributors:
 *    Kamesh Sampath - initial implementation
 ******************************************************************************/

package com.liferay.ide.eclipse.portlet.ui.editor.internal;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.sapphire.modeling.IModelElement;
import org.eclipse.sapphire.modeling.ModelProperty;
import org.eclipse.sapphire.modeling.ModelPropertyListener;
import org.eclipse.sapphire.modeling.ValueProperty;
import org.eclipse.sapphire.ui.SapphirePropertyEditorActionHandler;
import org.eclipse.sapphire.ui.SapphireRenderingContext;

import com.liferay.ide.eclipse.portlet.core.model.internal.ResourceBundleRelativePathService;
import com.liferay.ide.eclipse.portlet.core.util.PortletUtil;
import com.liferay.ide.eclipse.portlet.ui.PortletUIPlugin;

/**
 * @author <a href="mailto:kamesh.sampath@accenture.com">Kamesh Sampath</a>
 */
public abstract class AbstractResourceBundleActionHandler extends SapphirePropertyEditorActionHandler {

	final IWorkspace workspace = ResourcesPlugin.getWorkspace();
	final IWorkspaceRoot wroot = workspace.getRoot();
	protected ModelPropertyListener listener;

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.sapphire.ui.SapphirePropertyEditorActionHandler#computeEnablementState()
	 */
	@Override
	protected boolean computeEnablementState() {
		boolean isEnabled = super.computeEnablementState();
		final IModelElement element = getModelElement();
		final ModelProperty property = getProperty();
		final IProject project = element.adapt( IProject.class );
		String rbFile = element.read( (ValueProperty) property ).getText();
		if ( rbFile != null ) {
			String ioFileName =
				PortletUtil.convertJavaToIoFileName( rbFile, ResourceBundleRelativePathService.RB_FILE_EXTENSION );
			isEnabled = isEnabled && !getFileFromClasspath( project, ioFileName );
		}
		return isEnabled;
	}

	/**
	 * @param project
	 * @param ioFileName
	 * @return
	 */
	protected final boolean getFileFromClasspath( IProject project, String ioFileName ) {

		IClasspathEntry[] cpEntries = PortletUtil.getClasspathEntries( project );
		for ( IClasspathEntry iClasspathEntry : cpEntries ) {
			if ( IClasspathEntry.CPE_SOURCE == iClasspathEntry.getEntryKind() ) {
				IPath entryPath = wroot.getFolder( iClasspathEntry.getPath() ).getLocation();
				entryPath = entryPath.append( ioFileName );
				IFile resourceBundleFile = wroot.getFileForLocation( entryPath );
				if ( resourceBundleFile != null && resourceBundleFile.exists() ) {
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * @param project
	 * @param ioFileName
	 * @return
	 */
	protected final IPath getResourceBundleFolderLocation( IProject project, String ioFileName ) {

		IClasspathEntry[] cpEntries = PortletUtil.getClasspathEntries( project );
		for ( IClasspathEntry iClasspathEntry : cpEntries ) {
			if ( IClasspathEntry.CPE_SOURCE == iClasspathEntry.getEntryKind() ) {
				IPath srcFolder = wroot.getFolder( iClasspathEntry.getPath() ).getLocation();
				IPath entryPath = wroot.getFolder( iClasspathEntry.getPath() ).getLocation();
				entryPath = entryPath.append( ioFileName );
				IFile resourceBundleFile = wroot.getFileForLocation( entryPath );
				if ( resourceBundleFile != null ) {
					return srcFolder;
				}
			}
		}
		return null;
	}

	/**
	 * @param rbFiles
	 * @param rbFileBuffer
	 */
	protected final void createFiles(
		final SapphireRenderingContext context, final List<IFile> rbFiles, final StringBuilder rbFileBuffer ) {
		if ( !rbFiles.isEmpty() ) {
			final int workUnit = rbFiles.size() + 1;
			final IRunnableWithProgress rbCreationProc = new IRunnableWithProgress() {

				public void run( final IProgressMonitor monitor ) throws InvocationTargetException,
					InterruptedException {
					monitor.beginTask( "", workUnit );
					ListIterator<IFile> rbFilesIterator = rbFiles.listIterator();
					while ( rbFilesIterator.hasNext() ) {
						try {
							IFile rbFile = rbFilesIterator.next();
							rbFile.create(
								new ByteArrayInputStream( rbFileBuffer.toString().getBytes() ), true, monitor );
							monitor.worked( 1 );
						}
						catch ( CoreException e ) {
							PortletUIPlugin.logError( e );
						}
					}

					monitor.done();

				}
			};

			try {
				( new ProgressMonitorDialog( context.getShell() ) ).run( false, false, rbCreationProc );
				rbFiles.clear();
			}
			catch ( InvocationTargetException e ) {
				PortletUIPlugin.logError( e );
			}
			catch ( InterruptedException e ) {
				PortletUIPlugin.logError( e );
			}
		}
	}

}
