package com.libgdxdesign.manager;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
public class ImportManager {


	public ImportManager() {

	}

	public Array<FileHandle> getFilesFromPaths(String[] paths) {
		Array<FileHandle> files = new Array<>();
		for (int i = 0; i < paths.length; i++) {
			files.add(new FileHandle(new File(paths[i])));
		}

		return files;
	}

	public String[] catchFiles(DropTargetDropEvent dtde) {
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
