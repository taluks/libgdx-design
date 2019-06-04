package com.libgdxdesign.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener.FocusEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.libgdxdesign.core.event.ActorSelectEvent;
import com.libgdxdesign.core.event.ActorUpdateEvent;
import com.libgdxdesign.core.event.ResizeEvent;
import com.libgdxdesign.core.resource.CloneableGroup;
import com.libgdxdesign.core.resource.CloneableResource;
import com.libgdxdesign.core.resource.ResourceItem;
import com.libgdxdesign.core.resource.parser.ResourceParser;
import com.libgdxdesign.core.resource.parser.SkinParser;
import com.libgdxdesign.manager.ProjectManager;
import com.libgdxdesign.model.Project;
import com.libgdxdesign.scene.TransformRectangle;
import com.libgdxdesign.utils.Assets;
import com.libgdxdesign.view.lilbrary.LilbraryPanel;
import com.libgdxdesign.view.property.PropertiesPanel;
import com.libgdxdesign.view.tool.ToolPanel;
import com.libgdxdesign.view.tree.TreePanel;

@Singleton
public class EditorUI extends Stage {

	private final EventBus eventBus;
	private final Assets assets;
	private final TransformRectangle transformRectangle;

	private Group dummyTarget;
	private ToolPanel toolPanel;
	private TreePanel treePanel;
	private LilbraryPanel lilbraryPanel;
	private PropertiesPanel propertiesPanel;
	private ActorRouterPanel routePanel;
	@Inject
	private SkinParser skinParser;
	@Inject
	private ProjectManager projectManager;

	@Inject
	public EditorUI(Assets assets, EditorScene scene, EventBus eventBus) {
		super(new ScreenViewport());
		this.assets = assets;
		this.eventBus = eventBus;
		
		Skin skin = assets.getSkin();

		transformRectangle = new TransformRectangle(eventBus, skin);
		transformRectangle.setVisible(false);
		transformRectangle.setTouchable(Touchable.childrenOnly);

		dummyTarget = new Group();
		dummyTarget.setPosition(0, 0);

		addActor(dummyTarget);// for triggering drop actor on stage
		addActor(transformRectangle);

		treePanel = new TreePanel(scene.getRoot(), eventBus, skin);
		addActor(treePanel);

		propertiesPanel = new PropertiesPanel(eventBus, skin);	
		addActor(propertiesPanel);

		lilbraryPanel = new LilbraryPanel(skin, dummyTarget);
		addActor(lilbraryPanel);

		toolPanel = new ToolPanel(eventBus, skin);
		addActor(toolPanel);
		
		routePanel = new ActorRouterPanel(skin);
		addActor(routePanel);

		eventBus.register(this);
					
	}

	@Subscribe
	private void resizeEvent(ResizeEvent event) {
		
		int width = event.getWidth();
		int height = event.getHeight();
		dummyTarget.setSize(width, height);
		
		
		toolPanel.setY(height - toolPanel.getHeight());
		toolPanel.setSize(width, toolPanel.getHeight());
		
		propertiesPanel.setPosition(width - propertiesPanel.getWidth(), lilbraryPanel.getHeight()+2);
		propertiesPanel.setHeight(height - lilbraryPanel.getHeight() - toolPanel.getHeight() - 2);
		
		lilbraryPanel.setSize(width, 160);
		
		treePanel.setPosition(0, lilbraryPanel.getHeight()+2);
		treePanel.setSize(200, height - lilbraryPanel.getHeight() - toolPanel.getHeight() - 2);
		
		routePanel.setPosition(treePanel.getWidth(), height - routePanel.getHeight() - toolPanel.getHeight());
		routePanel.setSize(width - propertiesPanel.getWidth() - treePanel.getWidth(), routePanel.getHeight());
	}

	@Subscribe
	private void actorSelectEvent(ActorSelectEvent event) {
		if (event.getActor() == null) {
			unselectActor(null);
		} else {
			selectActor(event.getActor());
		}
	}
	
	@Subscribe
	private void actorUpdateEvent(ActorUpdateEvent event) {
		selectActor(event.getActor());
	}
	
	public void displayResources() {		
		Project project = projectManager.getProject();
		
		lilbraryPanel.clearItems();
		for(ResourceParser parser: skinParser.getParsers()) {
			List<ResourceItem> items = new ArrayList<>();
			Map<String, CloneableResource<?>> parsers = parser.getResources(project.getSkin());
			for (Map.Entry<String, CloneableResource<?>> entry : parsers.entrySet()) {
			    String key = entry.getKey();
			    CloneableResource<?> value = entry.getValue();
			    items.add(new ResourceItem(eventBus, value, assets.getSkin(), key));
			}
			lilbraryPanel.addItems(items);
		}
		
		Group group = new Group();
		group.setSize(100, 100);
		CloneableResource<?> value = new CloneableGroup(group);
		lilbraryPanel.addItems(Arrays.asList(new ResourceItem(eventBus, value, assets.getSkin(), Group.class.getSimpleName())));
		
		lilbraryPanel.displayResources();
	}

	public void selectActor(Actor actor) {
		if(!actor.hasParent())
			return;
		transformRectangle.clear();
		transformRectangle.setVisible(true);		
		transformRectangle.setActor(actor);
		routePanel.setActor(actor);
	}

	public void unselectActor(Actor actor) {
		transformRectangle.clear();
		transformRectangle.setVisible(false);
	}
	
	public TransformRectangle getSelectionRectangle() {
		return transformRectangle;
	}

	public ToolPanel getToolPanel() {
		return toolPanel;
	}

}
