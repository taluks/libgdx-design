package com.libgdxdesign.core.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.libgdxdesign.component.UIComponent;
import com.libgdxdesign.core.controller.notification.ResourceNotification;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

public class DropFilesCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {
        String[] paths = catchFiles((DropTargetDropEvent) notification.getBody());
        Array<FileHandle> files = getFilesFromPaths(paths);

        if (!loadSkin(files.first())) {
            UIComponent component = loadUi(files.first());
            if (component != null) {
                sendNotification(ResourceNotification.UI_LOADED, component);
            }
        }
    }

    private boolean loadSkin(FileHandle fileHandle) {
        try {
            Skin skin = new Skin(fileHandle);
            sendNotification(ResourceNotification.SKIN_LOADED, skin);
            return true;
        } catch (Exception e) {
            Gdx.app.error(getClass().getSimpleName(), e.getMessage());
        }
        return false;
    }

    private UIComponent loadUi(FileHandle fileHandle) {
        return new UIComponent().load(fileHandle);
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