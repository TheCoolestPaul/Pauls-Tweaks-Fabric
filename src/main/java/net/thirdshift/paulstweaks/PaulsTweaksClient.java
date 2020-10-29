package net.thirdshift.paulstweaks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.thirdshift.paulstweaks.screen.bookbinder.BookBinderGuiDescription;
import net.thirdshift.paulstweaks.screen.bookbinder.BookBinderScreen;

public class PaulsTweaksClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ScreenRegistry.<BookBinderGuiDescription, BookBinderScreen>register(PaulsTweaks.BOOK_BINDER_SCREEN_HANDLER_TYPE, (gui, inventory, title) -> new BookBinderScreen(gui, inventory.player, title));
	}
}
