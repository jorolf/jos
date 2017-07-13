package rusky.husky.testing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

import org.reflections.Reflections;

import rusky.husky.Anchor;
import rusky.husky.Axes;
import rusky.husky.Container;
import rusky.husky.Direction;
import rusky.husky.Drawable;
import rusky.husky.InputState;
import rusky.husky.MarginPadding;
import rusky.husky.containers.FlowContainer;
import rusky.husky.math.Vector2;
import rusky.husky.sprites.Box;
import rusky.husky.sprites.SpriteNumber;
import rusky.husky.standalone.Window;

public class TestCaseBrowser extends Window {
	private final FlowContainer<TestCaseButton> testCaseButtons = new FlowContainer<>();
	private final Container<Drawable> testCaseContainer = new Container<>();
	private final List<TestCase> testCases;
	private TestCase currentTestCase;
//	private WatchService watcher;
//	private final String testCasePackage;
//	private final Reflections reflect;

	public TestCaseBrowser(String testCasePackage){
//		this.testCasePackage = testCasePackage;
		testCaseContainer.setRelativeSizeAxes(Axes.both());
		testCaseContainer.setPadding(new MarginPadding().setLeft(100));
		add(testCaseContainer);

		Box bg = new Box();
		bg.setRelativeSizeAxes(EnumSet.of(Axes.Y));
		bg.setWidth(100);
		bg.setColor(Color.DARK_GRAY);
		add(bg);

		testCaseButtons.setWidth(100);
		testCaseButtons.setAutoSizeAxes(EnumSet.of(Axes.Y));
		testCaseButtons.setDirection(EnumSet.of(Direction.Vertical));
		testCaseButtons.setSpacing(new Vector2(0, 3));
		add(testCaseButtons);

		Reflections reflect = new Reflections(testCasePackage);
		testCases = reflect.getSubTypesOf(TestCase.class).stream()
				.sorted((case1, case2) -> case1.getName().compareTo(case2.getName()))
				.map(clazz -> {
					try {
						return clazz.newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
					}
					return null;
				}).collect(Collectors.toList());		
		showTestCase(testCases.get(0));
		testCases.forEach(this::addButton);
		
		Container<Drawable> fpsDisplay = new NumberDisplay(){
			long lastUpdate;
			
			@Override
			public void paint(Graphics2D gr) {
				setValue((System.nanoTime() - lastUpdate) / 1000000f);
				lastUpdate = System.nanoTime();
				
				super.paint(gr);
			}
		};
		fpsDisplay.setAutoSizeAxes(Axes.both());
		fpsDisplay.origin = Anchor.BottomLeft;
		fpsDisplay.anchor = Anchor.BottomLeft;
		add(fpsDisplay);
		
		Container<Drawable> tpsDisplay = new NumberDisplay(){
			long lastUpdate;
			
			@Override
			public void update(InputState state) {
				setValue((System.nanoTime() - lastUpdate) / 1000000f);
				lastUpdate = System.nanoTime();
				
				super.update(state);
			}
		};
		tpsDisplay.setAutoSizeAxes(Axes.both());
		tpsDisplay.origin = Anchor.BottomLeft;
		tpsDisplay.anchor = Anchor.BottomLeft;
		tpsDisplay.setY(-10);
		add(tpsDisplay);
		
//		try {
//			Path dir = Paths.get(TestCaseBrowser.class.getResource("/" + testCasePackage.replace('.', '/')).toURI());
//			watcher = FileSystems.getDefault().newWatchService();
//			dir.register(watcher, ENTRY_MODIFY, ENTRY_CREATE, ENTRY_DELETE);
//		} catch (URISyntaxException | IOException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public void init() {
		getParent().setFrameTime(0);
		getParent().setTickTime(0);
	}
	
//	@Override
//	protected void update(InputState state) {
//		super.update(state);
//		WatchKey key;
//		while((key = watcher.poll()) != null){
//			for(WatchEvent<?> event : key.pollEvents()){
//				if(!event.kind().type().equals(OVERFLOW)){
//					Path clazz = (Path) event.context();
////					try(URLClassLoader loader = new URLClassLoader(new URL[]{getClass().getProtectionDomain().getCodeSource().getLocation()} ,getClass().getClassLoader().getParent())){
//						Reflections reflect = new Reflections(testCasePackage, getClass().getClassLoader());
//						testCases.stream().filter(testCase -> testCase.getClass().getSimpleName().equals(clazz.toString().split("\\.")[0])).forEach(this::removeButton);
//						testCases.removeIf(testCase -> testCase.getClass().getSimpleName().equals(clazz.toString().split("\\.")[0]));
//						reflect.getSubTypesOf(TestCase.class).stream().filter(testCase -> testCase.getSimpleName().equals(clazz.toString().split("\\.")[0])).forEach(testCase ->{
//							try {
//								TestCase testCaseObj = testCase.newInstance();
//								addButton(testCaseObj);
//								testCases.add(testCaseObj);
//							} catch (InstantiationException | IllegalAccessException e) {
//								e.printStackTrace();
//							}
//						});
//						if(!testCases.contains(currentTestCase))
//							showTestCase(testCases.get(0));
////						Class<?> reloaded = loader.loadClass(testCasePackage + "." + clazz.toString().split("\\.")[0]);
////					} catch (IOException e) {
////						e.printStackTrace();
////					}
//				}
//			}
//		}
//		System.out.println("done!");
//	}

	@Override
	public String getName() {
		return "Test case browser";
	}

	private void showTestCase(TestCase testCase){
		if(testCase.equals(currentTestCase)){
			testCase.reset();
			return;
		}
		
		if(currentTestCase != null){
			testCaseContainer.remove(currentTestCase);
		}
		currentTestCase = testCase;
		testCase.reset();
		testCaseContainer.add(testCase);
	}

	private void addButton(TestCase testCase){
		TestCaseButton b = new TestCaseButton(testCase.getClass().getSimpleName().replaceFirst("TestCase", ""), testCase.getDescription(), () -> showTestCase(testCase));
		b.setRelativeSizeAxes(EnumSet.of(Axes.X));
		b.setHeight(45);
		b.masked = true;
		b.cornerRadius = 15;
		testCaseButtons.add(b);
		testCaseButtons.getChildren().sort((b1, b2) -> b1.getButtonText().compareTo(b2.getButtonText()));
	}
	
//	private boolean removeButton(TestCase testCase){
//		return testCaseButtons.getChildren().removeIf(button -> button.getButtonText().equals(testCase.getClass().getSimpleName().replaceFirst("TestCase", "")));
//	}

	@Override
	public boolean resizeable() {
		return true;
	}
	
	private class NumberDisplay extends Container<Drawable>{
		
		private SpriteNumber number;
		
		public NumberDisplay(){
			number = new SpriteNumber();
			number.setHeight(10);
			number.setColor(Color.RED);
			add(number);
			
			Box fpsBackground = new Box();
			fpsBackground.setRelativeSizeAxes(Axes.both());
			fpsBackground.depth = -1;
			add(fpsBackground);
		}
		
		public void setValue(float value){
			number.setValue(value);
		}
	}
}
