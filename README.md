# LibGDX Design
*A visual Scene2D UI editor for LibGDX*

[![Watch the video](https://img.youtube.com/vi/7TFNbOt0BdA/0.jpg)](https://youtu.be/7TFNbOt0BdA)

Create any interface using the editor. Add the component module to your project.

```java
AssetManager manager = new AssetManager();
manager.setLoader(UIComponent.class, new UIComponentLoader(manager.getFileHandleResolver()));
manager.load(fileSkinPath, Skin.class);
manager.load(fileUiPath, UIComponent.class);
manager.finishLoading();

Skin skin = manager.get(fileSkin);
UIComponent component = manager.get(fileUiPath);
```
You can download the ui file directly:
```java
new UIComponent().load(fileHandle);
```
Place components on the stage:
```java
ObjectMap<String, Actor> actors = component.build(skin);
for (Actor a : actors.values()) {
    stage.addActor(a);
}
// Find actor
TextButton btn = ((Window) actors.get("wnd")).findActor("btn");
```

> 💡 **Need ready-made skins to start with?** Browse the community collection at  
> [https://github.com/czyzby/gdx-skins](https://github.com/czyzby/gdx-skins)
