package com.libgdxdesign.core.controller;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.libgdxdesign.component.UIComponent;
import com.libgdxdesign.core.controller.notification.ResourceNotification;

public class DropFilesCommand extends SimpleCommand {

	@Override
	public void execute(INotification notification) {
		String[] paths = catchFiles((DropTargetDropEvent) notification.getBody());
		Array<FileHandle> files = getFilesFromPaths(paths);
		
		Skin skin = loadSkin(files.first());
		sendNotification(ResourceNotification.SKIN_LOADED, skin);

		UIComponent component = loadUi(files.first());
		if(component != null) {
			sendNotification(ResourceNotification.UI_LOADED, component);
		}		
		
	}
	
	private Skin loadSkin(FileHandle fileHandle) {
		return new Skin(fileHandle);
	}
	
	private UIComponent loadUi(FileHandle fileHandle) {
		UIComponent component = new UIComponent();		
		return component.load(fileHandle);
	}
	
	private Array<FileHandle> getFilesFromPaths(String[] paths) {
		Array<FileHandle> files = new Array<>();
		for (String path : paths) {
			files.add(new FileHandle(new File(path)));
		}

		return files;
	}
	
	private String[] catchFiles(DropTargetDropEvent dtde) {
		dtde.acceptDrop(DnDConstants.ACTION_LINK);

		Transferable t = dtde.getTransferable();
		if (t.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			try {
				@SuppressWarnings("unchecked")
				List<File> list = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
				String[] paths = new String[list.size()];
				for (int i = 0; i < list.size(); i++) {
					paths[i] = list.get(i).getAbsolutePath();
				}
				return paths;
			} catch (Exception e) {
				e.fillInStackTrace();
			}
		}
		return null;
	}
}