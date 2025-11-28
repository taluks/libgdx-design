import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.badlogic.gdx.utils.ObjectMap;
import com.libgdxdesign.component.UIComponent;
import com.libgdxdesign.component.loader.UIComponentLoader;
import com.libgdxdesign.component.model.ActorData;
import com.libgdxdesign.core.util.Version;

public class UiComponentTest extends ApplicationAdapter {
	private Stage stage;

	@Override
	public void create() {
		stage = new Stage();

		String fileSkin = "skin.json";
		String fileUi = "test.json";

		AssetManager manager = new AssetManager();
		manager.setLoader(UIComponent.class, new UIComponentLoader(manager.getFileHandleResolver()));
		manager.load(fileSkin, Skin.class);
		manager.finishLoading();

		Skin skin = manager.get(fileSkin);
		
		Label label = new Label("My litle label", skin);
		label.setName("lbl1");
		
		Button button = new TextButton("Button", skin);
		button.setSize(70, 30);
		button.setName("btn1");
		
		Window window = new Window("Window", skin);
		window.setName("wnd1");
		window.add(label).expand().center().row();
		window.add(button).expandX().row();
		window.setSize(200, 150);
		stage.addActor(window);
		
		UIComponent builder = new UIComponent();
		ActorData actorData = builder.assemble(skin, window);
		window.remove();
		
		FileHandle file = new FileHandle(fileUi);
		Json json = new Json();
		json.setOutputType(OutputType.javascript);
		json.toJson(actorData, file);
		
		manager.load(fileUi, UIComponent.class);
		manager.finishLoading();
		
		builder = manager.get(fileUi);
		ObjectMap<String, Actor> actors = builder.build(skin);
		for (Actor a : actors.values()) {
			stage.addActor(a);
		}
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void render() {
		GL20 gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 0);
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.getViewport().update(width, height);
	}

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = 300;
		config.backgroundFPS = 0;
		config.title = "Test - " + Version.VERSION;
		new LwjglApplication(new UiComponentTest(), config);
	}

}
