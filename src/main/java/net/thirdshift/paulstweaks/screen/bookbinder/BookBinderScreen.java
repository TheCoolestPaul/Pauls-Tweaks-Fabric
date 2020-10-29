package net.thirdshift.paulstweaks.screen.bookbinder;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class BookBinderScreen extends CottonInventoryScreen<BookBinderGuiDescription> {
	public BookBinderScreen(BookBinderGuiDescription gui, PlayerEntity player, Text title){
		super(gui,player,title);
	}
}
