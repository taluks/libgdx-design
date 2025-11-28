package com.libgdxdesign.core.controller;

import com.libgdxdesign.core.controller.command.*;
import com.libgdxdesign.core.controller.notification.EditorNotification;
import com.libgdxdesign.core.controller.notification.ResourceNotification;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.command.SimpleCommand;

public class EditorCommand extends SimpleCommand {

    @Override
    public void execute(INotification notification) {
        facade.registerCommand(EditorNotification.ADD_ACTOR, AddActorToSceneCommand::new);
        facade.registerCommand(EditorNotification.REMOVE_ACTOR, RemoveActorCommand::new);
        facade.registerCommand(EditorNotification.ACTOR_MOVED, MoveActorCommand::new);
        facade.registerCommand(EditorNotification.ACTOR_SELECTED, SelectActorCommand::new);
        facade.registerCommand(EditorNotification.TRANSFORM_ACTORS, TransformActorsCommand::new);
        facade.registerCommand(EditorNotification.OPEN_GROUP_EDIT, EnterGroupCommand::new);
        facade.registerCommand(EditorNotification.UPDATE_ACTOR_POSITION, UpdateActorPositionCommand::new);
        facade.registerCommand(EditorNotification.UPDATE_ACTOR_WIDTH, UpdateActorWidthCommand::new);
        facade.registerCommand(EditorNotification.UPDATE_ACTOR_WIDTH, UpdateActorHeightCommand::new);

        facade.registerCommand(EditorNotification.TABLE_ADD_CELL, TableAddCellCommand::new);
        facade.registerCommand(EditorNotification.TABLE_ROW, TableRowCommand::new);
        facade.registerCommand(EditorNotification.TABLE_CELL_EXPAND, TableCellExpandCommand::new);
        facade.registerCommand(EditorNotification.TABLE_CELL_FILL, TableCellFillCommand::new);
        facade.registerCommand(EditorNotification.TABLE_CLEAR_CHILDREN, TableClearChildrenCommand::new);
        facade.registerCommand(EditorNotification.TABLE_CELL_RESET, TableCellResetCommand::new);
        facade.registerCommand(EditorNotification.TABLE_CELL_ALIGN, TableCellAlignCommand::new);
        facade.registerCommand(EditorNotification.TABLE_CELL_PAD, TableCellPadCommand::new);

        facade.registerCommand(EditorNotification.UPDATE_WINDOW_TITLE, UpdateWindowTitleCommand::new);
        facade.registerCommand(EditorNotification.UPDATE_LABEL_TEXT, UpdateLabelTextCommand::new);

        facade.registerCommand(ResourceNotification.SAVE_UI_COMPONENT, SaveUiComponentCommand::new);
        facade.registerCommand(ResourceNotification.UI_LOADED, UiLoadedCommand::new);
    }
}
